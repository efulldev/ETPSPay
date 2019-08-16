package com.efulltech.etpspay;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.efulltech.epay_tps_library_module.UsbPrinter;
import com.efulltech.epay_tps_library_module.Utility;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.makeToast(MainActivity.this, "Printer Class Initiated", Utility.LENGTH_SHORT);
                UsbPrinter usbPrinter = new UsbPrinter(MainActivity.this);
                usbPrinter.PrintDemoText();

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

}
