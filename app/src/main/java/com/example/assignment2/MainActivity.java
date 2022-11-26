package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.assignment2.databinding.ActivityMainBinding;

import java.io.File;

public class MainActivity<pricate> extends AppCompatActivity {
    //binding declaration
    private ActivityMainBinding binding = null;

    private int score = 0;
    private boolean gameStarted = false;
    private CountDownTimer timer;
    private final long INIT_COUNT = 10000; //60 seconds
    private long remaining_time = INIT_COUNT;
    private final long INTERVAL = 1000; //1 second
    private static boolean appStarted = false;
    private static boolean skipSplashArt = false;
    private static boolean settingChanged = false;

    private final static String RECENT_SCORE_FILE_NAME = "recent_score.txt";
    private final static String TOP_SCORE_FILE_NAME = "top_score.txt";
    public final static String SETTING_FILE_NAME = "setting.txt";
    private FileHandler fileHandler;
    ScoreList scoreList = new ScoreList();

    //create the media player object
    MediaPlayer mediaPlayer = null;
    private static boolean soundEffectOnOFF;
    private static int soundEffectVolume;

    Animation scaleUp, scaleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load the settings
        loadSettings();

        if(!skipSplashArt && !appStarted){ // calls splash screen depending on setting, NOTE: starts from bottom to top
            appStarted = true;

            Intent intent = new Intent(getApplicationContext(), SplashScreenGameActivity.class); // show game name splash screen
            startActivity(intent);

            intent = new Intent(getApplicationContext(), SplashScreenDevActivity.class); // show developer name splash screen
            startActivity(intent);
        }

        //binding declaration
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //load the score list
        loadScoreList();

        // load button scale animation
        loadAnimation();

        //game function piece
        binding.buttonClick.setOnClickListener(view -> {
            incrementScore();

            //refresh the settings
            if(settingChanged){
                loadSettings();
            }
            //control the pop sound
            if (soundEffectOnOFF){
                popSoundHandler();
            }
        });

        binding.buttonClick.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    binding.buttonClick.startAnimation(scaleDown);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    binding.buttonClick.setAnimation(scaleUp);
                }

                return false;
            }
        });

        if (!gameStarted) {
            resetGame();
        }//end if

    }//end onCreate

    private void loadAnimation() {
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);
    }

    @Override
    protected void onStop() {
        super.onStop();

        //save the score into the file
        saveScoreList();
    }//end onStop


    /**
     * loadSettings: <br/>
     * - use to load the settings <br/>
     * - if the file not exist it will create a new one
     */
    public void loadSettings() {
        //setup the FileHandler
        fileHandler = new FileHandler(getApplicationContext(), SETTING_FILE_NAME);
        //check if file exist
        if(new File(getApplicationContext().getFilesDir(), SETTING_FILE_NAME).exists()){

            //load the skip splash art setting
            String[] a = fileHandler.loadData();
            String[] b = a[0].split( " = " );
            skipSplashArt = Boolean.parseBoolean(b[1]);

            //load the sound effect on off setting
            a = fileHandler.loadData();
            b = a[1].split(" = ");
            soundEffectOnOFF = Boolean.parseBoolean(b[1]);

            //load the sound effect volume setting
            a = fileHandler.loadData();
            b = a[2].split(" = ");
            soundEffectVolume = Integer.parseInt(b[1]);
        }else{
            //create string array that need to be saved
            String[] saveString = new String[3];

            //add item into string array
            saveString[0] = "Skip Splash Screen = false";
            saveString[1] = "Sound Effect = true";
            saveString[2] = "Sound Effect Volume = 50";

            //create and write the file
            fileHandler.setData(saveString);
            fileHandler.saveData();

            //set the settings value
            soundEffectOnOFF = true;
            skipSplashArt = false;
            soundEffectVolume = 50;
        }
    }

    /**
     * popSoundHandler: <br/>
     * - use to play the pop sound
     */
    private void popSoundHandler() {
        //reference to the pop sound raw resource file
        mediaPlayer = MediaPlayer.create(this, R.raw.pop);

        //set pop sound volume
        mediaPlayer.setVolume((float)soundEffectVolume/100, (float)soundEffectVolume/100);

        if (!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }else{
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(this, R.raw.pop);
        }

        //use to reset the media player when it finish playing the sound
        mediaPlayer.setOnCompletionListener(mediaPlayer -> {
            mediaPlayer.reset();
            mediaPlayer.release();
        });
    }


    /**
     * loadScoreList: <br/>
     * - use to load the score list
     */
    public void loadScoreList(){
        // here1
        // Log.d("score", "loadScoreList: Called");

        //initialize the file handler: for recent score file name
        fileHandler = new FileHandler(getApplicationContext(), RECENT_SCORE_FILE_NAME);

        //check if file exist
        if (new File(getApplicationContext().getFilesDir(), RECENT_SCORE_FILE_NAME).exists()){
            int dataArrayLength = fileHandler.loadData().length;

            if(dataArrayLength != 0){
                //load the data into score list
                for (int i = dataArrayLength; i >= 0; i--){
                    String[] record = fileHandler.loadData()[i].split(", ");
                    scoreList.addRecentScore(Integer.parseInt(record[1]), record[0]);
                }
            }
        }else{
            saveScoreList();
        }

        //initialize the file handler: for top score file name
        fileHandler = new FileHandler(getApplicationContext(), TOP_SCORE_FILE_NAME);

        //check if file exist
        if (new File(getApplicationContext().getFilesDir(), TOP_SCORE_FILE_NAME).exists()){
            int dataArrayLength = fileHandler.loadData().length;

            //load the data into score list
            for (int i = 0; i < dataArrayLength; i++){
                String[] record = fileHandler.loadData()[i].split(", ");
                scoreList.addTopScore(Integer.parseInt(record[1]), record[0]);
            }
        }else{
            saveScoreList();
        }
    }

    /**
     * saveScore: <br/>
     * - use to add score with respective player name into the scoreList
     *
     * @param name player name
     * @param score final score of the game
     */
    public void saveScore(String name, int score){ //save score into ScoreList object
        // here2
        // Log.d("score", "saveScore: Called");
        scoreList.addRecentScore(score, name);
        scoreList.addTopScore(score, name);
    }

    /**
     * saveScoreList: <br/>
     * - use to save the score list into a file
     */
    public void saveScoreList(){
        //create string array that need to be saved
        String[] saveStringRecent = new String[scoreList.getRecentLength()];
        String[] saveStringTop = new String[scoreList.getTopLength()];

        //generating the data string array that will be stored in the recent score file
        for (int i = 0 ; i < 10 ; i++) {
            if(scoreList.getRecentScore(i) != -1){
                //add item into string array
                saveStringRecent[i] = scoreList.getRecentName(i) + ", " + scoreList.getRecentScore(i);
            }else{
                break;
            }
        }

        //generating the data string array that will be stored in the top score file
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


    /**
     * incrementScore: <br/>
     * - use to start the game if the game is not started <br/>
     * - use to increase the score by one
     */
    private void incrementScore(){
        //start the game
        if (!gameStarted) startGame();

        //increase the score
        score++;

        //update the text view that display score
        String text = getString(R.string.score, score);
        binding.textScore.setText(text);

    }//end of incrementScore

    /**
     * startGame: <br/>
     * - start the game
     */
    private void startGame() {
        //start the timer
        timer.start();
        //update the gameStarted status
        gameStarted = true;
    }

    /**
     * resetGame: <br/>
     * - reset the timer, score and so on
     */
    private void resetGame(){
        score = 0;
        remaining_time = INIT_COUNT;

        //reset score displaying text view
        String text = getString(R.string.score, score);
        binding.textScore.setText(text);

        //reset time remaining displaying text view
        String timeLeft = getString(R.string.time_left, 60);
        binding.textTime.setText(timeLeft);

        //reset gameStarted status
        gameStarted = false;

        //reset timer
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

    /**
     * endGame: <br/>
     * - use to reset the game and call the end game dialog
     */
    private void endGame(){
        showEndGameDialog();
        resetGame();
    }

    /**
     * showEndGameDialog: <br/>
     * - use to display the custom dialog
     */
    private void showEndGameDialog() {
        DisplayScore dialog = new DisplayScore();
        dialog.setScore(score);
        dialog.show(getSupportFragmentManager(), "ShowDialog");
    }

    /**
     * onCreateOptionsMenu: <br/>
     * - set up the menu by linking it to the menu layout
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //create menu object & place it on then action bar
        getMenuInflater().inflate(R.menu.menu_layout, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * onOptionsItemSelected: <br/>
     * - set the listener to the menu for open the respective activity
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        //assign the selected activity
        switch (item.getItemId()){
            case R.id.menu_setting:
                 intent = new Intent(getApplicationContext(), SettingActivity.class);
                 settingChanged = true;
                break;
            case R.id.menu_recent_score:
                intent = new Intent(getApplicationContext(), RecentScoreActivity.class);
                intent.putExtra("SCORE_LIST", scoreList);
                break;
            case R.id.menu_leaderboard:
                intent = new Intent(getApplicationContext(), LeaderboardActivity.class);
                intent.putExtra("SCORE_LIST", scoreList);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        //start the activity
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    /**
     * onSaveInstanceState: <br/>
     * - use to save the required data to prevent data lost when rotating the screen
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //saving the data
        outState.putBoolean("START", gameStarted);
        outState.putLong("TIME_LEFT", remaining_time);
        outState.putInt("SCORE", score);

        //cancel the old timer
        timer.cancel();
    }

    /**
     * onRestoreInstanceState: <br/>
     * - use to load the required data that has been saved when the screen rotate
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //get the data
        gameStarted = savedInstanceState.getBoolean("START");
        score = savedInstanceState.getInt("SCORE");
        remaining_time = savedInstanceState.getLong("TIME_LEFT");

        //restore the score
        String text = getString(R.string.score, score);
        binding.textScore.setText(text);

        //restore the timer
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