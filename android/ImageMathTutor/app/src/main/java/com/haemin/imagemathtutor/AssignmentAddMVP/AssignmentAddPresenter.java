package com.haemin.imagemathtutor.AssignmentAddMVP;

import android.util.Log;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.Assignment;
import com.haemin.imagemathtutor.Data.Lecture;
import com.haemin.imagemathtutor.Data.ServerFile;
import com.haemin.imagemathtutor.Data.Test;
import com.haemin.imagemathtutor.GlobalApplication;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class AssignmentAddPresenter implements AssignmentAddContract.AssignmentAddPresenter {
    private static final String TAG = "AssignmentAddPresenter";
    AssignmentAddContract.AssignmentAddView view;

    public AssignmentAddPresenter(AssignmentAddContract.AssignmentAddView view) {
        this.view = view;
    }

    @Override
    public void uploadAssignment(HashMap<String, String> assignmentMap, ArrayList<ServerFile> answerFiles) {
        GlobalApplication.getAPIService().postAssignmentInfo(GlobalApplication.getAccessToken(),assignmentMap).enqueue(new Callback<Assignment>() {
            @Override
            public void onResponse(Call<Assignment> call, Response<Assignment> response) {
                if(response.code() == 200 && response.body() != null){
                    for (ServerFile answerFile :
                            answerFiles) {
                        uploadAnswerFile(response.body().getAssignmentSeq()+"", answerFile);
                    }
                    view.showSuccess("과제 등록이 성공적으로 완료되었습니다.");
                }else{
                    view.showToast("과제 등록이 실패했습니다.");
                    Log.e(TAG,response.message());
                }
            }

            @Override
            public void onFailure(Call<Assignment> call, Throwable t) {
                Log.e(TAG,t.getMessage(),t);
                view.showToast(AppString.ERROR_NETWORK_MESSAGE);
            }
        });
    }

    @Override
    public void requestLectureData() {
        GlobalApplication.getAPIService().getLectureList(false).enqueue(new Callback<ArrayList<Lecture>>() {
            @Override
            public void onResponse(Call<ArrayList<Lecture>> call, Response<ArrayList<Lecture>> response) {
                if(response.code() ==200 && response.body() != null){
                    view.showLectureDialog(response.body());
                }else{
                    view.showToast(AppString.ERROR_LOAD_LECTURE_LIST);
                    Log.e(TAG,response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Lecture>> call, Throwable t) {
                Log.e(TAG,t.getMessage(),t);
                view.showToast(AppString.ERROR_NETWORK_MESSAGE);
            }
        });
    }
    public void uploadAnswerFile(String assignmentSeq, ServerFile answerFile) {
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", answerFile.getFileUrl(), RequestBody.create(null ,new File(answerFile.getFileUrl())));
        GlobalApplication.getAPIService().uploadAssignmentAnswer(GlobalApplication.getAccessToken(),assignmentSeq,part)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 200){
                            //view.showToast("엑셀파일 업로드가 성공적으로 이루어졌습니다.");
                        }else{
                            view.showToast("답안지 파일 업로드 에러 발생");
                            Log.e(TAG,response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        view.showToast("답안지 파일 업로드 에러 발생");
                        Log.e(TAG,t.getMessage(),t);
                    }
                });
    }
}
