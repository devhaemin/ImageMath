package com.haemin.imagemathstudent.TestFragmentMVP;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathstudent.Data.StudentTest;
import com.haemin.imagemathstudent.R;
import com.haemin.imagemathstudent.TestInfoMVP.TestInfoActivity;

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {

    Context context;
    ArrayList<StudentTest> tests;

    public TestAdapter(Context context, ArrayList<StudentTest> tests) {
        this.context = context;
        this.tests = tests;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.recycler_test, parent, false);


        return new TestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        StudentTest test = tests.get(position);
        holder.textRank.setText(test.getRank() + "등");
        holder.textRankSmall.setText(test.getRank() + "등");
        holder.textScore.setText(test.getScore() + "점");
        holder.textTestName.setText(test.getTitle());
        holder.itemView.setOnClickListener(v -> {
            TestInfoActivity.start(context, test);
        });
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_rank)
        TextView textRank;
        @BindView(R.id.text_test_name)
        TextView textTestName;
        @BindView(R.id.text_score)
        TextView textScore;
        @BindView(R.id.text_rank_small)
        TextView textRankSmall;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
