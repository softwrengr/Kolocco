package com.kooloco.ui.visitor.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;
import com.kooloco.model.Duration;
import com.kooloco.model.SubService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hlink44 on 14/9/17.
 */

public class DurationAdapter extends RecyclerView.Adapter<DurationAdapter.ViewHolder> {
    Context context;
    List<Duration> durations;
    int selectPosition = 0;

    public DurationAdapter(Context context, List<Duration> durations) {
        this.context = context;
        this.durations = durations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_duration_time, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.customTextViewTime.setText(durations.get(position).getDuration());

        if (selectPosition == position) {
            holder.customTextViewTime.setSelected(true);
        } else {
            holder.customTextViewTime.setSelected(false);
        }


        holder.customTextViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPosition = position;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return durations.size();
    }

    @OnClick(R.id.customTextViewTime)
    public void onViewClicked() {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customTextViewTime)
        AppCompatTextView customTextViewTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
