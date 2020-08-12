 package com.efulltech.efull_nibss_bridge.epms;

 import android.app.Activity;
 import android.app.ProgressDialog;
 import android.content.SharedPreferences;
 import android.os.AsyncTask;
 import android.preference.PreferenceManager;
 import android.util.Log;
 import android.widget.Toast;
 import com.google.gson.JsonElement;
 import com.google.gson.JsonObject;
 import com.google.gson.JsonParser;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.Date;
 import javax.crypto.SecretKey;

 public class PrepTask
   extends AsyncTask<Void, Integer, Boolean> {
   private static final String TAG = "PrepTask";
   private ProgressDialog dialog;
   private Activity activity;
   SharedPreferences settings;

   public PrepTask(Activity activity) {
     this.settings = null;


     this.dialog = new ProgressDialog(activity);
     this.settings = PreferenceManager.getDefaultSharedPreferences(activity);
     this.activity = activity;
   }

   protected void onPreExecute() {
     this.dialog.setMessage("Processing...");
     this.dialog.show();
   }





   protected Boolean doInBackground(Void... params) {
     try {
       String terminalid = this.settings.getString("terminalid", "");
       String host = this.settings.getString("hostip", "");
       String port = this.settings.getString("hostport", "");
       String protocol = this.settings.getString("protocol", "");
       String key1 = this.settings.getString("key1", "");
       String key2 = this.settings.getString("key2", "");
                String fieldXMLReq = this.settings.getString("isoFieldXMLReq", "");
                String fieldXMLRes = this.settings.getString("isoFieldXMLRes", "");

       Log.d("PrepTask", "key1:" + key1);
       Log.d("PrepTask", "key2:" + key2);


       Log.d("PrepTask", "master key doawload");
       Initiate initiate = new Initiate(fieldXMLReq, host, port, protocol, "123456", terminalid, "123456789");
       String response = initiate.masterKeyDownload();
        Log.d("PrepTask", "response : " + response);

       if (response == null) {
         return Boolean.valueOf(false);
       }

       JsonElement jsonElement = (new JsonParser()).parse(response);
       JsonObject jobject = jsonElement.getAsJsonObject();
       String f39 = jobject.getAsJsonPrimitive("39").getAsString();
       Log.d("PrepTask", "f39 : " + f39);

       if (f39 != null && f39.equals("00")) {
         String f53 = jobject.getAsJsonPrimitive("53").getAsString();
         String emk = f53.substring(0, 32);
         String kcv = f53.substring(32, 38);
         Log.d("PrepTask", "f53 : " + f53);
         Log.d("PrepTask", "emk : " + emk);
         Log.d("PrepTask", "kcv : " + kcv);

         byte[] keyB1 = Crypto.hexToByte(key1 + key1.substring(0, 16));
         byte[] keyB2 = Crypto.hexToByte(key2 + key2.substring(0, 16));
         byte[] keyB3 = new byte[keyB1.length];

         for (int i = 0; i < keyB1.length; i++) {
           keyB3[i] = (byte)(keyB1[i] ^ keyB2[i]);
         }

         SecretKey key = Crypto.read3DESKey(keyB3);
         String dmk = Crypto.Decrypt3DES(key, emk);
         Log.d("PrepTask", "dmk : " + dmk);

         SharedPreferences.Editor editor = this.settings.edit();
         editor.putString("mkey", dmk);
         editor.commit();
       }
       else {

         return Boolean.valueOf(false);
       }


       Log.d("PrepTask", "session key download");
       response = initiate.sessionKeyDownload();
       Log.d("PrepTask", "response : " + response);

       if (response == null) {
         return Boolean.valueOf(false);
       }

       jsonElement = (new JsonParser()).parse(response);
       jobject = jsonElement.getAsJsonObject();
       f39 = jobject.getAsJsonPrimitive("39").getAsString();
       Log.d("PrepTask", "f39 : " + f39);

       if (f39 != null && f39.equals("00")) {
         String f53 = jobject.getAsJsonPrimitive("53").getAsString();
         String esk = f53.substring(0, 32);
         String kcv = f53.substring(32, 38);
         Log.d("PrepTask", "f53 : " + f53);
         Log.d("PrepTask", "esk : " + esk);
         Log.d("PrepTask", "kcv : " + kcv);

         String mkey = this.settings.getString("mkey", "");
         byte[] keyB1 = Crypto.hexToByte(mkey + mkey.substring(0, 16));

         SecretKey key = Crypto.read3DESKey(keyB1);
         String dsk = Crypto.Decrypt3DES(key, esk);
         Log.d("PrepTask", "dsk : " + dsk);

         SharedPreferences.Editor editor = this.settings.edit();
         editor.putString("skey", dsk);
         editor.commit();
       }
       else {

         return Boolean.valueOf(false);
       }


       Log.d("PrepTask", "pin key downlaod");
       response = initiate.pinKeyDownload();
       Log.d("PrepTask", "response : " + response);

       if (response == null) {
         return Boolean.valueOf(false);
       }

       jsonElement = (new JsonParser()).parse(response);
       jobject = jsonElement.getAsJsonObject();
       f39 = jobject.getAsJsonPrimitive("39").getAsString();
       Log.d("PrepTask", "f39 : " + f39);

       if (f39 != null && f39.equals("00")) {
         String f53 = jobject.getAsJsonPrimitive("53").getAsString();
         String epk = f53.substring(0, 32);
         String kcv = f53.substring(32, 38);
         Log.d("PrepTask", "f53 : " + f53);
         Log.d("PrepTask", "epk : " + epk);
         Log.d("PrepTask", "kcv : " + kcv);

         String mkey = this.settings.getString("mkey", "");
         byte[] keyB1 = Crypto.hexToByte(mkey + mkey.substring(0, 16));

         SecretKey key = Crypto.read3DESKey(keyB1);
         String dpk = Crypto.Decrypt3DES(key, epk);
         Log.d("PrepTask", "dsk : " + dpk);

         SharedPreferences.Editor editor = this.settings.edit();
         editor.putString("pkey", dpk);
         editor.putString("epkey", epk);
         editor.putString("epkeykcv", kcv);
         editor.commit();

       }
       else {

         return Boolean.valueOf(false);
       }


       Log.d("PrepTask", "parameter download");
       String skey = this.settings.getString("skey", "");
       Log.d("PrepTask", "parameter download:skey:" + skey);


       response = initiate.parametersDownload(skey);
       Log.d("PrepTask", "response : " + response);

       if (response == null) {
         return Boolean.valueOf(false);
       }

       jsonElement = (new JsonParser()).parse(response);
       jobject = jsonElement.getAsJsonObject();
       f39 = jobject.getAsJsonPrimitive("39").getAsString();
       Log.d("PrepTask", "f39 : " + f39);

       if (f39 != null && f39.equals("00")) {
         String f62 = jobject.getAsJsonPrimitive("62").getAsString();
         Log.d("PrepTask", "f62 : " + f62);



         while (!f62.isEmpty() && (f62.startsWith("02") || f62.startsWith("03") || f62.startsWith("04") || f62.startsWith("05") || f62.startsWith("06") || f62.startsWith("07") || f62.startsWith("08") || f62.startsWith("52")))
         {


           if (f62.startsWith("02")) {
             int index = f62.indexOf("02");
             String len = f62.substring(index + 2, index + 2 + 3);

             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
             Log.d("PrepTask", "CTMS Date and Time : " + value);

             SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
             try {
               Date date = sdf.parse(value);
               Calendar calendar = Calendar.getInstance();
               calendar.setTime(date);
               Log.d("PrepTask", "Set Date and Time : " + calendar.getTime());
             } catch (Exception exception) {}



             f62 = f62.replace("02" + len + value, "");
           }



           if (f62.startsWith("03")) {
             int index = f62.indexOf("03");
             String len = f62.substring(index + 2, index + 2 + 3);

             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
             Log.d("PrepTask", "Card Acceptor Identification Code : " + value);

             SharedPreferences.Editor editor = this.settings.edit();
             editor.putString("merchantid", value);
             editor.commit();


             f62 = f62.replace("03" + len + value, "");
           }



           if (f62.startsWith("04")) {
             int index = f62.indexOf("04");
             String len = f62.substring(index + 2, index + 2 + 3);

             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
             Log.d("PrepTask", "Receive Timeout : " + value);

             SharedPreferences.Editor editor = this.settings.edit();
             editor.putString("rtimeout", value);
             editor.commit();


             f62 = f62.replace("04" + len + value, "");
           }



           if (f62.startsWith("05")) {
             int index = f62.indexOf("05");
             String len = f62.substring(index + 2, index + 2 + 3);

             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
             Log.d("PrepTask", "Currency Code : " + value);

             SharedPreferences.Editor editor = this.settings.edit();
             editor.putString("currencycode", value);
             editor.commit();


             f62 = f62.replace("05" + len + value, "");
           }



           if (f62.startsWith("06")) {
             int index = f62.indexOf("06");
             String len = f62.substring(index + 2, index + 2 + 3);

             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
             Log.d("PrepTask", "Country Code : " + value);

             SharedPreferences.Editor editor = this.settings.edit();
             editor.putString("countrycode", value);
             editor.commit();


             f62 = f62.replace("06" + len + value, "");
           }



           if (f62.startsWith("07")) {
             int index = f62.indexOf("07");
             String len = f62.substring(index + 2, index + 2 + 3);

             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
             Log.d("PrepTask", "Callhome Timer : " + value);

             SharedPreferences.Editor editor = this.settings.edit();
             editor.putString("callhome", value);
             editor.commit();


             f62 = f62.replace("07" + len + value, "");
           }



           if (f62.startsWith("52")) {
             int index = f62.indexOf("52");
             String len = f62.substring(index + 2, index + 2 + 3);

             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
             Log.d("PrepTask", "Merchant Name and Location : " + value);

             SharedPreferences.Editor editor = this.settings.edit();
             editor.putString("merchant", value);
             editor.commit();


             f62 = f62.replace("52" + len + value, "");
           }



           if (f62.startsWith("08")) {
             int index = f62.indexOf("08");
             String len = f62.substring(index + 2, index + 2 + 3);

             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
             Log.d("PrepTask", "Merchant Category Code : " + value);

             SharedPreferences.Editor editor = this.settings.edit();
             editor.putString("mcc", value);
             editor.commit();


             f62 = f62.replace("08" + len + value, "");

           }

         }

       }
       else {

         return Boolean.valueOf(false);
       }

       return Boolean.valueOf(true);
     } catch (Exception e) {
       Log.d("PrepTask", e.getMessage());


       return Boolean.valueOf(false);
     }
   }


   protected void onPostExecute(Boolean result) {
     if (this.dialog.isShowing()) {
       this.dialog.dismiss();
     }

     if (result.booleanValue()) {

       this.dialog.setMessage("PREP OK");
       Toast.makeText(this.activity, "PREP OK", Toast.LENGTH_SHORT).show();
     }
     else {

       this.dialog.setMessage("PREP FAILED");
       Toast.makeText(this.activity, "PREP FAILED", Toast.LENGTH_SHORT).show();
     }
   }
 }
