package com.efulltech.epay_tps_library_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PinActivity extends AppCompatActivity {

    RecyclerView mRecycler;
    PinRecycler pinRecycler;
    ArrayList<PinClass> pinClasses;

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

        sortArr = new ArrayList<>();

//        pinRecycler = new PinRecycler(this, pinClasses);
//        GridLayoutManager gridLayoutManager =new GridLayoutManager(this,3);
//        mRecycler.setLayoutManager(gridLayoutManager);
//        mRecycler.setAdapter(pinRecycler);

        TextView txtArr[] = {one, two, three, four, five, six, seven, eight, nine, zero};


        scatterAlgorithm();

//        pinClasses.add(new PinClass("2"));

//        for (int i = 0; i < sortArr.size(); i++){
//            pinClasses.add(new PinClass(sortArr.get(i) + ""));
//        }

//        getSortStr(one, 0);
//        getSortStr(two, 1);
//        getSortStr(three, 2);
//        getSortStr(four, 3);
//        getSortStr(five, 4);
//        getSortStr(six, 5);

        for (int i = 0; i < 10; i++){
            getSortStr(txtArr[i], i);
        }

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
}
