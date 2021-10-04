package com.haemin.imagemathtutor.TestInfoMVP;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.Data.StudentTest;
import com.haemin.imagemathtutor.R;

import java.util.ArrayList;

public class TestInfoAdapter extends  RecyclerView.Adapter<TestInfoAdapter.TestInfoHolder> {

    Context context;
    ArrayList<StudentTest> studentTests;

    public TestInfoAdapter(Context context, ArrayList<StudentTest> studentTests) {
        this.context = context;
        this.studentTests = studentTests;
    }

    @NonNull
    @Override
    public TestInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.recycler_student_test,parent,false);
        return new TestInfoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TestInfoHolder holder, int position) {
        StudentTest studentTest = studentTests.get(position);
        holder.textAuthStatus.setText("제출");
        holder.textStudentName.setText(studentTest.getUserName());
        holder.textStudentScore.setText(studentTest.getScore()+"점");
        holder.textRank.setText(studentTest.getRank()+"등");
    }

    @Override
    public int getItemCount() {
        return studentTests.size();
    }

    class TestInfoHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text_auth_status)
        TextView textAuthStatus;
        @BindView(R.id.img_auth_status)
        ImageView imageAuthStatus;
        @BindView(R.id.text_student_name)
        TextView textStudentName;
        @BindView(R.id.text_student_score)
        TextView textStudentScore;
        @BindView(R.id.text_rank)
        TextView textRank;

        public TestInfoHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
