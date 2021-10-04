package com.haemin.imagemathtutor.AssignmentFragmentMVP;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.AssignmentInfoMVP.AssignmentInfoActivity;
import com.haemin.imagemathtutor.Data.Assignment;
import com.haemin.imagemathtutor.R;

import java.util.ArrayList;

public class AssignmentInsideAdapter extends RecyclerView.Adapter<AssignmentInsideAdapter.InsideViewHolder> {

    Context context;
    ArrayList<Assignment> assignments;

    public AssignmentInsideAdapter(Context context, ArrayList<Assignment> assignments) {
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
        Assignment assignment = assignments.get(position);
        holder.textAssignmentName.setText(assignment.getTitle());
        holder.textLectureName.setText(assignment.getLectureName());

        holder.imageStatus.setImageDrawable(context.getDrawable(R.drawable.img_showsubmit));

        if (assignment.getEndTime() > System.currentTimeMillis()) {
            holder.imageStatus.setImageDrawable(context.getDrawable(R.drawable.img_showcomplete));
        }
        holder.itemView.setOnClickListener(v -> {
            AssignmentInfoActivity.start(context, assignment);
        });
        final SpannableStringBuilder sp = new SpannableStringBuilder(assignment.getSubmitNum() + "/" + assignment.getStudentNum());
//sp.setSpan(new ForegroundColorSpan(Color.rgb(255, 255, 255)), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.etoos_color)), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.textAssignmentSubmitNum.setText(sp);
        holder.textStudentNum.setText(assignment.getStudentNum() + "명");
        holder.textUploadDay.setText(DateUtils.getRelativeTimeSpanString(assignment.getPostTime()));
        holder.textEndDay.setText(DateUtils.getRelativeTimeSpanString(assignment.getEndTime()));
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
        @BindView(R.id.text_assignment_submit_num)
        TextView textAssignmentSubmitNum;
        @BindView(R.id.text_student)
        TextView textStudentNum;
        @BindView(R.id.text_upload_day)
        TextView textUploadDay;
        @BindView(R.id.text_end_day)
        TextView textEndDay;
        @BindView(R.id.icon_status)
        ImageView imageStatus;

        public InsideViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
