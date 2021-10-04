package com.haemin.imagemathstudent.Data;

import com.google.gson.annotations.SerializedName;

public class SelectableData extends ServerData{
    @SerializedName("listName")
    String listName;

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
