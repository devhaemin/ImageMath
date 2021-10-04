package com.haemin.imagemathtutor.AssignmentInfoMVP;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.AssignmentEditMVP.AssignmentEditActivity;
import com.haemin.imagemathtutor.Data.Assignment;
import com.haemin.imagemathtutor.Data.ServerFile;
import com.haemin.imagemathtutor.Data.StudentAssignment;
import com.haemin.imagemathtutor.R;
import com.haemin.imagemathtutor.Utils.ConfirmStarter;
import com.haemin.imagemathtutor.View.UI.FileButton;

import java.util.ArrayList;

public class AssignmentInfoActivity extends AppCompatActivity implements AssignmentInfoContract.AssignmentInfoView {

    String assignmentSeq;
    AssignmentInfoContract.AssignmentInfoPresenter presenter;
    AssignmentStudentAdapter studentAdapter;
    Assignment mainAssignment;
    ArrayList<StudentAssignment> students;

    @BindView(R.id.btn_edit_assignment)
    ImageButton btnEditAssignment;
    @BindView(R.id.text_assignment_name)
    TextView textAssignmentName;
    @BindView(R.id.text_lecture_name)
    TextView textLectureName;
    @BindView(R.id.text_assignment_notice)
    TextView textAssignmentNotice;
    @BindView(R.id.text_upload_day)
    TextView textUploadDay;
    @BindView(R.id.text_end_day)
    TextView textEndDay;
    @BindView(R.id.text_lecture_day)
    TextView textLectureDay;
    @BindView(R.id.recycler_student_assignment)
    RecyclerView recyclerAssignment;
    @BindView(R.id.group_file)
    LinearLayout groupFile;
    @BindView(R.id.btn_delete)
    TextView btnDelete;


    public static void start(Context context, Assignment assignment) {
        Intent starter = new Intent(context, AssignmentInfoActivity.class);
        starter.putExtra("assignmentSeq",assignment.getAssignmentSeq()+"");
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_info);

        {
            Intent fromOutside = getIntent();
            ConfirmStarter.checkIntent(this, fromOutside);
            assignmentSeq = fromOutside.getStringExtra("assignmentSeq");

            presenter = new AssignmentInfoPresenter(this);
            students = new ArrayList<>();
            mainAssignment = new Assignment();
            studentAdapter = new AssignmentStudentAdapter(this,students, mainAssignment, presenter);
        }

        {
            ButterKnife.bind(this);
            btnEditAssignment.setOnClickListener(v -> {
                AssignmentEditActivity.start(this, assignmentSeq);
            });
            recyclerAssignment.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
            recyclerAssignment.setAdapter(studentAdapter);
        }
        {
            presenter.requestAssignmentData(assignmentSeq);
            presenter.requestStudentData(assignmentSeq);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        {
            presenter.requestAssignmentData(assignmentSeq);
            presenter.requestStudentData(assignmentSeq);
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateView(Assignment assignment) {
        assignment.set(assignment);
        textAssignmentNotice.setText(assignment.getContents());
        textAssignmentName.setText(assignment.getTitle());
        textLectureName.setText(assignment.getLectureName());
        textUploadDay.setText(DateUtils.getRelativeTimeSpanString(assignment.getPostTime()));
        textEndDay.setText(DateUtils.getRelativeTimeSpanString(assignment.getEndTime()));
        textLectureDay.setText(DateUtils.getRelativeTimeSpanString(assignment.getLectureTime()));
        if(assignment.getAnswerFiles() != null){
            groupFile.removeAllViews();
            for(ServerFile serverFile : assignment.getAnswerFiles()){
                FileButton fileButton = new FileButton(this);
                fileButton.setFile(serverFile);
                fileButton.setDeleteAble(false);
                groupFile.addView(fileButton);
            }
        }else{
            Toast.makeText(this,"해설지가 존재하지 않습니다.\n새로 등록해주세요.",Toast.LENGTH_SHORT).show();
        }

        {
            btnDelete.setOnClickListener(v -> {
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setCancelable(true)
                        .setMessage("정말로 삭제하시겠습니까?")
                        .setNegativeButton("취소", (dialog1, which) -> {dialog1.dismiss();})
                        .setPositiveButton("확인",(dialog1, which) -> {presenter.deleteAssignment(assignmentSeq);})
                        .create();
                dialog.show();
            });
        }

    }

    @Override
    public void updateRecycler(ArrayList<StudentAssignment> users) {
        this.students.clear();
        this.students.addAll(users);
        studentAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDeleteSuccess() {
        Toast.makeText(this,"과제가 삭제되었습니다.",Toast.LENGTH_SHORT).show();
        finish();
    }
}
