package com.haemin.imagemathtutor.Data;

public class Answer {
    String answerSeq;
    String questionSeq;
    String title;
    String contents;
    long postTime;
    long updateTime;
    String uploaderSeq;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswerSeq() {
        return answerSeq;
    }

    public void setAnswerSeq(String answerSeq) {
        this.answerSeq = answerSeq;
    }

    public String getQuestionSeq() {
        return questionSeq;
    }

    public void setQuestionSeq(String questionSeq) {
        this.questionSeq = questionSeq;
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
