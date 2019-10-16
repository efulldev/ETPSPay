/*     */ package com.efulltech.efull_nibss_bridge.epms;
/*     */ 
/*     */ import android.os.RemoteException;
/*     */ import com.efulltech.efull_nibss_bridge.epms.util.SerialPort;
/*     */ import java.io.File;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SerialLogger
/*     */ {
/*     */   private boolean status;
/*     */   
/*     */   public SerialLogger() {
/*     */     try {
/*  20 */       this.status = open();
/*     */     }
/*  22 */     catch (Exception ex) {
/*  23 */       this.status = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   private void close() {
    try {
        SerialPort.getInstance().close();
    } catch (RemoteException e) {
        e.printStackTrace();
    }
}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void flush() {
/*  38 */
    try {
        if (!SerialPort.getInstance().isBufferEmpty(true)) {
        /*  39 */       SerialPort.getInstance().clearInputBuffer();
        /*     */     }
    } catch (RemoteException e) {
        e.printStackTrace();
    }
    /*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private void readDataWithTimeout(byte[] readData) throws RemoteException { SerialPort.getInstance().read(readData, 10000); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private void sendDataWithTimeout(byte[] writeData) throws RemoteException { SerialPort.getInstance().write(writeData, 10000); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private void readData(byte[] readData) throws RemoteException { SerialPort.getInstance().read(readData, 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(byte[] writeData) throws RemoteException {
/*  68 */     if (isOpened()) {
/*     */       try {
/*  70 */         SerialPort.getInstance().write(writeData, 0);
/*     */       }
/*  72 */       catch (Exception exception) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void d(String tag, String msg) {
/*  81 */
                    try {
                        if (isOpened()) {
                               try {
                                 msg = tag + ":" + msg + "\r\n";
                                 SerialPort.getInstance().write(msg.getBytes(), 0);
                               }
                               catch (Exception exception) {}
                             }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
            }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean open() throws RemoteException {
/*  95 */     if (isConnectBase()) {
/*  96 */       String deviceName = getDeviceName(new String[] { "ttyUSB", "ttyACM" });
/*  97 */       if (deviceName == null) {
/*  98 */         return false;
/*     */       }
/*     */       
/* 101 */       SerialPort.getInstance().open(deviceName);
/*     */     } else {
/* 103 */       SerialPort.getInstance().open("USBD");
/*     */     } 
/*     */     
/* 106 */     SerialPort.getInstance().init(9, 78, 8);
/*     */     
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getDeviceName(String... prefixes) {
/* 115 */     File dev = new File("/dev");
/* 116 */     for (File file : dev.listFiles()) {
/* 117 */       for (String prefix : prefixes) {
/* 118 */         if (file.getAbsolutePath().startsWith("/dev/" + prefix)) {
/* 119 */           return file.toString().substring(5);
/*     */         }
/*     */       } 
/*     */     } 
/* 123 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isConnectBase() throws RemoteException {
/* 130 */     File dev = new File("/sys/bus/usb/devices/1-1.1");
/* 131 */     return dev.exists();
/*     */   }
/*     */ 
/*     */   
/* 135 */   public boolean isOpened() throws RemoteException { return this.status; }
/*     */ }


/* Location:              /Users/MAC/Documents/classes.jar!/com/arke/sdk/util/epms/SerialLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */