package com.efulltech.epay_tps_library_module;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements LogoutListener {

    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        new CardErrorFragment("Session timed out").show(getSupportFragmentManager(), "Card insertion timed out");
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        ((TimeOutController) getApplication()).onUserInteracted();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ((TimeOutController) getApplication()).cancelTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((TimeOutController) getApplication()).cancelTimer();
    }
}
