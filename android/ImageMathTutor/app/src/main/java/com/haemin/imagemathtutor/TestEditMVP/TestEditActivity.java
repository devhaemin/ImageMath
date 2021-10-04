package com.haemin.imagemathtutor.TestEditMVP;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.textfield.TextInputEditText;
import com.haemin.imagemathtutor.R;

public class TestEditActivity extends AppCompatActivity {


    @BindView(R.id.text_lecture_name)
    TextView textLectureName;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn_edit_complete)
    Button btnEditComplete;
    @BindView(R.id.text_test_name)
    TextView textTestName;
    @BindView(R.id.text_upload_day)
    TextView textUploadDay;
    @BindView(R.id.text_end_day)
    TextView textEndDay;
    @BindView(R.id.text_lecture_day)
    TextView textLectureDay;
    @BindView(R.id.edit_test_name)
    TextInputEditText editTestName;
    @BindView(R.id.edit_test_contents)
    TextInputEditText editTestContents;
    @BindView(R.id.group_file)
    LinearLayout groupFile;
    @BindView(R.id.btn_test_excel)
    Button btnTestExcel;
    @BindView(R.id.recycler_student_test)
    RecyclerView recyclerStudentTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_edit);
        ButterKnife.bind(this);
    }

}
