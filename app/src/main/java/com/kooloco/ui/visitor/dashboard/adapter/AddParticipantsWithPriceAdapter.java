package com.kooloco.ui.visitor.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;
import com.kooloco.model.AddParticipants;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class AddParticipantsWithPriceAdapter extends RecyclerView.Adapter<AddParticipantsWithPriceAdapter.ViewHolder> {
    Context context;
    List<AddParticipants> addParticipants;
    private double tootlePrice;
    private String visitorBookingCurrency;

    public AddParticipantsWithPriceAdapter(Context context, List<AddParticipants> addParticipants, double tootlePrice) {
        this.context = context;
        this.addParticipants = addParticipants;
        this.tootlePrice = tootlePrice;
        this.visitorBookingCurrency = BaseActivity.currency;

    }

    public AddParticipantsWithPriceAdapter(Context context, List<AddParticipants> addParticipants, double tootlePrice, String visitorBookingCurrency) {
        this.context = context;
        this.addParticipants = addParticipants;
        this.tootlePrice = tootlePrice;
        this.visitorBookingCurrency = visitorBookingCurrency;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_add_participants_with_price, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.textViewEmail.setText(addParticipants.get(position).getEmail());

        double v = tootlePrice / addParticipants.size();
        v = BaseFragment.roundDouble(v);

        holder.textViewPrice.setText(visitorBookingCurrency + " " + BaseFragment.convertFormat(v));
    }

    @Override
    public int getItemCount() {
        return addParticipants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewEmail)
        AppCompatTextView textViewEmail;

        @BindView(R.id.textViewPrice)
        AppCompatTextView textViewPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
