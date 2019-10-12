package com.efulltech.efull_nibss_bridge.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.efulltech.efull_nibss_bridge.Transaction;
import com.efulltech.efull_nibss_bridge.utils.Constants.TransactEntry;

import java.util.ArrayList;
import java.util.List;


public class TransactionDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = Constants.DB_NAME;
    public static final int DATABASE_VERSION = 1;

    public TransactionDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE "+TransactEntry.TABLE_NAME+" (" +
                "_id INTEGER PRIMARY KEY," +
                TransactEntry.APP_NAME+" TEXT," +
                TransactEntry.DOMAIN_NAME+" TEXT," +
                TransactEntry.BATCH_NO+" TEXT," +
                TransactEntry.SEQ_NO+" TEXT," +
                TransactEntry.STAN+" TEXT," +
                TransactEntry.TERMINAL_ID+" TEXT," +
                TransactEntry.MERCHANT_ID+" TEXT," +
                TransactEntry.FROM_AC+" INTEGER," +
                TransactEntry.TO_AC+" INTEGER," +
                TransactEntry.TRANS_TYPE+" INTEGER," +
                TransactEntry.TRANS_NAME+" TEXT," +
                TransactEntry.PAN+" TEXT," +
                TransactEntry.AMOUNT+" TEXT," +
                TransactEntry.CASHBACK+" TEXT," +
                TransactEntry.EXPIRY+" TEXT," +
                TransactEntry.MCC+" TEXT," +
                TransactEntry.ICC_DATA+" TEXT," +
                TransactEntry.PAN_SEQ_NO+" TEXT," +
                TransactEntry.BIN+" TEXT," +
                TransactEntry.TRACK1+" TEXT," +
                TransactEntry.TRACK2+" TEXT," +
                TransactEntry.TRACK3+" TEXT," +
                TransactEntry.REF_NO+" TEXT," +
                TransactEntry.SERVICE_CODE+" TEXT," +
                TransactEntry.MERCHANT_NAME+" TEXT," +
                TransactEntry.CURRENCY_CODE+" TEXT," +
                TransactEntry.PIN_BLOCK+" TEXT," +
                TransactEntry.MTI+" TEXT," +
                TransactEntry.DATE_TIME+" TEXT," +
                TransactEntry.S_AMOUNT+" TEXT," +
                TransactEntry.T_FEE+" TEXT," +
                TransactEntry.S_FEE+" TEXT," +
                TransactEntry.AID+" TEXT," +
                TransactEntry.CARD_HOLDER_NAME+" TEXT," +
                TransactEntry.LABEL+" TEXT," +
                TransactEntry.TVR+" TEXT," +
                TransactEntry.TSI+" TEXT," +
                TransactEntry.AC+" TEXT," +
                TransactEntry.DATE+" TEXT," +
                TransactEntry.TIME+" TEXT," +
                TransactEntry.STATUS+" TEXT," +
                TransactEntry.RESPONSE_CODE+" TEXT," +
                TransactEntry.RESPONSE_MESSGAE+" TEXT," +
                TransactEntry.AUTH_ID+" TEXT," +
                TransactEntry.MODE+" INTEGER," +
                TransactEntry.BALANCE+" TEXT," +
                TransactEntry.DELETED+" INTEGER," +
                TransactEntry.INSTITUTION_ID+" TEXT," +
                TransactEntry.LOCAL_TIME+" TEXT," +
                TransactEntry.LOCAL_DATE+" TEXT)";

        sqLiteDatabase.execSQL(CREATE_TRANSACTIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TransactEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public List<Transaction> listTransactions() {
        String sql = "SELECT * FROM "+TransactEntry.TABLE_NAME+" ORDER BY _id DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        List<Transaction> savedTransactions = new ArrayList();
        Cursor cursor = db.rawQuery(sql, (String[])null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String appname = cursor.getString(cursor.getColumnIndex(TransactEntry.APP_NAME));
                String domainname = cursor.getString(cursor.getColumnIndex(TransactEntry.DOMAIN_NAME));
                String batchno = cursor.getString(cursor.getColumnIndex(TransactEntry.BATCH_NO));
                String seqno = cursor.getString(cursor.getColumnIndex(TransactEntry.SEQ_NO));
                String stan = cursor.getString(cursor.getColumnIndex(TransactEntry.STAN));
                String terminalid = cursor.getString(cursor.getColumnIndex(TransactEntry.TERMINAL_ID));
                String marchantid = cursor.getString(cursor.getColumnIndex(TransactEntry.MERCHANT_ID));
                int fromac = cursor.getInt(cursor.getColumnIndex(TransactEntry.FROM_AC));
                int toac = cursor.getInt(cursor.getColumnIndex(TransactEntry.TO_AC));
                int transtype = cursor.getInt(cursor.getColumnIndex(TransactEntry.TRANS_TYPE));
                String transname = cursor.getString(cursor.getColumnIndex(TransactEntry.TRANS_NAME));
                String pan = cursor.getString(cursor.getColumnIndex(TransactEntry.PAN));
                String amount = cursor.getString(cursor.getColumnIndex(TransactEntry.AMOUNT));
                String cashback = cursor.getString(cursor.getColumnIndex(TransactEntry.CASHBACK));
                String expiry = cursor.getString(cursor.getColumnIndex(TransactEntry.EXPIRY));
                String mcc = cursor.getString(cursor.getColumnIndex(TransactEntry.MCC));
                String iccdata = cursor.getString(cursor.getColumnIndex(TransactEntry.ICC_DATA));
                String panseqno = cursor.getString(cursor.getColumnIndex(TransactEntry.PAN_SEQ_NO));
                String bin = cursor.getString(cursor.getColumnIndex(TransactEntry.BIN));
                String track1 = cursor.getString(cursor.getColumnIndex(TransactEntry.TRACK1));
                String track2 = cursor.getString(cursor.getColumnIndex(TransactEntry.TRACK2));
                String track3 = cursor.getString(cursor.getColumnIndex(TransactEntry.TRACK3));
                String refno = cursor.getString(cursor.getColumnIndex(TransactEntry.REF_NO));
                String servicecode = cursor.getString(cursor.getColumnIndex(TransactEntry.SERVICE_CODE));
                String marchant = cursor.getString(cursor.getColumnIndex(TransactEntry.MERCHANT_NAME));
                String currencycode = cursor.getString(cursor.getColumnIndex(TransactEntry.CURRENCY_CODE));
                String pinblock = cursor.getString(cursor.getColumnIndex(TransactEntry.PIN_BLOCK));
                String mti = cursor.getString(cursor.getColumnIndex(TransactEntry.MTI));
                String datetime = cursor.getString(cursor.getColumnIndex(TransactEntry.DATE_TIME));
                String samount = cursor.getString(cursor.getColumnIndex(TransactEntry.S_AMOUNT));
                String tfee = cursor.getString(cursor.getColumnIndex(TransactEntry.T_FEE));
                String sfee = cursor.getString(cursor.getColumnIndex(TransactEntry.S_FEE));
                String aid = cursor.getString(cursor.getColumnIndex(TransactEntry.AID));
                String cardholdername = cursor.getString(cursor.getColumnIndex(TransactEntry.CARD_HOLDER_NAME));
                String label = cursor.getString(cursor.getColumnIndex(TransactEntry.LABEL));
                String tvr = cursor.getString(cursor.getColumnIndex(TransactEntry.TVR));
                String tsi = cursor.getString(cursor.getColumnIndex(TransactEntry.TSI));
                String ac = cursor.getString(cursor.getColumnIndex(TransactEntry.AC));
                String date = cursor.getString(cursor.getColumnIndex(TransactEntry.DATE));
                String time = cursor.getString(cursor.getColumnIndex(TransactEntry.TIME));
                String status = cursor.getString(cursor.getColumnIndex(TransactEntry.STATUS));
                String responsecode = cursor.getString(cursor.getColumnIndex(TransactEntry.RESPONSE_CODE));
                String responsemessage = cursor.getString(cursor.getColumnIndex(TransactEntry.RESPONSE_MESSGAE));
                String authid = cursor.getString(cursor.getColumnIndex(TransactEntry.AUTH_ID));
                int mode = cursor.getInt(cursor.getColumnIndex(TransactEntry.MODE));
                String balance = cursor.getString(cursor.getColumnIndex(TransactEntry.BALANCE));
                int deleted = cursor.getInt(cursor.getColumnIndex(TransactEntry.DELETED));
                String institutionid = cursor.getString(cursor.getColumnIndex(TransactEntry.INSTITUTION_ID));
                String localtime = cursor.getString(cursor.getColumnIndex(TransactEntry.LOCAL_TIME));
                String localdate = cursor.getString(cursor.getColumnIndex(TransactEntry.LOCAL_DATE));
                savedTransactions.add(new Transaction(id, appname, domainname, batchno, seqno, stan, terminalid, marchantid, fromac, toac, transtype, transname, pan, amount, cashback, expiry, mcc, iccdata, panseqno, bin, track1, track2, track3, refno, servicecode, marchant, currencycode, pinblock, mti, datetime, samount, tfee, sfee, aid, cardholdername, label, tvr, tsi, ac, date, time, status, responsecode, responsemessage, authid, mode, balance, deleted, institutionid, localtime, localdate));
            } while(cursor.moveToNext());
        }
        cursor.close();
        return savedTransactions;
    }


    public void saveEftTransaction(Transaction transaction) {
        ContentValues values = new ContentValues();
        values.put(TransactEntry.APP_NAME, transaction.getAppName());
        values.put(TransactEntry.DOMAIN_NAME, transaction.getDomainName());
        values.put(TransactEntry.BATCH_NO, transaction.getBatchNo());
        values.put(TransactEntry.SEQ_NO, transaction.getSeqNo());
        values.put(TransactEntry.STAN, transaction.getStan());
        values.put(TransactEntry.TERMINAL_ID, transaction.getTerminalId());
        values.put(TransactEntry.MERCHANT_ID, transaction.getMerchantId());
        values.put(TransactEntry.FROM_AC, transaction.getFromAc());
        values.put(TransactEntry.TO_AC, transaction.getToAc());
        values.put(TransactEntry.TRANS_TYPE, transaction.getTransType());
        values.put(TransactEntry.TRANS_NAME, transaction.getTransName());
        values.put(TransactEntry.PAN, transaction.getPan());
        values.put(TransactEntry.AMOUNT, transaction.getAmount());
        values.put(TransactEntry.CASHBACK, transaction.getCashBack());
        values.put(TransactEntry.EXPIRY, transaction.getExpiry());
        values.put(TransactEntry.MCC, transaction.getMcc());
        values.put(TransactEntry.ICC_DATA, transaction.getIccData());
        values.put(TransactEntry.PAN_SEQ_NO, transaction.getPanSeqno());
        values.put(TransactEntry.BIN, transaction.getBin());
        values.put(TransactEntry.TRACK1, transaction.getTrack1());
        values.put(TransactEntry.TRACK2, transaction.getTrack2());
        values.put(TransactEntry.TRACK3, transaction.getTrack3());
        values.put(TransactEntry.REF_NO, transaction.getRefNo());
        values.put(TransactEntry.SERVICE_CODE, transaction.getServiceCode());
        values.put(TransactEntry.MERCHANT_NAME, transaction.getMerchantName());
        values.put(TransactEntry.CURRENCY_CODE, transaction.getCurrencyCode());
        values.put(TransactEntry.PIN_BLOCK, transaction.getPinBlock());
        values.put(TransactEntry.MTI, transaction.getMti());
        values.put(TransactEntry.DATE_TIME, transaction.getDateTime());
        values.put(TransactEntry.S_AMOUNT, transaction.getsAmount());
        values.put(TransactEntry.T_FEE, transaction.gettFee());
        values.put(TransactEntry.S_FEE, transaction.getsFee());
        values.put(TransactEntry.AID, transaction.getAid());
        values.put(TransactEntry.CARD_HOLDER_NAME, transaction.getCardHolderName());
        values.put(TransactEntry.LABEL, transaction.getLabel());
        values.put(TransactEntry.TVR, transaction.getTvr());
        values.put(TransactEntry.TSI, transaction.getTsi());
        values.put(TransactEntry.AC, transaction.getAc());
        values.put(TransactEntry.DATE, transaction.getDate());
        values.put(TransactEntry.TIME, transaction.getTime());
        values.put(TransactEntry.STATUS, transaction.getStatus());
        values.put(TransactEntry.RESPONSE_CODE, transaction.getResponseCode());
        values.put(TransactEntry.RESPONSE_MESSGAE, transaction.getResponseMessage());
        values.put(TransactEntry.AUTH_ID, transaction.getAuthId());
        values.put(TransactEntry.MODE, transaction.getMode());
        values.put(TransactEntry.BALANCE, transaction.getBalance());
        values.put(TransactEntry.DELETED, transaction.getDeleted());
        values.put(TransactEntry.INSTITUTION_ID, transaction.getInstitutionId());
        values.put(TransactEntry.LOCAL_TIME, transaction.getLocalTime());
        values.put(TransactEntry.LOCAL_DATE, transaction.getLocalDate());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TransactEntry.TABLE_NAME, null, values);
    }

    public void updateEftTransaction(Transaction transaction) {
        ContentValues values = new ContentValues();
        values.put(TransactEntry.DATE_TIME, transaction.getDateTime());
        values.put(TransactEntry.STATUS, transaction.getStatus());
        values.put(TransactEntry.RESPONSE_CODE, transaction.getResponseCode());
        values.put(TransactEntry.RESPONSE_MESSGAE, transaction.getResponseMessage());
        values.put(TransactEntry.ICC_DATA, transaction.getIccData());
        values.put(TransactEntry.REF_NO, transaction.getRefNo());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TransactEntry.TABLE_NAME, values, TransactEntry.STAN+"  = ?", new String[]{transaction.getStan()});
    }


    public Transaction getEftTransaction(String seqnumber) {
        String query = "Select * FROM "+TransactEntry.TABLE_NAME+" WHERE "+TransactEntry.REF_NO+" = '" + seqnumber + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Transaction transaction = null;
        Cursor cursor = db.rawQuery(query, (String[])null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String appname = cursor.getString(cursor.getColumnIndex(TransactEntry.APP_NAME));
            String domainname = cursor.getString(cursor.getColumnIndex(TransactEntry.DOMAIN_NAME));
            String batchno = cursor.getString(cursor.getColumnIndex(TransactEntry.BATCH_NO));
            String seqno = cursor.getString(cursor.getColumnIndex(TransactEntry.SEQ_NO));
            String stan = cursor.getString(cursor.getColumnIndex(TransactEntry.STAN));
            String terminalid = cursor.getString(cursor.getColumnIndex(TransactEntry.TERMINAL_ID));
            String marchantid = cursor.getString(cursor.getColumnIndex(TransactEntry.MERCHANT_ID));
            int fromac = cursor.getInt(cursor.getColumnIndex(TransactEntry.FROM_AC));
            int toac = cursor.getInt(cursor.getColumnIndex(TransactEntry.TO_AC));
            int transtype = cursor.getInt(cursor.getColumnIndex(TransactEntry.TRANS_TYPE));
            String transname = cursor.getString(cursor.getColumnIndex(TransactEntry.TRANS_NAME));
            String pan = cursor.getString(cursor.getColumnIndex(TransactEntry.PAN));
            String amount = cursor.getString(cursor.getColumnIndex(TransactEntry.AMOUNT));
            String cashback = cursor.getString(cursor.getColumnIndex(TransactEntry.CASHBACK));
            String expiry = cursor.getString(cursor.getColumnIndex(TransactEntry.EXPIRY));
            String mcc = cursor.getString(cursor.getColumnIndex(TransactEntry.MCC));
            String iccdata = cursor.getString(cursor.getColumnIndex(TransactEntry.ICC_DATA));
            String panseqno = cursor.getString(cursor.getColumnIndex(TransactEntry.PAN_SEQ_NO));
            String bin = cursor.getString(cursor.getColumnIndex(TransactEntry.BIN));
            String track1 = cursor.getString(cursor.getColumnIndex(TransactEntry.TRACK1));
            String track2 = cursor.getString(cursor.getColumnIndex(TransactEntry.TRACK2));
            String track3 = cursor.getString(cursor.getColumnIndex(TransactEntry.TRACK3));
            String refno = cursor.getString(cursor.getColumnIndex(TransactEntry.REF_NO));
            String servicecode = cursor.getString(cursor.getColumnIndex(TransactEntry.SERVICE_CODE));
            String marchant = cursor.getString(cursor.getColumnIndex(TransactEntry.MERCHANT_NAME));
            String currencycode = cursor.getString(cursor.getColumnIndex(TransactEntry.CURRENCY_CODE));
            String pinblock = cursor.getString(cursor.getColumnIndex(TransactEntry.PIN_BLOCK));
            String mti = cursor.getString(cursor.getColumnIndex(TransactEntry.MTI));
            String datetime = cursor.getString(cursor.getColumnIndex(TransactEntry.DATE_TIME));
            String samount = cursor.getString(cursor.getColumnIndex(TransactEntry.S_AMOUNT));
            String tfee = cursor.getString(cursor.getColumnIndex(TransactEntry.T_FEE));
            String sfee = cursor.getString(cursor.getColumnIndex(TransactEntry.S_FEE));
            String aid = cursor.getString(cursor.getColumnIndex(TransactEntry.AID));
            String cardholdername = cursor.getString(cursor.getColumnIndex(TransactEntry.CARD_HOLDER_NAME));
            String label = cursor.getString(cursor.getColumnIndex(TransactEntry.LABEL));
            String tvr = cursor.getString(cursor.getColumnIndex(TransactEntry.TVR));
            String tsi = cursor.getString(cursor.getColumnIndex(TransactEntry.TSI));
            String ac = cursor.getString(cursor.getColumnIndex(TransactEntry.AC));
            String date = cursor.getString(cursor.getColumnIndex(TransactEntry.DATE));
            String time = cursor.getString(cursor.getColumnIndex(TransactEntry.TIME));
            String status = cursor.getString(cursor.getColumnIndex(TransactEntry.STATUS));
            String responsecode = cursor.getString(cursor.getColumnIndex(TransactEntry.RESPONSE_CODE));
            String responsemessage = cursor.getString(cursor.getColumnIndex(TransactEntry.RESPONSE_MESSGAE));
            String authid = cursor.getString(cursor.getColumnIndex(TransactEntry.AUTH_ID));
            int mode = cursor.getInt(cursor.getColumnIndex(TransactEntry.MODE));
            String balance = cursor.getString(cursor.getColumnIndex(TransactEntry.BALANCE));
            int deleted = cursor.getInt(cursor.getColumnIndex(TransactEntry.DELETED));
            String institutionid = cursor.getString(cursor.getColumnIndex(TransactEntry.INSTITUTION_ID));
            String localtime = cursor.getString(cursor.getColumnIndex(TransactEntry.LOCAL_TIME));
            String localdate = cursor.getString(cursor.getColumnIndex(TransactEntry.LOCAL_DATE));
            transaction = new Transaction(id, appname, domainname, batchno, seqno, stan, terminalid, marchantid, fromac, toac, transtype, transname, pan, amount, cashback, expiry, mcc, iccdata, panseqno, bin, track1, track2, track3, refno, servicecode, marchant, currencycode, pinblock, mti, datetime, samount, tfee, sfee, aid, cardholdername, label, tvr, tsi, ac, date, time, status, responsecode, responsemessage, authid, mode, balance, deleted, institutionid, localtime, localdate);
        }
        cursor.close();

        return transaction;
    }

    public void deleteEftTransaction(int id) {
        // DELETES A TRANSACTION WRT ID
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TransactEntry.TABLE_NAME, "_id    = ?", new String[]{String.valueOf(id)});
    }

    public void deleteEftTransaction(String stan) {
        // DELETES A TRANSACTION WRT STAN
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TransactEntry.TABLE_NAME, TransactEntry.STAN+" = ?", new String[]{stan});
    }

    public void deleteEftTransaction() {
        //DELETES ALL TRANSACTION RECORDS
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TransactEntry.TABLE_NAME, (String)null, (String[])null);
    }
}
