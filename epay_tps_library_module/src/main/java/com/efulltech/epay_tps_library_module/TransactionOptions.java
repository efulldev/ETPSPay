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
import androidx.fragment.app.FragmentManager;

import com.telpo.tps550.api.led.Led900;

import java.util.Locale;

import static android.os.SystemClock.sleep;

public class TransactionOptions extends BaseActivity  implements TextToSpeech.OnInitListener{


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
    private String cardType;
    private int accType;
    private String ttsOption;
    Button savings, current, credits, defaultBtn;
    private boolean sessionDialogState;

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
        ttsOption = mPreferences.getString("ttsOption", "false");
        sessionDialogState = false;

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
                    sleep(500);
                    TransactionOptions.speakWords(" Choose account type to proceed");
                }

                while (threadRunT){
                    sessionDialogState = mPreferences.getBoolean("sessionDialogState", false);
                    if(!sessionDialogState) { //checks that the session timed out dialog is no displayed
                        try {
                            if (!readerx.iccPowerOff()) {
                                Log.d("Card log error", "Card turned off");
                                threadRunT = false;
//                            "Transaction interrupted, kindly restart the process"
                                finishActivityWithErrorMsg("Transaction Error, Card Removed");
                                thread.isInterrupted();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            thread.isInterrupted();
                        }
                    }else{
                        // interrupt thread
                        interruptThreads();
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

    private void finishActivityWithErrorMsg(String error){
        thread.isInterrupted();
        Intent _data = new Intent();
        _data.putExtra("response", ""+error);
        _data.putExtra("positive", false);
        setResult(RESULT_CANCELED, _data);
        finish();
    }

    private void showEditDialog(String title, String message, Boolean positive) {
        FragmentManager fm = getSupportFragmentManager();
        AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(title, message, positive);
        alertDialogFragment.show(fm, "fragment_edit_name");
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
                String response = data.getStringExtra("response");
                Boolean positive = data.getBooleanExtra("positive", false);

                Intent _data = new Intent();
                _data.putExtra("response", response);
                _data.putExtra("positive", positive);

                setResult(RESULT_CANCELED, _data);
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
        finishActivityWithErrorMsg("Transaction aborted");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTTS.stop();
        myTTS.shutdown();
        interruptThreads();
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
