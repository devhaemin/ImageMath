package com.haemin.imagemathstudent.TestFragmentMVP;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.haemin.imagemathstudent.Data.Lecture;
import com.haemin.imagemathstudent.Data.StudentTest;
import com.haemin.imagemathstudent.R;
import com.haemin.imagemathstudent.View.UI.ListPickerDialog;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment implements TestFragmentContract.TestFragmentView {


    @BindView(R.id.text_lecture_name)
    TextView textLectureName;
    @BindView(R.id.test_chart)
    LineChart testChart;
    @BindView(R.id.dummy_group_average)
    ConstraintLayout groupAverage;
    @BindView(R.id.text_recent_my_rank)
    TextView textRecentMyRank;
    @BindView(R.id.text_average_score)
    TextView textAverageScore;
    @BindView(R.id.text_recent_rank)
    TextView textRecentScore;
    @BindView(R.id.text_no_test_data)
    TextView textNoTestData;

    @BindView(R.id.recycler_test)
    RecyclerView recyclerView;

    TestFragmentContract.TestFragmentPresenter presenter;
    TestAdapter testAdapter;
    ArrayList<StudentTest> studentTests;
    String lectureSeq = null;


    public TestFragment() {
        // Required empty public constructor
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoData() {
        testChart.setVisibility(View.GONE);
        groupAverage.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        textNoTestData.setVisibility(View.VISIBLE);
    }

    @Override
    public void showHasData() {
        testChart.setVisibility(View.VISIBLE);
        groupAverage.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        textNoTestData.setVisibility(View.GONE);
    }

    @Override
    public void showDialog(ArrayList<Lecture> lectures) {
        ListPickerDialog<Lecture> lectureListPickerDialog = new ListPickerDialog<>(lectures, "수업을 선택해주세요.");
        lectureListPickerDialog.setOnItemClickListener(data -> {
            lectureSeq = data.getLectureSeq();
            presenter.updateData(data.getLectureSeq()+"");
            updateLectureName(data.getName());
            lectureListPickerDialog.dismiss();
        });
        lectureListPickerDialog.show(getFragmentManager(), "TAG");

    }

    @Override
    public void updateChart(ArrayList<StudentTest> studentTests) {

        this.studentTests.clear();
        this.studentTests.addAll(studentTests);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        ArrayList<Entry> scoreEntries = new ArrayList<>();
        for (int i = 0; i < studentTests.size(); i++) {
            Entry entry = new Entry(i, studentTests.get(i).getScore());
            scoreEntries.add(entry);
        }

        ArrayList<Entry> averageEntries = new ArrayList<>();
        for (int i = 0; i < studentTests.size(); i++) {
            Entry entry = new Entry(i, studentTests.get(i).getAverageScore());
            averageEntries.add(entry);
        }


        LineDataSet d = new LineDataSet(scoreEntries, "내 점수");
        d.setLineWidth(2.5f);
        d.setCircleRadius(4f);
        d.setColor(R.color.etoos_color);
        d.setCircleColor(R.color.etoos_color);

        LineDataSet d2 = new LineDataSet(averageEntries, "평균 점수");
        d2.setLineWidth(2.5f);
        d2.setCircleRadius(4f);
        d2.setColor(R.color.exo_gray);
        d2.setCircleColor(R.color.exo_gray);

        dataSets.add(d);
        dataSets.add(d2);

        //((LineDataSet) dataSets.get(0)).enableDashedLine(10, 10, 0);

        ((LineDataSet) dataSets.get(0)).setColors(Color.rgb(50,148,141));
        ((LineDataSet) dataSets.get(0)).setCircleColors(Color.rgb(50,148,141));

        LineData data = new LineData(dataSets);
        testChart.setData(data);
        testChart.getXAxis().setEnabled(false);
        testChart.getAxisRight().setEnabled(false);
        testChart.getAxisLeft().setAxisMinimum(0);
        testChart.getAxisLeft().setAxisMaximum(105);
        Description description = new Description();
        description.setText("테스트 지표");
        testChart.setDescription(description);
        testChart.invalidate();

    }

    @Override
    public void updateRecycler(ArrayList<StudentTest> studentTests) {
        this.studentTests.clear();
        this.studentTests.addAll(studentTests);
        Collections.reverse(this.studentTests);
        testAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateAverages(String recentRank, String averageScore, String recentScore) {
        textRecentMyRank.setText(recentRank + "등");
        textAverageScore.setText(averageScore + "점");
        textRecentScore.setText(recentScore + "점");
    }

    @Override
    public void updateLectureName(String lectureName) {
        textLectureName.setText(lectureName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        studentTests = new ArrayList<>();
        View v = inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this, v);
        presenter = new TestFragmentPresenter(this);
        testAdapter = new TestAdapter(getContext(), studentTests);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(testAdapter);

        textLectureName.setOnClickListener(v1 -> {
            presenter.requestLecturePick();
        });
        showNoData();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(lectureSeq != null && !lectureSeq.equals("")) presenter.updateData(lectureSeq);
    }

}
