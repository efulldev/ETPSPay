package com.efulltech.etpspay.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;


public class TTS extends Activity implements TextToSpeech.OnInitListener {


    private static final int MY_DATA_CHECK_CODE = 679;
    private TextToSpeech myTTS;
    private Context mContext;

    public TTS(Context ctx){
        this.mContext = ctx;
        this.myTTS = new TextToSpeech(this, this);

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

}
