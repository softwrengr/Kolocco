package com.kooloco.uilocal.earnings.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;
import com.kooloco.core.Common;
import com.kooloco.model.EarningRate;
import com.kooloco.model.Month;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class RateEarningAdapter extends RecyclerView.Adapter<RateEarningAdapter.ViewHolder> {
    Context context;
    List<EarningRate> earningRates;

    public RateEarningAdapter(Context context, List<EarningRate> earningRates) {
        this.context = context;
        this.earningRates = earningRates;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_earning_rating, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String textOption = earningRates.get(position).getName();

        switch (textOption) {
            case Common.LocalRate.QUALITY_PRICE:
                textOption = context.getResources().getString(R.string.local_review_quality_price);
                break;
            case Common.LocalRate.ARRIVAL_TIME:
                textOption = context.getResources().getString(R.string.local_review_arrival_time);
                break;
            case Common.LocalRate.PROFESSIONALISM:
                textOption = context.getResources().getString(R.string.local_review_professionalism);
                break;
            case Common.LocalRate.GUIDANCE:
                textOption = context.getResources().getString(R.string.local_review_guidance);
                break;
            case Common.LocalRate.MEETING_SPOT:
                textOption = context.getResources().getString(R.string.local_review_meeting_point);
                break;
            case Common.LocalRate.ACTIVITY_DURATION:
                textOption = context.getResources().getString(R.string.local_review_activity_duration);
                break;
            case Common.LocalRate.Others:
                textOption = context.getResources().getString(R.string.local_review_other);
                break;

        }

        holder.customTextViewName.setText(textOption);
        holder.customTextViewImprove.setText(earningRates.get(position).getImproved());
        holder.customTextViewWrong.setText(earningRates.get(position).getWrong());
    }

    @Override
    public int getItemCount() {
        return earningRates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.customTextViewImprove)
        AppCompatTextView customTextViewImprove;
        @BindView(R.id.customTextViewWrong)
        AppCompatTextView customTextViewWrong;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface CallBack {
        void onSelect(Month month);
    }
}
