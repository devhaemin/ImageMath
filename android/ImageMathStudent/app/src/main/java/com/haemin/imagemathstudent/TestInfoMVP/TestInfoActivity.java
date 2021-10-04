package com.haemin.imagemathstudent.TestInfoMVP;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathstudent.Data.ServerFile;
import com.haemin.imagemathstudent.Data.StudentTest;
import com.haemin.imagemathstudent.R;
import com.haemin.imagemathstudent.Utils.ConfirmStarter;
import com.haemin.imagemathstudent.View.UI.FileButton;

import java.util.ArrayList;

public class TestInfoActivity extends AppCompatActivity implements TestInfoContract.TestInfoView {

    String testAdmSeq;
    String testSeq;
    TestInfoPresenter presenter;

    @BindView(R.id.text_test_score)
    TextView textTestScore;
    @BindView(R.id.text_test_rank)
    TextView textTestRank;
    @BindView(R.id.text_test_name)
    TextView textTestName;
    @BindView(R.id.text_lecture_name)
    TextView textLectureName;
    @BindView(R.id.text_test_notice)
    TextView textTestNotice;
    @BindView(R.id.text_upload_day)
    TextView textUploadDay;
    @BindView(R.id.text_lecture_day)
    TextView textLectureDay;
    @BindView(R.id.group_file)
    LinearLayout groupFile;

    public static void start(Context context, StudentTest test) {
        Intent starter = new Intent(context, TestInfoActivity.class);
        starter.putExtra("testAdmSeq", test.getTestAdmSeq()+"");
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
            ConfirmStarter.checkIntent(this, fromOutside);
            testAdmSeq = fromOutside.getStringExtra("testAdmSeq");
            testSeq = fromOutside.getStringExtra("testSeq");
        }
        {
            presenter = new TestInfoPresenter(this);
            presenter.updateData(testAdmSeq);
            presenter.getAnswerFilesInfo(testSeq);
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateView(StudentTest test) {
        textLectureName.setText(test.getLectureName());
        textTestName.setText(test.getTitle());
        textTestNotice.setText(test.getContents());
        textTestRank.setText(test.getRank()+"등");
        textTestScore.setText(test.getScore()+"점");
        textUploadDay.setText(DateUtils.getRelativeTimeSpanString(test.getPostTime()));
        textLectureDay.setText(DateUtils.getRelativeTimeSpanString(test.getLectureTime()));

    }

    @Override
    public void updateAnswerView(ArrayList<ServerFile> answerFiles) {

        if(answerFiles != null){
            groupFile.removeAllViews();
            for(ServerFile serverFile : answerFiles){
                FileButton fileButton = new FileButton(this);
                fileButton.setFile(serverFile);
                fileButton.setDeleteAble(false);
                groupFile.addView(fileButton);
            }
        }else{
            Toast.makeText(this,"해설지가 존재하지 않습니다.\n관련 조교에게 문의해주세요.",Toast.LENGTH_SHORT).show();
        }
    }
}
