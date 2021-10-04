package com.haemin.imagemathtutor.NoticeMVP;

import com.haemin.imagemathtutor.Data.Notice;

import java.util.ArrayList;

public interface NoticeContract {
    interface NoticeView {
        void refreshView(ArrayList<Notice> notices);

        void showErrorMessage(String error);
    }

    interface NoticePresenter {
        void updateData(String lectureSeq);

    }
}
