package com.haemin.imagemathtutor.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.haemin.imagemathtutor.AssignmentFragmentMVP.AssignmentFragment;
import com.haemin.imagemathtutor.View.Fragment.LectureInfoFragment;
import com.haemin.imagemathtutor.QAFragmentMVP.QAFragment;
import com.haemin.imagemathtutor.TestFragmentMVP.TestFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {


    public static final int FRAGMENT_COUNT = 4;
    LectureInfoFragment lectureInfoFragment;
    AssignmentFragment assignmentFragment;
    TestFragment testFragment;
    QAFragment qaFragment;

    public MainPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        lectureInfoFragment = new LectureInfoFragment();
        assignmentFragment = new AssignmentFragment();
        testFragment = new TestFragment();
        qaFragment = new QAFragment();
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return assignmentFragment;
            case 2:
                return testFragment;
            case 3:
                return qaFragment;
            default:
                return lectureInfoFragment;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 1:
                return "과제";
            case 2:
                return "테스트";
            case 3:
                return "Q&A";
            default:
                return "수강중인 강좌";
        }
    }
}
