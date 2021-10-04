package com.haemin.imagemathstudent.SingleTon;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Debug;
import com.bumptech.glide.Glide;
import com.haemin.imagemathstudent.Retrofit.RetrofitInterface;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GlobalApplication extends Application {

    private static Retrofit retrofit = null;
    private static String accessToken;
    private static Context context;
    private static String SERVER_BASE_URL = "https://api-doc.imagemath.kr:3001/";

    public static String getAccessToken() {
        SharedPreferences pref = context.getSharedPreferences("ImageMathStudent", MODE_PRIVATE);
        accessToken = pref.getString("accessToken", null);
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        GlobalApplication.accessToken = accessToken;
        SharedPreferences pref = context.getSharedPreferences("ImageMathStudent", MODE_PRIVATE);
        pref.edit().putString("accessToken", accessToken).apply();

    }

    public static void removeAccessToken() {
        GlobalApplication.accessToken = null;
        SharedPreferences pref = context.getSharedPreferences("ImageMathStudent", MODE_PRIVATE);
        pref.edit().clear().apply();
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static RetrofitInterface getAPIService() {
        return getRetrofit().create(RetrofitInterface.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (context == null)
            context = this;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).trimMemory(level);
    }
}
