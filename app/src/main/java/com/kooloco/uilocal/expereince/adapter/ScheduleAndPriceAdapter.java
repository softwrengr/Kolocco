package com.kooloco.uilocal.expereince.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.model.SchduleDashboard;
import com.kooloco.model.SchedulePrice;
import com.kooloco.model.Time;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.util.TimeConvertUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class ScheduleAndPriceAdapter extends RecyclerView.Adapter<ScheduleAndPriceAdapter.ViewHolder> {
    Context context;
    List<SchedulePrice> schedulePrice;
    private CallBack callBack;


    public ScheduleAndPriceAdapter(Context context, List<SchedulePrice> schedulePrice, CallBack callBack) {
        this.context = context;
        this.schedulePrice = schedulePrice;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.exp_local_row_schedule_price, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String time;

        if (schedulePrice.get(position).getIsMultipleDay().equalsIgnoreCase("1")) {
            time = context.getResources().getString(R.string.schdule_price_start_time) + " " + TimeConvertUtils.dateTimeConvertLocalToLocal(schedulePrice.get(position).getStartTime(), "HH:mm:ss", "hh:mm a") + " " + context.getResources().getString(R.string.schdule_and_price_for) + " " + schedulePrice.get(position).getDurationInDays() + " " + context.getResources().getString(R.string.schdule_price_days);
        } else {
            time = "" + context.getResources().getString(R.string.sch_from) + " " + TimeConvertUtils.dateTimeConvertLocalToLocal(schedulePrice.get(position).getStartTime(), "HH:mm:ss", "hh:mm a") + " " + context.getResources().getString(R.string.sch_to) + " " + TimeConvertUtils.dateTimeConvertLocalToLocal(schedulePrice.get(position).getEndTime(), "HH:mm:ss", "hh:mm a");
        }

        holder.customTextViewTitle.setText(time);

        holder.textExpCurrency.setText(BaseActivity.currency);

        holder.textViewPrice.setText(schedulePrice.get(position).getPrice());

        holder.imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickDelete(schedulePrice.get(position));
            }
        });

        holder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickEdit(schedulePrice.get(position));
            }
        });
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
        @BindView(R.id.imageViewEdit)
        ImageView imageViewEdit;
        @BindView(R.id.imageViewCancel)
        ImageView imageViewCancel;
        @BindView(R.id.linearLayoutExpRoot)
        LinearLayout linearLayoutExpRoot;
        @BindView(R.id.textExpCurrency)
        AppCompatTextView textExpCurrency;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface CallBack {

        void onClickEdit(SchedulePrice schedulePrice);

        void onClickDelete(SchedulePrice schedulePrice);

    }


}
