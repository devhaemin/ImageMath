package com.haemin.imagemathstudent.Data;

import android.annotation.SuppressLint;
import com.google.gson.annotations.SerializedName;

public class Lecture extends SelectableData{
    @SerializedName("lectureSeq")
    String lectureSeq;
    @SerializedName("time")
    String time;
    @SerializedName("name")
    String name;
    @SerializedName("totalDate")
    String totalDate; // ,를 구분자로 사용함.
    //String week;
    @SerializedName("reqStudentCnt")
    int reqStudentCount;
    @SerializedName("studentNum")
    String studentNum;
    @SerializedName("academyName")
    String academyName;
    @SerializedName("academySeq")
    String academySeq;
    @SerializedName("isExpired")
    boolean isExpired;

    @Override
    public String getListName() {
        return name;
    }

    public String getName() {
        return name;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLectureSeq() {
        return lectureSeq;
    }

    public void setLectureSeq(String lectureSeq) {
        this.lectureSeq = lectureSeq;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalDate() {
        return totalDate;
    }

    public void setTotalDate(String totalDate) {
        this.totalDate = totalDate;
    }

    /*
    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
    */

    public int getReqStudentCount() {
        return reqStudentCount;
    }

    public void setReqStudentCount(int reqStudentCount) {
        this.reqStudentCount = reqStudentCount;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public String getAcademySeq() {
        return academySeq;
    }

    public void setAcademySeq(String academySeq) {
        this.academySeq = academySeq;
    }
}
