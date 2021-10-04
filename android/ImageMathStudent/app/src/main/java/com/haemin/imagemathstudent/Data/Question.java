package com.haemin.imagemathstudent.Data;

import com.google.gson.annotations.SerializedName;

public class Question {

    @SerializedName("questionSeq")
    String questionSeq;
    @SerializedName("title")
    String title;
    @SerializedName("contents")
    String contents;
    @SerializedName("postTime")
    long postTime;
    @SerializedName("updateTime")
    long updateTime;
    @SerializedName("uploaderSeq")
    String uploaderSeq;

    public String getQuestionSeq() {
        return questionSeq;
    }

    public void setQuestionSeq(String questionSeq) {
        this.questionSeq = questionSeq;
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

    public long getPostTime() {
        return postTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getUploaderSeq() {
        return uploaderSeq;
    }

    public void setUploaderSeq(String uploaderSeq) {
        this.uploaderSeq = uploaderSeq;
    }
}
