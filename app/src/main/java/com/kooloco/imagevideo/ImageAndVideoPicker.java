package com.kooloco.imagevideo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.kooloco.R;
import com.kooloco.constant.Common;
import com.kooloco.crop.CropImage;
import com.kooloco.ui.TrimmerActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.util.FileUtils;
import com.kooloco.util.ImagePicker;
import com.kooloco.util.Utils;
import com.soundcloud.android.crop.Crop;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by hlink56 on 6/11/17.
 */

public class ImageAndVideoPicker extends BottomSheetDialogFragment {

    private static final int CAMERA_VIDEO_REQUEST = 4;
    private static final int GALLERY_VIDEO = 5;
    private static final int TRIIM_VIDEO = 45;
    @BindView(R.id.imageViewCamera)
    AppCompatTextView imageViewCamera;
    @BindView(R.id.imageViewGallery)
    AppCompatTextView imageViewGallery;
    @BindView(R.id.linearVideo)
    LinearLayout linearVideo;
    private static final int PICK_Camera_IMAGE = 2;
    private static final int SELECT_FILE1 = 1;
    private static final int STORAGE_PERMISSION = 3;
    private File file;
    Uri selectedImage;
    private boolean isclicked;
    private String selectedImagePath;
    public static final int CROP_IMAGE_ACTIVITY_REQUEST_CODE = 203;
    Uri imageUri;
    private int seconds = 0;
    private File fileC;


    public void setImageCallBack(ImageCallBack imageCallBack) {
        this.imageCallBack = imageCallBack;
    }

    private ImageCallBack imageCallBack;


    boolean isVideo = false;

    boolean isCustomCrop = false;

    public void setCustomCrop(boolean customCrop) {
        isCustomCrop = customCrop;
    }

    public static ImageAndVideoPicker newInstance(boolean isVideo) {

        Bundle args = new Bundle();

        ImageAndVideoPicker fragment = new ImageAndVideoPicker();
        fragment.setArguments(args);
        fragment.isVideo = isVideo;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_image, container, false);
        ButterKnife.bind(this, view);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        linearVideo.setVisibility(isVideo ? View.VISIBLE : View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkStoragePermissions();
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isclicked = false;
        switch (requestCode) {
            case SELECT_FILE1:
                if (resultCode == RESULT_OK) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        dismiss();
                    } else {
                        try {

                            Log.e(this.getClass().getName(), "Kp crash Image path is  ::::: " + data.getData());

                            selectedImage = data.getData();

                            if (selectedImage != null) {
                                String path = FileUtils.getPath(getActivity(), selectedImage);
                                // Now we need to set the GUI ImageView data with data read from the picked file.
                                Log.e(this.getClass().getName(), "::::: Image path is  ::::: " + path);
                        /*ImagePickerPath imagePicker = new ImagePickerPath();
                        imagePicker.setPick(true);
                        imagePicker.setImagePath(path);
                        if (imageCallBack != null)
                            //imageCallBack.sendImage(imagePicker);
                            dismiss();*/


                                cropImage(selectedImage);


                                ///Code for direct data transfare



                            /*    ImagePickerPath imagePicker = new ImagePickerPath();
                                imagePicker.setPick(true);

//                             fileC = new File(data.getExtras().getString("ImageData"));

                                fileC = new File(compressImage(path));

                                imagePicker.setImagePath(fileC.getAbsolutePath());
                                if (imageCallBack != null)
                                    imageCallBack.sendImage(imagePicker);
                                dismiss();*/

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    ImagePickerPath imagePicker = new ImagePickerPath();
                    imagePicker.setPick(false);
                    imagePicker.setError("PROBLEM TO SET IMAGE FROM GALLERY");
                    if (imageCallBack != null)
                        imageCallBack.sendImage(imagePicker);
                    dismiss();
                }
                break;

            case Common.RequestCode.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                if (data != null) {

                    if (data.getExtras() != null) {
                        if (data.getExtras().getString("ImageData") != null && !(data.getExtras().getString("ImageData").isEmpty())) {

                            ImagePickerPath imagePicker = new ImagePickerPath();
                            imagePicker.setPick(true);

//                             fileC = new File(data.getExtras().getString("ImageData"));

                            fileC = new File(compressImage(data.getExtras().getString("ImageData")));

                            imagePicker.setImagePath(fileC.getAbsolutePath());
                            if (imageCallBack != null)
                                imageCallBack.sendImage(imagePicker);
                            dismiss();
                        }
                    }
                }

                break;

            case PICK_Camera_IMAGE:
                if (resultCode == RESULT_OK) {

                    String path = selectedImagePath;
                    ImagePickerPath imagePicker = new ImagePickerPath();
                    imagePicker.setPick(true);
                    imagePicker.setImagePath(path);

                    cropImage(Uri.fromFile(new File(path)));

                 /*   if (imageCallBack != null)*/
                    // imageCallBack.sendImage(imagePicker);
//                    dismiss();

                    /*CropImage.activity(Uri.fromFile(new File(path)))
                            .setGuidelines(CropImageView.Guidelines.OFF)
                            .setOutputCompressQuality(100)
                            .setAutoZoomEnabled(true)
                            .setOutputUri(imageUri)
                            .setActivityTitle("Image Crop")
                            .start(getActivity(), ImageChooserDialog.this);*/

                } else {

                    Log.e(this.getClass().getName(), ":::: NOTHING SET IS CALLED :::::");
                    ImagePickerPath imagePicker = new ImagePickerPath();
                    imagePicker.setPick(false);
                    imagePicker.setError("PROBLEM TO SET IMAGE FROM GELLERY");
                    if (imageCallBack != null)
                        imageCallBack.sendImage(imagePicker);
                    dismiss();
                }
                break;

            case CAMERA_VIDEO_REQUEST:
                try {
                    // selectedImage = data.getData();
                    // String path = FileUtils.getPath(getActivity(), selectedImage);
                    ImagePickerPath imagePicker = new ImagePickerPath();
                    imagePicker.setPick(true);
                    imagePicker.setVideo(true);
                    imagePicker.setImagePath(selectedImagePath);
                    if (imageCallBack != null)
                        imageCallBack.sendImage(imagePicker);
                    dismiss();


/*
                    final boolean isNoget = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
                    File sdImageMainDirectory = new File(selectedImagePath);

                    if (isNoget) {
                        selectedImage = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", sdImageMainDirectory);
                    } else {
                        selectedImage = Uri.fromFile(sdImageMainDirectory);
                    }

                    startActivityForResult(new Intent(getActivity(), TrimmerActivity.class)
                            .putExtra(TrimmerActivity.EXTRA_VIDEO_PATH, selectedImagePath).putExtra(TrimmerActivity.VIDEO_LENGTH, toCheckVideoLength(selectedImage)), TRIIM_VIDEO);
*/

                } catch (Exception e) {

                }

                break;
            case GALLERY_VIDEO:
                try {
                    Uri selectedImageUri = data.getData();
                    selectedImagePath = Utils.getFileFromStorage(selectedImageUri, getActivity());
                    /*ImagePicker imagePicker = new ImagePicker();
                    imagePicker.setPick(true);
                    imagePicker.setVideo(true);
                    imagePicker.setImagePath(selectedImagePath);
                    if (imageCallBack != null)
                        imageCallBack.sendImage(imagePicker);
                    dismiss();*/
                    if (selectedImageUri != null) {
                        startActivityForResult(new Intent(getActivity(), TrimmerActivity.class)
                                .putExtra(TrimmerActivity.EXTRA_VIDEO_PATH, selectedImagePath)
                                .putExtra(TrimmerActivity.VIDEO_LENGTH, toCheckVideoLength(selectedImageUri)), TRIIM_VIDEO);
                    } else {
                        Toast.makeText(getActivity(), "Video url not found!", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }


                break;

            case TRIIM_VIDEO:
                if (data != null) {
                    Uri uri = Uri.parse(data.getStringExtra(TrimmerActivity.VIDEO_URI));
                    String path = FileUtils.getPath(getActivity(), uri);

                    ImagePickerPath imagePicker = new ImagePickerPath();
                    imagePicker.setPick(true);
                    imagePicker.setVideo(true);
                    imagePicker.setImagePath(path);
                    if (imageCallBack != null)
                        imageCallBack.sendImage(imagePicker);
                    dismiss();

                }
                break;
            case UCrop.REQUEST_CROP:

                try {
                    Uri resultUri = UCrop.getOutput(data);
                    String path = FileUtils.getPath(getActivity(), resultUri);
                    ImagePickerPath imagePickernew = new ImagePickerPath();
                    imagePickernew.setPick(true);

                    //                             fileC = new File(data.getExtras().getString("ImageData"));

                    fileC = new File(compressImage(path));

                    imagePickernew.setImagePath(fileC.getAbsolutePath());
                    if (imageCallBack != null) {
                        imageCallBack.sendImage(imagePickernew);
                    }

                    dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            default:
                ImagePickerPath imagePicker = new ImagePickerPath();
                imagePicker.setPick(false);
                imagePicker.setError("PROBLEM TO SET IMAGE FROM GELLERY");
                if (imageCallBack != null)
                    imageCallBack.sendImage(imagePicker);
                dismiss();
                break;
        }
    }

    public int toCheckVideoLength(Uri data) {
        try {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(getActivity().getApplication(), data);
            MediaPlayer mp = MediaPlayer.create(getActivity().getBaseContext(), data);
            int millis = mp.getDuration();
            Uri videoUri = data;
            String video = FileUtils.getPath(getActivity(), videoUri);

            seconds = (millis / 1000);
            if (seconds > 15) {
                return 15;
            } else {
                return seconds;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkStoragePermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
        } else {
            //selectImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // selectImage();
                    return;
                } else {
                    dismiss();
                }
                break;
        }
    }

    public void selectImage() {
        if (!isclicked) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECT_FILE1);
        }
        isclicked = true;
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        if (manager.findFragmentByTag(tag) == null) {
            super.show(manager, tag);
        }
    }

    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.imageViewCamera, R.id.imageViewGallery, R.id.imageViewVideoCamera, R.id.imageViewVideoGallery})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewCamera:
                if (!isclicked) {
                    getSaveImageUri();
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            imageUri);

                    startActivityForResult(cameraIntent, PICK_Camera_IMAGE);
                }
                isclicked = true;
                break;
            case R.id.imageViewGallery:

                selectImage();

                break;
            case R.id.imageViewVideoCamera:
                getSaveVideoUri();
                // start default camera
                Log.d(":::::", "" + selectedImagePath);

                Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        imageUri);
                startActivityForResult(cameraIntent, CAMERA_VIDEO_REQUEST);
                break;
            case R.id.imageViewVideoGallery:

             /*   Uri data = Uri.fromFile(Environment
                        .getExternalStorageDirectory());
                final boolean isNoget = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
                if (isNoget) {
                    data = FileProvider.getUriForFile(getActivity(),,Environment.getExternalStorageDirectory());
                }*/


                String type = "video/*";

                Intent cameraVideoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                cameraVideoIntent.setType(type);
                cameraVideoIntent.putExtra("return-data", true);

                try {
                    startActivityForResult(cameraVideoIntent, GALLERY_VIDEO);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Toast.makeText(BaseActivity.this, R.string.please_istall_gallery_application, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    public void getSaveImageUri() {

        try {
            File root = new File(Environment.getExternalStorageDirectory()
                    + "/kooloco/");
            if (!root.exists()) {
                root.mkdirs();
            }
            String imageName = "image_" + System.currentTimeMillis() + ".png";

            File sdImageMainDirectory = new File(root, imageName);


            final boolean isNoget = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
            if (isNoget) {
                imageUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", sdImageMainDirectory);
                selectedImagePath = sdImageMainDirectory.getAbsolutePath();
            } else {
                imageUri = Uri.fromFile(sdImageMainDirectory);
                selectedImagePath = FileUtils.getPath(getActivity(),
                        imageUri);
            }
        } catch (Exception e) {
            //  DebugLog.e("Incident Photo" + "Error occurred. Please try again later.");
        }


    }


    public Uri getSaveImageUriDestin() {

        Uri destin;

        File root = new File(Environment.getExternalStorageDirectory()
                + "/kooloco/");
        if (!root.exists()) {
            root.mkdirs();
        }
        String imageName = "video_" + System.currentTimeMillis() + ".mp4";

        File sdImageMainDirectory = new File(root, imageName);

        destin = Uri.fromFile(sdImageMainDirectory);

        return destin;
    }

    /*
        public Uri getSaveImageUriDestin() {

            try {
                File root = new File(Environment.getExternalStorageDirectory()
                        + "/kooloco/");
                if (!root.exists()) {
                    root.mkdirs();
                }
                String imageName = "image_" + System.currentTimeMillis() + ".png";

                File sdImageMainDirectory = new File(root, imageName);


                final boolean isNoget = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
                if (isNoget) {
                    imageUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", sdImageMainDirectory);
                    selectedImagePath = sdImageMainDirectory.getAbsolutePath();
                } else {
                    imageUri = Uri.fromFile(sdImageMainDirectory);
                    selectedImagePath = FileUtils.getPath(getActivity(),
                            imageUri);
                }
            } catch (Exception e) {
                //  DebugLog.e("Incident Photo" + "Error occurred. Please try again later.");
            }


        }*/
    public void getSaveVideoUri() {

        try {
            File root = new File(Environment.getExternalStorageDirectory()
                    + "/kooloco/");
            if (!root.exists()) {
                root.mkdirs();
            }
            String imageName = "video_" + System.currentTimeMillis() + ".mp4";

            File sdImageMainDirectory = new File(root, imageName);


            final boolean isNoget = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
            if (isNoget) {
                imageUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", sdImageMainDirectory);
                selectedImagePath = sdImageMainDirectory.getAbsolutePath();
            } else {
                imageUri = Uri.fromFile(sdImageMainDirectory);
                selectedImagePath = FileUtils.getPath(getActivity(),
                        imageUri);
            }
        } catch (Exception e) {
            //  DebugLog.e("Incident Photo" + "Error occurred. Please try again later.");
        }

    }


    public interface ImageCallBack {

        void sendImage(ImagePickerPath imagePicker);
    }


    private void cropImage(Uri selectUri) {

        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));


        options.setActiveWidgetColor(getResources().getColor(R.color.colorPrimary));

        UCrop.of(selectUri, getSaveImageUriDestin())
                .withOptions(options)
                .start(getActivity(), this);

/*
        if (mCurrentPhotoPath != null && !(mCurrentPhotoPath.isEmpty())) {
            //Log.e("File::",mCurrentPhotoPath);
            CropImage cropImage = new CropImage();
            cropImage.setCustomeCrop(true);
            cropImage.setFileName(mCurrentPhotoPath)
                    .setRequestCode(Common.RequestCode.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
                    .start(getContext(), this);
        }
*/
    }


    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public String compressImage(String filePath) {

        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 1920.0f;//1280.0f;//816.0f;
        float maxWidth = 1080.0f;//852.0f;//612.0f;

        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }


    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "Kooloco/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }


    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }


    public void getSaveVideoFileExt() {

        try {
            File root = new File(Environment.getExternalStorageDirectory() + "/.kooloco/");

            if (!root.exists()) {
                root.mkdirs();
            }

            String imageName = "video_" + System.currentTimeMillis() + ".mp4";

            File sdImageMainDirectory = new File(root, imageName);


        } catch (Exception e) {
            //  DebugLog.e("Incident Photo" + "Error occurred. Please try again later.");
        }

    }

}