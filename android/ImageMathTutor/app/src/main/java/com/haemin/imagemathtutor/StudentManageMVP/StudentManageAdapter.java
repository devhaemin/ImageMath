package com.haemin.imagemathtutor.StudentManageMVP;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.Data.User;
import com.haemin.imagemathtutor.R;
import com.haemin.imagemathtutor.StudentInfoMVP.StudentInfoActivity;

import java.util.ArrayList;

public class StudentManageAdapter extends RecyclerView.Adapter<StudentManageAdapter.StudentViewHolder> {

    Context context;
    ArrayList<User> students;

    public StudentManageAdapter(Context context, ArrayList<User> students) {
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_student_manage, parent, false);
        return new StudentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {

        User user = students.get(position);
        holder.btnStudentInfo.setOnClickListener(v -> {
            StudentInfoActivity.start(context, user.getUserSeq() + "");
        });
        holder.toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            user.setChecked(isChecked);
        });
        holder.toggleButton.setChecked(user.isChecked());
        holder.textStudentName.setText(user.getName());
        holder.textStudentCode.setText(user.getStudentCode());
        holder.textStudentSchool.setText(user.getSchoolName() + "");

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_student_info)
        Button btnStudentInfo;
        @BindView(R.id.text_student_code)
        TextView textStudentCode;
        @BindView(R.id.text_student_school)
        TextView textStudentSchool;
        @BindView(R.id.text_student_name)
        TextView textStudentName;
        @BindView(R.id.toggle_check_student)
        CheckBox toggleButton;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
