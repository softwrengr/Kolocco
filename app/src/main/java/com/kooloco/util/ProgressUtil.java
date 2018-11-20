package com.kooloco.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import com.kooloco.R;

/**
 * Created by hlink44 on 18/4/17.
 */

public class ProgressUtil {
    static Dialog progressDialog;

    public static void show(Context context) {
        if (context != null) {
            try {
                if (progressDialog != null) {
                    progressDialog.hide();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                progressDialog = new Dialog(context);
                progressDialog.setCancelable(false);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null, false);
                progressDialog.setContentView(view);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void hide() {
        if (progressDialog != null) {
            try {
                progressDialog.dismiss();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}
