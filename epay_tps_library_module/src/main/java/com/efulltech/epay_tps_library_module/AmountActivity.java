package com.efulltech.epay_tps_library_module;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.telpo.tps550.api.util.StringUtil;

public class AmountActivity extends AppCompatActivity {

    TextView test;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);

//        String accType = getIntent().getStringExtra("AccountType");

//        Log.d("Account type", accType);

        test = findViewById(R.id.test);

        preferences = getSharedPreferences("AccCat", MODE_PRIVATE);


        test.setText(preferences.getString("accType", "No type Given"));
    }
}
