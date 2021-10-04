package com.haemin.imagemathtutor.QuestionInfoMVP;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.Answer;
import com.haemin.imagemathtutor.Data.ServerFile;
import com.haemin.imagemathtutor.GlobalApplication;
import com.haemin.imagemathtutor.R;
import com.haemin.imagemathtutor.View.UI.FileButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerHolder> {

    Context context;
    ArrayList<Answer> answers;

    public AnswerAdapter(Context context, ArrayList<Answer> answers) {
        this.context = context;
        this.answers = answers;
    }

    @NonNull
    @Override
    public AnswerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_answer, parent, false);
        return new AnswerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerHolder holder, int position) {
        Answer answer = answers.get(position);
        holder.textUpdateTime.setText(DateUtils.getRelativeTimeSpanString(answer.getUpdateTime()) + " 답변완료");
        holder.textTitle.setText(answer.getTitle());
        holder.textContents.setText(answer.getContents());
        holder.groupFile.removeAllViews();
        GlobalApplication.getAPIService().getAnswerFileList(GlobalApplication.getAccessToken(), answer.getAnswerSeq()).enqueue(new Callback<ArrayList<ServerFile>>() {
            @Override
            public void onResponse(Call<ArrayList<ServerFile>> call, Response<ArrayList<ServerFile>> response) {
                if(response.code() == 200 && response.body() != null){

                    for (ServerFile file : response.body()) {

                        FileButton button = new FileButton(context);
                        button.setFile(file);
                        button.setDeleteAble(false);
                        button.setClickable(true);
                        holder.groupFile.addView(button);
                    }
                }else{
                    Log.e("AnswerAdapter", response.message());
                    Toast.makeText(context, "파일 목록을 정상적으로 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ServerFile>> call, Throwable t) {
                Log.e("AnswerAdapter",t.getMessage(),t);
                Toast.makeText(context, AppString.ERROR_NETWORK_MESSAGE, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    class AnswerHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.text_update_time)
        TextView textUpdateTime;
        @BindView(R.id.text_title)
        TextView textTitle;
        @BindView(R.id.text_contents)
        TextView textContents;
        @BindView(R.id.group_file)
        LinearLayout groupFile;

        public AnswerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
