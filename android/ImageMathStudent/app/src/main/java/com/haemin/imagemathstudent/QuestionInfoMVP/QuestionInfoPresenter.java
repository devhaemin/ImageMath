package com.haemin.imagemathstudent.QuestionInfoMVP;

import android.util.Log;
import com.haemin.imagemathstudent.Data.Answer;
import com.haemin.imagemathstudent.Data.Question;
import com.haemin.imagemathstudent.SingleTon.AppString;
import com.haemin.imagemathstudent.SingleTon.GlobalApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class QuestionInfoPresenter implements QuestionContract.QuestionPresenter {

    QuestionContract.QuestionView view;

    public QuestionInfoPresenter(QuestionContract.QuestionView view) {
        this.view = view;
    }

    @Override
    public void requestData(String questionSeq) {

        GlobalApplication.getAPIService().getQuestionList(GlobalApplication.getAccessToken()).enqueue(new Callback<ArrayList<Question>>() {
            @Override
            public void onResponse(Call<ArrayList<Question>> call, Response<ArrayList<Question>> response) {
                if(response.code() == 200 && response.body() != null){
                    for(Question q : response.body()) {
                        if(q.getQuestionSeq().equals(questionSeq))   view.showQuestionData(q);
                    }
                }else{
                    view.showToast("질문 목록을 불러오는데 실패했습니다.");
                    Log.e("QuestionInfoPresenter",response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Question>> call, Throwable t) {
                Log.e("QuestionInfoPresenter",t.getMessage(),t);
                view.showToast(AppString.ERROR_NETWORK_MESSAGE);
            }
        });

        GlobalApplication.getAPIService().getAnswerList(GlobalApplication.getAccessToken(), questionSeq).enqueue(new Callback<ArrayList<Answer>>() {
            @Override
            public void onResponse(Call<ArrayList<Answer>> call, Response<ArrayList<Answer>> response) {
                if(response.code() == 200 && response.body() != null){

                    view.showAnswerData(response.body());

                }else{

                    Log.e("QuestionInfoPresenter",response.message());
                    view.showToast("답변 정보를 불러오는데 실패했습니다.");

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Answer>> call, Throwable t) {
                Log.e("QuestionInfoPresenter",t.getMessage(),t);
                view.showToast(AppString.ERROR_NETWORK_MESSAGE);
            }
        });
    }
}
