package com.haemin.imagemathtutor.TestInfoMVP;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.Data.ServerFile;
import com.haemin.imagemathtutor.Data.StudentTest;
import com.haemin.imagemathtutor.Data.Test;
import com.haemin.imagemathtutor.R;
import com.haemin.imagemathtutor.Utils.ConfirmStarter;
import com.haemin.imagemathtutor.View.UI.FileButton;

import java.util.ArrayList;

public class TestInfoActivity extends AppCompatActivity implements TestInfoContract.TestInfoView {

    String testSeq;
    TestInfoContract.TestInfoPresenter presenter;
    ArrayList<StudentTest> studentTests;
    TestInfoAdapter infoAdapter;

    @BindView(R.id.btn_edit_test)
    ImageButton btnEditTest;
    @BindView(R.id.text_test_name)
    TextView textTestName;
    @BindView(R.id.text_lecture_name)
    TextView textLectureName;
    @BindView(R.id.text_test_notice)
    TextView textTestContents;
    @BindView(R.id.text_test_average)
    TextView textTestAverage;
    @BindView(R.id.recycler_student_test)
    RecyclerView recyclerStudentTest;
    @BindView(R.id.group_file)
    LinearLayout groupFile;
    @BindView(R.id.text_upload_day)
    TextView textUploadDay;
    @BindView(R.id.text_end_day)
    TextView textEndDay;
    @BindView(R.id.text_lecture_day)
    TextView textLectureDay;
    @BindView(R.id.btn_delete)
    TextView btnDelete;

    public static void start(Context context, Test test) {
        Intent starter = new Intent(context, TestInfoActivity.class);
        starter.putExtra("testSeq",test.getTestSeq()+"");
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_info);
        ButterKnife.bind(this);
        {
            Intent fromOutside = getIntent();
            ConfirmStarter.checkIntent(this,fromOutside);
            testSeq = fromOutside.getStringExtra("testSeq");
        }
        {
            presenter = new TestInfoPresenter(this);
            studentTests = new ArrayList<>();
            infoAdapter = new TestInfoAdapter(this,studentTests);
        }
        {
            recyclerStudentTest.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
            recyclerStudentTest.setAdapter(infoAdapter);
        }
        btnEditTest.setOnClickListener(v -> {
            showToast("준비중인 기능입니다.");
        });
        presenter.requestStudentList(testSeq);
        presenter.requestTestInfo(testSeq);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateTestInfo(Test test) {
        textTestName.setText(test.getTitle());
        textLectureName.setText(test.getLectureName());
        textTestContents.setText(test.getContents());
        textTestAverage.setText(test.getAverageScore()+"점");
        textUploadDay.setText(DateUtils.getRelativeTimeSpanString(test.getPostTime()));
        textLectureDay.setText(DateUtils.getRelativeTimeSpanString(test.getLectureTime()));
        textEndDay.setText(DateUtils.getRelativeTimeSpanString(test.getEndTime()));
        if(test.getAnswerFiles() != null){

            for(ServerFile serverFile : test.getAnswerFiles()){
                FileButton fileButton = new FileButton(this);
                fileButton.setFile(serverFile);
                fileButton.setDeleteAble(false);
                groupFile.addView(fileButton);
            }
        }
        {
            btnDelete.setOnClickListener(v -> {
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setCancelable(true)
                        .setMessage("정말로 삭제하시겠습니까?")
                        .setNegativeButton("취소", (dialog1, which) -> {dialog1.dismiss();})
                        .setPositiveButton("확인",(dialog1, which) -> {presenter.deleteTestInfo(testSeq);})
                        .create();
                dialog.show();
            });
        }
    }

    @Override
    public void showDeleteSuccess() {
        Toast.makeText(this,"테스트 삭제가 완료되었습니다.",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void updateRecycler(ArrayList<StudentTest> studentTests) {
        this.studentTests.clear();
        this.studentTests.addAll(studentTests);
        infoAdapter.notifyDataSetChanged();
    }
}
