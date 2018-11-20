package com.kooloco.uilocal.organaization.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.PaymentRuleList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class OrgSetPaymentRulesListAdapter extends RecyclerView.Adapter<OrgSetPaymentRulesListAdapter.ViewHolder> {
    Context context;
    private List<PaymentRuleList> orgLocals;
    private int expandIndex = -1;
    private CallBack callBack;

    public OrgSetPaymentRulesListAdapter(Context context, List<PaymentRuleList> orgLocals, CallBack callBack) {
        this.context = context;
        this.orgLocals = orgLocals;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_org_set_payment, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.imageViewExpand.setSelected(!(position == expandIndex));

        holder.textViewPaymentRules.setText(orgLocals.get(position).getTitle());

        holder.textViewPaymentRulesValue.setText(orgLocals.get(position).getPaymentOptionValue());

        holder.linearLayoutExpand.setVisibility((position == expandIndex) ? View.VISIBLE : View.GONE);
        holder.recyclerViewLocal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.recyclerViewLocal.setAdapter(new OrgSetPaymentLocalListAdapter(context, orgLocals.get(position).getAssignedLocal()));

        holder.textViewNoLocalFound.setVisibility((orgLocals.get(position).getAssignedLocal().size() == 0) ? View.VISIBLE : View.GONE);

        holder.imageViewExpand.setOnClickListener(view -> {
            if (expandIndex == position) {
                expandIndex = -1;
                notifyItemChanged(position);
            } else {
                int oldIndex = expandIndex;
                expandIndex = position;
                notifyItemChanged(oldIndex);
                notifyItemChanged(expandIndex);
            }
        });

        holder.imageViewEdit.setOnClickListener(view -> callBack.onClickEdit(orgLocals.get(position)));
        holder.imageViewDelete.setOnClickListener(view -> callBack.onClickDelete(orgLocals.get(position)));

    }

    @Override
    public int getItemCount() {
        return orgLocals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewPaymentRules)
        AppCompatTextView textViewPaymentRules;
        @BindView(R.id.imageViewEdit)
        ImageView imageViewEdit;
        @BindView(R.id.imageViewDelete)
        ImageView imageViewDelete;
        @BindView(R.id.textViewPaymentRulesValue)
        AppCompatTextView textViewPaymentRulesValue;
        @BindView(R.id.recyclerViewLocal)
        RecyclerView recyclerViewLocal;
        @BindView(R.id.imageViewExpand)
        AppCompatImageView imageViewExpand;
        @BindView(R.id.linearLayoutRoot)
        LinearLayout linearLayoutRoot;
        @BindView(R.id.linearLayoutExpand)
        LinearLayout linearLayoutExpand;
        @BindView(R.id.textViewNoLocalFound)
        AppCompatTextView textViewNoLocalFound;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickEdit(PaymentRuleList paymentRuleList);

        void onClickDelete(PaymentRuleList paymentRuleList);
    }
}
