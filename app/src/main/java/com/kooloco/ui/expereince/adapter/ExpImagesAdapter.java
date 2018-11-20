package com.kooloco.ui.expereince.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kooloco.R;
import com.kooloco.model.AddImages;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class ExpImagesAdapter extends RecyclerView.Adapter<ExpImagesAdapter.ViewHolder> {
    Context context;
    List<AddImages> addImagesList;
    private CallBack callBack;


    public ExpImagesAdapter(Context context, List<AddImages> addImagesList, CallBack callBack) {
        this.context = context;
        this.addImagesList = addImagesList;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.exp_row_visitor_and_local_details_extra_fields_images, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (addImagesList.get(position).getImageUrl().contains("android.resource:")) {
            Picasso.with(context).load(addImagesList.get(position).getImageUrl()).into(holder.imageView);
        } else {
            Glide.with(context).load(addImagesList.get(position).getImageUrl()).asBitmap().placeholder(R.drawable.place).error(R.drawable.place).into(holder.imageView);
        }

        holder.imageView.setOnClickListener(view -> callBack.onClickSelect(addImagesList.get(position).getImageUrl()));

    }

    @Override
    public int getItemCount() {
        return addImagesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface CallBack {
        void onClickSelect(String imageUrl);
    }


}
