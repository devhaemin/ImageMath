package com.haemin.imagemathstudent.TestFragmentMVP;

import com.haemin.imagemathstudent.Data.Lecture;
import com.haemin.imagemathstudent.Data.StudentTest;

import java.util.ArrayList;

public interface TestFragmentContract {
    interface TestFragmentView{
        void showToast(String text);
        void showDialog(ArrayList<Lecture> lectures);
        void updateChart(ArrayList<StudentTest> studentTests);
        void updateRecycler(ArrayList<StudentTest> studentTests);
        void updateAverages(String averageRank, String averageScore, String recentScore);
        void updateLectureName(String lectureName);
        void showNoData();
        void showHasData();
    }
    interface TestFragmentPresenter{
        void updateData(String lectureSeq);
        void requestLecturePick();
    }
}
