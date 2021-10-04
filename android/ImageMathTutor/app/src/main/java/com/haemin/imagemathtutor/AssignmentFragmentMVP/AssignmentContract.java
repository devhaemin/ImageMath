package com.haemin.imagemathtutor.AssignmentFragmentMVP;

import com.haemin.imagemathtutor.Data.Assignment;

import java.util.ArrayList;

public interface AssignmentContract {
    interface AssignmentView{
        void showToast(String text);
        void updateRecycler(ArrayList<AssignmentRecyclerAdapter.AssignmentDateHolder> dateHolders);
        void updateView(ArrayList<Assignment> assignments);
    }
    interface AssignmentPresenter{
        void requestData();

    }
}
