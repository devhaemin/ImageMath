package com.haemin.imagemathstudent.Data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StudentTest extends Test{

    @SerializedName("score")
    int score;
    @SerializedName("rank")
    int rank;
    @SerializedName("testAdmSeq")
    String testAdmSeq;

    public String getTestAdmSeq() {
        return testAdmSeq;
    }

    public void setTestAdmSeq(String testAdmSeq) {
        this.testAdmSeq = testAdmSeq;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
