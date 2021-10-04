package com.haemin.imagemathstudent.QuestionAddMVP;

import com.haemin.imagemathstudent.Data.ServerFile;
import retrofit2.http.PartMap;

import java.util.ArrayList;
import java.util.HashMap;

public interface QuestionAddContract {
    interface QuestionAddView{

        void showToast(String error);

        void showSuccess();

        void refreshFileList(ArrayList<ServerFile> files);
    }
    interface QuestionAddPresenter{
        void addFile(String path, String fileType);

        void deleteFile(int fileSeq);

        void writeQuestion(String title,String contents);

    }
}
