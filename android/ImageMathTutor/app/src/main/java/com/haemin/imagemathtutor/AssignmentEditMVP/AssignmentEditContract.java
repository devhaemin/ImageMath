package com.haemin.imagemathtutor.AssignmentEditMVP;

import com.haemin.imagemathtutor.Data.Assignment;
import com.haemin.imagemathtutor.Data.Lecture;
import com.haemin.imagemathtutor.Data.ServerFile;

import java.util.ArrayList;
import java.util.HashMap;

public interface AssignmentEditContract {
    interface AssignmentEditView{
        void showToast(String text);
        void showSuccess(String text);
        void showLectureDialog(ArrayList<Lecture> lectures);
        void showAssignmentData(Assignment assignment);
    }
    interface AssignmentEditPresenter{
        void uploadAssignment(HashMap<String, String> assignmentMap, ArrayList<ServerFile> answerFiles);
        void requestLectureData();
        void updateAssignmentData(String assignmentSeq);
    }
}
