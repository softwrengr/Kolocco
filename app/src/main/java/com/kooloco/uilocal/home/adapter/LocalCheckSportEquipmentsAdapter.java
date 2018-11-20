package com.kooloco.uilocal.home.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.CheckEquipment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class LocalCheckSportEquipmentsAdapter extends RecyclerView.Adapter<LocalCheckSportEquipmentsAdapter.ViewHolder> {
    Context context;
    List<CheckEquipment> sports;
    private String orderStatus;
    private CallBack callBack;

    public LocalCheckSportEquipmentsAdapter(Context context, List<CheckEquipment> sports, String orderStaus, CallBack callBack) {
        this.context = context;
        this.sports = sports;
        this.orderStatus = orderStaus;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_new_check_equipments, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.customTextViewName.setText(sports.get(position).getName());

        holder.textViewStatus.setText(sports.get(position).isSelect() ? context.getString(R.string.equ_checked) : context.getString(R.string.equ_unchecked));
        holder.textViewStatus.setTextColor(sports.get(position).isSelect() ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.yellow));


        holder.imageViewCancel.setVisibility((sports.get(position).getIsNew().equalsIgnoreCase("1") && orderStatus.equalsIgnoreCase("1")) ? View.VISIBLE : View.INVISIBLE);

        holder.imageViewCancel.setOnClickListener(v -> {

            callBack.onClickDelete(sports.get(position), position);

        });

    }

    @Override
    public int getItemCount() {
        return sports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.textViewStatus)
        AppCompatTextView textViewStatus;
        @BindView(R.id.linearLayoutRow)
        LinearLayout linearLayoutRow;
        @BindView(R.id.imageViewCancel)
        ImageView imageViewCancel;
        @BindView(R.id.frameLayoutClose)
        FrameLayout frameLayoutClose;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {

        void onClickDelete(CheckEquipment sport, int pos);
    }

    public List<CheckEquipment> getData() {
        return sports;
    }

}
