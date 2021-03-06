package com.kooloco.uilocal.profile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.model.LocalImage;
import com.kooloco.model.OrgImage;
import com.kooloco.util.picaso.Rounded;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class SportImageAdapter extends RecyclerView.Adapter<SportImageAdapter.ViewHolder> {
    Context context;
    private List<LocalImage> orgImages;

    public SportImageAdapter(Context context, List<LocalImage> orgImage) {
        this.context = context;
        orgImages = orgImage;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_visitor_organization_detail_org_images, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.with(context).load(orgImages.get(position).getLocalImageUrl()).resize(context.getResources().getDimensionPixelOffset(R.dimen.dp_75), context.getResources().getDimensionPixelOffset(R.dimen.dp_75)).centerCrop().transform(new Rounded(30, Rounded.Corners.ALL)).placeholder(R.drawable.place).into(holder.imageViewServic);
    }

    @Override
    public int getItemCount() {
        return orgImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewServic)
        ImageView imageViewServic;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
