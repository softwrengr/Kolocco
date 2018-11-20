package com.kooloco.ui.visitor.home.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.model.Service;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class HomeServiceAdapter extends RecyclerView.Adapter<HomeServiceAdapter.ViewHolder> {
    Context context;
    List<Service> services;
    boolean isVisableName = false;

    int selectPosition = -1;
    CallBack callBack;


    public HomeServiceAdapter(Context context, List<Service> services, boolean isVisableName,CallBack callBack) {
        this.context = context;
        this.services = services;
        this.isVisableName = isVisableName;
        this.callBack=callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_home_service_image, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //As per client comment code //28-02-2018
        //holder.customTextViewName.setVisibility((position == selectPosition) ? View.VISIBLE : View.GONE);

        Picasso.with(context).load(services.get(position).getServiceImage()).into(holder.imageViewService);
        holder.customTextViewName.setText(services.get(position).getName());
        if (isVisableName) {
            holder.imageViewService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    callBack.onClickItem(position);
                    //As per client comment code //28-02-2018
/*
                    selectPosition = position;
                    notifyDataSetChanged();
*/
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewService)
        ImageView imageViewService;
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack{
        void onClickItem(int pos);
    }
}
