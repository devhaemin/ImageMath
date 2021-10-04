package com.haemin.imagemathstudent.NoticeActivityMVP;

import android.util.Log;
import com.haemin.imagemathstudent.Data.Notice;
import com.haemin.imagemathstudent.SingleTon.AppString;
import com.haemin.imagemathstudent.SingleTon.GlobalApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class NoticePresenter implements NoticeContract.NoticePresenter {
    NoticeContract.NoticeView noticeView;

    public NoticePresenter(NoticeContract.NoticeView noticeView) {
        this.noticeView = noticeView;
    }

    @Override
    public void updateData(String lectureSeq) {
        GlobalApplication.getAPIService().getNoticeList(GlobalApplication.getAccessToken(),lectureSeq,0)
        .enqueue(new Callback<ArrayList<Notice>>() {
            @Override
            public void onResponse(Call<ArrayList<Notice>> call, Response<ArrayList<Notice>> response) {
                if(response.code() == 200 && response.body() != null){
                    noticeView.updateView(response.body());
                }else{
                    noticeView.showToast(AppString.ERROR_LOAD_NOTICE_LIST);
                    Log.e("NoticePresenter",response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Notice>> call, Throwable t) {
                noticeView.showToast(AppString.ERROR_NETWORK_MESSAGE);
                Log.e("NoticePresenter",t.getMessage(),t);

            }
        });
    }
}
