package com.efulltech.etpspay.ui;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.efulltech.efull_nibss_bridge.Downloader;
//import com.efulltech.efull_nibss_bridge.MessagePackager;
import com.efulltech.efull_nibss_bridge.epms.CallhomeTask;
import com.efulltech.efull_nibss_bridge.epms.NewKeysTask;
import com.efulltech.epay_tps_library_module.CardPaymentActivity;
import com.efulltech.epay_tps_library_module.TransactionOptions;
import com.efulltech.epay_tps_library_module.misc.ATRParser;
import com.efulltech.etpspay.R;
import com.efulltech.etpspay.ui.data.LoginDataSource;
import com.efulltech.etpspay.ui.data.LoginRepository;
import com.efulltech.etpspay.ui.data.model.LoggedInUser;
import com.efulltech.etpspay.ui.preferences.MainPreferencesActivity;
import com.efulltech.etpspay.utils.Constants;
import com.efulltech.etpspay.utils.DataProccessor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.os.SystemClock.sleep;


@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private static final String TAG = "MainActivity";
    private static final int CARD_PAY_REQ_CODE = 384;
    private static final int MY_DATA_CHECK_CODE = 13409;

    DataProccessor dataProccessor;
    //    declaration
    private SharedPreferences mPreferences;
    private LoginDataSource dataSource;
    private LoginRepository loginRepository;
    private LoggedInUser user;
    private static TextToSpeech myTTS;
    private String ttsOption;

    TextView userNameText;
    TextView permLevelText;


    Button eodBtn, printEodBtn, rePrintRecieptBtn, printTransHistoryBtn;
    @BindView(R.id.signOutBtn)
    Button signOutBtn;
    @BindView(R.id.cardPaymentBtn)
    Button cardPayment;
    @BindView(R.id.transactionHistoryBtn)
    Button transactionHistory;
//    @BindView(R.id.printEODBtn)
//    Button printReceipt;
    @BindView(R.id.userPreferencesBtn)
    Button userPreferences;
    private SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        Downloader downloader = new Downloader(this, "isoFieldXML");
//        downloader.downloadFile("https://raw.githubusercontent.com/zheeno/ISO8583_Messages/master/iso8583CustomPackager.xml");


//        Text to speech code
        //check for TTS data
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        ttsOption = mPreferences.getString("ttsOption", "false");

        eodBtn = findViewById(R.id.eodBtn);
        printEodBtn = findViewById(R.id.printEODBtn);
        rePrintRecieptBtn = findViewById(R.id.reprintRecieptBtn);
        printTransHistoryBtn = findViewById(R.id.printTransHistoryBtn);


//        on click of print end of the day
        printEodBtn.setOnClickListener(view ->
                Toast.makeText(this, "Printing end of the day receipt", Toast.LENGTH_SHORT).show()
        );

        //        on click of print end of the day
        rePrintRecieptBtn.setOnClickListener(view ->
                Toast.makeText(this, "Re Printing end of the day reciept", Toast.LENGTH_SHORT).show()
        );

        //        on click of print end of the day
        printTransHistoryBtn.setOnClickListener(view ->
                Toast.makeText(this, "Printing Transaction History reciept", Toast.LENGTH_SHORT).show()
        );

        userNameText = findViewById(R.id.userNameText);
        permLevelText = findViewById(R.id.permLevText);
        // get current user
        dataSource = new LoginDataSource();
        loginRepository = LoginRepository.getInstance(dataSource);
        user = loginRepository.getLoggedInUser();
//        if(user == null){
//            // log out
//            this.logUserOut();
//        }
        // display user details
//        userNameText.setText("You\'re logged in as "+user.getDisplayName());
//        permLevelText.setText(user.getPermLevelName());

        dataProccessor = new DataProccessor(this);
//        initialisation very important
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

//        checkOsVersion();

    }



//        on click of end of the day btn

    @OnClick(R.id.eodBtn)
    public void eodBtn(View view) {

        ATRParser atr = new ATRParser("3B 6D 00 00 80 31 80 65 B0 89 35 01 F1 83 00 90 00");
        byte[] history = atr.getBytes();
        byte item = 0;
        for (int i = 0; i < history.length; i++){
            item = history[i];
            Log.d(TAG, "BYTE ITEM: "+item);
        }
        Toast.makeText(this, "End of the day was clicked "+atr.toString(), Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.cardPaymentBtn)
    public void cardPayment(View view) {
        Intent cardPayment = new Intent(MainActivity.this, CardPaymentActivity.class);
        cardPayment.putExtra("ttsOption", ttsOption);
        startActivityForResult(cardPayment, CARD_PAY_REQ_CODE);
    }


//
    @OnClick(R.id.walletPaymentBtn)
    public void walletPayment(View view) {
        com.efulltech.epay_tps_library_module.UsbPrinter usbPrinter = new com.efulltech.epay_tps_library_module.UsbPrinter(this);
//        usbPrinter.PrintDemoText();
//        Intent walletPayment = new Intent(MainActivity.this, LedActivity.class);

//        we have to pass this line of codes anytime we want to implement the tts on any activity
//        String ttsOption = mPreferences.getString("ttsOption", "false");

//        cardPayment.putExtra("ttsOption", ttsOption);
//        startActivity(walletPayment);

        // download new kekys
        NewKeysTask newKeysTask = new NewKeysTask(this);
        newKeysTask.execute();

        // call to home
//        CallhomeTask callhomeTask = new CallhomeTask(this);
//        callhomeTask.execute();


  }





    @OnClick(R.id.signOutBtn)
    public void logOut(View view){
        this.logUserOut();
    }


    private void logUserOut() {
//        LoginActivity.speakWords("Goodbye");
        LoginDataSource dataSource = new LoginDataSource();
        LoginRepository loginRepository = LoginRepository.getInstance(dataSource);
        loginRepository.logout();
        finish();
        // open slash screen activity
        Intent splashIntent = new Intent(MainActivity.this, SplashActivity.class);
        startActivity(splashIntent);
    }
//
    @OnClick(R.id.transactionHistoryBtn)
    public void transactionHistory(View view) {
        Intent transactionHistory = new Intent(MainActivity.this, TransactionHistoryActivity.class);
        startActivity(transactionHistory);

    }

    @OnClick(R.id.userPreferencesBtn)
    public void userPreferences(View view) {
//        confirmPinThenSettings();
        Button setLogo = findViewById(R.id.userPreferencesBtn);
        Intent intent = new Intent(MainActivity.this, MainPreferencesActivity.class);
        startActivity(intent);
    }

    private void confirmPinThenSettings() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.admin_pin_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText uPin = promptView.findViewById(R.id.pinTextField);
        dataProccessor.setStr(Constants.OPERATOR_PIN, "1234");
        dataProccessor.setStr(Constants.SUPERVISOR_PIN, "1111");
        dataProccessor.setStr(Constants.ADMIN_PIN, "260089");

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Confirm", (dialog, id) -> {

                    String pin = uPin.getText().toString();
                    String permission = "";
                    if(dataProccessor.getStr(Constants.OPERATOR_PIN).equals(pin) ||
                            dataProccessor.getStr(Constants.SUPERVISOR_PIN).equals(pin) ||
                            dataProccessor.getStr(Constants.ADMIN_PIN).equals(pin)){
                        if(Constants.ADMIN_PIN.equals(pin)){
                            dataProccessor.setStr(Constants.ADMIN, "admin");
//                            Log.d("MainActivity", "Admin:" + pin);
//                            permission = "admin";
                        }else if(Constants.SUPERVISOR_PIN.equals(pin)){
                            dataProccessor.setStr(Constants.SUPERVISOR, "supervisor");
//                            Log.d("MainActivity", "Supervisor:" + pin);
//                            permission = "supervisor";
                        }else{
                            dataProccessor.setStr(Constants.OPERATOR, "operator");
//                            Log.d("MainActivity", "Operator:" + pin);
//                            permission = "operator";
                        }
//                            display SettingsActivity page
                        Button setLogo = findViewById(R.id.userPreferencesBtn);
                        Intent intent = new Intent(MainActivity.this, MainPreferencesActivity.class);
//
                        startActivity(intent);

                    }else{
//                            alert invalid PIN
                        Snackbar.make(findViewById(android.R.id.content), "Invalid PIN! Please try again", Snackbar.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel",
                        (dialog, id) ->
                                dialog.cancel());

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }


    public static void speakWords(String speech) {
        myTTS.setSpeechRate(1.3f);
        //speak straight away
        myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
    }

//
//    public void checkOsVersion() {
//
////        String _uri = sharedPref.getString("cloudDBUri", "http://192.168.8.101/api/");
////        String packageName =  BuildConfig.APPLICATION_ID;
////        String versionName = BuildConfig.VERSION_NAME;
////        String tid = this.sharedPref.getString("terminalid", "");
////        String url = _uri + "/checkOsVersion?terminalId="+tid+"&package="+packageName+"&version="+versionName;
////
////        Log.d("Checking OS Version ", url);
////        Toast.makeText(MainActivity.this, "Checking for updates", Toast.LENGTH_SHORT).show();
//
////        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
////        StringRequest stringRequest = new StringRequest(
////                Request.Method.GET,
////                url,
////                new Response.Listener<String>() {
////                    @Override
////                    public void onResponse(String response) {
////                        Log.d("Update Response", response);
////                        if(response.equals("true")){
////                            Toast.makeText(MainActivity.this, "Your app is up to date", Toast.LENGTH_SHORT).show();
////                        }else{
//////                            navigate to update page
////                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(response)));
////                        }
////
////                    }
////                },
////                new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        Log.e("Cloud DB Error", error.toString());
////                        Toast.makeText(MainActivity.this, "Timeout, Please check your internet connection", Toast.LENGTH_LONG).show();
////                    }
////                }
////        );
//////      set retry policy to determine how long volley should wait before resending a failed request
////        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
////                30000,
////                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
////                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//////        add jsonObjectRequest to the queue
////        requestQueue.add(stringRequest);
//    }


    //    On delete user dialog
    public void showDialog(String response) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.delete_item_dialog_layout, null);

        // get a reference to add user btn on the dialog
        Button cancelBtn = (Button) mView.findViewById(R.id.cancelBtn);
        Button proceedBtn = (Button) mView.findViewById(R.id.proceedBtn);
        FloatingActionButton fab = mView.findViewById(R.id.floatingActionButton);
        TextView title =  mView.findViewById(R.id.deleteItemTitle);
        TextView message =  mView.findViewById(R.id.deleteItemMessageText);

        fab.setImageResource(R.drawable.ic_info_black_24dp);
        title.setText("Notification");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        title.setTextSize(20);
        message.setText(response);
        message.setTextSize(16);
        proceedBtn.setVisibility(View.GONE);
        cancelBtn.setVisibility(View.GONE);

        builder.setView(mView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TTS
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
                myTTS = new TextToSpeech(MainActivity.this,this);
            } else {
                //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
        if(requestCode == CARD_PAY_REQ_CODE){
            if(resultCode == Activity.RESULT_OK){
                // process was completed
                Toast.makeText(MainActivity.this, "mActivity completed!!!", Toast.LENGTH_SHORT).show();
            }else{
                // process was interrupted
                String response = data.getStringExtra("response");
                Boolean positive = data.getBooleanExtra("positive", false);
                ttsOption = mPreferences.getString("ttsOption", "false");

                if(ttsOption.equals("true")) {
                    sleep(1 * 500);
                    MainActivity.speakWords(response);
                }
                String moreInfo = "\nKindly remove the card and restart the process if you wish to proceed with the transaction.";
                showDialog(response+moreInfo);
            }
        }
    }

    @Override
    public void onInit(int initStatus) {
        //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            if(myTTS.isLanguageAvailable(Locale.UK)==TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.UK);
        }
        else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }
}