package com.haemin.imagemathtutor.Data;

public class ServerFile extends ServerData {

    public static final String FILE_TYPE_IMAGE = "image";
    public static final String FILE_TYPE_NORMAL = "normal";

    public ServerFile() {
    }

    public ServerFile(int fileSeq, String fileName, String fileUrl, String fileType) {
        this.fileSeq = fileSeq;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
    }

    int fileSeq;
    String fileName;
    String fileUrl;
    String fileType;

    String postSeq;
    long uploadTime;
    String userSeq;

    public String getPostSeq() {
        return postSeq;
    }

    public void setPostSeq(String postSeq) {
        this.postSeq = postSeq;
    }

    public long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(String userSeq) {
        this.userSeq = userSeq;
    }

    public int getFileSeq() {
        return fileSeq;
    }

    public void setFileSeq(int fileSeq) {
        this.fileSeq = fileSeq;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
