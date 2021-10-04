package com.haemin.imagemathstudent.Data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StudentAssignment extends Assignment {
    @SerializedName("userSeq")
    String userSeq;
    @SerializedName("submitFiles")
    ArrayList<ServerFile> submitFiles;
    @SerializedName("submitState")
    int submitState;
    @SerializedName("assignment")
    Assignment assignment;

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public String getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(String userSeq) {
        this.userSeq = userSeq;
    }

    public ArrayList<ServerFile> getSubmitFiles() {
        return submitFiles;
    }

    public void setSubmitFiles(ArrayList<ServerFile> submitFiles) {
        this.submitFiles = submitFiles;
    }

    public int getSubmitState() {
        return submitState;
    }

    public void setSubmitState(int submitState) {
        this.submitState = submitState;
    }
}
