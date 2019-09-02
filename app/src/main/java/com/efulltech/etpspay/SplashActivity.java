package com.efulltech.etpspay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import com.efulltech.etpspay.ui.MainActivity;
import com.efulltech.etpspay.ui.auth.login.LoginActivity;
import com.efulltech.etpspay.ui.data.LoginDataSource;
import com.efulltech.etpspay.ui.data.LoginRepository;

import java.util.Locale;

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
                            startActivity(loginIntent);
                        }
                    }else{
                        // take user to log in view
                        Intent loginIntent =new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                    }


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
