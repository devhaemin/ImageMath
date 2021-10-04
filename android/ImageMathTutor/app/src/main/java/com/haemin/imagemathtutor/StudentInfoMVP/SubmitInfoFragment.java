package com.haemin.imagemathtutor.StudentInfoMVP;


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.StudentAssignment;
import com.haemin.imagemathtutor.GlobalApplication;
import com.haemin.imagemathtutor.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitInfoFragment extends Fragment {

    @BindView(R.id.recycler_assignment_submit)
    RecyclerView recyclerAssignment;
    SubmitInfoAdapter submitInfoAdapter;
    ArrayList<StudentAssignment> assignments;
    String userSeq;

    private static final String ARG_USER_SEQ = "userSeq";

    public SubmitInfoFragment() {
        // Required empty public constructor
    }

    public static SubmitInfoFragment newInstance(String userSeq) {
        SubmitInfoFragment fragment = new SubmitInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_SEQ, userSeq+"");
        fragment.setArguments(args);
        return fragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_submit_info, container, false);

        ButterKnife.bind(this, v);

        assignments = new ArrayList<>();
        submitInfoAdapter = new SubmitInfoAdapter(getContext(),assignments);

        recyclerAssignment.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recyclerAssignment.setAdapter(submitInfoAdapter);

        if (getArguments() != null) {
            userSeq = getArguments().getString(ARG_USER_SEQ);
            refresh();
        }
        return v;
    }
    void refresh(){
        GlobalApplication.getAPIService().getStudentSubmitAssignmentList(GlobalApplication.getAccessToken(),userSeq).enqueue(new Callback<ArrayList<StudentAssignment>>() {
            @Override
            public void onResponse(Call<ArrayList<StudentAssignment>> call, Response<ArrayList<StudentAssignment>> response) {
                if(response.code() == 200 && response.body() != null){

                    Log.e("SubmitInfoFragment",response.body().toString());
                    assignments.clear();
                    assignments.addAll(response.body());
                    submitInfoAdapter.notifyDataSetChanged();
                }else{
                    Log.e("SubmitInfoFragment",response.message());
                    Toast.makeText(getContext(),AppString.ERROR_LOAD_ASSIGNMENT_LIST,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StudentAssignment>> call, Throwable t) {
                Log.e("SubmitInfoFragment",t.getMessage(),t);
                Toast.makeText(getContext(), AppString.ERROR_NETWORK_MESSAGE,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
