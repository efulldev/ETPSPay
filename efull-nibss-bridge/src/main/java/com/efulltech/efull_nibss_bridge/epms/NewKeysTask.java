    package com.efulltech.efull_nibss_bridge.epms;

    import android.app.Activity;
    import android.app.ProgressDialog;
    import android.content.SharedPreferences;
    import android.os.AsyncTask;
    import android.preference.PreferenceManager;
    import android.util.Log;
    import android.widget.Toast;

    import com.efulltech.efull_nibss_bridge.utils.Logs;
    import com.google.gson.JsonElement;
    import com.google.gson.JsonObject;
    import com.google.gson.JsonParser;
    import javax.crypto.SecretKey;


     public class NewKeysTask extends AsyncTask<Void, Integer, Boolean> {
       private static final String TAG = "PrepTask";
       private ProgressDialog dialog;
       private Activity activity;
       SharedPreferences settings;
       private Logs logs;

       public NewKeysTask(Activity activity) {
         this.settings = null;
         this.dialog = new ProgressDialog(activity);
         this.settings = PreferenceManager.getDefaultSharedPreferences(activity);
         this.activity = activity;
         logs = new Logs(false);
       }

       public NewKeysTask(){
         logs = new Logs(true);
         logs.d(TAG, "Instance...");
       }

       protected void onPreExecute() {
         logs.d(TAG, "Processing...");
         this.dialog.setMessage("Processing...");
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

           logs.d("PrepTask", "key1:" + key1);
           logs.d("PrepTask", "key2:" + key2);

//           String key1 = "5D25072F04832A2329D93E4F91BA23A2", key2 = "86CBCDE3B0A22354853E04521686863D";

           logs.d("PrepTask", "master key download");
//           Controller initiate = new Controller("", "196.6.103.72", "5042", "0", "123456", "20390015", "123456789");

           Initiate initiate = new Initiate(fieldXMLReq, host, port, protocol, "123456", terminalid, "123456789");
           String response = initiate.masterKeyDownload();
           logs.d("PrepTask", "response : " + response);


           if (response == null) {
             return Boolean.valueOf(false);
           }

           JsonElement jsonElement = (new JsonParser()).parse(response);
           JsonObject jobject = jsonElement.getAsJsonObject();
           String f39 = jobject.getAsJsonPrimitive("39").getAsString();
           logs.d("PrepTask", "f39 : " + f39);

           if (f39 != null && f39.equals("00")) {
             String f53 = jobject.getAsJsonPrimitive("53").getAsString();
             String emk = f53.substring(0, 32);
             String kcv = f53.substring(32, 38);
             logs.d("PrepTask", "f53 : " + f53);
             logs.d("PrepTask", "emk : " + emk);
             logs.d("PrepTask", "kcv : " + kcv);

             byte[] keyB1 = Crypto.hexToByte(key1 + key1.substring(0, 16));
             byte[] keyB2 = Crypto.hexToByte(key2 + key2.substring(0, 16));
             byte[] keyB3 = new byte[keyB1.length];

             for (int i = 0; i < keyB1.length; i++) {
               keyB3[i] = (byte)(keyB1[i] ^ keyB2[i]);
             }

             SecretKey key = Crypto.read3DESKey(keyB3);
             String dmk = Crypto.Decrypt3DES(key, emk);
             logs.d("PrepTask", "dmk : " + dmk);

             SharedPreferences.Editor editor = this.settings.edit();
             editor.putString("mkey", dmk);
             editor.commit();
           }
           else {

             return Boolean.valueOf(false);
           }


           logs.d("PrepTask", "session key download");

//           response = initiate.sessionKeyDownload();



           logs.d("PrepTask", "response : " + response);

           if (response == null) {
             return Boolean.valueOf(false);
           }

           jsonElement = (new JsonParser()).parse(response);
           jobject = jsonElement.getAsJsonObject();
           f39 = jobject.getAsJsonPrimitive("39").getAsString();
           logs.d("PrepTask", "f39 : " + f39);

           if (f39 != null && f39.equals("00")) {
             String f53 = jobject.getAsJsonPrimitive("53").getAsString();
             String esk = f53.substring(0, 32);
             String kcv = f53.substring(32, 38);
             logs.d("PrepTask", "f53 : " + f53);
             logs.d("PrepTask", "esk : " + esk);
             logs.d("PrepTask", "kcv : " + kcv);

             String mkey = this.settings.getString("mkey", "");
             byte[] keyB1 = Crypto.hexToByte(mkey + mkey.substring(0, 16));

             SecretKey key = Crypto.read3DESKey(keyB1);
             String dsk = Crypto.Decrypt3DES(key, esk);
             logs.d("PrepTask", "dsk : " + dsk);

             SharedPreferences.Editor editor = this.settings.edit();
             editor.putString("skey", dsk);
             editor.commit();
           }
           else {

             return Boolean.valueOf(false);
           }


           logs.d("PrepTask", "pin key downlaod");
//           response = Controller.pinKeyDownload(fieldXMLReq,fieldXMLRes, host, port, protocol, "123456", terminalid, "123456789");
           logs.d("PrepTask", "response : " + response);

           if (response == null) {
             return Boolean.valueOf(false);
           }

           jsonElement = (new JsonParser()).parse(response);
           jobject = jsonElement.getAsJsonObject();
           f39 = jobject.getAsJsonPrimitive("39").getAsString();
           logs.d("PrepTask", "f39 : " + f39);

           if (f39 != null && f39.equals("00")) {
             String f53 = jobject.getAsJsonPrimitive("53").getAsString();
             String epk = f53.substring(0, 32);
             String kcv = f53.substring(32, 38);
             logs.d("PrepTask", "f53 : " + f53);
             logs.d("PrepTask", "epk : " + epk);
             logs.d("PrepTask", "kcv : " + kcv);

             String mkey = this.settings.getString("mkey", "");
             byte[] keyB1 = Crypto.hexToByte(mkey + mkey.substring(0, 16));

             SecretKey key = Crypto.read3DESKey(keyB1);
             String dpk = Crypto.Decrypt3DES(key, epk);
             logs.d("PrepTask", "dsk : " + dpk);

             SharedPreferences.Editor editor = this.settings.edit();
             editor.putString("pkey", dpk);
             editor.putString("epkey", epk);
             editor.putString("epkeykcv", kcv);
             editor.commit();

           }
           else {

             return Boolean.valueOf(false);
           }

           return Boolean.valueOf(true);
         } catch (Exception e) {
           logs.d("PrepTask", e.getMessage());


           return Boolean.valueOf(false);
         }
       }


       protected void onPostExecute(Boolean result) {
         if (this.dialog.isShowing()) {
           this.dialog.dismiss();
         }

         if (result.booleanValue()) {

           this.dialog.setMessage("NEW KEYS DOWNLOAD OK");
           Toast.makeText(this.activity, "NEW KEYS DOWNLOAD OK", Toast.LENGTH_SHORT).show();
         }
         else {

           this.dialog.setMessage("NEW KEYS DOWNLOAD FAILED");
           Toast.makeText(this.activity, "NEW KEYS DOWNLOAD FAILED", Toast.LENGTH_SHORT).show();
         }
       }
     }
