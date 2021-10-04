package com.haemin.imagemathtutor.AssignmentFragmentMVP;

import android.util.Log;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.Assignment;
import com.haemin.imagemathtutor.GlobalApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class AssignmentPresenter implements AssignmentContract.AssignmentPresenter {
    AssignmentContract.AssignmentView view;

    public AssignmentPresenter(AssignmentContract.AssignmentView view) {
        this.view = view;
    }

    @Override
    public void requestData() {
        GlobalApplication.getAPIService().getAssignmentList(GlobalApplication.getAccessToken(), 0).enqueue(new Callback<ArrayList<Assignment>>() {
            @Override
            public void onResponse(Call<ArrayList<Assignment>> call, Response<ArrayList<Assignment>> response) {
                if (response.code() == 200 && response.body() != null) {
                    ArrayList<AssignmentRecyclerAdapter.AssignmentDateHolder> dateHolders = new ArrayList<>();
                    ArrayList<Assignment> assignments = response.body();
                    ArrayList<Integer> dates = new ArrayList<>();

                    for (Assignment assignment : assignments) {
                        boolean hasSame = false;
                        for (Integer date : dates) {
                            if ((int)(assignment.getEndTime() / (1000 * 3600 * 24)) == date) {
                                hasSame = true;
                            }
                        }
                        if (!hasSame) {
                            dates.add((int)( assignment.getEndTime() / (1000 * 3600 * 24)));
                        }
                    }
                    for (Integer date : dates) {
                        AssignmentRecyclerAdapter.AssignmentDateHolder dateHolder = new AssignmentRecyclerAdapter.AssignmentDateHolder();
                        dateHolder.setDate((long)date * 1000 * 3600 * 24);
                        dateHolder.setAssignments(new ArrayList<>());
                        dateHolders.add(dateHolder);
                    }
                    for (AssignmentRecyclerAdapter.AssignmentDateHolder dateHolder : dateHolders) {
                        for (Assignment assignment : assignments) {
                            if (((int)((assignment.getEndTime() / (1000 * 3600 * 24)))) == (dateHolder.getDate() / (1000*3600*24))) {
                                dateHolder.getAssignments().add(assignment);
                            }
                        }
                    }
                    view.updateRecycler(dateHolders);
                } else {
                    view.showToast(AppString.ERROR_LOAD_ASSIGNMENT_LIST);
                    Log.e("AssignmnetPresenter", response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Assignment>> call, Throwable t) {
                view.showToast(AppString.ERROR_NETWORK_MESSAGE);
                Log.e("AssignmentPresenter", t.getMessage(), t);
            }
        });
    }
}
