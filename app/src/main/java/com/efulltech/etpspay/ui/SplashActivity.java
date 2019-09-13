package com.efulltech.etpspay.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.efulltech.etpspay.R;
import com.efulltech.etpspay.ui.auth.login.LoginActivity;
import com.efulltech.etpspay.ui.data.LoginDataSource;
import com.efulltech.etpspay.ui.data.LoginRepository;
import com.efulltech.etpspay.utils.Constants;
import com.efulltech.etpspay.utils.UsersDBHelper;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private ImageView splashLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        checkSharedPreferences();

        splashLogo = findViewById(R.id.logo_splash);

        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run() {
                try {
                        Intent loginIntent =new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                        //Remove activity
                        finish();
                }
                catch (Exception e) {
//                    Log.d("Splash", e.toString());
                }
            }
        }, 3000);

    }


    //checking the shared preferences and set them accordingly
    private void checkSharedPreferences() {
        String ttsOption = mPreferences.getString("ttsOption", null);
        if (ttsOption == null){
            mEditor.putString("ttsOption", "true");
            mEditor.apply();
        }
    }
}
