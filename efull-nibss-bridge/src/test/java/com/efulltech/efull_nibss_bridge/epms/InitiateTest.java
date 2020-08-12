package com.efulltech.efull_nibss_bridge.epms;

import com.efulltech.efull_nibss_bridge.epms.packager.Field;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.builders.ISOMessageBuilder;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.entities.ISOMessage;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.enums.FIELDS;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.enums.MESSAGE_FUNCTION;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.enums.MESSAGE_ORIGIN;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.enums.VERSION;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.exceptions.ISOException;
import com.efulltech.efull_nibss_bridge.epms.packager.IsoField;
import com.efulltech.efull_nibss_bridge.epms.util.Converters;
import com.efulltech.efull_nibss_bridge.epms.util.DateUtil;
import com.efulltech.efull_nibss_bridge.operations.Controller;
import com.efulltech.efull_nibss_bridge.operations.PrepTask;
import com.efulltech.efull_nibss_bridge.utils.Logs;


import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.net.ssl.KeyManager;

import static com.efulltech.efull_nibss_bridge.operations.Controller.sendAndReceiveDataFromNIBSS;
import static org.junit.Assert.*;

public class InitiateTest {

    private String HOST = "196.6.103.72";
    private int PORT = 5042;

    @Test
    public void masterKeyDownload() {
    }

    @Test
    public void sessionKeyDownload() {
    }

    @Test
    public void pinKeyDownload() {
    }

    @Test
    public void parametersDownload() {
    }

    @Test
    public void callhome() {

        String date1 = DateUtil.getDate(new Date(), "yyMMdd");
        String dateF13 = DateUtil.getDate(new Date(), "MMdd");
        String timeF12 = DateUtil.getDate(new Date(), "HHmmss");
        String dateF7 = DateUtil.getDate(new Date(), "MMddHHmmss");
        System.out.println("Res "+ dateF7);
    }

    @Test
    public void transaction() {
    }

    @Test
    public void hexStringToBytes() {
        Initiate initiate = new Initiate();
        String val = "08008238000000000000040000000000000010041837091234561837091004001";
        byte[] res = initiate.hexStringToBytes(val);
        String result = "";
        for(int i = 0; i < res.length; i++){
            result = result +""+res[i];
        }
//        System.out.println("::hexToBytes:: "+result);
        Converters converters = new Converters();
        System.out.println("::Converters.bytesToHex:: "+converters.bytesToHex(res));
        System.out.println("::Converters.byteArrToHex:: "+converters.byteArrToHex(res));
    }

    @Test
    public void hex() {
    }

    @Test
    public void MTI() {
        Initiate initiate = new Initiate("", "196.6.103.72", "5042", "1", "123456", "20390015", "123456789");
        String res = initiate.masterKeyDownload();
        System.out.println("Res:: "+res);
    }

    @Test
    public void packMessage(){
        Logs logs = new Logs(true);
//        ISOMessage response = Controller.masterKeyDownload("0", "123456", "20390015", "123456789");
//        logs.d("PrepTask", "response : " + response);
//        if (response == null) {
//        } else {
//            String f39 = null;
//            try {
//                f39 = response.getStringField(39);
//                logs.d("PrepTask", "f39 : " + f39);
//            } catch (ISOException e) {
//                e.printStackTrace();
//            }
//        }


        ISOMessage isoMessage = null;
        try {
            String posSerial = "123456789";
            String dateF13 = DateUtil.getDate(new Date(), "MMdd");
            String timeF12 = DateUtil.getDate(new Date(), "HHmmss");
            String dateF7 = DateUtil.getDate(new Date(), "MMddHHmmss");
            String F62 = String.format("01%03d%s", posSerial.length(), posSerial);
            isoMessage = ISOMessageBuilder.Packer(VERSION.V1987)
                    .networkManagement()
                    .mti(MESSAGE_FUNCTION.Request, MESSAGE_ORIGIN.Acquirer)
                    .processCode("9A0000")
                    .setField(FIELDS.F7_TransmissionDataTime, dateF7)
                    .setField(FIELDS.F11_STAN, "0987665")
                    .setField(FIELDS.F12_LocalTime, timeF12)
                    .setField(FIELDS.F13_LocalDate, dateF13)
                    .setField(FIELDS.F41_CA_TerminalID, "24555667")
                    .setField(FIELDS.F62_Reserved_Private, "01009123456789")
//                    .setField(FIELDS.F70_Network_Code, "001")
                    .setHeader("1002230000")
                    .build();

            String packed = isoMessage.toString();
            logs.d("Initiate", "request.masterKey : " + packed);
            if (packed != null) {
                byte[] response = sendAndReceiveDataFromNIBSS(0, packed.getBytes());
                if (response != null) {
                    logs.d("Initiate", "response : " + Converters.bytesToHex(response));
                    if (response.length <= 12) {
                        logs.d("Test 1", "null");
                    }
                    ISOMessage unpackedMessage = ISOMessageBuilder.Unpacker()
                            .setMessage(Converters.bytesToHex(response))
                            .build();
                    logs.d("Unpacked::", unpackedMessage.toString());
                }else{
                    logs.d("Test 2", "null");            }
            } else {
                logs.d("Test 3", "null");
            }
        } catch (ISOException e) {
            e.printStackTrace();
        }
    }
}