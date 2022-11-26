package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.databinding.RecentScoresBinding;

public class RecentScoreActivity extends AppCompatActivity {
    private RecentScoresBinding binding = null;

    private ScoreList scoreList = new ScoreList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_scores);

        //set up view binding
        binding = RecentScoresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //set onclick listener for the button to end this activity
        binding.buttonBackRs.setOnClickListener(view -> finish());

        //get the pass in intent
        Intent intent = getIntent();

        //retrieve the scoreList from the pass in intent parcelable
        scoreList = (ScoreList) intent.getParcelableExtra("SCORE_LIST");

        //set the adapter for the list item view
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scoreList.getRecentScores());

        //set the adapter to the list item view
        binding.listviewRecentScores.setAdapter(adapter);

    }
}
