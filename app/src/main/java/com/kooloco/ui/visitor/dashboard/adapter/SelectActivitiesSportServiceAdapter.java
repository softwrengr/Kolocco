package com.kooloco.ui.visitor.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.model.Activities;
import com.kooloco.model.Service;
import com.kooloco.model.SubService;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class SelectActivitiesSportServiceAdapter extends RecyclerView.Adapter<SelectActivitiesSportServiceAdapter.ViewHolder> {
    Context context;
    List<Service> services;
    int selectPosition = -1;
    CallBack callBack;

    public SelectActivitiesSportServiceAdapter(Context context, List<Service> services, CallBack callBack) {
        this.context = context;
        this.services = services;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_select_activity_service_type, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.with(context).load(services.get(position).getServiceImage()).into(holder.imageViewService);
        holder.customTextViewName.setText(services.get(position).getName());

        if (selectPosition == position) {
            holder.customTextViewName.setSelected(true);
            holder.imageViewSelection.setVisibility(View.VISIBLE);
        } else {
            holder.customTextViewName.setSelected(false);
            holder.imageViewSelection.setVisibility(View.GONE);
        }

        holder.customTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imageViewService.performClick();
            }
        });

        holder.imageViewService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPosition = position;
                callBack.onClickItem(position, services.get(position));
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewService)
        ImageView imageViewService;
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.imageViewSelection)
        ImageView imageViewSelection;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickItem(int position, Service service);
    }

    public void setSelectPosition(int position) {
        selectPosition = position;
    }
}
