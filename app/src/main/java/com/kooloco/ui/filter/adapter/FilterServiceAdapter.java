package com.kooloco.ui.filter.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.Service;
import com.kooloco.model.SportActivity;
import com.kooloco.model.SportSubActivity;
import com.kooloco.model.SubService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class FilterServiceAdapter extends RecyclerView.Adapter<FilterServiceAdapter.ViewHolder> {
    Context context;
    List<Service> sportActivities;
    private CallBack callBack;

    int selectPosition = -1;

    public FilterServiceAdapter(Context context, List<Service> sportActivities, CallBack callBack) {
        this.context = context;
        this.sportActivities = sportActivities;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_filter_service, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.linearLayoutSingle.setVisibility(!sportActivities.get(position).getIsExpandable().equalsIgnoreCase("1") ? View.VISIBLE : View.GONE);
        holder.linearLayoutExpand.setVisibility(sportActivities.get(position).getIsExpandable().equalsIgnoreCase("1") ? View.VISIBLE : View.GONE);

        holder.customTextViewNameSingle.setText(sportActivities.get(position).getName());
        holder.customTextViewNameExpand.setText(sportActivities.get(position).getName());

        holder.customTextViewNameExpand.setSelected((selectPosition == position));

        holder.customTextViewNameExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectPosition == position) {
                    selectPosition = -1;
                    notifyDataSetChanged();
                    return;
                }
                selectPosition = position;
                notifyDataSetChanged();
            }
        });

        holder.customTextViewNameSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickSelect(position, sportActivities.get(position).getName(), sportActivities.get(position).getId());
            }
        });

        holder.recyclerExpand.setVisibility((selectPosition == position) ? View.VISIBLE : View.GONE);

        holder.recyclerExpand.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        holder.recyclerExpand.setAdapter(new FilterSubServiceAdapter(context, sportActivities.get(position).getSubServices(), position, new FilterSubServiceAdapter.CallBack() {
            @Override
            public void onClickSelect(int positionSub, int mainPosition) {

                String mainName = sportActivities.get(mainPosition).getName();

                String subName = "";
                String ids = "";

                for (int i = 0; i < sportActivities.size(); i++) {

                    for (int j = 0; j < sportActivities.get(i).getSubServices().size(); j++) {

                        SubService sportSubActivity = sportActivities.get(i).getSubServices().get(j);

                        if (mainPosition == i) {

                            if (sportSubActivity.isSelect()) {
                                if (subName.isEmpty()) {
                                    subName = subName + sportSubActivity.getName();
                                    ids = ids + sportSubActivity.getId();
                                } else {
                                    subName = subName + ", " + sportSubActivity.getName();
                                    ids = ids + "," + sportSubActivity.getId();
                                }
                            }
                        } else {
                            sportSubActivity.setSelect(false);
                            sportActivities.get(i).getSubServices().set(j, sportSubActivity);
                        }
                    }
                }

                if (!subName.isEmpty()) {
                    callBack.onClickSelect(-1, mainName + " : " + subName, ids);
                } else {
                    callBack.onClickSelect(-1, "", "");
                }
            }
        }));
    }

    @Override
    public int getItemCount() {
        return sportActivities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customTextViewNameSingle)
        AppCompatTextView customTextViewNameSingle;
        @BindView(R.id.linearLayoutSingle)
        LinearLayout linearLayoutSingle;
        @BindView(R.id.customTextViewNameExpand)
        AppCompatTextView customTextViewNameExpand;
        @BindView(R.id.recyclerExpand)
        RecyclerView recyclerExpand;
        @BindView(R.id.linearLayoutExpand)
        LinearLayout linearLayoutExpand;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void onSelect(int pos) {
        selectPosition = pos;
    }

    public void setNewFilterData(List<Service> newFilterData){
        sportActivities.clear();
        sportActivities.addAll(newFilterData);
    }


    public interface CallBack {

        void onClickSelect(int position, String name, String id);

    }
}
