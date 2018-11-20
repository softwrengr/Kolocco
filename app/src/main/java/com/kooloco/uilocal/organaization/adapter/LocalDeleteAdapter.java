package com.kooloco.uilocal.organaization.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.OrgLocal;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class LocalDeleteAdapter extends RecyclerView.Adapter<LocalDeleteAdapter.ViewHolder> {
    Context context;
    private List<OrgLocal> orgLocals;
    CallBack callBack;

    public LocalDeleteAdapter(Context context, List<OrgLocal> orgLocals, CallBack callBack) {
        this.context = context;
        this.orgLocals = orgLocals;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_create_organization_local_delete, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickDelete(position);
            }
        });
        holder.customTextViewName.setText(orgLocals.get(position).getName());
        Picasso.with(context).load(orgLocals.get(position).getImageUrl()).into(holder.imageViewProfile);

    }

    @Override
    public int getItemCount() {
        return orgLocals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewProfile)
        ImageView imageViewProfile;
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.imageViewDelete)
        ImageView imageViewDelete;
        @BindView(R.id.linearLayoutContent)
        LinearLayout linearLayoutContent;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickDelete(int pos);
    }
}
