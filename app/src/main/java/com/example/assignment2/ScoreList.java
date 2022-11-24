package com.example.assignment2;

import java.util.List;
import java.util.ArrayList;

public class ScoreList {
    private List<Score> recentScoreList = new ArrayList<Score>();
    private List<Score> topScoreList = new ArrayList<Score>();

    public void addScore(int score, String name) {
        Score score0 = new Score(score, name);

        recentScoreList.add(0, score0);

        if (recentScoreList.size() > 10) {
            recentScoreList.remove(9);
        }

    }

    public int getScore(int index) {
        if(index+1 <= recentScoreList.size()){
            return recentScoreList.get(index).getScore();
        }
        return -1;
    }

    public String getName(int index) {
        if(index+1 <= recentScoreList.size()){
            return recentScoreList.get(index).getName();
        }
        return "RECORD NOT FOUND";
    }

    public int getLength(){
        return recentScoreList.size();
    }
}
