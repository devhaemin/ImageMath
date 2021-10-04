package com.haemin.imagemathtutor.NoticeMVP;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.Data.Notice;
import com.haemin.imagemathtutor.NoticeEditMVP.NoticeEditActivity;
import com.haemin.imagemathtutor.R;
import com.haemin.imagemathtutor.Utils.ConfirmStarter;

import java.util.ArrayList;

public class NoticeActivity extends AppCompatActivity implements NoticeContract.NoticeView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_notification)
    ImageButton btnNotification;
    @BindView(R.id.btn_setting)
    ImageButton btnSetting;
    @BindView(R.id.text_lecture_name)
    TextView textLectureName;
    @BindView(R.id.btn_add_notice)
    Button btnAddNotice;
    @BindView(R.id.notice_recycler)
    RecyclerView noticeRecycler;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    NoticeRecyclerAdapter recyclerAdapter;
    NoticeContract.NoticePresenter noticePresenter;

    String lectureSeq;
    String lectureName;
    ArrayList<Notice> notices;

    public static void start(Context context, String lectureSeq, String lectureName) {
        Intent starter = new Intent(context, NoticeActivity.class);
        starter.putExtra("lectureSeq", lectureSeq);
        starter.putExtra("lectureName", lectureName);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        notices = new ArrayList<>();

        Intent fromOutside = getIntent();
        ConfirmStarter.checkIntent(this, fromOutside);

        noticePresenter = new NoticePresenter(this);
        recyclerAdapter = new NoticeRecyclerAdapter(this, notices,noticePresenter);
        noticeRecycler.setAdapter(recyclerAdapter);
        noticeRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        lectureSeq = fromOutside.getStringExtra("lectureSeq");
        lectureName = fromOutside.getStringExtra("lectureName");

        bindListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();

        noticePresenter.updateData(lectureSeq);
        textLectureName.setText(lectureName);

    }

    private void bindListeners() {
        refreshLayout.setOnRefreshListener(() -> {
            noticePresenter.updateData(lectureSeq);
            refreshLayout.setRefreshing(false);
        });
        btnAddNotice.setOnClickListener(v -> {
            NoticeEditActivity.start(this, lectureSeq, lectureName);
        });
    }

    @Override
    public void refreshView(ArrayList<Notice> notices) {
        this.notices.clear();
        this.notices.addAll(notices);
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMessage(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
