package com.kooloco.ui.visitor.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.model.Review;
import com.kooloco.util.ReadMore;
import com.kooloco.util.TimeConvertUtils;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class RateReviewAdapter extends RecyclerView.Adapter<RateReviewAdapter.ViewHolder> {
    Context context;
    List<Review> reviews;


    public RateReviewAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_dashboard_review, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Picasso.with(context).load(reviews.get(position).getImageUrl()).placeholder(R.drawable.user_round).error(R.drawable.user_round).resize(250,250).centerCrop().transform(new CircleTransform()).into(holder.imageViewProfile);

        holder.customTextViewName.setText(reviews.get(position).getName());
        holder.customTextViewDate.setText(TimeConvertUtils.dateTimeConvertLocalToLocal(reviews.get(position).getInsertDate(), "yyyy-MM-dd HH:mm:ss", "dd MMM, yyyy"));
        holder.customTextViewDesc.setText(reviews.get(position).getReview());

        if (holder.customTextViewDesc.length() >= 100) {
            ReadMore.less = context.getString(R.string.readless);
            ReadMore.more = context.getString(R.string.readmore);
            ReadMore.makeTextViewResizable(holder.customTextViewDesc, 2, context.getString(R.string.readmore), true);
        }

        float rate = 0.0f;
        if (reviews.get(position).getRate() != null) {
            if (!reviews.get(position).getRate().isEmpty()) {
                try {
                    rate = Float.parseFloat(reviews.get(position).getRate());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        holder.ratingBarRat.setRating(rate);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewProfile)
        ImageView imageViewProfile;
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.customTextViewDate)
        AppCompatTextView customTextViewDate;
        @BindView(R.id.customTextViewDesc)
        AppCompatTextView customTextViewDesc;
        @BindView(R.id.ratingBarRat)
        AppCompatRatingBar ratingBarRat;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
