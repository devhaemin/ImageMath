package com.haemin.imagemathtutor.AssignmentFragmentMVP;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.Data.Assignment;
import com.haemin.imagemathtutor.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AssignmentRecyclerAdapter  extends RecyclerView.Adapter<AssignmentRecyclerAdapter.AssignmentViewHolder> {

    Context context;
    ArrayList<AssignmentDateHolder> dateHolders;

    public AssignmentRecyclerAdapter(Context context, ArrayList<AssignmentDateHolder> dateHolders) {
        this.context = context;
        this.dateHolders = dateHolders;
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.recycler_assignment,parent,false);
        return new AssignmentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {

        AssignmentDateHolder dateHolder = dateHolders.get(position);
        AssignmentInsideAdapter insideAdapter = new AssignmentInsideAdapter(context,dateHolder.getAssignments());
        holder.insideRecycler.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
//        holder.insideRecycler.setNestedScrollingEnabled(false);
//        holder.insideRecycler.setHasFixedSize(false);
        holder.insideRecycler.setAdapter(insideAdapter);

        long dateTime = dateHolder.getDate();
        Date date = new Date(dateTime);
        String[] calHeader = {"일","월","화","수","목","금","토"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String time = (calendar.get(Calendar.MONTH)+1)+". "+calendar.get(Calendar.DATE)+" "+calHeader[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        holder.textAssignmentDay.setText(time);

    }
    void notifyData(){
        super.notifyDataSetChanged();

    }


    @Override
    public int getItemCount() {
        return dateHolders.size();
    }

    class AssignmentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recycler_assignment_inside)
        RecyclerView insideRecycler;
        @BindView(R.id.text_assignment_day)
        TextView textAssignmentDay;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public static class AssignmentDateHolder{
        ArrayList<Assignment> assignments;
        long date;

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public ArrayList<Assignment> getAssignments() {
            return assignments;
        }

        public void setAssignments(ArrayList<Assignment> assignments) {
            this.assignments = assignments;
        }
    }
}
