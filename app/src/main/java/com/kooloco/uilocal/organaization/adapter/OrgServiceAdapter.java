package com.kooloco.uilocal.organaization.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;
import com.kooloco.model.Activities;
import com.kooloco.model.Service;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class OrgServiceAdapter extends RecyclerView.Adapter<OrgServiceAdapter.ViewHolder> {
    Context context;
    List<Service> services;
    CallBack callBack;

    public OrgServiceAdapter(Context context, List<Service> services, CallBack callBack) {
        this.context = context;
        this.services = services;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_choose_language, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.customTextViewName.setText(services.get(position).getName());
        holder.customTextViewName.setSelected(services.get(position).isSelect());


        holder.customTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Service service = services.get(position);
                service.setSelect(!service.isSelect());
                services.set(position, service);
                callBack.onClickItem(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return services.size();
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
    }

}
