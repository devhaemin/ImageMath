package com.haemin.imagemathstudent.Data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User extends ServerData{
    @SerializedName("userSeq")
    int userSeq;
    @SerializedName("name")
    String name;
    @SerializedName("birthday")
    String birthday;
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
    @SerializedName("lectureSeqs")
    String lectureSeqs; // "3,5,6,~"
    @SerializedName("reqLectureSeqs")
    String reqLectureSeqs;
    @SerializedName("schoolName")
    String schoolName;
    @SerializedName("phone")
    String phone;
    @SerializedName("accessToken")
    String accessToken;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public User(int userSeq, String name, String birthday, String email, String password, String lectureSeqs, String reqLectureSeqs, int schoolSeq, String phone) {
        this.userSeq = userSeq;
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        this.lectureSeqs = lectureSeqs;
        this.reqLectureSeqs = reqLectureSeqs;
        this.phone = phone;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(int userSeq) {
        this.userSeq = userSeq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLectureSeqs() {
        return lectureSeqs;
    }

    public void setLectureSeqs(String lectureSeqs) {
        this.lectureSeqs = lectureSeqs;
    }

    public String getReqLectureSeqs() {
        return reqLectureSeqs;
    }

    public void setReqLectureSeqs(String reqLectureSeqs) {
        this.reqLectureSeqs = reqLectureSeqs;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
