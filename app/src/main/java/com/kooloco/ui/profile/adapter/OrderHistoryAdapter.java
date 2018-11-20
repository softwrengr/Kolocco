package com.kooloco.ui.profile.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.amazonaws.util.StringUtils;
import com.kooloco.R;
import com.kooloco.constant.Common;
import com.kooloco.model.Order;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.util.TimeConvertUtils;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {
    Context context;
    List<Order> orders;
    private CallBack callBack;

    public OrderHistoryAdapter(Context context, List<Order> orders, CallBack callBack) {
        this.context = context;
        this.orders = orders;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_order_history, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.with(context).load(orders.get(position).getProfileImage()).resizeDimen(R.dimen.dp_60, R.dimen.dp_60).centerCrop().transform(new CircleTransform()).placeholder(R.drawable.user_round).into(holder.imageViewProfile);
        holder.imageViewProfile.setOnClickListener(view -> callBack.onClickImage(orders.get(position).getProfileImage()));
        holder.customTextViewName.setText(orders.get(position).getFirstname() + " " + orders.get(position).getLastname());
        holder.ratingBar.setRating(Float.parseFloat(orders.get(position).getRating()));

        holder.customTextViewServiceType.setText(orders.get(position).getExperienceTitle());


        String orderStatus = orders.get(position).getPaymentStatus();

        if (orders.get(position).getPaymentStatus().equalsIgnoreCase(Common.OrderDetails.PAID)) {
            holder.customTextViewOrderStatus.setTextColor(context.getResources().getColor(R.color.green));
            holder.linearLayoutStatus.setVisibility(View.GONE);
            orderStatus = context.getResources().getString(R.string.order_history_paid);
        } else if (orders.get(position).getPaymentStatus().equalsIgnoreCase(Common.OrderDetails.CANCEL)) {
            holder.customTextViewOrderStatus.setTextColor(context.getResources().getColor(R.color.red));
            holder.linearLayoutStatus.setVisibility(View.GONE);
            orderStatus = context.getResources().getString(R.string.order_status_cancelled);
        } else if (orders.get(position).getPaymentStatus().equalsIgnoreCase(Common.OrderDetails.REFUND)) {
            holder.customTextViewOrderStatus.setTextColor(context.getResources().getColor(R.color.yellow));
            holder.linearLayoutStatus.setVisibility(View.VISIBLE);
            orderStatus = context.getResources().getString(R.string.order_payment_status_refunded);
        } else {
            holder.customTextViewOrderStatus.setTextColor(context.getResources().getColor(R.color.yellow));
            holder.linearLayoutStatus.setVisibility(View.VISIBLE);
            orderStatus = context.getResources().getString(R.string.order_history_escrow);
        }


        holder.customTextViewOrderStatus.setText(orderStatus);


        String status = orders.get(position).getStatus().toLowerCase();

        String statusText = "";

        switch (status) {
            case Common.OrderDetails.PENDING:
                status = context.getString(R.string.pending_for_approval);
                statusText = context.getString(R.string.pending_for_approval);
                break;
            case Common.OrderDetails.ACCEPTED:
                status = context.getString(R.string.ready_start);
                statusText = context.getString(R.string.ready_start);
                break;
            case Common.OrderDetails.COMPLETED:
                status = Common.OrderDetails.COMPLETED;
                statusText = context.getString(R.string.order_status_completed);
                break;
            case Common.OrderDetails.REJECTED:
                status = Common.OrderDetails.REJECTED;
                statusText = context.getString(R.string.order_status_rejected);
                break;
            case Common.OrderDetails.DECLINE:
                status = Common.OrderDetails.DECLINE;
                statusText = context.getString(R.string.order_status_decline);
                break;
            case Common.OrderDetails.CANCELED:
                status = Common.OrderDetails.CANCELED;
                statusText = context.getString(R.string.order_status_cancelled);
                break;
            default:
                status = Common.OrderDetails.PENDING;
                statusText = context.getString(R.string.order_status_pending);
                break;

        }


        status = status.substring(0, 1).toUpperCase() + status.substring(1);


        statusText = statusText.substring(0, 1).toUpperCase() + statusText.substring(1);

        holder.customTextViewStatus.setText(statusText);


        holder.customTextViewStatus.setTextColor(context.getResources().getColor(R.color.yellow));

        if (orders.get(position).getStatus().equalsIgnoreCase(Common.OrderDetails.PENDING)) {
            holder.customTextViewStatus.setTextColor(context.getResources().getColor(R.color.yellow));
        } else if (orders.get(position).getStatus().equalsIgnoreCase(Common.OrderDetails.ACCEPTED) || orders.get(position).getStatus().equalsIgnoreCase(Common.OrderDetails.COMPLETED)) {
            holder.customTextViewStatus.setTextColor(context.getResources().getColor(R.color.green));
        } else if (orders.get(position).getStatus().equalsIgnoreCase(Common.OrderDetails.REJECTED) || orders.get(position).getStatus().equalsIgnoreCase(Common.OrderDetails.DECLINE) || orders.get(position).getStatus().equalsIgnoreCase(Common.OrderDetails.CANCELED)) {
            holder.customTextViewStatus.setTextColor(context.getResources().getColor(R.color.red));
        }

        if (orders.get(position).getStatus().equalsIgnoreCase(Common.OrderDetails.REJECTED) || orders.get(position).getStatus().equalsIgnoreCase(Common.OrderDetails.DECLINE)) {
            holder.customTextViewOrderStatus.setVisibility(View.GONE);
        } else {
            holder.customTextViewOrderStatus.setVisibility(View.VISIBLE);
        }

        holder.textViewReviewCount.setText("(" + orders.get(position).getReviewCount() + ")");

        holder.textViewCurrencyType.setText(orders.get(position).getVisitorCurrency());

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
        @BindView(R.id.customTextViewAddress)
        AppCompatTextView customTextViewAddress;
        @BindView(R.id.customTextViewPrice)
        AppCompatTextView customTextViewPrice;
        @BindView(R.id.linearLayoutContent)
        LinearLayout linearLayoutContent;
        @BindView(R.id.imageViewExp)
        ImageView imageViewExp;
        @BindView(R.id.textViewReviewCount)
        AppCompatTextView textViewReviewCount;
        @BindView(R.id.textViewCurrencyType)
        AppCompatTextView textViewCurrencyType;
        @BindView(R.id.customTextViewStatus)
        AppCompatTextView customTextViewStatus;
        @BindView(R.id.linearLayoutStatus)
        LinearLayout linearLayoutStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickChat(Order order);

        void onClickCall(Order order);

        void onClickRow(Order order);

        void onClickImage(String imageUrl);

    }
}
