package com.kooloco.uilocal.addservice.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;
import com.kooloco.model.SportActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class BottomSheetSportTypeAdapter extends RecyclerView.Adapter<BottomSheetSportTypeAdapter.ViewHolder> {
    Context context;
    List<SportActivity> sportActivities;
    private CallBack callBack;

    public BottomSheetSportTypeAdapter(Context context, List<SportActivity> sportActivities, CallBack callBack) {
        this.context = context;
        this.sportActivities = sportActivities;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_filter, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.customTextViewName.setText(sportActivities.get(position).getName());
        holder.customTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickSelect(sportActivities.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return sportActivities.size();
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

        void onClickSelect(SportActivity sportActivity);

    }
}
