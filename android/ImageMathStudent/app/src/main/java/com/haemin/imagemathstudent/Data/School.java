package com.haemin.imagemathstudent.Data;

import com.google.gson.annotations.SerializedName;

public class School extends SelectableData{
    @SerializedName("schoolSeq")
    int schoolSeq;
    @SerializedName("schoolName")
    String schoolName;

    @Override
    public String getListName() {
        return schoolName;
    }

    public int getSchoolSeq() {
        return schoolSeq;
    }

    public void setSchoolSeq(int schoolSeq) {
        this.schoolSeq = schoolSeq;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
