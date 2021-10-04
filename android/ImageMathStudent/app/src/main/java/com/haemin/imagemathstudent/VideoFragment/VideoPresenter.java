package com.haemin.imagemathstudent.VideoFragment;

import android.util.Log;
import android.widget.VideoView;
import com.haemin.imagemathstudent.Data.Lecture;
import com.haemin.imagemathstudent.Data.Video;
import com.haemin.imagemathstudent.SingleTon.AppString;
import com.haemin.imagemathstudent.SingleTon.GlobalApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class VideoPresenter implements VideoContract.VideoPresenter {

   VideoContract.VideoView videoView;

    public VideoPresenter(VideoContract.VideoView videoView) {
        this.videoView = videoView;
    }

    @Override
    public void requestData(String lectureSeq) {
        GlobalApplication.getAPIService().getVideoListByLecture(GlobalApplication.getAccessToken(), lectureSeq)
                .enqueue(new Callback<ArrayList<Video>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Video>> call, Response<ArrayList<Video>> response) {
                        if(response.code() == 200 && response.body() != null){
                            videoView.updateRecycler(response.body());
                        }else{
                            videoView.showToast("동영상 목록을 불러오는데 실패했습니다.");
                            Log.e("VideoPresenter", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Video>> call, Throwable t) {
                        videoView.showToast(AppString.ERROR_NETWORK_MESSAGE);
                        Log.e("VideoPresenter", t.getMessage() , t);
                    }
                });
    }

    @Override
    public void requestLecturePick() {
        GlobalApplication.getAPIService().getMyLectureList(GlobalApplication.getAccessToken(), false, 0)
                .enqueue(new Callback<ArrayList<Lecture>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Lecture>> call, Response<ArrayList<Lecture>> response) {
                        if (response.code() == 200 && response.body() != null) {
                            videoView.showDialog(response.body());
                        } else {
                            videoView.showToast(AppString.ERROR_LOAD_LECTURE_LIST);
                            Log.e("TestFragmentPresenter", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Lecture>> call, Throwable t) {
                        Log.e("TestFragmentPresenter", t.getMessage(), t);
                    }
                });
    }
}
