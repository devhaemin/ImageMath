package com.haemin.imagemathstudent.Adapter;

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
import com.haemin.imagemathstudent.AssignmentInfoMVP.AssignmentInfoActivity;
import com.haemin.imagemathstudent.Data.StudentAssignment;
import com.haemin.imagemathstudent.R;

import java.util.ArrayList;

public class AssignmentInsideAdapter extends RecyclerView.Adapter<AssignmentInsideAdapter.InsideViewHolder> {

    Context context;
    ArrayList<StudentAssignment> assignments;

    public AssignmentInsideAdapter(Context context, ArrayList<StudentAssignment> assignments) {
        this.context = context;
        this.assignments = assignments;
    }

    @NonNull
    @Override
    public InsideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_assignment_inside, parent, false);

        return new InsideViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InsideViewHolder holder, int position) {
        StudentAssignment assignment = assignments.get(position);
        holder.textAssignmentName.setText(assignment.getTitle());
        holder.textLectureName.setText(assignment.getLectureName());

        switch (assignment.getSubmitState()) {
            case 0:
                holder.imageStatus.setImageDrawable(context.getDrawable(R.drawable.icon_unsubmit));
                if (assignment.getEndTime() < System.currentTimeMillis()) {
                    holder.imageStatus.setImageDrawable(context.getDrawable(R.drawable.icon_neglect));
                }
                break;
            case 1:
                holder.imageStatus.setImageDrawable(context.getDrawable(R.drawable.img_showcomplete));
                break;
            case 2:
                holder.imageStatus.setImageDrawable(context.getDrawable(R.drawable.img_showsubmit));
                break;
            case 3:
                holder.imageStatus.setImageDrawable(context.getDrawable(R.drawable.img_showsubmit));
                break;
            case 4:
                holder.imageStatus.setImageDrawable(context.getDrawable(R.drawable.img_showsubmit));
                break;
            case 5:
                holder.imageStatus.setImageDrawable(context.getDrawable(R.drawable.icon_neglect));
                if (assignment.getEndTime() < System.currentTimeMillis()) {
                    holder.imageStatus.setImageDrawable(context.getDrawable(R.drawable.icon_neglect));
                }
                break;
        }

        holder.itemView.setOnClickListener(v -> {
            AssignmentInfoActivity.start(context, assignment.getAssignmentSeq() + "");
        });
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    class InsideViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_lecture_name)
        TextView textLectureName;
        @BindView(R.id.text_assignment_name)
        TextView textAssignmentName;
        @BindView(R.id.icon_status)
        ImageView imageStatus;

        public InsideViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
