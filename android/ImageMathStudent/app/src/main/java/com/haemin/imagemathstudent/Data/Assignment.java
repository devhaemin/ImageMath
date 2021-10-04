package com.haemin.imagemathstudent.Data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Assignment extends ServerData{
    @SerializedName("assignmentSeq")
    String assignmentSeq;
    @SerializedName("title")
    String title;
    @SerializedName("contents")
    String contents;
    @SerializedName("postTime")
    long postTime;
    @SerializedName("endTime")
    long endTime;
    @SerializedName("lectureTime")
    long lectureTime;
    @SerializedName("answerFiles")
    ArrayList<ServerFile> answerFiles;
    @SerializedName("lectureName")
    String lectureName;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getAssignmentSeq() {
        return assignmentSeq;
    }

    public void setAssignmentSeq(String assignmentSeq) {
        this.assignmentSeq = assignmentSeq;
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
