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
                Log.d("CPA", "Initiated Card reader listener");
                cardReader.open();
//                cardReader.iccPowerOn(1);
                while (threadRun){
                    Log.d("CPA", "Running while loop");
//                    isPoweredOn = cardReader.iccPowerOn();
                    try {
//                        cardReader.mICCardReader.power_on(cardReader.SLOT_ICC);
                        Log.d("CPA", "Listening for card insertion");
                        if (cardReader.iccPowerOn(1)){
//                            cardReader.mICCardReader.power_on(cardReader.SLOT_ICC);
                            Log.d("CPA", "ICC Powered On");
                            threadRun = false;
                            finish();
                            startActivity(new Intent(CardPaymentActivity.this, TransactionOptions.class));
                            Log.d("CPA", "ICC card inserted");
//                            cardReader.iccPowerOff();
                        }else{
//                        isPoweredOn = cardReader.iccPowerOn();
                            Log.d("CPA", "ICC Powered off");
                        }
//                        cardReader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
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

