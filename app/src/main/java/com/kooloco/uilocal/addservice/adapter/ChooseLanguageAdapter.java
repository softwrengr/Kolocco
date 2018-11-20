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

public class ChooseLanguageAdapter extends RecyclerView.Adapter<ChooseLanguageAdapter.ViewHolder> {
    Context context;
    List<String> subServices;
    List<Integer> selectPosition;
    CallBack callBack;
    boolean isSelectSingal = false;

    public ChooseLanguageAdapter(Context context, List<String> subServices, List<Integer> selectPosition, boolean isSelectSingal, CallBack callBack) {
        this.context = context;
        this.subServices = subServices;
        this.selectPosition = selectPosition;
        this.callBack = callBack;
        this.isSelectSingal = isSelectSingal;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_choose_language, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.customTextViewName.setText(subServices.get(position));

        if (selectPosition.contains(position)) {
            holder.customTextViewName.setSelected(true);
        } else {
            holder.customTextViewName.setSelected(false);
        }


        holder.customTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelectSingal) {
                    selectPosition.clear();
                    selectPosition.add(position);
                } else {
                    if (selectPosition.contains(position)) {
                        if (selectPosition.size() != 0) {
                            try {
                                selectPosition.remove(position);
                            } catch (IndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        selectPosition.add(position);
                    }

                }

                holder.customTextViewName.postDelayed(() -> notifyDataSetChanged(), 200);
            }
        });

    }

    @Override
    public int getItemCount() {
        return subServices.size();
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
        void onClickItem(List<Integer> subService);
    }

}
