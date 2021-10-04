package com.haemin.imagemathstudent.NoticeActivityMVP;

import com.haemin.imagemathstudent.Data.Notice;

import java.util.ArrayList;

public interface NoticeContract {

    interface NoticeView{
        void updateView(ArrayList<Notice> notices);
        void showToast(String text);
    }
    interface NoticePresenter{
        void updateData(String lectureSeq);
    }

}
