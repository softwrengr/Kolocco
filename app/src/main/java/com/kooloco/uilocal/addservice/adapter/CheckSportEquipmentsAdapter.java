package com.kooloco.uilocal.addservice.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.Equipment;
import com.kooloco.model.EquipmentResponse;

import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by hlink44 on 14/9/17.
 */

public class CheckSportEquipmentsAdapter extends RecyclerView.Adapter<CheckSportEquipmentsAdapter.ViewHolder> {
    Context context;
    List<EquipmentResponse> equipmentResponses;
    private CallBack callBack;

    public CheckSportEquipmentsAdapter(Context context, List<EquipmentResponse> equipmentResponses, CallBack callBack) {
        this.context = context;
        this.equipmentResponses = equipmentResponses;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_choose_sport_equipments, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.customTextServiceName.setText(equipmentResponses.get(position).getName());


        int temp = 0;
        for (Equipment equipment : equipmentResponses.get(position).getEquipments()) {
            if (equipment.getIsSelected().equalsIgnoreCase("1")) {
                temp = temp + 1;
            }
        }
        holder.customTextCount.setText("(" + temp +" "+ context.getResources().getString(R.string.selected)+")");

        holder.recyclerEquipmentType.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        Observable.fromIterable(equipmentResponses.get(position).getEquipments()).sorted(new Comparator<Equipment>() {
            @Override
            public int compare(Equipment equipment, Equipment t1) {
                return equipment.getName().compareToIgnoreCase(t1.getName());
            }
        }).toList().subscribe(equipment -> {

            holder.recyclerEquipmentType.setAdapter(new CheckSportEquipmentsSubAdapter(context, equipment, new CheckSportEquipmentsSubAdapter.CallBack() {
                @Override
                public void onClickSelect(int pos, Equipment equipmentTemp) {
                    int temp = 0;
                    for (Equipment equipment : equipmentResponses.get(position).getEquipments()) {
                        if (equipment.getIsSelected().equalsIgnoreCase("1")) {
                            temp = temp + 1;
                        }
                    }
                    holder.customTextCount.setText("(" + temp +" "+ context.getResources().getString(R.string.selected)+")");

                    callBack.onSelect(equipmentTemp);
                    holder.recyclerEquipmentType.scrollToPosition(pos);

                }

                @Override
                public void onClickDelete(int pos, Equipment equipment) {
                    callBack.onDelete(equipment);
                }
            }));
        });


        holder.imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickAdd(equipmentResponses.get(position), "");
            }
        });

        holder.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.editTextEquipmentName.getText().toString().isEmpty()) {
                    callBack.onShowErrorMessage(context.getResources().getString(R.string.val_add_other_equipment));
                } else {
                    callBack.onClickAdd(equipmentResponses.get(position), holder.editTextEquipmentName.getText().toString());
                    holder.editTextEquipmentName.setText("");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return equipmentResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customTextServiceName)
        AppCompatTextView customTextServiceName;
        @BindView(R.id.customTextCount)
        AppCompatTextView customTextCount;
        @BindView(R.id.linearLayoutSelectAll)
        LinearLayout linearLayoutSelectAll;
        @BindView(R.id.recyclerEquipmentType)
        RecyclerView recyclerEquipmentType;
        @BindView(R.id.imageViewAdd)
        ImageView imageViewAdd;
        @BindView(R.id.editTextEquipmentName)
        AppCompatEditText editTextEquipmentName;
        @BindView(R.id.buttonAdd)
        AppCompatButton buttonAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickAdd(EquipmentResponse equipmentResponse, String text);

        void onShowErrorMessage(String message);

        void onSelect(Equipment equipment);

        void onDelete(Equipment equipment);

    }

}
