package com.kooloco.ui.myexperience.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.model.BlogMedia;
import com.kooloco.model.LocalImage;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class BlogSlider extends RecyclerView.Adapter<BlogSlider.ViewHolder> {
    Context context;
    List<BlogMedia> localImages;
    CallBack callBack;

    public BlogSlider(Context context, List<BlogMedia> localImages, CallBack callBack) {
        this.context = context;
        this.localImages = localImages;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_blog_slider_image, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.imageViewVideo.setVisibility(localImages.get(position).getMediaType().equalsIgnoreCase("V") ? View.VISIBLE : View.INVISIBLE);

        if (localImages.get(position).getMediaType().equalsIgnoreCase("V")) {
            Picasso.with(context).load(localImages.get(position).getVideoThumb()).placeholder(R.drawable.place).into(holder.imageView);
        } else {
            Picasso.with(context).load(localImages.get(position).getFile()).placeholder(R.drawable.place).into(holder.imageView);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClick(position, localImages.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return localImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.imageViewVideo)
        ImageView imageViewVideo;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClick(int position, BlogMedia blogMedia);
    }
}
