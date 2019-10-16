//package com.efulltech.efull_nibss_bridge;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.res.AssetManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Environment;
//import android.preference.PreferenceManager;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.efulltech.efull_nibss_bridge.parser.Converters;
//import com.efulltech.efull_nibss_bridge.parser.Strings;
//import com.efulltech.efull_nibss_bridge.parser.field.CompoundField;
//import com.efulltech.efull_nibss_bridge.parser.field.FixedCompoundField;
//import com.efulltech.efull_nibss_bridge.parser.field.IsoField;
//
//import org.apache.commons.io.FileUtils;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.util.Arrays;
//
//import javax.xml.bind.JAXBException;
//
//import pub.devrel.easypermissions.EasyPermissions;
//
//public class MessagePackager {
//
//    private static final int WRITE_REQUEST_CODE = 3764;
//    private final SharedPreferences mPreferences;
//    private int msgType;
//    private Context mContext;
//    private String TAG = "MsgPackager";
//
//    public MessagePackager(Context context){
//        mContext = context;
//        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
//    }
//
//    public void send(String resourceType){
//        byte[] bytes = null;
//        String lines;
//            try {
//                String filePath = mPreferences.getString(resourceType, null);
//                bytes = getFileBytes(filePath);
//                if(bytes != null) {
//                    lines = new String(bytes, "ISO-8859-1");
//                    IsoField isoField = null;
//                    try {
//                        isoField = Strings.unmarshal(lines, IsoField.class, "application/xml");
//                        String encodedValue;
//                        // switch case to determine the kind of message to send
//                        switch (resourceType){
//                            case "field":
//                                //
//
////                                encodedValue = field.encode();
////                                Log.i("Encoded value => %s", encodedValue);
////                                field.setValue("");
////                                field.decode(encodedValue);
//                                break;
//                            default:
//
//                                break;
//                        }
//
//
//                    } catch (JAXBException ex) {
//                        ex.printStackTrace();
//                    }
//
//                }else{
//                    Toast.makeText(mContext, "Field XML file does not exist", Toast.LENGTH_SHORT).show();
//                }
//
//            } catch (IOException  e) {
//                e.printStackTrace();
//            }
//
//    }
//
//    private CompoundField genFinReqMsg(IsoField isoField){
//
////        private class String encodeField(){
////
////        }
//
//        // this field contains values for sending a financial request
//        CompoundField field = new FixedCompoundField(isoField);
//        field.setValue(0, "0200"); // MTI - n4
//        field.setValue(2, "9001000000672941810"); // PAN - n..19
//        field.setValue(3, "010000"); // Processing Code - n6
//        field.setValue(4, "5000000"); // Amount, Transaction - n12
//        field.setValue(7, "1124222950"); // Date Time - n10
//        field.setValue(11, "869236"); // STAN - n6
//        field.setValue(12, "012958"); // Local Transaction Time (hhmmss) - n6
//        field.setValue(13, "1125"); // Local Transaction Date (MMDD) - n4
//        field.setValue(18, "6011"); // Merchant Type - n4
//        field.setValue(19, "254"); // Acquiring Institution Country Code - n3
//        field.setValue(22, "1"); // Point of Service Entry Mode - n3
//        field.setValue(23, "177884993993003"); // card sequence number
//        field.setValue(25, "00"); // Point of Service Condition Code - n2
//        field.setValue(26, "12"); // Point of Service Capture Code - n2
//        field.setValue(28, "D00000000"); // Amount, Transaction Fee - x+n8
//        field.setValue(32, "666767"); // Acquiring Institution Identification Code - n..11
//        field.setValue(35, "555555"); // track 2 data
//        field.setValue(37, "732822869236"); // Retrieval Reference Number - an12
//        field.setValue(40, "9844"); // service restriction code
//        field.setValue(41, "42810486"); // Card Acceptor Terminal Identification - ans8
//        field.setValue(42, "Shemistone PLC"); // Card Acceptor Identification Code - ans15
//        field.setValue(43, "Shemistone PLC NRB KE"); // Card acceptor name/location (1-23 street address, 24-36 city, 37-38 state, 39-40 country)  - ans40
//        field.setValue(49, "835"); // Currency Code Transaction - a3 or n3
//        field.setValue(52, Converters.hexToBin("931EFFFFFFFFFFFF")); // PIN Data - b8
//        field.setValue(53, "2001000000000000"); // Security Related Control Information - n16
//        field.setValue(54, "5000000"); // Additional amounts
//        field.setValue(55, "66477388399HG990"); // Integrated Circuit Card System Related Data
//        field.setValue(56, "0900"); // Message response code
//        field.setValue(59, "5000000"); // Transport (echo) data
//        field.setValue(60, "00000000000000000000"); // Payment information
//        field.setValue(62, "3552772882"); // Private Field, Management Data
//        field.setValue(123, "5000000"); // POS Data
//        field.setValue(124, "003993883"); // Near field communication Data
//        field.setValue(128, "5678938837"); // Secondary Message Hash Value
//
//        return field;
//    }
//
//    private byte[] getFileBytes(String path) {
//        // https://stackoverflow.com/questions/10039672/android-how-to-read-file-in-bytes
//        File f = new File(path);
//        int size = (int) f.length();
//        byte bytes[] = new byte[size];
//        byte tmpBuff[] = new byte[size];
//        FileInputStream fis= null;
//        try {
//            fis = new FileInputStream(f);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//
//            int read = fis.read(bytes, 0, size);
//            if (read < size) {
//                int remain = size - read;
//                while (remain > 0) {
//                    read = fis.read(tmpBuff, 0, remain);
//                    System.arraycopy(tmpBuff, 0, bytes, size - remain, read);
//                    remain -= read;
//                }
//            }
//        }  catch (IOException e){
//            try {
//                throw e;
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        } finally {
//            try {
//                fis.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return bytes;
//    }
//
//}
