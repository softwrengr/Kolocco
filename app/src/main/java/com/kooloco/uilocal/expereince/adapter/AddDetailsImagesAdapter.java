package com.kooloco.uilocal.expereince.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.model.AddImages;
import com.kooloco.ui.alllocal.fragment.AllLocalMapFragment;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class AddDetailsImagesAdapter extends RecyclerView.Adapter<AddDetailsImagesAdapter.ViewHolder> {
    Context context;
    List<AddImages> addImagesList;
    private boolean isDelete;
    private CallBack callBack;


    public AddDetailsImagesAdapter(Context context, List<AddImages> addImagesList, boolean isDelete, CallBack callBack) {
        this.context = context;
        this.addImagesList = addImagesList;
        this.isDelete = isDelete;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.exp_row_local_add_details, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //Glide.with(context).load(addImagesList.get(position).getImageUrl()).asBitmap().override(context.getResources().getDimensionPixelOffset(R.dimen.dp_100), context.getResources().getDimensionPixelOffset(R.dimen.dp_100)).transform(new RoundedCornersTransformation(context,30,0)).into(holder.imageViewServic);
        if (addImagesList.get(position).getImageUrl().contains("android.resource:")) {
            Picasso.with(context).load(addImagesList.get(position).getImageUrl()).into(holder.imageViewServic);
        } else {


            Glide.with(context).load(addImagesList.get(position).getImageUrl()).asBitmap().placeholder(R.drawable.place).error(R.drawable.place).into(holder.imageViewServic);


        }

        holder.imageViewClose.setVisibility(isDelete ? View.VISIBLE : View.INVISIBLE);

        holder.imageViewServic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickSelect(addImagesList.get(position));
                notifyDataSetChanged();
            }
        });

        holder.imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickDelete(addImagesList.get(position));
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
        @BindView(R.id.imageViewClose)
        ImageView imageViewClose;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface CallBack {

        void onClickSelect(AddImages addImages);

        void onClickDelete(AddImages addImages);

    }


}
