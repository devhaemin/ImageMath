package com.haemin.imagemathtutor.AssignmentEditMVP;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.textfield.TextInputEditText;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.AssignmentAddMVP.AssignmentAddContract;
import com.haemin.imagemathtutor.AssignmentAddMVP.AssignmentAddPresenter;
import com.haemin.imagemathtutor.Data.Assignment;
import com.haemin.imagemathtutor.Data.Lecture;
import com.haemin.imagemathtutor.Data.ServerFile;
import com.haemin.imagemathtutor.GlobalApplication;
import com.haemin.imagemathtutor.R;
import com.haemin.imagemathtutor.View.UI.FileButton;
import com.haemin.imagemathtutor.View.UI.ListPickerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.util.*;

import static com.haemin.imagemathtutor.NoticeEditMVP.NoticeEditActivity.getPath;

public class AssignmentEditActivity extends AppCompatActivity implements AssignmentEditContract.AssignmentEditView {

    private static final int ADD_ANSWER_FILE_CODE = 564;
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.btn_edit_complete)
    ImageButton btnEditComplete;
    @BindView(R.id.text_assignment_lecture)
    TextView textAssignmentLecture;
    @BindView(R.id.text_end_day)
    TextView textEndDay;
    @BindView(R.id.text_lecture_day)
    TextView textLectureDay;
    @BindView(R.id.edit_assignment_name)
    TextInputEditText editAssignmentName;
    @BindView(R.id.edit_assignment_contents)
    TextInputEditText editAssignmentContents;
    @BindView(R.id.btn_add_file)
    Button btnAddFile;
    @BindView(R.id.group_file)
    LinearLayout groupFile;

    HashMap<String, String> assignmentField;
    AssignmentEditContract.AssignmentEditPresenter presenter;
    Lecture lecture;
    long endTime = 0;
    long lectureTime = 0;
    long postTime = 0;
    Calendar endCalendar;
    Calendar lectureCalendar;

    ArrayList<ServerFile> answerFiles;
    String assignmentSeq;

    public static void start(Context context, String assignmentSeq) {
        Intent starter = new Intent(context, AssignmentEditActivity.class);
        starter.putExtra("assignmentSeq",assignmentSeq+"");
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_edit);
        ButterKnife.bind(this);
        Intent fromOutside = getIntent();
        assignmentSeq = fromOutside.getStringExtra("assignmentSeq");
        {
            presenter = new AssignmentEditPresenter(this);
            assignmentField = new HashMap<>();
            answerFiles = new ArrayList<>();

            endCalendar = new GregorianCalendar();
            endCalendar.setTimeInMillis(System.currentTimeMillis());
            lectureCalendar = new GregorianCalendar();
            lectureCalendar.setTimeInMillis(System.currentTimeMillis());
        }
        {
            btnBack.setOnClickListener(v -> finish());
            btnEditComplete.setOnClickListener(v -> {
                if (checkData()) presenter.uploadAssignment(assignmentField, answerFiles);
            });
            textAssignmentLecture.setOnClickListener(v -> {
                presenter.requestLectureData();
            });
            textEndDay.setOnClickListener(v -> {
                showDate(1);
            });
            textLectureDay.setOnClickListener(v -> {
                showDate(0);
            });
            btnAddFile.setOnClickListener(v -> {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(i, ADD_ANSWER_FILE_CODE);
            });

        }
        presenter.updateAssignmentData(assignmentSeq);
    }

    @Override
    public void showAssignmentData(Assignment assignment) {
        lecture = new Lecture();
        lecture.setLectureSeq(assignment.getLectureSeq());
        lecture.setName(assignment.getLectureName());
        postTime = assignment.getPostTime();
        lectureTime = assignment.getLectureTime();
        endTime = assignment.getEndTime();

        textAssignmentLecture.setText(assignment.getLectureName());
        textEndDay.setText(DateUtils.getRelativeTimeSpanString(assignment.getEndTime()));
        textLectureDay.setText(DateUtils.getRelativeTimeSpanString(assignment.getLectureTime()));
        editAssignmentName.setText(assignment.getTitle());
        editAssignmentContents.setText(assignment.getContents());

        groupFile.removeAllViews();
        if(assignment.getAnswerFiles() != null){
            for(ServerFile serverFile : assignment.getAnswerFiles()){
                FileButton fileButton = new FileButton(this);
                fileButton.setFile(serverFile);
                fileButton.setDeleteAble(true);
                groupFile.addView(fileButton);
                fileButton.setOnDeleteClickListener((fileButton1, file) -> {
                    GlobalApplication.getAPIService().deleteAnswerFile(GlobalApplication.getAccessToken(),serverFile.getFileSeq()+"").enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.code() == 200){
                                presenter.updateAssignmentData(assignmentSeq);
                                showToast("파일삭제가 성공적으로 이루어졌습니다.");
                            }else{
                                Log.e("AssignmentEditActivity",response.message());
                                showToast(AppString.ERROR_DELETE_FILE);
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("AssignmentEditActivity",t.getMessage(),t);
                            showToast(AppString.ERROR_NETWORK_MESSAGE);
                        }
                    });
                });
            }
        }else{
            Toast.makeText(this,"해설지가 존재하지 않습니다.\n새로 등록해주세요.",Toast.LENGTH_SHORT).show();
        }
    }

    void showDate(int mode) {
        Calendar combinedCal = new GregorianCalendar();
        combinedCal.setTime(new Date(System.currentTimeMillis()));
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (mode == 0) {
                    lectureCalendar.set(Calendar.YEAR, year);
                    lectureCalendar.set(Calendar.MONTH, month);
                    lectureCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                } else {
                    endCalendar.set(Calendar.YEAR, year);
                    endCalendar.set(Calendar.MONTH, month);
                    endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                }
                showTime(mode);
            }
        }, combinedCal.get(Calendar.YEAR), combinedCal.get(Calendar.MONTH), combinedCal.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.setMessage("시간을 선택해주세요.");
        datePickerDialog.show();
    }

    void showTime(int mode) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                if (mode == 0) {
                    lectureCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    lectureCalendar.set(Calendar.MINUTE, minute);
                    lectureTime = lectureCalendar.getTimeInMillis();
                    textLectureDay.setText(DateUtils.getRelativeTimeSpanString(lectureTime));
                } else {
                    endCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    endCalendar.set(Calendar.MINUTE, minute);
                    endTime = endCalendar.getTimeInMillis();
                    textEndDay.setText(DateUtils.getRelativeTimeSpanString(endTime));
                }
            }
        }, 20, 0, true);

        timePickerDialog.setMessage("시간을 선택해주세요.");
        timePickerDialog.show();
    }

    private boolean checkData() {
        String testName = editAssignmentName.getText().toString();
        String testContents = editAssignmentContents.getText().toString();
        if (testName.equals("")) {
            showToast("과제 이름을 입력해주세요.");
            return false;
        } else {
            assignmentField.put("title", testName);
        }
        if (testContents.equals("")) {
            showToast("과제 내용을 입력해주세요.");
            return false;
        } else {
            assignmentField.put("contents", testContents);
        }
        if (lecture == null) {
            showToast("수업을 선택해주세요.");
            return false;
        } else {
            assignmentField.put("lectureSeq", lecture.getLectureSeq());
            assignmentField.put("lectureName", lecture.getName());
        }
        if (lectureTime == 0) {
            showToast("수업이 진행된 시간을 선택해주세요.");
            return false;
        } else {
            assignmentField.put("lectureTime", lectureTime + "");
        }
        if (endTime == 0) {
            showToast("괴제 마감 시간을 선택해주세요.");
            return false;
        } else {
            assignmentField.put("endTime", endTime + "");
        }
        postTime = System.currentTimeMillis();
        assignmentField.put("postTime", postTime + "");
        assignmentField.put("assignmentSeq",assignmentSeq);
        return true;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == ADD_ANSWER_FILE_CODE && resultCode == Activity.RESULT_OK) {

            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();

                ServerFile answerFile = new ServerFile();
                {
                    File file = new File(getPath(this,uri)+"");
                    answerFile.setFileSeq(answerFiles.size());
                    answerFile.setFileUrl(getPath(this, uri));
                    answerFile.setFileType(ServerFile.FILE_TYPE_NORMAL);
                    answerFile.setFileName(file.getName()+"");
                    answerFiles.add(answerFile);
                }

                FileButton fileButton = new FileButton(this);
                fileButton.setFile(answerFile);
                fileButton.setOnDeleteClickListener((fileButton1, file) ->{
                    Iterator<ServerFile> iter = answerFiles.iterator();
                    while (iter.hasNext()) {
                        ServerFile serverFile = iter.next();
                        if (serverFile.getFileSeq() == file.getFileSeq()) {
                            groupFile.removeView(fileButton1);
                            iter.remove();
                        }
                    }

                });

                groupFile.addView(fileButton);
            }
        }
    }


    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showLectureDialog(ArrayList<Lecture> lectures) {
        ListPickerDialog<Lecture> lectureListPickerDialog = new ListPickerDialog<>(lectures, "수업을 선택해주세요.");
        lectureListPickerDialog.setOnItemClickListener(data -> {
            this.lecture = data;
            textAssignmentLecture.setText(data.getName());
        });
        lectureListPickerDialog.show(getSupportFragmentManager(), "wrerwe");

    }
}
