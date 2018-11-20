package com.kooloco.ui.filter.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;
import com.kooloco.model.OtherDetailsFieldsSelect;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class FilterSelectionFeildsAdapter extends RecyclerView.Adapter<FilterSelectionFeildsAdapter.ViewHolder> {
    Context context;
    List<OtherDetailsFieldsSelect> otherDetailsFieldsSelects;
    CallBack callBack;
    Map<String, OtherDetailsFieldsSelect> otherDetailsFieldsSelectMap;
    int maximum = 3;

    private boolean isDisableAll=false;

    public FilterSelectionFeildsAdapter(Context context, List<OtherDetailsFieldsSelect> otherDetailsFieldsSelects, int maximum, CallBack callBack) {
        this.context = context;
        this.otherDetailsFieldsSelects = otherDetailsFieldsSelects;
        this.callBack = callBack;
        this.maximum = maximum;
        otherDetailsFieldsSelectMap = new LinkedHashMap<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_choose_language, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.customTextViewName.setText(otherDetailsFieldsSelects.get(position).getName());

        holder.customTextViewName.setSelected(otherDetailsFieldsSelects.get(position).isSelect());

        if (otherDetailsFieldsSelects.get(position).isSelect()) {
            otherDetailsFieldsSelectMap.put(otherDetailsFieldsSelects.get(position).getId(), otherDetailsFieldsSelects.get(position));
        } else {
            otherDetailsFieldsSelectMap.remove(otherDetailsFieldsSelects.get(position).getId());
        }

        holder.customTextViewName.setClickable(!isDisableAll);
        holder.customTextViewName.setEnabled(!isDisableAll);

        holder.customTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isDisableAll){
                    return;
                }

                if (!holder.customTextViewName.isSelected()) {
                    if (otherDetailsFieldsSelectMap.size() == maximum) {
                        callBack.onClickMaximumError();
                        return;
                    }
                }

                OtherDetailsFieldsSelect language = otherDetailsFieldsSelects.get(position);
                language.setSelect(!holder.customTextViewName.isSelected());
                otherDetailsFieldsSelects.set(position, language);
                callBack.onClickItem(position);

                holder.customTextViewName.postDelayed(() -> notifyDataSetChanged(), 200);


            }
        });

    }

    @Override
    public int getItemCount() {
        return otherDetailsFieldsSelects.size();
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
        void onClickItem(int position);

        void onClickMaximumError();

    }

    public boolean isDisableAll() {
        return isDisableAll;
    }

    public void setDisableAll(boolean disableAll) {
        isDisableAll = disableAll;
    }
}

