package com.efulltech.etpspay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.efulltech.etpspay.ui.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(5*1000);

                    // After 5 seconds redirect
                    Intent i=new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);

                    //Remove activity
                    finish();
                }
                catch (Exception e) {
                    Log.d("Splash", e.toString());
                }
            }
        };
        // start thread
        background.start();
    }
}
