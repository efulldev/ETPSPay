package com.efulltech.efull_nibss_bridge.operations;

import android.util.Log;

import com.besl.nibss.epms.ISO8583;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.builders.ISOMessageBuilder;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.entities.ISOMessage;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.enums.FIELDS;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.enums.MESSAGE_FUNCTION;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.enums.MESSAGE_ORIGIN;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.enums.VERSION;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.exceptions.ISOException;
import com.efulltech.efull_nibss_bridge.epms.util.Converters;
import com.efulltech.efull_nibss_bridge.epms.util.DateUtil;
import com.efulltech.efull_nibss_bridge.utils.Logs;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Controller {
    private static final String TAG = "Initiate";
    private static String dateF13 = DateUtil.getDate(new Date(), "MMdd");
    private static String timeF12 = DateUtil.getDate(new Date(), "HHmmss");
    private static String dateF7 = DateUtil.getDate(new Date(), "MMddHHmmss");
    public static boolean SENT = false;
    public static boolean RECEIVED = false;
    private static Logs logs;


    private static final String HOST = "196.6.103.72";
    private static final int PORT = 5042;

    public Controller() {
        logs = new Logs(true);
    }

    public static ISOMessage masterKeyDownload(String protocol, String stan, String terminalid, String posSerial) {
            //Pack
            try {
                ISOMessage isoMessage = ISOMessageBuilder.Packer(VERSION.V1987)
                        .networkManagement()
                        .mti(MESSAGE_FUNCTION.Request, MESSAGE_ORIGIN.Acquirer)
                        .processCode("9A0000")
                        .setField(FIELDS.F7_TransmissionDataTime, dateF7)
                        .setField(FIELDS.F11_STAN, stan)
                        .setField(FIELDS.F12_LocalTime, timeF12)
                        .setField(FIELDS.F13_LocalDate, dateF13)
                        .setField(FIELDS.F41_CA_TerminalID, terminalid)
                        .setField(FIELDS.F70_Network_Code, "001")
                        .setHeader("1002230000")
                        .build();
                String packed = isoMessage.toString();
                logs.d("Initiate", "request.masterKey : " + packed);
              if (packed != null) {
                byte[] response = sendAndReceiveDataFromNIBSS(Integer.parseInt(protocol), packed.getBytes());
                if (response != null) {
                    logs.d("Initiate", "response : " + Converters.bytesToHex(response));
                    if (response.length <= 12) {
                        return null;
                    }

                    ISOMessage unpackedMessage = ISOMessageBuilder.Unpacker()
                            .setMessage(isoMessage.toString())
                            .build();
                    logs.d("Initiate", "unpackedISO : " + unpackedMessage.toString());
                    return unpackedMessage;
                }else{
                    return null;
                }
              } else {
                return null;
              }
        } catch (ISOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static byte[] sendAndReceiveDataFromNIBSS(int ssl, byte[] requestData) {
        byte[] responseData = null;

        try {
            if (ssl == 1) {
                TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }};
                SSLContext sc = SSLContext.getInstance("TLS");
                HostnameVerifier hv = new HostnameVerifier() {
                    public boolean verify(String arg0, SSLSession arg1) {
                        return true;
                    }
                };
                sc.init((KeyManager[])null, trustAllCerts, (SecureRandom)null);
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(hv);
                Log.d("Initiate", "Connecting via SSL...");
                SSLSocketFactory ssf = sc.getSocketFactory();
                SSLSocket socket = (SSLSocket)ssf.createSocket();
                socket.setSoTimeout(60000);
                socket.connect(new InetSocketAddress(HOST, PORT), 30000);
                socket.startHandshake();
                Log.d("Initiate", "Sending...");
                OutputStream socketOutputStream = socket.getOutputStream();
                socketOutputStream.write(requestData);
                SENT = true;
                InputStream socketInputStream = socket.getInputStream();
                Log.d("Initiate", "Receiving...");
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                byte[] data = new byte[1024];

                int nRead;
                while((nRead = socketInputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }

                buffer.flush();
                responseData = buffer.toByteArray();
                socketOutputStream.close();
                socketInputStream.close();
                socket.close();
                RECEIVED = true;
            } else {
                Socket socket = new Socket();
                socket.setSoTimeout(60000);
                socket.connect(new InetSocketAddress(HOST, PORT), 30000);
                Log.d("Initiate", "Sending...");
                OutputStream socketOutputStream = socket.getOutputStream();
                socketOutputStream.write(requestData);
                SENT = true;
                Log.d("Initiate", "Receiving...");
                InputStream socketInputStream = socket.getInputStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                byte[] data = new byte[1024];

                int nRead;
                while((nRead = socketInputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }

                buffer.flush();
                responseData = buffer.toByteArray();
                socketOutputStream.close();
                socketInputStream.close();
                socket.close();
                RECEIVED = true;
            }
        } catch (Exception var15) {
            Log.d("Initiate", "Error:" + var15.getMessage());
        }

        return responseData;
    }

}
