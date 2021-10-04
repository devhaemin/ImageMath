package com.haemin.imagemathtutor.AnswerAddMVP;

import com.haemin.imagemathtutor.Data.ServerFile;

import java.util.ArrayList;

public interface AnswerAddContract {
    interface AnswerAddView {

        void showToast(String error);

        void showSuccess();

        void refreshFileList(ArrayList<ServerFile> files);
    }
    interface AnswerAddPresenter {
        void addFile(String path, String fileType);

        void deleteFile(int fileSeq);

        void writeAnswer(String title, String contents, String questionSeq);

    }
}
