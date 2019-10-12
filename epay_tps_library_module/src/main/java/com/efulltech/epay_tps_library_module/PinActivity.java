package com.efulltech.epay_tps_library_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.efulltech.efull_nibss_bridge.Transaction;
import com.efulltech.efull_nibss_bridge.parser.field.CompoundField;

import java.util.ArrayList;
import java.util.List;

public class PinActivity extends BaseActivity {

    public Thread cardWatcherThread;
    boolean threadRunT, turnedOn;
    private String cardType;
    private int accType;
    private String amount;
    private SharedPreferences mPreferences;

    CountDownTimer countDownTimer;
    RecyclerView mRecycler;
    PinRecycler pinRecycler;
    ArrayList<PinClass> pinClasses;
    SmartCardReaderx readerx;
    String no = "";
    ArrayList<Integer> sortArr;
    Button one, two, three, four, five, six, seven, eight, nine, zero, cancel, clear, enter;
    TextView pinInput, custNameText, cardTypeText, accTypeText, amountText;
    private boolean sessionDialogState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        // get values passed via intent
        Intent intent = getIntent();
        this.cardType = intent.getStringExtra("cardType");
        this.accType = intent.getIntExtra("accType", 0);
        this.amount = intent.getStringExtra("amount");

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sessionDialogState = false;

        pinClasses = new ArrayList<>();
//        mRecycler = findViewById(R.id.mRecycler);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        zero = findViewById(R.id.zero);

        cancel = findViewById(R.id.cancelPinBtn);
        clear = findViewById(R.id.clearPinBtn);
        enter = findViewById(R.id.confirmPinBtn);

        pinInput = findViewById(R.id.pinInput);
        custNameText = findViewById(R.id.custNameText);
        cardTypeText = findViewById(R.id.cardTypeText);
        accTypeText = findViewById(R.id.accTypeText);
        amountText = findViewById(R.id.amountText);

        custNameText.setText("James St Patrick");
        cardTypeText.setText(this.cardType);
        accTypeText.setText(this.accType+"");
        amountText.setText("NGN" + this.amount);

        sortArr = new ArrayList<>();
        readerx = new SmartCardReaderx(PinActivity.this);
        threadRunT = true;

        Button txtArr[] = {one, two, three, four, five, six, seven, eight, nine, zero};
        // Implement the scattering of the digits
        scatterAlgorithm();
        // populate the pin pad with the random buttons
        for (int i = 0; i < 10; i++){
            getSortStr(txtArr[i], i);
        }
        // Populate the memory with the necessary listeners
        for (int i = 0; i < txtArr.length; i++){
            onButtonPressed(txtArr[i], txtArr[i].getText().toString().trim());
        }


        cardWatcherThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Opens the card readerx object in the thread to handle loop
                readerx.open();
                Log.d("ICC status", "Running");
//                try{
//                    turnedOn = readerx.iccPowerOn(1);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
                while (threadRunT){
                    sessionDialogState = mPreferences.getBoolean("sessionDialogState", false);
                    if(!sessionDialogState) { //checks that the session timed out dialog is no displayed
                        Log.d("ICC status", "Extra Running");
//                    Log.d("Card type", Integer.toString(readerx.getCardType()));
                        try {
                            if (!readerx.iccPowerOff()) {
                                Log.d("Card log error", "Card turned off");
                                threadRunT = false;
                                finishActivityWithErrorMsg("Transaction error, card removed");
                                cardWatcherThread.isInterrupted();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            cardWatcherThread.isInterrupted();
                        }
                    }else{
                        threadRunT = false;
                        cardWatcherThread.isInterrupted();
                    }
                }
            }
        });
        cardWatcherThread.start();

        // cancel pin button click listener
        cancel.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }));

        // clear pin button click listener
        clear.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beep(1);
                pinInput.setText("");
            }
        }));

        // confirm pin button click listener
        enter.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beep(2);
                if (pinInput.getText().toString().length() >= 4) {
//                  call this method if pin is correct
                    // card type
//                Transaction transaction = new Transaction();
//                transaction.setAccType(accType);
//                transaction.setCardType(cardType);
                    parseTransData();
                    countDownTimer.cancel();
                    ((TimeOutController) getApplication()).cancelTimer();
                }
            }
        }));

//        count down timer
        countDownTimer = new CountDownTimer(15000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                cardTypeText.setText(""+millisUntilFinished /1000);
            }

            @Override
            public void onFinish() {
                Toast.makeText(PinActivity.this, "Pin input timed out", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    private void parseTransData() {
//        CompoundField compoundField = new Com

    }


    private void finishActivityWithErrorMsg(String error){
        cardWatcherThread.isInterrupted();
        Intent _data = new Intent();
        _data.putExtra("response", ""+error);
        _data.putExtra("positive", false);
        setResult(RESULT_CANCELED, _data);
        finish();
    }

    public void getSortStr(Button button, int i){
        String s = sortArr.get(i) + "";
        button.setText(s);
    }

    public void scatterAlgorithm(){
        int numArr[][] = {
                {1,2,3},
                {4,5,6},
                {7,8,9},
                {0,0,0}
        };
        int rand1 = (int) Math.floor(Math.random() * 4);
        int rand2 = (int) Math.floor(Math.random() * 3);
        int temp = numArr[rand1][rand2];
        if (!isPresent(temp)){
            sortArr.add(temp);
        }
        if (sortArr.size() < 10) {
            scatterAlgorithm();
        }
    }

    public boolean isPresent(int temp){
        for (int i = 0; i < sortArr.size(); i++ ){
            if (sortArr.get(i) == temp){
                return true;
            }
        }
        return false;
    }


    public void onButtonPressed(Button btnVal, final String value){
        btnVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pinContent = pinInput.getText().toString();
                if (pinInput.getText().toString().length() > 0) {
                    if( pinInput.getText().toString().length() < 4) {
                        pinInput.setText(pinContent + value);
                    }else{
                        Toast.makeText(PinActivity.this, "PIN must be 4 digits long", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    pinInput.setText(value);
                }
                beep(1);
            }
        });
    }

    private void beep(int iterator) {
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
    }


    @Override
    public void onBackPressed() {
//        threadRunT = false;
//        cardWatcherThread.isInterrupted();
//        finishActivityWithErrorMsg("Transaction aborted");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        threadRunT = false;
        cardWatcherThread.isInterrupted();
//        finishActivityWithErrorMsg("Transaction aborted");
//        ((TimeOutController) getApplication()).cancelTimer();
    }
}
