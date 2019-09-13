package com.efulltech.epay_tps_library_module;

import android.app.Activity;
import android.app.Application;

import java.util.Timer;
import java.util.TimerTask;

public class TimeOutController extends Application {
    private LogoutListener logoutListener;
    private Timer timer;

    public void startUserSession() {
        cancelTimer();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                logoutListener.onSessionLogOut();
            }
        }, 15000);
    }

    public void cancelTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public void registerSessionListener(LogoutListener logoutListener) {
        this.logoutListener = logoutListener;
    }

    public void onUserInteracted() {
        startUserSession();
    }
}
