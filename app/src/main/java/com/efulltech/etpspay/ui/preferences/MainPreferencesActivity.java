package com.efulltech.etpspay.ui.preferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.efulltech.etpspay.R;

import butterknife.OnClick;

public class MainPreferencesActivity extends AppCompatActivity {


    private Switch ttsSwitch;
    private Boolean switchState;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_preferences);
        // Initiate shared preferences
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
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
            com.efulltech.etpspay.ui.auth.login.LoginActivity.speakWords("Speech synthesis is now activated");
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
}
