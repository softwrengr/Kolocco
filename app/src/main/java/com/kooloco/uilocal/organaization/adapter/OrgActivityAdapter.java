package com.kooloco.uilocal.organaization.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;
import com.kooloco.model.Activities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class OrgActivityAdapter extends RecyclerView.Adapter<OrgActivityAdapter.ViewHolder> {
    Context context;
    List<Activities> activities;
    CallBack callBack;

    public OrgActivityAdapter(Context context, List<Activities> activities, CallBack callBack) {
        this.context = context;
        this.activities = activities;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_choose_language, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.customTextViewName.setText(activities.get(position).getName());
        holder.customTextViewName.setSelected(activities.get(position).isSelect());


        holder.customTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activities activitie = activities.get(position);
                activitie.setSelect(!activitie.isSelect());
                activities.set(position, activitie);
                callBack.onClick(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return activities.size();
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
        void onClick(int position);
    }

}
