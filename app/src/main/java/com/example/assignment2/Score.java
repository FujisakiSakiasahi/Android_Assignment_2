package com.example.assignment2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Score: <br/>
 * - use to store details of a score
 */
public class Score implements Parcelable {
    private String name;
    private int score;

    /**
     * Score Constructor: <br/>
     * - use to create an object of Score<br/>
     *
     * @param score final score of the game
     * @param name player name
     */
    public Score(int score, String name) {
        this.score = score;
        this.name = name;
    }

    /**
     * getScore: <br/>
     * - use to get score
     *
     * @return score of the object
     */
    public int getScore() {
        return score;
    }

    /**
     * setScore: <br/>
     * - use to set the score
     *
     * @param score final score of the game
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * getName: <br/>
     * - use to get player name
     *
     * @return player name
     */
    public String getName() {
        return name;
    }

    /**
     * setName: <br/>
     * - use to set the player name
     *
     * @param name player name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * describeContents: <br/>
     * - ______
     *
     * @return ______
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * writeToParcel: <br/>
     * - ______
     *
     * @param dest ______
     * @param flags _____
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(score);
    }

    //______________
    public static final Parcelable.Creator<Score> CREATOR = new Parcelable.Creator<Score>() {
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        public Score[] newArray(int size) {
            return new Score[size];
        }
    };

    /**
     * Score: <br/>
     * - ______
     *
     * @param in __________
     */
    private Score(Parcel in) {
        name = in.readString();
        score = in.readInt();
    }
}
