package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.databinding.LeaderboardBinding;
import com.example.assignment2.databinding.SettingBinding;

public class LeaderboardActivity extends AppCompatActivity {
    private LeaderboardBinding binding = null;

    private ScoreList scoreList = new ScoreList();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        binding = LeaderboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        scoreList = (ScoreList) intent.getParcelableExtra("SCORE_LIST");
        Log.d("DISPLAYING", String.valueOf(scoreList.getTopLength()));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scoreList.getTopScores());

        binding.listviewHighscore.setAdapter(adapter);

        binding.buttonRecent.setOnClickListener(view -> {
            Intent recIntent = null;
            recIntent = new Intent(getApplicationContext(), RecentScoreActivity.class);
            recIntent.putExtra("SCORE_LIST", scoreList);
            startActivity(recIntent);
        });

    }
}
