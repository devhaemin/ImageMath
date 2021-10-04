package com.haemin.imagemathtutor.LectureAddMVP;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.Data.Academy;
import com.haemin.imagemathtutor.Data.ServerTime;
import com.haemin.imagemathtutor.R;
import com.haemin.imagemathtutor.View.UI.AcademyPicker;
import com.haemin.imagemathtutor.View.UI.LectureTimeView;

import java.util.ArrayList;
import java.util.Iterator;

public class AddLectureActivity extends AppCompatActivity implements AddLectureContract.AddLectureView {

    AddLectureContract.AddLecturePresenter presenter;
    Academy academy;
    ArrayList<ServerTime> serverTimes;
    ArrayList<LectureTimeView> lectureTimeViews;

    @BindView(R.id.btn_upload_lecture)
    Button btnUploadLecture;
    @BindView(R.id.btn_add_time)
    Button btnAddTime;
    @BindView(R.id.edit_academy)
    EditText editAcademy;
    @BindView(R.id.edit_lecture_name)
    EditText editLectureName;
    @BindView(R.id.edit_duration_first)
    EditText editDurationFirst;
    @BindView(R.id.edit_duration_second)
    EditText editDurationSecond;
    @BindView(R.id.group_lecture_times)
    LinearLayout holderLectureTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lecture);
        ButterKnife.bind(this);
        presenter = new AddLecturePresenter(this);
        serverTimes = new ArrayList<>();
        lectureTimeViews = new ArrayList<>();

        initView();

    }

    private void initView() {
        btnUploadLecture.setOnClickListener(v -> {
            if (academy == null) {
                Toast.makeText(this, "학원을 선택해주세요.", Toast.LENGTH_SHORT).show();
                return;
            } else if (editAcademy.getText().toString().equals("")) {
                Toast.makeText(this, "수업 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            for (LectureTimeView timeView : lectureTimeViews) {
                serverTimes.add(timeView.getServerTime());
            }
            if (serverTimes.size() == 0) {
                Toast.makeText(this, "수업 시간을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }else{

                presenter.uploadLecture(academy, editLectureName.getText().toString(), serverTimes,
                        editDurationFirst.getText().toString() + "~" + editDurationSecond.getText().toString());

            }
        });

        btnAddTime.setOnClickListener(v -> {
            LectureTimeView lectureTimeView = new LectureTimeView(this);
            lectureTimeView.setOnDeleteClickListener(view -> {
                Iterator<LectureTimeView> iter = lectureTimeViews.iterator();
                while (iter.hasNext()) {
                    LectureTimeView timeView = iter.next();
                    if (timeView.getSeq() == view.getSeq()) {
                        holderLectureTime.removeView(timeView);
                        iter.remove();
                    }
                }
            });
            lectureTimeView.setSeq(lectureTimeViews.size());
            lectureTimeViews.add(lectureTimeView);
            holderLectureTime.addView(lectureTimeView);
        });
        editAcademy.setOnClickListener(v -> {
            presenter.requestAcademyData();
        });
    }

    @Override
    public void showDialog(ArrayList<Academy> academies) {
        AcademyPicker academyPicker = new AcademyPicker(academies);
        academyPicker.show(getSupportFragmentManager(), "TAG");
        academyPicker.setOnItemClickListener(academy1 -> {
            academy = academy1;
            editAcademy.setText(academy.getAcademyName());
        });
    }

    @Override
    public void showSuccess() {
        Toast.makeText(this, "수업이 성공적으로 등록되었습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showErrorMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
