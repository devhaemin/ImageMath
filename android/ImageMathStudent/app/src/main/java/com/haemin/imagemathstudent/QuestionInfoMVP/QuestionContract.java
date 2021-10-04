package com.haemin.imagemathstudent.QuestionInfoMVP;

import com.haemin.imagemathstudent.Data.Answer;
import com.haemin.imagemathstudent.Data.Question;

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
