package com.haemin.imagemathtutor.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.User;
import com.haemin.imagemathtutor.GlobalApplication;
import com.haemin.imagemathtutor.R;
import com.haemin.imagemathtutor.View.RegisterProcess.RegisterTermsActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_login)
    Button btnLogin;

    @BindView(R.id.edit_pw)
    EditText editPW;
    @BindView(R.id.edit_email)
    EditText editEmail;
    @BindView(R.id.text_register)
    TextView textRegister;
    @BindView(R.id.text_find_pw)
    TextView textFindPW;
    @BindView(R.id.toggle_save_email)
    CheckBox toggleSaveEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        bindListener();
    }
    void bindListener(){
        textRegister.setOnClickListener(v-> {
                    startActivity(new Intent(LoginActivity.this, RegisterTermsActivity.class));
                }
        );
        btnLogin.setOnClickListener(v->{
            if(getEmail().equals("")){
                showToast(AppString.ERROR_EMAIL_EMPTY);
            }
            else if(getPW().equals("")){
                showToast(AppString.ERROR_PW_EMPTY);
            }
            else{
                GlobalApplication.getAPIService().loginWithEmail(getEmail(),getPW())
                        .enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if(response.code() == 200&& response.body() != null){
                                    User user = response.body();
                                    GlobalApplication.setAccessToken(user.getAccessToken());
                                    GoToMain(user.getName());
                                }else{
                                    showToast("아이디 혹은 비밀번호가 일치하지 않습니다.");
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                showToast(AppString.ERROR_NETWORK_MESSAGE);
                                Log.e("LoginActivity",t.getMessage(),t);
                            }
                        });
            }
        });
    }

    private void GoToMain(String name) {
        showToast(AppString.SUCCESS_LOGIN_MESSAGE(name));
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    private String getEmail(){
        return editEmail.getText().toString();
    }
    private String getPW(){
        return editPW.getText().toString();
    }
    private void showToast(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

}
