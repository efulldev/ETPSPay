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
/*     */ import javax.crypto.SecretKey;
/*     */ 
/*     */ 
/*     */ public class NewKeysTask
/*     */   extends AsyncTask<Void, Integer, Boolean>
/*     */ {
/*     */   private static final String TAG = "PrepTask";
/*     */   private ProgressDialog dialog;
/*     */   private Activity activity;
/*     */   SharedPreferences settings;
/*     */   
/*     */   public NewKeysTask(Activity activity) {
/*  25 */     this.settings = null;
/*     */ 
/*     */     
/*  28 */     this.dialog = new ProgressDialog(activity);
/*  29 */     this.settings = PreferenceManager.getDefaultSharedPreferences(activity);
/*  30 */     this.activity = activity;
/*     */   }
/*     */   
/*     */   protected void onPreExecute() {
/*  34 */     this.dialog.setMessage("Processing...");
/*  35 */     this.dialog.show();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Boolean doInBackground(Void... params) {
/*     */     try {
/*  44 */       String terminalid = this.settings.getString("terminalid", "20390015");
/*  45 */       String host = this.settings.getString("hostip", "196.6.103.72");
/*  46 */       String port = this.settings.getString("hostport", "5042");
/*  47 */       String protocol = this.settings.getString("protocol", "0");
/*  48 */       String key1 = this.settings.getString("key1", "5D25072F04832A2329D93E4F91BA23A2");
/*  49 */       String key2 = this.settings.getString("key2", "86CBCDE3B0A22354853E04521686863D");
                String fieldXMLReq = this.settings.getString("isoFieldXMLReq", "");
                String fieldXMLRes = this.settings.getString("isoFieldXMLRes", "");
/*     */
/*  51 */       Log.d("PrepTask", "key1:" + key1);
/*  52 */       Log.d("PrepTask", "key2:" + key2);
/*     */ 
/*     */       
/*  55 */       Log.d("PrepTask", "master key download");
/*  56 */       String response = Initiate.masterKeyDownload(fieldXMLReq, fieldXMLRes, host, port, protocol, "123456", terminalid, "123456789");
/*  57 */       Log.d("PrepTask", "response : " + response);
/*     */       
/*  59 */       if (response == null) {
/*  60 */         return Boolean.valueOf(false);
/*     */       }
/*     */       
/*  63 */       JsonElement jsonElement = (new JsonParser()).parse(response);
/*  64 */       JsonObject jobject = jsonElement.getAsJsonObject();
/*  65 */       String f39 = jobject.getAsJsonPrimitive("39").getAsString();
/*  66 */       Log.d("PrepTask", "f39 : " + f39);
/*     */       
/*  68 */       if (f39 != null && f39.equals("00")) {
/*  69 */         String f53 = jobject.getAsJsonPrimitive("53").getAsString();
/*  70 */         String emk = f53.substring(0, 32);
/*  71 */         String kcv = f53.substring(32, 38);
/*  72 */         Log.d("PrepTask", "f53 : " + f53);
/*  73 */         Log.d("PrepTask", "emk : " + emk);
/*  74 */         Log.d("PrepTask", "kcv : " + kcv);
/*     */         
/*  76 */         byte[] keyB1 = Crypto.hexToByte(key1 + key1.substring(0, 16));
/*  77 */         byte[] keyB2 = Crypto.hexToByte(key2 + key2.substring(0, 16));
/*  78 */         byte[] keyB3 = new byte[keyB1.length];
/*     */         
/*  80 */         for (int i = 0; i < keyB1.length; i++) {
/*  81 */           keyB3[i] = (byte)(keyB1[i] ^ keyB2[i]);
/*     */         }
/*     */         
/*  84 */         SecretKey key = Crypto.read3DESKey(keyB3);
/*  85 */         String dmk = Crypto.Decrypt3DES(key, emk);
/*  86 */         Log.d("PrepTask", "dmk : " + dmk);
/*     */         
/*  88 */         SharedPreferences.Editor editor = this.settings.edit();
/*  89 */         editor.putString("mkey", dmk);
/*  90 */         editor.commit();
/*     */       }
/*     */       else {
/*     */         
/*  94 */         return Boolean.valueOf(false);
/*     */       } 
/*     */ 
/*     */       
/*  98 */       Log.d("PrepTask", "session key download");

/*  99 */       response = Initiate.sessionKeyDownload(fieldXMLReq, fieldXMLRes, host, port, protocol, "123456", terminalid, "123456789");



/* 100 */       Log.d("PrepTask", "response : " + response);
/*     */       
/* 102 */       if (response == null) {
/* 103 */         return Boolean.valueOf(false);
/*     */       }
/*     */       
/* 106 */       jsonElement = (new JsonParser()).parse(response);
/* 107 */       jobject = jsonElement.getAsJsonObject();
/* 108 */       f39 = jobject.getAsJsonPrimitive("39").getAsString();
/* 109 */       Log.d("PrepTask", "f39 : " + f39);
/*     */       
/* 111 */       if (f39 != null && f39.equals("00")) {
/* 112 */         String f53 = jobject.getAsJsonPrimitive("53").getAsString();
/* 113 */         String esk = f53.substring(0, 32);
/* 114 */         String kcv = f53.substring(32, 38);
/* 115 */         Log.d("PrepTask", "f53 : " + f53);
/* 116 */         Log.d("PrepTask", "esk : " + esk);
/* 117 */         Log.d("PrepTask", "kcv : " + kcv);
/*     */         
/* 119 */         String mkey = this.settings.getString("mkey", "");
/* 120 */         byte[] keyB1 = Crypto.hexToByte(mkey + mkey.substring(0, 16));
/*     */         
/* 122 */         SecretKey key = Crypto.read3DESKey(keyB1);
/* 123 */         String dsk = Crypto.Decrypt3DES(key, esk);
/* 124 */         Log.d("PrepTask", "dsk : " + dsk);
/*     */         
/* 126 */         SharedPreferences.Editor editor = this.settings.edit();
/* 127 */         editor.putString("skey", dsk);
/* 128 */         editor.commit();
/*     */       }
/*     */       else {
/*     */         
/* 132 */         return Boolean.valueOf(false);
/*     */       } 
/*     */ 
/*     */       
/* 136 */       Log.d("PrepTask", "pin key downlaod");
/* 137 */       response = Initiate.pinKeyDownload(fieldXMLReq,fieldXMLRes, host, port, protocol, "123456", terminalid, "123456789");
/* 138 */       Log.d("PrepTask", "response : " + response);
/*     */       
/* 140 */       if (response == null) {
/* 141 */         return Boolean.valueOf(false);
/*     */       }
/*     */       
/* 144 */       jsonElement = (new JsonParser()).parse(response);
/* 145 */       jobject = jsonElement.getAsJsonObject();
/* 146 */       f39 = jobject.getAsJsonPrimitive("39").getAsString();
/* 147 */       Log.d("PrepTask", "f39 : " + f39);
/*     */       
/* 149 */       if (f39 != null && f39.equals("00")) {
/* 150 */         String f53 = jobject.getAsJsonPrimitive("53").getAsString();
/* 151 */         String epk = f53.substring(0, 32);
/* 152 */         String kcv = f53.substring(32, 38);
/* 153 */         Log.d("PrepTask", "f53 : " + f53);
/* 154 */         Log.d("PrepTask", "epk : " + epk);
/* 155 */         Log.d("PrepTask", "kcv : " + kcv);
/*     */         
/* 157 */         String mkey = this.settings.getString("mkey", "");
/* 158 */         byte[] keyB1 = Crypto.hexToByte(mkey + mkey.substring(0, 16));
/*     */         
/* 160 */         SecretKey key = Crypto.read3DESKey(keyB1);
/* 161 */         String dpk = Crypto.Decrypt3DES(key, epk);
/* 162 */         Log.d("PrepTask", "dsk : " + dpk);
/*     */         
/* 164 */         SharedPreferences.Editor editor = this.settings.edit();
/* 165 */         editor.putString("pkey", dpk);
/* 166 */         editor.putString("epkey", epk);
/* 167 */         editor.putString("epkeykcv", kcv);
/* 168 */         editor.commit();
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 173 */         return Boolean.valueOf(false);
/*     */       } 
/*     */       
/* 176 */       return Boolean.valueOf(true);
/* 177 */     } catch (Exception e) {
/* 178 */       Log.d("PrepTask", e.getMessage());
/*     */ 
/*     */       
/* 181 */       return Boolean.valueOf(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onPostExecute(Boolean result) {
/* 187 */     if (this.dialog.isShowing()) {
/* 188 */       this.dialog.dismiss();
/*     */     }
/*     */     
/* 191 */     if (result.booleanValue()) {
/*     */       
/* 193 */       this.dialog.setMessage("NEW KEYS DOWNLOAD OK");
/* 194 */       Toast.makeText(this.activity, "NEW KEYS DOWNLOAD OK", Toast.LENGTH_SHORT).show();
/*     */     }
/*     */     else {
/*     */       
/* 198 */       this.dialog.setMessage("NEW KEYS DOWNLOAD FAILED");
/* 199 */       Toast.makeText(this.activity, "NEW KEYS DOWNLOAD FAILED", Toast.LENGTH_SHORT).show();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/MAC/Documents/classes.jar!/com/arke/sdk/util/epms/NewKeysTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */