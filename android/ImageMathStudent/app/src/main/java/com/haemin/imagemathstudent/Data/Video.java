package com.haemin.imagemathstudent.Data;

import com.google.gson.annotations.SerializedName;

public class Video {
    @SerializedName("videoSeq")
    int videoSeq;
    @SerializedName("userSeq")
    int userSeq;
    @SerializedName("uploadTime")
    long uploadTime;
    @SerializedName("title")
    String title;
    @SerializedName("contents")
    String contents;

    public int getVideoSeq() {
        return videoSeq;
    }

    public void setVideoSeq(int videoSeq) {
        this.videoSeq = videoSeq;
    }

    public int getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(int userSeq) {
        this.userSeq = userSeq;
    }

    public long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
