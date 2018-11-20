package com.kooloco.ui.visitor.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.model.LocalImage;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class HomeLocalAndExpLocalWithScrollImagesAdapter extends RecyclerView.Adapter<HomeLocalAndExpLocalWithScrollImagesAdapter.ViewHolder> {
    Context context;
    List<LocalImage> localImages;
    CallBack callBack;


    public HomeLocalAndExpLocalWithScrollImagesAdapter(Context context, List<LocalImage> localImages, CallBack callBack) {
        this.context = context;
        this.localImages = localImages;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_local_and_exp_row_local_scroll_image, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(localImages.get(position).getLocalImage()).resizeDimen(R.dimen.dp_260,R.dimen.dp_160).centerCrop().placeholder(R.drawable.place).into(holder.imageView);
        holder.imageView.setOnClickListener(view -> callBack.onClick());
    }

    @Override
    public int getItemCount() {
        return localImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        PorterShapeImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClick();
    }
}
