package com.efulltech.epay_tps_library_module;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.TranslateAnimation;

import com.telpo.tps550.api.TelpoException;

public class CardPaymentActivity extends AppCompatActivity {

    //    Handler handler;
    private TranslateAnimation moveUpwards;
    boolean threadRun;
    private SmartCardReaderx cardReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_payment);

        moveUpwards = new TranslateAnimation(0, 0, 1000, 0);
        moveUpwards.setDuration(3000);
        moveUpwards.setFillAfter(true);
        moveUpwards.setRepeatCount(-1);
        findViewById(R.id.card).startAnimation(moveUpwards);
        Log.d("CPA", "Card payment Activity");

        cardReader = new SmartCardReaderx(CardPaymentActivity.this);
//        cardReader.mCardReader.open(1);
//        Log.d("New String", cardReader.getATRString());
//
//        // Initialize a boolean data type to control the thread
//        threadRun = cardReader.open(1);
//
//        //Run the thread to enable active listening for changes on the state of the port.
        threadRun = true;
        new Thread(new Runnable() {
            @Override
            public void run(){
                // Indicates that the thread is started
                Log.d("CPA", "Initiated Card reader listener");
                cardReader.open();
                while (threadRun){
                    // Indicates that the while loop is running
                    Log.d("CPA", "Running while loop");
                    try {
                        Log.d("CPA", "Listening for card insertion");
                        // Listening for insertion of a card
                        if (cardReader.iccPowerOn()){
                            Log.d("CPA", "ICC Powered On");
                            threadRun = false;
                            finish();
                            startActivity(new Intent(CardPaymentActivity.this, TransactionOptions.class));
                            Log.d("CPA", "ICC card inserted");
                        }else{
                            Log.d("CPA", "ICC Powered off");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // terminates the thread after catching error
                        Thread.currentThread().isInterrupted();
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

