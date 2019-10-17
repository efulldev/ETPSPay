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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParametersTask
/*     */   extends AsyncTask<Void, Integer, Boolean>
/*     */ {
/*     */   private static final String TAG = "ParametersTask";
/*     */   private ProgressDialog dialog;
/*     */   private Activity activity;
/*     */   SharedPreferences settings;
/*     */   
/*     */   public ParametersTask(Activity activity) {
/*  29 */     this.settings = null;
/*     */ 
/*     */     
/*  32 */     this.dialog = new ProgressDialog(activity);
/*  33 */     this.settings = PreferenceManager.getDefaultSharedPreferences(activity);
/*  34 */     this.activity = activity;
/*     */   }
/*     */   
/*     */   protected void onPreExecute() {
/*  38 */     this.dialog.setMessage("Processing...");
/*  39 */     this.dialog.show();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Boolean doInBackground(Void... params) {
/*     */     try {
/*  48 */       String terminalid = this.settings.getString("terminalid", "");
/*  49 */       String host = this.settings.getString("hostip", "");
/*  50 */       String port = this.settings.getString("hostport", "");
/*  51 */       String protocol = this.settings.getString("protocol", "");
                String fieldXMLReq = this.settings.getString("isoFieldXMLReq", "");
                String fieldXMLRes = this.settings.getString("isoFieldXMLRes", "");
/*     */
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  59 */       Log.d("ParametersTask", "parameter download");
/*  60 */       String skey = this.settings.getString("skey", "");
/*  61 */       Log.d("ParametersTask", "parameter download:skey:" + skey);
/*     */ 
/*     */       
/*  64 */       String response = Initiate.parametersDownload(fieldXMLReq, fieldXMLRes, host, port, protocol, "123456", terminalid, "123456789", skey);
/*  65 */       Log.d("ParametersTask", "response : " + response);
/*     */       
/*  67 */       if (response == null) {
/*  68 */         return Boolean.valueOf(false);
/*     */       }
/*     */       
/*  71 */       JsonElement jsonElement = (new JsonParser()).parse(response);
/*  72 */       JsonObject jobject = jsonElement.getAsJsonObject();
/*  73 */       String f39 = jobject.getAsJsonPrimitive("39").getAsString();
/*  74 */       Log.d("ParametersTask", "f39 : " + f39);
/*     */       
/*  76 */       if (f39 != null && f39.equals("00")) {
/*  77 */         String f62 = jobject.getAsJsonPrimitive("62").getAsString();
/*  78 */         Log.d("ParametersTask", "f62 : " + f62);
/*     */ 
/*     */ 
/*     */         
/*  82 */         while (!f62.isEmpty() && (f62.startsWith("02") || f62.startsWith("03") || f62.startsWith("04") || f62.startsWith("05") || f62.startsWith("06") || f62.startsWith("07") || f62.startsWith("08") || f62.startsWith("52")))
/*     */         {
/*     */ 
/*     */           
/*  86 */           if (f62.startsWith("02")) {
/*  87 */             int index = f62.indexOf("02");
/*  88 */             String len = f62.substring(index + 2, index + 2 + 3);
/*     */             
/*  90 */             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
/*  91 */             Log.d("ParametersTask", "CTMS Date and Time : " + value);
/*     */             
/*  93 */             SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
/*     */             try {
/*  95 */               Date date = sdf.parse(value);
/*  96 */               Calendar calendar = Calendar.getInstance();
/*  97 */               calendar.setTime(date);
/*  98 */               Log.d("ParametersTask", "Set Date and Time : " + calendar.getTime());
/*  99 */             } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */             
/* 103 */             f62 = f62.replace("02" + len + value, "");
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 108 */           if (f62.startsWith("03")) {
/* 109 */             int index = f62.indexOf("03");
/* 110 */             String len = f62.substring(index + 2, index + 2 + 3);
/*     */             
/* 112 */             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
/* 113 */             Log.d("ParametersTask", "Card Acceptor Identification Code : " + value);
/*     */             
/* 115 */             SharedPreferences.Editor editor = this.settings.edit();
/* 116 */             editor.putString("merchantid", value);
/* 117 */             editor.commit();
/*     */ 
/*     */             
/* 120 */             f62 = f62.replace("03" + len + value, "");
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 125 */           if (f62.startsWith("04")) {
/* 126 */             int index = f62.indexOf("04");
/* 127 */             String len = f62.substring(index + 2, index + 2 + 3);
/*     */             
/* 129 */             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
/* 130 */             Log.d("ParametersTask", "Receive Timeout : " + value);
/*     */             
/* 132 */             SharedPreferences.Editor editor = this.settings.edit();
/* 133 */             editor.putString("rtimeout", value);
/* 134 */             editor.commit();
/*     */ 
/*     */             
/* 137 */             f62 = f62.replace("04" + len + value, "");
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 142 */           if (f62.startsWith("05")) {
/* 143 */             int index = f62.indexOf("05");
/* 144 */             String len = f62.substring(index + 2, index + 2 + 3);
/*     */             
/* 146 */             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
/* 147 */             Log.d("ParametersTask", "Currency Code : " + value);
/*     */             
/* 149 */             SharedPreferences.Editor editor = this.settings.edit();
/* 150 */             editor.putString("currencycode", value);
/* 151 */             editor.commit();
/*     */ 
/*     */             
/* 154 */             f62 = f62.replace("05" + len + value, "");
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 159 */           if (f62.startsWith("06")) {
/* 160 */             int index = f62.indexOf("06");
/* 161 */             String len = f62.substring(index + 2, index + 2 + 3);
/*     */             
/* 163 */             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
/* 164 */             Log.d("ParametersTask", "Country Code : " + value);
/*     */             
/* 166 */             SharedPreferences.Editor editor = this.settings.edit();
/* 167 */             editor.putString("countrycode", value);
/* 168 */             editor.commit();
/*     */ 
/*     */             
/* 171 */             f62 = f62.replace("06" + len + value, "");
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 176 */           if (f62.startsWith("07")) {
/* 177 */             int index = f62.indexOf("07");
/* 178 */             String len = f62.substring(index + 2, index + 2 + 3);
/*     */             
/* 180 */             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
/* 181 */             Log.d("ParametersTask", "Callhome Timer : " + value);
/*     */             
/* 183 */             int callhome = Integer.parseInt(value) * 60 * 60;
/*     */             
/* 185 */             SharedPreferences.Editor editor = this.settings.edit();
/*     */             
/* 187 */             editor.putString("callhome", "" + callhome);
/* 188 */             editor.commit();
/*     */ 
/*     */             
/* 191 */             f62 = f62.replace("07" + len + value, "");
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 196 */           if (f62.startsWith("52")) {
/* 197 */             int index = f62.indexOf("52");
/* 198 */             String len = f62.substring(index + 2, index + 2 + 3);
/*     */             
/* 200 */             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
/* 201 */             Log.d("ParametersTask", "Merchant Name and Location : " + value);
/*     */             
/* 203 */             SharedPreferences.Editor editor = this.settings.edit();
/* 204 */             editor.putString("merchant", value);
/* 205 */             editor.commit();
/*     */ 
/*     */             
/* 208 */             f62 = f62.replace("52" + len + value, "");
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 213 */           if (f62.startsWith("08")) {
/* 214 */             int index = f62.indexOf("08");
/* 215 */             String len = f62.substring(index + 2, index + 2 + 3);
/*     */             
/* 217 */             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
/* 218 */             Log.d("ParametersTask", "Merchant Category Code : " + value);
/*     */             
/* 220 */             SharedPreferences.Editor editor = this.settings.edit();
/* 221 */             editor.putString("mcc", value);
/* 222 */             editor.commit();
/*     */ 
/*     */             
/* 225 */             f62 = f62.replace("08" + len + value, "");
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 234 */         return Boolean.valueOf(false);
/*     */       } 
/*     */       
/* 237 */       return Boolean.valueOf(true);
/* 238 */     } catch (Exception e) {
/* 239 */       Log.d("ParametersTask", e.getMessage());
/*     */ 
/*     */       
/* 242 */       return Boolean.valueOf(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onPostExecute(Boolean result) {
/* 248 */     if (this.dialog.isShowing()) {
/* 249 */       this.dialog.dismiss();
/*     */     }
/*     */     
/* 252 */     if (result.booleanValue()) {
/*     */       
/* 254 */       this.dialog.setMessage("PARAMETERS DOWNLOAD OK");
/* 255 */       Toast.makeText(this.activity, "PARAMETERS DOWNLOAD OK", Toast.LENGTH_SHORT).show();
/*     */     }
/*     */     else {
/*     */       
/* 259 */       this.dialog.setMessage("PARAMETERS DOWNLOAD FAILED");
/* 260 */       Toast.makeText(this.activity, "PARAMETERS DOWNLOAD FAILED", Toast.LENGTH_SHORT).show();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/MAC/Documents/classes.jar!/com/arke/sdk/util/epms/ParametersTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */