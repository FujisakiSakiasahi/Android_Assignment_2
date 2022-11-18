package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.assignment2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private int score = 0;
    private boolean gameStarted = false;
    private CountDownTimer timer;
    private final long INIT_COUNT = 60000; //60 seconds
    private final long INTERVAL = 1000; //1 second

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonClick.setOnClickListener(view -> {
            incrementScore();
        });

        resetGame();

    }

    private void incrementScore(){

        if (!gameStarted) startGame();

        score++;

        String text = getString(R.string.score, score);
        binding.textScore.setText(text);

    }

    private void startGame() {
        timer.start();
        gameStarted = true;
    }

    private void resetGame(){
        score = 0;

        String text = getString(R.string.score, score);
        binding.textScore.setText(text);

        String timeLeft = getString(R.string.time_left, 60);
        binding.textTime.setText(timeLeft);

        gameStarted = false;

        timer = new CountDownTimer(INIT_COUNT, INTERVAL) {
            @Override
            public void onTick(long l) {
                int second = (int) l / 1000;
                String timeLeft = getString(R.string.time_left, second);
                binding.textTime.setText(timeLeft);
            }

            @Override
            public void onFinish() {
                endGame();
            }
        };

    }

    private void endGame(){
        Toast.makeText(this, "Game Ended", Toast.LENGTH_LONG).show();

        resetGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //create menu object & place it on then action bar
        getMenuInflater().inflate(R.menu.menu_layout, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_setting:

                break;
            case R.id.menu_scoreboard:

                break;
            case R.id.menu_taktaulagi:

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}