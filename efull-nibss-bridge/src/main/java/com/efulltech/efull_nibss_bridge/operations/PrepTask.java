package com.efulltech.efull_nibss_bridge.operations;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.entities.ISOMessage;
import com.efulltech.efull_nibss_bridge.operations.Controller;
import com.efulltech.efull_nibss_bridge.utils.Logs;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.SecretKey;

public class PrepTask extends AsyncTask<Void, Integer, Boolean> {
    private static final String TAG = "PrepTask";

    private String terminalid = "20390015";
    private String host = "196.6.103.72";
    private int port = 5042;
    private String protocol = "0";
    private String key1 = "5D25072F04832A2329D93E4F91BA23A2";
    private String key2 = "86CBCDE3B0A22354853E04521686863D";

    Logs logs = new Logs(true);

    public PrepTask() {

    }

    protected void onPreExecute() {
        logs.d("PrepTask", "master key download");
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            logs.d("PrepTask", "key1:" + key1);
            logs.d("PrepTask", "key2:" + key2);
            ISOMessage response = Controller.masterKeyDownload(protocol, "123456", terminalid, "123456789");
            logs.d("PrepTask", "response : " + response);
            if (response == null) {
                return false;
            } else {
                String f39 = response.getStringField(39);
                logs.d("PrepTask", "f39 : " + f39);
                return true;
//                if (f39 != null && f39.equals("00")) {
//                    String skey = jobject.getAsJsonPrimitive("53").getAsString();
//                    String f62 = skey.substring(0, 32);
//                    String kcv = skey.substring(32, 38);
//                    Log.d("PrepTask", "f53 : " + skey);
//                    Log.d("PrepTask", "emk : " + f62);
//                    Log.d("PrepTask", "kcv : " + kcv);
//                    byte[] keyB1 = Crypto.hexToByte(key1 + key1.substring(0, 16));
//                    byte[] keyB1 = Crypto.hexToByte(key2 + key2.substring(0, 16));
//                    byte[] keyB3 = new byte[keyB1.length];
//
//                    for(int i = 0; i < keyB1.length; ++i) {
//                        keyB3[i] = (byte)(keyB1[i] ^ keyB1[i]);
//                    }
//
//                    SecretKey key = Crypto.read3DESKey(keyB3);
//                    String dmk = Crypto.Decrypt3DES(key, f62);
//                    Log.d("PrepTask", "dmk : " + dmk);
//                    SharedPreferences.Editor editor = this.settings.edit();
//                    editor.putString("mkey", dmk);
//                    editor.commit();
//                    Log.d("PrepTask", "session key download");
//                    response = Initiate.sessionKeyDownload(host, port, protocol, "123456", terminalid, "123456789");
//                    Log.d("PrepTask", "response : " + response);
//                    if (response == null) {
//                        return false;
//                    } else {
//                        jsonElement = (new JsonParser()).parse(response);
//                        jobject = jsonElement.getAsJsonObject();
//                        f39 = jobject.getAsJsonPrimitive("39").getAsString();
//                        Log.d("PrepTask", "f39 : " + f39);
//                        if (f39 != null && f39.equals("00")) {
//                            skey = jobject.getAsJsonPrimitive("53").getAsString();
//                            f62 = skey.substring(0, 32);
//                            kcv = skey.substring(32, 38);
//                            Log.d("PrepTask", "f53 : " + skey);
//                            Log.d("PrepTask", "esk : " + f62);
//                            Log.d("PrepTask", "kcv : " + kcv);
//                            String len = this.settings.getString("mkey", "");
//                            keyB1 = Crypto.hexToByte(len + len.substring(0, 16));
//                            SecretKey key = Crypto.read3DESKey(keyB1);
//                            String dpk = Crypto.Decrypt3DES(key, f62);
//                            Log.d("PrepTask", "dsk : " + dpk);
//                            SharedPreferences.Editor editor = this.settings.edit();
//                            editor.putString("skey", dpk);
//                            editor.commit();
//                            Log.d("PrepTask", "pin key downlaod");
//                            response = Initiate.pinKeyDownload(host, port, protocol, "123456", terminalid, "123456789");
//                            Log.d("PrepTask", "response : " + response);
//                            if (response == null) {
//                                return false;
//                            } else {
//                                jsonElement = (new JsonParser()).parse(response);
//                                jobject = jsonElement.getAsJsonObject();
//                                f39 = jobject.getAsJsonPrimitive("39").getAsString();
//                                Log.d("PrepTask", "f39 : " + f39);
//                                if (f39 != null && f39.equals("00")) {
//                                    skey = jobject.getAsJsonPrimitive("53").getAsString();
//                                    f62 = skey.substring(0, 32);
//                                    kcv = skey.substring(32, 38);
//                                    Log.d("PrepTask", "f53 : " + skey);
//                                    Log.d("PrepTask", "epk : " + f62);
//                                    Log.d("PrepTask", "kcv : " + kcv);
//                                    len = this.settings.getString("mkey", "");
//                                    keyB1 = Crypto.hexToByte(len + len.substring(0, 16));
//                                    key = Crypto.read3DESKey(keyB1);
//                                    dpk = Crypto.Decrypt3DES(key, f62);
//                                    Log.d("PrepTask", "dsk : " + dpk);
//                                    editor = this.settings.edit();
//                                    editor.putString("pkey", dpk);
//                                    editor.putString("epkey", f62);
//                                    editor.putString("epkeykcv", kcv);
//                                    editor.commit();
//                                    Log.d("PrepTask", "parameter download");
//                                    skey = this.settings.getString("skey", "");
//                                    Log.d("PrepTask", "parameter download:skey:" + skey);
//                                    response = Initiate.parametersDownload(host, port, protocol, "123456", terminalid, "123456789", skey);
//                                    Log.d("PrepTask", "response : " + response);
//                                    if (response == null) {
//                                        return false;
//                                    } else {
//                                        jsonElement = (new JsonParser()).parse(response);
//                                        jobject = jsonElement.getAsJsonObject();
//                                        f39 = jobject.getAsJsonPrimitive("39").getAsString();
//                                        Log.d("PrepTask", "f39 : " + f39);
//                                        if (f39 != null && f39.equals("00")) {
//                                            f62 = jobject.getAsJsonPrimitive("62").getAsString();
//                                            Log.d("PrepTask", "f62 : " + f62);
//
//                                            while(!f62.isEmpty() && (f62.startsWith("02") || f62.startsWith("03") || f62.startsWith("04") || f62.startsWith("05") || f62.startsWith("06") || f62.startsWith("07") || f62.startsWith("08") || f62.startsWith("52"))) {
//                                                int index;
//                                                String value;
//                                                if (f62.startsWith("02")) {
//                                                    index = f62.indexOf("02");
//                                                    len = f62.substring(index + 2, index + 2 + 3);
//                                                    value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
//                                                    Log.d("PrepTask", "CTMS Date and Time : " + value);
//                                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//
//                                                    try {
//                                                        Date date = sdf.parse(value);
//                                                        Calendar calendar = Calendar.getInstance();
//                                                        calendar.setTime(date);
//                                                        Log.d("PrepTask", "Set Date and Time : " + calendar.getTime());
//                                                    } catch (Exception var21) {
//                                                    }
//
//                                                    f62 = f62.replace("02" + len + value, "");
//                                                }
//
//                                                SharedPreferences.Editor editor;
//                                                if (f62.startsWith("03")) {
//                                                    index = f62.indexOf("03");
//                                                    len = f62.substring(index + 2, index + 2 + 3);
//                                                    value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
//                                                    Log.d("PrepTask", "Card Acceptor Identification Code : " + value);
//                                                    editor = this.settings.edit();
//                                                    editor.putString("merchantid", value);
//                                                    editor.commit();
//                                                    f62 = f62.replace("03" + len + value, "");
//                                                }
//
//                                                if (f62.startsWith("04")) {
//                                                    index = f62.indexOf("04");
//                                                    len = f62.substring(index + 2, index + 2 + 3);
//                                                    value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
//                                                    Log.d("PrepTask", "Receive Timeout : " + value);
//                                                    editor = this.settings.edit();
//                                                    editor.putString("rtimeout", value);
//                                                    editor.commit();
//                                                    f62 = f62.replace("04" + len + value, "");
//                                                }
//
//                                                if (f62.startsWith("05")) {
//                                                    index = f62.indexOf("05");
//                                                    len = f62.substring(index + 2, index + 2 + 3);
//                                                    value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
//                                                    Log.d("PrepTask", "Currency Code : " + value);
//                                                    editor = this.settings.edit();
//                                                    editor.putString("currencycode", value);
//                                                    editor.commit();
//                                                    f62 = f62.replace("05" + len + value, "");
//                                                }
//
//                                                if (f62.startsWith("06")) {
//                                                    index = f62.indexOf("06");
//                                                    len = f62.substring(index + 2, index + 2 + 3);
//                                                    value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
//                                                    Log.d("PrepTask", "Country Code : " + value);
//                                                    editor = this.settings.edit();
//                                                    editor.putString("countrycode", value);
//                                                    editor.commit();
//                                                    f62 = f62.replace("06" + len + value, "");
//                                                }
//
//                                                if (f62.startsWith("07")) {
//                                                    index = f62.indexOf("07");
//                                                    len = f62.substring(index + 2, index + 2 + 3);
//                                                    value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
//                                                    Log.d("PrepTask", "Callhome Timer : " + value);
//                                                    editor = this.settings.edit();
//                                                    editor.putString("callhome", value);
//                                                    editor.commit();
//                                                    f62 = f62.replace("07" + len + value, "");
//                                                }
//
//                                                if (f62.startsWith("52")) {
//                                                    index = f62.indexOf("52");
//                                                    len = f62.substring(index + 2, index + 2 + 3);
//                                                    value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
//                                                    Log.d("PrepTask", "Merchant Name and Location : " + value);
//                                                    editor = this.settings.edit();
//                                                    editor.putString("merchant", value);
//                                                    editor.commit();
//                                                    f62 = f62.replace("52" + len + value, "");
//                                                }
//
//                                                if (f62.startsWith("08")) {
//                                                    index = f62.indexOf("08");
//                                                    len = f62.substring(index + 2, index + 2 + 3);
//                                                    value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
//                                                    Log.d("PrepTask", "Merchant Category Code : " + value);
//                                                    editor = this.settings.edit();
//                                                    editor.putString("mcc", value);
//                                                    editor.commit();
//                                                    f62 = f62.replace("08" + len + value, "");
//                                                }
//                                            }
//
//                                            return true;
//                                        } else {
//                                            return false;
//                                        }
//                                    }
//                                } else {
//                                    return false;
//                                }
//                            }
//                        } else {
//                            return false;
//                        }
//                    }
//                } else {
//                    return false;
//                }
            }
        } catch (Exception var22) {
            logs.d("PrepTask", var22.getMessage());
            return false;
        }
    }

    protected void onPostExecute(Boolean result) {
        if (result) {
            logs.d(TAG, "PREP OK");
        } else {
            logs.d(TAG, "PREP FAILED");
        }

    }
}
