package com.kooloco.ui.notification.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.CheckEquipment;
import com.kooloco.model.CheckSportEquipment;
import com.kooloco.model.Sport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class CheckSportEquipmentsAdapter extends RecyclerView.Adapter<CheckSportEquipmentsAdapter.ViewHolder> {
    Context context;
    List<CheckEquipment> sports;
    private CallBack callBack;
    public String orderStatus = "1";

    public CheckSportEquipmentsAdapter(Context context, List<CheckEquipment> sports, String orderStatus, CallBack callBack) {
        this.context = context;
        this.sports = sports;
        this.callBack = callBack;
        this.orderStatus = orderStatus;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_check_equipments, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.checkBoxSelect.setChecked(sports.get(position).isSelect());
        holder.customTextViewName.setSelected(sports.get(position).isSelect());


  /*      holder.checkBoxSelect.setClickable(orderStatus.equalsIgnoreCase("1"));
        holder.checkBoxSelect.setAlpha(orderStatus.equalsIgnoreCase("1") ? 1f : 0.75f);

*/

        holder.checkBoxSelect.setAlpha(orderStatus.equalsIgnoreCase("1") ? 1f : 0.75f);

        holder.linearLayoutRow.setClickable(orderStatus.equalsIgnoreCase("1"));
        holder.linearLayoutRow.setAlpha(orderStatus.equalsIgnoreCase("1") ? 1f : 0.75f);
        holder.linearLayoutRow.setEnabled(orderStatus.equalsIgnoreCase("1"));


        holder.customTextViewName.setText(sports.get(position).getName());

        holder.linearLayoutRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.checkBoxSelect.setChecked(!holder.checkBoxSelect.isChecked());
                CheckEquipment sport = sports.get(position);
                sport.setSelect(holder.checkBoxSelect.isChecked());
                holder.customTextViewName.setSelected(holder.checkBoxSelect.isChecked());
                sports.set(position, sport);
                callBack.onClickSelect(sports.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return sports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.checkBoxSelect)
        CheckBox checkBoxSelect;
        @BindView(R.id.linearLayoutRow)
        LinearLayout linearLayoutRow;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {

        void onClickSelect(CheckEquipment sport);
    }

    public List<CheckEquipment> getData() {
        return sports;
    }

}
