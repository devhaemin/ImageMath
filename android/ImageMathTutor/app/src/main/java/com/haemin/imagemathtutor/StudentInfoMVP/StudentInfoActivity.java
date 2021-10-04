package com.haemin.imagemathtutor.StudentInfoMVP;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.tabs.TabLayout;
import com.haemin.imagemathtutor.Data.User;
import com.haemin.imagemathtutor.R;

public class StudentInfoActivity extends AppCompatActivity implements StudentInfoContract.StudentInfoView {

    String userSeq;

    @BindView(R.id.text_student_name)
    TextView textStudentName;
    @BindView(R.id.text_student_num)
    TextView textStudentNum;
    @BindView(R.id.text_student_gender)
    TextView textStudentGender;
    @BindView(R.id.text_student_school_name)
    TextView textStudentSchoolName;
    @BindView(R.id.text_student_birthday)
    TextView textStudentBirthday;
    @BindView(R.id.text_student_register_day)
    TextView textRegisterDay;
    @BindView(R.id.tab_student_info)
    TabLayout tabStudentInfo;
    @BindView(R.id.pager_info)
    ViewPager pagerInfo;
    StudentInfoPresenter presenter;


    public static void start(Context context, String userSeq) {
        Intent starter = new Intent(context, StudentInfoActivity.class);
        starter.putExtra("userSeq",userSeq);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        ButterKnife.bind(this);

        Intent fromOutside = getIntent();
        userSeq = fromOutside.getStringExtra("userSeq");

        StudentInfoPagerAdapter pagerAdapter = new StudentInfoPagerAdapter(getSupportFragmentManager(),2,userSeq);
        pagerInfo.setAdapter(pagerAdapter);
        tabStudentInfo.setupWithViewPager(pagerInfo);
        tabStudentInfo.getTabAt(0).setText("참여수업");
        tabStudentInfo.getTabAt(1).setText("제출현황");
        presenter = new StudentInfoPresenter(this);
        presenter.updateStudentData(userSeq);
    }

    @Override
    public void updateStudentView(User student) {
        textStudentName.setText(student.getName());
        textStudentNum.setText("NO."+student.getStudentCode());
        if(student.getGender() == User.GENDER_MALE)
            textStudentGender.setText("남학생");
        if(student.getGender() == User.GENDER_FEMALE)
            textStudentGender.setText("여학생");
        textStudentSchoolName.setText("학교 ) "+student.getSchoolName());
        textStudentBirthday.setText("생년월일 ) "+student.getBirthday() );
        textRegisterDay.setText("가입일 ) "+ DateUtils.getRelativeTimeSpanString(student.getRegisterTime()));

    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}
