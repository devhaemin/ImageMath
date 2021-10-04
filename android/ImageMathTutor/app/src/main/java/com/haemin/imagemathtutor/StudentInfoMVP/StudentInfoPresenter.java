package com.haemin.imagemathtutor.StudentInfoMVP;

import android.util.Log;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.Lecture;
import com.haemin.imagemathtutor.Data.User;
import com.haemin.imagemathtutor.GlobalApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class StudentInfoPresenter implements StudentInfoContract.StudentInfoPresenter {

    StudentInfoContract.StudentInfoView infoView;

    public StudentInfoPresenter(StudentInfoContract.StudentInfoView infoView) {
        this.infoView = infoView;
    }

    @Override
    public void updateStudentData(String userSeq) {
        GlobalApplication.getAPIService().getUserInfo(GlobalApplication.getAccessToken(),userSeq).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200 && response.body() != null){
                    infoView.updateStudentView(response.body());
                }else{
                    infoView.showToast(AppString.ERROR_LOAD_USER_LIST);
                    Log.e("StudentInfoPresenter",response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                infoView.showToast(AppString.ERROR_NETWORK_MESSAGE);
                Log.e("StudentInfoPresenter",t.getMessage(),t);
            }
        });
    }
}
