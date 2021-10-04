package com.haemin.imagemathtutor.NoticeMVP;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.Data.Notice;
import com.haemin.imagemathtutor.Data.ServerFile;
import com.haemin.imagemathtutor.GlobalApplication;
import com.haemin.imagemathtutor.R;
import com.haemin.imagemathtutor.View.UI.FileButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class NoticeRecyclerAdapter extends RecyclerView.Adapter<NoticeRecyclerAdapter.NoticeViewHolder> {

    Context context;
    ArrayList<Notice> notices;
    NoticeContract.NoticePresenter presenter;

    public NoticeRecyclerAdapter(Context context, ArrayList<Notice> notices, NoticeContract.NoticePresenter presenter) {
        this.context = context;
        this.notices = notices;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_notice, parent, false);
        return new NoticeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        Notice notice = notices.get(position);
        holder.btnDelete.setOnClickListener(v -> {
            GlobalApplication.getAPIService().deleteNotice(GlobalApplication.getAccessToken(),notice.getNoticeSeq()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 200)presenter.updateData(notice.getLectureSeq());
                        else Toast.makeText(context,"공지사항 삭제가 실패했습니다.\n이미 삭제된 공지일 수 있습니다.",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("NoticeRecyclerAdapter",t.getMessage(),t);
                    Toast.makeText(context, AppString.ERROR_NETWORK_MESSAGE,Toast.LENGTH_SHORT).show();
                }
            });
        });
        GlobalApplication.getAPIService().getNoticeFileList(GlobalApplication.getAccessToken(),notice.getNoticeSeq()).enqueue(new Callback<ArrayList<ServerFile>>() {
            @Override
            public void onResponse(Call<ArrayList<ServerFile>> call, Response<ArrayList<ServerFile>> response) {
                if(response.code() == 200 && response.body() != null){
                    holder.groupFile.removeAllViews();
                    for(ServerFile serverFile : response.body()){
                        FileButton fileButton = new FileButton(context);
                        fileButton.setDeleteAble(false);
                        fileButton.setFile(serverFile);
                        holder.groupFile.addView(fileButton);
                    }
                }else{
                    Toast.makeText(context,"공지사항 파일을 읽어오는데 실패했습니다.",Toast.LENGTH_SHORT).show();
                    Log.e("NoticeRecyclerAdapter",response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ServerFile>> call, Throwable t) {
                Log.e("NoticeRecyclerAdapter",t.getMessage(),t);
                Toast.makeText(context,AppString.ERROR_NETWORK_MESSAGE,Toast.LENGTH_SHORT).show();
            }
        });

        holder.textNoticeTime.setText(DateUtils.getRelativeTimeSpanString(notice.getPostTime()));
        holder.textNoticeNumber.setText(notice.getNoticeSeq());
        holder.textNoticeTitle.setText(notice.getTitle());
        holder.textNoticeText.setText(notice.getContents());

    }

    @Override
    public int getItemCount() {
        return notices.size();
    }

    class NoticeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_delete)
        ImageButton btnDelete;
        @BindView(R.id.group_file)
        LinearLayout groupFile;
        @BindView(R.id.text_notice_time)
        TextView textNoticeTime;
        @BindView(R.id.text_notice_number)
        TextView textNoticeNumber;
        @BindView(R.id.text_notice_title)
        TextView textNoticeTitle;
        @BindView(R.id.text_notice_text)
        TextView textNoticeText;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
