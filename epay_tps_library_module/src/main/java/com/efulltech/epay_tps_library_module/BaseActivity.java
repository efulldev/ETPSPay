package com.efulltech.epay_tps_library_module;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Method;

import static android.os.SystemClock.sleep;

public class BaseActivity extends AppCompatActivity implements LogoutListener {

    private boolean isBeeping;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isBeeping = true;
        // Initialize the session listener
        ((TimeOutController) getApplication()).registerSessionListener(this);
        // Start session
        ((TimeOutController) getApplication()).startUserSession();
        // Create shared preference to control the session and card removal clashes
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        mEditor.putBoolean("sessionDialogState", false);
        mEditor.apply();
    }

    @Override
    public void onSessionLogOut() {
        mEditor.putBoolean("sessionDialogState", true);
        mEditor.apply();
        ((TimeOutController) getApplication()).cancelTimer();
        final ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 800);
        // Display an alert dialog indicating timeout
        CardErrorFragment errorFragment = new CardErrorFragment("Session timed out");
        errorFragment.show(getSupportFragmentManager(), "Card insertion timed out");
//        sleep(100);
//        errorFragment.dismiss();

//        Intent _data = new Intent();
//        _data.putExtra("response", "Session timeout");
//        _data.putExtra("positive", false);
//        setResult(RESULT_CANCELED, _data);
//        finish();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        ((TimeOutController) getApplication()).onUserInteracted();
        isBeeping = false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ((TimeOutController) getApplication()).cancelTimer();
        isBeeping = false;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((TimeOutController) getApplication()).cancelTimer();
        isBeeping = false;
    }
}
