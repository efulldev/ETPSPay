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




 public class ParametersTask
   extends AsyncTask<Void, Integer, Boolean>
 {
   private static final String TAG = "ParametersTask";
   private ProgressDialog dialog;
   private Activity activity;
   SharedPreferences settings;

   public ParametersTask(Activity activity) {
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
                String fieldXMLReq = this.settings.getString("isoFieldXMLReq", "");
                String fieldXMLRes = this.settings.getString("isoFieldXMLRes", "");







       Log.d("ParametersTask", "parameter download");
       String skey = this.settings.getString("skey", "");
       Log.d("ParametersTask", "parameter download:skey:" + skey);

       Initiate initiate = new Initiate(fieldXMLReq, host, port, protocol, "123456", terminalid, "123456789");
       String response = initiate.parametersDownload(skey);
       Log.d("ParametersTask", "response : " + response);

       if (response == null) {
         return Boolean.valueOf(false);
       }

       JsonElement jsonElement = (new JsonParser()).parse(response);
       JsonObject jobject = jsonElement.getAsJsonObject();
       String f39 = jobject.getAsJsonPrimitive("39").getAsString();
       Log.d("ParametersTask", "f39 : " + f39);

       if (f39 != null && f39.equals("00")) {
         String f62 = jobject.getAsJsonPrimitive("62").getAsString();
         Log.d("ParametersTask", "f62 : " + f62);



         while (!f62.isEmpty() && (f62.startsWith("02") || f62.startsWith("03") || f62.startsWith("04") || f62.startsWith("05") || f62.startsWith("06") || f62.startsWith("07") || f62.startsWith("08") || f62.startsWith("52")))
         {


           if (f62.startsWith("02")) {
             int index = f62.indexOf("02");
             String len = f62.substring(index + 2, index + 2 + 3);

             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
             Log.d("ParametersTask", "CTMS Date and Time : " + value);

             SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
             try {
               Date date = sdf.parse(value);
               Calendar calendar = Calendar.getInstance();
               calendar.setTime(date);
               Log.d("ParametersTask", "Set Date and Time : " + calendar.getTime());
             } catch (Exception exception) {}



             f62 = f62.replace("02" + len + value, "");
           }



           if (f62.startsWith("03")) {
             int index = f62.indexOf("03");
             String len = f62.substring(index + 2, index + 2 + 3);

             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
             Log.d("ParametersTask", "Card Acceptor Identification Code : " + value);

             SharedPreferences.Editor editor = this.settings.edit();
             editor.putString("merchantid", value);
             editor.commit();


             f62 = f62.replace("03" + len + value, "");
           }



           if (f62.startsWith("04")) {
             int index = f62.indexOf("04");
             String len = f62.substring(index + 2, index + 2 + 3);

             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
             Log.d("ParametersTask", "Receive Timeout : " + value);

             SharedPreferences.Editor editor = this.settings.edit();
             editor.putString("rtimeout", value);
             editor.commit();


             f62 = f62.replace("04" + len + value, "");
           }



           if (f62.startsWith("05")) {
             int index = f62.indexOf("05");
             String len = f62.substring(index + 2, index + 2 + 3);

             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
             Log.d("ParametersTask", "Currency Code : " + value);

             SharedPreferences.Editor editor = this.settings.edit();
             editor.putString("currencycode", value);
             editor.commit();


             f62 = f62.replace("05" + len + value, "");
           }



           if (f62.startsWith("06")) {
             int index = f62.indexOf("06");
             String len = f62.substring(index + 2, index + 2 + 3);

             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
             Log.d("ParametersTask", "Country Code : " + value);

             SharedPreferences.Editor editor = this.settings.edit();
             editor.putString("countrycode", value);
             editor.commit();


             f62 = f62.replace("06" + len + value, "");
           }



           if (f62.startsWith("07")) {
             int index = f62.indexOf("07");
             String len = f62.substring(index + 2, index + 2 + 3);

             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
             Log.d("ParametersTask", "Callhome Timer : " + value);

             int callhome = Integer.parseInt(value) * 60 * 60;

             SharedPreferences.Editor editor = this.settings.edit();

             editor.putString("callhome", "" + callhome);
             editor.commit();


             f62 = f62.replace("07" + len + value, "");
           }



           if (f62.startsWith("52")) {
             int index = f62.indexOf("52");
             String len = f62.substring(index + 2, index + 2 + 3);

             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
             Log.d("ParametersTask", "Merchant Name and Location : " + value);

             SharedPreferences.Editor editor = this.settings.edit();
             editor.putString("merchant", value);
             editor.commit();


             f62 = f62.replace("52" + len + value, "");
           }



           if (f62.startsWith("08")) {
             int index = f62.indexOf("08");
             String len = f62.substring(index + 2, index + 2 + 3);

             String value = f62.substring(index + 2 + 3, index + 2 + 3 + Integer.parseInt(len));
             Log.d("ParametersTask", "Merchant Category Code : " + value);

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
       Log.d("ParametersTask", e.getMessage());


       return Boolean.valueOf(false);
     }
   }


   protected void onPostExecute(Boolean result) {
     if (this.dialog.isShowing()) {
       this.dialog.dismiss();
     }

     if (result.booleanValue()) {

       this.dialog.setMessage("PARAMETERS DOWNLOAD OK");
       Toast.makeText(this.activity, "PARAMETERS DOWNLOAD OK", Toast.LENGTH_SHORT).show();
     }
     else {

       this.dialog.setMessage("PARAMETERS DOWNLOAD FAILED");
       Toast.makeText(this.activity, "PARAMETERS DOWNLOAD FAILED", Toast.LENGTH_SHORT).show();
     }
   }
 }
