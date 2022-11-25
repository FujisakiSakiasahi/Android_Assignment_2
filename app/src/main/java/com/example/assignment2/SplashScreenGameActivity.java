package com.example.assignment2;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenGameActivity extends AppCompatActivity {

    private final long INIT_COUNT = 6000; //6 seconds
    private long remaining_time = INIT_COUNT;
    private final long INTERVAL = 1000; //1 second
    private ImageView imageView_game;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_game);

        imageView_game = findViewById(R.id.imageView_game); // set reference

        // set animation reference and their durations
        final Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        final Animation fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        fadeIn.setDuration(1500);
        fadeOut.setDuration(1500);

        imageView_game.setAnimation(fadeIn); // start fade in

        CountDownTimer t = new CountDownTimer(remaining_time, INTERVAL) {
            @Override
            public void onTick(long l) {
                int second = (int) l / 1000;
                remaining_time = l;

                if (second == 1) {
                    // start fade out and hide image view
                    imageView_game.setAnimation(fadeOut);
                    imageView_game.setVisibility(View.INVISIBLE);
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
