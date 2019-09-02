package com.efulltech.epay_tps_library_module;

import android.content.Context;
import android.util.Log;

import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.reader.CardReader;
import com.telpo.tps550.api.reader.ICCardReader;
import com.telpo.tps550.api.reader.SmartCardReader;
import com.telpo.tps550.api.util.StringUtil;
import com.telpo.tps550.api.util.SystemUtil;

import java.util.Arrays;

public class SmartCardReaderx extends CardReader {
    private static final String TAG = "SmartCardReaderx";
    public static final int PROTOCOL_T0 = 0;
    public static final int PROTOCOL_T1 = 1;
    public static final int PROTOCOL_NA = 2;
    public static final int SLOT_ICC = 0;
    public static final int SLOT_PSAM1 = 1;
    public static final int SLOT_PSAM2 = 2;
    public static final int SLOT_PSAM3 = 3;

    public SmartCardReaderx() {
        this.cardType = 1;
        this.mSlot = 0;
    }

    public SmartCardReaderx(Context context) {
        this.context = context;
        this.cardType = 1;
        this.mSlot = 0;
        this.mICCardReader = new ICCardReader(context);
    }

    public SmartCardReaderx(Context context, int slot) {
        this.context = context;
        this.cardType = 1;
        this.mSlot = slot;
        this.mICCardReader = new ICCardReader(context);
    }

    public byte[] transmit(byte[] apdu) throws NullPointerException {
        if (apdu == null) {
            throw new NullPointerException();
        } else if (SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS390P.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900MB.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS450CIC.ordinal()) {
            byte[] pRecvRes = new byte[512];
            if (this.reader_type != 2 && this.reader_type != 1 && this.reader_type != 0) {
                int[] pRevAPDULen = new int[1];
                int result = this.reader.transmit(apdu, apdu.length, pRecvRes, pRevAPDULen);
                if (result == 0) {
                    return Arrays.copyOf(pRecvRes, pRevAPDULen[0]);
                }

                Log.e("SmartCardReaderx", "send APDU failed: " + result);
            } else {
                int len = send_apdu(apdu, pRecvRes);
                if (len > 0) {
                    return Arrays.copyOf(pRecvRes, len);
                }
            }

            return null;
        } else {
            try {
                return this.mICCardReader.transmit(this.mSlot, apdu, apdu.length);
            } catch (TelpoException var5) {
                var5.printStackTrace();
                return null;
            }
        }
    }

    public byte[] transmit(byte[] apdu, int ic360) throws NullPointerException {
        if (apdu == null) {
            throw new NullPointerException();
        } else if (SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS390P.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900MB.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS360IC.ordinal()) {
            byte[] pRecvRes = new byte[512];
            if (this.reader_type != 2 && this.reader_type != 1 && this.reader_type != 0) {
                int[] pRevAPDULen = new int[1];
                int result = this.reader.transmit(apdu, apdu.length, pRecvRes, pRevAPDULen);
                if (result == 0) {
                    return Arrays.copyOf(pRecvRes, pRevAPDULen[0]);
                }

                Log.e("SmartCardReaderx", "send APDU failed: " + result);
            } else {
                int len = send_apdu(apdu, pRecvRes);
                if (len > 0) {
                    return Arrays.copyOf(pRecvRes, len);
                }
            }

            return null;
        } else {
            try {
                return this.mICCardReader.transmit(this.mSlot, apdu, apdu.length);
            } catch (TelpoException var6) {
                var6.printStackTrace();
                return null;
            }
        }
    }

    public int getProtocol() {
        if (SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS390P.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900MB.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS450CIC.ordinal()) {
            int t;
            if (this.reader_type != 2 && this.reader_type != 1 && this.reader_type != 0) {
                byte[] proto = new byte[1];
                t = this.reader.getProtocol(proto);
                if (t == 0) {
                    if (proto[0] == 1) {
                        return 0;
                    }

                    if (proto[0] == 2) {
                        return 1;
                    }
                } else {
                    Log.e("SmartCardReaderx", "get protocol failed: " + t);
                }
            } else if (this.mATR != null) {
                if ((this.mATR[1] >> 7 & 1) != 1) {
                    return 0;
                }

                int c = 0;

                for(t = 4; t < 7; ++t) {
                    if ((this.mATR[1] >> t & 1) != 0) {
                        ++c;
                    }
                }

                t = this.mATR[2 + c] & 15;
                if (t == 0) {
                    return 0;
                }

                if (t == 1) {
                    return 1;
                }
            }

            return 2;
        } else {
            return 0;
        }
    }

    public int getProtocol(int ic360) {
        if (SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS390P.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS900MB.ordinal() && SystemUtil.getDeviceType() != StringUtil.DeviceModelEnum.TPS360IC.ordinal()) {
            int t;
            if (this.reader_type != 2 && this.reader_type != 1 && this.reader_type != 0) {
                byte[] proto = new byte[1];
                t = this.reader.getProtocol(proto);
                if (t == 0) {
                    if (proto[0] == 1) {
                        return 0;
                    }

                    if (proto[0] == 2) {
                        return 1;
                    }
                } else {
                    Log.e("SmartCardReaderx", "get protocol failed: " + t);
                }
            } else if (this.mATR != null) {
                if ((this.mATR[1] >> 7 & 1) != 1) {
                    return 0;
                }

                int c = 0;

                for(t = 4; t < 7; ++t) {
                    if ((this.mATR[1] >> t & 1) != 0) {
                        ++c;
                    }
                }

                t = this.mATR[2 + c] & 15;
                if (t == 0) {
                    return 0;
                }

                if (t == 1) {
                    return 1;
                }
            }

            return 2;
        } else {
            return 0;
        }
    }

    public int set_mode(int slot, int mode) {
        int result = -1;
        if (this.mICCardReader != null) {
            try {
                result = this.mICCardReader.set_mode(slot, mode);
            } catch (TelpoException var5) {
                var5.printStackTrace();
            }
        }

        return result;
    }
}

