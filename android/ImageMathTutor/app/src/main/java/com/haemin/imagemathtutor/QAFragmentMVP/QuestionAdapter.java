package com.haemin.imagemathtutor.QAFragmentMVP;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.Data.Question;
import com.haemin.imagemathtutor.QuestionInfoMVP.QuestionInfoActivity;
import com.haemin.imagemathtutor.R;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionHolder> {

    Context context;
    ArrayList<Question> questions;

    public QuestionAdapter(Context context, ArrayList<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_qa, parent, false);
        return new QuestionHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
        Question question = questions.get(position);

        holder.textTitle.setText(question.getTitle());
        holder.textContents.setText(question.getContents());
        holder.textUploaderName.setText(question.getName()+" 작성");
        holder.textUploadTime.setText(DateUtils.getRelativeTimeSpanString(question.getPostTime())+" 작성됨.");
        if(question.getUpdateTime() != 0) {
            holder.textUpdateTime.setText(DateUtils.getRelativeTimeSpanString(question.getUpdateTime()) + " 답변완료");
            holder.qaStatus.setBackground(context.getResources().getDrawable(R.color.etoos_color,null));
        }else{
            holder.textUpdateTime.setText("아직 답변 대기중인 질문입니다.");
            holder.qaStatus.setBackground(context.getResources().getDrawable(android.R.color.holo_red_dark, null));
        }
        holder.itemView.setOnClickListener(v -> {
            QuestionInfoActivity.start(context, question.getQuestionSeq()+ "");
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class QuestionHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_update_time)
        TextView textUpdateTime;
        @BindView(R.id.text_title)
        TextView textTitle;
        @BindView(R.id.text_contents)
        TextView textContents;
        @BindView(R.id.qa_status)
        View qaStatus;
        @BindView(R.id.text_uploader_name)
        TextView textUploaderName;
        @BindView(R.id.text_upload_time)
        TextView textUploadTime;


        public QuestionHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
