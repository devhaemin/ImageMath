package com.haemin.imagemathtutor.StudentManageMVP;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.Data.Lecture;
import com.haemin.imagemathtutor.Data.User;
import com.haemin.imagemathtutor.GlobalApplication;
import com.haemin.imagemathtutor.R;
import com.haemin.imagemathtutor.Utils.ConfirmStarter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class StudentManageActivity extends AppCompatActivity implements StudentManageContract.StudentManageView {

    String lectureSeq;
    String lectureName;

    StudentManageAdapter adapter;
    ArrayList<User> students;

    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.text_lecture_name)
    TextView textLectureName;
    @BindView(R.id.recycler_student_manage)
    RecyclerView recyclerStudentManage;
    @BindView(R.id.btn_delete_students)
    Button btnDeleteStudents;
    @BindView(R.id.btn_post_push)
    Button btnPostPush;
    @BindView(R.id.toggle_check_all_student)
    CheckBox toggleCheckAll;


    StudentManagePresenter presenter;


    public static void start(Context context, Lecture lecture) {
        Intent starter = new Intent(context, StudentManageActivity.class);
        starter.putExtra("lectureSeq", lecture.getLectureSeq());
        starter.putExtra("lectureName", lecture.getName());
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_manage);
        ButterKnife.bind(this);

        {
            Intent fromOutside = getIntent();
            ConfirmStarter.checkIntent(this, fromOutside);
            presenter = new StudentManagePresenter(this);
            lectureSeq = fromOutside.getStringExtra("lectureSeq");
            lectureName = fromOutside.getStringExtra("lectureName");
        }

        {
            students = new ArrayList<>();
            adapter = new StudentManageAdapter(this, students);
        }

        {
            recyclerStudentManage.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            recyclerStudentManage.setAdapter(adapter);
        }
        {
            btnBack.setOnClickListener(v -> finish());
            textLectureName.setText(lectureName);
            presenter.requestStudentList(lectureSeq);
            btnDeleteStudents.setOnClickListener(v -> {
                for (User user : students) {
                    if (user.isChecked()) presenter.requestDeleteStudent(lectureSeq, user.getUserSeq());
                }
                presenter.requestStudentList(lectureSeq);
            });
            toggleCheckAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
                for (User user : students) {
                    user.setChecked(isChecked);
                }
                adapter.notifyDataSetChanged();
            });
            btnPostPush.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("알림메시지 작성");

// Set up the input
                final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("전송", (dialog, which) -> {
                    for (User user : students) {

                        if (user.isChecked()) {
                            GlobalApplication.getAPIService().postPushAlarm(GlobalApplication.getAccessToken(), user.getUserSeq(), input.getText().toString()).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.code() != 200) {
                                        Toast.makeText(StudentManageActivity.this, "푸시알림 전송에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });
                        }
                    }
                });
                builder.setNegativeButton("취소", (dialog, which) -> dialog.cancel());

                builder.show();


            });
        }

    }

    @Override
    public void updateStudentList(ArrayList<User> students) {
        this.students.clear();
        this.students.addAll(students);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
