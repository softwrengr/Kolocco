package com.kooloco.ui.payment.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.Card;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardAdapter.ViewHolder> {
    Context context;
    List<Card> cards;
    CallBack callBack;

    public CreditCardAdapter(Context context, List<Card> cards, CallBack callBack) {
        this.context = context;
        this.cards = cards;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_visitor_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.customTextViewName.setText(cards.get(position).getCardNumber());

        if (cards.get(position).getImage().isEmpty()) {
            Picasso.with(context).load(R.drawable.place).error(R.drawable.place).placeholder(R.drawable.place).into(holder.imageViewIcon);
        } else {
            Picasso.with(context).load(cards.get(position).getImage()).error(R.drawable.place).placeholder(R.drawable.place).into(holder.imageViewIcon);
        }

        holder.imageViewDelete.setOnClickListener(view -> callBack.onClickDelete(cards.get(position),position));
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewIcon)
        ImageView imageViewIcon;
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.imageViewDelete)
        ImageView imageViewDelete;
        @BindView(R.id.linearLayoutRoot)
        LinearLayout linearLayoutRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickDelete(Card card,int pos);
    }
}
