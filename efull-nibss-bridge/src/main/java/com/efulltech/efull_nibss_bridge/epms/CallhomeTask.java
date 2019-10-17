/*     */ package com.efulltech.efull_nibss_bridge.epms;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.app.ProgressDialog;
/*     */ import android.content.SharedPreferences;
/*     */ import android.os.AsyncTask;
/*     */ import android.preference.PreferenceManager;
/*     */ import android.util.Log;
/*     */ import android.widget.Toast;
///*     */ import com.arke.sdk.api.PinpadForMKSK;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CallhomeTask
/*     */   extends AsyncTask<Void, Integer, Boolean>
/*     */ {
/*     */   private static final String TAG = "CallhomeTask";
/*     */   private ProgressDialog dialog;
/*     */   private Activity activity;
/*     */   SharedPreferences settings;
/*     */   
/*     */   public CallhomeTask(Activity activity) {
/*  26 */     this.settings = null;
/*     */ 
/*     */     
/*  29 */     this.dialog = new ProgressDialog(activity);
/*  30 */     this.dialog.setCancelable(false);
/*  31 */     this.settings = PreferenceManager.getDefaultSharedPreferences(activity);
/*  32 */     this.activity = activity;
/*     */   }
/*     */   
/*     */   protected void onPreExecute() {
/*  36 */     this.dialog.setMessage("Calling Home...");
/*  37 */     this.dialog.show();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Boolean doInBackground(Void... params) {
/*     */     try {
/*  46 */       String terminalid = this.settings.getString("terminalid", "20390015");
/*  45 */       String host = this.settings.getString("hostip", "196.6.103.72");
/*  46 */       String port = this.settings.getString("hostport", "5042");
/*  47 */       String protocol = this.settings.getString("protocol", "0");
/*  48 */       String key1 = this.settings.getString("key1", "5D25072F04832A2329D93E4F91BA23A2");
/*  49 */       String key2 = this.settings.getString("key2", "86CBCDE3B0A22354853E04521686863D");
                String fieldXMLReq = this.settings.getString("isoFieldXMLReq", "");
                String fieldXMLRes = this.settings.getString("isoFieldXMLRes", "");
/*     */
/*  51 */       String stan = GetUniqueKey(6);
/*     */ 
/*     */       
/*  54 */       Log.d("CallhomeTask", "callhome");
/*  55 */       String response = Initiate.callhome(fieldXMLReq, fieldXMLRes, host, port, protocol, stan, terminalid, "123456789");
/*  56 */       Log.d("CallhomeTask", "response : " + response);
/*     */       
/*  58 */       if (response == null) {
/*  59 */         return Boolean.valueOf(false);
/*     */       }
/*     */       
/*  62 */       JsonElement jsonElement = (new JsonParser()).parse(response);
/*  63 */       JsonObject jobject = jsonElement.getAsJsonObject();
/*  64 */       String f39 = jobject.getAsJsonPrimitive("39").getAsString();
/*  65 */       Log.d("CallhomeTask", "f39 : " + f39);
/*     */       
/*  67 */       if (f39 != null && f39.equals("00"))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  74 */         return Boolean.valueOf(true); }  return Boolean.valueOf(false);
/*  75 */     } catch (Exception e) {
/*  76 */       Log.d("CallhomeTask", e.getMessage());
/*     */ 
/*     */       
/*  79 */       return Boolean.valueOf(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String GetUniqueKey(int len8digitslenMax) {
/*  88 */     byte[] data = new byte[8];
/*     */ 
/*     */     
/*  91 */     String digits = "1234567890";
/*     */ 
/*     */ 
/*     */     
///*     */     try {
///*  96 */       PinpadForMKSK.getInstance().open();
///*     */
///*  98 */       data = PinpadForMKSK.getInstance().getRandom(8);
///*  99 */       data = PinpadForMKSK.getInstance().getRandom(8);
///*     */
///* 101 */       PinpadForMKSK.getInstance().close();
///*     */     }
///* 103 */     catch (Exception ex) {
///* 104 */       Log.d("CallhomeTask", "Exception : " + ex.getMessage());
///*     */     }
/*     */     
/* 107 */     String result = "";
/*     */     
/* 109 */     for (int b = 0; b < len8digitslenMax; b++) {
/*     */       
/* 111 */       int index = data[b] % digits.length();
/*     */       
/* 113 */       if (index < 0) {
/* 114 */         index *= -1;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 119 */       String tmp = String.format("%c", new Object[] { Character.valueOf(digits.charAt(index)) });
/*     */       
/* 121 */       result = result + tmp;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onPostExecute(Boolean result) {
/* 134 */     if (this.dialog.isShowing()) {
/* 135 */       this.dialog.dismiss();
/*     */     }
/*     */     
/* 138 */     if (result.booleanValue()) {
/*     */       
/* 140 */       this.dialog.setMessage("CALLHOME OK");
/* 141 */       Toast.makeText(this.activity, "CALLHOME OK", Toast.LENGTH_SHORT).show();
/*     */     }
/*     */     else {
/*     */       
/* 145 */       this.dialog.setMessage("CALLHOME FAILED");
/* 146 */       Toast.makeText(this.activity, "CALLHOME FAILED", Toast.LENGTH_SHORT).show();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/MAC/Documents/classes.jar!/com/arke/sdk/util/epms/CallhomeTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */