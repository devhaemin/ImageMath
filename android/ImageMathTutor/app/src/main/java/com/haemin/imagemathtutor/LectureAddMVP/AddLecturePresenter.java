package com.haemin.imagemathtutor.LectureAddMVP;

import android.util.Log;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.Academy;
import com.haemin.imagemathtutor.Data.Lecture;
import com.haemin.imagemathtutor.Data.ServerTime;
import com.haemin.imagemathtutor.GlobalApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddLecturePresenter implements AddLectureContract.AddLecturePresenter {

    AddLectureContract.AddLectureView lectureView;
    ArrayList<Academy> academies;

    public AddLecturePresenter(AddLectureContract.AddLectureView lectureView) {
        this.lectureView = lectureView;
        academies = new ArrayList<>();
    }

    @Override
    public void uploadLecture(Academy academy, String lectureName, ArrayList<ServerTime> serverTimes, String lectureDuration) {

        ServerTime serverTime = serverTimes.get(0);
        Map<String,String> lectureField = new HashMap<>();
        lectureField.put("academySeq",academy.getAcademySeq());
        lectureField.put("academyName",academy.getAcademyName());
        lectureField.put("name",lectureName);
        if(serverTime.getLectureTimeDay().equals("월")){
            lectureField.put("weekDay",""+2);
        }else if(serverTime.getLectureTimeDay().equals("화")){
            lectureField.put("weekDay",""+3);
        }else if(serverTime.getLectureTimeDay().equals("수")){
            lectureField.put("weekDay",""+4);
        }else if(serverTime.getLectureTimeDay().equals("목")){
            lectureField.put("weekDay",""+5);
        }else if(serverTime.getLectureTimeDay().equals("금")){
            lectureField.put("weekDay",""+6);
        }else if(serverTime.getLectureTimeDay().equals("토")){
            lectureField.put("weekDay",""+7);
        }else if(serverTime.getLectureTimeDay().equals("일")){
            lectureField.put("weekDay",""+1);
        }else{
            lectureField.put("weekDay",""+1);
        }
        lectureField.put("time",serverTime.getLectureTimeFirst()+"~"+serverTime.getLectureTimeSecond());
        lectureField.put("totalDate",lectureDuration);
        lectureField.put("week",14+"");
        lectureField.put("studentNum",0+"");
        GlobalApplication.getAPIService().addLecture(GlobalApplication.getAccessToken(),lectureField).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200 ){
                    lectureView.showSuccess();
                }else{
                    lectureView.showErrorMsg(AppString.ERROR_ADD_LECTURE);
                    Log.e("AddLecturePresenter",response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("AddLecturePresenter",t.getMessage(),t);
                lectureView.showErrorMsg(AppString.ERROR_ADD_LECTURE);
            }
        });
    }
    @Override
    public void requestAcademyData() {
        GlobalApplication.getAPIService().getAcademyList().enqueue(new Callback<ArrayList<Academy>>() {
            @Override
            public void onResponse(Call<ArrayList<Academy>> call, Response<ArrayList<Academy>> response) {
                if(response.code() == 200 && response.body() != null){
                    lectureView.showDialog(response.body());
                }else{
                    lectureView.showErrorMsg(AppString.ERROR_LOAD_ACADEMY_LIST);
                    Log.e("AddLecturePresenter",response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Academy>> call, Throwable t) {
                Log.e("AddLecturePresenter",t.getMessage(),t);
            }
        });
    }
}
