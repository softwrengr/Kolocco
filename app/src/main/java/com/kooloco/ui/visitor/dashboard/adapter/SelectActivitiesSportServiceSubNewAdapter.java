package com.kooloco.ui.visitor.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;
import com.kooloco.model.Service;
import com.kooloco.model.SubService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class SelectActivitiesSportServiceSubNewAdapter extends RecyclerView.Adapter<SelectActivitiesSportServiceSubNewAdapter.ViewHolder> {
    Context context;
    List<SubService> subServices;
    Service service;
    int selectPosition = -1;
    CallBack callBack;

    public SelectActivitiesSportServiceSubNewAdapter(Context context, List<SubService> subServices, Service service, int selectPosition, CallBack callBack) {
        this.context = context;
        this.subServices = subServices;
        this.service = service;
        this.callBack = callBack;
        this.selectPosition = selectPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_select_activity_service_type_sub, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.customTextViewName.setText(subServices.get(position).getName());

        if (selectPosition == position) {
            holder.customTextViewName.setSelected(true);
        } else {
            holder.customTextViewName.setSelected(false);
        }


        holder.customTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPosition = position;
                callBack.onClickItem(position,subServices.get(position), service);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return subServices.size();
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
        void onClickItem(int position,SubService subService, Service service);
    }
}
