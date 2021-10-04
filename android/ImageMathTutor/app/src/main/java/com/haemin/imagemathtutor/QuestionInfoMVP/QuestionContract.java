package com.haemin.imagemathtutor.QuestionInfoMVP;



import com.haemin.imagemathtutor.Data.Answer;
import com.haemin.imagemathtutor.Data.Question;

import java.util.ArrayList;

public interface QuestionContract {
    interface QuestionView{
        void showQuestionData(Question question);
        void showAnswerData(ArrayList<Answer> answers);
        void showToast(String text);
    }
    interface QuestionPresenter{
        void requestData(String questionSeq);
    }
}
