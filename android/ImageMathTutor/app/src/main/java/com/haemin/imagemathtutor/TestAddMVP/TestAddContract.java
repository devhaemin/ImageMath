package com.haemin.imagemathtutor.TestAddMVP;

import com.haemin.imagemathtutor.Data.Lecture;
import com.haemin.imagemathtutor.Data.ServerFile;
import retrofit2.http.FieldMap;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

public interface TestAddContract {
    interface TestAddView{

        void showToast(String text);
        void showSuccess(String text);
        void showLectureDialog(ArrayList<Lecture> lectures);
        int getXlsNum();
        String getLectureSeq();


    }
    interface TestAddPresenter{
        void uploadTest(HashMap<String, String> testMap, ArrayList<ServerFile> answerFiles,ServerFile xlsFile);
        void requestLectureData();
    }
}
