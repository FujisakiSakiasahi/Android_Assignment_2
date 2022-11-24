package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.databinding.RecentScoresBinding;

public class RecentScoreActivity extends AppCompatActivity {
    private RecentScoresBinding binding = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_scores);

        binding.buttonBack.setOnClickListener(view -> finish());

        Intent intent = getIntent();

    }
}
