package com.kooloco.ui.visitor.home.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;
import com.kooloco.model.ServiceType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class HomeServiceTypeAdapter extends RecyclerView.Adapter<HomeServiceTypeAdapter.ViewHolder> {
    Context context;

    private List<ServiceType> serviceTypes;

    public HomeServiceTypeAdapter(Context context, List<ServiceType> serviceTypes) {
        this.context = context;
        this.serviceTypes = serviceTypes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_home_service_type, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.customTextViewServicePrice.setText(serviceTypes.get(position).getServicePrice());
        holder.customTextViewAs.setText(serviceTypes.get(position).getServicePriceAs());
        holder.customTextViewServiceType.setText(serviceTypes.get(position).getServiceName());
    }

    @Override
    public int getItemCount() {
        return serviceTypes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customTextViewServicePrice)
        AppCompatTextView customTextViewServicePrice;
        @BindView(R.id.customTextViewServiceType)
        AppCompatTextView customTextViewServiceType;
        @BindView(R.id.customTextViewAs)
        AppCompatTextView customTextViewAs;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
