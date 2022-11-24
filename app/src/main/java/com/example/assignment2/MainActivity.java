package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.assignment2.databinding.ActivityMainBinding;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fileHandler = new FileHandler(getApplicationContext(), SCORE_FILE_NAME);

        loadScoreList();

        binding.buttonClick.setOnClickListener(view -> incrementScore());

        if (!gameStarted) {
            resetGame();
        }//end if

    }//end onCreate

    @Override
    protected void onStop() {
        super.onStop();
        saveScoreList();
    }

    public void loadScoreList(){
        for (int i = 0; i<fileHandler.loadData().length;i++){
            String[] record = fileHandler.loadData()[i].split("\\s+");
            scoreList.addScore(Integer.parseInt(record[1]), record[0]);
        }
        fileHandler.loadData();

//        try {
//            FileInputStream fis = openFileInput(SCORE_FILE_NAME);
//            Scanner sc = new Scanner(fis);
//
//            while(sc.hasNextLine()){
//                String[] record = sc.nextLine().split("\\s+");
//                scoreList.addScore(Integer.parseInt(record[1]), record[0]);
//            }
//
//            sc.close();
//            fis.close();
//        }catch (FileNotFoundException e){
//            Log.d("Error", "No data found");
//        }catch (Exception e){
//            Log.d("Error", e.getLocalizedMessage());
//        }
    }

    public void saveScore(String name){ //save score into ScoreList object
        scoreList.addScore(score, name);
    }

    public void saveScoreList(){
//        //create string array that need to be saved
//        String[] saveString = new String[1];
//
//        for (int i = 0 ; i < 10 ; i++) {
//            ps.println(scoreList.getName(i) + " " + scoreList.getScore(i));}
//
//        //add item into string array
//        saveString[0] = "Skip Splash Screen = " + settingBinding.checkboxSplashScreen.isChecked();
//
//        //save the setting status
//        fileHandler.setData(saveString);
//        fileHandler.saveData();


//        try {
//            FileOutputStream fos = openFileOutput(SCORE_FILE_NAME, MODE_PRIVATE);
//
//            PrintStream ps = new PrintStream(fos);
//
//            for (int i = 0 ; i < 10 ; i++) {
//                ps.println(scoreList.getName(i) + " " + scoreList.getScore(i));
//            }
//
//            fos.close();
//            ps.close();
//        } catch (FileNotFoundException e) {
//            Log.d("Error", "File is missing");
//        } catch (Exception e){
//            Log.d("Error", e.getLocalizedMessage());
//        }
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
//        DisplayScore dialog = new DisplayScore();
//        dialog.setScore(score);
//        if(!getSupportFragmentManager().isDestroyed()){
//            dialog.show(getSupportFragmentManager(), "ShowDialog");
//        }else{
//            Log.d("Error", "Fragment Manager was destroyed");
//        }
        //for debug purpose as the custom dialog crash the app
        saveScore("test2");

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
                break;
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