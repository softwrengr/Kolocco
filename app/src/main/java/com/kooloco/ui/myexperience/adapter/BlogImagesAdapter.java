package com.kooloco.ui.myexperience.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.model.AddImages;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class BlogImagesAdapter extends RecyclerView.Adapter<BlogImagesAdapter.ViewHolder> {
    Context context;
    List<AddImages> addImagesList;
    CallBack callBack;

    public BlogImagesAdapter(Context context, List<AddImages> addImagesList, CallBack callBack) {
        this.context = context;
        this.addImagesList = addImagesList;
        this.callBack = callBack;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_blog_add_image, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.imageViewPlay.setVisibility(addImagesList.get(position).isVideo() ? View.VISIBLE : View.GONE);
        holder.frameLayoutFilt.setVisibility(addImagesList.get(position).isDelete() ? View.VISIBLE : View.GONE);

        Glide.with(context).load(addImagesList.get(position).getImageUrl()).asBitmap().placeholder(R.drawable.place).into(holder.imageViewServic);

        holder.imageViewServic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AddImages addImages = addImagesList.get(position);
                addImages.setDelete(true);
                addImagesList.set(position, addImages);
                notifyDataSetChanged();
                return true;
            }
        });

        holder.imageViewServic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClick(addImagesList.get(position));
            }
        });
        holder.frameLayoutFilt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onDelete(addImagesList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return addImagesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewServic)
        PorterShapeImageView imageViewServic;
        @BindView(R.id.imageViewPlay)
        ImageView imageViewPlay;
        @BindView(R.id.frameLayoutFilt)
        FrameLayout frameLayoutFilt;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface CallBack {
        void onClick(AddImages addImages);

        void onDelete(AddImages addImages);

    }

}
