package com.haemin.imagemathtutor.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.Adapter.RecognitionAdapter;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.Lecture;
import com.haemin.imagemathtutor.Data.User;
import com.haemin.imagemathtutor.GlobalApplication;
import com.haemin.imagemathtutor.R;
import com.haemin.imagemathtutor.Utils.ConfirmStarter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class RecognitionActivity extends AppCompatActivity {


    String lectureSeq;
    String lectureName;
    RecognitionAdapter adapter;
    ArrayList<User> users;

    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.text_lecture_name)
    TextView textLectureName;
    @BindView(R.id.toggle_check_all_student)
    CheckBox toggleCheckAll;
    @BindView(R.id.btn_delete)
    ImageButton btnDelete;
    @BindView(R.id.recycler_recognition)
    RecyclerView recyclerRecognition;
    @BindView(R.id.btn_recognition)
    Button btnRecognition;

    public static void start(Context context, Lecture lecture) {
        Intent starter = new Intent(context, RecognitionActivity.class);
        starter.putExtra("lectureSeq",lecture.getLectureSeq());
        starter.putExtra("lectureName",lecture.getName());
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition);
        ButterKnife.bind(this);
        users = new ArrayList<>();
        adapter = new RecognitionAdapter(this, users);

        Intent fromOutside = getIntent();
        ConfirmStarter.checkIntent(this, fromOutside);
        lectureSeq = fromOutside.getStringExtra("lectureSeq");
        lectureName = fromOutside.getStringExtra("lectureName");

        btnBack.setOnClickListener(v -> {
            finish();
        });
        textLectureName.setText(lectureName);
        btnDelete.setOnClickListener(v -> {
            deleteRecognition();
        });
        btnRecognition.setOnClickListener(v -> {
            recognizeSelects();
        });
        toggleCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    for(User user : users)
                        user.setChecked(isChecked);
                    adapter.notifyDataSetChanged();
            }
        });
        recyclerRecognition.setAdapter(adapter);
        recyclerRecognition.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        refreshView();
    }

    private void recognizeSelects() {
        for(User user : users){

            if(user.isChecked())
            GlobalApplication.getAPIService().recognizeStudent(GlobalApplication.getAccessToken(),lectureSeq,user.getUserSeq(),user.isChecked())
                    .enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.code() == 200){
                                showToast("수업 승인이 성공적으로 완료되었습니다.");
                                refreshView();
                            }else{
                                showToast("수업 승인에 실패하였습니다.");
                                Log.e("RecognitionActivity",response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            showToast(AppString.ERROR_NETWORK_MESSAGE);
                            Log.e("RecognitionActivity",t.getMessage(),t);
                        }
                    });

        }
    }

    private void deleteRecognition() {
        for(User user : users){

            if(user.isChecked())
                GlobalApplication.getAPIService().recognizeStudent(GlobalApplication.getAccessToken(),lectureSeq,user.getUserSeq(),!user.isChecked())
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.code() == 200){
                                    showToast("수업 승인이 성공적으로 완료되었습니다.");
                                    refreshView();
                                }else{
                                    showToast("수업 승인에 실패하였습니다.");
                                    Log.e("RecognitionActivity",response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                showToast(AppString.ERROR_NETWORK_MESSAGE);
                                Log.e("RecognitionActivity",t.getMessage(),t);
                            }
                        });

        }
    }

    private void refreshView() {
        GlobalApplication.getAPIService().getRequestUserList(GlobalApplication.getAccessToken(),lectureSeq,0)
                .enqueue(new Callback<ArrayList<User>>() {
                    @Override
                    public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                        if(response.code() == 200 && response.body() != null){
                            users.clear();
                            users.addAll(response.body());
                            adapter.notifyDataSetChanged();
                        }else{
                            Log.e("RecognitionActivity",response.message());
                            showToast(AppString.ERROR_LOAD_USER_LIST);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                        Log.e("RecognitionActivity",t.getMessage(),t);
                        showToast(AppString.ERROR_NETWORK_MESSAGE);
                    }
                });
    }
    private void showToast(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

}
