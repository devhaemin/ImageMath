package com.haemin.imagemathstudent.AssignmentInfoMVP;

import android.net.Uri;
import android.util.Log;
import com.haemin.imagemathstudent.Data.Assignment;
import com.haemin.imagemathstudent.Data.StudentAssignment;
import com.haemin.imagemathstudent.SingleTon.AppString;
import com.haemin.imagemathstudent.SingleTon.GlobalApplication;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;

public class AssignmentInfoPresenter implements AssignmentInfoContract.AssignmentInfoPresenter {

    AssignmentInfoContract.AssignmentInfoView infoView;

    public AssignmentInfoPresenter(AssignmentInfoContract.AssignmentInfoView infoView) {
        this.infoView = infoView;
    }

    @Override
    public void updateData(String assignmentSeq) {

        GlobalApplication.getAPIService().getStudentAssignmentInfo(GlobalApplication.getAccessToken(),assignmentSeq)
        .enqueue(new Callback<StudentAssignment>() {
            @Override
            public void onResponse(Call<StudentAssignment> call, Response<StudentAssignment> response) {
                if(response.code() == 200){
                    infoView.updateView(response.body());
                }else{
                    infoView.showToast("과제 정보를 불러오지 못했습니다.");
                    Log.e("AssignmentInfoPresenter",response.message());
                }
            }

            @Override
            public void onFailure(Call<StudentAssignment> call, Throwable t) {
                infoView.showToast(AppString.ERROR_NETWORK_MESSAGE);
                Log.e("AssignmentInfoPresenter",t.getMessage(),t);
            }
        });

    }

    @Override
    public void submitPicture(String assignmentSeq, Uri imageUri) {

        MultipartBody.Part part = MultipartBody.Part.createFormData("submitFile", imageUri.getPath(), RequestBody.create(MediaType.parse("image/png"), new File(imageUri.getPath())));

        GlobalApplication.getAPIService().postPicture(GlobalApplication.getAccessToken(), assignmentSeq, part).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    infoView.showToast("과제 제출이 완료되었습니다.");
                    updateData(assignmentSeq);
                }else{
                    infoView.showToast("과제 제출이 실패하였습니다.\n파일 크기가 10MB를 넘지 않는지 확인해주세요.");
                    Log.e("AssignmentInfoPresenter",response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                infoView.showToast(t.getMessage());
                Log.e("AssignmentInfoPresenter",t.getMessage(),t);
            }
        });
    }

    @Override
    public void getAnswerFilesInfo(String assignmentSeq) {
        GlobalApplication.getAPIService().getAssignmentInfo(GlobalApplication.getAccessToken(),assignmentSeq).enqueue(new Callback<Assignment>() {
            @Override
            public void onResponse(Call<Assignment> call, Response<Assignment> response) {
                if(response.code() == 200 && response.body() != null){
                    infoView.updateAnswerView(response.body().getAnswerFiles());
                }else{
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
}
