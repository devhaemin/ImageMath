package com.haemin.imagemathtutor.Data;

import java.util.ArrayList;

public class Test extends ServerData{
    int testSeq;
    String title;
    long postTime;
    long endTime;
    long lectureTime;
    ArrayList<ServerFile> answerFiles;
    int averageScore;
    int studentNum;
    String contents;
    String lectureName;

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(int averageScore) {
        this.averageScore = averageScore;
    }

    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }

    public int getTestSeq() {
        return testSeq;
    }

    public void setTestSeq(int testSeq) {
        this.testSeq = testSeq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPostTime() {
        return postTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getLectureTime() {
        return lectureTime;
    }

    public void setLectureTime(long lectureTime) {
        this.lectureTime = lectureTime;
    }

    public ArrayList<ServerFile> getAnswerFiles() {
        return answerFiles;
    }

    public void setAnswerFiles(ArrayList<ServerFile> answerFiles) {
        this.answerFiles = answerFiles;
    }
}
