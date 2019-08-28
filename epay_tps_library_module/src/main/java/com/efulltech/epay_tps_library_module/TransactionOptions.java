package com.efulltech.epay_tps_library_module;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class TransactionOptions extends AppCompatActivity {

    SmartCardReaderx readerx;
    boolean threadRunT;
    String no = "";
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_options);

        readerx = new SmartCardReaderx(TransactionOptions.this);

        handler = new Handler(getApplicationContext().getMainLooper());




//        Log.d("ICC stat", Boolean.toString(readerx.isICCPresent()));

        new Thread(new Runnable() {
            @Override
            public void run() {
                readerx.open();
//                threadRunT = readerx.iccPowerOn();
//                threadRunT = readerx.isICCPresent();
                Log.d("ICC status", "Running");
                while (threadRunT){
                    try{
                        if (readerx.iccPowerOff()){
                            Log.d("Card Activity", "Powered off");
//                     try {
//                            no = readerx.getATRString();
//                         Log.d("Atr String", no);
//                        no = readerx.setATRString();
//                         readerx.close();
//                         threadRun = false;
//                     }catch (Exception e){
////                         Toast.makeText(TransactionOptions.this, "Card removed", Toast.LENGTH_LONG).show();
//                         e.printStackTrace();
//                     }
//                     Toast.makeText(TransactionOptions.this, "Card removed", Toast.LENGTH_LONG).show();
//                     startActivity(new Intent(TransactionOptions.this, CardPaymentActivity.class));
//                            if (!readerx.isICCPresent()){
                                threadRunT = false;
                                Log.d("Card log error", "Card removed");
//                                finish();
//                            }
                        }else {
                            Log.d("Card log error", "Card error");
                            threadRunT = false;

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                        Thread.currentThread().isInterrupted();
                    }

                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        threadRunT = false;
    }
}
