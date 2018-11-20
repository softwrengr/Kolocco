package com.kooloco.ui.invite.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.model.Invite;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class InviteListAdapter extends RecyclerView.Adapter<InviteListAdapter.ViewHolder> {
    Context context;
    List<Invite> invites;
    int selectAll = 0;

    public InviteListAdapter(Context context, List<Invite> invites, int selectAll) {
        this.context = context;
        this.invites = invites;
        this.selectAll = selectAll;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_invite_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.with(context).load(invites.get(position).getImageUrl()).into(holder.imageViewProfile);
        holder.customTextViewName.setText(invites.get(position).getName());

        holder.customTextViewInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imageTick.setVisibility(View.VISIBLE);
                holder.customTextViewInvite.setVisibility(View.GONE);
            }
        });

        if(selectAll==1){
            holder.imageTick.setVisibility(View.VISIBLE);
            holder.customTextViewInvite.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return invites.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewProfile)
        ImageView imageViewProfile;
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.customTextViewInvite)
        AppCompatTextView customTextViewInvite;
        @BindView(R.id.imageTick)
        ImageView imageTick;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
