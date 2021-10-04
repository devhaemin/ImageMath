package com.haemin.imagemathtutor.Adapter;

import android.app.AlertDialog;
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
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.Lecture;
import com.haemin.imagemathtutor.Data.Notice;
import com.haemin.imagemathtutor.GlobalApplication;
import com.haemin.imagemathtutor.NoticeMVP.NoticeActivity;
import com.haemin.imagemathtutor.R;
import com.haemin.imagemathtutor.StudentManageMVP.StudentManageActivity;
import com.haemin.imagemathtutor.View.Activity.*;
import com.haemin.imagemathtutor.View.UI.ToggleConstraintLayout;
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
            NoticeActivity.start(context, lecture.getLectureSeq(), lecture.getName());
        });
        if (lecture.isExpired()) {
            holder.toggleWhole.setChecked(false);
            holder.toggleBtnList.setChecked(false);
        } else {
            holder.toggleWhole.setChecked(true);
            holder.toggleBtnList.setChecked(true);
        }
        holder.btnStudentManage.setOnClickListener(v -> {
            StudentManageActivity.start(context, lecture);
        });
        holder.btnRecognition.setOnClickListener(v -> {
            RecognitionActivity.start(context, lecture);
        });
        holder.btnDeleteLecture.setOnClickListener(v -> {
            checkDelete(position);
        });
        holder.btnSetExpired.setOnClickListener(v -> {
            checkExpired(position);
        });

        holder.textLectureName.setText(lecture.getName());
        holder.textClassTimes.setText(lecture.getTime());
        holder.textAcademyName.setText(lecture.getAcademyName());
        holder.textClassDuration.setText(lecture.getTotalDate());

        GlobalApplication.getAPIService().getNoticeList(GlobalApplication.getAccessToken(), lecture.getLectureSeq() + "")
                .enqueue(new Callback<ArrayList<Notice>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Notice>> call, Response<ArrayList<Notice>> response) {
                        if (response.code() == 200 && response.body() != null) {
                            ArrayList<Notice> notices = response.body();
                            Log.e("LectureRecyclerAdapter", notices.toString());
                            if (notices.size() >= 2 && notices.get(0) != null && notices.get(1) != null) {
                                holder.notice_preview.setText(notices.get(0).getTitle() + "\n" + notices.get(1).getTitle());
                            } else if (notices.size() == 1 && notices.get(0) != null) {
                                holder.notice_preview.setText(notices.get(0).getTitle());
                            } else {
                                holder.notice_preview.setText("공지사항이 없습니다.");
                            }
                        } else {
                            holder.notice_preview.setText("공지사항 정보를 불러오는데에 실패했습니다.");
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Notice>> call, Throwable t) {
                        //showToast(AppString.ERROR_NETWORK_MESSAGE);
                        Log.e("LectureRecyclerAdapter", t.getMessage(), t);
                    }
                });
        holder.btnAssignment.setOnClickListener(v -> {
            // AssignmentInfoActivity.start(context,lecture);
        });
        holder.btnTest.setOnClickListener(v -> {
            StudentTestActivity.start(context, lecture);
        });
    }

    private void checkExpired(int position) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(lectures.get(position).getName() + " 수업 종강 처리")
                .setMessage("정말로 종강 처리하시겠습니까?")
                .setNegativeButton("취소", (dialog1, which) -> {
                    dialog1.dismiss();
                })
                .setPositiveButton("확인", (dialog1, which) -> {
                    setLectureExpired(position);
                    dialog1.dismiss();
                })
                .create();
        dialog.show();
    }

    private void checkDelete(int position) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(lectures.get(position).getName() + " 수업 삭제")
                .setMessage("정말로 삭제하시겠습니까?")
                .setNegativeButton("취소", (dialog1, which) -> {
                    dialog1.dismiss();
                })
                .setPositiveButton("삭제", (dialog1, which) -> {
                    deleteLecture(position);
                    dialog1.dismiss();
                })
                .create();
        dialog.show();
    }

    private void setLectureExpired(int position) {
        GlobalApplication.getAPIService().setExpiredLecture(GlobalApplication.getAccessToken(), lectures.get(position).getLectureSeq(), 1).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    showToast("성공적으로 수업을 종강처리했습니다.");
                    lectures.get(position).setExpired(true);
                    notifyDataSetChanged();
                } else {
                    showToast("종강처리에 실패했습니다.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showToast(AppString.ERROR_NETWORK_MESSAGE);
            }
        });
    }

    private void deleteLecture(int position) {
        GlobalApplication.getAPIService().deleteLecture(GlobalApplication.getAccessToken(), lectures.get(position).getLectureSeq()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    showToast("성공적으로 수업을 삭제했습니다.");
                    lectures.remove(position);
                    notifyDataSetChanged();
                } else {
                    showToast("수업 삭제에 실패했습니다.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showToast(AppString.ERROR_NETWORK_MESSAGE);
            }
        });
    }

    void showToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
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

        @BindView(R.id.btn_student_manage)
        Button btnStudentManage;
        @BindView(R.id.btn_recognition)
        Button btnRecognition;
        @BindView(R.id.btn_delete_lecture)
        Button btnDeleteLecture;
        @BindView(R.id.btn_set_expired)
        Button btnSetExpired;

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
