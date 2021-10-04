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
import com.haemin.imagemathtutor.Adapter.LectureRecyclerAdapter;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.Lecture;
import com.haemin.imagemathtutor.GlobalApplication;
import com.haemin.imagemathtutor.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;


public class StudentLectureFragment extends Fragment {
    @BindView(R.id.recycler_student_lecture)
    RecyclerView recyclerLecture;

    LectureRecyclerAdapter adapter;

    ArrayList<Lecture> lectures;
    private static final String ARG_USER_SEQ = "userSeq";
    private String userSeq;


    public StudentLectureFragment() {
        // Required empty public constructor
    }
    public static StudentLectureFragment newInstance(String userSeq) {
        StudentLectureFragment fragment = new StudentLectureFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_SEQ, userSeq);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lecture, container, false);
        ButterKnife.bind(this,v);

        lectures = new ArrayList<>();

        if (getArguments() != null) {
            userSeq = getArguments().getString(ARG_USER_SEQ);
            adapter = new LectureRecyclerAdapter(getContext(),lectures);
            recyclerLecture.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
            recyclerLecture.setAdapter(adapter);
            refresh();
        }
        return v;
    }
    void refresh(){
        GlobalApplication.getAPIService().getStudentLectureList(GlobalApplication.getAccessToken(),userSeq)
                .enqueue(new Callback<ArrayList<Lecture>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Lecture>> call, Response<ArrayList<Lecture>> response) {
                        if(response.code() == 200 && response.body() != null){
                            lectures.clear();
                            lectures.addAll(response.body());
                            adapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(getContext(), AppString.ERROR_LOAD_LECTURE_LIST,Toast.LENGTH_SHORT).show();
                            Log.e("LectureFragment",response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Lecture>> call, Throwable t) {
                        Toast.makeText(getContext(),AppString.ERROR_NETWORK_MESSAGE,Toast.LENGTH_SHORT).show();
                        Log.e("LectureFragment",t.getMessage(),t);
                    }
                });
    }

}
