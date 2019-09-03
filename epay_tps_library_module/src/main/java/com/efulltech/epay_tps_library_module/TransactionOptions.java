package com.efulltech.epay_tps_library_module;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.telpo.tps550.api.TelpoException;

public class TransactionOptions extends AppCompatActivity {


    private static final int MY_DATA_CHECK_CODE = 1309;
    private static TextToSpeech myTTS;
    SmartCardReaderx readerx;
    boolean threadRunT;
    String no = "";
    Handler handler;
    boolean turnedOn;

    SharedPreferences preferences;

    Button savings,current, credits, defaultBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_options);

        readerx = new SmartCardReaderx(TransactionOptions.this);
        handler = new Handler(getApplicationContext().getMainLooper());
        threadRunT = true;


        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

        Thread _ttsThread = new Thread() {
            private boolean speakThread = true;
            public void run() {
                while(speakThread) {
                    try {
                        // Thread will sleep for 5 seconds
                        sleep(1 * 500);
                        CardPaymentActivity.speakWords("Please Choose account type");
                        speakThread = false;
                        Thread.currentThread().isInterrupted();
                    } catch (Exception e) {
                        Log.d("Splash", e.toString());
                    }
                }
            }
        };
        _ttsThread.start();

//        Log.d("ICC stat", Boolean.toString(readerx.isICCPresent()));

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
                    try{
                        if (readerx.iccPowerOff()){
                            Log.d("Card Activity", "Powered on");
//                            if (!readerx.isICCPresent()){
//                                threadRunT = false;
//                                Log.d("Card log", "Card is on");
////                                finish();
//                            }
                        }else {
                            Log.d("Card log error", "Card turned off");
                            threadRunT = false;
                            new CardRemovedFragment().show(getSupportFragmentManager(), "Cardremoved");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Thread.currentThread().isInterrupted();
                    }
                }
            }
        }).start();

        savings = findViewById(R.id.savings);
        current = findViewById(R.id.current);
        credits = findViewById(R.id.credits);
        defaultBtn = findViewById(R.id.defaultBtn);

        preferences = getSharedPreferences("AccCat", MODE_PRIVATE);

        savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionOptions.this, AmountActivity.class);
//                intent.putExtra("AccountType", "type1");
                preferences.edit().putString("accType", "type1").apply();
                //Type 1 is for savings
                startActivity(intent);

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        threadRunT = false;
        Thread.currentThread().isInterrupted();
    }
}
