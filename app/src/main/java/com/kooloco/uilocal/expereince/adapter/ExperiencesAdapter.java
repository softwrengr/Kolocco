package com.kooloco.uilocal.expereince.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.ui.base.BaseActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class ExperiencesAdapter extends RecyclerView.Adapter<ExperiencesAdapter.ViewHolder> {
    Context context;
    CallBack callBack;
    List<ExpereinceNew> expereinceNews;


    public ExperiencesAdapter(Context context, List<ExpereinceNew> expereinceNews, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        this.expereinceNews = expereinceNews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.exp_local_row_create, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.with(context).load(expereinceNews.get(position).getImage_url()).placeholder(R.drawable.place).error(R.drawable.place).into(holder.imageView);

        holder.linearLayoutExpRoot.setClickable(false);

        holder.customTextViewTitle.setText(expereinceNews.get(position).getTitle());
        holder.customTextViewTitle.setMinLines(1);
        float rate = 0.0f;

        try {
            rate = Float.parseFloat(expereinceNews.get(position).getRate());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        holder.ratingBar.setRating(rate);

        holder.textViewRateCount.setText("(" + expereinceNews.get(position).getRateCount() + ")");

        if (expereinceNews.get(position).getCity().isEmpty() && expereinceNews.get(position).getCountry().isEmpty()) {
            holder.customTextViewLocation.setText(R.string.set_meeting_spot);
        } else {
            holder.customTextViewLocation.setText(expereinceNews.get(position).getLocation());
        }

        holder.textExpCurrency.setText(BaseActivity.currency);
        holder.textViewExpPrice.setText(expereinceNews.get(position).getPrice());


        if (expereinceNews.get(position).getExperience_url().trim().isEmpty()) {
            holder.imageViewExp.setVisibility(View.GONE);
        } else {
            holder.imageViewExp.setVisibility(View.VISIBLE);
            Picasso.with(context).load(expereinceNews.get(position).getExperience_url()).placeholder(R.drawable.place).error(R.drawable.place).into(holder.imageViewExp);
        }

        holder.frameDelete.setOnClickListener(view -> holder.imageViewCancel.performClick());
        holder.frameEdit.setOnClickListener(view -> holder.imageViewEdit.performClick());

        holder.imageViewCancel.setOnClickListener(view -> callBack.onClickDelete(expereinceNews.get(position), position));
        holder.imageViewEdit.setOnClickListener(view -> callBack.onClickEdit(expereinceNews.get(position), position));
        holder.linearLayoutRoot.setOnClickListener(view -> callBack.onClickItem(expereinceNews.get(position), position));
    }

    @Override
    public int getItemCount() {
        return expereinceNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        PorterShapeImageView imageView;
        @BindView(R.id.customTextViewTitle)
        AppCompatTextView customTextViewTitle;
        @BindView(R.id.ratingBar)
        AppCompatRatingBar ratingBar;
        @BindView(R.id.customTextViewLocation)
        AppCompatTextView customTextViewLocation;
        @BindView(R.id.imageViewExp)
        ImageView imageViewExp;
        @BindView(R.id.textViewExpPrice)
        AppCompatTextView textViewExpPrice;
        @BindView(R.id.imageViewEdit)
        ImageView imageViewEdit;
        @BindView(R.id.imageViewCancel)
        ImageView imageViewCancel;
        @BindView(R.id.textViewRateCount)
        AppCompatTextView textViewRateCount;
        @BindView(R.id.linearLayoutRoot)
        LinearLayout linearLayoutRoot;
        @BindView(R.id.linearLayoutExpRoot)
        LinearLayout linearLayoutExpRoot;
        @BindView(R.id.frameEdit)
        FrameLayout frameEdit;
        @BindView(R.id.frameDelete)
        FrameLayout frameDelete;

        @BindView(R.id.textExpCurrency)
        AppCompatTextView textExpCurrency;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickItem(ExpereinceNew expereinceNew, int pos);

        void onClickDelete(ExpereinceNew expereinceNew, int pos);

        void onClickEdit(ExpereinceNew expereinceNew, int pos);
    }
}

