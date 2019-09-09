package com.efulltech.epay_tps_library_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PinActivity extends BaseActivity {

    RecyclerView mRecycler;
    PinRecycler pinRecycler;
    ArrayList<PinClass> pinClasses;

    TextView pinInput;

    SmartCardReaderx readerx;
    boolean threadRunT;
    String no = "";
    boolean turnedOn;

    ArrayList<Integer> sortArr;
    Button one, two, three, four, five, six, seven, eight, nine, zero;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

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

        pinInput = findViewById(R.id.pinInput);

        sortArr = new ArrayList<>();

        readerx = new SmartCardReaderx(PinActivity.this);

        sharedPreferences = getSharedPreferences("SessionController", MODE_PRIVATE);

        threadRunT = true;

        TextView txtArr[] = {one, two, three, four, five, six, seven, eight, nine, zero};


        scatterAlgorithm();

        for (int i = 0; i < 10; i++){
            getSortStr(txtArr[i], i);
        }


        onButtonPressed(one, "1");
        onButtonPressed(two, "2");


        new Thread(new Runnable() {
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
                            Thread.currentThread().isInterrupted();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Thread.currentThread().isInterrupted();
                    }
                }
            }
        }).start();

    }

    public void getSortStr(TextView button, int i){
        String s = sortArr.get(i) + "";
        button.setText(s);
    }

    public boolean isPresent(int temp){
        for (int i = 0; i < sortArr.size(); i++ ){
            if (sortArr.get(i) == temp){
                return true;
            }
        }
        return false;
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

    public void onButtonPressed(Button btnVal, final String value){
        if (pinInput.getText().toString().length() > 0) {
            final String pinContent = pinInput.getText().toString();
            btnVal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pinInput.setText(pinContent + value);
                }
            });

        }else {
            pinInput.setText(value);
        }
    }
}
