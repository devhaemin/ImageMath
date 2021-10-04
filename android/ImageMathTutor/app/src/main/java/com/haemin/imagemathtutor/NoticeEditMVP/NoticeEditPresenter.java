package com.haemin.imagemathtutor.NoticeEditMVP;

import android.net.Uri;
import android.util.Log;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.Notice;
import com.haemin.imagemathtutor.Data.ServerFile;
import com.haemin.imagemathtutor.GlobalApplication;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class NoticeEditPresenter implements NoticeEditContract.NoticeEditPresenter {

    ArrayList<ServerFile> files;
    NoticeEditContract.NoticeEditView noticeEditView;
    boolean isSuccess;

    public NoticeEditPresenter(NoticeEditContract.NoticeEditView noticeEditView) {
        this.noticeEditView = noticeEditView;
        this.files = new ArrayList<>();
    }

    @Override
    public void addFile(String path, String fileType) {
        File file = new File(path);
        ServerFile serverFile = new ServerFile(files.size(), file.getName(), file.getPath(), fileType);
        files.add(serverFile);
        noticeEditView.refreshFileList(files);
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
        noticeEditView.refreshFileList(files);
    }

    @Override
    public void uploadNotice(String lectureSeq, String title, String content) {
        GlobalApplication.getAPIService().addNotice(GlobalApplication.getAccessToken(),title,content,lectureSeq).enqueue(new Callback<Notice>() {
            @Override
            public void onResponse(Call<Notice> call, Response<Notice> response) {
                if(response.code() == 200 && response.body() != null){
                    if(files.size() == 0){
                        noticeEditView.showSuccess("공지사항이 성공적으로 업로드 되었습니다.");
                    }else{
                        isSuccess = true;
                        for(ServerFile serverFile : files){
                            MultipartBody.Part part = MultipartBody.Part.createFormData("file", serverFile.getFileUrl(), RequestBody.create(null ,new File(serverFile.getFileUrl())));
                            GlobalApplication.getAPIService().addNoticeFile(GlobalApplication.getAccessToken(),response.body().getNoticeSeq(),part).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.code() == 200){
                                    }else{
                                        noticeEditView.showError("파일 업로드에 실패하였습니다.");
                                        Log.e("NoticeEditPresenter",response.message());
                                        isSuccess = false;
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    noticeEditView.showError(AppString.ERROR_NETWORK_MESSAGE);
                                    Log.e("NoticeEditPresenter",t.getMessage(),t);
                                    isSuccess = false;
                                }
                            });
                        }
                        if(isSuccess) noticeEditView.showSuccess("공지사항이 성공적으로 업로드 되었습니다.");
                    }
                }else{
                    noticeEditView.showError(AppString.ERROR_LOAD_NOTICE_LIST);
                    Log.e("NoticeEditPresenter",response.message());
                }
            }

            @Override
            public void onFailure(Call<Notice> call, Throwable t) {
                noticeEditView.showError(AppString.ERROR_NETWORK_MESSAGE);
                Log.e("NoticeEditPresenter",t.getMessage(),t);
            }
        });

    }
}
