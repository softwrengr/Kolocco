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
import com.kooloco.model.ServiceType;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class DashboardServiceTypeAdapter extends RecyclerView.Adapter<DashboardServiceTypeAdapter.ViewHolder> {
    Context context;
    private List<ServiceType> serviceTypes;
    int selectPosition = 0;
    boolean isVisable = false;
    CallBack callBack;

    public DashboardServiceTypeAdapter(Context context, List<ServiceType> serviceTypes, boolean isVisable, CallBack callBack) {
        this.context = context;
        this.serviceTypes = serviceTypes;
        this.isVisable = isVisable;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_dashboard_service_type, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

/*

        if (serviceTypes.get(position).getServiceName().equalsIgnoreCase("Discovery")) {
            Picasso.with(context).load(R.drawable.session_new).into(holder.imageViewService);
        }

        if (serviceTypes.get(position).getServiceName().equalsIgnoreCase("lesson")) {
            Picasso.with(context).load(R.drawable.lesson_new).into(holder.imageViewService);
        }

        if (serviceTypes.get(position).getServiceName().equalsIgnoreCase("session")) {
            Picasso.with(context).load(R.drawable.discovery_new).into(holder.imageViewService);
        }
*/


        Picasso.with(context).load(serviceTypes.get(position).getImageUrlTwo()).into(holder.imageViewService);

        holder.customTextViewServicePrice.setText(serviceTypes.get(position).getServicePrice());
        holder.customTextViewAs.setText(serviceTypes.get(position).getServicePriceAs());
        holder.customTextViewServiceType.setText("" + serviceTypes.get(position).getServiceName() + "");

        //holder.customTextViewServiceDesc.setText(serviceTypes.get(position).getDesc());

        holder.imageViewSelection.setVisibility((position == selectPosition) ? View.VISIBLE : View.GONE);

        if (isVisable) {
            holder.customTextViewServiceType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.customTextViewServicePrice.performClick();
                }
            });
            holder.customTextViewAs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.customTextViewServicePrice.performClick();
                }
            });
            holder.imageViewService.setOnClickListener(view -> holder.customTextViewServicePrice.performClick());

            holder.customTextViewServicePrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectPosition = position;
                    callBack.onClick(position,serviceTypes.get(position));
                    notifyDataSetChanged();
                }
            });
        }
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
        @BindView(R.id.customTextViewServiceDesc)
        AppCompatTextView customTextViewServiceDesc;
        @BindView(R.id.imageViewService)
        ImageView imageViewService;
        @BindView(R.id.imageViewSelection)
        ImageView imageViewSelection;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClick(int pos, ServiceType activities);
    }
}
