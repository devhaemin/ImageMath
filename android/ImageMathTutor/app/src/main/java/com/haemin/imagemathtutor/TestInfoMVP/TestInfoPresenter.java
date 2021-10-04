package com.haemin.imagemathtutor.TestInfoMVP;

import android.util.Log;
import android.widget.Toast;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.StudentTest;
import com.haemin.imagemathtutor.Data.Test;
import com.haemin.imagemathtutor.GlobalApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class TestInfoPresenter implements TestInfoContract.TestInfoPresenter {

    TestInfoContract.TestInfoView infoView;

    public TestInfoPresenter(TestInfoContract.TestInfoView infoView) {
        this.infoView = infoView;
    }

    @Override
    public void requestTestInfo(String testSeq) {
        GlobalApplication.getAPIService().getTutorTestInfo(GlobalApplication.getAccessToken(),testSeq)
                .enqueue(new Callback<Test>() {
                    @Override
                    public void onResponse(Call<Test> call, Response<Test> response) {
                        if(response.code() == 200 && response.body() != null){
                            infoView.updateTestInfo(response.body());
                        }else{
                            infoView.showToast(AppString.ERROR_LOAD_TEST_LIST);
                            Log.e("TestInfoPresenter",response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Test> call, Throwable t) {
                        infoView.showToast(AppString.ERROR_NETWORK_MESSAGE);
                        Log.e("TestInfoPresenter",t.getMessage(),t);
                    }
                });
    }

    @Override
    public void requestStudentList(String testSeq) {
        GlobalApplication.getAPIService().getTestResults(GlobalApplication.getAccessToken(),testSeq,0)
                .enqueue(new Callback<ArrayList<StudentTest>>() {
                    @Override
                    public void onResponse(Call<ArrayList<StudentTest>> call, Response<ArrayList<StudentTest>> response) {
                        if(response.code() == 200 && response.body() != null){
                            infoView.updateRecycler(response.body());
                        }else{
                            infoView.showToast(AppString.ERROR_LOAD_TEST_RESULT_LIST);
                            Log.e("TestInfoStudent",response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<StudentTest>> call, Throwable t) {
                        infoView.showToast(AppString.ERROR_NETWORK_MESSAGE);
                        Log.e("TestInfoStudent",t.getMessage(),t);
                    }
                });
    }

    @Override
    public void deleteTestInfo(String testSeq) {
        GlobalApplication.getAPIService().deleteTestInfo(GlobalApplication.getAccessToken(),testSeq).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    infoView.showDeleteSuccess();
                }else{
                    infoView.showToast("테스트 삭제가 실패했습니다.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                infoView.showToast(AppString.ERROR_NETWORK_MESSAGE);
                Log.e("TestInfoDelete",t.getMessage(),t);
            }
        });
    }
}
