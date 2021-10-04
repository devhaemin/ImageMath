package com.haemin.imagemathtutor.StudentInfoMVP;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.AssignmentInfoMVP.AssignmentInfoActivity;
import com.haemin.imagemathtutor.Data.StudentAssignment;
import com.haemin.imagemathtutor.R;

import java.util.ArrayList;

public class SubmitInfoAdapter extends RecyclerView.Adapter<SubmitInfoAdapter.SubmitViewHolder> {
    Context context;
    ArrayList<StudentAssignment> assignments;

    public SubmitInfoAdapter(Context context, ArrayList<StudentAssignment> assignments) {
        this.context = context;
        this.assignments = assignments;
    }

    @NonNull
    @Override
    public SubmitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_assignment_submit,parent,false);

        return new SubmitViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubmitViewHolder holder, int position) {
        StudentAssignment assignment = assignments.get(position);

        holder.itemView.setOnClickListener(v -> {
            AssignmentInfoActivity.start(context,assignment);
        });
        holder.textAssignmentName.setText(assignment.getTitle());
        holder.textEndDay.setText(DateUtils.getRelativeTimeSpanString(assignment.getEndTime()));
        holder.textUploadDay.setText(DateUtils.getRelativeTimeSpanString(assignment.getUploadTime()));
        holder.textLectureName.setText(assignment.getLectureName());

    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    class SubmitViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_lecture_name)
        TextView textLectureName;
        @BindView(R.id.text_assignment_name)
        TextView textAssignmentName;
        @BindView(R.id.text_upload_day)
        TextView textUploadDay;
        @BindView(R.id.text_end_day)
        TextView textEndDay;

        public SubmitViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
