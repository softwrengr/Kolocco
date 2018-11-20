package com.kooloco.uilocal.setavability.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.DisableSport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class DisableSportAdapter extends RecyclerView.Adapter<DisableSportAdapter.ViewHolder> {
    Context context;
    List<DisableSport> disableSports;
    CallBack callBack;


    public DisableSportAdapter(Context context, List<DisableSport> disableSports, CallBack callBack) {
        this.context = context;
        this.disableSports = disableSports;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_disable_sports, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.linearLayoutRow.setAlpha(disableSports.get(position).isEditable() ? 1f : 0.75f);

        holder.textViewServiceName.setSelected(disableSports.get(position).isEditable());
        holder.textViewServiceName.setText(disableSports.get(position).getName());
        holder.customTextViewStartTime1.setText(disableSports.get(position).getStartDate());
        holder.customTextViewEndTime1.setText(disableSports.get(position).getEndDate());

        holder.customTextViewEndTime1.setEnabled(disableSports.get(position).isEditable());

        holder.customTextViewStartTime1.setEnabled(disableSports.get(position).isEditable());

        holder.customTextViewStartTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickStartDate(position);
            }
        });
        holder.customTextViewEndTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickEndDate(position);
            }
        });

        holder.checkBoxNotification.setChecked(disableSports.get(position).isEditable());

        holder.checkBoxNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisableSport disableSport = disableSports.get(position);
                disableSport.setEditable(holder.checkBoxNotification.isChecked());
                disableSports.set(position, disableSport);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return disableSports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewServiceName)
        AppCompatTextView textViewServiceName;
        @BindView(R.id.checkBoxNotification)
        CheckBox checkBoxNotification;
        @BindView(R.id.customTextViewStartTime1)
        AppCompatTextView customTextViewStartTime1;
        @BindView(R.id.customTextViewEndTime1)
        AppCompatTextView customTextViewEndTime1;
        @BindView(R.id.linearLayoutRow)
        LinearLayout linearLayoutRow;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickStartDate(int position);

        void onClickEndDate(int position);
    }
}
