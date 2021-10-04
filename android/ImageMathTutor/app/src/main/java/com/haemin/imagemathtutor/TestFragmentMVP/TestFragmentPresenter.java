package com.haemin.imagemathtutor.TestFragmentMVP;

import android.util.Log;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.Academy;
import com.haemin.imagemathtutor.Data.Lecture;
import com.haemin.imagemathtutor.Data.Test;
import com.haemin.imagemathtutor.GlobalApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class TestFragmentPresenter implements TestFragmentContract.TestFragmentPresenter {


    TestFragmentContract.TestFragmentView testView;

    public TestFragmentPresenter(TestFragmentContract.TestFragmentView testView) {
        this.testView = testView;
    }

    @Override
    public void requestLectures() {
        GlobalApplication.getAPIService().getLectureList(false).enqueue(new Callback<ArrayList<Lecture>>() {
            @Override
            public void onResponse(Call<ArrayList<Lecture>> call, Response<ArrayList<Lecture>> response) {
                if(response.code() == 200 && response.body() != null){
                    testView.showDialog(response.body());
                }else{
                    testView.showToast(AppString.ERROR_LOAD_ACADEMY_LIST);
                    Log.e("AddLecturePresenter",response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Lecture>> call, Throwable t) {
                Log.e("AddLecturePresenter",t.getMessage(),t);
                testView.showToast(AppString.ERROR_NETWORK_MESSAGE);
            }
        });
    }

    @Override
    public void requestTestData(String lectureSeq) {
        GlobalApplication.getAPIService().getTutorTestList(GlobalApplication.getAccessToken(),lectureSeq,0)
                .enqueue(new Callback<ArrayList<Test>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Test>> call, Response<ArrayList<Test>> response) {
                        if(response.code() == 200 && response.body() != null){
                            if(response.body().size() > 0 && response.body().get(0) == null){
                                testView.showToast("시험 목록이 비었습니다.");
                            }else{
                                testView.updateRecycler(response.body());
                            }
                        }else{
                            testView.showToast(AppString.ERROR_LOAD_TEST_LIST);
                            Log.e("TestFragmentPresenter",response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Test>> call, Throwable t) {
                        testView.showToast(AppString.ERROR_NETWORK_MESSAGE);
                        Log.e("TestFragmentPresenter",t.getMessage(),t);
                    }
                });
    }
}
