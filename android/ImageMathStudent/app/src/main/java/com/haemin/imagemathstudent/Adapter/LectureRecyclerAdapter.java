package com.haemin.imagemathstudent.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathstudent.Data.Lecture;
import com.haemin.imagemathstudent.Data.Notice;
import com.haemin.imagemathstudent.R;
import com.haemin.imagemathstudent.SingleTon.AppString;
import com.haemin.imagemathstudent.SingleTon.GlobalApplication;
import com.haemin.imagemathstudent.AssignmentInfoMVP.AssignmentInfoActivity;
import com.haemin.imagemathstudent.NoticeActivityMVP.NoticeActivity;
import com.haemin.imagemathstudent.View.UI.ToggleConstraintLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class LectureRecyclerAdapter extends RecyclerView.Adapter<LectureRecyclerAdapter.LectureViewHolder> {

    Context context;
    ArrayList<Lecture> lectures;

    public LectureRecyclerAdapter(Context context, ArrayList<Lecture> lectures) {
        this.context = context;
        this.lectures = lectures;
    }

    @NonNull
    @Override
    public LectureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_lecture, parent, false);

        return new LectureViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LectureViewHolder holder, int position) {
        Lecture lecture = lectures.get(position);
        holder.btnNoticeGroup.setOnClickListener(v -> {
            NoticeActivity.start(context,lecture);
        });
        holder.textLectureName.setText(lecture.getName());
        holder.textClassTimes.setText(lecture.getTime());
        holder.textAcademyName.setText(lecture.getAcademyName());
        holder.textClassDuration.setText(lecture.getTotalDate());
        holder.btnAssignment.setOnClickListener(v -> {
            AssignmentInfoActivity.start(context,lecture.getLectureSeq()+"");
        });
//        holder.btnTest.setOnClickListener(v -> {
//            TestInfoActivity.start(context,lecture,);
//        });
        if(lecture.isExpired()){
            holder.toggleWhole.setChecked(false);
            holder.toggleBtnList.setChecked(false);
        }else{
            holder.toggleWhole.setChecked(true);
            holder.toggleBtnList.setChecked(true);
        }
        GlobalApplication.getAPIService().getNoticeList(GlobalApplication.getAccessToken(),lecture.getLectureSeq()+"",1)
        .enqueue(new Callback<ArrayList<Notice>>() {
            @Override
            public void onResponse(Call<ArrayList<Notice>> call, Response<ArrayList<Notice>> response) {
                if(response.code() == 200 && response.body() != null){
                    ArrayList<Notice> notices = response.body();
                    if(notices.size() >= 2){
                        holder.notice_preview.setText(notices.get(0).getTitle()+"\n"+notices.get(1).getTitle());
                    }else if(notices.size() == 1 && notices.get(0) != null){
                        holder.notice_preview.setText(notices.get(0).getTitle());
                    }else{
                        holder.notice_preview.setText("공지사항이 없습니다.");
                    }
                }else{
                    holder.notice_preview.setText("공지사항 정보를 불러오는데에 실패했습니다.");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Notice>> call, Throwable t) {
                showToast(AppString.ERROR_NETWORK_MESSAGE);
                Log.e("LectureRecyclerAdapter",t.getMessage(),t);
            }
        });

    }

    private void showToast(String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return lectures.size();
    }

    class LectureViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.toggle_whole_recycler)
        ToggleConstraintLayout toggleWhole;
        @BindView(R.id.btn_list_group)
        ToggleConstraintLayout toggleBtnList;

        @BindView(R.id.text_lecture_name)
        TextView textLectureName;
        @BindView(R.id.text_lecture_times)
        TextView textClassTimes;
        @BindView(R.id.text_academy_name)
        TextView textAcademyName;
        @BindView(R.id.text_lecture_duration)
        TextView textClassDuration;



        @BindView(R.id.btn_notice_group)
        ConstraintLayout btnNoticeGroup;

        @BindView(R.id.text_notice_preview)
        TextView notice_preview;

        @BindView(R.id.btn_assignment)
        Button btnAssignment;
        @BindView(R.id.btn_test)
        Button btnTest;

        public LectureViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
