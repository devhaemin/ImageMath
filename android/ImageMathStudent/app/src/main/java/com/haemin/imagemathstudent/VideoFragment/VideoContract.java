package com.haemin.imagemathstudent.VideoFragment;

import com.haemin.imagemathstudent.Data.Lecture;
import com.haemin.imagemathstudent.Data.Video;

import java.util.ArrayList;

public interface VideoContract {
    interface VideoView{
        void showToast(String msg);
        void updateLectureName(String lectureName);
        void showDialog(ArrayList<Lecture> lectures);
        void updateRecycler(ArrayList<Video> videos);
    }
    interface VideoPresenter{
        void requestData(String lectureSeq);
        void requestLecturePick();
    }
}
