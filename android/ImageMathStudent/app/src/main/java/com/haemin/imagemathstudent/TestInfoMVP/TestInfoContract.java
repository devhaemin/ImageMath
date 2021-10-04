package com.haemin.imagemathstudent.TestInfoMVP;

import android.util.Log;
import com.haemin.imagemathstudent.Data.Assignment;
import com.haemin.imagemathstudent.Data.ServerFile;
import com.haemin.imagemathstudent.Data.StudentTest;
import com.haemin.imagemathstudent.SingleTon.AppString;
import com.haemin.imagemathstudent.SingleTon.GlobalApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public interface TestInfoContract {
    interface TestInfoView {
        void showToast(String text);
        void updateView(StudentTest test);
        void updateAnswerView(ArrayList<ServerFile> answerFiles);
    }
    interface TestInfoPresenter {
        void updateData(String lectureSeq);
        void getAnswerFilesInfo(String assignmentSeq);
    }
}
