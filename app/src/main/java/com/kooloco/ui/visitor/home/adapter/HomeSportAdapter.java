package com.kooloco.ui.visitor.home.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.kooloco.R;
import com.kooloco.model.Service;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class HomeSportAdapter extends RecyclerView.Adapter<HomeSportAdapter.ViewHolder> {
    Context context;
    List<Service> services;
    private CallBack callBack;

    public HomeSportAdapter(Context context, List<Service> services, CallBack callBack) {
        this.context = context;
        this.services = services;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_new_row_sport, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Picasso.with(context).load(services.get(position).getServiceImage()).placeholder(R.drawable.place).error(R.drawable.place).into(holder.imageViewService);
        holder.customTextViewName.setText(services.get(position).getName());

        holder.viewFast.setVisibility((position == 0) ? View.VISIBLE : View.GONE);

        holder.linearLayoutExpRoot.setOnClickListener(view -> {
            callBack.onClickItem(services.get(position), position);
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
        @BindView(R.id.linearLayoutExpRoot)
        LinearLayout linearLayoutExpRoot;
        @BindView(R.id.viewFast)
        View viewFast;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickItem(Service service, int position);
    }


}
