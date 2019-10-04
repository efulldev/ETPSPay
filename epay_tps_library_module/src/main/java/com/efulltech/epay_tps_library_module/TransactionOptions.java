package com.efulltech.epay_tps_library_module;

import android.app.Activity;
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

import static android.os.SystemClock.sleep;

public class TransactionOptions extends AppCompatActivity  implements TextToSpeech.OnInitListener{


    private static final int MY_DATA_CHECK_CODE = 1309;
    private static final int AMT_RES_CODE = 874;
    public static TextToSpeech myTTS;
    private SmartCardReaderx readerx;
    private boolean threadRunT;
    private Thread thread;
    private String no = "";
    private Handler handler;
    private boolean turnedOn;
    private Led900 led;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    Button savings,current, credits, defaultBtn;
    private String cardType;
    private int accType;
    private String ttsOption;

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

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        ttsOption = mPreferences.getString("ttsOption", "false");

        readerx = new SmartCardReaderx(TransactionOptions.this);
        handler = new Handler(getApplicationContext().getMainLooper());
        threadRunT = true;
        led = new Led900(this);

        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

       thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Opens the card readerx object in the thread to handle loop
                readerx.open();
                // Thread will sleep for 5 seconds
                if(ttsOption.equals("true")) {
                    sleep(1 * 500);
                    TransactionOptions.speakWords(" Choose account type to proceed");
                }
                while (threadRunT){
                    try{
                        if (!readerx.iccPowerOff()){
                            Log.d("Card log error", "Card turned off");
                            threadRunT = false;
                            new CardRemovedFragment().show(getSupportFragmentManager(), "Card Removed");
                            thread.isInterrupted();
                            if(ttsOption.equals("true")) {
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
        startActivityForResult(intent, AMT_RES_CODE);
    }


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
        else if(requestCode == AMT_RES_CODE){
            if(resultCode == Activity.RESULT_OK){
                // process was completed

                Toast.makeText(this, "Activity completed!!!", Toast.LENGTH_SHORT).show();
            }else{
                // process was interrupted
                setResult(RESULT_CANCELED);
                finish();
            }
            interruptThreads();
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
//        interruptThreads();
//        new CardErrorFragment("Card Error").show(getSupportFragmentManager(), "Please eject your card");


        // return result to the calling activity
//        Intent _data = new Intent();
//        _data.putExtra("response", "Transaction Successful");
        setResult(RESULT_CANCELED);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTTS.stop();
        myTTS.shutdown();
        interruptThreads();
        setResult(RESULT_CANCELED);
    }

    private void interruptThreads(){
        Thread[] threads = {thread};
        Boolean[] threadsBoo = {threadRunT};

        for(int i = 0; i < threads.length; i++){
            // interrupt threads
            threads[i].isInterrupted();
            // set booleans to false
            threadsBoo[i] = false;
        }
//        ((TimeOutController) getApplication()).cancelTimer();
    }
}
