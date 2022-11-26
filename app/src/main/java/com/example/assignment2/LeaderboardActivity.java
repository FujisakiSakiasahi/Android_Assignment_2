package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.databinding.LeaderboardBinding;

public class LeaderboardActivity extends AppCompatActivity {
    private LeaderboardBinding binding = null;

    private ScoreList scoreList = new ScoreList();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        //set up view binding
        binding = LeaderboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //get the pass in intent
        Intent intent = getIntent();

        //retrieve the scoreList pass in through the intent parcelable
        scoreList = (ScoreList) intent.getParcelableExtra("SCORE_LIST");
        Log.d("DISPLAYING", String.valueOf(scoreList.getTopLength()));

        //set the adapter for the list item view
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scoreList.getTopScores());

        //set the adapter to the list item view
        binding.listviewHighscore.setAdapter(adapter);

        //set on click listener for the button that navigates to the recent score activity
        binding.buttonRecent.setOnClickListener(view -> {
            Intent recIntent = null;
            recIntent = new Intent(getApplicationContext(), RecentScoreActivity.class);
            recIntent.putExtra("SCORE_LIST", scoreList);
            startActivity(recIntent);
        });
    }
}
