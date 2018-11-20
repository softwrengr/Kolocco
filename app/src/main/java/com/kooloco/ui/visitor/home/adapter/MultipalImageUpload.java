package com.kooloco.ui.visitor.home.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.kooloco.R;
import com.kooloco.model.Chat;
import com.kooloco.model.MultiFile;
import com.kooloco.util.Util;
import com.kooloco.util.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class MultipalImageUpload extends RecyclerView.Adapter<MultipalImageUpload.ViewHolder> {
    Context context;
    List<String> files;
    private String userId;
    CallBack callBack;
    String folderLocation;

    List<MultiFile> imagePath = new ArrayList<>();

    public MultipalImageUpload(Context context, List<String> files, String folderLocation, String userId, CallBack callBack) {
        this.context = context;
        this.files = files;
        this.userId = userId;
        this.callBack = callBack;
        this.folderLocation = folderLocation;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_progress_multipale, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        File file = new File(files.get(position));

        if (Utils.isVideoFile(file.getAbsolutePath())) {
            Bitmap compressedThumbFile = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
            holder.imageViewUpload.setImageBitmap(compressedThumbFile);
        } else {
            Bitmap compressedThumbFile = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(file.getAbsolutePath()), 200, 200);
            holder.imageViewUpload.setImageBitmap(compressedThumbFile);
        }

        TransferUtility transferUtility = Util.getTransferUtility(context);


        TransferObserver observer = transferUtility.upload(folderLocation, userId + "_" + file.getName(), file, CannedAccessControlList.PublicRead);

        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                Log.e("AMZON", "onStateChanged: " + id + ", " + state);
                if (state == TransferState.IN_PROGRESS) {

                }
                if (state == TransferState.COMPLETED) {
                    Log.e("AMZ", "Upload Complete");

                } else if (state == TransferState.FAILED) {
                    //sendCallBack();
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

                int percentage = (int) (100F * bytesCurrent / bytesTotal);
                Log.e("AMZON_PROGRESS_CHANGE", String.format("onProgressChanged: %d, total: %d, current: %d per: %d",
                        id, bytesTotal, bytesCurrent, percentage));


                if (holder.textViewCount != null) {
                    holder.textViewCount.setText("" + percentage + "%");
                }
                if (holder.progressBar1 != null) {

                    ObjectAnimator animation = ObjectAnimator.ofInt(holder.progressBar1, "progress", percentage);
                    animation.setDuration(500); // 0.5 second
                    animation.setInterpolator(new DecelerateInterpolator());
                    animation.start();
                }

                if (percentage == 100) {

                    if (Utils.isVideoFile(file.getAbsolutePath())) {
                        Bitmap compressedThumbFile = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);

                        String filename = userId + "_" + file.getName();
                        filename = filename.replace(".mp4", ".jpg");

                        uploads3Thum(folderLocation + "/thum", filename, Utils.saveImage(compressedThumbFile));
                    } else {
                        Bitmap compressedThumbFile = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(file.getAbsolutePath()), 200, 200);
                        uploads3Thum(folderLocation + "/thum", userId + "_" + file.getName(), Utils.saveImage(compressedThumbFile));
                    }

                    MultiFile multiFile = new MultiFile();
                    multiFile.setFile(userId + "_" + file.getName());

                    String mediaType = "I";
                    if (Utils.isVideoFile(file.getAbsolutePath())) {
                        mediaType = "V";
                    }

                    multiFile.setMediaType(mediaType);

                    if (Utils.isVideoFile(file.getAbsolutePath())) {

                        String filename = userId + "_" + file.getName();
                        filename = filename.replace(".mp4", ".jpg");

                        multiFile.setVidepThumb(filename);
                    }

                    if (!checkIsAllReadyFile(multiFile.getFile())) {
                        imagePath.add(multiFile);
                    }

                    if (imagePath.size() == files.size()) {
                        sendCallBack();
                    }
                }
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e("AMZONERROR", "Error during upload: " + id + ex.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressBar1)
        ProgressBar progressBar1;
        @BindView(R.id.textViewCount)
        AppCompatTextView textViewCount;
        @BindView(R.id.imageViewUpload)
        ImageView imageViewUpload;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onSuccess(List<MultiFile> multiFiles);

        void onError(String message);
    }

    void uploads3Thum(final String objectKey,
                      final String fileName, final File originalFile) {

        TransferUtility transferUtility = Util.getTransferUtility(context);
        TransferObserver observer = transferUtility.upload(objectKey,
                fileName, originalFile, CannedAccessControlList.PublicRead);

        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                Log.e("AMZON THUM", "onStateChanged: " + id + ", " + state);

                if (state == TransferState.IN_PROGRESS) {

                }

                if (state == TransferState.COMPLETED) {


                } else if (state == TransferState.FAILED) {
                    callBack.onError(context.getResources().getString(R.string.val_upload_again));
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

                int percentage = (int) (100F * bytesCurrent / bytesTotal);
                Log.e("AMZON_PROGRESS THUM", String.format("onProgressChanged: %d, total: %d, current: %d per: %d",
                        id, bytesTotal, bytesCurrent, percentage));
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e("AMZONERROR THUM", "Error during upload: " + id + ex.toString());

            }
        });
    }


    private boolean checkIsAllReadyFile(String nameFile) {
        boolean isExists = false;
        for (MultiFile MultiFile : imagePath) {
            if (MultiFile.getFile().equalsIgnoreCase(nameFile)) {
                isExists = true;
                break;
            }
        }

        return isExists;
    }

    private void sendCallBack() {
        List<MultiFile> multiFiles = new ArrayList<>();

        for (String s : files) {
            File file = new File(s);

            MultiFile multiFile = new MultiFile();
            multiFile.setFile(userId + "_" + file.getName());

            String mediaType = "I";
            if (Utils.isVideoFile(file.getAbsolutePath())) {
                mediaType = "V";
            }
            multiFile.setMediaType(mediaType);


            if (Utils.isVideoFile(file.getAbsolutePath())) {

                String filename = userId + "_" + file.getName();
                filename = filename.replace(".mp4", ".jpg");

                multiFile.setVidepThumb(filename);
            }

            multiFiles.add(multiFile);
        }

        if (callBack != null) {
            callBack.onSuccess(multiFiles);
        }
    }
}
