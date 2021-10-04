package com.haemin.imagemathstudent.Data;

import com.google.gson.annotations.SerializedName;

public class Academy extends SelectableData{

    @SerializedName("academySeq")
    int academySeq;
    @SerializedName("academyName")
    String academyName;

    @Override
    public String getListName() {
        return academyName;
    }

    public int getAcademySeq() {
        return academySeq;
    }

    public void setAcademySeq(int academySeq) {
        this.academySeq = academySeq;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }
}
