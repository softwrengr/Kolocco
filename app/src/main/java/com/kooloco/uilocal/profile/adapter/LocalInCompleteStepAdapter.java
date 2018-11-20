package com.kooloco.uilocal.profile.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.ProfileStatus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class LocalInCompleteStepAdapter extends RecyclerView.Adapter<LocalInCompleteStepAdapter.ViewHolder> {
    Context context;
    List<ProfileStatus> profileStatuses;
    CallBack callBack;

    public LocalInCompleteStepAdapter(Context context, List<ProfileStatus> profileStatuses, CallBack callBack) {
        this.context = context;
        this.profileStatuses = profileStatuses;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_step_complete, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.customTextViewName.setText(profileStatuses.get(position).getTitle());
        holder.customTextViewName.setSelected(profileStatuses.get(position).getIsComplete().equalsIgnoreCase("1"));
        holder.linearLayoutRow.setOnClickListener(view -> callBack.onClickItem(profileStatuses.get(position)));

        holder.imageViewIndicator.setVisibility(profileStatuses.get(position).getIsComplete().equalsIgnoreCase("0")?View.VISIBLE:View.GONE);
        holder.imageViewIndicatorYes.setVisibility(profileStatuses.get(position).getIsComplete().equalsIgnoreCase("1")?View.VISIBLE:View.GONE);

    }

    @Override
    public int getItemCount() {
        return profileStatuses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.imageViewIndicator)
        AppCompatImageView imageViewIndicator;
        @BindView(R.id.linearLayoutRow)
        LinearLayout linearLayoutRow;
        @BindView(R.id.imageViewIndicatorYes)
        AppCompatImageView imageViewIndicatorYes;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickItem(ProfileStatus profileStatus);
    }

}
