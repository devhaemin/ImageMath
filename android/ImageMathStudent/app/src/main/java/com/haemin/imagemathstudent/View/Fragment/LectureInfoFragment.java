package com.haemin.imagemathstudent.View.Fragment;


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
import com.haemin.imagemathstudent.Adapter.LectureRecyclerAdapter;
import com.haemin.imagemathstudent.Data.Lecture;
import com.haemin.imagemathstudent.R;
import com.haemin.imagemathstudent.SingleTon.AppString;
import com.haemin.imagemathstudent.SingleTon.GlobalApplication;
import com.haemin.imagemathstudent.View.UI.ListPickerDialog;
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
    @BindView(R.id.text_no_lecture_data)
    TextView textNoLecture;

    int page = 1;

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
            refresh(toggleExceptExpire.isChecked());
            refreshLayout.setRefreshing(false);
        });

        btnAddLecture.setOnClickListener(v1 -> {
            GlobalApplication.getAPIService().getLectureList(true).enqueue(new Callback<ArrayList<Lecture>>() {
                @Override
                public void onResponse(Call<ArrayList<Lecture>> call, Response<ArrayList<Lecture>> response) {

                    if (response.code() == 200 && response.body() != null) {

                        ListPickerDialog<Lecture> lectureListPickerDialog = new ListPickerDialog<>(response.body(), "수업을 선택하세요.");
                        lectureListPickerDialog.setOnItemClickListener(data -> {
                            requestAddLecture(data);
                        });
                        lectureListPickerDialog.show(getFragmentManager(),"LECTURE_INFO_LIST");
                    } else {
                        showToast(AppString.ERROR_LOAD_LECTURE_LIST);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Lecture>> call, Throwable t) {
                    showToast(AppString.ERROR_NETWORK_MESSAGE);
                    Log.e("LectureInfoFragment", t.getMessage(), t);
                }
            });
        });

        toggleExceptExpire.setOnCheckedChangeListener((buttonView, isChecked) -> {
            refresh(toggleExceptExpire.isChecked());
        });

        refresh(toggleExceptExpire.isChecked());
        return v;
    }

    private void requestAddLecture(Lecture data) {
        GlobalApplication.getAPIService().requestAddLecture(GlobalApplication.getAccessToken(), data.getLectureSeq() + "", data.getName())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            showToast("수업추가 요청이 완료되었습니다.\n수업이 승인될 때까지 기다려주세요.");
                        } else {
                            showToast("이미 승인요청 중이거나 승인이 완료된 수업입니다.");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        showToast(AppString.ERROR_NETWORK_MESSAGE);
                        Log.e("LectureInfoFragment", t.getMessage(), t);
                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();
        refresh(toggleExceptExpire.isChecked());
    }

    private void refresh(boolean exceptExpired) {
        GlobalApplication.getAPIService().getMyLectureList(GlobalApplication.getAccessToken(), exceptExpired, page)
                .enqueue(new Callback<ArrayList<Lecture>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Lecture>> call, Response<ArrayList<Lecture>> response) {
                        if (response.code() == 200 && response.body() != null) {

                            if(response.body().size() !=0) {
                                textNoLecture.setVisibility(View.GONE);
                                lectures.clear();
                                lectures.addAll(response.body());
                                lectureRecyclerAdapter.notifyDataSetChanged();
                                Log.e("LectureInfoFragment", response.body().toString());
                            }else{
                                textNoLecture.setVisibility(View.VISIBLE);
                            }
                        } else {
                            showToast(AppString.ERROR_LOAD_LECTURE_LIST);
                            Log.e("LectureInfoFragment",response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Lecture>> call, Throwable t) {
                        showToast(AppString.ERROR_NETWORK_MESSAGE);
                        Log.e("LectureInfoFragment", t.getMessage(), t);
                    }
                });
    }

    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


}
