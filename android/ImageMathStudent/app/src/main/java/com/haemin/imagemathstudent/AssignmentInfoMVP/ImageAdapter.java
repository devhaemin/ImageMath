package com.haemin.imagemathstudent.AssignmentInfoMVP;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.haemin.imagemathstudent.Data.ServerFile;
import com.haemin.imagemathstudent.R;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

    Context context;
    ArrayList<ServerFile> imageFiles;

    public ImageAdapter(Context context, ArrayList<ServerFile> imageFiles) {
        this.context = context;
        this.imageFiles = imageFiles;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_image,parent,false);

        return new ImageHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        ServerFile image = imageFiles.get(position);
        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions().fitCenter())
                .load(image.getFileUrl())
                .placeholder(R.drawable.e_mgt02)
                .thumbnail(0.1f)
                .into(holder.submitImage);

        ImagePopup imagePopup = new ImagePopup(context);
        imagePopup.setBackgroundColor(Color.BLACK);  // Optional
        imagePopup.setFullScreen(false); // Optional
        imagePopup.setHideCloseIcon(false);  // Optional
        imagePopup.setImageOnClickClose(false);  // Optional
        imagePopup.initiatePopupWithGlide(image.getFileUrl());

        holder.itemView.setOnClickListener(v -> {
            imagePopup.viewPopup();
        });
    }

    @Override
    public int getItemCount() {
        return imageFiles.size();
    }

    class ImageHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_submit)
        ImageView submitImage;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
