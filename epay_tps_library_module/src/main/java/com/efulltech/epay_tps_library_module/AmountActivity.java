package com.efulltech.epay_tps_library_module;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
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

import java.io.IOException;
import java.util.Locale;

public class AmountActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    Button proceed, cancel;
    private static final int MY_DATA_CHECK_CODE = 1209;
    public static TextToSpeech myTTS;
    SmartCardReaderx readerx;
    boolean threadRunT;
    String no = "";
    Handler handler;
    boolean turnedOn;
    SharedPreferences preferences;
    Led900 led = new Led900(this);



    //    used to declare preference wrt tts
    SharedPreferences mPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);
//        test = findViewById(R.id.amount);
        proceed = findViewById(R.id.proceedAmount);
        cancel = findViewById(R.id.cancelAmount);

        readerx = new SmartCardReaderx(AmountActivity.this);

//        handler = new Handler(getApplicationContext().getMainLooper());

        threadRunT = true;



//        used to initiate
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

//        this code is a beep sound notification
//        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
//        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                preferences = getSharedPreferences("AmountCat", MODE_PRIVATE);
//                preferences.edit().putString("AmountSent", test.getText().toString()).apply();
                finish();
                startActivity(new Intent(AmountActivity.this, PinActivity.class));
            }
        });


        Thread tsThread = new Thread() {
            private boolean speakThread = true;
            String ttsOption = mPreferences.getString("ttsOption", "true");

            public void run() {
                while(speakThread) {
                    try {
                        // Thread will sleep for 5 seconds
                        if(ttsOption.equals("true")) {
                            sleep(1 * 500);
                            CardPaymentActivity.speakWords("Kindly, input amount");
                            speakThread = false;
                            Thread.currentThread().isInterrupted();
                        }
                    } catch (Exception e) {
                        Log.d("Splash", e.toString());
                    }
                }
            }
        };
        tsThread.start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                // Opens the card readerx object in the thread to handle loop
                readerx.open();

                Log.d("ICC status", "Running");
                try{

                    turnedOn = readerx.iccPowerOn(1);
                }catch (Exception e){
                    e.printStackTrace();
                    finish();
                }
                while (threadRunT){
                    Log.d("ICC status", "Extra Running");
                    Log.d("Card type", Integer.toString(readerx.getCardType()));
                    try{
                        if (readerx.iccPowerOff()){

                            Log.d("Card Activity", "Powered on");

                        }else {
                            Log.d("Card log error", "Card turned off");
                            threadRunT = false;
                            new CardRemovedFragment().show(getSupportFragmentManager(), "Cardremoved");
                            Thread.currentThread().isInterrupted();
                            //      This are the various led activities when ever an atm card is removed

                            led.blink(3,5000);
                            led.off(2);
                            led.off(3);
                            led.off(4);

//                            playSound(AmountActivity.this);


                            AmountActivity.speakWords("Transaction Error, Card Remove");

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Thread.currentThread().isInterrupted();
                    }
                }
            }
        }).start();


//
//
//        test.setText(preferences.getString("accType", "No type Given"));
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
}
