package com.kooloco.uilocal.addservice.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kooloco.R;
import com.kooloco.model.AddImages;
import com.kooloco.model.SportActivity;
import com.kooloco.model.SportSubActivity;
import com.kooloco.ui.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class ChooseSportActivityAdapter extends RecyclerView.Adapter<ChooseSportActivityAdapter.ViewHolder> {
    Context context;
    List<SportActivity> sportActivities;
    CallBack callBack;

    public ChooseSportActivityAdapter(Context context, List<SportActivity> sportActivities, CallBack callBack) {
        this.context = context;
        this.sportActivities = sportActivities;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_choose_sport_activites, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (sportActivities.get(position).getPrice() != null)
            holder.editTextValueNoExpand.setText(sportActivities.get(position).getPrice());

        holder.linearLayoutNoExpand.setVisibility(!sportActivities.get(position).isExpand() ? View.VISIBLE : View.GONE);
        holder.linearLayoutExpand.setVisibility(sportActivities.get(position).isExpand() ? View.VISIBLE : View.GONE);

        holder.imageViewSelectNoExpand.setVisibility(sportActivities.get(position).isSelect() && sportActivities.get(position).isGroup() ? View.VISIBLE : View.GONE);

        holder.imageViewSelectExpand.setVisibility(sportActivities.get(position).isSelect() && sportActivities.get(position).isGroup() ? View.VISIBLE : View.GONE);

        holder.customEditTextNameNoExpand.setText(sportActivities.get(position).getName() + " " + context.getResources().getString(R.string.my_c_d));
        holder.customEditTextNameExpand.setText(sportActivities.get(position).getName());

        holder.editTextValueNoExpand.setEnabled(sportActivities.get(position).isSelect());
        holder.editTextValueNoExpand.setSelected(sportActivities.get(position).isSelect());

        holder.customTextViewSelectExpand.setEnabled(sportActivities.get(position).isSelect());

        holder.customTextViewSelectExpand.setSelected(true);
        holder.linearLayoutLocalSpeakLanguage.setVisibility(View.GONE);

        ///

        int tempD = 0;
        for (SportSubActivity sportSubActivity1 : sportActivities.get(position).getSportSubActivities()) {
            if (sportSubActivity1.isSelect()) {
                tempD = tempD + 1;
            }
        }
        holder.customTextViewExpand.setText(tempD + " Selected");

        holder.customTextViewExpand.setVisibility((tempD == 0) ? View.INVISIBLE : View.VISIBLE);


        holder.linearLayoutExpandNewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sportActivities.get(position).isSelect()) {
                    holder.customTextViewSelectExpand.setSelected(!holder.customTextViewSelectExpand.isSelected());
                    holder.linearLayoutLocalSpeakLanguage.setVisibility(!holder.customTextViewSelectExpand.isSelected() ? View.VISIBLE : View.GONE);
                }
            }
        });

        holder.linearLayoutExpandNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sportActivities.get(position).isSelect()) {
                    holder.customTextViewSelectExpand.setSelected(!holder.customTextViewSelectExpand.isSelected());
                    holder.linearLayoutLocalSpeakLanguage.setVisibility(!holder.customTextViewSelectExpand.isSelected() ? View.VISIBLE : View.GONE);
                }
            }
        });


        if (sportActivities.get(position).isOpen()) {
            holder.customTextViewSelectExpand.performClick();
        }

        holder.customTextViewSelectExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.customTextViewSelectExpand.setSelected(!holder.customTextViewSelectExpand.isSelected());
                holder.linearLayoutLocalSpeakLanguage.setVisibility(!holder.customTextViewSelectExpand.isSelected() ? View.VISIBLE : View.GONE);
            }
        });

        holder.recyclerExpand.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.recyclerExpand.setAdapter(new ChooseSportSubActivityAdapter(context, sportActivities.get(position).getSportSubActivities(), new ChooseSportSubActivityAdapter.CallBack() {
            @Override
            public void onSelect(SportSubActivity sportSubActivity, int subPosition, BaseFragment.CallBackPriceSportSubActivity callBackPriceSportSubActivity) {

                if (sportSubActivity.isSelect()) {
                    callBack.onClickSub(sportActivities.get(position), sportSubActivity, true, position, subPosition, callBackPriceSportSubActivity);
                } else {
                    boolean isSelect = false;
                    for (SportSubActivity sportSubActivity1 : sportActivities.get(position).getSportSubActivities()) {
                        if (sportSubActivity1.isSelect()) {
                            isSelect = true;
                            break;
                        }
                    }

                    callBack.onClickSub(sportActivities.get(position), sportSubActivity, false, position, subPosition, callBackPriceSportSubActivity);
                }

                int temp = 0;
                for (SportSubActivity sportSubActivity1 : sportActivities.get(position).getSportSubActivities()) {
                    if (sportSubActivity1.isSelect()) {
                        temp = temp + 1;
                    }
                }

                holder.customTextViewExpand.setText(temp + " Selected");

                holder.customTextViewExpand.setVisibility((temp == 0) ? View.INVISIBLE : View.VISIBLE);


            }
        }));


        holder.checkboxExpand.setChecked(sportActivities.get(position).isSelect());
        holder.checkBoxNoExpand.setChecked(sportActivities.get(position).isSelect());

        holder.checkboxExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SportActivity sportActivity = sportActivities.get(position);
                sportActivity.setSelect(holder.checkboxExpand.isChecked());
                sportActivities.set(position, sportActivity);

                if (!holder.checkboxExpand.isChecked()) {
                    // holder.imageViewSelectNoExpand.setVisibility(View.INVISIBLE);
                    callBack.onClick(sportActivities.get(position), false, position);
                }
                notifyItemChanged(position);


            }
        });

        holder.checkBoxNoExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SportActivity sportActivity = sportActivities.get(position);
                sportActivity.setSelect(holder.checkBoxNoExpand.isChecked());
                sportActivities.set(position, sportActivity);

                if (sportActivities.get(position).isSelect()) {
                    callBack.onClick(sportActivities.get(position), true, position);
                    //holder.imageViewSelectNoExpand.setVisibility(View.VISIBLE);
                } else {
                    // holder.imageViewSelectNoExpand.setVisibility(View.INVISIBLE);
                    callBack.onClick(sportActivities.get(position), false, position);
                }

                notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sportActivities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkBoxNoExpand)
        AppCompatCheckBox checkBoxNoExpand;
        @BindView(R.id.customTextViewSelectValueNoExpand)
        AppCompatTextView customTextViewSelectValueNoExpand;
        @BindView(R.id.customEditTextNameNoExpand)
        AppCompatEditText customEditTextNameNoExpand;
        @BindView(R.id.imageViewSelectNoExpand)
        ImageView imageViewSelectNoExpand;
        @BindView(R.id.editTextValueNoExpand)
        AppCompatEditText editTextValueNoExpand;
        @BindView(R.id.linearLayoutNoExpand)
        LinearLayout linearLayoutNoExpand;
        @BindView(R.id.checkboxExpand)
        AppCompatCheckBox checkboxExpand;
        @BindView(R.id.customTextViewExpand)
        AppCompatTextView customTextViewExpand;
        @BindView(R.id.customEditTextNameExpand)
        AppCompatTextView customEditTextNameExpand;
        @BindView(R.id.imageViewSelectExpand)
        ImageView imageViewSelectExpand;
        @BindView(R.id.customTextViewSelectExpand)
        TextView customTextViewSelectExpand;
        @BindView(R.id.recyclerExpand)
        RecyclerView recyclerExpand;
        @BindView(R.id.linearLayoutLocalSpeakLanguage)
        LinearLayout linearLayoutLocalSpeakLanguage;
        @BindView(R.id.linearLayoutExpand)
        LinearLayout linearLayoutExpand;

        @BindView(R.id.linearLayoutExpandNew)
        LinearLayout linearLayoutExpandNew;

        @BindView(R.id.linearLayoutExpandNewMain)
        LinearLayout linearLayoutExpandNewMain;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface CallBack {
        void onClick(SportActivity sportActivity, boolean isShowDialog, int position);

        void onClickSub(SportActivity sportActivity, SportSubActivity sportSubActivity, boolean isShowDialog, int position, int subPosition, BaseFragment.CallBackPriceSportSubActivity callBackPriceSportSubActivity);
    }
}
