package com.haemin.imagemathtutor.TestInfoMVP;

import com.haemin.imagemathtutor.Data.StudentTest;
import com.haemin.imagemathtutor.Data.Test;

import java.util.ArrayList;

public interface TestInfoContract {
    interface TestInfoView{
        void showToast(String text);
        void updateTestInfo(Test test);
        void updateRecycler(ArrayList<StudentTest> studentTests);
        void showDeleteSuccess();
    }
    interface TestInfoPresenter{
        void requestTestInfo(String testSeq);
        void requestStudentList(String testSeq);
        void deleteTestInfo(String testSeq);
    }
}
