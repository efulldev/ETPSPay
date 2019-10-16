/*    */ package com.efulltech.efull_nibss_bridge.epms;
/*    */ 
/*    */ import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
/*    */ import android.database.sqlite.SQLiteException;
/*    */ import android.util.Log;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Database
/*    */ {
/*    */   private static final String TAG = "Database";
/*    */   
/*    */   public static SQLiteDatabase openTransactionsDB(String path, String dbname) throws IOException {
/* 25 */     boolean dbExist = checkTransactionsDB(path, dbname);
/* 26 */     SQLiteDatabase db = null;
/* 27 */     if (dbExist) {
/*    */       
/* 29 */       Log.d("Database", "db exist!");
/* 30 */       return SQLiteDatabase.openDatabase(path + dbname, null, 0);
/*    */     } 
/*    */     
/*    */     try {
/* 34 */       File dir = new File(path);
/* 35 */       if (!dir.exists()) {
/* 36 */         dir.mkdirs();
/*    */       }
/* 38 */       File dbf = new File(path + dbname);
/* 39 */       if (dbf.exists()) {
/* 40 */         dbf.delete();
/*    */       }
/* 42 */       db = SQLiteDatabase.openOrCreateDatabase(dbf, null);
/* 43 */       db.execSQL("CREATE TABLE IF NOT EXISTS eft (batchno VARCHAR(6), seqno VARCHAR(6), stan VARCHAR(6), terminalid VARCHAR(8), marchantid VARCHAR(15), fromac INT, toac INT, transtype INT, transname VARCHAR(100), pan VARCHAR(50), amount VARCHAR(12), cashback VARCHAR(12), expiry VARCHAR(6), mcc VARCHAR(6), iccdata VARCHAR(512), panseqno VARCHAR(6), bin VARCHAR(6), track1 VARCHAR(256), track2 VARCHAR(256), track3 VARCHAR(256), refno VARCHAR(12), servicecode VARCHAR(6), merchant VARCHAR(500), currencycode VARCHAR(6), pinblock VARCHAR(25), mti VARCHAR(6), rdatetime VARCHAR(25), samount VARCHAR(12), tfee VARCHAR(12), sfee VARCHAR(12), aid VARCHAR(50), cardholdername VARCHAR(100), label VARCHAR(50), tvr VARCHAR(20), tsi VARCHAR(20), ac VARCHAR(50), date VARCHAR(25), time VARCHAR(25), deleted INT, rstatus VARCHAR(5), rcode VARCHAR(5), rmessage VARCHAR(100))");
/*    */ 
/*    */       
/* 46 */       Log.d("Database", "new db created");
/* 47 */     } catch (Exception e) {
/* 48 */       Log.d("Database", "Exception:" + e.getMessage());
/* 49 */       return null;
/*    */     } 
/*    */     
/* 52 */     return db;
/*    */   }
/*    */   
/*    */   @SuppressLint("WrongConstant")
            public static boolean checkTransactionsDB(String path, String dbname) {
/* 56 */     SQLiteDatabase checkDB = null;
/* 57 */     String myPath = path + dbname;
/*    */     try {
/* 59 */       checkDB = SQLiteDatabase.openDatabase(myPath, null, 1);
/* 60 */     } catch (SQLiteException e) {
/*    */       
/* 62 */       Log.d("Database", "database does't exist yet");
/* 63 */       Log.d("Database", "Exception:" + e.getMessage());
/*    */     } 
/* 65 */     if (checkDB != null) {
/* 66 */       checkDB.close();
/*    */     }
/* 68 */     return (checkDB != null);
/*    */   }
/*    */   
/*    */   public static boolean deleteTransactionsDB(String path, String dbname) {
/* 72 */     boolean checkDB = false;
/*    */     try {
/* 74 */       File dbf = new File(path + dbname);
/* 75 */       if (dbf.exists()) {
/* 76 */         checkDB = SQLiteDatabase.deleteDatabase(dbf);
/*    */       }
/*    */     }
/* 79 */     catch (SQLiteException sQLiteException) {}
/*    */     
/* 81 */     return checkDB;
/*    */   }
/*    */ }


/* Location:              /Users/MAC/Documents/classes.jar!/com/arke/sdk/util/epms/Database.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */