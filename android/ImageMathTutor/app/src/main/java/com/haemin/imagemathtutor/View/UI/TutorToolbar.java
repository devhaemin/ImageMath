package com.haemin.imagemathtutor.View.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.R;

public class TutorToolbar extends Toolbar {

    @BindView(R.id.btn_setting)
    ImageButton btnSetting;
    @BindView(R.id.btn_notification)
    ImageButton btnNotification;

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

    void init(Context context) {
        View toolbar = LayoutInflater.from(context).inflate(R.layout.toolbar, this, false);
        addView(toolbar);
        ButterKnife.bind(this, toolbar);

        btnSetting.setOnClickListener(v -> {

        });
        btnNotification.setOnClickListener(v -> {

        });
    }
}
