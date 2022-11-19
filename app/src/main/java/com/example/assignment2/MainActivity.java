package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.assignment2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding = null;

    private int score = 0;
    private boolean gameStarted = false;
    private CountDownTimer timer;
    private final long INIT_COUNT = 60000; //60 seconds
    private long remaining_time = INIT_COUNT;
    private final long INTERVAL = 1000; //1 second

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonClick.setOnClickListener(view -> incrementScore());

        if (!gameStarted) {
            resetGame();
        }//end if

    }//end onCreate

    public void saveScore(String name){

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
        remaining_time = INIT_COUNT;

        String text = getString(R.string.score, score);
        binding.textScore.setText(text);

        String timeLeft = getString(R.string.time_left, 60);
        binding.textTime.setText(timeLeft);

        gameStarted = false;

        timer = new CountDownTimer(remaining_time, INTERVAL) {
            @Override
            public void onTick(long l) {
                int second = (int) l / 1000;
                String timeLeft = getString(R.string.time_left, second);
                binding.textTime.setText(timeLeft);
                remaining_time = l;
            }

            @Override
            public void onFinish() {
                endGame();
            }
        };

    }

    private void endGame(){
        DisplayScore dialog = new DisplayScore();
        dialog.setScore(score);
        if(!getSupportFragmentManager().isDestroyed()){
            dialog.show(getSupportFragmentManager(), "ShowDialog");
        }else{
            Log.d("Error", "Fragment Manager was destroyed");
        }

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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("START", gameStarted);
        outState.putLong("TIME_LEFT", remaining_time);
        outState.putInt("SCORE", score);


    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        gameStarted = savedInstanceState.getBoolean("START");
        score = savedInstanceState.getInt("SCORE");
        remaining_time = savedInstanceState.getLong("TIME_LEFT");

        String text = getString(R.string.score, score);
        binding.textScore.setText(text);

        if(gameStarted){
            timer = new CountDownTimer(remaining_time, INTERVAL) {
                @Override
                public void onTick(long l) {
                    int second = (int) l / 1000;
                    String timeLeft = getString(R.string.time_left, second);
                    binding.textTime.setText(timeLeft);
                    remaining_time = l;
                }

                @Override
                public void onFinish() {
                    endGame();
                }
            }.start();
        }
    }
}