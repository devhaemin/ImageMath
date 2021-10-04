package com.haemin.imagemathstudent.TestFragmentMVP;

import android.util.Log;
import com.haemin.imagemathstudent.Data.Lecture;
import com.haemin.imagemathstudent.Data.StudentTest;
import com.haemin.imagemathstudent.SingleTon.AppString;
import com.haemin.imagemathstudent.SingleTon.GlobalApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class TestFragmentPresenter implements TestFragmentContract.TestFragmentPresenter {
    TestFragmentContract.TestFragmentView testFragmentView;

    public TestFragmentPresenter(TestFragmentContract.TestFragmentView testFragmentView) {
        this.testFragmentView = testFragmentView;
    }

    @Override
    public void updateData(String lectureSeq) {
        GlobalApplication.getAPIService().getUserTest(GlobalApplication.getAccessToken(), lectureSeq, 0)
                .enqueue(new Callback<ArrayList<StudentTest>>() {
                    @Override
                    public void onResponse(Call<ArrayList<StudentTest>> call, Response<ArrayList<StudentTest>> response) {
                        if (response.code() == 200 && response.body() != null) {
                            ArrayList<StudentTest> tests = response.body();

                            if (tests.size() != 0) {
                                testFragmentView.showHasData();
                                testFragmentView.updateChart(tests);
                                testFragmentView.updateRecycler(tests);

                                int sumScore = 0;
                                int sumRank = 0;

                                for (StudentTest test : tests) {
                                    sumRank += test.getRank();
                                    sumScore += test.getScore();
                                }
                                testFragmentView.updateAverages("" +  tests.get(tests.size() - 1).getRank(), "" + sumScore / tests.size(), "" + tests.get(tests.size() - 1).getScore());
                            } else {
                                testFragmentView.showNoData();
                            }
                        } else {
                            testFragmentView.showToast(AppString.ERROR_LOAD_TEST_LIST);
                            Log.e("TestFragmentPresenter", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<StudentTest>> call, Throwable t) {
                        testFragmentView.showToast(AppString.ERROR_NETWORK_MESSAGE);
                        Log.e("TestFragmentPresenter", t.getMessage(), t);
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
                            testFragmentView.showDialog(response.body());
                        } else {
                            testFragmentView.showToast(AppString.ERROR_LOAD_LECTURE_LIST);
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
