    package com.efulltech.efull_nibss_bridge.epms;

    import android.util.Log;
    import com.efulltech.efull_nibss_bridge.epms.packager.Field;
    import com.efulltech.efull_nibss_bridge.epms.packager.IsoField;
    import com.efulltech.efull_nibss_bridge.epms.util.Converters;
    import com.efulltech.efull_nibss_bridge.epms.util.DateUtil;
    import com.efulltech.efull_nibss_bridge.epms.util.ISO8583;
    import com.google.gson.JsonObject;
    import java.io.ByteArrayOutputStream;
    import java.io.InputStream;
    import java.io.OutputStream;
    import java.net.InetSocketAddress;
    import java.net.Socket;
    import java.security.cert.CertificateException;
    import java.security.cert.X509Certificate;

    import java.util.Date;
    import java.util.List;
    import javax.net.ssl.HostnameVerifier;
    import javax.net.ssl.HttpsURLConnection;
    import javax.net.ssl.SSLContext;
    import javax.net.ssl.SSLSession;
    import javax.net.ssl.SSLSocket;
    import javax.net.ssl.SSLSocketFactory;
    import javax.net.ssl.TrustManager;
    import javax.net.ssl.X509TrustManager;


    import static com.efulltech.efull_nibss_bridge.epms.util.Converters.byteArrToHex;


    public class Initiate {
        private static final String TAG = "Controller";

        public static boolean SENT = false;
        public static boolean RECEIVED = false;

        private String fieldXML, mHost, mPort, mProtocol, mStan, mTerminalId, mPosSerial, date1, dateF13, timeF12, dateF7;


        Initiate(){

        }

        Initiate(String fieldXMLReq, String host, String port, String protocol, String stan, String terminalid, String posSerial){
            fieldXML = fieldXMLReq;
            mHost = host;
            mPort = port;
            mProtocol = protocol;
            mStan = stan;
            mTerminalId = terminalid;
            mPosSerial = posSerial;

            date1 = DateUtil.getDate(new Date(), "yyMMdd");
            dateF13 = DateUtil.getDate(new Date(), "MMdd");
            timeF12 = DateUtil.getDate(new Date(), "HHmmss");
            dateF7 = DateUtil.getDate(new Date(), "MMddHHmmss");
        }



        String masterKeyDownload() {
            System.out.println("Master Key Downloader ");
            int unpacked = 0;
            Field field = new Field();
            field.setField(0, "0800");
            field.setField(7, dateF7); // dateTime
            field.setField(11, mStan); // stan
            field.setField(12, timeF12); // time
            field.setField(13, dateF13); // date
            field.setField(70, "001");
            // get all fields
            List<IsoField> fields = field.listFields();
            // pack fields into ISO message
            String packedMsg =  field.packMessage(fields, fieldXML);
            // send packed message to NIBSS
            byte[] response = sendAndReceiveDataFromNIBSS(mHost, Integer.parseInt(mPort), Integer.parseInt(mProtocol), Converters.hexToBytes(packedMsg));
            if (response != null) {
                Log.d(TAG, "Sender Response " + byteArrToHex(response));
                unpacked = field.unpackMessage(response, fieldXML);
            }
            return Integer.toString(unpacked);
       }

       String sessionKeyDownload(){

            return null;
       }

       String pinKeyDownload(){

            return null;
       }

       String parametersDownload(String skey){

            return null;
       }

       String callhome(){

            return null;
       }


      String transaction(String host, String port, String protocol, String stan, String terminalid, String marchantid, int fromac, int toac, int transtype, String pan, String amount, String cashback, String expiry, String mcc, String iccdata, String panseqno, String bin, String track2, String refno, String servicecode, String marchantNameAndAddress, String currencycode, String pinblock, String mti, String datetimeF7, String settlementAmount, String transactionFee, String settlementFee, String key) {
         ISO8583 iso8583 = new ISO8583();


         JsonObject innerObject = new JsonObject();


         if (transtype == 1) {
           String F3 = String.format("00%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
           innerObject.addProperty("0", "0200");
           innerObject.addProperty("2", pan);
           innerObject.addProperty("3", F3);
         } else if (transtype == 7) {
           String F3 = String.format("31%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
           innerObject.addProperty("0", "0100");
           innerObject.addProperty("2", pan);
           innerObject.addProperty("3", F3);
         } else if (transtype == 5) {
           String F3 = String.format("20%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
           innerObject.addProperty("0", "0200");
           innerObject.addProperty("2", pan);
           innerObject.addProperty("3", F3);
         } else if (transtype == 45) {
           String F3 = String.format("01%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
           innerObject.addProperty("0", "0200");
           innerObject.addProperty("2", pan);
           innerObject.addProperty("3", F3);
         } else if (transtype == 3) {
           String F3 = String.format("09%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
           innerObject.addProperty("0", "0200");
           innerObject.addProperty("2", pan);
           innerObject.addProperty("3", F3);
         } else if (transtype == 11) {
           String F3 = String.format("21%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
           innerObject.addProperty("0", "0200");
           innerObject.addProperty("2", pan);
           innerObject.addProperty("3", F3);
         } else if (transtype == 4) {
           String F3 = String.format("00%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
           innerObject.addProperty("0", "0420");
           innerObject.addProperty("2", pan);
           innerObject.addProperty("3", F3);
         } else if (transtype == 103) {
           String F3 = String.format("00%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
           innerObject.addProperty("0", "0421");
           innerObject.addProperty("2", pan);
           innerObject.addProperty("3", F3);
         } else if (transtype == 102) {
           String F3 = String.format("30%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
           innerObject.addProperty("0", "0100");
           innerObject.addProperty("2", pan);
           innerObject.addProperty("3", F3);
         } else if (transtype == 9) {
           String F3 = String.format("39%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
           innerObject.addProperty("0", "0100");
           innerObject.addProperty("2", pan);
           innerObject.addProperty("3", F3);
         } else if (transtype == 10) {
           String F3 = String.format("40%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
           innerObject.addProperty("0", "0200");
           innerObject.addProperty("2", pan);
           innerObject.addProperty("3", F3);
         } else if (transtype == 39) {
           String F3 = String.format("48%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
           innerObject.addProperty("0", "0200");
           innerObject.addProperty("2", pan);
           innerObject.addProperty("3", F3);
         } else if (transtype == 101) {
           String F3 = String.format("4A%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
           innerObject.addProperty("0", "0200");
           innerObject.addProperty("2", pan);
           innerObject.addProperty("3", F3);
         } else if (transtype == 33) {
           String F3 = String.format("60%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
           innerObject.addProperty("0", "0100");
           innerObject.addProperty("2", pan);
           innerObject.addProperty("3", F3);
         } else if (transtype == 34) {
           String F3 = String.format("61%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
           innerObject.addProperty("0", "0200");
           innerObject.addProperty("2", pan);
           innerObject.addProperty("3", F3);
         } else if (transtype == 8) {
           String F3 = String.format("90%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
           innerObject.addProperty("0", "0100");
           innerObject.addProperty("2", pan);
           innerObject.addProperty("3", F3);
        }

         innerObject.addProperty("4", amount);

         Log.d("Controller", "amount: " + amount);

         innerObject.addProperty("7", dateF7);
         innerObject.addProperty("11", stan);
         innerObject.addProperty("12", timeF12);
         innerObject.addProperty("13", dateF13);
                innerObject.addProperty("14", expiry);
         innerObject.addProperty("18", mcc);

         if (iccdata == null || iccdata.isEmpty()) {
           innerObject.addProperty("22", "051");
        } else {
           innerObject.addProperty("22", "901");
        }

         innerObject.addProperty("23", panseqno);
         innerObject.addProperty("25", "00");
         innerObject.addProperty("26", "12");
         innerObject.addProperty("28", "D00000000");
         innerObject.addProperty("32", bin);
         innerObject.addProperty("35", track2);

         innerObject.addProperty("37", refno);

         innerObject.addProperty("40", servicecode);
         innerObject.addProperty("41", terminalid);
         innerObject.addProperty("42", marchantid);

         innerObject.addProperty("43", marchantNameAndAddress);
         innerObject.addProperty("49", currencycode);

         if (pinblock != null) {
           pinblock = pinblock.trim();
        }

         if (pinblock != null && !pinblock.isEmpty()) {
           innerObject.addProperty("52", pinblock);
        }

         if (transtype == 3) {
           String F54 = String.format("%02d%02d%s%s%012d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(5), currencycode, "D", Long.valueOf(Long.parseLong(cashback)) });
           innerObject.addProperty("54", F54);
        }

         if (iccdata != null && !iccdata.isEmpty()) {
           innerObject.addProperty("55", iccdata);
        }

         if (transtype == 4 || transtype == 103) {
           String F90 = String.format("%s%s%s%022d", new Object[] { mti, stan, datetimeF7, Integer.valueOf(0) });

           innerObject.addProperty("90", F90);
        }

         if (transtype == 4 || transtype == 103 || transtype == 5) {
           String F95 = String.format("%012d%012dD%08dD%08d", new Object[] { Long.valueOf(Long.parseLong(amount)), Long.valueOf(Long.parseLong(settlementAmount)), Long.valueOf(Long.parseLong(transactionFee)), Long.valueOf(Long.parseLong(settlementFee)) });
           innerObject.addProperty("95", F95);
        }

         innerObject.addProperty("123", "510101511344001");

         Log.d("Controller", "transaction : " + innerObject.toString());

         String unpacked = null;

         String packed = iso8583.packEPMSISO8583Message(innerObject.toString(), key);
         Log.d("Controller", "packedFields : " + iso8583.unpackEPMSISO8583Message(packed, key));
         Log.d("Controller", "packedISO : " + packed);
         if (packed != null) {
           Log.d("Controller", "IP:PORT : " + host + ":" + port);
           byte[] response = sendAndReceiveDataFromNIBSS(host, Integer.parseInt(port), Integer.parseInt(protocol), hexStringToBytes(packed));
           if (response != null) {
             Log.d("Controller", "response : " + byteArrToHex(response));
             if (response.length > 12) {
               unpacked = iso8583.unpackEPMSISO8583Message(byteArrToHex(response), key);
            } else {

               return null;
            }
             Log.d("Controller", "unpackedISO : " + unpacked);
          }
        } else {
           return null;
        }
         return unpacked;
      }


      public static byte[] sendAndReceiveDataFromNIBSS(String host, int port, int ssl, byte[] requestData) {
         byte[] responseData = null;


        try {
           if (ssl == 1)
          {
             TrustManager[] trustAllCerts = { new X509TrustManager() {
                  public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}
                  public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}
                  public X509Certificate[] getAcceptedIssuers() {
                     return new X509Certificate[0];
                  }
                }
                        };

             SSLContext sc = SSLContext.getInstance("TLS");
             HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String arg0, SSLSession arg1) {
                   return true;
                }
              };
             sc.init(null, trustAllCerts, null);
             HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
             HttpsURLConnection.setDefaultHostnameVerifier(hv);

             Log.d("Controller", "Connecting via SSL...");

             SSLSocketFactory ssf = sc.getSocketFactory();

             SSLSocket socket = (SSLSocket)ssf.createSocket();
             socket.setSoTimeout(60000);
             socket.connect(new InetSocketAddress(host, port), 30000);
             socket.startHandshake();

             Log.d("Controller", "Sending...");
             OutputStream socketOutputStream = socket.getOutputStream();
             socketOutputStream.write(requestData);

             SENT = true;


             InputStream socketInputStream = socket.getInputStream();

             Log.d("Controller", "Receiving...");
             ByteArrayOutputStream buffer = new ByteArrayOutputStream();

             byte[] data = new byte[1024]; int nRead;
             while ((nRead = socketInputStream.read(data, 0, data.length)) != -1) {
               buffer.write(data, 0, nRead);
            }

             buffer.flush();
             responseData = buffer.toByteArray();

             socketOutputStream.close();
             socketInputStream.close();
             socket.close();

             RECEIVED = true;

          }
          else
          {
             Socket socket = new Socket();
             socket.setSoTimeout(60000);
             socket.connect(new InetSocketAddress(host, port), 30000);

             //Log.d("Controller", "Sending...");

             OutputStream socketOutputStream = socket.getOutputStream();
             socketOutputStream.write(requestData);

             SENT = true;

             //Log.d("Controller", "Receiving...");

             InputStream socketInputStream = socket.getInputStream();

             ByteArrayOutputStream buffer = new ByteArrayOutputStream();

             byte[] data = new byte[1024]; int nRead;
             while ((nRead = socketInputStream.read(data, 0, data.length)) != -1) {
               buffer.write(data, 0, nRead);
            }

             buffer.flush();
             responseData = buffer.toByteArray();

             socketOutputStream.close();
             socketInputStream.close();
             socket.close();

             RECEIVED = true;
          }

        }
         catch (Exception e) {
           //Log.d("Controller", "Error:" + e.getMessage());
        }

         return responseData;
      }



      public static byte[] hexStringToBytes(String s) {
         int iLength = s.length();
         int iBuff = iLength / 2;

         byte[] buff = new byte[iBuff];

         int j = 0;
         for (int i = 0; i < iLength; i += 2) {


          try {
             String s1 = s.substring(i, i + 2);
             buff[j++] = (byte)Integer.parseInt(s1, 16);
          }
           catch (Exception e) {
             e.printStackTrace();
          }
        }


         return buff;

      }

    }
