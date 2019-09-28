package com.efulltech.etpspay.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import com.efulltech.etpspay.R;

public class DatabaseMgtActivity extends AppCompatActivity {

    private TranslateAnimation moveSideways;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_mgt);
        animateElement();
    }

    private void animateElement() {
        // animate the user management header
        moveSideways = new TranslateAnimation(-800, 0, 0, 0);
        moveSideways.setDuration(800);
        moveSideways.setFillAfter(true);
        findViewById(R.id.databaseMgtCardViewHeader).startAnimation(moveSideways);
    }
    public void clearDatabase(View view) {
        Toast.makeText(this, "Database cleared", Toast.LENGTH_SHORT).show();
    }

    public void clearBatch(View view) {
        Toast.makeText(this, "Database Batched", Toast.LENGTH_SHORT).show();
    }

    public void cloudDatabase(View view) {
        Toast.makeText(this, "Fetch cloud Database", Toast.LENGTH_SHORT).show();
    }
}
