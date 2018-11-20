package com.kooloco.ui.visitor.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.Time;
import com.kooloco.util.TimeConvertUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class SelectDateTimeAdapter extends RecyclerView.Adapter<SelectDateTimeAdapter.ViewHolder> {
    Context context;
    List<Time> times;
    int selectPosition = -1;
    CallBack callBack;

    public SelectDateTimeAdapter(Context context, List<Time> times, int selectPosition, CallBack callBack) {
        this.context = context;
        this.times = times;
        this.callBack = callBack;
        this.selectPosition = selectPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_visitor_select_date_time, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.customTextViewTimeBooked.setText(TimeConvertUtils.dateTimeConvertLocalToLocal("" + times.get(position).getTime(), "HH:mm:ss", "hh:mm a"));
        holder.customTextViewTimeDisable.setText(TimeConvertUtils.dateTimeConvertLocalToLocal("" + times.get(position).getTime(), "HH:mm:ss", "hh:mm a"));
        holder.customTextViewTimeSelected.setText(TimeConvertUtils.dateTimeConvertLocalToLocal("" + times.get(position).getTime(), "HH:mm:ss", "hh:mm a"));

        holder.customTextViewTimeSelectedStTEn.setText(TimeConvertUtils.dateTimeConvertLocalToLocal("" + times.get(position).getTime(), "HH:mm:ss", "hh:mm a"));
        if (times.get(position).getIsSelectedStTEn().equalsIgnoreCase("1")) {
            holder.linearLayoutBooked.setVisibility(View.GONE);
            holder.linearLayoutSelected.setVisibility(View.GONE);
            holder.linearLayoutDisable.setVisibility(View.GONE);
            holder.linearLayoutSelectedStTEn.setVisibility(View.VISIBLE);

            holder.customTextViewTimeSelectedStTEn.setEnabled(times.get(position).getIsDisallow().equalsIgnoreCase("0"));

        } else if (times.get(position).getIsDisable().equalsIgnoreCase("1")) {
            holder.linearLayoutBooked.setVisibility(View.VISIBLE);
            holder.linearLayoutSelected.setVisibility(View.GONE);
            holder.linearLayoutDisable.setVisibility(View.GONE);
            holder.linearLayoutSelectedStTEn.setVisibility(View.GONE);

            holder.customTextViewTimeBooked.setEnabled(times.get(position).getIsDisallow().equalsIgnoreCase("0"));

        } else if (times.get(position).getIsVisible().equalsIgnoreCase("1")) {
            holder.linearLayoutBooked.setVisibility(View.GONE);
            holder.linearLayoutSelected.setVisibility(View.GONE);
            holder.linearLayoutDisable.setVisibility(View.VISIBLE);
            holder.linearLayoutSelectedStTEn.setVisibility(View.GONE);

            holder.customTextViewTimeDisable.setEnabled(times.get(position).getIsDisallow().equalsIgnoreCase("0"));

        } else {
            holder.linearLayoutBooked.setVisibility(View.GONE);
            holder.linearLayoutSelected.setVisibility(View.VISIBLE);
            holder.linearLayoutDisable.setVisibility(View.GONE);
            holder.linearLayoutSelectedStTEn.setVisibility(View.GONE);

            holder.customTextViewTimeSelected.setEnabled(times.get(position).getIsDisallow().equalsIgnoreCase("0"));

        }


        if (selectPosition == position) {
            holder.customTextViewTimeSelected.setSelected(true);
            holder.customTextViewTimeSelected.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.drawable_select_time_selected_select));
        } else {
            holder.customTextViewTimeSelected.setSelected(false);
            holder.customTextViewTimeSelected.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.drawable_select_time_selected_un_select));
        }


        holder.customTextViewTimeSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPosition = position;
                notifyDataSetChanged();
                callBack.onClick(times.get(position), position);
            }
        });

        holder.customTextViewTimeSelectedStTEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (times.get(position).getIsDisable().equalsIgnoreCase("0") && times.get(position).getIsVisible().equalsIgnoreCase("0")) {
                    selectPosition = position;
                    notifyDataSetChanged();
                    callBack.onClick(times.get(position), position);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    public void setSelectedIndex(int position) {
        selectPosition = position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customTextViewTimeDisable)
        AppCompatTextView customTextViewTimeDisable;
        @BindView(R.id.linearLayoutDisable)
        LinearLayout linearLayoutDisable;
        @BindView(R.id.customTextViewTimeBooked)
        AppCompatTextView customTextViewTimeBooked;
        @BindView(R.id.linearLayoutBooked)
        LinearLayout linearLayoutBooked;
        @BindView(R.id.customTextViewTimeSelected)
        AppCompatTextView customTextViewTimeSelected;
        @BindView(R.id.linearLayoutSelected)
        LinearLayout linearLayoutSelected;

        @BindView(R.id.customTextViewTimeSelectedStTEn)
        AppCompatTextView customTextViewTimeSelectedStTEn;
        @BindView(R.id.linearLayoutSelectedStTEn)
        LinearLayout linearLayoutSelectedStTEn;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClick(Time time, int position);
    }
}
