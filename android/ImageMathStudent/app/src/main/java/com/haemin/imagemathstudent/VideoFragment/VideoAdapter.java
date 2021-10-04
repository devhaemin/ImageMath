package com.haemin.imagemathstudent.VideoFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.haemin.imagemathstudent.Data.ServerFile;
import com.haemin.imagemathstudent.Data.Video;
import com.haemin.imagemathstudent.R;
import com.haemin.imagemathstudent.SingleTon.AppString;
import com.haemin.imagemathstudent.SingleTon.GlobalApplication;
import com.haemin.imagemathstudent.VideoDetailView.VideoPlayActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    Context context;
    ArrayList<Video> videos;

    public VideoAdapter(Context context, ArrayList<Video> videos) {
        this.context = context;
        this.videos = videos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_video, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video = videos.get(position);

        holder.textTitle.setText(video.getTitle());
        holder.textContents.setText(video.getContents());
        holder.textUpdateTime.setText(DateUtils.getRelativeTimeSpanString(context, video.getUploadTime()));

        GlobalApplication.getAPIService().getVideoFileList(GlobalApplication.getAccessToken(), "" + video.getVideoSeq())
                .enqueue(new Callback<ArrayList<ServerFile>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ServerFile>> call, Response<ArrayList<ServerFile>> response) {
                        if (response.code() == 200 && response.body() != null && response.body().size() > 0) {
                            ServerFile videoFile = response.body().get(0);

                            ThumbnailTask.ThumbnailHolder holder1 = new ThumbnailTask.ThumbnailHolder(videoFile.getFileUrl(), holder.imageThumbnail);
                            new ThumbnailTask()
                                    .execute(holder1);
                            holder.imageThumbnail.setOnClickListener(v -> VideoPlayActivity.start(context, videoFile.getFileUrl()));
                        } else {
                            Toast.makeText(context, "동영상 파일을 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ServerFile>> call, Throwable t) {
                        Toast.makeText(context, AppString.ERROR_NETWORK_MESSAGE, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }


    public static class ThumbnailTask extends AsyncTask<ThumbnailTask.ThumbnailHolder, Void, Bitmap> {
        public static class ThumbnailHolder {
            private String videoPath;
            private ImageView holder;

            public ThumbnailHolder(String videoPath, ImageView holder) {
                this.videoPath = videoPath;
                this.holder = holder;
            }

            public String getVideoPath() {
                return videoPath;
            }

            public void setVideoPath(String videoPath) {
                this.videoPath = videoPath;
            }

            public ImageView getHolder() {
                return holder;
            }

            public void setHolder(ImageView holder) {
                this.holder = holder;
            }
        }

        ThumbnailHolder thumbnailHolder;

        @Override
        protected Bitmap doInBackground(ThumbnailHolder... holders) {
            thumbnailHolder = holders[0];
            String videoPath = thumbnailHolder.videoPath;
            Bitmap bitmap = null;
            MediaMetadataRetriever mediaMetadataRetriever = null;
            try {
                mediaMetadataRetriever = new MediaMetadataRetriever();
                if (Build.VERSION.SDK_INT >= 14)
                    mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
                else
                    mediaMetadataRetriever.setDataSource(videoPath);

                bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("VideoAdapter", "Thumbnail Error!", new Throwable(
                        "Exception in retriveVideoFrameFromVideo(String videoPath)"
                                + e.getMessage()));

            } finally {
                if (mediaMetadataRetriever != null) {
                    mediaMetadataRetriever.release();
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                bitmap = Bitmap.createScaledBitmap(bitmap, 240, 240, false);
                thumbnailHolder.holder.setImageBitmap(bitmap);
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_update_time)
        TextView textUpdateTime;
        @BindView(R.id.text_title)
        TextView textTitle;
        @BindView(R.id.image_thumbnail)
        ImageView imageThumbnail;
        @BindView(R.id.text_contents)
        TextView textContents;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
