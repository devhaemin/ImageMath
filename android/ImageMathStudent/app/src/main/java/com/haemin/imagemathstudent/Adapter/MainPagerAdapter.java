package com.haemin.imagemathstudent.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.haemin.imagemathstudent.VideoFragment.VideoFragment;
import com.haemin.imagemathstudent.View.Fragment.AssignmentFragment;
import com.haemin.imagemathstudent.View.Fragment.LectureInfoFragment;
import com.haemin.imagemathstudent.QAMVP.QAFragment;
import com.haemin.imagemathstudent.TestFragmentMVP.TestFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {


    public static final int FRAGMENT_COUNT = 5;
    LectureInfoFragment lectureInfoFragment;
    AssignmentFragment assignmentFragment;
    TestFragment testFragment;
    VideoFragment videoFragment;
    QAFragment qaFragment;

    public MainPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm);
        lectureInfoFragment = new LectureInfoFragment();
        assignmentFragment = new AssignmentFragment();
        testFragment = new TestFragment();
        videoFragment = new VideoFragment();
        qaFragment = new QAFragment();
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return assignmentFragment;
            case 2:
                return testFragment;
            case 3:
                return videoFragment;
            case 4:
                return qaFragment;
            default:
                return lectureInfoFragment;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 1:
                return "과제";
            case 2:
                return "테스트";
            case 3:
                return "영상";
            case 4:
                return "Q&A";
            default:
                return "수강중인 강좌";
        }
    }
}
