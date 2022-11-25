package com.example.assignment2;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Array;
import java.util.List;
import java.util.ArrayList;

public class ScoreList implements Parcelable {
    private List<Score> recentScoreList = new ArrayList<Score>();
    private List<Score> topScoreList = new ArrayList<Score>();

    public void addRecentScore(int score, String name) {
        Score score0 = new Score(score, name);

        recentScoreList.add(0, score0);

        if (recentScoreList.size() > 10) {
            recentScoreList.remove(9);
        }
    }

    public void addTopScore(int score, String name){
        Score score0 = new Score(score, name);

        for (int i = 0 ; i < topScoreList.size() ; i++){
            if(topScoreList.get(i).getScore() <= score){
                topScoreList.add(i, score0);
                if (topScoreList.size() > 10) {
                    topScoreList.remove(9);
                }
            }
        }
    }

    public ScoreList(){}

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

    public ArrayList getTopScores(){
        ArrayList<String> post = new ArrayList<>();

        for (int i = 0; i<10; i++){
            post.add("<b>" + topScoreList.get(i).getName() + "</b> \n" + topScoreList.get(i).getScore());
        }

        return post;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.recentScoreList);
        dest.writeList(this.topScoreList);
    }

    public static final Parcelable.Creator<ScoreList> CREATOR = new Parcelable.Creator<ScoreList>() {
        public ScoreList createFromParcel(Parcel in) {
            return new ScoreList(in);
        }

        public ScoreList[] newArray(int size) {
            return new ScoreList[size];
        }
    };

    private ScoreList(Parcel in) {
        in.readList(recentScoreList, ScoreList.class.getClassLoader());
        in.readList(topScoreList, ScoreList.class.getClassLoader());
    }

}
