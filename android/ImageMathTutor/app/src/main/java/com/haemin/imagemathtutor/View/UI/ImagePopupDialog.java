package com.haemin.imagemathtutor.View.UI;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.haemin.imagemathtutor.AppString;
import com.haemin.imagemathtutor.AssignmentInfoMVP.AssignmentInfoContract;
import com.haemin.imagemathtutor.Data.ServerFile;
import com.haemin.imagemathtutor.Data.StudentAssignment;
import com.haemin.imagemathtutor.GlobalApplication;
import com.haemin.imagemathtutor.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class ImagePopupDialog extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.btn_assignment_a)
    Button btnAssignmentA;
    @BindView(R.id.btn_assignment_b)
    Button btnAssignmentB;
    @BindView(R.id.btn_assignment_c)
    Button btnAssignmentC;
    @BindView(R.id.btn_assignment_d)
    Button btnAssignmentD;
    @BindView(R.id.recycler_image)
    RecyclerView recyclerImage;

    String assignmentSeq;
    String assignmentAdmSeq;
    ArrayList<ServerFile> imageFiles;
    AssignmentInfoContract.AssignmentInfoPresenter presenter;

    public ImagePopupDialog(String assignmentSeq, String assignmentAdmSeq, ArrayList<ServerFile> imageFiles, AssignmentInfoContract.AssignmentInfoPresenter presenter) {
        this.assignmentSeq = assignmentSeq;
        this.imageFiles = imageFiles;
        this.assignmentAdmSeq = assignmentAdmSeq;
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.assignment_pop_up,container,false);
        ButterKnife.bind(this,v);
        btnAssignmentA.setOnClickListener(this);
        btnAssignmentB.setOnClickListener(this);
        btnAssignmentC.setOnClickListener(this);
        btnAssignmentD.setOnClickListener(this);

        ImageAdapter imageAdapter = new ImageAdapter(getContext(),imageFiles);
        recyclerImage.setAdapter(imageAdapter);
        recyclerImage.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));


        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_assignment_a:
                patchStatus(2);
                break;
            case R.id.btn_assignment_b:
                patchStatus(3);
                break;
            case R.id.btn_assignment_c:
                patchStatus(4);
                break;
            case R.id.btn_assignment_d:
                patchStatus(5);
                break;
            default:
        }
    }

    private void patchStatus(int status) {
        GlobalApplication.getAPIService().setAssignmentAdmStatus(GlobalApplication.getAccessToken(),assignmentAdmSeq,status)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 200){
                            Toast.makeText(getContext(),"숙제 검사가 성공적으로 완료되었습니다.",Toast.LENGTH_SHORT).show();
                            presenter.requestStudentData(assignmentSeq);
                            dismiss();
                        }else{
                            Log.e("ImagePopupDialog",response.message());
                            Toast.makeText(getContext(),"숙제 검사가 실패하였습니다.\n잠시후 다시 시도해주세요.",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("ImagePopupDialog",t.getMessage(),t);
                        Toast.makeText(getContext(), AppString.ERROR_NETWORK_MESSAGE,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public class ImageAdapter extends RecyclerView.Adapter<ImageHolder>{
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
            ServerFile serverFile = imageFiles.get(position);
            Glide.with(context)
                    .load(serverFile.getFileUrl())
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return imageFiles.size();
        }
    }
    class ImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView imageView;
        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
