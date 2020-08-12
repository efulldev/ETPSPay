package com.efulltech.efull_nibss_bridge.utils;

import android.util.Log;

public class Logs {

    private Boolean debug;

    public Logs(Boolean debug){
        this.debug = debug;
    }

    public void d(String TAG, String msg){
        if(!debug){
            Log.d(TAG, msg);
        }else{
            System.out.println(TAG+" => "+msg);
        }
    }
}
