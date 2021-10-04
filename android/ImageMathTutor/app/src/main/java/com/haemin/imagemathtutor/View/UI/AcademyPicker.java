package com.haemin.imagemathtutor.View.UI;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.haemin.imagemathtutor.Data.Academy;

import java.util.ArrayList;

public class AcademyPicker extends DialogFragment {

    ArrayList<Academy> academies;
    Academy selectedAcademy;
    OnItemClickListener onItemClickListener;

    public AcademyPicker(ArrayList<Academy> academies) {
        this.academies = academies;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        CharSequence[] names = new CharSequence[academies.size()];
        for (int i = 0; i < academies.size(); i++) {
            names[i] = academies.get(i).getAcademyName();
        }
        builder.setTitle("학원을 선택하세요.")
                .setItems(names, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedAcademy = academies.get(which);
                        if (onItemClickListener != null) onItemClickListener.onItemClick(selectedAcademy);
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Academy academy);
    }

}
