package com.haemin.imagemathstudent.TestInfoMVP;

import android.util.Log;
import com.haemin.imagemathstudent.Data.Assignment;
import com.haemin.imagemathstudent.Data.StudentTest;
import com.haemin.imagemathstudent.Data.Test;
import com.haemin.imagemathstudent.SingleTon.AppString;
import com.haemin.imagemathstudent.SingleTon.GlobalApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestInfoPresenter implements TestInfoContract.TestInfoPresenter {
    TestInfoContract.TestInfoView testView;

    public TestInfoPresenter(TestInfoContract.TestInfoView testView) {
        this.testView = testView;
    }

    @Override
    public void updateData(String testAdmSeq) {
        GlobalApplication.getAPIService().getStudentTestInfo(GlobalApplication.getAccessToken(),testAdmSeq)
                .enqueue(new Callback<StudentTest>() {
                    @Override
                    public void onResponse(Call<StudentTest> call, Response<StudentTest> response) {
                        if(response.code() == 200 && response.body() != null){
                            testView.updateView(response.body());
                        }else{
                            testView.showToast(AppString.ERROR_LOAD_LECTURE_LIST);
                        }
                    }

                    @Override
                    public void onFailure(Call<StudentTest> call, Throwable t) {
                        testView.showToast(AppString.ERROR_NETWORK_MESSAGE);
                        Log.e("TestInfoPresenter",t.getMessage(),t);
                    }
                });
    }
    @Override
    public void getAnswerFilesInfo(String testSeq) {
        GlobalApplication.getAPIService().getTestInfo(GlobalApplication.getAccessToken(),testSeq).enqueue(new Callback<Test>() {
            @Override
            public void onResponse(Call<Test> call, Response<Test> response) {
                if(response.code() == 200 && response.body() != null){
                    testView.updateAnswerView(response.body().getAnswerFiles());
                }else{
                    Log.e("TestInfoPresenter",response.message());
                }
            }

            @Override
            public void onFailure(Call<Test> call, Throwable t) {
                testView.showToast(AppString.ERROR_NETWORK_MESSAGE);
                Log.e("TestInfoPresenter",t.getMessage(),t);
            }
        });
    }
}
