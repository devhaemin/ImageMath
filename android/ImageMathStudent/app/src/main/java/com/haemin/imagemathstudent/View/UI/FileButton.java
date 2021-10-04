package com.haemin.imagemathstudent.View.UI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.haemin.imagemathstudent.Data.ServerFile;
import com.haemin.imagemathstudent.R;

public class FileButton extends RelativeLayout implements View.OnClickListener {

    Context context;
    @BindView(R.id.text_file_name)
    TextView fileName;
    @BindView(R.id.btn_delete_file)
    ImageView btnDeleteFile;

    boolean isDeleteAble = false;
    OnDeleteClickListener onDeleteClickListener;

    ServerFile file;


    @Override
    public void onClick(View v) {
        if(file == null){
            return;
        }
        if (file.getFileUrl() == null || file.getFileUrl().equals("")) {
            return;
        }
        String url = file.getFileUrl();
        String type = url.substring(url.length() - 3);
        Log.e("TEST",type);
        if(type.equals("jpg") || type.equals("png") ){
            showImageDialog(url);
        }else {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(file.getFileUrl()));
            context.startActivity(i);
            Toast.makeText(context, "파일 다운로드를 위해 브라우저로 이동합니다.\n크롬 브라우저를 선택해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public void setFile(ServerFile file) {
        this.file = file;
        fileName.setText(file.getFileName());
    }

    public void setDeleteAble(boolean deleteAble) {
        isDeleteAble = deleteAble;
        if(isDeleteAble){
            btnDeleteFile.setVisibility(VISIBLE);
            if(onDeleteClickListener != null)
                btnDeleteFile.setOnClickListener(v1 -> onDeleteClickListener.onDeleteClick(this, file));
        }else{
            btnDeleteFile.setVisibility(GONE);
        }
    }

    public FileButton(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public FileButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
        getAttrs(attrs);
    }

    public FileButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
        getAttrs(attrs);
    }

    public FileButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
        getAttrs(attrs);
    }

    private void getAttrs(AttributeSet attrs) {
        setDeleteAble(attrs.getAttributeBooleanValue(R.styleable.FileButton_deleteable,false));
    }

    private void init() {
        View v = LayoutInflater.from(context).inflate(R.layout.btn_file,this,false);
        addView(v);
        setOnClickListener(this);
        ButterKnife.bind(this,v);
    }

    public void showImageDialog(String url){

        AlertDialog.Builder builder;
        AlertDialog alertDialog;

        FrameLayout frameLayout = new FrameLayout(context);

        ImageView image = new ImageView(context);
        image.setLayoutParams(new FrameLayout.LayoutParams(dpToPx(context,300),dpToPx(context,500)));
        frameLayout.addView(image);
        Glide.with(context)
                .load(url)
                .into(image);

        builder = new AlertDialog.Builder(context);
        builder.setView(frameLayout);
        alertDialog = builder.create();
        alertDialog.show();
    }
    public int dpToPx(Context context, float dp){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
    }

    public interface OnDeleteClickListener{
        void onDeleteClick(FileButton fileButton, ServerFile file);
    }

}
