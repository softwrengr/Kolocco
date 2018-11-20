package com.kooloco.uilocal.expereince.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.kooloco.R;
import com.kooloco.data.temp.Temp;
import com.kooloco.model.CancellationPolicy;
import com.kooloco.ui.expereince.adapter.ExperienceCancellationPolicyAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class CancellationPoliciciesNewAdapter extends RecyclerView.Adapter<CancellationPoliciciesNewAdapter.ViewHolder> {
    Context context;
    List<CancellationPolicy> cancellationPolicies;
    int selectPosition = -1;


    public CancellationPoliciciesNewAdapter(Context context, List<CancellationPolicy> cancellationPolicies) {
        this.context = context;
        this.cancellationPolicies = cancellationPolicies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_set_cancellation_new, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (cancellationPolicies.get(position).getIsSelected().equalsIgnoreCase("1")) {
            selectPosition = position;
        }


        holder.radioButton.setChecked(selectPosition == position);

        holder.textViewName.setText(cancellationPolicies.get(position).getTitle());

        holder.recyclerViewCancellation.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        holder.recyclerViewCancellation.setAdapter(new ExperienceCancellationPolicyAdapter(context, cancellationPolicies.get(position).getCancellationNewPolicyList(), new ExperienceCancellationPolicyAdapter.CallBack() {
            @Override
            public void onClickListener() {
                holder.linearLayoutRow.performClick();
            }
        }));

        holder.linearLayoutRow.setOnClickListener(new View.OnClickListener() {
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
        @BindView(R.id.recyclerViewCancellation)
        RecyclerView recyclerViewCancellation;
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
