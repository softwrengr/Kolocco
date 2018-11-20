package com.kooloco.ui.visitor.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.model.AddParticipants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class AddParticipantsAdapter extends RecyclerView.Adapter<AddParticipantsAdapter.ViewHolder> {
    Context context;
    List<AddParticipants> strings;
    CallBack callBack;

    public AddParticipantsAdapter(Context context, List<AddParticipants> strings, CallBack callBack) {
        this.context = context;
        this.strings = strings;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_visitor_participants_email, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.customTextViewAddParticipants.setText(strings.get(position).getEmail());

        holder.customTextViewAddParticipants.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (position < strings.size()) {
                    AddParticipants addParticipants = strings.get(position);
                    addParticipants.setEmail(holder.customTextViewAddParticipants.getText().toString());
                    strings.set(position, addParticipants);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customTextViewAddParticipants)
        AppCompatEditText customTextViewAddParticipants;
        @BindView(R.id.imageViewClose)
        ImageView imageViewClose;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void setData(List<String> strings) {
        strings.clear();
        strings.addAll(strings);

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void removeDataPosition(int position) {
        strings.remove(position);
    }

    public interface CallBack {
        void onClickDelete(int position);
    }
}
