package com.efulltech.efull_nibss_bridge.utils;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


import java.util.ArrayList;
import java.util.List;

import com.efulltech.efull_nibss_bridge.Transaction;


public class mainDatabase extends SQLiteOpenHelper {

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor mEditor;


    public mainDatabase(Context context) {
        super(context, Constants.DB_NAME, (CursorFactory)null, 5);
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE TransactEntry (_id INTEGER PRIMARY KEY,batchno TEXT,seqno TEXT,stan TEXT,terminalid TEXT,marchantid TEXT,fromac INTEGER,toac INTEGER,transtype INTEGER,transname TEXT,pan TEXT,amount TEXT,cashback TEXT,expiry TEXT,mcc TEXT,iccdata TEXT,panseqno TEXT,bin TEXT,track1 TEXT,track2 TEXT,track3 TEXT,refno TEXT,servicecode TEXT,marchant TEXT,currencycode TEXT,pinblock TEXT,mti TEXT,datetime TEXT,smount TEXT,tfee TEXT,sfee TEXT,aid TEXT,cardholdername TEXT,label TEXT,tvr TEXT,tsi TEXT,ac TEXT,date TEXT,time TEXT,status TEXT,code TEXT,message TEXT,authid TEXT,mode INTEGER,balance TEXT,deleted INTEGER,institutionid TEXT,localtime TEXT,localdate TEXT)";
        db.execSQL(CREATE_PRODUCTS_TABLE);
//        create table to store additional data about the transaction
        String CREATE_TRANSACTION_DATA_TABLE = "CREATE TABLE transData (ref_no TEXT PRIMARY KEY, app_name TEXT, app_domain_name TEXT)";
        db.execSQL(CREATE_TRANSACTION_DATA_TABLE);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS eft");
        this.onCreate(db);
    }

    public List<Transaction> listTransactions() {
        String sql = "select * from eft order by _id desc";
        SQLiteDatabase db = this.getReadableDatabase();
        List<Transaction> storeProducts = new ArrayList();
        Cursor cursor = db.rawQuery(sql, (String[])null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String batchno = cursor.getString(1);
                String seqno = cursor.getString(2);
                String stan = cursor.getString(3);
                String terminalid = cursor.getString(4);
                String marchantid = cursor.getString(5);
                int fromac = cursor.getInt(6);
                int toac = cursor.getInt(7);
                int transtype = cursor.getInt(8);
                String transname = cursor.getString(9);
                String pan = cursor.getString(10);
                String amount = cursor.getString(11);
                String cashback = cursor.getString(12);
                String expiry = cursor.getString(13);
                String mcc = cursor.getString(14);
                String iccdata = cursor.getString(15);
                String panseqno = cursor.getString(16);
                String bin = cursor.getString(17);
                String track1 = cursor.getString(18);
                String track2 = cursor.getString(19);
                String track3 = cursor.getString(20);
                String refno = cursor.getString(21);
                String servicecode = cursor.getString(22);
                String marchant = cursor.getString(23);
                String currencycode = cursor.getString(24);
                String pinblock = cursor.getString(25);
                String mti = cursor.getString(26);
                String datetime = cursor.getString(27);
                String samount = cursor.getString(28);
                String tfee = cursor.getString(29);
                String sfee = cursor.getString(30);
                String aid = cursor.getString(31);
                String cardholdername = cursor.getString(32);
                String label = cursor.getString(33);
                String tvr = cursor.getString(34);
                String tsi = cursor.getString(35);
                String ac = cursor.getString(36);
                String date = cursor.getString(37);
                String time = cursor.getString(38);
                String status = cursor.getString(39);
                String responsecode = cursor.getString(40);
                String responsemessage = cursor.getString(41);
                String authid = cursor.getString(42);
                int mode = cursor.getInt(43);
                String balance = cursor.getString(44);
                int deleted = cursor.getInt(45);
                String institutionid = cursor.getString(46);
                String localtime = cursor.getString(47);
                String localdate = cursor.getString(48);
//                storeProducts.add(new Transaction(id, batchno, seqno, stan, terminalid, marchantid, fromac, toac, transtype, transname, pan, amount, cashback, expiry, mcc, iccdata, panseqno, bin, track1, track2, track3, refno, servicecode, marchant, currencycode, pinblock, mti, datetime, samount, tfee, sfee, aid, cardholdername, label, tvr, tsi, ac, date, time, status, responsecode, responsemessage, authid, mode, balance, deleted, institutionid, localtime, localdate));
            } while(cursor.moveToNext());
        }

        cursor.close();
        return storeProducts;
    }

    public void saveEftTransaction(Transaction product) {
        ContentValues values = new ContentValues();
        values.put("batchno", product.getBatchNo());
        values.put("seqno", product.getSeqNo());
        values.put("stan", product.getStan());
        values.put("terminalid", product.getTerminalId());
        values.put("marchantid", product.getMerchantId());
        values.put("fromac", product.getFromAc());
        values.put("toac", product.getToAc());
        values.put("transtype", product.getTransType());
        values.put("transname", product.getTransName());
        values.put("pan", product.getPan());
        values.put("amount", product.getAmount());
        values.put("cashback", product.getCashBack());
        values.put("expiry", product.getExpiry());
        values.put("mcc", product.getMcc());
        values.put("iccdata", product.getIccData());
        values.put("panseqno", product.getPanSeqno());
        values.put("bin", product.getBin());
        values.put("track1", product.getTrack1());
        values.put("track2", product.getTrack2());
        values.put("track3", product.getTrack3());
        values.put("refno", product.getRefNo());
        values.put("servicecode", product.getServiceCode());
        values.put("marchant", product.getMerchantName());
        values.put("currencycode", product.getCurrencyCode());
        values.put("pinblock", product.getPinBlock());
        values.put("mti", product.getMti());
        values.put("datetime", product.getDateTime());
        values.put("smount", product.getsAmount());
        values.put("tfee", product.gettFee());
        values.put("sfee", product.getsFee());
        values.put("aid", product.getAid());
        values.put("cardholdername", product.getCardHolderName());
        values.put("label", product.getLabel());
        values.put("tvr", product.getTvr());
        values.put("tsi", product.getTsi());
        values.put("ac", product.getAc());
        values.put("date", product.getDate());
        values.put("time", product.getTime());
        values.put("status", product.getStatus());
        values.put("code", product.getResponseCode());
        values.put("message", product.getResponseMessage());
        values.put("authid", product.getAuthId());
        values.put("mode", product.getMode());
        values.put("balance", product.getBalance());
        values.put("deleted", product.getDeleted());
        values.put("institutionid", product.getInstitutionId());
        values.put("localtime", product.getLocalTime());
        values.put("localdate", product.getLocalDate());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("eft", (String)null, values);
    }

    public void updateEftTransaction(Transaction product) {
        ContentValues values = new ContentValues();
        values.put("datetime", product.getDateTime());
        values.put("status", product.getStatus());
        values.put("code", product.getResponseCode());
        values.put("message", product.getResponseMessage());
        values.put("iccdata", product.getIccData());
        values.put("refno", product.getRefNo());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update("eft", values, "stan    = ?", new String[]{product.getStan()});
    }

    public Transaction getEftTransaction(String seqnumber) {
        String query = "Select * FROM eft WHERE refno = '" + seqnumber + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Transaction mProduct = null;
        Cursor cursor = db.rawQuery(query, (String[])null);
        if (cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(0));
            String batchno = cursor.getString(1);
            String seqno = cursor.getString(2);
            String stan = cursor.getString(3);
            String terminalid = cursor.getString(4);
            String marchantid = cursor.getString(5);
            int fromac = cursor.getInt(6);
            int toac = cursor.getInt(7);
            int transtype = cursor.getInt(8);
            String transname = cursor.getString(9);
            String pan = cursor.getString(10);
            String amount = cursor.getString(11);
            String cashback = cursor.getString(12);
            String expiry = cursor.getString(13);
            String mcc = cursor.getString(14);
            String iccdata = cursor.getString(15);
            String panseqno = cursor.getString(16);
            String bin = cursor.getString(17);
            String track1 = cursor.getString(18);
            String track2 = cursor.getString(19);
            String track3 = cursor.getString(20);
            String refno = cursor.getString(21);
            String servicecode = cursor.getString(22);
            String marchant = cursor.getString(23);
            String currencycode = cursor.getString(24);
            String pinblock = cursor.getString(25);
            String mti = cursor.getString(26);
            String datetime = cursor.getString(27);
            String samount = cursor.getString(28);
            String tfee = cursor.getString(29);
            String sfee = cursor.getString(30);
            String aid = cursor.getString(31);
            String cardholdername = cursor.getString(32);
            String label = cursor.getString(33);
            String tvr = cursor.getString(34);
            String tsi = cursor.getString(35);
            String ac = cursor.getString(36);
            String date = cursor.getString(37);
            String time = cursor.getString(38);
            String status = cursor.getString(39);
            String responsecode = cursor.getString(40);
            String responsemessage = cursor.getString(41);
            String authid = cursor.getString(42);
            int mode = cursor.getInt(43);
            String balance = cursor.getString(44);
            int deleted = cursor.getInt(45);
            String institutionid = cursor.getString(46);
            String localtime = cursor.getString(47);
            String localdate = cursor.getString(48);
//            mProduct = new Transaction(id, batchno, seqno, stan, terminalid, marchantid, fromac, toac, transtype, transname, pan, amount, cashback, expiry, mcc, iccdata, panseqno, bin, track1, track2, track3, refno, servicecode, marchant, currencycode, pinblock, mti, datetime, samount, tfee, sfee, aid, cardholdername, label, tvr, tsi, ac, date, time, status, responsecode, responsemessage, authid, mode, balance, deleted, institutionid, localtime, localdate);
        }

        cursor.close();

        return mProduct;
    }

    public String getTransactionOrigin(String seqnumber) {
        String query = "Select * FROM transData WHERE ref_no = '" + seqnumber + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        String appName = "Terminal";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            if(cursor.moveToFirst()) {
                do{
                    appName = cursor.getString(cursor.getColumnIndex("app_name"));
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        return appName;
    }

    public void saveTransactionOrigin(String refNo, String appName) {
        ContentValues values = new ContentValues();
        values.put("app_name", appName);
        values.put("ref_no", refNo);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("transData", (String)null, values);
    }

    public void deleteEftTransaction(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("eft", "_id    = ?", new String[]{String.valueOf(id)});
    }

    public void deleteEftTransaction(String stan) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("eft", "stan    = ?", new String[]{stan});
    }

    public void deleteEftTransaction() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("eft", (String)null, (String[])null);
    }
}
