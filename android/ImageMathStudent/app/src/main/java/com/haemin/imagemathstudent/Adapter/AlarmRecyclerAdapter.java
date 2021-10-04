package com.haemin.imagemathstudent.Adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathstudent.Data.Alarm;
import com.haemin.imagemathstudent.R;
import com.haemin.imagemathstudent.SingleTon.GlobalApplication;

import java.util.ArrayList;

public class AlarmRecyclerAdapter extends RecyclerView.Adapter<AlarmRecyclerAdapter.AlarmViewHolder> {

    Context context;
    ArrayList<Alarm> alarms;

    public AlarmRecyclerAdapter(Context context, ArrayList<Alarm> alarms) {
        this.context = context;
        this.alarms = alarms;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_alarm, parent, false);
        return new AlarmViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {

        Alarm alarm = alarms.get(position);
        holder.textAlarmTitle.setText(alarm.getTitle());
        holder.textAlarmContent.setText(alarm.getContent());
        holder.textAlarmTime.setText(DateUtils.getRelativeTimeSpanString(alarm.getPostTime()));
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    class AlarmViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_alarm_title)
        TextView textAlarmTitle;
        @BindView(R.id.text_alarm_content)
        TextView textAlarmContent;
        @BindView(R.id.text_alarm_time)
        TextView textAlarmTime;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
}
