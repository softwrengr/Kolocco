package com.kooloco.ui.visitor.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.Service;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class DashboardServiceNewAdapter extends RecyclerView.Adapter<DashboardServiceNewAdapter.ViewHolder> {
    Context context;
    List<Service> services;
    int selectPosition = 0;

    CallBack callBack;

    public DashboardServiceNewAdapter(Context context, List<Service> services, CallBack callBack) {
        this.context = context;
        this.services = services;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_dashboard_service_image_new, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.with(context).load(services.get(position).getServiceImage()).into(holder.imageViewService);
        if (selectPosition == position) {
            holder.imageViewSelection.setVisibility(View.VISIBLE);
        } else {
            holder.imageViewSelection.setVisibility(View.GONE);
        }

        holder.linearLayoutRow.setOnClickListener(view -> {
            holder.imageViewService.performClick();
        });

        holder.imageViewService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPosition = position;
                notifyDataSetChanged();
                callBack.onClick(services.get(position));
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
        @BindView(R.id.imageViewSelection)
        ImageView imageViewSelection;
        @BindView(R.id.linearLayoutRow)
        LinearLayout linearLayoutRow;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClick(Service service);
    }
}
