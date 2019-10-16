/*     */ package com.efulltech.efull_nibss_bridge.epms;
/*     */
/*     */ import android.util.Log;
/*     */ import com.efulltech.efull_nibss_bridge.epms.util.DateUtil;
/*     */ import com.efulltech.efull_nibss_bridge.epms.util.ISO8583;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */
import java.util.Arrays;
import java.util.Date;
/*     */ import javax.net.ssl.HostnameVerifier;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.SSLSocket;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.X509TrustManager;
/*     */ 
/*     */
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOField;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
/*     */ 
/*     */ 
/*     */ // https://bayurimba.wordpress.com/2014/08/04/build-and-parse-iso-8583-in-java/





    public class Initiate {
/*     */   private static final String TAG = "Initiate";
/*  43 */   private static String date1 = DateUtil.getDate(new Date(), "yyMMdd");
/*  44 */   private static String dateF13 = DateUtil.getDate(new Date(), "MMdd");
/*     */
/*  46 */   private static String timeF12 = DateUtil.getDate(new Date(), "HHmmss");
/*  47 */   private static String dateF7 = DateUtil.getDate(new Date(), "MMddHHmmss");
/*     */ 
/*     */   public static boolean SENT = false;
/*     */   public static boolean RECEIVED = false;



   public static String masterKeyDownload(String fieldXML, String host, String port, String protocol, String stan, String terminalid, String posSerial) {
        return packager("9A0000", fieldXML, host, port, protocol, stan, terminalid, posSerial);
   }

   public static String sessionKeyDownload(String fieldXML, String host, String port, String protocol, String stan, String terminalid, String posSerial) {
       return packager("9B0000", fieldXML, host, port, protocol, stan, terminalid, posSerial);
    }


   public static String pinKeyDownload(String fieldXML, String host, String port, String protocol, String stan, String terminalid, String posSerial) {
       return packager("9G0000", fieldXML, host, port, protocol, stan, terminalid, posSerial);

   }

    public static String parametersDownload(String fieldXML, String host, String port, String protocol, String stan, String terminalid, String posSerial, String key) {
        return packager("9C0000", fieldXML, host, port, protocol, stan, terminalid, posSerial);
    }

    public static String callhome(String fieldXML, String host, String port, String protocol, String stan, String terminalid, String posSerial) {
        return packager("9D0000", fieldXML, host, port, protocol, stan, terminalid, posSerial);
    }



        private static String packager(String ProcCode, String fieldXML, String host, String port, String protocol, String stan, String terminalid, String posSerial){
            String F62 = String.format("01%03d%s", new Object[] { Integer.valueOf(posSerial.length()), posSerial });
            String packedIsoMessage = "", unpackedIsoMessage = "";
            byte[] bIsoMsg = null;
            date1 = DateUtil.getDate(new Date(), "yyMMdd");
            dateF13 = DateUtil.getDate(new Date(), "MMdd");
            timeF12 = DateUtil.getDate(new Date(), "HHmmss");
            dateF7 = DateUtil.getDate(new Date(), "MMddHHmmss");
            try {
                // Packing the ISO message

                ISOMsg m = new ISOMsg();
                // Setting packager
                GenericPackager packager = new GenericPackager(fieldXML);

                m.set (new ISOField (0, "0800"));
                m.set (new ISOField(3, ProcCode));
                m.set (new ISOField (7, dateF7));
                m.set (new ISOField (11, stan));
                m.set (new ISOField (12, timeF12));
                m.set (new ISOField (13, dateF13));
                m.set (new ISOField (41, terminalid));
                m.set (new ISOField (62, F62));
                m.setPackager(packager);

                // pack the ISO 8583 Message
                bIsoMsg = m.pack();
                packedIsoMessage = new String(bIsoMsg);
                Log.d(TAG, "Master Key Download: "+ packedIsoMessage);
            } catch (ISOException e) {
                e.printStackTrace();
            }


            if (packedIsoMessage != null) {
                Log.d("Initiate", "IP:PORT : " + host + ":" + port+" "+hexStringToBytes(hex(bIsoMsg)));
                byte[] response = sendAndReceiveDataFromNIBSS(host, Integer.parseInt(port), Integer.parseInt(protocol), bIsoMsg);
                String res = new String(response);
                if (response != null) {
                    Log.d("Initiate", "response : " + hex(response));
                    if (response.length > 12) {
                        // unpack ISO Response
                        try {
                            GenericPackager UnPackager = new GenericPackager(fieldXML);
                            ISOMsg isoMsg = new ISOMsg();
                            isoMsg.setPackager(UnPackager);
                            isoMsg.unpack(response);
                            Log.d(TAG, "Field 14: "+isoMsg.getString("14"));
                            // last, print the unpacked ISO8583
                            Log.d(TAG, "MTI='"+isoMsg.getMTI()+"'");
                            for(int i=1; i<= isoMsg.getMaxField(); i++){
                                if(isoMsg.hasField(i))
                                    unpackedIsoMessage += i+"='"+isoMsg.getString(i)+"'";
                                Log.d(TAG, i+"='"+isoMsg.getString(i)+"'");
                            }
                        } catch (ISOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        return null;
                    }
                    Log.d("Initiate", "unpackedISO : " + unpackedIsoMessage);
                }
            } else {
                return null;
            }

            return unpackedIsoMessage;
        }

/*     */   public static String transaction(String host, String port, String protocol, String stan, String terminalid, String marchantid, int fromac, int toac, int transtype, String pan, String amount, String cashback, String expiry, String mcc, String iccdata, String panseqno, String bin, String track2, String refno, String servicecode, String marchantNameAndAddress, String currencycode, String pinblock, String mti, String datetimeF7, String settlementAmount, String transactionFee, String settlementFee, String key) {
/* 271 */     ISO8583 iso8583 = new ISO8583();
/*     */ 
/*     */     
/* 274 */     JsonObject innerObject = new JsonObject();
/*     */ 
/*     */     
/* 277 */     if (transtype == 1) {
/* 278 */       String F3 = String.format("00%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
/* 279 */       innerObject.addProperty("0", "0200");
/* 280 */       innerObject.addProperty("2", pan);
/* 281 */       innerObject.addProperty("3", F3);
/* 282 */     } else if (transtype == 7) {
/* 283 */       String F3 = String.format("31%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
/* 284 */       innerObject.addProperty("0", "0100");
/* 285 */       innerObject.addProperty("2", pan);
/* 286 */       innerObject.addProperty("3", F3);
/* 287 */     } else if (transtype == 5) {
/* 288 */       String F3 = String.format("20%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
/* 289 */       innerObject.addProperty("0", "0200");
/* 290 */       innerObject.addProperty("2", pan);
/* 291 */       innerObject.addProperty("3", F3);
/* 292 */     } else if (transtype == 45) {
/* 293 */       String F3 = String.format("01%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
/* 294 */       innerObject.addProperty("0", "0200");
/* 295 */       innerObject.addProperty("2", pan);
/* 296 */       innerObject.addProperty("3", F3);
/* 297 */     } else if (transtype == 3) {
/* 298 */       String F3 = String.format("09%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
/* 299 */       innerObject.addProperty("0", "0200");
/* 300 */       innerObject.addProperty("2", pan);
/* 301 */       innerObject.addProperty("3", F3);
/* 302 */     } else if (transtype == 11) {
/* 303 */       String F3 = String.format("21%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
/* 304 */       innerObject.addProperty("0", "0200");
/* 305 */       innerObject.addProperty("2", pan);
/* 306 */       innerObject.addProperty("3", F3);
/* 307 */     } else if (transtype == 4) {
/* 308 */       String F3 = String.format("00%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
/* 309 */       innerObject.addProperty("0", "0420");
/* 310 */       innerObject.addProperty("2", pan);
/* 311 */       innerObject.addProperty("3", F3);
/* 312 */     } else if (transtype == 103) {
/* 313 */       String F3 = String.format("00%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
/* 314 */       innerObject.addProperty("0", "0421");
/* 315 */       innerObject.addProperty("2", pan);
/* 316 */       innerObject.addProperty("3", F3);
/* 317 */     } else if (transtype == 102) {
/* 318 */       String F3 = String.format("30%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
/* 319 */       innerObject.addProperty("0", "0100");
/* 320 */       innerObject.addProperty("2", pan);
/* 321 */       innerObject.addProperty("3", F3);
/* 322 */     } else if (transtype == 9) {
/* 323 */       String F3 = String.format("39%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
/* 324 */       innerObject.addProperty("0", "0100");
/* 325 */       innerObject.addProperty("2", pan);
/* 326 */       innerObject.addProperty("3", F3);
/* 327 */     } else if (transtype == 10) {
/* 328 */       String F3 = String.format("40%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
/* 329 */       innerObject.addProperty("0", "0200");
/* 330 */       innerObject.addProperty("2", pan);
/* 331 */       innerObject.addProperty("3", F3);
/* 332 */     } else if (transtype == 39) {
/* 333 */       String F3 = String.format("48%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
/* 334 */       innerObject.addProperty("0", "0200");
/* 335 */       innerObject.addProperty("2", pan);
/* 336 */       innerObject.addProperty("3", F3);
/* 337 */     } else if (transtype == 101) {
/* 338 */       String F3 = String.format("4A%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
/* 339 */       innerObject.addProperty("0", "0200");
/* 340 */       innerObject.addProperty("2", pan);
/* 341 */       innerObject.addProperty("3", F3);
/* 342 */     } else if (transtype == 33) {
/* 343 */       String F3 = String.format("60%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
/* 344 */       innerObject.addProperty("0", "0100");
/* 345 */       innerObject.addProperty("2", pan);
/* 346 */       innerObject.addProperty("3", F3);
/* 347 */     } else if (transtype == 34) {
/* 348 */       String F3 = String.format("61%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
/* 349 */       innerObject.addProperty("0", "0200");
/* 350 */       innerObject.addProperty("2", pan);
/* 351 */       innerObject.addProperty("3", F3);
/* 352 */     } else if (transtype == 8) {
/* 353 */       String F3 = String.format("90%02d%02d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(toac) });
/* 354 */       innerObject.addProperty("0", "0100");
/* 355 */       innerObject.addProperty("2", pan);
/* 356 */       innerObject.addProperty("3", F3);
/*     */     } 
/*     */     
/* 359 */     innerObject.addProperty("4", amount);
/*     */     
/* 361 */     Log.d("Initiate", "amount: " + amount);
/*     */     
/* 363 */     innerObject.addProperty("7", dateF7);
/* 364 */     innerObject.addProperty("11", stan);
/* 365 */     innerObject.addProperty("12", timeF12);
/* 366 */     innerObject.addProperty("13", dateF13);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 378 */     innerObject.addProperty("14", expiry);
/* 379 */     innerObject.addProperty("18", mcc);
/*     */     
/* 381 */     if (iccdata == null || iccdata.isEmpty()) {
/* 382 */       innerObject.addProperty("22", "051");
/*     */     } else {
/* 384 */       innerObject.addProperty("22", "901");
/*     */     } 
/*     */     
/* 387 */     innerObject.addProperty("23", panseqno);
/* 388 */     innerObject.addProperty("25", "00");
/* 389 */     innerObject.addProperty("26", "12");
/* 390 */     innerObject.addProperty("28", "D00000000");
/* 391 */     innerObject.addProperty("32", bin);
/* 392 */     innerObject.addProperty("35", track2);
/*     */     
/* 394 */     innerObject.addProperty("37", refno);
/*     */     
/* 396 */     innerObject.addProperty("40", servicecode);
/* 397 */     innerObject.addProperty("41", terminalid);
/* 398 */     innerObject.addProperty("42", marchantid);
/*     */     
/* 400 */     innerObject.addProperty("43", marchantNameAndAddress);
/* 401 */     innerObject.addProperty("49", currencycode);
/*     */     
/* 403 */     if (pinblock != null) {
/* 404 */       pinblock = pinblock.trim();
/*     */     }
/*     */     
/* 407 */     if (pinblock != null && !pinblock.isEmpty()) {
/* 408 */       innerObject.addProperty("52", pinblock);
/*     */     }
/*     */     
/* 411 */     if (transtype == 3) {
/* 412 */       String F54 = String.format("%02d%02d%s%s%012d", new Object[] { Integer.valueOf(fromac), Integer.valueOf(5), currencycode, "D", Long.valueOf(Long.parseLong(cashback)) });
/* 413 */       innerObject.addProperty("54", F54);
/*     */     } 
/*     */     
/* 416 */     if (iccdata != null && !iccdata.isEmpty()) {
/* 417 */       innerObject.addProperty("55", iccdata);
/*     */     }
/*     */     
/* 420 */     if (transtype == 4 || transtype == 103) {
/* 421 */       String F90 = String.format("%s%s%s%022d", new Object[] { mti, stan, datetimeF7, Integer.valueOf(0) });
/*     */       
/* 423 */       innerObject.addProperty("90", F90);
/*     */     } 
/*     */     
/* 426 */     if (transtype == 4 || transtype == 103 || transtype == 5) {
/* 427 */       String F95 = String.format("%012d%012dD%08dD%08d", new Object[] { Long.valueOf(Long.parseLong(amount)), Long.valueOf(Long.parseLong(settlementAmount)), Long.valueOf(Long.parseLong(transactionFee)), Long.valueOf(Long.parseLong(settlementFee)) });
/* 428 */       innerObject.addProperty("95", F95);
/*     */     } 
/*     */     
/* 431 */     innerObject.addProperty("123", "510101511344001");
/*     */     
/* 433 */     Log.d("Initiate", "transaction : " + innerObject.toString());
/*     */     
/* 435 */     String unpacked = null;
/*     */     
/* 437 */     String packed = iso8583.packEPMSISO8583Message(innerObject.toString(), key);
/* 438 */     Log.d("Initiate", "packedFields : " + iso8583.unpackEPMSISO8583Message(packed, key));
/* 439 */     Log.d("Initiate", "packedISO : " + packed);
/* 440 */     if (packed != null) {
/* 441 */       Log.d("Initiate", "IP:PORT : " + host + ":" + port);
/* 442 */       byte[] response = sendAndReceiveDataFromNIBSS(host, Integer.parseInt(port), Integer.parseInt(protocol), hexStringToBytes(packed));
/* 443 */       if (response != null) {
/* 444 */         Log.d("Initiate", "response : " + hex(response));
/* 445 */         if (response.length > 12) {
/* 446 */           unpacked = iso8583.unpackEPMSISO8583Message(hex(response), key);
/*     */         } else {
/*     */           
/* 449 */           return null;
/*     */         } 
/* 451 */         Log.d("Initiate", "unpackedISO : " + unpacked);
/*     */       } 
/*     */     } else {
/* 454 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 469 */     return unpacked;
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte[] sendAndReceiveDataFromNIBSS(String host, int port, int ssl, byte[] requestData) {
/* 474 */     byte[] responseData = null;
/*     */ 
/*     */     
/*     */     try {
/* 478 */       if (ssl == 1)
/*     */       {
/* 480 */         TrustManager[] trustAllCerts = { new X509TrustManager() {
/*     */               public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}
/*     */               public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}
/*     */               public X509Certificate[] getAcceptedIssuers() {
/* 496 */                 return new X509Certificate[0];
/*     */               }
/*     */             }
                    };

/* 502 */         SSLContext sc = SSLContext.getInstance("TLS");
/* 504 */         HostnameVerifier hv = new HostnameVerifier() {
/*     */             public boolean verify(String arg0, SSLSession arg1) {
/* 506 */               return true;
/*     */             }
/*     */           };
/* 510 */         sc.init(null, trustAllCerts, null);
/* 511 */         HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
/* 512 */         HttpsURLConnection.setDefaultHostnameVerifier(hv);
/*     */         
/* 514 */         Log.d("Initiate", "Connecting via SSL...");
/*     */         
/* 516 */         SSLSocketFactory ssf = sc.getSocketFactory();
/*     */         
/* 518 */         SSLSocket socket = (SSLSocket)ssf.createSocket();
/* 519 */         socket.setSoTimeout(60000);
/* 520 */         socket.connect(new InetSocketAddress(host, port), 30000);
/* 521 */         socket.startHandshake();

/* 537 */         Log.d("Initiate", "Sending...");
/* 538 */         OutputStream socketOutputStream = socket.getOutputStream();
/* 539 */         socketOutputStream.write(requestData);
/*     */         
/* 541 */         SENT = true;
/*     */ 
/*     */         
/* 544 */         InputStream socketInputStream = socket.getInputStream();
/*     */         
/* 546 */         Log.d("Initiate", "Receiving...");
/* 547 */         ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/*     */         
/* 549 */         byte[] data = new byte[1024]; int nRead;
/* 550 */         while ((nRead = socketInputStream.read(data, 0, data.length)) != -1) {
/* 551 */           buffer.write(data, 0, nRead);
/*     */         }
/*     */         
/* 554 */         buffer.flush();
/* 555 */         responseData = buffer.toByteArray();
/*     */         
/* 557 */         socketOutputStream.close();
/* 558 */         socketInputStream.close();
/* 559 */         socket.close();
/*     */         
/* 561 */         RECEIVED = true;
/*     */       
/*     */       }
/*     */       else
/*     */       {
/* 566 */         Socket socket = new Socket();
/* 567 */         socket.setSoTimeout(60000);
/* 568 */         socket.connect(new InetSocketAddress(host, port), 30000);
/*     */         
/* 570 */         Log.d("Initiate", "Sending...");
/*     */         
/* 572 */         OutputStream socketOutputStream = socket.getOutputStream();
/* 573 */         socketOutputStream.write(requestData);
/*     */         
/* 575 */         SENT = true;
/*     */         
/* 577 */         Log.d("Initiate", "Receiving...");
/*     */         
/* 579 */         InputStream socketInputStream = socket.getInputStream();
/*     */         
/* 581 */         ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/*     */         
/* 583 */         byte[] data = new byte[1024]; int nRead;
/* 584 */         while ((nRead = socketInputStream.read(data, 0, data.length)) != -1) {
/* 585 */           buffer.write(data, 0, nRead);
/*     */         }
/*     */         
/* 588 */         buffer.flush();
/* 589 */         responseData = buffer.toByteArray();
/*     */         
/* 591 */         socketOutputStream.close();
/* 592 */         socketInputStream.close();
/* 593 */         socket.close();
/*     */         
/* 595 */         RECEIVED = true;
/*     */       }
/*     */     
/*     */     }
/* 599 */     catch (Exception e) {
/* 600 */       Log.d("Initiate", "Error:" + e.getMessage());
/*     */     } 
/*     */     
/* 603 */     return responseData;
/*     */   }
/*     */ 
/*     */
/*     */   
/*     */   public static byte[] hexStringToBytes(String s) {
/* 609 */     int iLength = s.length();
/* 610 */     int iBuff = iLength / 2;
/*     */     
/* 612 */     byte[] buff = new byte[iBuff];
/*     */     
/* 614 */     int j = 0;
/* 615 */     for (int i = 0; i < iLength; i += 2) {
/*     */ 
/*     */       
/*     */       try {
/* 619 */         String s1 = s.substring(i, i + 2);
/* 620 */         buff[j++] = (byte)Integer.parseInt(s1, 16);
/*     */       }
/* 622 */       catch (Exception e) {
/* 623 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 628 */     return buff;

/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String hex(byte[] data) {
/* 634 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 636 */     for (byte b : data) {
/*     */       
/* 638 */       sb.append(Character.forDigit((b & 0xF0) >> 4, 16));
/* 639 */       sb.append(Character.forDigit(b & 0xF, 16));
/*     */     } 
/*     */     
/* 642 */     return sb.toString().toUpperCase();
/*     */   }
/*     */ }


/* Location:              /Users/MAC/Documents/classes.jar!/com/arke/sdk/util/epms/Initiate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */