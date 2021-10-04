package com.haemin.imagemathstudent.AssignmentInfoMVP;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathstudent.Data.ServerFile;
import com.haemin.imagemathstudent.Data.StudentAssignment;
import com.haemin.imagemathstudent.R;
import com.haemin.imagemathstudent.Utils.ConfirmStarter;
import com.haemin.imagemathstudent.View.UI.FileButton;
import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

import java.util.ArrayList;

public class AssignmentInfoActivity extends AppCompatActivity implements AssignmentInfoContract.AssignmentInfoView {

    String assignmentSeq;
    AssignmentInfoPresenter presenter;
    @BindView(R.id.img_submit_state)
    TextView textSubmitState;
    @BindView(R.id.text_assignment_name)
    TextView textAssignmentName;
    @BindView(R.id.text_lecture_name)
    TextView textLectureName;
    @BindView(R.id.text_assignment_notice)
    TextView textAssignmentNotice;
    @BindView(R.id.text_overlay_file)
    TextView textOverlayFile;
    @BindView(R.id.btn_answer_file)
    LinearLayout groupFile;
    @BindView(R.id.text_upload_day)
    TextView textUploadDay;
    @BindView(R.id.text_end_day)
    TextView textEndDay;
    @BindView(R.id.text_lecture_day)
    TextView textLectureDay;
    @BindView(R.id.btn_add_submit)
    Button btnAddSubmit;
    @BindView(R.id.recycler_submit)
    RecyclerView recyclerSubmit;

    ImageAdapter imageAdapter;
    ArrayList<ServerFile> imageFiles;


    public static void start(Context context, String assignmentSeq) {
        Intent starter = new Intent(context, AssignmentInfoActivity.class);
        starter.putExtra("assignmentSeq", assignmentSeq);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_info);
        Intent i = getIntent();
        ConfirmStarter.checkIntent(this, i);
        assignmentSeq = i.getStringExtra("assignmentSeq");
        ButterKnife.bind(this);
        presenter = new AssignmentInfoPresenter(this);
        presenter.updateData(assignmentSeq);

        imageFiles = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, imageFiles);

        recyclerSubmit.setAdapter(imageAdapter);
        recyclerSubmit.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

    }

    @Override
    public void updateView(StudentAssignment studentAssignment) {


        switch (studentAssignment.getSubmitState()) {
            case 0:
                textSubmitState.setText("미제출");
                textSubmitState.setTextColor(getResources().getColor(android.R.color.black));
                textOverlayFile.setVisibility(View.VISIBLE);
                if (studentAssignment.getEndTime() < System.currentTimeMillis()) {
                    textSubmitState.setText("불성실");
                    textSubmitState.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    textOverlayFile.setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                textSubmitState.setText("제출");
                textSubmitState.setTextColor(Color.YELLOW);
                textOverlayFile.setVisibility(View.VISIBLE);
                break;
            case 2:
                textSubmitState.setText("A등급");
                textSubmitState.setTextColor(getResources().getColor(R.color.etoos_color));
                textOverlayFile.setVisibility(View.GONE);
                presenter.getAnswerFilesInfo(assignmentSeq);
                break;
            case 3:
                textSubmitState.setText("B등급");
                textSubmitState.setTextColor(getResources().getColor(R.color.etoos_color));
                textOverlayFile.setVisibility(View.GONE);
                presenter.getAnswerFilesInfo(assignmentSeq);
                break;

            case 4:
                textSubmitState.setText("C등급");
                textSubmitState.setTextColor(getResources().getColor(R.color.etoos_color));
                textOverlayFile.setVisibility(View.GONE);
                presenter.getAnswerFilesInfo(assignmentSeq);
                break;

            case 5:
                textSubmitState.setText("불성실");
                textSubmitState.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                textOverlayFile.setVisibility(View.VISIBLE);
                if (studentAssignment.getEndTime() < System.currentTimeMillis()) {
                    textSubmitState.setText("불성실");
                    textSubmitState.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    textOverlayFile.setVisibility(View.VISIBLE);
                }
                break;
        }

        textLectureName.setText(studentAssignment.getLectureName());
        textAssignmentName.setText(studentAssignment.getTitle());
        textAssignmentNotice.setText(studentAssignment.getContents());
        textEndDay.setText(DateUtils.getRelativeTimeSpanString(studentAssignment.getEndTime()));
        textLectureDay.setText(DateUtils.getRelativeTimeSpanString(studentAssignment.getLectureTime()));
        textUploadDay.setText(DateUtils.getRelativeTimeSpanString(studentAssignment.getPostTime()));
        btnAddSubmit.setOnClickListener(v -> {
            TedBottomPicker.with(AssignmentInfoActivity.this)
                    .show(uri -> {
                        // here is selected image uri
                        presenter.submitPicture(assignmentSeq, uri);
                    });
        });
        imageFiles.clear();
        imageFiles.addAll(studentAssignment.getSubmitFiles());
        imageAdapter.notifyDataSetChanged();

    }

    @Override
    public void updateAnswerView(ArrayList<ServerFile> answerFiles) {

        if (answerFiles != null) {
            groupFile.removeAllViews();
            for (ServerFile serverFile : answerFiles) {
                FileButton fileButton = new FileButton(this);
                fileButton.setFile(serverFile);
                fileButton.setDeleteAble(false);
                groupFile.addView(fileButton);
            }
        } else {
            Toast.makeText(this, "해설지가 존재하지 않습니다.\n관련 조교에게 문의해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
