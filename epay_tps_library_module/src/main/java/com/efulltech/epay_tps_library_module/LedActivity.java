package com.efulltech.epay_tps_library_module;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.led.Led900;

public class LedActivity extends Activity {
    private final String TAG = "LedActivity";

    private Button ledOnBtn     = null;
    private Button ledOffBtn    = null;
    private Button ledBlinkBtn  = null;
    private EditText periodText = null;
    private Spinner ledItem     = null;
    private int lenInex = 1;
    Led900 led = new Led900(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_led900);

        ledItem = (Spinner) findViewById(R.id.ledSpinner);
        ledOnBtn = (Button) findViewById(R.id.onBtn);
        ledOffBtn = (Button) findViewById(R.id.offBtn);
        ledBlinkBtn = (Button) findViewById(R.id.periodBtn);
        periodText = (EditText) findViewById(R.id.editPeriod);

        ledItem.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                lenInex = 1;
            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String[] ledChoice = getResources().getStringArray(R.array.led_item);
                lenInex = Integer.parseInt(ledChoice[position]);
            }
        });

        ledOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    led.on(lenInex);
                } catch (TelpoException e) {
                    e.printStackTrace();
                }
            }
        });

        ledBlinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    led.blink(lenInex, Integer.parseInt(periodText.getText().toString()));
                } catch (TelpoException e) {
                    e.printStackTrace();
                }
            }
        });

        ledOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    led.off(lenInex);
                } catch (TelpoException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void onDestroy() {

        super.onDestroy();
    }
}
