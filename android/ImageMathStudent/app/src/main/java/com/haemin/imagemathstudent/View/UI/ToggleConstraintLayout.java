package com.haemin.imagemathstudent.View.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Checkable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.haemin.imagemathstudent.R;

public class ToggleConstraintLayout extends ConstraintLayout implements Checkable {

    boolean checked;
    Drawable drawableOn;
    Drawable drawableOff;
    AttributeSet attr;
    Context context;


    public ToggleConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        attr = attrs;
        this.context = context;
        init();
    }



    public ToggleConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        attr = attrs;
        this.context = context;
        init();
    }

    private void init() {
        TypedArray array = context.obtainStyledAttributes(attr, R.styleable.ToggleConstraintLayout);
        drawableOn = array.getDrawable(R.styleable.ToggleConstraintLayout_drawableOn);
        drawableOff = array.getDrawable(R.styleable.ToggleConstraintLayout_drawableOff);
        checked = array.getBoolean(R.styleable.ToggleConstraintLayout_checked,true);

        if(checked &&drawableOn != null)
            setBackground(drawableOn);
        else if(!checked && drawableOff != null)
            setBackground(drawableOff);
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
        if(checked &&drawableOn != null)
            setBackground(drawableOn);
        else if(!checked && drawableOff != null)
            setBackground(drawableOff);
    }

    @Override
    public boolean isChecked() {
        return this.checked;
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }
}

