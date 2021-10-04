package com.haemin.imagemathtutor.Data;

import java.util.ArrayList;

public class StudentAssignment extends Assignment{
    String userSeq;
    ArrayList<ServerFile> submitFiles;
    int submitState;
    String assignmentAdmSeq;
    long uploadTime;

    public long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getAssignmentAdmSeq() {
        return assignmentAdmSeq;
    }

    public void setAssignmentAdmSeq(String assignmentAdmSeq) {
        this.assignmentAdmSeq = assignmentAdmSeq;
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
