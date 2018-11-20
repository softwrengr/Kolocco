package com.kooloco.uilocal.earnings.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.EarningMonth;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class EarningMonthAdapter extends RecyclerView.Adapter<EarningMonthAdapter.ViewHolder> {
    Context context;
    List<EarningMonth> favorites;


    private CallBack callBack;


    public EarningMonthAdapter(Context context, List<EarningMonth> favorites, CallBack callBack) {
        this.context = context;
        this.favorites = favorites;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_earning_month, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
       /* Picasso.with(context).load(favorites.get(position).getImageUrl()).into(holder.imageViewProfile);
        holder.customTextViewName.setText(favorites.get(position).getName());

        holder.customTextViewServiceType.setText(favorites.get(position).getServiceType());


        holder.customTextViewServiceName.setText(favorites.get(position).getService());
        holder.customTextViewPrice.setText(favorites.get(position).getPrice());

        holder.customTextViewDate.setText(favorites.get(position).getDay());
        holder.customTextViewMonth.setText(favorites.get(position).getMonth());
        holder.customTextViewAddress.setText(favorites.get(position).getLocation());*/

    }

    @Override
    public int getItemCount() {
        return favorites.size();
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
        @BindView(R.id.customTextCompleted)
        AppCompatTextView customTextCompleted;
        @BindView(R.id.customTextCancelled)
        AppCompatTextView customTextCancelled;
        @BindView(R.id.imageViewExp)
        ImageView imageViewExp;
        @BindView(R.id.textViewCurrencyType)
        AppCompatTextView textViewCurrencyType;
        @BindView(R.id.customTextViewPrice)
        AppCompatTextView customTextViewPrice;
        @BindView(R.id.linearLayoutContent)
        LinearLayout linearLayoutContent;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.onClickItem(getAdapterPosition());
                }
            });
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {


        void onClickItem(int pos);

    }
}
