package com.kooloco.uilocal.earnings.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.AllMonth;
import com.kooloco.ui.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class EarningAllMonthAdapter extends RecyclerView.Adapter<EarningAllMonthAdapter.ViewHolder> {
    Context context;
    List<AllMonth> allMonths;
    private CallBack callBack;

    public EarningAllMonthAdapter(Context context, List<AllMonth> allMonths, CallBack callBack) {
        this.context = context;
        this.allMonths = allMonths;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_earning_all_month, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.customTextViewMonthName.setText(allMonths.get(position).getMonthName());
        holder.customTextViewPrice.setText(BaseActivity.currency + " " + allMonths.get(position).getPrice());
        holder.linearLayoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickItem(allMonths.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allMonths.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customTextViewMonthName)
        AppCompatTextView customTextViewMonthName;
        @BindView(R.id.customTextViewPrice)
        AppCompatTextView customTextViewPrice;
        @BindView(R.id.linearLayoutContent)
        LinearLayout linearLayoutContent;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.onClickItem(allMonths.get(getAdapterPosition()),getAdapterPosition());
                }
            });
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {

        void onClickItem(AllMonth allMonth,int pos);

    }
}
