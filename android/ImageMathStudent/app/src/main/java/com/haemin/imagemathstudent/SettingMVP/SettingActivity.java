package com.haemin.imagemathstudent.SettingMVP;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathstudent.R;
import com.haemin.imagemathstudent.SingleTon.GlobalApplication;
import com.haemin.imagemathstudent.View.Activity.SplashActivity;

public class SettingActivity extends AppCompatActivity {


    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.btn_terms)
    Button btnTerms;
    @BindView(R.id.btn_set_notification)
    Button setNotification;
    @BindView(R.id.btn_back)
    ImageButton btnBack;

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingActivity.class);
        //starter.putExtra();
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);

        btnLogout.setOnClickListener(v -> {
            GlobalApplication.removeAccessToken();
            Toast.makeText(this,"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SplashActivity.class);
            this.startActivity(intent);
            this.finishAffinity();
        });
        btnTerms.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://imagemath.kr"));
            Toast.makeText(this,"이용약관으로 이동합니다.",Toast.LENGTH_SHORT).show();
            startActivity(i);
        });
        btnBack.setOnClickListener(v -> finish());
    }
}
