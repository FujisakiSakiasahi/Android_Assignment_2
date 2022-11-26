package com.example.assignment2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.ArrayList;

/**
 * ScoreList: <br/>
 * - use to store a set of Score
 */
public class ScoreList implements Parcelable {
    private List<Score> recentScoreList = new ArrayList<Score>();
    private List<Score> topScoreList = new ArrayList<Score>();

    /**
     * Score Constructor: <br/>
     * - use to create an object of ScoreList<br/>
     */
    public ScoreList(){}

    /**
     * addRecentScore: <br/>
     * - use to add a score to the recent score list
     *
     * @param score final score of the game
     * @param name player name
     */
    public void addRecentScore(int score, String name) {
        Score score0 = new Score(score, name);

        recentScoreList.add(0, score0);

        if (recentScoreList.size() > 10) {
            recentScoreList.remove(9);
        }
    }

    /**
     * addTopScore: <br/>
     * - use to add a score to the top score list
     *
     * @param score final score of the game
     * @param name player name
     */
    public void addTopScore(int score, String name){
        Score score0 = new Score(score, name);

        int x = topScoreList.size();

        for (int i = 0 ; i < x ; i++){
            if(topScoreList.get(i).getScore() <= score){
                topScoreList.add(i, score0);
                if (topScoreList.size() > 10) {
                    topScoreList.remove(9);
                }
                return;
            }
        }

        topScoreList.add(new Score(score, name));

    }

    /**
     * getRecentScore: <br/>
     * - use to get a specific score from the recent score list
     *
     * @param index the position of the score in the list
     * @return the score
     */
    public int getRecentScore(int index) {
        if(index+1 <= recentScoreList.size()){
            return recentScoreList.get(index).getScore();
        }
        return -1;
    }

    /**
     * getRecentScores: <br/>
     * - use to get the list of scores from the recent score list
     *
     * @return list of the scores
     */
    public ArrayList getRecentScores(){
        ArrayList<String> post = new ArrayList<>();

        if(getTopLength() == 0){
            return post;
        }else{
            for (int i = 0; i<10; i++){
                post.add(recentScoreList.get(i).getName() + "\n" + recentScoreList.get(i).getScore());
            }
            return post;
        }
    }

    /**
     * getRecentName: <br/>
     * - use to get a specific name from the recent score list
     *
     * @param index the position of the name in the list
     * @return player name
     */
    public String getRecentName(int index) {
        if(index+1 <= recentScoreList.size()){
            return recentScoreList.get(index).getName();
        }
        return "RECORD NOT FOUND";
    }

    /**
     * getRecentLength: <br/>
     * - use to get the length of the recent score list
     *
     * @return the length of the list
     */
    public int getRecentLength(){
        return recentScoreList.size();
    }

    /**
     * getTopScore: <br/>
     * - use to get a specific score from the top score list
     *
     * @param index the position of the score in the list
     * @return the score
     */
    public int getTopScore(int index) {
        if(index+1 <= topScoreList.size()){
            return topScoreList.get(index).getScore();
        }
        return -1;
    }

    /**
     * getTopScores: <br/>
     * - use to get the list of the scores from the top score list
     *
     * @return list of the scores
     */
    public ArrayList getTopScores(){
        ArrayList<String> post = new ArrayList<>();

        if(getTopLength() == 0){
            return post;
        }else{
            for (int i = 0; i < getTopLength(); i++){
                post.add(topScoreList.get(i).getName() + "\n" + topScoreList.get(i).getScore());
            }
            return post;
        }
    }

    /**
     * getTopName: <br/>
     * - use ot get a specific name from the top score list
     *
     * @param index the position of the name in the list
     * @return player name
     */
    public String getTopName(int index) {
        if(index+1 <= topScoreList.size()){
            return topScoreList.get(index).getName();
        }
        return "RECORD NOT FOUND";
    }

    /**
     * getTopLength: <br/>
     * - use to get the length of the top score list
     *
     * @return the length of the list
     */
    public int getTopLength(){
        return topScoreList.size();
    }

    /**
     * describeContents: <br/>
     * - overriding an abstract method
     *
     * @return ______
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * writeToParcel: <br/>
     * - writing the data to a parcel to be transferred through activities
     *
     * @param dest ______
     * @param flags _____
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.recentScoreList);
        dest.writeList(this.topScoreList);
    }

    /**
     * calls the new constructor below and sends the parcel through for processing and creating a new object
     */
    public static final Parcelable.Creator<ScoreList> CREATOR = new Parcelable.Creator<ScoreList>() {
        public ScoreList createFromParcel(Parcel in) {
            return new ScoreList(in);
        }

        public ScoreList[] newArray(int size) {
            return new ScoreList[size];
        }
    };

    /**
     * ScoreList: <br/>
     * - a constructor for parcel parameter for data transfer
     *
     * @param in __________
     */
    private ScoreList(Parcel in) {
        in.readList(recentScoreList, ScoreList.class.getClassLoader());
        in.readList(topScoreList, ScoreList.class.getClassLoader());
    }

}
