package com.kooloco.uilocal.expereince.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.Service;
import com.kooloco.model.SportActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class ExperienceSportAdapter extends RecyclerView.Adapter<ExperienceSportAdapter.ViewHolder> {
    Context context;
    List<SportActivity> services;
    private CallBack callBack;
    int selectPosition = -1;

    public ExperienceSportAdapter(Context context, List<SportActivity> services, int selectPosition, CallBack callBack) {
        this.context = context;
        this.services = services;
        this.callBack = callBack;
        this.selectPosition = selectPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_select_activity_service_new, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (selectPosition == -1) {
            holder.imageViewService.setAlpha(1.0f);
            holder.customTextViewName.setAlpha(1.0f);
        } else {
            holder.imageViewService.setAlpha((selectPosition == position) ? 1.0f : 0.5f);
            holder.customTextViewName.setAlpha((selectPosition == position) ? 1.0f : 0.5f);
        }


/*        if (services.get(position).getSportSubActivities() == null) {
            services.get(position).setSportSubActivities(new ArrayList<>());
        }*/

        holder.imageViewExpand.setVisibility((services.get(position).getSportSubActivities().size() != 0) ? View.VISIBLE : View.INVISIBLE);

        holder.linearLayoutLast.setVisibility((position == (services.size() - 1)) ? View.VISIBLE : View.GONE);
        holder.linearLayoutFest.setVisibility((position == 0) ? View.VISIBLE : View.GONE);


        Picasso.with(context).load(services.get(position).getIcon()).into(holder.imageViewService);
        holder.customTextViewName.setText(services.get(position).getName());
        holder.imageViewTick.setVisibility((selectPosition == position) ? View.VISIBLE : View.GONE);

        holder.linearLayoutRow.setOnClickListener(view -> {
            selectPosition = position;
/*
            notifyDataSetChanged();
*/
            callBack.onClickPosition(position, services.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.linearLayoutFest)
        LinearLayout linearLayoutFest;
        @BindView(R.id.imageViewService)
        ImageView imageViewService;
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.linearLayoutRow)
        LinearLayout linearLayoutRow;
        @BindView(R.id.imageViewTick)
        ImageView imageViewTick;
        @BindView(R.id.linearLayoutLast)
        LinearLayout linearLayoutLast;
        @BindView(R.id.imageViewExpand)
        ImageView imageViewExpand;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickPosition(int position, SportActivity service);
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }
}
