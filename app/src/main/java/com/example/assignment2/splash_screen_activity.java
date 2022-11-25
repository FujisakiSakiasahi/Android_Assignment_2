package com.example.assignment2;

import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;

public class splash_screen_activity extends AppCompatActivity {

    private final long INIT_COUNT = 5000; //5 seconds
    private long remaining_time = INIT_COUNT;
    private final long INTERVAL = 1000; //1 second

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);



        CountDownTimer t = new CountDownTimer(remaining_time, INTERVAL) {
            @Override
            public void onTick(long l) {
                int second = (int) l / 1000;
                remaining_time = l;
            }

            @Override
            public void onFinish() {
                finish();
            }
        };

        t.start();

    }

}
