package com.kooloco.uilocal.home.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class DurationStringAdapter extends RecyclerView.Adapter<DurationStringAdapter.ViewHolder> {
    Context context;
    List<String> subServices;
    CallBack callBack;
    int selectPosition = 0;

    public DurationStringAdapter(Context context, List<String> subServices, CallBack callBack) {
        this.context = context;
        this.subServices = subServices;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_duration_time, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.customTextViewName.setText(subServices.get(position)+" hour");

        if (selectPosition == position) {
            holder.customTextViewName.setSelected(true);
        } else {
            holder.customTextViewName.setSelected(false);
        }


        holder.customTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPosition = position;
                callBack.onClickItem(subServices.get(position));
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return subServices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customTextViewTime)
        AppCompatTextView customTextViewName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickItem(String time);
    }

}
