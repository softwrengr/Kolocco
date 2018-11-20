package com.kooloco.uilocal.organaization.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.kooloco.R;
import com.kooloco.model.OrgLocal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by hlink44 on 14/9/17.
 */

public class OrgAssignLocalListAdapter extends RecyclerView.Adapter<OrgAssignLocalListAdapter.ViewHolder> {
    Context context;
    private List<OrgLocal> orgLocals;
    private CallBack callBack;

    public OrgAssignLocalListAdapter(Context context, List<OrgLocal> orgLocals, CallBack callBack) {
        this.context = context;
        this.orgLocals = orgLocals;
        this.callBack = callBack;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_org_assign_local, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Glide.with(context).load(orgLocals.get(position).getImageUrl()).asBitmap().transform(new CropCircleTransformation(context)).placeholder(R.drawable.user_round).into(holder.imageViewProfile);

        holder.customTextViewName.setText(orgLocals.get(position).getName());

        holder.checkBoxSelect.setChecked(orgLocals.get(position).isSelect());

        holder.imageViewCancel.setVisibility(orgLocals.get(position).getIsAdded().equalsIgnoreCase("1") ? View.VISIBLE : View.GONE);

        holder.frameLayoutClose.setVisibility(orgLocals.get(position).getIsAdded().equalsIgnoreCase("1") ? View.VISIBLE : View.GONE);

        holder.checkBoxSelect.setVisibility(!orgLocals.get(position).getIsAdded().equalsIgnoreCase("1") ? View.VISIBLE : View.GONE);

        holder.checkBoxSelect.setOnClickListener(view -> {

            //holder.checkBoxSelect.setChecked(!holder.checkBoxSelect.isChecked());

            OrgLocal orgLocal = orgLocals.get(position);
            orgLocal.setSelect(holder.checkBoxSelect.isChecked());

            orgLocals.set(position, orgLocal);

            //notifyItemChanged(position);
        });

        holder.linearLayoutRow.setOnClickListener(view -> {
            callBack.onClickItem(position, orgLocals.get(position));
        });

        holder.imageViewCancel.setOnClickListener(view -> {
            callBack.onDelete(position, orgLocals.get(position));
        });

        holder.frameLayoutClose.setOnClickListener(view -> {
            callBack.onDelete(position, orgLocals.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return orgLocals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewProfile)
        ImageView imageViewProfile;
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.checkBoxSelect)
        CheckBox checkBoxSelect;
        @BindView(R.id.linearLayoutRow)
        LinearLayout linearLayoutRow;
        @BindView(R.id.imageViewCancel)
        ImageView imageViewCancel;
        @BindView(R.id.frameLayoutClose)
        FrameLayout frameLayoutClose;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface CallBack {
        void onDelete(int position, OrgLocal orgLocal);

        void onClickItem(int position, OrgLocal orgLocal);

    }
}
