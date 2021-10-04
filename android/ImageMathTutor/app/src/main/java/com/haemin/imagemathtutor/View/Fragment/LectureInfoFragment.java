package com.haemin.imagemathtutor.View.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.Adapter.LectureRecyclerAdapter;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.Lecture;
import com.haemin.imagemathtutor.GlobalApplication;
import com.haemin.imagemathtutor.LectureAddMVP.AddLectureActivity;
import com.haemin.imagemathtutor.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LectureInfoFragment extends Fragment {


    @BindView(R.id.btn_add_lecture)
    Button btnAddLecture;
    @BindView(R.id.toggle_except_expire)
    CheckBox toggleExceptExpire;
    @BindView(R.id.recycler_lecture)
    RecyclerView recyclerLecture;

    ArrayList<Lecture> lectures;
    LectureRecyclerAdapter lectureRecyclerAdapter;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    public LectureInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lecture_info, container, false);
        ButterKnife.bind(this, v);
        lectures = new ArrayList<>();
        lectureRecyclerAdapter = new LectureRecyclerAdapter(getContext(), lectures);

        recyclerLecture.setAdapter(lectureRecyclerAdapter);
        recyclerLecture.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        refreshLayout.setOnRefreshListener(() -> {
            refresh();
            refreshLayout.setRefreshing(false);
        });
        toggleExceptExpire.setOnCheckedChangeListener((buttonView, isChecked) -> {
            refreshLayout.setRefreshing(true);
            refresh();
            refreshLayout.setRefreshing(false);
        });

        btnAddLecture.setOnClickListener(v1 -> {
            Intent i = new Intent(getContext(), AddLectureActivity.class);
            startActivity(i);
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        GlobalApplication.getAPIService().getLectureList(toggleExceptExpire.isChecked()).enqueue(new Callback<ArrayList<Lecture>>() {
            @Override
            public void onResponse(Call<ArrayList<Lecture>> call, Response<ArrayList<Lecture>> response) {
                if(response.code() == 200 && response.body() != null){
                    lectures.clear();
                    lectures.addAll(response.body());
                    lectureRecyclerAdapter.notifyDataSetChanged();
                }else{
                    showToast(AppString.ERROR_LOAD_LECTURE_LIST);
                    Log.e("LectureInfoFragment",response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Lecture>> call, Throwable t) {
                showToast(AppString.ERROR_NETWORK_MESSAGE);
                Log.e("LectureInfoFragment",t.getMessage(),t);
            }
        });
    }

    private void showToast(String text) {
        Toast.makeText(getContext(),text,Toast.LENGTH_SHORT).show();
    }


}
