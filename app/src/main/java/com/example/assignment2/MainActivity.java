package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;

import com.example.assignment2.databinding.ActivityMainBinding;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    //binding declaration
    private ActivityMainBinding binding = null;

    private int score = 0;
    private boolean gameStarted = false;
    private CountDownTimer timer;
    private final long INIT_COUNT = 10000; //60 seconds
    private long remaining_time = INIT_COUNT;
    private final long INTERVAL = 1000; //1 second

    private final static String SCORE_FILE_NAME = "score.txt";
    private FileHandler fileHandler;
    ScoreList scoreList = new ScoreList();

    //create the media player object
    MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding declaration
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initialize the file handler
        fileHandler = new FileHandler(getApplicationContext(), SCORE_FILE_NAME);

        //load the score list
        loadScoreList();



        //game function piece
        binding.buttonClick.setOnClickListener(view -> {
            incrementScore();
            popSoundHandler();
        });

        if (!gameStarted) {
            resetGame();
        }//end if

    }//end onCreate

    private void popSoundHandler() {
        mediaPlayer = MediaPlayer.create(this, R.raw.pop);
        if (!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }else{
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(this, R.raw.pop);
        }

        mediaPlayer.setOnCompletionListener(mediaPlayer -> {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveScoreList();
    }

    public void loadScoreList(){
        if (new File(getApplicationContext().getFilesDir(), SCORE_FILE_NAME).exists()){
            int dataArrayLength = fileHandler.loadData().length;

            for (int i = 0; i < dataArrayLength; i++){
                String[] record = fileHandler.loadData()[i].split(", ");
                scoreList.addScore(Integer.parseInt(record[1]), record[0]);
            }
        }else{
            saveScoreList();
        }
    }

    public void saveScore(String name){ //save score into ScoreList object
        scoreList.addScore(score, name);
    }

    public void saveScoreList(){
        //create string array that need to be saved
        String[] saveString = new String[scoreList.getLength()];

        for (int i = 0 ; i < 10 ; i++) {
            if(scoreList.getScore(i) != -1){
                //add item into string array
                saveString[i] = scoreList.getName(i) + ", " + scoreList.getScore(i);
            }else{
                break;
            }
        }

        //save the setting status
        fileHandler.setData(saveString);
        fileHandler.saveData();
    }

    private void incrementScore(){

        if (!gameStarted) startGame();

        score++;

        String text = getString(R.string.score, score);
        binding.textScore.setText(text);

    }//end of incrementScore

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
        showEndGameDialog();
        resetGame();
    }

    private void showEndGameDialog() {
        DisplayScore dialog = new DisplayScore();
        dialog.setScore(score);
        dialog.show(getSupportFragmentManager(), "ShowDialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //create menu object & place it on then action bar
        getMenuInflater().inflate(R.menu.menu_layout, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()){
            case R.id.menu_setting:
                 intent = new Intent(getApplicationContext(), SettingActivity.class);
                break;
            case R.id.menu_scoreboard:
                //intent = new Intent(getApplicationContext(), SettingActivity.class);
                break;
            case R.id.menu_taktaulagi:
                //intent = new Intent(getApplicationContext(), SettingActivity.class);
                showEndGameDialog();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("START", gameStarted);
        outState.putLong("TIME_LEFT", remaining_time);
        outState.putInt("SCORE", score);

        timer.cancel();
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