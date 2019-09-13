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
    SmartCardReaderx readerx;
    boolean threadRunT;
    String no = "";
    Handler handler;
    boolean turnedOn;
    Led900 led = new Led900(this);

    public Thread thread;


    SharedPreferences preferences;


//    used to declare preference wrt tts
    SharedPreferences mPreferences;

    Button savings,current, credits, defaultBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_options);

        readerx = new SmartCardReaderx(TransactionOptions.this);
        handler = new Handler(getApplicationContext().getMainLooper());
        threadRunT = true;

//        used to initiate
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);


        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread _ttsThread = new Thread() {
            private boolean speakThread = true;
            String ttsOption = mPreferences.getString("ttsOption", "true");

            public void run() {
                while(speakThread) {
                    try {
                        // Thread will sleep for 5 seconds
                        if(ttsOption.equals("true")) {
                            sleep(1 * 500);
                            TransactionOptions.speakWords(" Choose account type to proceed");
                            speakThread = false;
                            //Interrupt the thread
                            Thread.currentThread().isInterrupted();
                        }


                    } catch (Exception e) {
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
                        if (readerx.iccPowerOn()){ // Turned it from Off to On
//                            led.on(2);

                            Log.d("Card Activity", "Powered on");
                        }else {
                            Log.d("Card log error", "Card turned off");
                            threadRunT = false;
                            new CardRemovedFragment().show(getSupportFragmentManager(), "Card Removed");
                            Thread.currentThread().isInterrupted();

//                          These are the various led activities whenever an atm card is removed
                            led.blink(3, 5000);
                            led.off(2);
                            led.off(3);
                            led.off(4);

                            TransactionOptions.speakWords("Transaction Error, Card Removed");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Thread.currentThread().isInterrupted();
                    }
                }
            }

        });
       thread.start();

        savings = findViewById(R.id.savings);
        current = findViewById(R.id.current);
        credits = findViewById(R.id.credits);
        defaultBtn = findViewById(R.id.defaultBtn);

        preferences = getSharedPreferences("AccCat", MODE_PRIVATE);

        savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();


                Intent intent = new Intent(TransactionOptions.this, AmountActivity.class);
//                intent.putExtra("AccountType", "type1");
                preferences.edit().putString("accType", "type1").apply();
                //Type 1 is for savings
                startActivity(intent);
            }
        });

}




    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Thread.currentThread().isInterrupted();
//        threadRunT = false;
        ((TimeOutController) getApplication()).cancelTimer();
        thread.isInterrupted();
//        new CardErrorFragment("Card Error").show(getSupportFragmentManager(), "Please eject your card");
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

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        try {
//            led.off(2);
//        }catch (TelpoException e){
//            e.printStackTrace();
//        }
//    }


        @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.isInterrupted();
        ((TimeOutController) getApplication()).cancelTimer();
    }
}
