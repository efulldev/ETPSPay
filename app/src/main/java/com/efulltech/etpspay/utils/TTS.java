package com.efulltech.etpspay.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;


public class TTS extends Activity implements TextToSpeech.OnInitListener {


//    private static final int MY_DATA_CHECK_CODE = 679;
    private TextToSpeech myTTS;
    private Context mContext;
    private Activity mActivity;
    private int mRequestCode;

    public TTS(Activity activity, Context ctx, int requestCode){
        this.mContext = ctx;
        this.mActivity = activity;
        this.mRequestCode = requestCode;
        //check for TTS data
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        activity.startActivityForResult(checkTTSIntent, requestCode);
    }

    public int getmRequestCode(){
        return this.mRequestCode;
    }

    public void speakWords(String speech) {
//        speak straight away
        myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
        myTTS.setSpeechRate(0.3f);

    }

    @Override
    public void onInit(int initStatus) {
        //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            if(myTTS.isLanguageAvailable(Locale.UK)==TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.UK);
        }
        else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(mContext, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }

    //act on result of TTS data check
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.mRequestCode) {
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

}
