package com.haemin.imagemathstudent.Data;

import com.google.gson.annotations.SerializedName;

public class ServerData {
    @SerializedName("msg")
    String msg;
    @SerializedName("code")
    int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
