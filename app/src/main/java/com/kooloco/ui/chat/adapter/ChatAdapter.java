package com.kooloco.ui.chat.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.Chat;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 17/3/16.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    Context context;
    List<Chat> chats;
    private String personalImageUrl;
    private String reciverImageUrl;


    public ChatAdapter(Context context, List<Chat> chats, String personalImageUrl, String reciverImageUrl) {
        this.context = context;
        this.chats = chats;
        this.personalImageUrl = personalImageUrl;
        this.reciverImageUrl = reciverImageUrl;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_chat, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.linearLayoutTopSpace.setVisibility((position == 0) ? View.VISIBLE : View.GONE);

        if (chats.get(position).isUserType()) {
            holder.linearLayoutServiceProvider.setVisibility(View.GONE);
            holder.linearLayoutServiceUser.setVisibility(View.VISIBLE);
            holder.textViewServiceUserMessage.setText(chats.get(position).getMessage());
            if (isShowGroupProfile(position)) {
                holder.imageViewServiceUser.setVisibility(View.VISIBLE);
            } else {
                holder.imageViewServiceUser.setVisibility(View.INVISIBLE);
            }

            if (personalImageUrl.isEmpty()) {
                Picasso.with(context).load(R.drawable.user_round).transform(new CircleTransform()).placeholder(R.drawable.user_round).error(R.drawable.user_round).into(holder.imageViewServiceUser);
            } else {
                Picasso.with(context).load(personalImageUrl).transform(new CircleTransform()).placeholder(R.drawable.user_round).resizeDimen(R.dimen.dp_30,R.dimen.dp_30).centerCrop().error(R.drawable.user_round).into(holder.imageViewServiceUser);
            }

        } else {
            holder.linearLayoutServiceUser.setVisibility(View.GONE);
            holder.linearLayoutServiceProvider.setVisibility(View.VISIBLE);

            holder.textViewServiceProviderMessage.setText(chats.get(position).getMessage());

            if (isShowGroupProfile(position)) {
                holder.imageViewServiceProvider.setVisibility(View.VISIBLE);
            } else {
                holder.imageViewServiceProvider.setVisibility(View.INVISIBLE);
            }

            if (reciverImageUrl.isEmpty()) {
                Picasso.with(context).load(R.drawable.user_round).transform(new CircleTransform()).placeholder(R.drawable.user_round).error(R.drawable.user_round).into(holder.imageViewServiceProvider);
            } else {
                Picasso.with(context).load(reciverImageUrl).transform(new CircleTransform()).placeholder(R.drawable.user_round).resizeDimen(R.dimen.dp_30,R.dimen.dp_30).centerCrop().error(R.drawable.user_round).into(holder.imageViewServiceProvider);
            }

        }

        if (isShowGroupTime(position)) {
            holder.textViewTime.setVisibility(View.VISIBLE);
            holder.textViewTime.setText(chats.get(position).getTime());
        } else {
            holder.textViewTime.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewServiceProviderMessage)
        AppCompatTextView textViewServiceProviderMessage;
        @BindView(R.id.linearLayoutServiceProvider)
        LinearLayout linearLayoutServiceProvider;
        @BindView(R.id.textViewServiceUserMessage)
        AppCompatTextView textViewServiceUserMessage;
        @BindView(R.id.linearLayoutServiceUser)
        LinearLayout linearLayoutServiceUser;
        @BindView(R.id.imageViewServiceProvider)
        ImageView imageViewServiceProvider;
        @BindView(R.id.imageViewServiceUser)
        ImageView imageViewServiceUser;
        @BindView(R.id.textViewTime)
        AppCompatTextView textViewTime;
        @BindView(R.id.linearLayoutTopSpace)
        LinearLayout linearLayoutTopSpace;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private boolean isShowGroupTime(int position) {
        boolean isShow;
        if (position == chats.size() - 1) {
            isShow = true;
        } else {

            String nextText = chats.get(position + 1).getTime();
            String curranText = chats.get(position).getTime();

            if (curranText.equalsIgnoreCase(nextText)) {
                isShow = false;
            } else {
                isShow = true;
            }
        }

        return isShow;
    }

    private boolean isShowGroupProfile(int position) {
        boolean isShow;
        if (position == 0) {
            isShow = true;
        } else {
            boolean prevImage = chats.get(position - 1).isUserType();
            boolean curranImage = chats.get(position).isUserType();

            if (curranImage == prevImage) {
                isShow = false;
            } else {
                isShow = true;
            }
        }

        return isShow;
    }
}