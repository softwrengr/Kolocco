package com.kooloco.uilocal.organaization.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.kooloco.R;
import com.kooloco.model.PaymentRulesOption;
import com.kooloco.util.InputFilterMinMaxDouble;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class OrgAddPaymentRulesListAdapter extends RecyclerView.Adapter<OrgAddPaymentRulesListAdapter.ViewHolder> {
    private CallBack callBack;
    Context context;
    private List<PaymentRulesOption> orgLocals;

    private int selectPosition = 0;

    private String setValue = "";

    public OrgAddPaymentRulesListAdapter(Context context, List<PaymentRulesOption> orgLocals, CallBack callBack) {
        this.context = context;
        this.orgLocals = orgLocals;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_org_add_new_payment_rules, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.radioButton.setChecked(selectPosition == position);

        holder.ediTextPer.setEnabled((selectPosition == position) && orgLocals.get(position).getRuleType().equalsIgnoreCase("split"));

        holder.ediTextPer.setSelected((selectPosition == position) && orgLocals.get(position).getRuleType().equalsIgnoreCase("split"));

        if ((selectPosition == position) && orgLocals.get(position).getRuleType().equalsIgnoreCase("split")) {
            holder.ediTextPer.requestFocus();
            callBack.showKey();

        } else {
            holder.ediTextPer.clearFocus();
            callBack.hideKey();
        }

        holder.ediTextPer.setVisibility(orgLocals.get(position).getRuleType().equalsIgnoreCase("split") ? View.VISIBLE : View.GONE);

        holder.ediTextPer.setFilters(new InputFilter[]{new InputFilterMinMaxDouble(0.00, 100.00)});

        holder.textViewLabel.setText(orgLocals.get(position).getTitle());

        holder.ediTextPer.setText(setValue);

        if (orgLocals.get(position).getRuleType().equalsIgnoreCase("split")) {
            holder.ediTextPer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    setValue = charSequence.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }

        holder.linearLayoutRefundFlatAmount.setOnClickListener(view -> {
            holder.radioButton.performClick();
        });

        holder.radioButton.setOnClickListener(view -> {
            setValue = "";
            selectPosition = position;
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return orgLocals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.radioButton)
        RadioButton radioButton;
        @BindView(R.id.textViewLabel)
        AppCompatTextView textViewLabel;
        @BindView(R.id.ediTextPer)
        AppCompatEditText ediTextPer;
        @BindView(R.id.linearLayoutRefundFlatAmount)
        LinearLayout linearLayoutRefundFlatAmount;
        @BindView(R.id.linearLayoutRoot)
        LinearLayout linearLayoutRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public String getSetValue() {
        return setValue;
    }

    public void setSetValue(String setValue) {
        this.setValue = setValue;
    }

    public PaymentRulesOption getSelectData() {
        return orgLocals.get(selectPosition);
    }

    public int getSelectPosition() {
        return selectPosition;
    }


    public void setSelectIndex(int selectPosition) {
        this.selectPosition = selectPosition;
    }


    public interface CallBack {
        void showKey();

        void hideKey();

    }


}
