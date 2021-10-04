package com.haemin.imagemathtutor.View.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.Data.ServerTime;
import com.haemin.imagemathtutor.R;

public class LectureTimeView extends LinearLayout {

    Context context;
    OnDeleteClickListener onDeleteClickListener;
    int seq;


    @BindView(R.id.edit_lecture_time_day)
    EditText editLectureTimeDay;
    @BindView(R.id.edit_time_first)
    EditText editTimeFirst;
    @BindView(R.id.edit_time_second)
    EditText editTimeSecond;
    @BindView(R.id.btn_delete)
    ImageButton btnDelete;

    public LectureTimeView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public LectureTimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getAttrs(attrs);
        init();
    }

    public LectureTimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        getAttrs(attrs);
        init();
    }

    public LectureTimeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        getAttrs(attrs);
        init();
    }

    public ServerTime getServerTime() {
        ServerTime serverTime = new ServerTime();
        serverTime.setLectureTimeDay(editLectureTimeDay.getText().toString());
        serverTime.setLectureTimeFirst(editTimeFirst.getText().toString());
        serverTime.setLectureTimeSecond(editTimeSecond.getText().toString());
        return serverTime;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    private void init() {
        View v = LayoutInflater.from(context).inflate(R.layout.time_recycler, this, false);
        ButterKnife.bind(this, v);
        addView(v);

        btnDelete.setOnClickListener(v1 -> {
            if (onDeleteClickListener != null)
                onDeleteClickListener.onDelete(this);
        });
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public EditText getEditLectureTimeDay() {
        return editLectureTimeDay;
    }

    public EditText getEditTimeFirst() {
        return editTimeFirst;
    }

    public EditText getEditTimeSecond() {
        return editTimeSecond;
    }

    private void getAttrs(AttributeSet attrs) {
    }

    public interface OnDeleteClickListener {
        void onDelete(LectureTimeView view);
    }
}
