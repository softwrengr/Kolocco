package com.kooloco.ui.visitor.dashboard.adapter;

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

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
    Context context;
    List<Card> cards;
    private CallBack callBack;
    int selectPosition = 0;


    public PaymentAdapter(Context context, List<Card> cards, CallBack callBack) {
        this.context = context;
        this.cards = cards;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_payment, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.customTextViewName.setText(cards.get(position).getCardNumber());
        Picasso.with(context).load(cards.get(position).getImage()).into(holder.imageViewIcon);
        if (selectPosition == position) {
            holder.imageViewSelect.setVisibility(View.VISIBLE);
        } else {
            holder.imageViewSelect.setVisibility(View.INVISIBLE);
        }


        holder.linearLayoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPosition = position;
                callBack.onSelect(cards.get(position));
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.imageViewSelect)
        ImageView imageViewSelect;
        @BindView(R.id.linearLayoutRoot)
        LinearLayout linearLayoutRoot;
        @BindView(R.id.imageViewIcon)
        ImageView imageViewIcon;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onSelect(Card card);
    }
}
