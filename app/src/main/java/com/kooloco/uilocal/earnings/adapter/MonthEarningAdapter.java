package com.kooloco.uilocal.earnings.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;
import com.kooloco.model.Month;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class MonthEarningAdapter extends RecyclerView.Adapter<MonthEarningAdapter.ViewHolder> {
    Context context;
    List<Month> months;
    CallBack callBack;
    int select = 0;

    public MonthEarningAdapter(Context context, List<Month> months, CallBack callBack) {
        this.context = context;
        this.months = months;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_earning_month, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.customTextViewName.setSelected(select == position);
        holder.customTextViewName.setText(months.get(position).getName());
        holder.customTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (select != position) {
                    int tempPos = select;
                    select = position;
                    callBack.onSelect(months.get(position), position);
                    notifyItemChanged(position);
                    notifyItemChanged(tempPos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface CallBack {
        void onSelect(Month month, int position);
    }
}
