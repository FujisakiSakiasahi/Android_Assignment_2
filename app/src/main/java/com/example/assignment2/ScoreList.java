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

        for (int i = 0 ; i < topScoreList.size() ; i++){
            if(topScoreList.get(i).getScore() <= score){
                topScoreList.add(i, score0);
                if (topScoreList.size() > 10) {
                    topScoreList.remove(9);
                }
            }
        }
    }

    public int getRecentScore(int index) {
        if(index+1 <= recentScoreList.size()){
            return recentScoreList.get(index).getScore();
        }
        return -1;
    }

    public String getRecentName(int index) {
        if(index+1 <= recentScoreList.size()){
            return recentScoreList.get(index).getName();
        }
        return "RECORD NOT FOUND";
    }

    public int getRecentLength(){
        return recentScoreList.size();
    }

    public int getTopScore(int index) {
        if(index+1 <= topScoreList.size()){
            return topScoreList.get(index).getScore();
        }
        return -1;
    }

    public String getTopName(int index) {
        if(index+1 <= topScoreList.size()){
            return topScoreList.get(index).getName();
        }
        return "RECORD NOT FOUND";
    }

    public int getTopLength(){
        return topScoreList.size();
    }
}
