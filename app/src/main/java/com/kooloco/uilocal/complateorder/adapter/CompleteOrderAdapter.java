package com.kooloco.uilocal.complateorder.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.constant.Common;
import com.kooloco.model.Chat;
import com.kooloco.model.Order;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.util.TimeConvertUtils;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class CompleteOrderAdapter extends RecyclerView.Adapter<CompleteOrderAdapter.ViewHolder> implements Filterable {
    Context context;
    List<Order> orders;
    List<Order> ordersTemp;


    private CallBack callBack;

    public CompleteOrderAdapter(Context context, List<Order> orders, CallBack callBack) {
        this.context = context;
        this.orders = orders;
        this.ordersTemp = orders;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_complate_order, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Picasso.with(context).load(orders.get(position).getProfileImage()).resizeDimen(R.dimen.dp_60,R.dimen.dp_60).centerCrop().transform(new CircleTransform()).placeholder(R.drawable.user_round).into(holder.imageViewProfile);

        holder.imageViewProfile.setOnClickListener(view -> callBack.onClickImage(orders.get(position).getProfileImage()));
        holder.customTextViewName.setText(orders.get(position).getFirstname() + " " + orders.get(position).getLastname());
        holder.ratingBar.setRating(Float.parseFloat(orders.get(position).getVisitorRate()));

        holder.customTextViewServiceType.setText(orders.get(position).getExperienceTitle());

        holder.customTextViewOrderStatus.setText(orders.get(position).getPaymentStatus());

        if (orders.get(position).getPaymentStatus().equalsIgnoreCase(Common.OrderDetails.PAID)) {
            holder.customTextViewOrderStatus.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            holder.customTextViewOrderStatus.setTextColor(context.getResources().getColor(R.color.yellow));
        }

        if (orders.get(position).getPaymentStatus().equalsIgnoreCase(Common.OrderDetails.PAID)) {
            holder.customTextCompleted.setVisibility(View.VISIBLE);
            holder.customTextCancelled.setVisibility(View.GONE);

        } else if (orders.get(position).getPaymentStatus().equalsIgnoreCase(Common.OrderDetails.REFUND)) {
            holder.customTextCompleted.setVisibility(View.GONE);
            holder.customTextCancelled.setVisibility(View.VISIBLE);
        }


        holder.textViewReviewCount.setText("(" + orders.get(position).getReviewCount() + ")");
        holder.textViewCurrencyType.setText(BaseActivity.currency);

        holder.customTextViewPrice.setText(orders.get(position).getTotalPrice());

        holder.customTextViewDate.setText(TimeConvertUtils.dateTimeConvertLocalToLocal(orders.get(position).getBookingDate(), "yyyy-MM-dd", "dd"));
        holder.customTextViewMonth.setText(TimeConvertUtils.dateTimeConvertLocalToLocal(orders.get(position).getBookingDate(), "yyyy-MM-dd", "MMM"));

        holder.customTextViewAddress.setText((orders.get(position).getCity().isEmpty() ? orders.get(position).getCountry() : orders.get(position).getCity() + ", " + orders.get(position).getCountry()));

        Picasso.with(context).load(orders.get(position).getExpIcon()).placeholder(R.drawable.place).into(holder.imageViewExp);

        holder.linearLayoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickRow(orders.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    orders = ordersTemp;
                } else {

                    ArrayList<Order> orderList = new ArrayList<>();

                    for (Order order : ordersTemp) {


                        if ((order.getFirstname() + " " + order.getLastname()).toLowerCase().contains(charString.toLowerCase()) || (order.getCity() + ", " + order.getCountry()).toLowerCase().contains(charString.toLowerCase()) || order.getExperienceTitle().toLowerCase().contains(charString.toLowerCase()) || order.getTotalPrice().toLowerCase().contains(charString.toLowerCase())) {
                            orderList.add(order);
                        }
                    }

                    orders = orderList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = orders;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                orders = (ArrayList<Order>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public int countItems() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewProfile)
        ImageView imageViewProfile;
        @BindView(R.id.customTextViewDate)
        AppCompatTextView customTextViewDate;
        @BindView(R.id.customTextViewMonth)
        AppCompatTextView customTextViewMonth;
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.customTextViewOrderStatus)
        AppCompatTextView customTextViewOrderStatus;
        @BindView(R.id.customTextViewServiceType)
        AppCompatTextView customTextViewServiceType;
        @BindView(R.id.ratingBar)
        AppCompatRatingBar ratingBar;
        @BindView(R.id.textViewReviewCount)
        AppCompatTextView textViewReviewCount;
        @BindView(R.id.customTextViewAddress)
        AppCompatTextView customTextViewAddress;
        @BindView(R.id.imageViewExp)
        ImageView imageViewExp;
        @BindView(R.id.textViewCurrencyType)
        AppCompatTextView textViewCurrencyType;
        @BindView(R.id.customTextViewPrice)
        AppCompatTextView customTextViewPrice;
        @BindView(R.id.linearLayoutContent)
        LinearLayout linearLayoutContent;
        @BindView(R.id.customTextCompleted)
        AppCompatTextView customTextCompleted;
        @BindView(R.id.customTextCancelled)
        AppCompatTextView customTextCancelled;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {

        void onClickRow(Order order);

        void onClickImage(String imageUrl);

    }
}
