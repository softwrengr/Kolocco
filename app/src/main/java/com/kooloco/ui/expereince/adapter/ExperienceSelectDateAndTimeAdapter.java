package com.kooloco.ui.expereince.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.SchedulePrice;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.util.TimeConvertUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class ExperienceSelectDateAndTimeAdapter extends RecyclerView.Adapter<ExperienceSelectDateAndTimeAdapter.ViewHolder> {
    Context context;
    List<SchedulePrice> schedulePrice;

    private CallBack callBack;


    public ExperienceSelectDateAndTimeAdapter(Context context, List<SchedulePrice> schedulePrice, CallBack callBack) {
        this.context = context;
        this.schedulePrice = schedulePrice;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.exp_select_date_time_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String time;

        if (schedulePrice.get(position).getIsMultipleDay().equalsIgnoreCase("1")) {
            time = context.getResources().getString(R.string.schdule_price_start_time) + " " + TimeConvertUtils.dateTimeConvertLocalToLocal(schedulePrice.get(position).getStartTime(), "HH:mm:ss", "hh:mm a") + " " + context.getResources().getString(R.string.schdule_and_price_for) + " " + schedulePrice.get(position).getDurationInDays() + " " + context.getResources().getString(R.string.schdule_price_days);
        } else {
            time = "" + context.getResources().getString(R.string.sch_from) + " " + TimeConvertUtils.dateTimeConvertLocalToLocal(schedulePrice.get(position).getStartTime(), "HH:mm:ss", "hh:mm a") + " " + context.getResources().getString(R.string.sch_to) + " " + TimeConvertUtils.dateTimeConvertLocalToLocal(schedulePrice.get(position).getEndTime(), "HH:mm:ss", "hh:mm a");
        }

        if (schedulePrice.get(position).getIsDisable().equalsIgnoreCase("0")) {
            holder.buttonSelect.setVisibility(View.VISIBLE);
            holder.buttonUnSelect.setVisibility(View.GONE);
        } else {
            holder.buttonUnSelect.setVisibility(View.VISIBLE);
            holder.buttonSelect.setVisibility(View.GONE);
        }

        holder.customTextViewTitle.setText(time);

        holder.textViewCurrencyType.setText(BaseActivity.currency);
        holder.textViewPrice.setText(schedulePrice.get(position).getPrice());

        holder.linearLayoutExpRoot.setSelected(false);

        holder.buttonSelect.setOnClickListener(view -> callBack.onClickSelect(schedulePrice.get(position)));
    }

    @Override
    public int getItemCount() {
        return schedulePrice.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.customTextViewTitle)
        AppCompatTextView customTextViewTitle;
        @BindView(R.id.textViewPrice)
        AppCompatTextView textViewPrice;
        @BindView(R.id.buttonSelect)
        AppCompatButton buttonSelect;
        @BindView(R.id.linearLayoutExpRoot)
        LinearLayout linearLayoutExpRoot;
        @BindView(R.id.buttonUnSelect)
        AppCompatButton buttonUnSelect;
        @BindView(R.id.textViewCurrencyType)
        AppCompatTextView textViewCurrencyType;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface CallBack {

        void onClickSelect(SchedulePrice schedulePrice);

    }


}
