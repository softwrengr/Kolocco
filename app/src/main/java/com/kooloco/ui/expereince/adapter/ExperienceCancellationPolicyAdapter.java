package com.kooloco.ui.expereince.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.CancellationNewPolicy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class ExperienceCancellationPolicyAdapter extends RecyclerView.Adapter<ExperienceCancellationPolicyAdapter.ViewHolder> {
    Context context;
    List<CancellationNewPolicy> cancellationNewPolicies;
    private CallBack callBack;


    public ExperienceCancellationPolicyAdapter(Context context, List<CancellationNewPolicy> cancellationNewPolicies, CallBack callBack) {
        this.context = context;
        this.cancellationNewPolicies = cancellationNewPolicies;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_cancelation, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.textViewTitle.setText(cancellationNewPolicies.get(position).getTitle());
        holder.textViewTitleValue.setText(cancellationNewPolicies.get(position).getRefaund());
        holder.linearLayoutMain.setOnClickListener(view -> callBack.onClickListener());

    }

    @Override
    public int getItemCount() {
        return cancellationNewPolicies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewTitle)
        AppCompatTextView textViewTitle;
        @BindView(R.id.textViewTitleValue)
        AppCompatTextView textViewTitleValue;
        @BindView(R.id.linearLayoutMain)
        LinearLayout linearLayoutMain;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface CallBack {
        void onClickListener();
    }


}
