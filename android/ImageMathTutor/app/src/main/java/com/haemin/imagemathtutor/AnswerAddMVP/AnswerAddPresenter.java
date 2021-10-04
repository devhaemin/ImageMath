package com.haemin.imagemathtutor.AnswerAddMVP;

import android.util.Log;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.Answer;
import com.haemin.imagemathtutor.Data.Answer;
import com.haemin.imagemathtutor.Data.ServerFile;
import com.haemin.imagemathtutor.GlobalApplication;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class AnswerAddPresenter implements AnswerAddContract.AnswerAddPresenter {
    AnswerAddContract.AnswerAddView addView;
    ArrayList<ServerFile> files;
    boolean isSuccess;



    public AnswerAddPresenter(AnswerAddContract.AnswerAddView addView) {
        this.addView = addView;
        this.files = new ArrayList<>();
    }
    @Override
    public void addFile(String path, String fileType) {
        File file = new File(path);
        ServerFile serverFile = new ServerFile(files.size(), file.getName(), file.getPath(), fileType);
        files.add(serverFile);
        addView.refreshFileList(files);
    }


    @Override
    public void deleteFile(int fileSeq) {
        Log.e("LOL", "ClickED!");


        Iterator<ServerFile> iter = files.iterator();
        while (iter.hasNext()) {
            ServerFile s = iter.next();
            if (s.getFileSeq() == fileSeq)
                iter.remove();
        }
        addView.refreshFileList(files);
    }

    @Override
    public void writeAnswer(String title, String contents, String questionSeq) {
        GlobalApplication.getAPIService().writeAnswer(GlobalApplication.getAccessToken(),title,contents, questionSeq).enqueue(new Callback<Answer>() {
            @Override
            public void onResponse(Call<Answer> call, Response<Answer> response) {
                if(response.code() == 200 && response.body() != null){
                    if(files.size() == 0){
                        addView.showSuccess();
                    }else{
                        isSuccess = true;
                        for(ServerFile serverFile : files){
                            MultipartBody.Part part = MultipartBody.Part.createFormData("file", serverFile.getFileUrl(), RequestBody.create(null ,new File(serverFile.getFileUrl())));
                            GlobalApplication.getAPIService().addAnswerFile(GlobalApplication.getAccessToken(),response.body().getAnswerSeq(),part).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.code() == 200){
                                    }else{
                                        addView.showToast("파일 업로드에 실패하였습니다.");
                                        Log.e("NoticeEditPresenter",response.message());
                                        isSuccess = false;
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    addView.showToast(AppString.ERROR_NETWORK_MESSAGE);
                                    Log.e("NoticeEditPresenter",t.getMessage(),t);
                                    isSuccess = false;
                                }
                            });
                        }
                        if(isSuccess) addView.showSuccess();
                    }
                }else{
                    Log.e("AnswerAddPresenter",response.message());
                    addView.showToast("답변을 등록하는데 실패했습니다. 인터넷 연결을 확인해주세요.");
                }
            }

            @Override
            public void onFailure(Call<Answer> call, Throwable t) {
                Log.e("AnswerAddPresenter",t.getMessage(),t);
                addView.showToast(AppString.ERROR_NETWORK_MESSAGE);
            }
        });
    }
}
