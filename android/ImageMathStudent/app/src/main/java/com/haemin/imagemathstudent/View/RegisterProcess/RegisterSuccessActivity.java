package com.haemin.imagemathstudent.View.RegisterProcess;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathstudent.R;

public class RegisterSuccessActivity extends AppCompatActivity {

    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.text_personal_num)
    TextView textPersonalNum;

    String userCode;

    public static void start(Context context, String userCode) {
        Intent starter = new Intent(context, RegisterSuccessActivity.class);
        starter.putExtra("userCode",userCode);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);
        ButterKnife.bind(this);

        Intent fromOutside = getIntent();
        userCode = fromOutside.getStringExtra("userCode");

        btnBack.setOnClickListener(v -> finish());
        setNumString(userCode);

    }

    void setNumString(String num){
        SpannableString spannableString = new SpannableString(num);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.etoos_color)),0,spannableString.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textPersonalNum.append("개인 번호는 ");
        textPersonalNum.append(spannableString);
        textPersonalNum.append("\n수업 인증 후 수업정보가 업데이트 됩니다.\n인증기간은 2~3일 정도 소요됩니다.");
    }
}
