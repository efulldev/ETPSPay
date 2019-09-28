package com.efulltech.epay_tps_library_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.efulltech.efull_nibss_bridge.Transaction;

import java.util.ArrayList;
import java.util.List;

public class PinActivity extends BaseActivity {

    public Thread cardWatcherThread;
    boolean threadRunT, turnedOn;
    private String cardType;
    private int accType;
    private String amount;

    RecyclerView mRecycler;
    PinRecycler pinRecycler;
    ArrayList<PinClass> pinClasses;
    SmartCardReaderx readerx;
    String no = "";
    ArrayList<Integer> sortArr;
    Button one, two, three, four, five, six, seven, eight, nine, zero, cancel, clear, enter;
    TextView pinInput, custNameText, cardTypeText, accTypeText, amountText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        // get values passed via intent
        Intent intent = getIntent();
        this.cardType = intent.getStringExtra("cardType");
        this.accType = intent.getIntExtra("accType", 0);
        this.amount = intent.getStringExtra("amount");

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
        sharedPreferences = getSharedPreferences("SessionController", MODE_PRIVATE);
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
                try{
                    turnedOn = readerx.iccPowerOn(1);
                }catch (Exception e){
                    e.printStackTrace();
                }
                while (threadRunT){
                    Log.d("ICC status", "Extra Running");
//                    Log.d("Card type", Integer.toString(readerx.getCardType()));
                    try{
                        if (readerx.iccPowerOff()){
                            Log.d("Card Activity", "Powered on");
                        }else {
                            Log.d("Card log error", "Card turned off");
                            threadRunT = false;
                            if (sharedPreferences.getString("sessionState", "sessionLoggedIn") != "sessionLogOut") {
                                new CardRemovedFragment().show(getSupportFragmentManager(), "Cardremoved");
                            }
                            cardWatcherThread.isInterrupted();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        cardWatcherThread.isInterrupted();
                        threadRunT = false;
                    }
                }
            }
        });
        cardWatcherThread.start();

        // cancel pin button click listener
        cancel.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beep(1);
                finish();
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
                // card type
//                Transaction transaction = new Transaction();
//                transaction.setAccType(accType);
//                transaction.setCardType(cardType);
            }
        }));
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
                        beep(3);
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
        super.onBackPressed();
        threadRunT = false;
        cardWatcherThread.isInterrupted();
        ((TimeOutController) getApplication()).cancelTimer();
        new CardErrorFragment("Card Error").show(getSupportFragmentManager(), "Please eject your card");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        threadRunT = false;
        cardWatcherThread.isInterrupted();
        ((TimeOutController) getApplication()).cancelTimer();
    }
}
