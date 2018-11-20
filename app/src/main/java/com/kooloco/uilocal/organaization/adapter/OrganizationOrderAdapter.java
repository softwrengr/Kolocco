package com.kooloco.uilocal.organaization.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.core.Session;
import com.kooloco.model.Order;
import com.kooloco.model.OrderOrg;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class OrganizationOrderAdapter extends RecyclerView.Adapter<OrganizationOrderAdapter.ViewHolder> {
    Context context;
    List<OrderOrg> orders;

    private CallBack callBack;

    public OrganizationOrderAdapter(Context context, List<OrderOrg> orders, CallBack callBack) {
        this.context = context;
        this.orders = orders;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_organization_dashboard, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.with(context).load(orders.get(position).getProfileImage()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(holder.imageViewProfile);

        holder.imageViewProfile.setOnClickListener(view -> callBack.onClickImage(orders.get(position).getProfileImage()));

        holder.customTextViewName.setText(orders.get(position).getFirstname() + " " + orders.get(position).getLastname());

        holder.customTextViewServiceType.setText(orders.get(position).getExperienceTitle());

        holder.customTextViewServiceName.setText(orders.get(position).getSport());

        holder.customTextViewPrice.setText(orders.get(position).getActivityTootal());

        holder.textViewCurrencyType.setText(BaseActivity.currency);



        holder.customTextViewAddress.setText(orders.get(position).getCity() + ", " + orders.get(position).getCountry());

        String htmlText = "<u>" + orders.get(position).getLocalFirstName() + " " + orders.get(position).getLocalLastName() + "</u>";

        holder.customTextViewLocalName.setText(Html.fromHtml(htmlText));

        holder.customTextViewLocalName.setOnClickListener(view -> callBack.onClickLocalDetails(orders.get(position).getLocalId()));


        holder.customTextStatus.setVisibility(orders.get(position).getStatus().equalsIgnoreCase("completed") ? View.GONE : View.VISIBLE);

        holder.customTextCompleted.setVisibility(orders.get(position).getStatus().equalsIgnoreCase("completed") ? View.VISIBLE : View.GONE);

        holder.customTextViewPaymentRule.setText(orders.get(position).getPaymentRuleText());

        //It is used to set status

        holder.customTextStatus.setText(orders.get(position).getStatus());
        holder.customTextCompleted.setText(orders.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewProfile)
        ImageView imageViewProfile;
        @BindView(R.id.customTextCompleted)
        AppCompatTextView customTextCompleted;
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.customTextViewPrice)
        AppCompatTextView customTextViewPrice;
        @BindView(R.id.customTextViewServiceType)
        AppCompatTextView customTextViewServiceType;
        @BindView(R.id.customTextViewServiceName)
        AppCompatTextView customTextViewServiceName;
        @BindView(R.id.customTextViewAddress)
        AppCompatTextView customTextViewAddress;
        @BindView(R.id.linearLayoutContent)
        LinearLayout linearLayoutContent;
        @BindView(R.id.customTextStatus)
        AppCompatTextView customTextStatus;
        @BindView(R.id.customTextViewLocalName)
        AppCompatTextView customTextViewLocalName;
        @BindView(R.id.customTextViewPaymentRule)
        AppCompatTextView customTextViewPaymentRule;
        @BindView(R.id.textViewCurrencyType)
        AppCompatTextView textViewCurrencyType;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {


        void onClickRow(Order order);

        void onClickImage(String profileImage);

        void onClickLocalDetails(String localId);

    }
}
