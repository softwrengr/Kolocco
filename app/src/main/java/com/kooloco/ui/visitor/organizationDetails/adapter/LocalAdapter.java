package com.kooloco.ui.visitor.organizationDetails.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.model.OrgLocal;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.ViewHolder> {
    Context context;
    private List<OrgLocal> orgLocals;

    public LocalAdapter(Context context, List<OrgLocal> orgLocals) {
        this.context = context;
        this.orgLocals = orgLocals;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_visitor_organization_detail_local, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
