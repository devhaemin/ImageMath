package com.haemin.imagemathtutor.AssignmentInfoMVP;

import android.util.Log;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.Assignment;
import com.haemin.imagemathtutor.Data.StudentAssignment;
import com.haemin.imagemathtutor.GlobalApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class AssignmentInfoPresenter implements AssignmentInfoContract.AssignmentInfoPresenter {

    AssignmentInfoContract.AssignmentInfoView infoView;

    public AssignmentInfoPresenter(AssignmentInfoContract.AssignmentInfoView infoView) {
        this.infoView = infoView;
    }

    @Override
    public void requestAssignmentData(String assignmentSeq) {
        GlobalApplication.getAPIService().getAssignmentInfo(GlobalApplication.getAccessToken(), assignmentSeq)
                .enqueue(new Callback<Assignment>() {
                    @Override
                    public void onResponse(Call<Assignment> call, Response<Assignment> response) {
                        if(response.code() == 200 && response.body() != null){
                            infoView.updateView(response.body());
                        }else{
                            infoView.showToast(AppString.ERROR_LOAD_ASSIGNMENT_LIST);
                            Log.e("AssignmentInfoPresenter",response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Assignment> call, Throwable t) {
                        infoView.showToast(AppString.ERROR_NETWORK_MESSAGE);
                        Log.e("AssignmentInfoPresenter",t.getMessage(),t);
                    }
                });
    }

    @Override
    public void requestStudentData(String assignmentSeq) {
        GlobalApplication.getAPIService().getStudentAssignmentList(GlobalApplication.getAccessToken(),assignmentSeq,0).enqueue(new Callback<ArrayList<StudentAssignment>>() {
            @Override
            public void onResponse(Call<ArrayList<StudentAssignment>> call, Response<ArrayList<StudentAssignment>> response) {
                    if(response.code() == 200 && response.body() != null){
                        infoView.updateRecycler(response.body());

                    }else{
                        infoView.showToast(AppString.ERROR_LOAD_USER_LIST);
                        Log.e("AssignmentInfoPresenter",response.message());
                    }
            }

            @Override
            public void onFailure(Call<ArrayList<StudentAssignment>> call, Throwable t) {
                infoView.showToast(AppString.ERROR_NETWORK_MESSAGE);
                Log.e("AssignmentInfoPresenter",t.getMessage(),t);
            }
        });
    }

    @Override
    public void deleteAssignment(String assignmentSeq) {
        GlobalApplication.getAPIService().deleteAssignmentInfo(GlobalApplication.getAccessToken(),assignmentSeq).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    infoView.showDeleteSuccess();
                }else{
                    infoView.showToast("과제 삭제에 실패했습니다.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                    infoView.showToast(AppString.ERROR_NETWORK_MESSAGE);
                    Log.e("AssignmentDelete", t.getMessage(),t);
            }
        });
    }
}
