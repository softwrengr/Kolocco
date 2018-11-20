package com.kooloco.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;


import com.kooloco.BuildConfig;
import com.kooloco.R;
import com.kooloco.constant.Common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by hlink21 on 4/5/16.
 */
public class ImagePicker extends BottomSheetDialogFragment {


    @BindView(R.id.camera)
    AppCompatTextView camera;
    @BindView(R.id.gallery)
    AppCompatTextView gallery;

    String mCurrentPhotoPath;

    ImagePickerResult imagePickerResult;

    public void setImagePickerResult(ImagePickerResult imagePickerResult) {
        this.imagePickerResult = imagePickerResult;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_image_picker, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

       /* if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("Path")) {

                mCurrentPhotoPath = savedInstanceState.getString("Path");
            }
        }*/

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //    outState.putString("Path", mCurrentPhotoPath);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.camera, R.id.gallery})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera:


                if (Build.VERSION.SDK_INT >= 23) {

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                            ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            ) {
                        requestPermissions(Common.PERMISSIONS_CAMERA, Common.REQUEST_CAMERA_PERMISSION);
                    } else {
                        dispatchTakePictureIntent();
                    }

                } else dispatchTakePictureIntent();


                break;
            case R.id.gallery:


                if (Build.VERSION.SDK_INT >= 23) {

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(Common.PERMISSIONS_GALLERY, Common.REQUEST_GALLERY_PERMISSION);
                    } else {
                        openGallory();
                    }

                } else openGallory();


                break;
        }

    }

    private void openGallory() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, Common.RequestCode.RESULT_LOAD_IMAGE);
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            Uri photoURI = null;
            try {
                photoFile = createImageFile();
                photoURI = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", photoFile);
            } catch (IOException ex) {
                Log.e("TakePicture", ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, Common.RequestCode.REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        /*mCurrentPhotoPath = "file:" + image.getAbsolutePath();*/
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Common.RequestCode.REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {

            if (mCurrentPhotoPath != null) {
                imagePickerResult.onResult(mCurrentPhotoPath);
                dismiss();
            }
        } else if (requestCode == Common.RequestCode.RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK) {

            if (data != null) {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        mCurrentPhotoPath = cursor.getString(columnIndex);
                        cursor.close();
                        if (mCurrentPhotoPath != null)
                            imagePickerResult.onResult(mCurrentPhotoPath);
                    }

                }
                dismiss();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Common.REQUEST_CAMERA_PERMISSION) {


            // We have requested multiple permissions for contacts, so all of them need to be
            // checked.
            if (PermissionUtil.verifyPermissions(grantResults)) {
                // All required permissions have been granted, display contacts fragment.
                /*startService();*/
                dispatchTakePictureIntent();

            } else {
               /* Log.i("Main Activity", "Contacts permissions were NOT granted.");*/
            }

        } else if (requestCode == Common.REQUEST_GALLERY_PERMISSION) {

            if (PermissionUtil.verifyPermissions(grantResults)) {
                // All required permissions have been granted, display contacts fragment.
                /*startService();*/
                openGallory();
            } else {
               /* Log.i("Main Activity", "Contacts permissions were NOT granted.");*/
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public interface ImagePickerResult {
        void onResult(String path);
    }

}