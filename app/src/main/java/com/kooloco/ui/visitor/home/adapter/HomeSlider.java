package com.kooloco.ui.visitor.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.model.LocalImage;
import com.kooloco.util.picaso.Rounded;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class HomeSlider extends RecyclerView.Adapter<HomeSlider.ViewHolder> {
    Context context;
    List<LocalImage> localImages;
    CallBack callBack;

    public HomeSlider(Context context, List<LocalImage> localImages,CallBack callBack) {
        this.context = context;
        this.localImages = localImages;
        this.callBack=callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_home_slider_image, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(localImages.get(position).getLocalImage()).placeholder(R.drawable.place).into(holder.imageView);
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

    public interface CallBack{
        void onClick();
    }
}
