package com.haemin.imagemathtutor.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.haemin.imagemathtutor.Data.Lecture;
import com.haemin.imagemathtutor.R;

public class StudentTestActivity extends AppCompatActivity {

    public static void start(Context context, Lecture lecture) {
        Intent starter = new Intent(context, StudentTestActivity.class);
        starter.putExtra("lectureSeq",lecture.getLectureSeq());
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_test);
    }
}
