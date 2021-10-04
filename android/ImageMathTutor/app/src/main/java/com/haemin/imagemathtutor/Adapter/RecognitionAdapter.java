package com.haemin.imagemathtutor.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.Data.User;
import com.haemin.imagemathtutor.GlobalApplication;
import com.haemin.imagemathtutor.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class RecognitionAdapter extends RecyclerView.Adapter<RecognitionAdapter.RecognitionViewHolder> {

    Context context;
    ArrayList<User> users;

    public RecognitionAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;

    }

    @NonNull
    @Override
    public RecognitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_recognition,parent,false);
        return new RecognitionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecognitionViewHolder holder, int position) {
        User user = users.get(position);
        holder.btnStudentInfo.setOnClickListener(v -> {

        });
        holder.toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            user.setChecked(isChecked);
        });
        holder.toggleButton.setChecked(user.isChecked());
        GlobalApplication.getAPIService().getUserInfo(GlobalApplication.getAccessToken(),user.getUserSeq())
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.code() == 200 && response.body() != null){
                            User user = response.body();
                            holder.textStudentName.setText(user.getName());
                            holder.textStudentCode.setText(user.getStudentCode());
                            holder.textStudentSchool.setText(user.getSchoolName()+"");
                        }else{
                            Toast.makeText(context,"학생정보를 불러오는데에 실패했습니다.",Toast.LENGTH_SHORT).show();
                            Log.e("RecognitionAdapter",response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("RecognitionAdapter",t.getMessage(),t);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class RecognitionViewHolder extends RecyclerView.ViewHolder {

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

        public RecognitionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
