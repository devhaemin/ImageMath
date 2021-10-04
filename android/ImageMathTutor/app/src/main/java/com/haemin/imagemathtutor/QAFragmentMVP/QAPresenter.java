package com.haemin.imagemathtutor.QAFragmentMVP;

import android.util.Log;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.Question;
import com.haemin.imagemathtutor.GlobalApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class QAPresenter implements QAContract.QAPresenter {

    QAContract.QAView qaView;

    public QAPresenter(QAContract.QAView qaView) {
        this.qaView = qaView;
    }

    @Override
    public void requestQuestionList() {
        GlobalApplication.getAPIService().getQuestionList(GlobalApplication.getAccessToken())
                .enqueue(new Callback<ArrayList<Question>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Question>> call, Response<ArrayList<Question>> response) {
                        if(response.code() == 200 && response.body() != null){
                            qaView.updateRecycler(response.body());
                        }else{
                            qaView.showToast("질문 목록을 불러오는데 실패했습니다.");
                            Log.e("QAPresenter",response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Question>> call, Throwable t) {
                        qaView.showToast(AppString.ERROR_NETWORK_MESSAGE);
                        Log.e("QAPresenter",t.getMessage(),t);
                    }
                });
    }
}
