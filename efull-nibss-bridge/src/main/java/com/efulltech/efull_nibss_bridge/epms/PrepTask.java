/*     */ package com.efulltech.efull_nibss_bridge.epms;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.app.ProgressDialog;
/*     */ import android.content.SharedPreferences;
/*     */ import android.os.AsyncTask;
/*     */ import android.preference.PreferenceManager;
/*     */ import android.util.Log;
/*     */ import android.widget.Toast;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import javax.crypto.SecretKey;
/*     */ 
/*     */ public class PrepTask
/*     */   extends AsyncTask<Void, Integer, Boolean> {
/*     */   private static final String TAG = "PrepTask";
/*     */   private ProgressDialog dialog;
/*     */   private Activity activity;
/*     */   SharedPreferences settings;
/*     */   
/*     */   public PrepTask(Activity activity) {
/*  26 */     this.settings = null;
/*     */ 
/*     */     
/*  29 */     this.dialog = new ProgressDialog(activity);
/*  30 */     this.settings = PreferenceManager.getDefaultSharedPreferences(activity);
/*  31 */     this.activity = activity;
/*     */   }
/*     */   
/*     */   protected void onPreExecute() {
/*  35 */     this.dialog.setMessage("Processing...");
/*  36 */     this.dialog.show();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Boolean doInBackground(Void... params) {
/*     */     try {
/*  45 */       String terminalid = this.settings.getString("terminalid", "");
/*  46 */       String host = this.settings.getString("hostip", "");
/*  47 */       String port = this.settings.getString("hostport", "");
/*  48 */       String protocol = this.settings.getString("protocol", "");
/*  49 */       String key1 = this.settings.getString("key1", "");
/*  50 */       String key2 = this.settings.getString("key2", "");
                String fieldXMLReq = this.settings.getString("isoFieldXMLReq", "");
                String fieldXMLRes = this.settings.getString("isoFieldXMLRes", "");
/*     */
/*  52 */       Log.d("PrepTask", "key1:" + key1);
/*  53 */       Log.d("PrepTask", "key2:" + key2);
/*     */ 
/*     */       
/*  56 */       Log.d("PrepTask", "master key doawload");
/*  57 */       String response = Initiate.masterKeyDownload(fieldXMLReq, fieldXMLRes, host, port, protocol, "123456", terminalid, "123456789");
/*  58 */       Log.d("PrepTask", "response : " + response);
/*     */       
/*  60 */       if (response == null) {
/*  61 */         return Boolean.valueOf(false);
/*     */       }
/*     */       
/*  64 */       JsonElement jsonElement = (new JsonParser()).parse(response);
/*  65 */       JsonObject jobject = jsonElement.getAsJsonObject();
/*  66 */       String f39 = jobject.getAsJsonPrimitive("39").getAsString();
/*  67 */       Log.d("PrepTask", "f39 : " + f39);
/*     */       
/*  69 */       if (f39 != null && f39.equals("00")) {
/*  70 */         String f53 = jobject.getAsJsonPrimitive("53").getAsString();
/*  71 */         String emk = f53.substring(0, 32);
/*  72 */         String kcv = f53.substring(32, 38);
/*  73 */         Log.d("PrepTask", "f53 : " + f53);
/*  74 */         Log.d("PrepTask", "emk : " + emk);
/*  75 */         Log.d("PrepTask", "kcv : " + kcv);
/*     */         
/*  77 */         byte[] keyB1 = Crypto.hexToByte(key1 + key1.substring(0, 16));
/*  78 */         byte[] keyB2 = Crypto.hexToByte(key2 + key2.substring(0, 16));
/*  79 */         byte[] keyB3 = new byte[keyB1.length];
/*     */         
/*  81 */         for (int i = 0; i < keyB1.length; i++) {
/*  82 */           keyB3[i] = (byte)(keyB1[i] ^ keyB2[i]);
/*     */         }
/*     */         
/*  85 */         SecretKey key = Crypto.read3DESKey(keyB3);
/*  86 */         String dmk = Crypto.Decrypt3DES(key, emk);
/*  87 */         Log.d("PrepTask", "dmk : " + dmk);
/*     */         
/*  89 */         SharedPreferences.Editor editor = this.settings.edit();
/*  90 */         editor.putString("mkey", dmk);
/*  91 */         editor.commit();
/*     */       }
/*     */       else {
/*     */         
/*  95 */         return Boolean.valueOf(false);
/*     */       } 
/*     */ 
/*     */       
/*  99 */       Log.d("PrepTask", "session key download");
/* 100 */       response = Initiate.sessionKeyDownload(fieldXMLReq, fieldXMLRes, host, port, protocol, "123456", terminalid, "123456789");
/* 101 */       Log.d("PrepTask", "response : " + response);
/*     */       
/* 103 */       if (response == null) {
/* 104 */         return Boolean.valueOf(false);
/*     */       }
/*     */       
/* 107 */       jsonElement = (new JsonParser()).parse(response);
/* 108 */       jobject = jsonElement.getAsJsonObject();
/* 109 */       f39 = jobject.getAsJsonPrimitive("39").getAsString();
/* 110 */       Log.d("PrepTask", "f39 : " + f39);
/*     */       
/* 112 */       if (f39 != null && f39.equals("00")) {
/* 113 */         String f53 = jobject.getAsJsonPrimitive("53").getAsString();
/* 114 */         String esk = f53.substring(0, 32);
/* 115 */         String kcv = f53.substring(32, 38);
/* 116 */         Log.d("PrepTask", "f53 : " + f53);
/* 117 */         Log.d("PrepTask", "esk : " + esk);
/* 118 */         Log.d("PrepTask", "kcv : " + kcv);
/*     */         
/* 120 */         String mkey = this.settings.getString("mkey", "");
/* 121 */         byte[] keyB1 = Crypto.hexToByte(mkey + mkey.substring(0, 16));
/*     */         
/* 123 */         SecretKey key = Crypto.read3DESKey(keyB1);
/* 124 */         String dsk = Crypto.Decrypt3DES(key, esk);
/* 125 */         Log.d("PrepTask", "dsk : " + dsk);
/*     */         
/* 127 */         SharedPreferences.Editor editor = this.settings.edit();
/* 128 */         editor.putString("skey", dsk);
/* 129 */         editor.commit();
/*     */       }
/*     */       else {
/*     */         
/* 133 */         return Boolean.valueOf(false);
/*     */       } 
/*     */ 
/*     */       
/* 137 */       Log.d("PrepTask", "pin key downlaod");
/* 138 */       response = Initiate.pinKeyDownload(fieldXMLReq, fieldXMLRes, host, port, protocol, "123456", terminalid, "123456789");
/* 139 */       Log.d("PrepTask", "response : " + response);
/*     */       
/* 141 */       if (response == null) {
/* 142 */         return Boolean.valueOf(false);
/*     */       }
/*     */       
/* 145 */       jsonElement = (new JsonParser()).parse(response);
/* 146 */       jobject = jsonElement.getAsJsonObject();
/* 147 */       f39 = jobject.getAsJsonPrimitive("39").getAsString();
/* 148 */       Log.d("PrepTask", "f39 : " + f39);
/*     */       
/* 150 */       if (f39 != null && f39.equals("00")) {
/* 151 */         String f53 = jobject.getAsJsonPrimitive("53").getAsString();
/* 152 */         String epk = f53.substring(0, 32);
/* 153 */         String kcv = f53.substring(32, 38);
/* 154 */         Log.d("PrepTask", "f53 : " + f53);
/* 155 */         Log.d("PrepTask", "epk : " + epk);
/* 156 */         Log.d("PrepTask", "kcv : " + kcv);
/*     */         
/* 158 */         String mkey = this.settings.getString("mkey", "");
/* 159 */         byte[] keyB1 = Crypto.hexToByte(mkey + mkey.substring(0, 16));
/*     */         
/* 161 */         SecretKey key = Crypto.read3DESKey(keyB1);
/* 162 */         String dpk = Crypto.Decrypt3DES(key, epk);
/* 163 */         Log.d("PrepTask", "dsk : " + dpk);
/*     */         
/* 165 */         SharedPreferences.Editor editor = this.settings.edit();
/* 166 */         editor.putString("pkey", dpk);
/* 167 */         editor.putString("epkey", epk);
/* 168 */         editor.putString("epkeykcv", kcv);
/* 169 */         editor.commit();
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 174 */         return Boolean.valueOf(false);
/*     */       } 
/*     */ 
/*     */       
/* 178 */       Log.d("PrepTask", "parameter download");
/* 179 */       String skey = this.settings.getString("skey", "");
/* 180 */       Log.d("PrepTask", "parameter download:skey:" + skey);
/*     */ 
/*     */       
/* 183 */       response = Initiate.parametersDownload(fieldXMLReq, fieldXMLRes, host, port, protocol, "123456", terminalid, "123456789", skey);
/* 184 */       Log.d("PrepTask", "response : " + response);
/*     */       
/* 186 */       if (response == null) {
/* 187 */         return Boolean.valueOf(false);
/*     */       }
/*     */       
/* 190 */       jsonElement = (new JsonParser()).parse(response);
/* 191 */       jobject = jsonElement.getAsJsonObject();
/* 192 */       f39 = jobject.getAsJsonPrimitive("39").getAsString();
/* 193 */       Log.d("PrepTask", "f39 : " + f39);
/*     */       
/* 195 */       if (f39 != null && f39.equals("00")) {
/* 196 */         String f62 = jobject.getAsJsonPrimitive("62").getAsString();
/* 197 */         Log.d("PrepTask", "f62 : " + f62);
/*     */ 
/*     */ 
/*     */         
/* 201 */         while (!f62.isEmpty() && (f62.startsWith("02") || f62.startsWith("03") || f62.startsWith("04") || f62.startsWith("05") || f62.startsWith("06") || f62.startsWith("07") || f62.startsWith("08") || f62.startsWith("52")))
/*     */         {
/*     */ 
/*     */           
/* 205 */           if (f62.startsWith("02")) {
/* 206 */             int index = f62.indexOf("02");
/* 207 */             String len = f62.substring(index + 2, index + 2 + 3);
/*     */             
/* 209 */             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
/* 210 */             Log.d("PrepTask", "CTMS Date and Time : " + value);
/*     */             
/* 212 */             SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
/*     */             try {
/* 214 */               Date date = sdf.parse(value);
/* 215 */               Calendar calendar = Calendar.getInstance();
/* 216 */               calendar.setTime(date);
/* 217 */               Log.d("PrepTask", "Set Date and Time : " + calendar.getTime());
/* 218 */             } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */             
/* 222 */             f62 = f62.replace("02" + len + value, "");
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 227 */           if (f62.startsWith("03")) {
/* 228 */             int index = f62.indexOf("03");
/* 229 */             String len = f62.substring(index + 2, index + 2 + 3);
/*     */             
/* 231 */             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
/* 232 */             Log.d("PrepTask", "Card Acceptor Identification Code : " + value);
/*     */             
/* 234 */             SharedPreferences.Editor editor = this.settings.edit();
/* 235 */             editor.putString("merchantid", value);
/* 236 */             editor.commit();
/*     */ 
/*     */             
/* 239 */             f62 = f62.replace("03" + len + value, "");
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 244 */           if (f62.startsWith("04")) {
/* 245 */             int index = f62.indexOf("04");
/* 246 */             String len = f62.substring(index + 2, index + 2 + 3);
/*     */             
/* 248 */             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
/* 249 */             Log.d("PrepTask", "Receive Timeout : " + value);
/*     */             
/* 251 */             SharedPreferences.Editor editor = this.settings.edit();
/* 252 */             editor.putString("rtimeout", value);
/* 253 */             editor.commit();
/*     */ 
/*     */             
/* 256 */             f62 = f62.replace("04" + len + value, "");
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 261 */           if (f62.startsWith("05")) {
/* 262 */             int index = f62.indexOf("05");
/* 263 */             String len = f62.substring(index + 2, index + 2 + 3);
/*     */             
/* 265 */             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
/* 266 */             Log.d("PrepTask", "Currency Code : " + value);
/*     */             
/* 268 */             SharedPreferences.Editor editor = this.settings.edit();
/* 269 */             editor.putString("currencycode", value);
/* 270 */             editor.commit();
/*     */ 
/*     */             
/* 273 */             f62 = f62.replace("05" + len + value, "");
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 278 */           if (f62.startsWith("06")) {
/* 279 */             int index = f62.indexOf("06");
/* 280 */             String len = f62.substring(index + 2, index + 2 + 3);
/*     */             
/* 282 */             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
/* 283 */             Log.d("PrepTask", "Country Code : " + value);
/*     */             
/* 285 */             SharedPreferences.Editor editor = this.settings.edit();
/* 286 */             editor.putString("countrycode", value);
/* 287 */             editor.commit();
/*     */ 
/*     */             
/* 290 */             f62 = f62.replace("06" + len + value, "");
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 295 */           if (f62.startsWith("07")) {
/* 296 */             int index = f62.indexOf("07");
/* 297 */             String len = f62.substring(index + 2, index + 2 + 3);
/*     */             
/* 299 */             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
/* 300 */             Log.d("PrepTask", "Callhome Timer : " + value);
/*     */             
/* 302 */             SharedPreferences.Editor editor = this.settings.edit();
/* 303 */             editor.putString("callhome", value);
/* 304 */             editor.commit();
/*     */ 
/*     */             
/* 307 */             f62 = f62.replace("07" + len + value, "");
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 312 */           if (f62.startsWith("52")) {
/* 313 */             int index = f62.indexOf("52");
/* 314 */             String len = f62.substring(index + 2, index + 2 + 3);
/*     */             
/* 316 */             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
/* 317 */             Log.d("PrepTask", "Merchant Name and Location : " + value);
/*     */             
/* 319 */             SharedPreferences.Editor editor = this.settings.edit();
/* 320 */             editor.putString("merchant", value);
/* 321 */             editor.commit();
/*     */ 
/*     */             
/* 324 */             f62 = f62.replace("52" + len + value, "");
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 329 */           if (f62.startsWith("08")) {
/* 330 */             int index = f62.indexOf("08");
/* 331 */             String len = f62.substring(index + 2, index + 2 + 3);
/*     */             
/* 333 */             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
/* 334 */             Log.d("PrepTask", "Merchant Category Code : " + value);
/*     */             
/* 336 */             SharedPreferences.Editor editor = this.settings.edit();
/* 337 */             editor.putString("mcc", value);
/* 338 */             editor.commit();
/*     */ 
/*     */             
/* 341 */             f62 = f62.replace("08" + len + value, "");
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 350 */         return Boolean.valueOf(false);
/*     */       } 
/*     */       
/* 353 */       return Boolean.valueOf(true);
/* 354 */     } catch (Exception e) {
/* 355 */       Log.d("PrepTask", e.getMessage());
/*     */ 
/*     */       
/* 358 */       return Boolean.valueOf(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onPostExecute(Boolean result) {
/* 364 */     if (this.dialog.isShowing()) {
/* 365 */       this.dialog.dismiss();
/*     */     }
/*     */     
/* 368 */     if (result.booleanValue()) {
/*     */       
/* 370 */       this.dialog.setMessage("PREP OK");
/* 371 */       Toast.makeText(this.activity, "PREP OK", Toast.LENGTH_SHORT).show();
/*     */     }
/*     */     else {
/*     */       
/* 375 */       this.dialog.setMessage("PREP FAILED");
/* 376 */       Toast.makeText(this.activity, "PREP FAILED", Toast.LENGTH_SHORT).show();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/MAC/Documents/classes.jar!/com/arke/sdk/util/epms/PrepTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */