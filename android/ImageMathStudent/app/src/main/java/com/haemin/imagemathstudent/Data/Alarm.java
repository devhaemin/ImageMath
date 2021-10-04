package com.haemin.imagemathstudent.Data;

import android.text.format.DateUtils;
import com.google.gson.annotations.SerializedName;

public class Alarm extends ServerData{
    @SerializedName("title")
    String title;
    @SerializedName("content")
    String content;
    @SerializedName("type")
    String type;
    @SerializedName("postTime")
    long postTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getPostTime() {
        return postTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }
}
