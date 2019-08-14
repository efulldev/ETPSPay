package com.efulltech.epay_tps_library_module;


import android.content.Context;
import android.widget.Toast;

public class Utility {
     public static int LENGTH_SHORT = Toast.LENGTH_SHORT;
     public static int LENGTH_LONG = Toast.LENGTH_LONG;

    public Utility(){}


    public static void makeToast(Context ctx, String message, int length) {
        Toast.makeText(ctx, message, length).show();
    }
}
