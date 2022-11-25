package com.example.assignment2;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenDevActivity extends AppCompatActivity {

    private final long INIT_COUNT = 6000; //6 seconds
    private long remaining_time = INIT_COUNT;
    private final long INTERVAL = 1000; //1 second

    private ImageView imageView_dev;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_dev);

        imageView_dev = findViewById(R.id.imageView_dev);

        final Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        fadeIn.setDuration(1500);

        final Animation fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        fadeOut.setDuration(1500);

        imageView_dev.setAnimation(fadeIn);
        CountDownTimer t = new CountDownTimer(remaining_time, INTERVAL) {
            @Override
            public void onTick(long l) {
                int second = (int) l / 1000;
                remaining_time = l;
                if (second == 1) {
                    imageView_dev.setAnimation(fadeOut);
                    imageView_dev.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFinish() {
                finish();
            }
        };

        t.start();

    }
}
