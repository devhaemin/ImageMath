package com.haemin.imagemathtutor.Data;

public class School extends SelectableData {
    int schoolSeq;
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
