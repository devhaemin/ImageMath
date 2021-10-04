package com.haemin.imagemathtutor.TestFragmentMVP;

import com.haemin.imagemathtutor.Data.Lecture;
import com.haemin.imagemathtutor.Data.Test;

import java.util.ArrayList;

public interface TestFragmentContract {
    interface TestFragmentView{
        void showToast(String text);
        void updateLectureName(Lecture lecture);
        void updateRecycler(ArrayList<Test> tests);
        void showDialog(ArrayList<Lecture> lectures);
    }
    interface TestFragmentPresenter{
        void requestLectures();
        void requestTestData(String lectureSeq);
    }
}
