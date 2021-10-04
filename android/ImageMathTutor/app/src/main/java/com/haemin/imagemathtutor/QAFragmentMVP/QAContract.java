package com.haemin.imagemathtutor.QAFragmentMVP;


import com.haemin.imagemathtutor.Data.Question;

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
