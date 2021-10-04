package com.haemin.imagemathstudent.View.UI;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathstudent.R;
import com.haemin.imagemathstudent.SettingMVP.SettingActivity;
import com.haemin.imagemathstudent.View.Activity.AlarmActivity;

public class TutorToolbar extends Toolbar {

    @BindView(R.id.btn_setting)
    ImageButton btnSetting;
    @BindView(R.id.btn_notification)
    ImageButton btnNotification;

    OnClickListener settingOnClickListener;
    OnClickListener notificationOnClickListener;

    public void setSettingOnClickListener(OnClickListener settingOnClickListener) {
        this.settingOnClickListener = settingOnClickListener;

        if(settingOnClickListener == null){
            settingOnClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    getContext().startActivity(new Intent(getContext(), SettingActivity.class));
                }
            };
        }
        btnSetting.setOnClickListener(settingOnClickListener);

    }

    public void setNotificationOnClickListener(OnClickListener notificationOnClickListener) {
        this.notificationOnClickListener = notificationOnClickListener;

        if(notificationOnClickListener == null){
            notificationOnClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {

                    getContext().startActivity(new Intent(getContext(), AlarmActivity.class));
                }
            };
        }
        btnNotification.setOnClickListener(notificationOnClickListener);
    }

    public TutorToolbar(Context context) {
        super(context);
        init(context);
    }

    public TutorToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TutorToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    void init(Context context){
        View toolbar = LayoutInflater.from(context).inflate(R.layout.toolbar, this, false);
        addView(toolbar);
        ButterKnife.bind(this,toolbar);

        setSettingOnClickListener(v -> {
            SettingActivity.start(context);
        });
        setNotificationOnClickListener(v -> {
            AlarmActivity.start(context);
        });

    }
}
