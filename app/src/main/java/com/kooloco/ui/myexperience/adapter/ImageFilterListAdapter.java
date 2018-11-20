package com.kooloco.ui.myexperience.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.model.ImageFilter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;

/**
 * Created by hlink44 on 14/9/17.
 */

public class ImageFilterListAdapter extends RecyclerView.Adapter<ImageFilterListAdapter.ViewHolder> {
    Context context;
    private CallBack callBack;
    List<ImageFilter> imageFilters;
    String imageUrl;
    int selectPosition = 0;

    public ImageFilterListAdapter(Context context, List<ImageFilter> imageFilters, String imageUrl, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        this.imageFilters = imageFilters;
        this.imageUrl = imageUrl;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_image_filter, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.imageViewTick.setVisibility((selectPosition == position) ? View.VISIBLE : View.GONE);

        if (position == 0) {
            Glide.with(context).load(imageUrl).asBitmap().into(holder.imageView);

        } else if (position == 1) {
            Glide.with(context).load(imageUrl).asBitmap().transform(new InvertFilterTransformation(context)).into(holder.imageView);

        } else if (position == 2) {
            Glide.with(context).load(imageUrl).asBitmap().transform(new ToonFilterTransformation(context)).into(holder.imageView);

        } else if (position == 3) {
            Glide.with(context).load(imageUrl).asBitmap().transform(new SepiaFilterTransformation(context)).into(holder.imageView);

        } else if (position == 4) {
            Glide.with(context).load(imageUrl).asBitmap().transform(new ContrastFilterTransformation(context)).into(holder.imageView);

        } else if (position == 5) {
            Glide.with(context).load(imageUrl).asBitmap().transform(new BrightnessFilterTransformation(context)).into(holder.imageView);

        } else if (position == 6) {
            Glide.with(context).load(imageUrl).asBitmap().transform(new SketchFilterTransformation(context)).into(holder.imageView);

        }


        holder.imageView.setOnClickListener(view -> {
            callBack.onClickItem(position);
            selectPosition = position;
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return imageFilters.size();
    }

    @OnClick(R.id.linearLayoutMain)
    public void onViewClicked() {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        PorterShapeImageView imageView;
        @BindView(R.id.imageViewTick)
        ImageView imageViewTick;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickItem(int position);

    }

}
