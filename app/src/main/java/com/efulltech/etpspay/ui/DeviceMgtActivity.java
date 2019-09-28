package com.efulltech.etpspay.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import com.efulltech.etpspay.R;

public class DeviceMgtActivity extends AppCompatActivity {

    private TranslateAnimation moveSideways;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_mgt);
        animateElements();
    }

    private void animateElements() {
        // animate the user management header
        moveSideways = new TranslateAnimation(-800, 0, 0, 0);
        moveSideways.setDuration(800);
        moveSideways.setFillAfter(true);
        findViewById(R.id.deviceMgtCardViewHeader).startAnimation(moveSideways);
//        moveUpwards = new TranslateAnimation(0, 0, 500, 0);
//        moveUpwards.setDuration(1000);
//        moveUpwards.setFillAfter(true);
//        findViewById(R.id.addUserFab).startAnimation(moveUpwards);
    }


    public void openHostConfig(View view) {
        Toast.makeText(this, "Host config clicked", Toast.LENGTH_SHORT).show();
    }

    public void openKeyConfig(View view) {
        Toast.makeText(this, "Key Config clicked", Toast.LENGTH_SHORT).show();
    }

    public void openTerminalId(View view) {
        Toast.makeText(this, "Terminal Id clicked", Toast.LENGTH_SHORT).show();
    }

    public void openEpmsConfig(View view) {
        Toast.makeText(this, "EPMS config clicked", Toast.LENGTH_SHORT).show();
    }

    public void openPrepTerminal(View view) {
        Toast.makeText(this, "Prep Terminal clicked", Toast.LENGTH_SHORT).show();
    }

    public void openDownloadParam(View view) {
        Toast.makeText(this, "Download Params clicked", Toast.LENGTH_SHORT).show();
    }

    public void openDownloadKey(View view) {
        Toast.makeText(this, "Download Key clicked", Toast.LENGTH_SHORT).show();
    }

    public void openPrintConfig(View view) {
        Toast.makeText(this, "Print Configuration clicked", Toast.LENGTH_SHORT).show();
    }

    public void openBusinessName(View view) {
        Toast.makeText(this, "Business Name clicked", Toast.LENGTH_SHORT).show();
    }

    public void openBusinessEmail(View view) {
        Toast.makeText(this, "Business email clicked", Toast.LENGTH_SHORT).show();
    }
}
