package com.efulltech.epay_tps_library_module;

import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements LogoutListener {

    public SharedPreferences sharedPreferences;
    private boolean isBeeping;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isBeeping = true;
        // Initialize the session listener
        ((TimeOutController) getApplication()).registerSessionListener(this);

        // Start session
        ((TimeOutController) getApplication()).startUserSession();

        // Create shared preference to control the session and card removal clashes
        sharedPreferences = getSharedPreferences("SessionController", MODE_PRIVATE);
    }

    @Override
    public void onSessionLogOut() {
        sharedPreferences.edit().putString("sessionState", "sessionLogOut").apply();
        ((TimeOutController) getApplication()).cancelTimer();
        final ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 800);



        new CardErrorFragment("Session timed out").show(getSupportFragmentManager(), "Card insertion timed out");
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
