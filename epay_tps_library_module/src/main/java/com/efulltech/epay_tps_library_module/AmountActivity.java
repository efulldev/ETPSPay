package com.efulltech.epay_tps_library_module;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.telpo.tps550.api.led.Led900;

import java.io.IOException;
import java.util.Locale;

import static android.os.SystemClock.sleep;


public class AmountActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private static final int PIN_RES_CODE = 584;
    Button proceed, cancel;
    EditText amountTxt;
    private static final int MY_DATA_CHECK_CODE = 1209;
    public static TextToSpeech myTTS;
    SmartCardReaderx readerx;
    private boolean threadRunT;
    String no = "";
    Handler handler;
    Led900 led = new Led900(this);
    Thread thread;
    SharedPreferences mPreferences;

    private String cardType;
    private int accType;
    private String ttsOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);
        // get values passed via intent
        Intent intent = getIntent();
        this.cardType = intent.getStringExtra("cardType");
        this.accType = intent.getIntExtra("accType", 0);

//        test = findViewById(R.id.amount);
        proceed = findViewById(R.id.proceedAmount);
        cancel = findViewById(R.id.cancelAmount);
        amountTxt = findViewById(R.id.amount);

        readerx = new SmartCardReaderx(AmountActivity.this);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        ttsOption = mPreferences.getString("ttsOption", "false");
        threadRunT = true;

        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

        cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               setResult(RESULT_CANCELED);
               finish();
           }
       });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = amountTxt.getText().toString().trim();
//                double amt  = Double.parseDouble(amount);
//                if(!amount.equals(null)) {
                    if(amountTxt.getText().length() > 0) {
                        Intent pinIntent = new Intent(AmountActivity.this, PinActivity.class);
                        pinIntent.putExtra("cardType", cardType);
                        pinIntent.putExtra("accType", accType);
                        pinIntent.putExtra("amount", amount);
                        finish();
                        startActivityForResult(pinIntent, PIN_RES_CODE);
                    }else{
                        Toast.makeText(AmountActivity.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                    }
//                }else{
//                    Toast.makeText(AmountActivity.this, "Input amount to proceed", Toast.LENGTH_SHORT).show();
//                }
            }
        });


        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Opens the card readerx object in the thread to handle loop
                readerx.open();
                if(ttsOption.equals("true")) {
                    sleep(1 * 500);
                    CardPaymentActivity.speakWords("Kindly input amount");
                }
                while (threadRunT){
                    try{
                        if (!readerx.iccPowerOff()) {
                            Log.d("Card log error", "Card turned off");
                            threadRunT = false;
                            new CardRemovedFragment().show(getSupportFragmentManager(), "Cardremoved");
                            thread.isInterrupted();
                            if (ttsOption.equals("true")) {
                                AmountActivity.speakWords("Transaction Error, Card Remove");
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
        else if(requestCode == PIN_RES_CODE){
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


    public void playSound(Context context) throws IllegalArgumentException,
            SecurityException,
            IllegalStateException,
            IOException {

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        MediaPlayer mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setDataSource(context, soundUri);
        final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTTS.stop();
        myTTS.shutdown();
        interruptThreads();
        setResult(RESULT_CANCELED);
    }
}
