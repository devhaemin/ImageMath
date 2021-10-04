package com.haemin.imagemathstudent.AssignmentInfoMVP;


import android.net.Uri;
import com.haemin.imagemathstudent.Data.ServerFile;
import com.haemin.imagemathstudent.Data.StudentAssignment;

import java.util.ArrayList;

public interface AssignmentInfoContract {
    interface AssignmentInfoView{
        void updateView(StudentAssignment studentAssignment);

        void showToast(String text);
        void updateAnswerView(ArrayList<ServerFile> answerFiles);
    }
    interface AssignmentInfoPresenter{
        void updateData(String assignmentSeq);

        void submitPicture(String assignmentSeq, Uri imageUri);

        void getAnswerFilesInfo(String assignmentSeq);
    }
}
