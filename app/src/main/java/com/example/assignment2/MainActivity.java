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

    private final static String RECENT_SCORE_FILE_NAME = "recent_score.txt";
    private final static String TOP_SCORE_FILE_NAME = "top_score.txt";
    public final static String SETTING_FILE_NAME = "setting.txt";
    private FileHandler fileHandler;
    ScoreList scoreList = new ScoreList();

    //create the media player object
    MediaPlayer mediaPlayer = null;
    private static boolean soundEffectOnOFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding declaration
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //load the score list
        loadScoreList();

        //load setting status
        fileHandler = new FileHandler(getApplicationContext(), SETTING_FILE_NAME);
        soundEffectOnOFF = Boolean.parseBoolean(fileHandler.loadData()[1].split(" = ")[1]);

        //game function piece
        binding.buttonClick.setOnClickListener(view -> {
            incrementScore();
            if (soundEffectOnOFF){
                popSoundHandler();
            }
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
        //initialize the file handler: for recent score file name
        fileHandler = new FileHandler(getApplicationContext(), RECENT_SCORE_FILE_NAME);

        if (new File(getApplicationContext().getFilesDir(), RECENT_SCORE_FILE_NAME).exists()){
            int dataArrayLength = fileHandler.loadData().length;

            for (int i = 0; i < dataArrayLength; i++){
                String[] record = fileHandler.loadData()[i].split(", ");
                scoreList.addRecentScore(Integer.parseInt(record[1]), record[0]);
            }
        }else{
            saveScoreList();
        }

        //initialize the file handler: for top score file name
        fileHandler = new FileHandler(getApplicationContext(), TOP_SCORE_FILE_NAME);

        if (new File(getApplicationContext().getFilesDir(), TOP_SCORE_FILE_NAME).exists()){
            int dataArrayLength = fileHandler.loadData().length;

            for (int i = 0; i < dataArrayLength; i++){
                String[] record = fileHandler.loadData()[i].split(", ");
                scoreList.addRecentScore(Integer.parseInt(record[1]), record[0]);
            }
        }else{
            saveScoreList();
        }
    }

    public void saveScore(String name){ //save score into ScoreList object
        scoreList.addRecentScore(score, name);
        scoreList.addTopScore(score, name);
    }

    public void saveScoreList(){
        //create string array that need to be saved
        String[] saveStringRecent = new String[scoreList.getRecentLength()];
        String[] saveStringTop = new String[scoreList.getTopLength()];

        for (int i = 0 ; i < 10 ; i++) {
            if(scoreList.getRecentScore(i) != -1){
                //add item into string array
                saveStringRecent[i] = scoreList.getRecentName(i) + ", " + scoreList.getRecentScore(i);
            }else{
                break;
            }
        }

        for (int i = 0 ; i < 10 ; i++) {
            if(scoreList.getTopScore(i) != -1){
                //add item into string array
                saveStringTop[i] = scoreList.getTopName(i) + ", " + scoreList.getTopScore(i);
            }else{
                break;
            }
        }

        //save the score status
        fileHandler = new FileHandler(getApplicationContext(), RECENT_SCORE_FILE_NAME);
        fileHandler.setData(saveStringRecent);
        fileHandler.saveData();

        //save the score status
        fileHandler = new FileHandler(getApplicationContext(), TOP_SCORE_FILE_NAME);
        fileHandler.setData(saveStringTop);
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