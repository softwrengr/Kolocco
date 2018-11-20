package com.kooloco.ui.visitor.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.LocalImage;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class DialogImageSlider extends RecyclerView.Adapter<DialogImageSlider.ViewHolder> {
    Context context;
    List<LocalImage> localImages;


    public DialogImageSlider(Context context, List<LocalImage> localImages) {
        this.context = context;
        this.localImages = localImages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_dialog_image_slider, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.with(context).load(localImages.get(position).getLocalImage()).placeholder(R.drawable.place).into(holder.imageView);

        if(localImages.get(position).getTitle()==null){
            localImages.get(position).setTitle("");
        }

        holder.linearLayoutTitle.setVisibility(localImages.get(position).getTitle().isEmpty() ? View.GONE : View.VISIBLE);

        holder.textViewTitle.setText(localImages.get(position).getTitle());
        holder.textViewDesc.setText(localImages.get(position).getDescription());

        if(localImages.get(position).getDescription()==null){
            localImages.get(position).setDescription("");
        }

        holder.textViewDesc.setVisibility(localImages.get(position).getDescription().isEmpty() ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return localImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.textViewTitle)
        AppCompatTextView textViewTitle;
        @BindView(R.id.textViewDesc)
        AppCompatTextView textViewDesc;
        @BindView(R.id.linearLayoutTitle)
        LinearLayout linearLayoutTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
