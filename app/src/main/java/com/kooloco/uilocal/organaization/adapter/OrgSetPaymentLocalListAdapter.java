package com.kooloco.uilocal.organaization.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.kooloco.R;
import com.kooloco.model.PaymentRuleLocal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by hlink44 on 14/9/17.
 */

public class OrgSetPaymentLocalListAdapter extends RecyclerView.Adapter<OrgSetPaymentLocalListAdapter.ViewHolder> {
    Context context;
    private List<PaymentRuleLocal> orgLocals;

    public OrgSetPaymentLocalListAdapter(Context context, List<PaymentRuleLocal> orgLocals) {
        this.context = context;
        this.orgLocals = orgLocals;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_org_set_payment_local, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Glide.with(context).load(orgLocals.get(position).getProfileImage()).asBitmap().transform(new CropCircleTransformation(context)).placeholder(R.drawable.user_round).into(holder.imageViewProfile);

        holder.customTextViewName.setText(orgLocals.get(position).getFirstname() + " " + orgLocals.get(position).getLastname());

        holder.customTextViewStatus.setText(orgLocals.get(position).getIsAccepted().equalsIgnoreCase("1") ? context.getString(R.string.notification_accept_new) : context.getString(R.string.pending));

        holder.customTextViewStatus.setTextColor(orgLocals.get(position).getIsAccepted().equalsIgnoreCase("1") ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.yellow));

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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
