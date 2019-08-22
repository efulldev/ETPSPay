package com.efulltech.epay_tps_library_module;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.TranslateAnimation;

public class CardPaymentActivity extends AppCompatActivity {

    //    Handler handler;
    private TranslateAnimation moveUpwards;
    private SmartCardReaderx cardReader;
    boolean threadRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_payment);

        moveUpwards = new TranslateAnimation(0, 0, 1000, 0);
        moveUpwards.setDuration(3000);
        moveUpwards.setFillAfter(true);
        moveUpwards.setRepeatCount(-1);
        findViewById(R.id.card).startAnimation(moveUpwards);
        cardReader = new SmartCardReaderx(CardPaymentActivity.this);
        cardReader.open();

        // Initialize a boolean data type to control the thread
        threadRun = true;

        //Run the thread to enable active listening for changes in the state of the port.
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (threadRun){
                    if (cardReader.iccPowerOn()){
                        threadRun = false;
                        finish();
                        startActivity(new Intent(CardPaymentActivity.this, TransactionOptions.class));
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        startTimer();
    }

}

