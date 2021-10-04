package com.haemin.imagemathstudent.QAMVP;

import com.haemin.imagemathstudent.Data.Question;

import java.util.ArrayList;

public interface QAContract {
    interface QAView{
        void showToast(String text);

        void updateRecycler(ArrayList<Question> questions);

        void bindView();

        void bindListener();
    }
    interface QAPresenter{
        void requestQuestionList();
    }
}
