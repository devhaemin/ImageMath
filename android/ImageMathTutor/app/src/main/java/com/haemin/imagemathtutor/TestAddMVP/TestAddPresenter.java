package com.haemin.imagemathtutor.TestAddMVP;

import android.util.Log;
import android.widget.Toast;
import com.haemin.imagemathtutor.AppString;
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

public class TestAddPresenter implements TestAddContract.TestAddPresenter {
    TestAddContract.TestAddView view;
    String TAG = "TestAddPresenter";

    public TestAddPresenter(TestAddContract.TestAddView view) {
        this.view = view;
    }

    @Override
    public void uploadTest(HashMap<String, String> testMap, ArrayList<ServerFile> answerFiles,ServerFile xlsFile) {
        GlobalApplication.getAPIService().addTestInfo(GlobalApplication.getAccessToken(),testMap).enqueue(new Callback<Test>() {
            @Override
            public void onResponse(Call<Test> call, Response<Test> response) {
                if(response.code() == 200){
                    view.showSuccess("테스트 업로드가 성공적으로 완료되었습니다.");
                    for (ServerFile answerFile :
                            answerFiles) {
                        uploadAnswerFile(response.body().getTestSeq()+"", answerFile);
                    }
                    if(xlsFile != null) uploadXlsFile(response.body().getTestSeq()+"",xlsFile);
                }else{
                    Log.e(TAG+":41",response.message());
                    view.showToast(AppString.ERROR_ADD_TEST);
                }
            }

            @Override
            public void onFailure(Call<Test> call, Throwable t) {
                Log.e(TAG+":48",t.getMessage(),t);
                view.showToast(AppString.ERROR_NETWORK_MESSAGE);
            }
        });
    }

    public void uploadAnswerFile(String testSeq, ServerFile answerFile) {
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", answerFile.getFileUrl(), RequestBody.create(null ,new File(answerFile.getFileUrl())));
        GlobalApplication.getAPIService().addTestAnswerFile(GlobalApplication.getAccessToken(),testSeq,part)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 200){
                            view.showToast("엑셀파일 업로드가 성공적으로 이루어졌습니다.");
                        }else{
                            view.showToast("답안지 파일 업로드 에러 발생");
                            Log.e(TAG+":64",response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        view.showToast("답안지 파일 업로드 에러 발생");
                        Log.e(TAG+":71",t.getMessage(),t);
                    }
                });
    }

    public void uploadXlsFile(String testSeq, ServerFile xlsFile) {
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", xlsFile.getFileUrl(), RequestBody.create(null ,new File(xlsFile.getFileUrl())));
        GlobalApplication.getAPIService().postTestWithExcel(GlobalApplication.getAccessToken(),testSeq,view.getXlsNum(),view.getLectureSeq(),part)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 200){
                            //view.showToast("엑셀파일 업로드가 성공적으로 이루어졌습니다.");
                        }else{
                            view.showToast("액셀 파일 업로드 에러 발생");
                            Log.e(TAG+":86",response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        view.showToast("액셀 파일 업로드 에러 발생");
                        Log.e(TAG+":93",t.getMessage(),t);
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
}
