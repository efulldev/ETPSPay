//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
package com.efulltech.epay_tps_library_module;

import android.annotation.SuppressLint;
import android.content.Context;

import com.telpo.tps550.api.InternalErrorException;
import com.telpo.tps550.api.TelpoException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LedClass {
    private Context mContext = null;

    public LedClass(Context context) {
        this.mContext = context;
    }

    @SuppressLint("WrongConstant")
    public synchronized void on(int num) throws TelpoException {
        Class<?> led = null;
        Method method = null;
        Object obj = null;

        try {
            led = Class.forName("com.common.sdk.led.LEDServiceManager");
        } catch (ClassNotFoundException var10) {
            var10.printStackTrace();
            throw new InternalErrorException();
        }

        obj = this.mContext.getSystemService("LED");

        try {
            method = led.getMethod("on", Integer.TYPE);
        } catch (NoSuchMethodException var9) {
            var9.printStackTrace();
            throw new InternalErrorException();
        }

        try {
            method.invoke(obj, num);
        } catch (IllegalArgumentException var6) {
            var6.printStackTrace();
            throw new InternalErrorException();
        } catch (IllegalAccessException var7) {
            var7.printStackTrace();
            throw new InternalErrorException();
        } catch (InvocationTargetException var8) {
            throw new TelpoException();
        }
    }

    public synchronized void off(int num) throws TelpoException {
        Class<?> led = null;
        Method method = null;
        Object obj = null;

        try {
            led = Class.forName("com.common.sdk.led.LEDServiceManager");
        } catch (ClassNotFoundException var10) {
            var10.printStackTrace();
            throw new InternalErrorException();
        }

        obj = this.mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        try {
            method = led.getMethod("off", Integer.TYPE);
        } catch (NoSuchMethodException var9) {
            var9.printStackTrace();
            throw new InternalErrorException();
        }

        try {
            method.invoke(obj, num);
        } catch (IllegalArgumentException var6) {
            var6.printStackTrace();
            throw new InternalErrorException();
        } catch (IllegalAccessException var7) {
            var7.printStackTrace();
            throw new InternalErrorException();
        } catch (InvocationTargetException var8) {
            throw new TelpoException();
        }
    }

    public synchronized void blink(int num, int period) throws TelpoException {
        Class<?> led = null;
        Method method = null;
        Object obj = null;

        try {
            led = Class.forName("com.common.sdk.led.LEDServiceManager");
        } catch (ClassNotFoundException var11) {
            var11.printStackTrace();
            throw new InternalErrorException();
        }

        obj = this.mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        try {
            method = led.getMethod("blink", Integer.TYPE, Integer.TYPE);
        } catch (NoSuchMethodException var10) {
            var10.printStackTrace();
            throw new InternalErrorException();
        }

        try {
            method.invoke(obj, num, period);
        } catch (IllegalArgumentException var7) {
            var7.printStackTrace();
            throw new InternalErrorException();
        } catch (IllegalAccessException var8) {
            var8.printStackTrace();
            throw new InternalErrorException();
        } catch (InvocationTargetException var9) {
            throw new TelpoException();
        }
    }
}
