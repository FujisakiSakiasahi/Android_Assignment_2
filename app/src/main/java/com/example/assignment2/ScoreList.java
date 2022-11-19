package com.example.assignment2;

public class ScoreList { //currently use the recent 10 record
    private static int recordCount = 0;
    private int[] scoreList = new int[10];
    private String[] nameList = new String[10];

    public void addScore(int score, String name) {
        if(recordCount < 10){
            this.scoreList[recordCount] = score;
            this.nameList[recordCount] = name;
            recordCount++;
        }else{
            for (int i = 0 ; i < 9 ; i++){
                this.scoreList[i] = this.scoreList[i+1];
                this.nameList[i] = this.nameList[i+1];
            }
            this.scoreList[recordCount] = score;
            this.nameList[recordCount] = name;
        }
    }

    public int getScore(int index) {
        if(index+1 <= recordCount){
            return scoreList[index];
        }
        return -1;
    }

    public String getName(int index) {
        if(index+1 <= recordCount){
            return nameList[index];
        }
        return "RECORD NOT FOUND";
    }
}
