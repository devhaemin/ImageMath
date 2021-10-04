package com.haemin.imagemathtutor.TestFragmentMVP;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.Data.Test;
import com.haemin.imagemathtutor.R;
import com.haemin.imagemathtutor.TestInfoMVP.TestInfoActivity;

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {
    Context context;
    ArrayList<Test> tests;

    public TestAdapter(Context context, ArrayList<Test> tests) {
        this.context = context;
        this.tests = tests;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_test,parent,false);
        return new TestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        Test test = tests.get(position);
        if(test == null) return;
        holder.textTestName.setText(test.getTitle());
        holder.textLectureStudentNum.setText(test.getStudentNum()+"명");
        holder.textAverage.setText(test.getAverageScore()+"점");
        holder.textSubmitNum.setText(test.getStudentNum()+" / "+test.getStudentNum());
        holder.itemView.setOnClickListener(v -> {
            TestInfoActivity.start(context,test);
        });
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.text_test_name)
        TextView textTestName;
        @BindView(R.id.text_test_submit_num)
        TextView textSubmitNum;
        @BindView(R.id.text_test_average)
        TextView textAverage;
        @BindView(R.id.text_lecture_student_num)
        TextView textLectureStudentNum;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
