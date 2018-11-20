package com.kooloco.crop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


import com.isseiaoki.simplecropview.CropImageView;
import com.kooloco.R;
import com.kooloco.constant.Common;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * on 4/11/16.
 */

public class CropperActivity extends AppCompatActivity {


    @BindView(R.id.cropView)
    CropImageView cropView;
    int requestCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_simple_crop);
        ButterKnife.bind(this);

        requestCode = getIntent().getExtras().getInt(Common.Crop.REQUEST_CODE);

        try {
            if (getIntent() != null && getIntent().getExtras() != null) {
                if (getIntent().getExtras().getBoolean(Common.Crop.ISCUSTOMCROP)) {
                    cropView.setCropMode(CropImageView.CropMode.FIT_IMAGE);
                } else {
                    cropView.setCropMode(CropImageView.CropMode.RATIO_16_9);
                }
                //getSupportActionBar().setTitle(getIntent().getExtras().getString(Common.Crop.TITLE));
                Picasso.with(this).load(new File(getIntent().getExtras().getString(Common.Crop.IMAGE))).into(cropView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crop_image_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.crop_image_menu_rotate_left:
                if (cropView.getImageBitmap() != null)
                    cropView.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D);
                return false;
            case R.id.crop_image_menu_rotate_right:
                if (cropView.getImageBitmap() != null)
                    cropView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
                return false;
            case R.id.crop_image_menu_crop:
                if (cropView.getImageBitmap() != null) {
                    Intent intent = new Intent();
                    String s = storeImageToCache(cropView.getCroppedBitmap(), this);
                    intent.putExtra("ImageData", s);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                return false;
        }
        return true;
    }

    public static String storeImageToCache(Bitmap data, Context context) {
        Bitmap thumbnail = null;
        try {
            Date dateTime = new Date();

            thumbnail = data;
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            String filenamePath = "tmp2" + System.currentTimeMillis() + ".jpg";
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            File outputDir = context.getCacheDir();
            File file = new File(outputDir.getPath() + "/" + filenamePath);
            file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.close();

            thumbnail.recycle();

            return file.getAbsolutePath().toString();
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }
}
