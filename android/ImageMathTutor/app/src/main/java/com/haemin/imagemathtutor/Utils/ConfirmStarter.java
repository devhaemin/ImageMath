package com.haemin.imagemathtutor.Utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

public class ConfirmStarter {
    public static boolean checkIntent(Activity activity, Intent fromOutside) {
        if (fromOutside == null) {
            Toast.makeText(activity, "비정상적인 접근입니다.", Toast.LENGTH_SHORT).show();
            activity.finish();
            return false;
        }
        return true;
    }
}
