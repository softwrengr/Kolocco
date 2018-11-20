package com.kooloco.uilocal.addservice.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;
import com.kooloco.model.Language;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class ChooseLanguageNewAdapter extends RecyclerView.Adapter<ChooseLanguageNewAdapter.ViewHolder> {
    Context context;
    List<Language> languages;
    CallBack callBack;
    boolean isSelectSingal = false;

    public ChooseLanguageNewAdapter(Context context, List<Language> languages, boolean isSelectSingal, CallBack callBack) {
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
        holder.customTextViewName.setText(languages.get(position).getName());

        holder.customTextViewName.setSelected(languages.get(position).isSelect());


        holder.customTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelectSingal) {

                    for (int i = 0; i < languages.size(); i++) {
                        Language language = languages.get(i);

                        if (position == i) {
                            language.setSelect(!holder.customTextViewName.isSelected());
                        } else {
                            language.setSelect(false);
                        }

                        languages.set(i, language);
                    }

                } else {
                    Language language = languages.get(position);
                    language.setSelect(!holder.customTextViewName.isSelected());
                    languages.set(position, language);
                }
                callBack.onClickItem();
                holder.customTextViewName.postDelayed(() -> notifyDataSetChanged(), 200);
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
        void onClickItem();
    }
}

