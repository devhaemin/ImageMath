package com.haemin.imagemathstudent.NoticeActivityMVP;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathstudent.Data.Lecture;
import com.haemin.imagemathstudent.Data.Notice;
import com.haemin.imagemathstudent.R;

import java.util.ArrayList;

public class NoticeActivity extends AppCompatActivity implements NoticeContract.NoticeView {


    String lectureName;
    String lectureSeq;
    NoticePresenter presenter;
    NoticeAdapter noticeAdapter;
    ArrayList<Notice> notices;

    @BindView(R.id.text_lecture_name)
    TextView textLectureName;
    @BindView(R.id.notice_recycler)
    RecyclerView noticeRecycler;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;


    public static void start(Context context, Lecture lecture) {
        Intent starter = new Intent(context, NoticeActivity.class);
        starter.putExtra("lectureName",lecture.getName());
        starter.putExtra("lectureSeq",lecture.getLectureSeq());
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        Intent fromOutside = getIntent();
        lectureSeq = fromOutside.getStringExtra("lectureSeq");
        lectureName = fromOutside.getStringExtra("lectureName");
        notices = new ArrayList<>();

        noticeAdapter = new NoticeAdapter(this,notices);
        noticeRecycler.setAdapter(noticeAdapter);
        noticeRecycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        textLectureName.setText(lectureName);
        presenter = new NoticePresenter(this);
        presenter.updateData(lectureSeq);

        refreshLayout.setOnRefreshListener(() -> {
            presenter.updateData(lectureSeq);
            refreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void updateView(ArrayList<Notice> notices) {
        this.notices.clear();
        this.notices.addAll(notices);
        noticeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}
