package com.haemin.imagemathstudent.Data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Notice extends ServerData{
    @SerializedName("noticeSeq")
    int noticeSeq;
    @SerializedName("title")
    String title;
    @SerializedName("postTime")
    long postTime;
    @SerializedName("contents")
    String contents;
    @SerializedName("lectureSeq")
    String lectureSeq;
    @SerializedName("files")
    ArrayList<ServerFile> files;

    public String getLectureSeq() {
        return lectureSeq;
    }

    public void setLectureSeq(String lectureSeq) {
        this.lectureSeq = lectureSeq;
    }

    public long getPostTime() {
        return postTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getNoticeSeq() {
        return noticeSeq;
    }

    public void setNoticeSeq(int noticeSeq) {
        this.noticeSeq = noticeSeq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ServerFile> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<ServerFile> files) {
        this.files = files;
    }
}
