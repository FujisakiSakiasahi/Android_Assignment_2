package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.databinding.LeaderboardBinding;
import com.example.assignment2.databinding.SettingBinding;

public class LeaderboardActivity extends AppCompatActivity {
    private LeaderboardBinding binding = null;

    private ScoreList scoreList = new ScoreList();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.leaderboard);

        binding = LeaderboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        scoreList = (ScoreList) intent.getParcelableExtra("SCORE_LIST");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scoreList.getTopScores());

        binding.listviewHighscore.setAdapter(adapter);

    }
}
