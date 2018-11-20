package com.kooloco.uilocal.addservice.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.model.Equipment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class CheckSportEquipmentsSubAdapter extends RecyclerView.Adapter<CheckSportEquipmentsSubAdapter.ViewHolder> {
    Context context;
    List<Equipment> equipments;
    private CallBack callBack;

    public CheckSportEquipmentsSubAdapter(Context context, List<Equipment> equipments, CallBack callBack) {
        this.context = context;
        this.equipments = equipments;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_choose_sport_equipment_sub, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.customTextViewName.setSelected((equipments.get(position).getIsSelected().equalsIgnoreCase("1")));

        holder.customTextViewNameNew.setText(equipments.get(position).getName());

        holder.customTextViewNameNew.setSelected((equipments.get(position).getIsSelected().equalsIgnoreCase("1")));

        holder.customTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.customTextViewName.setSelected(!holder.customTextViewName.isSelected());

                holder.customTextViewNameNew.setSelected(!holder.customTextViewNameNew.isSelected());

                Equipment equipment = equipments.get(position);

                equipment.setIsSelected(holder.customTextViewName.isSelected() ? "1" : "0");

                equipments.set(position, equipment);

                callBack.onClickSelect(position, equipment);
            }
        });

        holder.imageViewClose.setVisibility((equipments.get(position).getIsInbuilt().equalsIgnoreCase("1")) ? View.GONE : View.VISIBLE);

        holder.imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickDelete(position, equipments.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return equipments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.imageViewClose)
        ImageView imageViewClose;
        @BindView(R.id.customTextViewNameNew)
        AppCompatTextView customTextViewNameNew;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickSelect(int pos, Equipment equipment);

        void onClickDelete(int pos, Equipment equipment);
    }

    public List<Equipment> getData() {
        return equipments;
    }

}
