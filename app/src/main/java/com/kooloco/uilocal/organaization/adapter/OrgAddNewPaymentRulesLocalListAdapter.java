package com.kooloco.uilocal.organaization.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.kooloco.R;
import com.kooloco.model.OrgLocal;
import com.kooloco.model.PaymentRuleLocal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by hlink44 on 14/9/17.
 */

public class OrgAddNewPaymentRulesLocalListAdapter extends RecyclerView.Adapter<OrgAddNewPaymentRulesLocalListAdapter.ViewHolder> {
    Context context;
    private List<OrgLocal> orgLocals;
    private List<PaymentRuleLocal> assignedLocal;


    public OrgAddNewPaymentRulesLocalListAdapter(Context context, List<OrgLocal> orgLocals, List<PaymentRuleLocal> assignedLocal) {
        this.context = context;
        this.orgLocals = orgLocals;
        this.assignedLocal = assignedLocal;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_org_add_new_payment_rule_local, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Glide.with(context).load(orgLocals.get(position).getImageUrl()).asBitmap().transform(new CropCircleTransformation(context)).placeholder(R.drawable.user_round).into(holder.imageViewProfile);

        holder.customTextViewName.setText(orgLocals.get(position).getName());

        holder.checkBoxSelect.setChecked(orgLocals.get(position).isSelect());

        holder.linearLayoutRow.setOnClickListener(view -> {
            holder.checkBoxSelect.setChecked(!holder.checkBoxSelect.isChecked());

            OrgLocal orgLocal = orgLocals.get(position);
            orgLocal.setSelect(holder.checkBoxSelect.isChecked());

            orgLocals.set(position, orgLocal);
            notifyItemChanged(position);
        });
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
        @BindView(R.id.checkBoxSelect)
        CheckBox checkBoxSelect;
        @BindView(R.id.linearLayoutRow)
        LinearLayout linearLayoutRow;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
