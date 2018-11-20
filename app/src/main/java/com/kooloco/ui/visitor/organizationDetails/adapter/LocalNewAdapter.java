package com.kooloco.ui.visitor.organizationDetails.adapter;

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
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class LocalNewAdapter extends RecyclerView.Adapter<LocalNewAdapter.ViewHolder> {
    Context context;
    private List<OrgLocal> orgLocals;
    CallBack callBack;
    boolean isPreview = false;

    public LocalNewAdapter(Context context, List<OrgLocal> orgLocals, CallBack callBack, boolean isPreview) {
        this.context = context;
        this.orgLocals = orgLocals;
        this.callBack = callBack;
        this.isPreview = isPreview;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_visitor_org_local, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.customTextViewName.setText(orgLocals.get(position).getName());
        Picasso.with(context).load(orgLocals.get(position).getImageUrl()).placeholder(R.drawable.user_round).transform(new CircleTransform()).into(holder.imageViewProfile);
        holder.linearLayoutRow.setOnClickListener(view -> {
            callBack.onClickRow(position, orgLocals.get(position));
        });

        holder.linearLayoutAction.setVisibility(isPreview ? View.VISIBLE : View.GONE);

        holder.customTextViewStatus.setText(orgLocals.get(position).getIsAccepted().equalsIgnoreCase("1") ? context.getString(R.string.notification_accept_new) : context.getString(R.string.pending));

        holder.customTextViewStatus.setTextColor(orgLocals.get(position).getIsAccepted().equalsIgnoreCase("1") ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.yellow));

        holder.customTextViewAssign.setText(orgLocals.get(position).getIsPaymentRulesAssign().equalsIgnoreCase("1") ? context.getString(R.string.assign) : context.getString(R.string.unassign));

        holder.customTextViewAssign.setTextColor(orgLocals.get(position).getIsPaymentRulesAssign().equalsIgnoreCase("1") ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.yellow));

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
        @BindView(R.id.linearLayoutRow)
        LinearLayout linearLayoutRow;
        @BindView(R.id.customTextViewStatus)
        AppCompatTextView customTextViewStatus;
        @BindView(R.id.customTextViewAssign)
        AppCompatTextView customTextViewAssign;
        @BindView(R.id.linearLayoutAction)
        LinearLayout linearLayoutAction;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickRow(int position, OrgLocal orgLocal);
    }


}
