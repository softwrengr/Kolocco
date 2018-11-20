package com.kooloco.ui.expereince.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;
import com.kooloco.model.OtherDetailsAnotherFields;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class ExpAnotherFieldsAdapter extends RecyclerView.Adapter<ExpAnotherFieldsAdapter.ViewHolder> {
    Context context;
    CallBack callBack;
    List<OtherDetailsAnotherFields> otherDetailsAnotherFields;


    public ExpAnotherFieldsAdapter(Context context, List<OtherDetailsAnotherFields> otherDetailsAnotherFields, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        this.otherDetailsAnotherFields = otherDetailsAnotherFields;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.exp_row_visitor_and_local_details_extra_fields, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.textViewExpAddFieldTitle.setText(otherDetailsAnotherFields.get(position).getTitle());
        holder.textViewExpAddFieldsDesc.setText(otherDetailsAnotherFields.get(position).getDesc());

        holder.recyclerImages.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerImages.setAdapter(new ExpImagesAdapter(context, otherDetailsAnotherFields.get(position).getAddImages(), imageUrl -> callBack.onClickItem(imageUrl)));
    }

    @Override
    public int getItemCount() {
        return otherDetailsAnotherFields.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewExpAddFieldTitle)
        AppCompatTextView textViewExpAddFieldTitle;
        @BindView(R.id.textViewExpAddFieldsDesc)
        AppCompatTextView textViewExpAddFieldsDesc;
        @BindView(R.id.recyclerImages)
        RecyclerView recyclerImages;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickItem(String imageUrl);
    }
}

