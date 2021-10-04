package com.haemin.imagemathtutor.StudentInfoMVP;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class StudentInfoPagerAdapter extends FragmentPagerAdapter {


    SubmitInfoFragment submitInfoFragment;
    StudentLectureFragment studentLectureFragment;

    public StudentInfoPagerAdapter(@NonNull FragmentManager fm, int behavior, String userSeq) {
        super(fm, behavior);
        studentLectureFragment = StudentLectureFragment.newInstance(userSeq+"");
        submitInfoFragment = SubmitInfoFragment.newInstance(userSeq+"");
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return studentLectureFragment;
            case 1:
                return submitInfoFragment;
        }
        return studentLectureFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
