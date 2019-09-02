package com.efulltech.epay_tps_library_module;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.telpo.tps550.api.util.StringUtil;

public class AmountActivity extends AppCompatActivity {

    EditText test;
    SharedPreferences preferences;
    Button procced, cancel;

    SmartCardReaderx readerx;
    boolean threadRunT;
    String no = "";
    Handler handler;
    boolean turnedOn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);

        test = findViewById(R.id.amount);
        procced = findViewById(R.id.proceedAmount);
        cancel = findViewById(R.id.cancelAmount);

        readerx = new SmartCardReaderx(AmountActivity.this);

//        handler = new Handler(getApplicationContext().getMainLooper());

        threadRunT = true;

        procced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getSharedPreferences("AmountCat", MODE_PRIVATE);
                preferences.edit().putString("AmountSent", test.getText().toString()).apply();
                finish();
                startActivity(new Intent(AmountActivity.this, PinActivity.class));
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                // Opens the card readerx object in the thread to handle loop
                readerx.open();
                Log.d("ICC status", "Running");
                try{
                    turnedOn = readerx.iccPowerOn(1);
                }catch (Exception e){
                    e.printStackTrace();
                    finish();
                }
                while (threadRunT){
                    Log.d("ICC status", "Extra Running");
                    Log.d("Card type", Integer.toString(readerx.getCardType()));
                    try{
                        if (readerx.iccPowerOff()){
                            Log.d("Card Activity", "Powered on");
                        }else {
                            Log.d("Card log error", "Card turned off");
                            threadRunT = false;
                            new CardRemovedFragment().show(getSupportFragmentManager(), "Cardremoved");
                            Thread.currentThread().isInterrupted();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Thread.currentThread().isInterrupted();
                    }
                }
            }
        }).start();





    }
}
