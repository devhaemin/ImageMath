package com.haemin.imagemathtutor.TestFragmentMVP;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.Data.Lecture;
import com.haemin.imagemathtutor.Data.Test;
import com.haemin.imagemathtutor.R;
import com.haemin.imagemathtutor.TestAddMVP.TestAddActivity;
import com.haemin.imagemathtutor.View.UI.ListPickerDialog;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment implements TestFragmentContract.TestFragmentView {

    @BindView(R.id.text_lecture_name)
    TextView textLectureName;
    @BindView(R.id.text_submit_more)
    TextView textSubmitMore;
    @BindView(R.id.recycler_test)
    RecyclerView recyclerTest;
    @BindView(R.id.btn_add_test)
    Button btnAddTest;

    TestAdapter testAdapter;
    ArrayList<Test> tests;
    TestFragmentContract.TestFragmentPresenter presenter;

    String lectureSeq;

    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_test, container, false);
        {
            ButterKnife.bind(this, v);
            tests = new ArrayList<>();
            testAdapter = new TestAdapter(getContext(), tests);
            presenter = new TestFragmentPresenter(this);
            recyclerTest.setAdapter(testAdapter);
            recyclerTest.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            textLectureName.setText("수업을 선택해주세요.");
        }
        textLectureName.setOnClickListener(v1 -> {
            presenter.requestLectures();
        });
        {
            btnAddTest.setOnClickListener(v1 -> {
                startActivity(new Intent(getContext(), TestAddActivity.class));
            });
        }

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(lectureSeq != null && !lectureSeq.equals("")){
            presenter.requestTestData(lectureSeq);
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateLectureName(Lecture lecture) {
        textLectureName.setText(lecture.getName());
    }

    @Override
    public void updateRecycler(ArrayList<Test> tests) {
        this.tests.clear();
        this.tests.addAll(tests);
        testAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDialog(ArrayList<Lecture> lectures) {
        ListPickerDialog<Lecture> lectureListPickerDialog = new ListPickerDialog<>(lectures, "수업을 선택해주세요.");
        lectureListPickerDialog.setOnItemClickListener(data -> {
            presenter.requestTestData(data.getLectureSeq());
            lectureSeq = data.getLectureSeq();
            updateLectureName(data);
            lectureListPickerDialog.dismiss();
        });
        lectureListPickerDialog.show(getFragmentManager(), "TestFragmentListPick");
    }
}
