package com.efulltech.etpspay.ui;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.efulltech.epay_tps_library_module.CardPaymentActivity;
import com.efulltech.etpspay.R;
import com.efulltech.etpspay.ui.data.LoginDataSource;
import com.efulltech.etpspay.ui.data.LoginRepository;
import com.efulltech.etpspay.ui.data.model.LoggedInUser;
import com.efulltech.etpspay.ui.preferences.MainPreferencesActivity;
import com.efulltech.etpspay.utils.Constants;
import com.efulltech.etpspay.utils.DataProccessor;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    DataProccessor dataProccessor;
    //    declaration
    private SharedPreferences mPreferences;
    private LoginDataSource dataSource;
    private LoginRepository loginRepository;
    private LoggedInUser user;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        eodBtn = findViewById(R.id.eodBtn);
        printEodBtn = findViewById(R.id.printEODBtn);
        rePrintRecieptBtn = findViewById(R.id.reprintRecieptBtn);
        printTransHistoryBtn = findViewById(R.id.printTransHistoryBtn);

//        on click of end of the day btn
        eodBtn.setOnClickListener(view ->
                Toast.makeText(this, "End of the day was clicked", Toast.LENGTH_SHORT).show()
        );

//        on click of print end of the day
        printEodBtn.setOnClickListener(view ->
                Toast.makeText(this, "Printing end of the day reciept", Toast.LENGTH_SHORT).show()
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

        // display user details
        userNameText.setText("You\'re logged in as "+user.getDisplayName());
        permLevelText.setText(user.getPermLevelName());

        dataProccessor = new DataProccessor(this);
//        initialisation very important
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

//        checkOsVersion();

    }

    @OnClick(R.id.cardPaymentBtn)
    public void cardPayment(View view) {
        Intent cardPayment = new Intent(MainActivity.this, CardPaymentActivity.class);

//        we have to pass this line of codes anytime we want to implement the tts on any activity
        String ttsOption = mPreferences.getString("ttsOption", "false");

        cardPayment.putExtra("ttsOption", ttsOption);
        startActivity(cardPayment);
    }


//
    @OnClick(R.id.walletPaymentBtn)
    public void walletPayment(View view) {
        com.efulltech.epay_tps_library_module.UsbPrinter usbPrinter = new com.efulltech.epay_tps_library_module.UsbPrinter(this);
        usbPrinter.PrintDemoText();
//        Intent walletPayment = new Intent(MainActivity.this, LedActivity.class);

//        we have to pass this line of codes anytime we want to implement the tts on any activity
//        String ttsOption = mPreferences.getString("ttsOption", "false");

//        cardPayment.putExtra("ttsOption", ttsOption);
//        startActivity(walletPayment);
    }





    @OnClick(R.id.signOutBtn)
    public void logOut(View view){
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

        Pair[] pairs  = new Pair[1];
        pairs[0] = new Pair<View, String>(setLogo, "logoTransition");
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);

        startActivity(intent, activityOptions.toBundle());
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
                        Pair[] pairs  = new Pair[1];
                        pairs[0] = new Pair<View, String>(setLogo, "logoTransition");
                        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);

                        startActivity(intent, activityOptions.toBundle());

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



}
