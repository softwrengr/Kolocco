package com.kooloco.uilocal.addservice.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.kooloco.R;
import com.kooloco.model.Activities;
import com.kooloco.model.CancellationPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class CancellationPoliciciesAdapter extends RecyclerView.Adapter<CancellationPoliciciesAdapter.ViewHolder> {
    Context context;
    List<CancellationPolicy> cancellationPolicies;
    int selectPosition = -1;


    public CancellationPoliciciesAdapter(Context context, List<CancellationPolicy> cancellationPolicies) {
        this.context = context;
        this.cancellationPolicies = cancellationPolicies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_set_cancellation, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (cancellationPolicies.get(position).getIsSelected().equalsIgnoreCase("1")) {
            selectPosition = position;
        }


        holder.radioButton.setChecked(selectPosition == position);

        holder.textViewName.setText(cancellationPolicies.get(position).getTitle());
        holder.textViewDisc.setText(cancellationPolicies.get(position).getDescription());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectPosition != position) {

                    int oldPOsition = selectPosition;

                    selectPosition = position;

                    CancellationPolicy cancellationPolicy = cancellationPolicies.get(selectPosition);
                    cancellationPolicy.setIsSelected("1");
                    cancellationPolicies.set(selectPosition, cancellationPolicy);

                    CancellationPolicy cancellationPolicyOld = cancellationPolicies.get(oldPOsition);
                    cancellationPolicyOld.setIsSelected("0");
                    cancellationPolicies.set(oldPOsition, cancellationPolicyOld);

                    notifyItemChanged(oldPOsition);
                    notifyItemChanged(selectPosition);

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return cancellationPolicies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.radioButton)
        RadioButton radioButton;
        @BindView(R.id.textViewName)
        AppCompatTextView textViewName;
        @BindView(R.id.textViewDisc)
        AppCompatTextView textViewDisc;
        @BindView(R.id.linearLayout)
        LinearLayout linearLayout;
        @BindView(R.id.linearLayoutRow)
        LinearLayout linearLayoutRow;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
