package com.kooloco.ui.visitor.home.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.model.HomeTopLocation;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class HomeLocationAdapter extends RecyclerView.Adapter<HomeLocationAdapter.ViewHolder> {
    Context context;
    List<HomeTopLocation> homeTopLocations;
    private CallBack callBack;

    public HomeLocationAdapter(Context context, List<HomeTopLocation> homeTopLocations, CallBack callBack) {
        this.context = context;
        this.homeTopLocations = homeTopLocations;
        this.callBack = callBack;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_new_row_location, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        // Glide.with(context).load(homeTopLocations.get(position).getTopLocationImageUrl()).error(R.drawable.place).placeholder(R.drawable.place).into(holder.imageViewLocation);

        Picasso.with(context).load(homeTopLocations.get(position).getTopLocationImageUrl()).error(R.drawable.place).placeholder(R.drawable.place).into(holder.imageViewLocation);

        holder.viewFast.setVisibility((position == 0) ? View.VISIBLE : View.GONE);

        holder.customTextViewLocation.setText(homeTopLocations.get(position).getCity());

        holder.customTextViewExpCount.setText("(" + homeTopLocations.get(position).getExpCount() + ") " + context.getString(R.string.experiences_location));

        holder.linearLayoutExpRoot.setOnClickListener(view -> {
            callBack.onClickItem(homeTopLocations.get(position), position);
        });
    }

    @Override
    public int getItemCount() {
        return homeTopLocations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewLocation)
        PorterShapeImageView imageViewLocation;
        @BindView(R.id.linearLayoutExpRoot)
        LinearLayout linearLayoutExpRoot;
        @BindView(R.id.viewFast)
        View viewFast;

        @BindView(R.id.customTextViewLocation)
        AppCompatTextView customTextViewLocation;
        @BindView(R.id.customTextViewExpCount)
        AppCompatTextView customTextViewExpCount;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickItem(HomeTopLocation homeTopLocation, int position);
    }


}
