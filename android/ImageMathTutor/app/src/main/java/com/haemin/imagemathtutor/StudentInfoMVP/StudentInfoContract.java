package com.haemin.imagemathtutor.StudentInfoMVP;

import com.haemin.imagemathtutor.Data.User;

public interface StudentInfoContract {
    interface StudentInfoView{
        void updateStudentView(User student);
        void showToast(String text);
    }
    interface StudentInfoPresenter{
        void updateStudentData(String userSeq);
    }
}
