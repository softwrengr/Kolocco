package com.kooloco.uilocal.addservice.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.model.SportSubActivity;
import com.kooloco.ui.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class ChooseSportSubActivityAdapter extends RecyclerView.Adapter<ChooseSportSubActivityAdapter.ViewHolder> {
    Context context;
    List<SportSubActivity> sportSubActivities;
    CallBack callBack;

    public ChooseSportSubActivityAdapter(Context context, List<SportSubActivity> sportSubActivities, CallBack callBack) {
        this.context = context;
        this.sportSubActivities = sportSubActivities;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_check_sport_activities, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.customTextViewName.setText(sportSubActivities.get(position).getName() + " " + context.getResources().getString(R.string.my_c_d));
        holder.customTextViewName.setSelected(sportSubActivities.get(position).isSelect());

        holder.editTextValueNoExpand.setSelected(sportSubActivities.get(position).isSelect());
        holder.editTextValueNoExpand.setEnabled(sportSubActivities.get(position).isSelect());
        holder.editTextValueNoExpand.setText(sportSubActivities.get(position).getPrice());

        holder.imageViewGroup.setVisibility(sportSubActivities.get(position).isSelect() && sportSubActivities.get(position).isGroup() ? View.VISIBLE : View.GONE);

        holder.customTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SportSubActivity sportSubActivity = sportSubActivities.get(position);
                holder.editTextValueNoExpand.setSelected(!holder.editTextValueNoExpand.isSelected());
                holder.editTextValueNoExpand.setEnabled(holder.editTextValueNoExpand.isSelected());
                sportSubActivity.setSelect(holder.editTextValueNoExpand.isSelected());
                holder.customTextViewName.setSelected(sportSubActivities.get(position).isSelect());
                sportSubActivities.set(position, sportSubActivity);
                callBack.onSelect(sportSubActivities.get(position), position, new BaseFragment.CallBackPriceSportSubActivity() {
                    @Override
                    public void onCall(SportSubActivity sportActivity, int positionSub) {
                        sportSubActivities.set(positionSub, sportActivity);
                        notifyItemChanged(positionSub);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return sportSubActivities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.editTextValueNoExpand)
        AppCompatEditText editTextValueNoExpand;
        @BindView(R.id.imageViewGroup)
        ImageView imageViewGroup;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface CallBack {
        void onSelect(SportSubActivity sportSubActivity, int subPosition, BaseFragment.CallBackPriceSportSubActivity callBackPriceSportSubActivity);
    }

}
