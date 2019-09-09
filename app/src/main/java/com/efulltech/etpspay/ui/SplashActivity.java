package com.efulltech.etpspay.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.efulltech.etpspay.R;
import com.efulltech.etpspay.ui.auth.login.LoginActivity;
import com.efulltech.etpspay.ui.data.LoginDataSource;
import com.efulltech.etpspay.ui.data.LoginRepository;

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
                    // get username and pin from shared preferences
                    String username = "admin";
                    String password = "1234567890";

                    if(username.trim().length() > 0 && password.trim().length() > 0){

                        LoginDataSource dataSource = new LoginDataSource();
                        dataSource.login(username, password);
                        LoginRepository loginRepository = LoginRepository.getInstance(dataSource);

                        // check if user is logged in
                        if(loginRepository.isLoggedIn()){
                            // grant user access
                            Intent appIntent =new Intent(SplashActivity.this, MainActivity.class);
                              startActivity(appIntent);
                        }else{
                            // take user to log in view
                            Intent loginIntent =new Intent(SplashActivity.this, LoginActivity.class);

//                            Pair[] pairs  = new Pair[1];
//                            pairs[0] = new Pair<View, String>(splashLogo, "logoTransition");
//                            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pairs);
//
//                            startActivity(loginIntent, activityOptions.toBundle());
                            startActivity(loginIntent);
                        }
                    }else{
                        // take user to log in view
                        Intent loginIntent =new Intent(SplashActivity.this, LoginActivity.class);

//                        Pair[] pairs  = new Pair[1];
//                        pairs[0] = new Pair<View, String>(splashLogo, "logoTransition");
//                        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pairs);
//
//                        startActivity(loginIntent, activityOptions.toBundle());
                        startActivity(loginIntent);
                    }


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
