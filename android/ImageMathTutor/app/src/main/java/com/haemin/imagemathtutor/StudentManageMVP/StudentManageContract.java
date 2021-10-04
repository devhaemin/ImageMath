package com.haemin.imagemathtutor.StudentManageMVP;

import com.haemin.imagemathtutor.Data.User;

import java.util.ArrayList;

public interface StudentManageContract {
    interface StudentManageView{
        void updateStudentList(ArrayList<User> students);
        void showToast(String text);
    }
    interface StudentManagePresenter{

        void requestStudentList(String lectureSeq);
        void requestDeleteStudent(String lectureSeq, String studentSeq);

    }
}
