package com.efulltech.epay_tps_library_module;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.telpo.tps550.api.led.Led900;

import java.util.Locale;

public class TransactionOptions extends AppCompatActivity  implements TextToSpeech.OnInitListener{


    private static final int MY_DATA_CHECK_CODE = 1309;
    public static TextToSpeech myTTS;
    private SmartCardReaderx readerx;
    private boolean threadRunT, speakThread;
    private Thread thread, _ttsThread;
    private String no = "";
    private Handler handler;
    private boolean turnedOn;
    private Led900 led;
    private SharedPreferences preferences, mPreferences;
    private SharedPreferences.Editor mEditor;
    Button savings,current, credits, defaultBtn;
    private String cardType;
    private int accType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_options);
        // get values passed via intent
        Intent intent = getIntent();
        this.cardType = intent.getStringExtra("cardType");

        savings = findViewById(R.id.savings);
        current = findViewById(R.id.current);
        credits = findViewById(R.id.credits);
        defaultBtn = findViewById(R.id.defaultBtn);

        preferences = getSharedPreferences("AccCat", MODE_PRIVATE);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        readerx = new SmartCardReaderx(TransactionOptions.this);
        handler = new Handler(getApplicationContext().getMainLooper());
        threadRunT = true; speakThread = true;
        led = new Led900(this);

        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);


        _ttsThread = new Thread() {
            String ttsOption = mPreferences.getString("ttsOption", "false");

            public void run() {
                while(speakThread) {
                    try {
                        // Thread will sleep for 5 seconds
                        if(ttsOption.equals("true")) {
                            sleep(1 * 500);
                            TransactionOptions.speakWords(" Choose account type to proceed");
                            speakThread = false;
                            //Interrupt the thread
                            _ttsThread.isInterrupted();
                        }
                    } catch (Exception e) {
                        speakThread = false;
                        _ttsThread.isInterrupted();
                        Log.d("Splash", e.toString());
                    }
                }
            }
        };
        _ttsThread.start();


       thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String ttsOption = mPreferences.getString("ttsOption", "true");

                // Opens the card readerx object in the thread to handle loop
                readerx.open();
                Log.d("ICC status", "Running");
//                try{
//                   turnedOn = readerx.iccPowerOn(1);
//                }catch (Exception e){
//                    e.printStackTrace();
//                    finish();
//                }
                while (threadRunT){
                    Log.d("ICC status", "Extra Running");
                    Log.d("Card type", Integer.toString(readerx.getCardType()));
                    try{
                        if (readerx.iccPowerOff()){ // Turned it from Off to On
//                            led.on(2);
                            Log.d("Card Activity", "Powered on");
                        }else {
                            Log.d("Card log error", "Card turned off");
                            threadRunT = false;
                            new CardRemovedFragment().show(getSupportFragmentManager(), "Card Removed");
                            thread.isInterrupted();

//                          These are the various led activities whenever an atm card is removed
                            led.blink(3, 5000);
                            led.off(2);
                            led.off(3);
                            led.off(4);

                            if(ttsOption == "true") {
                                TransactionOptions.speakWords("Transaction Error, Card Removed");
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        thread.isInterrupted();
                    }
                }
            }

        });
       thread.start();

        savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accType = 1; // savings account type
                amountActivity();
            }
        });

}


    private void amountActivity(){
        Intent intent = new Intent(TransactionOptions.this, AmountActivity.class);
        intent.putExtra("cardType", this.cardType);
        intent.putExtra("accType", this.accType);
        finish();
        startActivity(intent);
    }

//    Note: onActivityResult,  speakwords onInt, onPointer capture change are functions that makes the text to speech work so it must be included in all pages
    //act on result of TTS data check
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
                myTTS = new TextToSpeech(this,this);
            } else {
                //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }


    public static void speakWords(String speech) {
        myTTS.setSpeechRate(1.3f);
        //speak straight away
        myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
    }


    @Override
    public void onInit(int initStatus) {
        //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            if(myTTS.isLanguageAvailable(Locale.UK)==TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.UK);
        }
        else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        interruptThreads();
        new CardErrorFragment("Card Error").show(getSupportFragmentManager(), "Please eject your card");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTTS.stop();
        myTTS.shutdown();
        interruptThreads();
    }

    private void interruptThreads(){
        Thread[] threads = {thread, _ttsThread};
        Boolean[] threadsBoo = {threadRunT, speakThread};

        for(int i = 0; i < threads.length; i++){
            // interrupt threads
            threads[i].isInterrupted();
            // set booleans to false
            threadsBoo[i] = false;
        }
        ((TimeOutController) getApplication()).cancelTimer();
    }
}
