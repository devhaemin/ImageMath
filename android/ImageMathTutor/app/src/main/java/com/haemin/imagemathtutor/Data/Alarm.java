package com.haemin.imagemathtutor.Data;

import android.text.format.DateUtils;

public class Alarm extends ServerData {
    String title;
    String text;
    String type;
    long time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return DateUtils.getRelativeTimeSpanString(time, System.currentTimeMillis(), 3000, DateUtils.FORMAT_ABBREV_ALL).toString();
    }

    public void setTime(long time) {
        this.time = time;
    }
}
