package com.kooloco.ui.profile.adapter;

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
import com.kooloco.model.HelpAndFaq;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class HelpAndFaqAdapter extends RecyclerView.Adapter<HelpAndFaqAdapter.ViewHolder> {
    Context context;
    List<HelpAndFaq> helpAndFaqs;
    int isExpand = 0;

    public HelpAndFaqAdapter(Context context, List<HelpAndFaq> helpAndFaqs) {
        this.context = context;
        this.helpAndFaqs = helpAndFaqs;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_help_faq, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        String textQ = "<font color='" + context.getResources().getColor(R.color.buttonColor) + "'> <b>Q. </b> </font>" + helpAndFaqs.get(position).getQ();
        String textA = "<font color='" + context.getResources().getColor(R.color.buttonColor) + "'> <b>A. </b> </font>" + helpAndFaqs.get(position).getAns();

        holder.textViewQution.setText(Html.fromHtml(textQ));
        holder.textViewAns.setText(Html.fromHtml(textA));

        if (isExpand == position) {
            holder.linearLayoutAns.setVisibility(View.VISIBLE);
            holder.imageDrop.setImageDrawable(context.getResources().getDrawable(R.drawable.help_up_arrow));
        } else {
            holder.linearLayoutAns.setVisibility(View.GONE);
            holder.imageDrop.setImageDrawable(context.getResources().getDrawable(R.drawable.help_drop_arrow));
        }

        holder.linearLayoutQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpand == position) {
                    isExpand = -1;
                    notifyDataSetChanged();
                } else {
                    isExpand = position;
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return helpAndFaqs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewQution)
        AppCompatTextView textViewQution;
        @BindView(R.id.imageDrop)
        ImageView imageDrop;
        @BindView(R.id.linearLayoutQ)
        LinearLayout linearLayoutQ;
        @BindView(R.id.textViewAns)
        AppCompatTextView textViewAns;
        @BindView(R.id.linearLayoutAns)
        LinearLayout linearLayoutAns;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
