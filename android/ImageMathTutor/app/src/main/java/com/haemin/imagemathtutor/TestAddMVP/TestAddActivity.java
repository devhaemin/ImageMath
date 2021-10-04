package com.haemin.imagemathtutor.TestAddMVP;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.textfield.TextInputEditText;
import com.haemin.imagemathtutor.Data.Lecture;
import com.haemin.imagemathtutor.Data.ServerFile;
import com.haemin.imagemathtutor.R;
import com.haemin.imagemathtutor.View.UI.FileButton;
import com.haemin.imagemathtutor.View.UI.ListPickerDialog;

import java.io.File;
import java.util.*;

import static com.haemin.imagemathtutor.NoticeEditMVP.NoticeEditActivity.getPath;

public class TestAddActivity extends AppCompatActivity implements TestAddContract.TestAddView {

    private static final int ADD_ANSWER_FILE_CODE = 123;
    private static final int GET_EXCEL_FILE = 456;
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.btn_edit_complete)
    ImageButton btnEditComplete;
    @BindView(R.id.text_test_lecture)
    TextView textTestLecture;
    @BindView(R.id.text_end_day)
    TextView textEndDay;
    @BindView(R.id.text_lecture_day)
    TextView textLectureDay;
    @BindView(R.id.edit_test_name)
    TextInputEditText editTestName;
    @BindView(R.id.edit_test_contents)
    TextInputEditText editTestContents;
    @BindView(R.id.edit_test_xls_num)
    TextInputEditText editTestXlsNum;
    @BindView(R.id.btn_add_file)
    Button btnAddFile;
    @BindView(R.id.group_file)
    LinearLayout groupFile;
    @BindView(R.id.btn_test_excel)
    Button btnTestExcel;
    @BindView(R.id.recycler_student_test)
    RecyclerView recyclerStudentTest;
    @BindView(R.id.btn_excel_file)
    FileButton btnExcelFile;

    TestAddPresenter presenter;
    Lecture lecture;
    HashMap<String, String> testField;
    long endTime = 0;
    long lectureTime = 0;
    long postTime = 0;
    Calendar endCalendar;
    Calendar lectureCalendar;

    ServerFile excelFile;
    ArrayList<ServerFile> answerFiles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_add);
        {

            ButterKnife.bind(this);
            presenter = new TestAddPresenter(this);
            testField = new HashMap<>();

            endCalendar = new GregorianCalendar();
            endCalendar.setTimeInMillis(System.currentTimeMillis());
            lectureCalendar = new GregorianCalendar();
            lectureCalendar.setTimeInMillis(System.currentTimeMillis());
            answerFiles = new ArrayList<>();

        }
        {
            btnBack.setOnClickListener(v -> finish());
            btnEditComplete.setOnClickListener(v -> {
                if (checkData()) presenter.uploadTest(testField, answerFiles, excelFile);
            });
            textTestLecture.setOnClickListener(v -> {
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
            btnTestExcel.setOnClickListener(v -> {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(i, GET_EXCEL_FILE);
            });

        }
    }

    @Override
    public int getXlsNum() {
        if (editTestXlsNum.getText().toString().equals("")) {
            return 0;
        } else {
            return Integer.parseInt(editTestXlsNum.getText().toString());
        }
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
                    File file = new File(getPath(this, uri) + "");
                    answerFile.setFileSeq(answerFiles.size());
                    answerFile.setFileUrl(getPath(this, uri));
                    answerFile.setFileType(ServerFile.FILE_TYPE_NORMAL);
                    answerFile.setFileName(file.getName() + "");
                    answerFiles.add(answerFile);
                }

                FileButton fileButton = new FileButton(this);
                fileButton.setFile(answerFile);
                fileButton.setOnDeleteClickListener((fileButton1, file) -> {
                    Iterator<ServerFile> iter = answerFiles.iterator();
                    Log.e("TestAddActivity", "DeleteButton Clicked!");
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
        if (requestCode == GET_EXCEL_FILE && resultCode == Activity.RESULT_OK) {

            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                File file = new File(getPath(this, uri) + "");
                excelFile = new ServerFile();
                excelFile.setFileUrl(getPath(this, uri));
                excelFile.setFileType(ServerFile.FILE_TYPE_NORMAL);
                excelFile.setFileName(file.getName() + "");
            }
            btnExcelFile.setFile(excelFile);
            btnExcelFile.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public String getLectureSeq() {

        return lecture.getLectureSeq();
    }

    private boolean checkData() {
        String testName = editTestName.getText().toString();
        String testContents = editTestContents.getText().toString();
        if (testName.equals("")) {
            showToast("테스트 이름을 입력해주세요.");
            return false;
        } else {
            testField.put("title", testName);
        }
        if (testContents.equals("")) {
            showToast("테스트 내용을 입력해주세요.");
            return false;
        } else {
            testField.put("contents", testContents);
        }
        if (lecture == null) {
            showToast("수업을 선택해주세요.");
            return false;
        } else {
            testField.put("lectureSeq", lecture.getLectureSeq());
            testField.put("lectureName", lecture.getName());
        }
        if (lectureTime == 0) {
            showToast("수업이 진행된 시간을 선택해주세요.");
            return false;
        } else {
            testField.put("lectureTime", lectureTime + "");
        }

        postTime = System.currentTimeMillis();
        endTime = System.currentTimeMillis();
        testField.put("endTime", endTime + "");
        testField.put("postTime", postTime + "");

        return true;

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
            textTestLecture.setText(data.getName());
            lectureListPickerDialog.dismiss();
        });
        lectureListPickerDialog.show(getSupportFragmentManager(), "aewf");
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
}
