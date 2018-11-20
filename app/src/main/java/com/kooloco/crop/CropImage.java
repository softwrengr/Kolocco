package com.kooloco.crop;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import com.kooloco.constant.Common;

import java.io.File;

public class CropImage {
    private File file = null;
    private int requestCode = 0;
    private String title = null;
    private String fileName;
    private boolean isCustomeCrop = false;


    public CropImage setFile(File file) {
        this.fileName = file.getAbsolutePath();
        return this;
    }


    public CropImage setFileName(String file) {
        this.fileName = file;
        return this;
    }

    public CropImage setRequestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public CropImage setTitle(String title) {
        this.title = title;
        return this;
    }

    public void setCustomeCrop(boolean customeCrop) {
        isCustomeCrop = customeCrop;
    }

    public CropMyImage createCropMyImage() {
        return new CropMyImage(file, requestCode, title);
    }

    public void start(@NonNull Context context, @NonNull Fragment fragment) {
        fragment.startActivityForResult(getIntent(context), requestCode);
    }

    public Intent getIntent(@NonNull Context context) {
        return getIntent(context, CropperActivity.class);
    }

    public Intent getIntent(@NonNull Context context, @Nullable Class<?> cls) {
        if (fileName == null || fileName.isEmpty())
            throw new IllegalArgumentException("Please provide File to Crop");
        else if (requestCode == 0)
            throw new IllegalArgumentException("Please provide Request code");
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtra(Common.Crop.IMAGE, fileName);
        intent.putExtra(Common.Crop.REQUEST_CODE, requestCode);
        intent.putExtra(Common.Crop.TITLE, "Crop Image");
        intent.putExtra(Common.Crop.ISCUSTOMCROP, isCustomeCrop);

        return intent;
    }
}