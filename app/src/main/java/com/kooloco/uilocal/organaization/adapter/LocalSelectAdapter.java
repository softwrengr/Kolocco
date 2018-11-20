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

public class LocalSelectAdapter extends RecyclerView.Adapter<LocalSelectAdapter.ViewHolder> {
    Context context;
    private List<OrgLocal> orgLocals;

    public LocalSelectAdapter(Context context, List<OrgLocal> orgLocals) {
        this.context = context;
        this.orgLocals = orgLocals;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_create_organization_local_select, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.imageViewSelect.setVisibility(orgLocals.get(position).isSelect() ? View.VISIBLE : View.INVISIBLE);

        holder.linearLayoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrgLocal orgLocal = orgLocals.get(position);
                orgLocal.setSelect(!orgLocal.isSelect());
                orgLocals.set(position, orgLocal);
                notifyDataSetChanged();
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
        @BindView(R.id.imageViewSelect)
        ImageView imageViewSelect;
        @BindView(R.id.linearLayoutContent)
        LinearLayout linearLayoutContent;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
