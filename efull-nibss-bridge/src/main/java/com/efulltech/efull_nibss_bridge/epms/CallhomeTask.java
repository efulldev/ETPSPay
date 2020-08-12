 package com.efulltech.efull_nibss_bridge.epms;

 import android.app.Activity;
 import android.app.ProgressDialog;
 import android.content.SharedPreferences;
 import android.os.AsyncTask;
 import android.preference.PreferenceManager;
 import android.util.Log;
 import android.widget.Toast;
// import com.arke.sdk.api.PinpadForMKSK;
 import com.google.gson.JsonElement;
 import com.google.gson.JsonObject;
 import com.google.gson.JsonParser;



 public class CallhomeTask
   extends AsyncTask<Void, Integer, Boolean>
 {
   private static final String TAG = "CallhomeTask";
   private ProgressDialog dialog;
   private Activity activity;
   SharedPreferences settings;

   public CallhomeTask(Activity activity) {
     this.settings = null;


     this.dialog = new ProgressDialog(activity);
     this.dialog.setCancelable(false);
     this.settings = PreferenceManager.getDefaultSharedPreferences(activity);
     this.activity = activity;
   }

   protected void onPreExecute() {
     this.dialog.setMessage("Calling Home...");
     this.dialog.show();
   }





   protected Boolean doInBackground(Void... params) {
     try {
       String terminalid = this.settings.getString("terminalid", "20390015");
       String host = this.settings.getString("hostip", "196.6.103.72");
       String port = this.settings.getString("hostport", "5042");
       String protocol = this.settings.getString("protocol", "0");
       String key1 = this.settings.getString("key1", "5D25072F04832A2329D93E4F91BA23A2");
       String key2 = this.settings.getString("key2", "86CBCDE3B0A22354853E04521686863D");
                String fieldXMLReq = this.settings.getString("isoFieldXMLReq", "");
                String fieldXMLRes = this.settings.getString("isoFieldXMLRes", "");

       String stan = GetUniqueKey(6);


       Log.d("CallhomeTask", "callhome");
       Initiate initiate = new Initiate(fieldXMLReq, host, port, protocol, stan, terminalid, "123456789");
       String response = initiate.callhome();
       Log.d("CallhomeTask", "response : " + response);

       if (response == null) {
         return Boolean.valueOf(false);
       }

       JsonElement jsonElement = (new JsonParser()).parse(response);
       JsonObject jobject = jsonElement.getAsJsonObject();
       String f39 = jobject.getAsJsonPrimitive("39").getAsString();
       Log.d("CallhomeTask", "f39 : " + f39);
        if (f39 != null && f39.equals("00")){
            return Boolean.valueOf(true); }  return Boolean.valueOf(false);
        } catch (Exception e) {
            Log.d("CallhomeTask", e.getMessage());
            return Boolean.valueOf(false);
        }
   }





   public String GetUniqueKey(int len8digitslenMax) {
     byte[] data = new byte[8];


     String digits = "1234567890";



//     try {
//       PinpadForMKSK.getInstance().open();
//
//       data = PinpadForMKSK.getInstance().getRandom(8);
//       data = PinpadForMKSK.getInstance().getRandom(8);
//
//       PinpadForMKSK.getInstance().close();
//     }
//     catch (Exception ex) {
//       Log.d("CallhomeTask", "Exception : " + ex.getMessage());
//     }

     String result = "";

     for (int b = 0; b < len8digitslenMax; b++) {

       int index = data[b] % digits.length();

       if (index < 0) {
         index *= -1;
       }



       String tmp = String.format("%c", new Object[] { Character.valueOf(digits.charAt(index)) });

       result = result + tmp;
     }




     return result;
   }




   protected void onPostExecute(Boolean result) {
     if (this.dialog.isShowing()) {
       this.dialog.dismiss();
     }

     if (result.booleanValue()) {

       this.dialog.setMessage("CALLHOME OK");
       Toast.makeText(this.activity, "CALLHOME OK", Toast.LENGTH_SHORT).show();
     }
     else {

       this.dialog.setMessage("CALLHOME FAILED");
       Toast.makeText(this.activity, "CALLHOME FAILED", Toast.LENGTH_SHORT).show();
     }
   }
 }
