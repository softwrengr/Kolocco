package com.kooloco.ui.expereince.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.model.ExpereinceNew;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class ExperienceSelectVisitorAdapter extends RecyclerView.Adapter<ExperienceSelectVisitorAdapter.ViewHolder> {
    Context context;
    CallBack callBack;
    List<ExpereinceNew> expereinceNews;
    int selectPosition = 0;

    public ExperienceSelectVisitorAdapter(Context context, List<ExpereinceNew> expereinceNews, int selectExpPosition, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        this.expereinceNews = expereinceNews;
        selectPosition = selectExpPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.exp_select_vistor_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (selectPosition == -1) {
            holder.linearLayoutExpRoot.setAlpha(1.0f);
        } else {
            holder.linearLayoutExpRoot.setAlpha((selectPosition == position) ? 1.0f : 0.5f);
        }

        holder.linearLayoutExpRoot.setClickable(true);

        Picasso.with(context).load(expereinceNews.get(position).getImage_url()).placeholder(R.drawable.place).error(R.drawable.place).into(holder.imageView);

        holder.customTextViewTitle.setText(expereinceNews.get(position).getTitle());

        holder.viewFast.setVisibility((position == 0) ? View.VISIBLE : View.GONE);

        holder.viewLast.setVisibility((position == (expereinceNews.size() - 1)) ? View.VISIBLE : View.GONE);

        float rate = 0.0f;

        try {
            rate = Float.parseFloat(expereinceNews.get(position).getRate());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        holder.ratingBar.setRating(rate);

        holder.textViewRateCount.setText("(" + expereinceNews.get(position).getRateCount() + ")");

        holder.customTextViewLocation.setText(expereinceNews.get(position).getLocation());

        holder.textViewExpPrice.setText(expereinceNews.get(position).getPrice());

        Picasso.with(context).load(expereinceNews.get(position).getExperience_url()).placeholder(R.drawable.place).error(R.drawable.place).into(holder.imageViewExp);

        holder.imageViewTick.setVisibility((selectPosition == position) ? View.VISIBLE : View.GONE);

        holder.linearLayoutExpRoot.setOnClickListener(view -> {
            if (selectPosition != position) {
                int tempPosition = selectPosition;
                selectPosition = position;
                if (tempPosition == -1) {
                    notifyDataSetChanged();
                } else {
                    notifyItemChanged(tempPosition);
                    notifyItemChanged(selectPosition);
                }
                callBack.onClickItem(expereinceNews.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expereinceNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.viewFast)
        View viewFast;
        @BindView(R.id.imageView)
        PorterShapeImageView imageView;
        @BindView(R.id.customTextViewTitle)
        AppCompatTextView customTextViewTitle;
        @BindView(R.id.ratingBar)
        AppCompatRatingBar ratingBar;
        @BindView(R.id.textViewRateCount)
        AppCompatTextView textViewRateCount;
        @BindView(R.id.customTextViewLocation)
        AppCompatTextView customTextViewLocation;
        @BindView(R.id.imageViewExp)
        ImageView imageViewExp;
        @BindView(R.id.textViewExpPrice)
        AppCompatTextView textViewExpPrice;
        @BindView(R.id.linearLayoutExpRoot)
        LinearLayout linearLayoutExpRoot;
        @BindView(R.id.imageViewTick)
        ImageView imageViewTick;
        @BindView(R.id.viewLast)
        View viewLast;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickItem(ExpereinceNew expereinceNew, int pos);
    }
}

