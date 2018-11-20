package com.kooloco.uilocal.addservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.model.AddImages;
import com.kooloco.util.picaso.Rounded;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class AddServiceAdapter extends RecyclerView.Adapter<AddServiceAdapter.ViewHolder> {
    Context context;
    List<AddImages> addImagesList;
    private CallBack callBack;
    int select = -1;

    public AddServiceAdapter(Context context, List<AddImages> addImagesList, CallBack callBack) {
        this.context = context;
        this.addImagesList = addImagesList;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_add_image, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.with(context).load(addImagesList.get(position).getImageUrl()).resize(context.getResources().getDimensionPixelOffset(R.dimen.dp_75), context.getResources().getDimensionPixelOffset(R.dimen.dp_75)).centerCrop().transform(new Rounded(30, Rounded.Corners.ALL)).into(holder.imageViewServic);
        if (select == position) {
            holder.imageViewAdd.setVisibility(View.VISIBLE);
            holder.imageViewFilter.setVisibility(View.VISIBLE);
        } else {
            holder.imageViewAdd.setVisibility(View.INVISIBLE);
            holder.imageViewFilter.setVisibility(View.INVISIBLE);
        }

        holder.imageViewServic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickSelect(addImagesList.get(position));
                select = position;
                notifyDataSetChanged();
            }
        });

        holder.imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickDelate(addImagesList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return addImagesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewServic)
        ImageView imageViewServic;
        @BindView(R.id.imageViewFilter)
        ImageView imageViewFilter;
        @BindView(R.id.imageViewAdd)
        ImageView imageViewAdd;
        @BindView(R.id.imageViewClose)
        ImageView imageViewClose;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {

        void onClickSelect(AddImages addImages);

        void onClickDelate(AddImages addImages);

    }


}
