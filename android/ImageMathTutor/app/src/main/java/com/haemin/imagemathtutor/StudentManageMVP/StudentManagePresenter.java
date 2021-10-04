package com.haemin.imagemathtutor.StudentManageMVP;

import android.util.Log;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.User;
import com.haemin.imagemathtutor.GlobalApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class StudentManagePresenter implements StudentManageContract.StudentManagePresenter {

    public String TAG = "StudentManagePresenter";

    StudentManageContract.StudentManageView view;

    public StudentManagePresenter(StudentManageContract.StudentManageView view) {
        this.view = view;
    }

    @Override
    public void requestStudentList(String lectureSeq) {
        GlobalApplication.getAPIService().getStudentList(GlobalApplication.getAccessToken(),lectureSeq).enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if(response.code() == 200 && response.body() != null){
                    view.updateStudentList(response.body());
                }else{
                    view.showToast(AppString.ERROR_LOAD_USER_LIST);
                    Log.e(TAG,response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Log.e(TAG,t.getMessage(),t);
                view.showToast(AppString.ERROR_NETWORK_MESSAGE);
            }
        });
    }

    @Override
    public void requestDeleteStudent(String lectureSeq, String studentSeq) {
        GlobalApplication.getAPIService().deleteStudent(GlobalApplication.getAccessToken(),lectureSeq,studentSeq).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200 && response.body() != null){
                    view.showToast("성공적으로 학생을 삭제했습니다.");
                }else{
                    view.showToast(AppString.ERROR_DELETE_USER);
                    Log.e(TAG,response.message()+"a");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });
    }

}
