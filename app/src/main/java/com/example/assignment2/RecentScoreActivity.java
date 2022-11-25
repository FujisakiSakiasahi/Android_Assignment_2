package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.databinding.LeaderboardBinding;
import com.example.assignment2.databinding.RecentScoresBinding;

public class RecentScoreActivity extends AppCompatActivity {
    private RecentScoresBinding binding = null;

    private ScoreList scoreList = new ScoreList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_scores);

        binding = RecentScoresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonBack.setOnClickListener(view -> finish());

        Intent intent = getIntent();

        scoreList = (ScoreList) intent.getParcelableExtra("SCORE_LIST");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scoreList.getRecentScores());

        binding.listviewRecentScores.setAdapter(adapter);

    }
}
