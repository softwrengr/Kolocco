package com.kooloco.uilocal.addservice.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.model.Activities;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class SelectExpServiceAdapter extends RecyclerView.Adapter<SelectExpServiceAdapter.ViewHolder> {
    Context context;
    List<Activities> services;

    public SelectExpServiceAdapter(Context context, List<Activities> services) {
        this.context = context;
        this.services = services;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_create_exper, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.with(context).load(services.get(position).getImageUrl()).into(holder.imageViewService);
        holder.customTextViewName.setText(services.get(position).getName());
        holder.customTextViewDesc.setText(services.get(position).getDesc());

        if (services.get(position).getIsSelected().equalsIgnoreCase("1")) {
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

                Activities activities = services.get(position);

                boolean b = activities.getIsSelected().equalsIgnoreCase("1");

                activities.setIsSelected(!b ? "1" : "0");

                services.set(position, activities);

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
        @BindView(R.id.imageViewSelection)
        ImageView imageViewSelection;
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.customTextViewDesc)
        AppCompatTextView customTextViewDesc;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
