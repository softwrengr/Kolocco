package com.kooloco.uilocal.expereince.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.SchedulePrice;
import com.kooloco.util.TimeConvertUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class ScheduleAndPriceDayAdapter extends RecyclerView.Adapter<ScheduleAndPriceDayAdapter.ViewHolder> {
    Context context;
    List<SchedulePrice> schedulePrice;
    private CallBack callBack;
    private List<String> currentDayName;
    private int selectPosition = 0;


    public ScheduleAndPriceDayAdapter(Context context, List<SchedulePrice> schedulePrice, List<String> currentDayName, int selectPosition, CallBack callBack) {
        this.context = context;
        this.schedulePrice = schedulePrice;
        this.currentDayName = currentDayName;
        this.selectPosition = selectPosition;
        this.callBack = callBack;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.exp_local_row_schedule_price_day, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.customTextViewDayCurrent.setText(schedulePrice.get(position).getDay().substring(0, 1).toUpperCase());
        holder.customTextViewDayNotCurrent.setText(schedulePrice.get(position).getDay().substring(0, 1).toUpperCase());


        boolean isOk = currentDayName.contains(TimeConvertUtils.dateTimeConvertLocalToLocalDay(schedulePrice.get(position).getDay(), "EEEE", "EEE"));


        holder.customTextViewDayCurrent.setVisibility(isOk ? View.VISIBLE : View.GONE);
        holder.customTextViewDayNotCurrent.setVisibility(isOk ? View.GONE : View.VISIBLE);

        holder.customTextViewDayCurrent.setSelected((selectPosition == position));
        holder.customTextViewDayNotCurrent.setSelected((selectPosition == position));

        holder.customTextViewDayNotCurrent.setOnClickListener(view -> {
            holder.customTextViewDayCurrent.performClick();
            selectPosition = position;
            notifyDataSetChanged();
        });

        holder.customTextViewDayCurrent.setOnClickListener(view -> {
            callBack.onSelect(schedulePrice.get(position), position);
            selectPosition = position;
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return schedulePrice.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.customTextViewDayCurrent)
        AppCompatTextView customTextViewDayCurrent;
        @BindView(R.id.customTextViewDayNotCurrent)
        AppCompatTextView customTextViewDayNotCurrent;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface CallBack {
        void onSelect(SchedulePrice schedulePrice, int position);
    }


}
