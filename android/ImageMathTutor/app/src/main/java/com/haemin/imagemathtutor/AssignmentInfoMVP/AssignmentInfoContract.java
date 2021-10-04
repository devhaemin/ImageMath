package com.haemin.imagemathtutor.AssignmentInfoMVP;

import com.haemin.imagemathtutor.Data.Assignment;
import com.haemin.imagemathtutor.Data.StudentAssignment;
import com.haemin.imagemathtutor.Data.User;

import java.util.ArrayList;

public interface AssignmentInfoContract {
    interface AssignmentInfoView{
        void showToast(String text);
        void updateView(Assignment assignment);
        void updateRecycler(ArrayList<StudentAssignment> assignments);
        void showDeleteSuccess();
    }
    interface AssignmentInfoPresenter{
        void requestAssignmentData(String assignmentSeq);
        void requestStudentData(String assignmentSeq);
        void deleteAssignment(String assignmentSeq);
    }
}
