package com.efulltech.etpspay.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;


import com.efulltech.etpspay.R;

import java.util.Timer;
import java.util.TimerTask;

public class CardPaymentActivity extends AppCompatActivity {

//    Handler handler;
    private TranslateAnimation moveUpwards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_payment);

        moveUpwards = new TranslateAnimation(0, 0, 1000, 0);
        moveUpwards.setDuration(3000);
        moveUpwards.setFillAfter(true);
        moveUpwards.setRepeatCount(-1);
        findViewById(R.id.card).startAnimation(moveUpwards);


    }

    @Override
    protected void onResume() {
        super.onResume();
//        startTimer();
    }


}
