package com.kooloco.ui.visitor.organizationDetails.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.model.LocalImage;
import com.kooloco.model.OrgImage;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class OrgImageSlider extends RecyclerView.Adapter<OrgImageSlider.ViewHolder> {
    Context context;
    List<OrgImage> localImages;
    CallBack callBack;

    public OrgImageSlider(Context context, List<OrgImage> localImages, CallBack callBack) {
        this.context = context;
        this.localImages = localImages;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_dashboard_slider_image, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.with(context).load(localImages.get(position).getImageUrl()).placeholder(R.drawable.place).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClick(position);
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClick(int position);
    }
}
