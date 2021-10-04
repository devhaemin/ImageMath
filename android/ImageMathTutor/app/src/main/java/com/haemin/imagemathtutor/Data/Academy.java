package com.haemin.imagemathtutor.Data;

public class Academy extends SelectableData {

    String academySeq;
    String academyName;

    @Override
    public String getListName() {
        return academyName;
    }

    public String getAcademySeq() {
        return academySeq;
    }

    public void setAcademySeq(String academySeq) {
        this.academySeq = academySeq;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }
}
