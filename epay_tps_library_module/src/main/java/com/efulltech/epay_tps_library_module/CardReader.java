package com.efulltech.epay_tps_library_module;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.collect.Collect;
import com.telpo.tps550.api.reader.ICCardReader;
import com.telpo.tps550.api.usb.UsbUtil;
import com.telpo.tps550.api.util.ShellUtils;
import com.telpo.tps550.api.util.StringUtil;
import com.telpo.tps550.api.util.SystemUtil;

import java.io.FileOutputStream;
import java.util.Iterator;

import amlib.ccid.Reader;
import amlib.ccid.Reader4428;
import amlib.ccid.Reader4442;
import amlib.hw.HWType;
import amlib.hw.HardwareInterface;
import amlib.hw.ReaderHwException;

public class CardReader {
    private static final String TAG = "TELPO_SDK";
    public static final int CARD_TYPE_UNKNOWN = -1;
    public static final int CARD_TYPE_MSC = 0;
    public static final int CARD_TYPE_ISO7816 = 1;
    public static final int CARD_TYPE_SLE4442 = 2;
    public static final int CARD_TYPE_SLE4428 = 3;
    public static final int CARD_TYPE_AT88SC153 = 4;
    public static final int SLOT_STATUS_ICC_ACTIVE = 0;
    public static final int SLOT_STATUS_ICC_INACTIVE = 1;
    public static final int SLOT_STATUS_ICC_ABSENT = 2;
    public static final int CARDREADER_HUB = 1;
    protected Context context;
    protected ICCardReader mICCardReader;
    protected int cardType = 1;
    protected boolean correct_psc_verification = false;
    protected UsbDevice usbDev;
    protected HardwareInterface myDev;
    protected UsbManager usbManager;
    protected int mSlot = 0;
    protected Reader reader = null;
    protected byte[] mATR = null;
    protected int reader_type = -1;
    Reader4442 m4442 = null;
    private PendingIntent permissionIntent;
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private HandlerThread handlerThread;
    private Handler handler;
    private Object lock = new Object();
    String[] portNum = new String[20];
    String[] productNum = new String[20];
    String[] readerNum = new String[4];
    private final BroadcastReceiver mReceiver;

    static {
        if (SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS390P.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900MB.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS450CIC.ordinal()) {
            System.loadLibrary("card_reader");
        }

    }

    protected static native int open_device(int var0, int var1);

    protected static native int close_device();

    protected static native byte[] icc_power_on(int var0);

    protected static native int icc_power_off();

    protected static native int get_card_status();

    protected static native int switch_mode(int var0);

    protected static native byte[] read_main_mem(int var0, int var1, int var2);

    protected static native int psc_verify(int var0, byte[] var1);

    protected static native int update_main_mem(int var0, int var1, byte[] var2);

    protected static native int psc_modify(int var0, byte[] var1);

    protected static native int send_apdu(byte[] var0, byte[] var1);

    protected static native int telpo_switch_psam(int var0);

    private native int device_power(int var1);

    public CardReader() {
        this.mReceiver = new NamelessClass_1();
    }

    public CardReader(Context context) {
        class NamelessClass_1 extends BroadcastReceiver {
            NamelessClass_1() {
            }

            public void onReceive(Context context, Intent intent) {
                Log.d("TELPO_SDK", "Broadcast Receiver");
                String action = intent.getAction();
                if ("com.android.example.USB_PERMISSION".equals(action)) {
                    synchronized(com.efulltech.epay_tps_library_module.CardReader.this.lock) {
                        UsbDevice devicex = (UsbDevice)intent.getParcelableExtra("device");
                        if (intent.getBooleanExtra("permission", false)) {
                            if (devicex != null && devicex.equals(com.efulltech.epay_tps_library_module.CardReader.this.usbDev)) {
                                try {
                                    if (com.efulltech.epay_tps_library_module.CardReader.this.myDev.Init(com.efulltech.epay_tps_library_module.CardReader.this.usbManager, com.efulltech.epay_tps_library_module.CardReader.this.usbDev)) {
                                        try {
                                            if (com.efulltech.epay_tps_library_module.CardReader.this.cardType == 2) {
                                                com.efulltech.epay_tps_library_module.CardReader.this.reader = new Reader4442(com.efulltech.epay_tps_library_module.CardReader.this.myDev);
                                                Log.d("TAG", "reader = new Reader4442(myDev);\ndevice name:" + com.efulltech.epay_tps_library_module.CardReader.this.usbDev.getDeviceName());
                                            } else if (com.efulltech.epay_tps_library_module.CardReader.this.cardType == 3) {
                                                com.efulltech.epay_tps_library_module.CardReader.this.reader = new Reader4428(com.efulltech.epay_tps_library_module.CardReader.this.myDev);
                                            } else {
                                                com.efulltech.epay_tps_library_module.CardReader.this.reader = new Reader(com.efulltech.epay_tps_library_module.CardReader.this.myDev);
                                                com.efulltech.epay_tps_library_module.CardReader.this.reader.switchMode((byte)1);
                                            }

                                            Log.d("TAG", "reader.open:" + com.efulltech.epay_tps_library_module.CardReader.this.reader.open());
                                            com.efulltech.epay_tps_library_module.CardReader.this.reader.setSlot((byte)0);
                                        } catch (Exception var7) {
                                            Log.e("TELPO_SDK", "Get Exception : " + var7.getMessage());
                                            var7.printStackTrace();
                                        }
                                    }
                                } catch (ReaderHwException var8) {
                                    var8.printStackTrace();
                                }

                                com.efulltech.epay_tps_library_module.CardReader.this.lock.notify();
                            }
                        } else {
                            Log.d("TELPO_SDK", "Permission denied for device " + devicex.getDeviceName());
                            com.efulltech.epay_tps_library_module.CardReader.this.lock.notify();
                        }
                    }
                } else if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action)) {
                    Log.d("TELPO_SDK", "Device Detached");
                    UsbDevice device = (UsbDevice)intent.getParcelableExtra("device");
                    if (device != null && device.equals(com.efulltech.epay_tps_library_module.CardReader.this.usbDev)) {
                        if (com.efulltech.epay_tps_library_module.CardReader.this.reader != null) {
                            com.efulltech.epay_tps_library_module.CardReader.this.reader.close();
                        }

                        if (com.efulltech.epay_tps_library_module.CardReader.this.myDev != null) {
                            com.efulltech.epay_tps_library_module.CardReader.this.myDev.Close();
                        }
                    }
                }

            }
        }

        this.mReceiver = new NamelessClass_1();
        this.context = context;
    }

    public boolean open() {
        if (SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS390P.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900MB.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS450CIC.ordinal()) {
            int type = SystemUtil.getICCReaderType();
            Log.d("TAG", "CardReader type is:" + type);
            Log.d("TAG", "mSlot is:" + this.mSlot);
            if (this.mSlot == 0 && (type == 2 || type == 1 || type == 0) || this.mSlot == 1 && (SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS350_4G.ordinal() || SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS350L.ordinal() || SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS573.ordinal() || SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS450.ordinal() || SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS450C.ordinal() || SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS365.ordinal() || SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS360C.ordinal() || SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS360IC.ordinal() || SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS360A.ordinal() || SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS470.ordinal()) || this.mSlot == 2 && SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS573.ordinal()) {
                Log.e("TAG", "AU9560_GCS-------");
                if (open_device(this.cardType, this.mSlot) < 0) {
                    Log.d("TAG", "open_device(cardType, mSlot) < 0");
                    return false;
                }
            } else {
                if (SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS390U.ordinal()) {
                    this.device_power(1);
                }

                type = 3;
                this.usbManager = (UsbManager)this.context.getSystemService(Context.USB_SERVICE);
                this.usbDev = this.getUsbDevice();
                if (this.usbDev == null) {
                    Log.e("TELPO_SDK", "get usb manager failed");
                    return false;
                }

                this.reader = null;
                this.myDev = new HardwareInterface(HWType.eUSB, this.context);
                this.handlerThread = new HandlerThread("card reader");
                this.handlerThread.start();
                this.handler = new Handler(this.handlerThread.getLooper());
                this.toRegisterReceiver();
                this.usbManager.requestPermission(this.usbDev, this.permissionIntent);
                synchronized(this.lock) {
                    if (this.reader == null) {
                        try {
                            this.lock.wait(30000L);
                        } catch (InterruptedException var4) {
                            var4.printStackTrace();
                        }
                    }
                }

                this.context.unregisterReceiver(this.mReceiver);
                this.handlerThread.quit();
                this.handlerThread = null;
                if (this.reader == null) {
                    Log.e("TELPO_SDK", "reader is null");
                    this.myDev.Close();
                    this.myDev = null;
                    this.usbManager = null;
                    this.usbDev = null;
                    return false;
                }
            }

            this.reader_type = type;
            return true;
        } else {
            try {
                this.mICCardReader.open(this.mSlot);
                return true;
            } catch (TelpoException var5) {
                var5.printStackTrace();
                return false;
            }
        }
    }
//
//    public boolean open(int isIC) {
//        if (SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS390P.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900MB.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS360IC.ordinal()) {
//            int type = true;
//            int type;
//            if (isIC == 0) {
//                type = SystemUtil.getICCReaderType();
//            } else {
//                type = 3;
//            }
//
//            Log.d("TAG", "CardReader type is:" + type);
//            if ((this.mSlot != 0 || type != 2 && type != 1 && type != 0) && (this.mSlot != 1 || SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS350_4G.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS350L.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS450.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS365.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS360C.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS360A.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS470.ordinal())) {
//                if (SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS390U.ordinal()) {
//                    this.device_power(1);
//                }
//
//                type = 3;
//                this.usbManager = (UsbManager)this.context.getSystemService(context.USB_SERVICE);
//                this.usbDev = this.getUsbDevice();
//                if (this.usbDev == null) {
//                    Log.e("TELPO_SDK", "get usb manager failed");
//                    return false;
//                }
//
//                this.reader = null;
//                this.myDev = new HardwareInterface(HWType.eUSB, this.context);
//                this.handlerThread = new HandlerThread("card reader");
//                this.handlerThread.start();
//                this.handler = new Handler(this.handlerThread.getLooper());
//                this.toRegisterReceiver();
//                this.usbManager.requestPermission(this.usbDev, this.permissionIntent);
//                synchronized(this.lock) {
//                    if (this.reader == null) {
//                        try {
//                            this.lock.wait(30000L);
//                        } catch (InterruptedException var5) {
//                            var5.printStackTrace();
//                        }
//                    }
//                }
//
//                this.context.unregisterReceiver(this.mReceiver);
//                this.handlerThread.quit();
//                this.handlerThread = null;
//                if (this.reader == null) {
//                    Log.e("TELPO_SDK", "reader is null");
//                    this.myDev.Close();
//                    this.myDev = null;
//                    this.usbManager = null;
//                    this.usbDev = null;
//                    return false;
//                }
//            } else {
//                Log.e("TAG", "AU9560_GCS-------");
//                if (open_device(this.cardType, this.mSlot) < 0) {
//                    Log.d("TAG", "open_device(cardType, mSlot) < 0");
//                    return false;
//                }
//            }
//
//            this.reader_type = type;
//            return true;
//        } else {
//            try {
//                this.mICCardReader.open(this.mSlot);
//                return true;
//            } catch (TelpoException var6) {
//                var6.printStackTrace();
//                return false;
//            }
//        }
//    }

    public UsbDevice getAT88SC153Device() {
        if (this.usbManager == null) {
            this.usbManager = (UsbManager)this.context.getSystemService(context.USB_SERVICE);
        }

        UsbDevice mUsbDev = this.getUsbDevice();
        Log.d("TAG", "getAT88SC153Device:pid:" + mUsbDev.getProductId() + "device name:" + mUsbDev.getDeviceName());
        return mUsbDev;
    }

    public boolean close() {
        if (SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS390P.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900MB.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS450CIC.ordinal()) {
            if (this.reader_type != 2 && this.reader_type != 1 && this.reader_type != 0) {
                int status;
                if (this.reader != null) {
                    do {
                        status = this.reader.close();
                    } while(status == 3);
                }

                if (this.myDev != null) {
                    this.myDev.Close();
                }

                if (SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS390U.ordinal()) {
                    this.device_power(0);
                }
            } else if (close_device() < 0) {
                return false;
            }

            this.reader_type = -1;
            return true;
        } else {
            try {
                this.mICCardReader.close(this.mSlot);
                return true;
            } catch (TelpoException var2) {
                var2.printStackTrace();
                return false;
            }
        }
    }

    public boolean close(int ic360) {
        if (SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS390P.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900MB.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS360IC.ordinal()) {
            if (this.reader_type != 2 && this.reader_type != 1 && this.reader_type != 0) {
                int status;
                if (this.reader != null) {
                    do {
                        status = this.reader.close();
                    } while(status == 3);
                }

                if (this.myDev != null) {
                    this.myDev.Close();
                }

                if (SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS390U.ordinal()) {
                    this.device_power(0);
                }
            } else if (close_device() < 0) {
                return false;
            }

            this.reader_type = -1;
            return true;
        } else {
            try {
                this.mICCardReader.close(this.mSlot);
                return true;
            } catch (TelpoException var3) {
                var3.printStackTrace();
                return false;
            }
        }
    }

    public boolean iccPowerOn() {
        if (SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS390P.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900MB.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS450CIC.ordinal()) {
            if (this.reader_type != 2 && this.reader_type != 1 && this.reader_type != 0) {
                Log.d("TAG", "reader_type:" + this.reader_type);
                byte[] status = new byte[1];
                byte[] pCardStatus = new byte[1];
                if (this.reader == null || this.reader.getCardStatus(status) != 0) {
                    return false;
                }

                if (status[0] == 2) {
                    return false;
                }

                int result = this.reader.setPower(1);
                if (result != 0) {
                    Log.e("TELPO_SDK", "setPower failed: " + result);
                    return false;
                }
            } else {
                Log.d("TAG", "reader_type:" + this.reader_type);
                this.mATR = icc_power_on(this.cardType);
                if (this.mATR == null) {
                    return false;
                }
            }

            if (this.mSlot == 0) {
                Collect.collectInfo(2, 1, (byte[])null);
            } else {
                Collect.collectInfo(3, 1, (byte[])null);
            }

            return true;
        } else {
            try {
                if (this.mICCardReader.detect(this.mSlot, 500) == 0) {
                    this.mICCardReader.power_on(this.mSlot);
                    return true;
                } else {
                    return false;
                }
            } catch (TelpoException var4) {
                var4.printStackTrace();
                return false;
            }
        }
    }

    public boolean iccPowerOn(int ic360) {
        if (SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS390P.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900MB.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS360IC.ordinal()) {
            if (this.reader_type != 2 && this.reader_type != 1 && this.reader_type != 0) {
                Log.d("TAG", "reader_type:" + this.reader_type);
                byte[] status = new byte[1];
                byte[] pCardStatus = new byte[1];
                if (this.reader == null || this.reader.getCardStatus(status) != 0) {
                    return false;
                }

                if (status[0] == 2) {
                    return false;
                }

                int result = this.reader.setPower(1);
                if (result != 0) {
                    Log.e("TELPO_SDK", "setPower failed: " + result);
                    return false;
                }
            } else {
                Log.d("TAG", "reader_type:" + this.reader_type);
                this.mATR = icc_power_on(this.cardType);
                if (this.mATR == null) {
                    return false;
                }
            }

            if (this.mSlot == 0) {
                Collect.collectInfo(2, 1, (byte[])null);
            } else {
                Collect.collectInfo(3, 1, (byte[])null);
            }

            return true;
        } else {
            try {
                if (this.mICCardReader.detect(this.mSlot, 500) == 0) {
                    this.mICCardReader.power_on(this.mSlot);
                    return true;
                } else {
                    return false;
                }
            } catch (TelpoException var5) {
                var5.printStackTrace();
                return false;
            }
        }
    }

    public boolean iccPowerOff() {
        if (SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS390P.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900MB.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS450CIC.ordinal()) {
            if (this.reader_type != 2 && this.reader_type != 1 && this.reader_type != 0) {
                byte[] status = new byte[1];
                if (this.reader == null || this.reader.getCardStatus(status) != 0) {
                    return false;
                }

                if (status[0] == 2) {
                    return false;
                }

                if (this.reader.setPower(-1) != 0) {
                    return false;
                }
            } else if (icc_power_off() < 0) {
                return false;
            }

            this.mATR = null;
            this.correct_psc_verification = false;
            return true;
        } else {
            try {
                this.mICCardReader.power_off(this.mSlot);
                return true;
            } catch (TelpoException var2) {
                var2.printStackTrace();
                return false;
            }
        }
    }

    public boolean iccPowerOff(int ic360) {
        if (SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS390P.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900MB.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS360IC.ordinal()) {
            if (this.reader_type != 2 && this.reader_type != 1 && this.reader_type != 0) {
                byte[] status = new byte[1];
                if (this.reader == null || this.reader.getCardStatus(status) != 0) {
                    return false;
                }

                if (status[0] == 2) {
                    return false;
                }

                if (this.reader.setPower(-1) != 0) {
                    return false;
                }
            } else if (icc_power_off() < 0) {
                return false;
            }

            this.mATR = null;
            this.correct_psc_verification = false;
            return true;
        } else {
            try {
                this.mICCardReader.power_off(this.mSlot);
                return true;
            } catch (TelpoException var3) {
                var3.printStackTrace();
                return false;
            }
        }
    }
//
//    public int getICCStatus() {
//        boolean result = false;
//        int iccStatus = 2;
//        if (this.reader_type != 2 && this.reader_type != 1 && this.reader_type != 0) {
//            byte[] status = new byte[1];
//            result = this.reader.getCardStatus(status);
//            if (result == 0) {
//                if (status[0] == 0) {
//                    iccStatus = 0;
//                } else if (status[0] == 1) {
//                    iccStatus = 1;
//                }
//            }
//        } else {
//            iccStatus = get_card_status();
//            if (iccStatus < 0) {
//                iccStatus = 2;
//            }
//        }
//
//        return iccStatus;
//    }

    public boolean isICCPresent() {
        if (SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS390P.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900MB.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS450CIC.ordinal()) {
            if (this.reader_type != 2 && this.reader_type != 1 && this.reader_type != 0) {
                byte[] status = new byte[1];
                if (this.reader.getCardStatus(status) == 0 && status[0] != 2) {
                    return true;
                }
            } else {
                int status = get_card_status();
                if (status == 0 || status == 1) {
                    return true;
                }
            }

            return false;
        } else {
            try {
                return this.mICCardReader.detect(this.mSlot, 500) == 0;
            } catch (TelpoException var2) {
                var2.printStackTrace();
                return false;
            }
        }
    }

    public boolean isICCPresent(int ic360) {
        if (SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS390P.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900MB.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS360IC.ordinal()) {
            if (this.reader_type != 2 && this.reader_type != 1 && this.reader_type != 0) {
                byte[] status = new byte[1];
                if (this.reader.getCardStatus(status) == 0 && status[0] != 2) {
                    return true;
                }
            } else {
                int status = get_card_status();
                if (status == 0 || status == 1) {
                    return true;
                }
            }

            return false;
        } else {
            try {
                return this.mICCardReader.detect(this.mSlot, 500) == 0;
            } catch (TelpoException var3) {
                var3.printStackTrace();
                return false;
            }
        }
    }

    public String getATRString() {
        if (SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS390P.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900MB.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS450CIC.ordinal()) {
            if (this.reader_type != 2 && this.reader_type != 1 && this.reader_type != 0) {
                return this.reader.getAtrString();
            } else {
                return this.mATR == null ? null : StringUtil.toHexString(this.mATR);
            }
        } else {
            try {
                return this.mICCardReader.getAtr(this.mSlot);
            } catch (TelpoException var2) {
                var2.printStackTrace();
                return null;
            }
        }
    }

    public String getATRString(int isic360) {
        if (SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS390P.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900MB.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS360IC.ordinal()) {
            if (this.reader_type != 2 && this.reader_type != 1 && this.reader_type != 0) {
                return this.reader.getAtrString();
            } else {
                return this.mATR == null ? null : StringUtil.toHexString(this.mATR);
            }
        } else {
            try {
                return this.mICCardReader.getAtr(this.mSlot);
            } catch (TelpoException var3) {
                var3.printStackTrace();
                return null;
            }
        }
    }

    public int getCardType() {
        String atr = this.getATRString();
        if (atr == null) {
            return -1;
        } else {
            String atrString = atr.replace(" ", "");
            Log.i("TELPO_SDK", "ATR: " + atrString);
            if (atrString.contains("A2131091")) {
                return 2;
            } else {
                return atrString.contains("92231091") ? 3 : -1;
            }
        }
    }

    public boolean switchMode(int cardType) {
        if (this.reader_type != 2 && this.reader_type != 1 && this.reader_type != 0) {
            if (cardType == 1) {
                if (this.reader.switchMode((byte)1) == 0) {
                    return true;
                }
            } else if (cardType == 2) {
                if (this.reader.switchMode((byte)4) == 0) {
                    return true;
                }
            } else if (cardType == 3 && this.reader.switchMode((byte)3) == 0) {
                return true;
            }
        } else if (switch_mode(cardType) == 0) {
            return true;
        }

        return false;
    }

    private UsbDevice getUsbDevice() {
        String deviceName = null;
        if (SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS510A_NHW.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS510D.ordinal()) {
            deviceName = UsbUtil.getUsbDevice(this.mSlot);
        } else {
            this.searchAllIndex(ShellUtils.execCommand("cat /sys/kernel/debug/usb/devices", false).successMsg, "Port=", 1);
            this.searchAllIndex(ShellUtils.execCommand("cat /sys/kernel/debug/usb/devices", false).successMsg, "Product=", 2);
            this.checkPort(this.portNum, this.productNum);
            if ((this.readerNum[0] == null || !this.readerNum[0].equals("05")) && (this.readerNum[1] == null || !this.readerNum[1].equals("05")) && (this.readerNum[2] == null || !this.readerNum[2].equals("05")) && (this.readerNum[3] == null || !this.readerNum[3].equals("05"))) {
                Log.d("TAG", "new usb_utils");
                deviceName = UsbUtil.getUsbDevicehub(this.mSlot, 1);
            } else {
                Log.d("TAG", "old usb_utils");
                deviceName = UsbUtil.getUsbDevice(this.mSlot);
            }
        }

        if (deviceName != null) {
            Iterator var3 = this.usbManager.getDeviceList().values().iterator();

            while(var3.hasNext()) {
                UsbDevice device = (UsbDevice)var3.next();
                if (deviceName.equals(device.getDeviceName())) {
                    this.switchPsam();
                    return device;
                }
            }
        }

        return null;
    }

    private void searchAllIndex(String str, String key, int type) {
        int a = str.indexOf(key);

        for(int i = -1; a != -1; a = str.indexOf(key, a + 1)) {
            ++i;
            if (type == 1) {
                this.portNum[i] = str.substring(a + 5, a + 7);
            } else if (type == 2) {
                this.productNum[i] = str.substring(a + 8, a + 11);
            }
        }

    }

    private void checkPort(String[] port, String[] product) {
        int k = -1;

        for(int i = 0; i < 20; ++i) {
            if (this.productNum[i] != null && this.productNum[i].equals("EMV")) {
                ++k;
                this.readerNum[k] = this.portNum[i];
                Log.d("TAG", "readnum[]:" + this.readerNum[k]);
            }
        }

    }

    private void toRegisterReceiver() {
        this.permissionIntent = PendingIntent.getBroadcast(this.context, 0, new Intent("com.android.example.USB_PERMISSION"), 0);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.example.USB_PERMISSION");
        filter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        this.context.registerReceiver(this.mReceiver, filter, (String)null, this.handler);
    }

    private void readerPoweron() {
        String[] paths = new String[]{"/sys/devices/platform/battery/GPIO30_PIN", "/sys/devices/platform/battery/GPIO31_PIN", "/sys/devices/platform/battery/GPIO142_PIN", "/sys/devices/platform/battery/GPIO145_PIN"};

        try {
            for(int i = 0; i < paths.length; ++i) {
                FileOutputStream fon = new FileOutputStream(paths[i]);
                fon.write(49);
                fon.flush();
                fon.close();
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        try {
            Thread.sleep(100L);
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }

    }

    private void readerPoweroff() {
        String[] paths = new String[]{"/sys/devices/platform/battery/GPIO30_PIN", "/sys/devices/platform/battery/GPIO31_PIN", "/sys/devices/platform/battery/GPIO142_PIN", "/sys/devices/platform/battery/GPIO145_PIN"};

        try {
            for(int i = 0; i < paths.length; ++i) {
                FileOutputStream fon = new FileOutputStream(paths[i]);
                fon.write(48);
                fon.flush();
                fon.close();
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        try {
            Thread.sleep(100L);
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }

    }

    private int switchPsam() {
        int ret = 0;
        String path = "/sys/class/hdxio/psam_select";
        if (SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS510A.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS510A_NHW.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS510D.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS450C.ordinal()) {
            if (SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS613.ordinal()) {
                if (this.mSlot == 1) {
                    ret = telpo_switch_psam(1);
                } else if (this.mSlot == 2) {
                    ret = telpo_switch_psam(2);
                }
            } else if (SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS390U.ordinal()) {
                ret = 0;
            } else {
                try {
                    FileOutputStream fon = new FileOutputStream(path);
                    if (this.mSlot == 2) {
                        if (SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.FFP2.ordinal()) {
                            fon.write(50);
                        } else {
                            fon.write(49);
                        }
                    } else if (this.mSlot == 3) {
                        fon.write(51);
                    } else {
                        fon.write(49);
                    }

                    fon.flush();
                    fon.close();
                } catch (Exception var4) {
                    var4.printStackTrace();
                    Log.e("TELPO_SDK", "switch slot " + this.mSlot + " failed");
                    ret = -1;
                }
            }
        } else if (this.mSlot == 2) {
            ret = telpo_switch_psam(2);
        } else if (this.mSlot == 3) {
            ret = telpo_switch_psam(3);
        }

        return ret;
    }

    private class NamelessClass_1 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }



}
