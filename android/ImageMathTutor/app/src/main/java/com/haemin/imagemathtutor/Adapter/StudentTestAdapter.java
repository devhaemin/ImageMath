package com.haemin.imagemathtutor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.Data.StudentTest;
import com.haemin.imagemathtutor.R;
import com.haemin.imagemathtutor.StudentInfoMVP.StudentInfoActivity;

import java.util.ArrayList;

public class StudentTestAdapter extends RecyclerView.Adapter<StudentTestAdapter.TestViewHolder> {

    Context context;
    ArrayList<StudentTest> tests;

    public StudentTestAdapter(Context context, ArrayList<StudentTest> tests) {
        this.context = context;
        this.tests = tests;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_student_admin_test,parent,false);
        return new TestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        StudentTest test = tests.get(position);
        holder.textRank.setText(test.getRank()+"");
        holder.textStudentName.setText(test.getUserName());
        holder.textStudentScore.setText(test.getScore()+"점");
        holder.btnStudentInfo.setOnClickListener(v -> {
            StudentInfoActivity.start(context,test.getUserSeq()+"");
        });
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.btn_student_info)
        Button btnStudentInfo;
        @BindView(R.id.text_rank)
        TextView textRank;
        @BindView(R.id.text_student_name)
        TextView textStudentName;
        @BindView(R.id.text_student_score)
        TextView textStudentScore;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
