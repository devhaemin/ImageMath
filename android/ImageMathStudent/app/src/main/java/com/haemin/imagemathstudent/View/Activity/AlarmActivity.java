package com.haemin.imagemathstudent.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathstudent.Adapter.AlarmRecyclerAdapter;
import com.haemin.imagemathstudent.Data.Alarm;
import com.haemin.imagemathstudent.R;
import com.haemin.imagemathstudent.SingleTon.AppString;
import com.haemin.imagemathstudent.SingleTon.GlobalApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class AlarmActivity extends AppCompatActivity {

    ArrayList<Alarm> alarms;
    AlarmRecyclerAdapter recyclerAdapter;

    @BindView(R.id.recycler_alarm)
    RecyclerView recyclerView;
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.text_no_alarm_data)
    TextView textNoAlarm;
    public static void start(Context context) {
        Intent starter = new Intent(context, AlarmActivity.class);
     //   starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
        btnBack.setOnClickListener(v -> finish());
        alarms = new ArrayList<>();

        recyclerAdapter = new AlarmRecyclerAdapter(this,alarms);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        GlobalApplication.getAPIService().getAlarmList(GlobalApplication.getAccessToken())
                .enqueue(new Callback<ArrayList<Alarm>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Alarm>> call, Response<ArrayList<Alarm>> response) {
                        if(response.code() == 200 && response.body() != null){
                            if(response.body().size() != 0){
                                textNoAlarm.setVisibility(View.GONE);
                            }else{
                                textNoAlarm.setVisibility(View.VISIBLE);
                            }
                            alarms.clear();
                            alarms.addAll(response.body());
                            recyclerAdapter.notifyDataSetChanged();
                        }else{
                            showToast(AppString.ERROR_NETWORK_MESSAGE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Alarm>> call, Throwable t) {
                        Log.e("AlarmActivity",t.getMessage(),t);
                    }
                });
    }

    private void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
