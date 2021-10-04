package com.haemin.imagemathtutor.View.UI;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.haemin.imagemathtutor.Data.SelectableData;

import java.util.ArrayList;

public class ListPickerDialog<T extends SelectableData> extends DialogFragment {

    ArrayList<T> datas;
    T selectedData;
    OnItemClickListener<T> onItemClickListener;
    String title;

    public ListPickerDialog(ArrayList<T> datas, String title) {
        this.datas = datas;
        this.title = title;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        CharSequence[] names = new CharSequence[datas.size()];
        for (int i = 0; i < datas.size(); i++) {
            names[i] = datas.get(i).getListName();
        }
        builder.setTitle(title)
                .setItems(names, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedData = datas.get(which);
                        if (onItemClickListener != null) onItemClickListener.onItemClick(selectedData);
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener<T extends SelectableData> {
        void onItemClick(T data);
    }

}
