package com.kooloco.ui.profile.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;
import com.kooloco.model.Currency;
import com.kooloco.model.Language;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class CurrencySelectAdapter extends RecyclerView.Adapter<CurrencySelectAdapter.ViewHolder> {
    Context context;
    List<Currency> languages;
    CallBack callBack;
    boolean isSelectSingal = false;

    int selectPosition = 0;

    public CurrencySelectAdapter(Context context, List<Currency> languages, boolean isSelectSingal, CallBack callBack) {
        this.context = context;
        this.languages = languages;
        this.callBack = callBack;
        this.isSelectSingal = isSelectSingal;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_choose_language, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.customTextViewName.setText(languages.get(position).getCurrency());

        holder.customTextViewName.setSelected(languages.get(position).getIsSelected().equalsIgnoreCase("1"));

        if (languages.get(position).getIsSelected().equalsIgnoreCase("1")) {
            selectPosition = position;
        }

        holder.customTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != selectPosition) {

                    int tempSelectPosition = selectPosition;

                    selectPosition = position;

                    Currency currencyNew = languages.get(tempSelectPosition);
                    currencyNew.setIsSelected("0");
                    languages.set(tempSelectPosition, currencyNew);


                    Currency currency = languages.get(position);
                    currency.setIsSelected((!holder.customTextViewName.isSelected()) ? "1" : "0");
                    languages.set(position, currency);

                    notifyItemChanged(tempSelectPosition);

                    notifyItemChanged(selectPosition);

                    callBack.onClickItem(languages.get(position));
                    holder.customTextViewName.postDelayed(() -> notifyDataSetChanged(), 200);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return languages.size();
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
        void onClickItem(Currency currency);
    }
}

