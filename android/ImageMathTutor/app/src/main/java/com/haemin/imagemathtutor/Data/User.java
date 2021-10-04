package com.haemin.imagemathtutor.Data;

public class User extends ServerData {
    String userSeq;
    String name;
    String birthday;
    String email;
    String password;
    String lectureSeqs; // "3,5,6,~"
    String reqLectureSeqs;
    String schoolName;
    String phone;
    String accessToken;
    String studentCode;
    long registerTime;
    int gender;
    public static final int GENDER_MALE = 0;

    public static final int GENDER_FEMALE = 1;

    boolean isChecked = false;

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(String userSeq) {
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
