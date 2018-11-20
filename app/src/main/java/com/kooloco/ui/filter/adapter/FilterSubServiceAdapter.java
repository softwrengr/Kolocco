package com.kooloco.ui.filter.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;
import com.kooloco.model.SportSubActivity;
import com.kooloco.model.SubService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class FilterSubServiceAdapter extends RecyclerView.Adapter<FilterSubServiceAdapter.ViewHolder> {
    Context context;
    List<SubService> sportSubActivities;
    @BindView(R.id.customTextViewName)
    AppCompatTextView customTextViewName;
    int mainPosition;
    private CallBack callBack;


    public FilterSubServiceAdapter(Context context, List<SubService> sportSubActivities, int mainPosition, CallBack callBack) {
        this.context = context;
        this.sportSubActivities = sportSubActivities;
        this.mainPosition = mainPosition;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_filter_sub_service, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.customTextViewName.setText(sportSubActivities.get(position).getName());
        holder.customTextViewName.setSelected(sportSubActivities.get(position).isSelect());

        holder.customTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubService sportSubActivity = sportSubActivities.get(position);
                sportSubActivity.setSelect(!holder.customTextViewName.isSelected());
                holder.customTextViewName.setSelected(sportSubActivities.get(position).isSelect());
                sportSubActivities.set(position, sportSubActivity);
                callBack.onClickSelect(position, mainPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sportSubActivities.size();
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
        void onClickSelect(int position, int mainPosition);
    }
}
