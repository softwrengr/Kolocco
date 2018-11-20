package com.kooloco.ui.visitor.home.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.model.LocalNew;
import com.kooloco.model.Service;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class HomeLocalAndExpLocalAdapter extends RecyclerView.Adapter<HomeLocalAndExpLocalAdapter.ViewHolder> {
    Context context;
    CallBack callBack;
    List<LocalNew> localNews;

    public HomeLocalAndExpLocalAdapter(Context context, List<LocalNew> localNews, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        this.localNews = localNews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_local_and_exp_row_local, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.with(context).load(localNews.get(position).getImage_url()).placeholder(R.drawable.place).error(R.drawable.place).into(holder.imageView);

        holder.customTextViewName.setText(localNews.get(position).getFirstName() + " " + localNews.get(position).getLastName());

        holder.viewFast.setVisibility((position == 0) ? View.VISIBLE : View.GONE);

        float rate = 0.0f;

        try {
            rate = Float.parseFloat(localNews.get(position).getRate());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        holder.ratingBar.setRating(rate);

        holder.linearLayoutExpRoot.setClickable(true);


        holder.customTextViewLocation.setText(localNews.get(position).getLocation());

        holder.textViewExpPrice.setText(localNews.get(position).getPrice());
        Picasso.with(context).load(localNews.get(position).getProfileImage()).placeholder(R.drawable.user_round).transform(new CircleTransform()).error(R.drawable.user_round).into(holder.imageViewProfile);

        List<Service> servicesTemp = new ArrayList<>();

        if (localNews.get(position).getServices().size() <= 3) {
            servicesTemp.addAll(localNews.get(position).getServices());
        } else {
            for (int i = 0; i < 3; i++) {
                servicesTemp.add(localNews.get(position).getServices().get(i));
            }
        }

        holder.recyclerViewService.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerViewService.setAdapter(new HomeServiceAdapter(context, servicesTemp, true, pos -> {

        }));
        holder.linearLayoutExpRoot.setOnClickListener(view -> callBack.onClickItem(localNews.get(position), position));
    }

    @Override
    public int getItemCount() {
        return localNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        PorterShapeImageView imageView;
        @BindView(R.id.checkboxFav)
        CheckBox checkboxFav;
        @BindView(R.id.imageViewProfile)
        ImageView imageViewProfile;
        @BindView(R.id.flowLayoutImgProfile)
        FrameLayout flowLayoutImgProfile;
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.ratingBar)
        AppCompatRatingBar ratingBar;
        @BindView(R.id.customTextViewLocation)
        AppCompatTextView customTextViewLocation;
        @BindView(R.id.textViewExpPrice)
        AppCompatTextView textViewExpPrice;
        @BindView(R.id.recyclerViewService)
        RecyclerView recyclerViewService;
        @BindView(R.id.linearLayoutMain)
        LinearLayout linearLayoutMain;
        @BindView(R.id.linearLayoutExpRoot)
        LinearLayout linearLayoutExpRoot;
        @BindView(R.id.viewFast)
        View viewFast;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickItem(LocalNew localNews, int pos);
    }

}

