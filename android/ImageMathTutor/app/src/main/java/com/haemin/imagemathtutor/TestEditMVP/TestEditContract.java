package com.haemin.imagemathtutor.TestEditMVP;

import com.haemin.imagemathtutor.Data.Lecture;
import com.haemin.imagemathtutor.Data.StudentTest;
import com.haemin.imagemathtutor.Data.Test;

import java.util.ArrayList;

public interface TestEditContract {
    interface TestEditView{
        void updateTestView(Test test);
        void updateLectureView(Lecture lecture);
        void updateRecycler(ArrayList<StudentTest> studentTests);
    }
    interface TestEditPresenter{
        void requestLectureData(String lectureSeq);
        void requestStudentData(ArrayList<StudentTest> studentTests);
        void requestTestData(String testSeq);
    }
}
