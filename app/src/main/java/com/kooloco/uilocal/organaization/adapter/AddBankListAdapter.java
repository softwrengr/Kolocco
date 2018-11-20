package com.kooloco.uilocal.organaization.adapter;

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
import com.kooloco.model.Bank;
import com.kooloco.model.OrgLocal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class AddBankListAdapter extends RecyclerView.Adapter<AddBankListAdapter.ViewHolder> {
    Context context;
    private List<Bank> banks;
    private CallBack callBack;
    private int selectPosition = -1;

    public AddBankListAdapter(Context context, List<Bank> banks, CallBack callBack) {
        this.context = context;
        this.banks = banks;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.add_bank_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.customTextViewBankName.setText(banks.get(position).getBankName());

        holder.customTextViewAccountNumber.setText(banks.get(position).getAccountNumber());

        if (banks.get(position).isSelect()) {
            selectPosition = position;
        }

        holder.radioButtonSelectBank.setChecked((position == selectPosition));

        holder.radioButtonSelectBank.setOnClickListener(view -> {
            if (position != selectPosition) {
                int tempPosition = selectPosition;
                selectPosition = position;

                notifyItemChanged(tempPosition);


                if (tempPosition != -1) {
                    Bank bank = banks.get(tempPosition);

                    bank.setSelect(false);

                    banks.set(tempPosition, bank);
                }

                Bank bank1 = banks.get(selectPosition);
                bank1.setSelect(true);
                banks.set(selectPosition, bank1);

                notifyItemChanged(selectPosition);

                callBack.onClickItem(position, banks.get(position));
            }
        });
        holder.imageViewDelete.setOnClickListener(view -> callBack.onClickDelete(position, banks.get(position)));
    }

    @Override
    public int getItemCount() {
        return banks.size();
    }

    public void setSelectPosition(int i) {
        selectPosition = i;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customTextViewBankName)
        AppCompatTextView customTextViewBankName;
        @BindView(R.id.customTextViewAccountNumber)
        AppCompatTextView customTextViewAccountNumber;
        @BindView(R.id.radioButtonSelectBank)
        RadioButton radioButtonSelectBank;
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

        void onClickItem(int position, Bank bank);

        void onClickDelete(int position, Bank bank);

    }
}
