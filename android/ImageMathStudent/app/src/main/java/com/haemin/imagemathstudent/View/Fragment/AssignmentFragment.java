package com.haemin.imagemathstudent.View.Fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathstudent.Adapter.AssignmentRecyclerAdapter;
import com.haemin.imagemathstudent.Data.StudentAssignment;
import com.haemin.imagemathstudent.R;
import com.haemin.imagemathstudent.SingleTon.AppString;
import com.haemin.imagemathstudent.SingleTon.GlobalApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssignmentFragment extends Fragment {

    @BindView(R.id.recycler_assignment)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.text_no_assignment_data)
    TextView textNoAssignment;



    AssignmentRecyclerAdapter assignmentRecyclerAdapter;
    ArrayList<AssignmentRecyclerAdapter.AssignmentDateHolder> dateHolders;

    public AssignmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_assignment, container, false);
        ButterKnife.bind(this,v);
        dateHolders  = new ArrayList<>();
        assignmentRecyclerAdapter  = new AssignmentRecyclerAdapter(getContext(),dateHolders);
        recyclerView.setAdapter(assignmentRecyclerAdapter);
        LinearLayoutManager li = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        li.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(li);
        refresh();
        refreshLayout.setOnRefreshListener(() -> {
            refresh();
            refreshLayout.setRefreshing(false);
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh(){
        GlobalApplication.getAPIService().getStudentAssignmentList(GlobalApplication.getAccessToken(),0)
        .enqueue(new Callback<ArrayList<StudentAssignment>>() {
            @Override
            public void onResponse(Call<ArrayList<StudentAssignment>> call, Response<ArrayList<StudentAssignment>> response) {
                if(response.code() == 200 && response.body() != null){


                    ArrayList<StudentAssignment> assignments = response.body();

                    if(assignments.size() != 0) {
                        textNoAssignment.setVisibility(View.GONE);
                        ArrayList<Integer> dates = new ArrayList<>();
                        dateHolders.clear();
                        for (StudentAssignment assignment : assignments) {
                            boolean hasSame = false;
                            for (Integer date : dates) {
                                if ((int) (assignment.getEndTime() / (1000 * 3600 * 24)) == date) {
                                    hasSame = true;
                                }
                            }
                            if (!hasSame) {
                                dates.add((int) (assignment.getEndTime() / (1000 * 3600 * 24)));
                            }
                        }
                        for (Integer date : dates) {
                            AssignmentRecyclerAdapter.AssignmentDateHolder dateHolder = new AssignmentRecyclerAdapter.AssignmentDateHolder();
                            dateHolder.setDate((long) date * 1000 * 3600 * 24);
                            dateHolder.setAssignments(new ArrayList<>());
                            dateHolders.add(dateHolder);
                        }
                        for (AssignmentRecyclerAdapter.AssignmentDateHolder dateHolder : dateHolders) {
                            for (StudentAssignment assignment : assignments) {
                                if ((((int) (assignment.getEndTime() / (1000 * 3600 * 24)))) == ( dateHolder.getDate() / (1000 * 3600 * 24))) {
                                    dateHolder.getAssignments().add(assignment);
                                }
                            }
                        }
                        assignmentRecyclerAdapter.notifyDataSetChanged();
                    }else{
                        textNoAssignment.setVisibility(View.VISIBLE);
                    }
                }else{
                    showToast(AppString.ERROR_LOAD_LECTURE_LIST);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StudentAssignment>> call, Throwable t) {
                showToast(AppString.ERROR_NETWORK_MESSAGE);
                Log.e("AssignmentFragment",t.getMessage(),t);
            }
        });
    }
    private void showToast(String text) {
        Toast.makeText(getContext(),text,Toast.LENGTH_SHORT).show();
    }

}
