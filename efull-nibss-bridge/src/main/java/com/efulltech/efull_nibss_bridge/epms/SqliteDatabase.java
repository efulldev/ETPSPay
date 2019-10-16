/*     */ package com.efulltech.efull_nibss_bridge.epms;
/*     */ 
/*     */ import android.content.ContentValues;
/*     */ import android.content.Context;
/*     */ import android.database.Cursor;
/*     */ import android.database.sqlite.SQLiteDatabase;
/*     */ import android.database.sqlite.SQLiteOpenHelper;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SqliteDatabase
/*     */   extends SQLiteOpenHelper
/*     */ {
/*  16 */   public SqliteDatabase(Context context) { super(context, "besldb.db", null, 5); }
/*     */ 
/*     */   
/*     */   public void onCreate(SQLiteDatabase db) {
/*  20 */     String CREATE_PRODUCTS_TABLE = "CREATE    TABLE eft(_id INTEGER PRIMARY KEY,batchno TEXT,seqno TEXT,stan TEXT,terminalid TEXT,marchantid TEXT,fromac INTEGER,toac INTEGER,transtype INTEGER,transname TEXT,pan TEXT,amount TEXT,cashback TEXT,expiry TEXT,mcc TEXT,iccdata TEXT,panseqno TEXT,bin TEXT,track1 TEXT,track2 TEXT,track3 TEXT,refno TEXT,servicecode TEXT,marchant TEXT,currencycode TEXT,pinblock TEXT,mti TEXT,datetime TEXT,smount TEXT,tfee TEXT,sfee TEXT,aid TEXT,cardholdername TEXT,label TEXT,tvr TEXT,tsi TEXT,ac TEXT,date TEXT,time TEXT,status TEXT,code TEXT,message TEXT,authid TEXT,mode INTEGER,balance TEXT,deleted INTEGER,institutionid TEXT,localtime TEXT,localdate TEXT)";
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
/*     */     
/*  74 */     db.execSQL(CREATE_PRODUCTS_TABLE);
/*     */   }
/*     */   
/*     */   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
/*  78 */     db.execSQL("DROP TABLE IF EXISTS eft");
/*  79 */     onCreate(db);
/*     */   }
/*     */   public List<Transaction> listTransactions() {
/*  82 */     String sql = "select * from eft";
/*  83 */     SQLiteDatabase db = getReadableDatabase();
/*  84 */     List<Transaction> storeProducts = new ArrayList<Transaction>();
/*  85 */     Cursor cursor = db.rawQuery(sql, null);
/*  86 */     if (cursor.moveToFirst()) {
/*     */       do {
/*  88 */         int id = Integer.parseInt(cursor.getString(0));
/*  89 */         String batchno = cursor.getString(1);
/*  90 */         String seqno = cursor.getString(2);
/*  91 */         String stan = cursor.getString(3);
/*  92 */         String terminalid = cursor.getString(4);
/*  93 */         String marchantid = cursor.getString(5);
/*  94 */         int fromac = cursor.getInt(6);
/*  95 */         int toac = cursor.getInt(7);
/*  96 */         int transtype = cursor.getInt(8);
/*  97 */         String transname = cursor.getString(9);
/*  98 */         String pan = cursor.getString(10);
/*  99 */         String amount = cursor.getString(11);
/* 100 */         String cashback = cursor.getString(12);
/* 101 */         String expiry = cursor.getString(13);
/* 102 */         String mcc = cursor.getString(14);
/* 103 */         String iccdata = cursor.getString(15);
/* 104 */         String panseqno = cursor.getString(16);
/* 105 */         String bin = cursor.getString(17);
/* 106 */         String track1 = cursor.getString(18);
/* 107 */         String track2 = cursor.getString(19);
/* 108 */         String track3 = cursor.getString(20);
/* 109 */         String refno = cursor.getString(21);
/* 110 */         String servicecode = cursor.getString(22);
/* 111 */         String marchant = cursor.getString(23);
/* 112 */         String currencycode = cursor.getString(24);
/* 113 */         String pinblock = cursor.getString(25);
/* 114 */         String mti = cursor.getString(26);
/* 115 */         String datetime = cursor.getString(27);
/* 116 */         String samount = cursor.getString(28);
/* 117 */         String tfee = cursor.getString(29);
/* 118 */         String sfee = cursor.getString(30);
/* 119 */         String aid = cursor.getString(31);
/* 120 */         String cardholdername = cursor.getString(32);
/* 121 */         String label = cursor.getString(33);
/* 122 */         String tvr = cursor.getString(34);
/* 123 */         String tsi = cursor.getString(35);
/* 124 */         String ac = cursor.getString(36);
/* 125 */         String date = cursor.getString(37);
/* 126 */         String time = cursor.getString(38);
/* 127 */         String status = cursor.getString(39);
/* 128 */         String responsecode = cursor.getString(40);
/* 129 */         String responsemessage = cursor.getString(41);
/* 130 */         String authid = cursor.getString(42);
/* 131 */         int mode = cursor.getInt(43);
/* 132 */         String balance = cursor.getString(44);
/* 133 */         int deleted = cursor.getInt(45);
/* 134 */         String institutionid = cursor.getString(46);
/* 135 */         String localtime = cursor.getString(47);
/* 136 */         String localdate = cursor.getString(48);
/*     */         
/* 138 */         storeProducts.add(new Transaction(id, batchno, seqno, stan, terminalid, marchantid, fromac, toac, transtype, transname, pan, amount, cashback, expiry, mcc, iccdata, panseqno, bin, track1, track2, track3, refno, servicecode, marchant, currencycode, pinblock, mti, datetime, samount, tfee, sfee, aid, cardholdername, label, tvr, tsi, ac, date, time, status, responsecode, responsemessage, authid, mode, balance, deleted, institutionid, localtime, localdate));
/* 139 */       } while (cursor.moveToNext());
/*     */     }
/* 141 */     cursor.close();
/* 142 */     return storeProducts;
/*     */   }
/*     */   public void saveEftTransaction(Transaction product) {
/* 145 */     ContentValues values = new ContentValues();
/*     */     
/* 147 */     values.put("batchno", product.getBatchno());
/* 148 */     values.put("seqno", product.getSeqno());
/* 149 */     values.put("stan", product.getStan());
/* 150 */     values.put("terminalid", product.getTerminalid());
/* 151 */     values.put("marchantid", product.getMarchantid());
/* 152 */     values.put("fromac", Integer.valueOf(product.getFromac()));
/* 153 */     values.put("toac", Integer.valueOf(product.getToac()));
/* 154 */     values.put("transtype", Integer.valueOf(product.getTranstype()));
/* 155 */     values.put("transname", product.getTransname());
/* 156 */     values.put("pan", product.getPan());
/* 157 */     values.put("amount", product.getAmount());
/* 158 */     values.put("cashback", product.getCashback());
/* 159 */     values.put("expiry", product.getExpiry());
/* 160 */     values.put("mcc", product.getMcc());
/* 161 */     values.put("iccdata", product.getIccdata());
/* 162 */     values.put("panseqno", product.getPanseqno());
/* 163 */     values.put("bin", product.getBin());
/* 164 */     values.put("track1", product.getTrack1());
/* 165 */     values.put("track2", product.getTrack2());
/* 166 */     values.put("track3", product.getTrack3());
/* 167 */     values.put("refno", product.getRefno());
/* 168 */     values.put("servicecode", product.getServicecode());
/* 169 */     values.put("marchant", product.getMarchant());
/* 170 */     values.put("currencycode", product.getCurrencycode());
/* 171 */     values.put("pinblock", product.getPinblock());
/* 172 */     values.put("mti", product.getMti());
/* 173 */     values.put("datetime", product.getDatetime());
/* 174 */     values.put("smount", product.getSamount());
/* 175 */     values.put("tfee", product.getTfee());
/* 176 */     values.put("sfee", product.getSfee());
/* 177 */     values.put("aid", product.getAid());
/* 178 */     values.put("cardholdername", product.getCardholdername());
/* 179 */     values.put("label", product.getLabel());
/* 180 */     values.put("tvr", product.getTvr());
/* 181 */     values.put("tsi", product.getTsi());
/* 182 */     values.put("ac", product.getAc());
/* 183 */     values.put("date", product.getDate());
/* 184 */     values.put("time", product.getTime());
/* 185 */     values.put("status", product.getStatus());
/* 186 */     values.put("code", product.getResponsecode());
/* 187 */     values.put("message", product.getResponsemessage());
/* 188 */     values.put("authid", product.getAuthid());
/* 189 */     values.put("mode", Integer.valueOf(product.getMode()));
/* 190 */     values.put("balance", product.getBalance());
/* 191 */     values.put("deleted", Integer.valueOf(product.getDeleted()));
/* 192 */     values.put("deleted", Integer.valueOf(product.getDeleted()));
/* 193 */     values.put("institutionid", product.getInstitutionid());
/* 194 */     values.put("localtime", product.getLocaltime());
/* 195 */     values.put("localdate", product.getLocaldate());
/*     */     
/* 197 */     SQLiteDatabase db = getWritableDatabase();
/* 198 */     db.insert("eft", null, values);
/*     */   }
/*     */   public void updateEftTransaction(Transaction product) {
/* 201 */     ContentValues values = new ContentValues();
/* 202 */     values.put("datetime", product.getDatetime());
/* 203 */     values.put("status", product.getStatus());
/* 204 */     values.put("code", product.getResponsecode());
/* 205 */     values.put("message", product.getResponsemessage());
/* 206 */     values.put("iccdata", product.getIccdata());
/* 207 */     values.put("refno", product.getRefno());
/* 208 */     SQLiteDatabase db = getWritableDatabase();
/* 209 */     db.update("eft", values, "stan    = ?", new String[] { product.getStan() });
/*     */   }
/*     */   public Transaction getEftTransaction(String seqnumber) {
/* 212 */     String query = "Select * FROM eft WHERE refno = '" + seqnumber + "'";
/*     */     
/* 214 */     SQLiteDatabase db = getWritableDatabase();
/* 215 */     Transaction mProduct = null;
/* 216 */     Cursor cursor = db.rawQuery(query, null);
/* 217 */     if (cursor.moveToFirst()) {
/* 218 */       int id = Integer.parseInt(cursor.getString(0));
/* 219 */       String batchno = cursor.getString(1);
/* 220 */       String seqno = cursor.getString(2);
/* 221 */       String stan = cursor.getString(3);
/* 222 */       String terminalid = cursor.getString(4);
/* 223 */       String marchantid = cursor.getString(5);
/* 224 */       int fromac = cursor.getInt(6);
/* 225 */       int toac = cursor.getInt(7);
/* 226 */       int transtype = cursor.getInt(8);
/* 227 */       String transname = cursor.getString(9);
/* 228 */       String pan = cursor.getString(10);
/* 229 */       String amount = cursor.getString(11);
/* 230 */       String cashback = cursor.getString(12);
/* 231 */       String expiry = cursor.getString(13);
/* 232 */       String mcc = cursor.getString(14);
/* 233 */       String iccdata = cursor.getString(15);
/* 234 */       String panseqno = cursor.getString(16);
/* 235 */       String bin = cursor.getString(17);
/* 236 */       String track1 = cursor.getString(18);
/* 237 */       String track2 = cursor.getString(19);
/* 238 */       String track3 = cursor.getString(20);
/* 239 */       String refno = cursor.getString(21);
/* 240 */       String servicecode = cursor.getString(22);
/* 241 */       String marchant = cursor.getString(23);
/* 242 */       String currencycode = cursor.getString(24);
/* 243 */       String pinblock = cursor.getString(25);
/* 244 */       String mti = cursor.getString(26);
/* 245 */       String datetime = cursor.getString(27);
/* 246 */       String samount = cursor.getString(28);
/* 247 */       String tfee = cursor.getString(29);
/* 248 */       String sfee = cursor.getString(30);
/* 249 */       String aid = cursor.getString(31);
/* 250 */       String cardholdername = cursor.getString(32);
/* 251 */       String label = cursor.getString(33);
/* 252 */       String tvr = cursor.getString(34);
/* 253 */       String tsi = cursor.getString(35);
/* 254 */       String ac = cursor.getString(36);
/* 255 */       String date = cursor.getString(37);
/* 256 */       String time = cursor.getString(38);
/* 257 */       String status = cursor.getString(39);
/* 258 */       String responsecode = cursor.getString(40);
/* 259 */       String responsemessage = cursor.getString(41);
/* 260 */       String authid = cursor.getString(42);
/* 261 */       int mode = cursor.getInt(43);
/* 262 */       String balance = cursor.getString(44);
/* 263 */       int deleted = cursor.getInt(45);
/* 264 */       String institutionid = cursor.getString(46);
/* 265 */       String localtime = cursor.getString(47);
/* 266 */       String localdate = cursor.getString(48);
/*     */       
/* 268 */       mProduct = new Transaction(id, batchno, seqno, stan, terminalid, marchantid, fromac, toac, transtype, transname, pan, amount, cashback, expiry, mcc, iccdata, panseqno, bin, track1, track2, track3, refno, servicecode, marchant, currencycode, pinblock, mti, datetime, samount, tfee, sfee, aid, cardholdername, label, tvr, tsi, ac, date, time, status, responsecode, responsemessage, authid, mode, balance, deleted, institutionid, localtime, localdate);
/*     */     } 
/* 270 */     cursor.close();
/* 271 */     return mProduct;
/*     */   }
/*     */   public void deleteEftTransaction(int id) {
/* 274 */     SQLiteDatabase db = getWritableDatabase();
/* 275 */     db.delete("eft", "_id    = ?", new String[] { String.valueOf(id) });
/*     */   }
/*     */   
/*     */   public void deleteEftTransaction(String stan) {
/* 279 */     SQLiteDatabase db = getWritableDatabase();
/* 280 */     db.delete("eft", "stan    = ?", new String[] { stan });
/*     */   }
/*     */   
/*     */   public void deleteEftTransaction() {
/* 284 */     SQLiteDatabase db = getWritableDatabase();
/* 285 */     db.delete("eft", null, null);
/*     */   }
/*     */ }


/* Location:              /Users/MAC/Documents/classes.jar!/com/arke/sdk/util/epms/SqliteDatabase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */