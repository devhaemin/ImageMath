package com.haemin.imagemathstudent.Data;

import com.google.gson.annotations.SerializedName;

public class ServerTime {
    @SerializedName("lectureTimeDay")
    String lectureTimeDay;
    @SerializedName("lectureTimeFirst")
    String lectureTimeFirst;
    @SerializedName("lectureTimeSecond")
    String lectureTimeSecond;

    public String getLectureTimeDay() {
        return lectureTimeDay;
    }

    public void setLectureTimeDay(String lectureTimeDay) {
        this.lectureTimeDay = lectureTimeDay;
    }

    public String getLectureTimeFirst() {
        return lectureTimeFirst;
    }

    public void setLectureTimeFirst(String lectureTimeFirst) {
        this.lectureTimeFirst = lectureTimeFirst;
    }

    public String getLectureTimeSecond() {
        return lectureTimeSecond;
    }

    public void setLectureTimeSecond(String lectureTimeSecond) {
        this.lectureTimeSecond = lectureTimeSecond;
    }
}
