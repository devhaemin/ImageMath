package com.haemin.imagemathtutor.QuestionInfoMVP;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.AnswerAddMVP.AnswerAddActivity;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.Answer;
import com.haemin.imagemathtutor.Data.Question;
import com.haemin.imagemathtutor.Data.ServerFile;
import com.haemin.imagemathtutor.GlobalApplication;
import com.haemin.imagemathtutor.R;
import com.haemin.imagemathtutor.View.UI.FileButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class QuestionInfoActivity extends AppCompatActivity implements QuestionContract.QuestionView {

    String questionSeq;
    QuestionContract.QuestionPresenter presenter;

    @BindView(R.id.text_update_time)
    TextView textUpdateTime;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.text_contents)
    TextView textContents;
    @BindView(R.id.group_file)
    LinearLayout groupFile;
    @BindView(R.id.recycler_answer)
    RecyclerView recyclerAnswer;
    @BindView(R.id.btn_add_answer)
    Button btnAddAnswer;
    @BindView(R.id.btn_back)
    ImageButton btnBack;

    AnswerAdapter answerAdapter;
    ArrayList<Answer> answers;

    public static void start(Context context, String questionSeq) {
        Intent starter = new Intent(context, QuestionInfoActivity.class);
        starter.putExtra("questionSeq",questionSeq);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_info);

        ButterKnife.bind(this);

        Intent fromOutside = getIntent();
        questionSeq = fromOutside.getStringExtra("questionSeq");

        presenter = new QuestionInfoPresenter(this);
        answers = new ArrayList<>();

        answerAdapter = new AnswerAdapter(this, answers);

        recyclerAnswer.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerAnswer.setAdapter(answerAdapter);

        presenter.requestData(questionSeq);

        btnAddAnswer.setOnClickListener(v -> {
            AnswerAddActivity.start(this, questionSeq);
        });
        btnBack.setOnClickListener(v ->{
            finish();
        });


    }

    @Override
    public void showQuestionData(Question question) {
        if(question.getUpdateTime() != 0)
        textUpdateTime.setText(DateUtils.getRelativeTimeSpanString(question.getPostTime())+" 게시됨");
        else{
            textUpdateTime.setText("아직 답변 대기중인 질문입니다.");
        }
        textTitle.setText(question.getTitle());
        textContents.setText(question.getContents());
        groupFile.removeAllViews();
        GlobalApplication.getAPIService().getQuestionFileList(GlobalApplication.getAccessToken(), questionSeq).enqueue(new Callback<ArrayList<ServerFile>>() {
            @Override
            public void onResponse(Call<ArrayList<ServerFile>> call, Response<ArrayList<ServerFile>> response) {
                if(response.code() == 200 && response.body() != null){
                    for (ServerFile file : response.body()) {

                        FileButton button = new FileButton(QuestionInfoActivity.this);
                        button.setFile(file);
                        button.setDeleteAble(false);
                        button.setClickable(true);
                        groupFile.addView(button);
                    }
                }else{
                    Log.e("QuestionInfoActivity",response.message());
                    showToast("질문 파일을 불러오지 못했습니다.");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ServerFile>> call, Throwable t) {
                Log.e("QuestionInfoActivity",t.getMessage(),t);
                showToast(AppString.ERROR_NETWORK_MESSAGE);
            }
        });
    }

    @Override
    public void showAnswerData(ArrayList<Answer> answers) {
        this.answers.clear();
        this.answers.addAll(answers);

        answerAdapter.notifyDataSetChanged();

    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}
