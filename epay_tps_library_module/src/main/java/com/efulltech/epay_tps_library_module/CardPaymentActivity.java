package com.efulltech.epay_tps_library_module;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.led.Led900;

import java.util.Locale;

public class CardPaymentActivity extends BaseActivity implements TextToSpeech.OnInitListener {

    //    Handler handler;
    private static final int MY_DATA_CHECK_CODE = 1309;
    private static TextToSpeech myTTS;
    private TranslateAnimation moveSideways;
    boolean threadRun;
    private SmartCardReaderx cardReader;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    Led900 led = new Led900(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_payment);

//        initialising shared preferences
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

//        getting value from intent
        String ttsOpt = getIntent().getStringExtra("ttsOption");
//        setting shared preference value to what was gotten from the intent
        mEditor.putString("ttsOption", ttsOpt);
        mEditor.commit();

//        Text to speech code
        //check for TTS data
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);


//        this is the thread for text to speech

        Thread ttsThread = new Thread() {
            private boolean speakThread = true;
            String ttsOption = mPreferences.getString("ttsOption", "true");

            public void run() {
                while(speakThread) {
                    try {

//                        led.on(4);

                        // Thread will sleep for 5 seconds
                        if(ttsOption.equals("true")) {
                            sleep(1 * 500);

                            led.blink(3,5000);

                            CardPaymentActivity.speakWords("Please insert your card");

                            speakThread = false;

                            Thread.currentThread().isInterrupted();
                        }
                    } catch (Exception e) {
                        Log.d("Splash", e.toString());
                    }
                }
            }
        };
        ttsThread.start();

        //this is an animation of the atm card display

        moveSideways = new TranslateAnimation(1000, 0, 0, 0);
        moveSideways.setDuration(3000);
        moveSideways.setFillAfter(true);
        moveSideways.setRepeatCount(-1);
        findViewById(R.id.card).startAnimation(moveSideways);

        cardReader = new SmartCardReaderx(CardPaymentActivity.this);

//
//        //Run the thread to enable active listening for changes on the state of the port.
        threadRun = true;
        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run(){
                // Indicates that the thread is started
                Log.d("CPA", "Initiated Card reader listener");
                cardReader.open();
                while (threadRun){
                    // Indicates that the while loop is running
                    Log.d("CPA", "Running while loop");
                    try {
                        Log.d("CPA", "Listening for card insertion");
                        // Listening for insertion of a card
                        if (cardReader.iccPowerOn()){
                            Log.d("CPA", "ICC Powered On");
                            try {
                                led.off(3);
                            } catch (TelpoException e) {
                                e.printStackTrace();
                            }
                            threadRun = false;
                            Thread.currentThread().isInterrupted();
                            Log.d("CPA", "ICC card inserted");
                            finish();
                            startActivity(new Intent(CardPaymentActivity.this, TransactionOptions.class));
                        }else{
                            if (!cardReader.iccPowerOff()){
                                cardReader.iccPowerOff();
                            }
                            Log.d("CPA", "ICC Powered off");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // terminates the thread after catching error
                        Thread.currentThread().isInterrupted();
                    }
                }
            }
        });

        newThread.start();

    }




    @Override
    protected void onResume() {
        super.onResume();
//        startTimer();
    }

    public static void speakWords(String speech) {
        myTTS.setSpeechRate(1.3f);
        //speak straight away
        myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
    }

    //act on result of TTS data check
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
                myTTS = new TextToSpeech(CardPaymentActivity.this,this);
            } else {
                //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    //setup TTS
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


//    This function destroys the yellow led light when back button is pressed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cardReader.iccPowerOff();
        ((TimeOutController) getApplication()).cancelTimer();
        try {
            led.off(3);
        } catch (TelpoException e) {
            e.printStackTrace();
        }
    }
}

