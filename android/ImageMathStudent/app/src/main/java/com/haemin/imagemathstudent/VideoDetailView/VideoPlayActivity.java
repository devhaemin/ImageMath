package com.haemin.imagemathstudent.VideoDetailView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.haemin.imagemathstudent.R;

public class VideoPlayActivity extends AppCompatActivity {

    String videoUrl = "";
    public static void start(Context context, String videoUrl) {
        Intent starter = new Intent(context, VideoPlayActivity.class);
        starter.putExtra("videoUrl", videoUrl);
        context.startActivity(starter);
    }
    private PlayerView playerView;
    private SimpleExoPlayer player;

    private Boolean playWhenReady = true;
    ImageView fullscreenButton;
    boolean fullscreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        // hide();

        Intent fromOutside = getIntent();
        videoUrl = fromOutside.getStringExtra("videoUrl");
        playerView = findViewById(R.id.exoPlayerView);
        initializePlayer();

        fullscreenButton = playerView.findViewById(R.id.exo_fullscreen_icon);
        fullscreenButton.setOnClickListener(view -> {
            if(fullscreen) {
                fullscreenButton.setImageDrawable(ContextCompat.getDrawable(VideoPlayActivity.this, R.drawable.ic_fullscreen_open));

                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                if(getSupportActionBar() != null){
                    getSupportActionBar().show();
                }

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                params.width = params.MATCH_PARENT;
                params.height = (int) ( 200 * getApplicationContext().getResources().getDisplayMetrics().density);
                playerView.setLayoutParams(params);

                fullscreen = false;
            }else{
                fullscreenButton.setImageDrawable(ContextCompat.getDrawable(VideoPlayActivity.this, R.drawable.ic_fullscreen_close));

                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                if(getSupportActionBar() != null){
                    getSupportActionBar().hide();
                }

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                ViewGroup.LayoutParams params = playerView.getLayoutParams();
                params.width = params.MATCH_PARENT;
                params.height = params.MATCH_PARENT;
                playerView.setLayoutParams(params);

                fullscreen = true;
            }
        });

        playerView.setPlayer(player);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);

        player.setPlayWhenReady(true);
        player.addListener(new Player.EventListener() {

            /**
             * @param playWhenReady - Whether playback will proceed when ready.
             * @param playbackState - One of the STATE constants.
             */
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                switch (playbackState) {

                    case Player.STATE_IDLE: // 1
                        //재생 실패
                        break;
                    case Player.STATE_BUFFERING: // 2
                        // 재생 준비
                        break;
                    case Player.STATE_READY: // 3
                        // 재생 준비 완료
                        break;
                    case Player.STATE_ENDED: // 4
                        // 재생 마침
                        break;
                    default:
                        break;
                }
            }
        });


    }
    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void initializePlayer() {
        if (player == null) {

            player = ExoPlayerFactory.newSimpleInstance(this.getApplicationContext());

            //플레이어 연결
            playerView.setPlayer(player);

        }

        String sample = videoUrl;

        MediaSource mediaSource = buildMediaSource(Uri.parse(sample));

        //prepare
        player.prepare(mediaSource, true, false);

        //start,stop
        player.setPlayWhenReady(playWhenReady);
    }
    private MediaSource buildMediaSource(Uri uri) {

        String userAgent = Util.getUserAgent(this, "blackJin");

        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri);
    }
    private void releasePlayer() {
        if (player != null) {

            playWhenReady = player.getPlayWhenReady();

            playerView.setPlayer(null);
            player.release();
            player = null;

        }
    }
}
