package com.haemin.imagemathtutor.AssignmentAddMVP;

import com.haemin.imagemathtutor.Data.Lecture;
import com.haemin.imagemathtutor.Data.ServerFile;

import java.util.ArrayList;
import java.util.HashMap;

public interface AssignmentAddContract {
    interface AssignmentAddView{
        void showToast(String text);
        void showSuccess(String text);
        void showLectureDialog(ArrayList<Lecture> lectures);

    }
    interface AssignmentAddPresenter{
        void uploadAssignment(HashMap<String, String> assignmentMap, ArrayList<ServerFile> answerFiles);
        void requestLectureData();
    }
}
