package com.efulltech.etpspay.ui.preferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.efulltech.etpspay.R;
import com.efulltech.etpspay.ui.auth.login.LoginActivity;

import java.util.Locale;

import butterknife.OnClick;

public class MainPreferencesActivity extends AppCompatActivity implements TextToSpeech.OnInitListener  {


    private Switch ttsSwitch;
    private Boolean switchState;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private static final int MY_DATA_CHECK_CODE = 13455;
    public static TextToSpeech myTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_preferences);

        // Initiate shared preferences
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        //check for TTS data
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

        // initiate tts Switch
        ttsSwitch = findViewById(R.id.ttsSwitch);
        // check current state of a Switch (true or false).
        switchState = ttsSwitch.isChecked();

        // get tts shared preference state
        String ttsOption = mPreferences.getString("ttsOption", "false");
        switch(ttsOption){
            case "true":
                switchState = true;
                break;
            default:
                switchState = false;
                break;
        }
        //set switch to reflect new state
        ttsSwitch.setChecked(switchState);
    }

    @OnClick(R.id.ttsSwitch)
    public void togTTS(View view) {
        switchState = ttsSwitch.isChecked();
        String appendage = "off";
        if(switchState){
            myTTS.setSpeechRate(1.1f);
            myTTS.speak("Speech synthesis is now activated", TextToSpeech.QUEUE_FLUSH, null);
            appendage = "on";
        }
        mEditor.putString("ttsOption", switchState.toString());
        mEditor.apply();
        Toast.makeText(this, "Speech synthesis has been turned "+appendage, Toast.LENGTH_SHORT).show();
    }

    public void openUserMgt(View view){
        Intent intent = new Intent(MainPreferencesActivity.this, UserMgtActivity.class);
        startActivity(intent);
    }

    //act on result of TTS data check
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
                myTTS = new TextToSpeech(MainPreferencesActivity.this,this);
            } else {
                //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
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
    public void onBackPressed(){
        super.onBackPressed();
        // end TTS
        myTTS.stop();
        myTTS.shutdown();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        // end TTS
        myTTS.stop();
        myTTS.shutdown();
    }
}
