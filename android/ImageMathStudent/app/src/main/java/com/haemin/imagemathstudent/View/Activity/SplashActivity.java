package com.haemin.imagemathstudent.View.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.common.GoogleApiAvailability;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.haemin.imagemathstudent.BuildConfig;
import com.haemin.imagemathstudent.Data.User;
import com.haemin.imagemathstudent.R;
import com.haemin.imagemathstudent.SingleTon.AppString;
import com.haemin.imagemathstudent.SingleTon.GlobalApplication;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TedPermission.with(this)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        hide();
                    }
                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        finish();
                    }
                })
                .setRationaleMessage("앱을 이용하기 위해서는 메모리 사용 권한이 필요합니다.")
                .setDeniedMessage("권한 설정에 동의하셔야 앱을 이용할 수 있습니다.\n하지만 [설정] > [권한] 에서 권한을 허용할 수 있어요.")
                .setPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE})
                .check();

        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void autoLoginProcess() {
        if (GlobalApplication.getAccessToken() != null) {
            GlobalApplication.getAPIService().loginWithToken(GlobalApplication.getAccessToken())
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.code() == 200 && response.body() != null) {
                                GoToMain(response.body());
                                Log.e("SplashActivity",response.body().getAccessToken()+"");
                            } else {
                                Toast.makeText(SplashActivity.this, AppString.ERROR_TOKEN_EXPIRED, Toast.LENGTH_SHORT).show();
                                GoToLogin();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(SplashActivity.this, AppString.ERROR_SERVER_INSPECT, Toast.LENGTH_SHORT).show();
                            Log.e("SplashActivity",t.getMessage(),t);
                            finish();
                        }
                    });

        } else {
            GoToLogin();
        }
    }
//    private void checkNewVersion(){
//        new getMarketVersion().execute();
//    }

    private void GoToMain(User user) {
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(i);
        Toast.makeText(this, AppString.SUCCESS_LOGIN_MESSAGE(user.getName()), Toast.LENGTH_LONG).show();
        finish();
    }

    private void GoToLogin() {
        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        autoLoginProcess();
        // Schedule a runnable to remove the status and navigation bar after a delay
//        checkNewVersion();
    }



}
