package com.kooloco.ui.visitor.home.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.kooloco.R;
import com.kooloco.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class MultipalImageUploadNew extends RecyclerView.Adapter<MultipalImageUploadNew.ViewHolder> {
    Context context;
    List<String> files;
    List<Integer> per;
    List<Integer> perOld;


    public MultipalImageUploadNew(Context context, List<String> files, List<Integer> per) {
        this.context = context;
        this.files = files;
        this.per = per;
        perOld = new ArrayList<>();
        perOld.addAll(per);
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


        holder.textViewCount.setText(per.get(position) + "%");

        ObjectAnimator animation = ObjectAnimator.ofInt(holder.progressBar1, "progress", perOld.get(position), per.get(position));
        animation.setDuration(200); // 0.5 second
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        perOld.set(position, per.get(position));

    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public void setProgres(int pos, int percentage) {
        per.set(pos, percentage);

        notifyItemChanged(pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressBar1)
        ProgressBar progressBar1;
        @BindView(R.id.imageViewUpload)
        ImageView imageViewUpload;
        @BindView(R.id.textViewCount)
        AppCompatTextView textViewCount;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
