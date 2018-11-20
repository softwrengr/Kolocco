package com.kooloco.uilocal.expereince.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.AddImages;
import com.kooloco.model.OtherDetailsAnotherFields;
import com.kooloco.util.FirstCapEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class OtherDetialsAnotherFieldsAdapter extends RecyclerView.Adapter<OtherDetialsAnotherFieldsAdapter.ViewHolder> {
    Context context;
    CallBack callBack;
    List<OtherDetailsAnotherFields> otherDetailsAnotherFields;


    public OtherDetialsAnotherFieldsAdapter(Context context, List<OtherDetailsAnotherFields> otherDetailsAnotherFields, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        this.otherDetailsAnotherFields = otherDetailsAnotherFields;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.exp_local_row_exp_other_details_another_fields, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.editTextOtherDetailsAnotherFieldsName.setText(otherDetailsAnotherFields.get(position).getTitle());
        holder.editTextOtherDetailsAnotherFieldsDesc.setText(otherDetailsAnotherFields.get(position).getDesc());

        holder.recyclerImageList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerImageList.setAdapter(new AddDetailsImagesAdapter(context, otherDetailsAnotherFields.get(position).getAddImages(), false, new AddDetailsImagesAdapter.CallBack() {
            @Override
            public void onClickSelect(AddImages addImages) {

            }

            @Override
            public void onClickDelete(AddImages addImages) {

            }
        }));

        holder.imageViewCancel.setOnClickListener(view -> {
            callBack.onClickItemDelete(otherDetailsAnotherFields.get(position), position);
        });
        holder.imageViewEdit.setOnClickListener(view -> {
            callBack.onClickItemEdit(otherDetailsAnotherFields.get(position), position);
        });

        holder.linearLayoutRow.setOnClickListener(view -> {
            callBack.onClickItem(otherDetailsAnotherFields.get(position), position);

        });
    }

    @Override
    public int getItemCount() {
        return otherDetailsAnotherFields.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.editTextOtherDetailsAnotherFieldsName)
        AppCompatTextView editTextOtherDetailsAnotherFieldsName;

        @BindView(R.id.editTextOtherDetailsAnotherFieldsDesc)
        FirstCapEditText editTextOtherDetailsAnotherFieldsDesc;

        @BindView(R.id.recyclerImageList)
        RecyclerView recyclerImageList;
        @BindView(R.id.imageViewEdit)
        ImageView imageViewEdit;
        @BindView(R.id.imageViewCancel)
        ImageView imageViewCancel;
        @BindView(R.id.linearLayoutRow)
        LinearLayout linearLayoutRow;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickItem(OtherDetailsAnotherFields otherDetailsAnotherFields, int pos);

        void onClickItemDelete(OtherDetailsAnotherFields otherDetailsAnotherFields, int pos);

        void onClickItemEdit(OtherDetailsAnotherFields otherDetailsAnotherFields, int pos);

    }
}

