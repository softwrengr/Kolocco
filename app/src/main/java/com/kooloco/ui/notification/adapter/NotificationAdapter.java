package com.kooloco.ui.notification.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.Notification;
import com.kooloco.util.TimeConvertUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    Context context;
    List<Notification> notifications;
    private CallBack callBack;

    public NotificationAdapter(Context context, List<Notification> notifications, CallBack callBack) {
        this.context = context;
        this.notifications = notifications;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_notification, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.linearLayoutFullContent.setVisibility((notifications.get(position).getStatus() == 0 || notifications.get(position).getStatus() == 5) ? View.VISIBLE : View.GONE);
        holder.linearLayoutObjection.setVisibility((notifications.get(position).getStatus() == 1) ? View.VISIBLE : View.GONE);

        holder.customTextTitle.setText(notifications.get(position).getTitle());
        holder.customTextSubTitle.setText(notifications.get(position).getSubTitle());
        holder.customTextSubTitle.setVisibility(notifications.get(position).getSubTitle().isEmpty() ? View.GONE : View.VISIBLE);


        String date = TimeConvertUtils.dateAndTimeGet(TimeConvertUtils.dateTimeConvertLocalToLocal(notifications.get(position).getTime(), "yyyy-MM-dd HH:mm:ss", "dd MMM, yyyy HH:mm:ss"), "dd MMM, yyyy HH:mm:ss");

        date = TimeConvertUtils.dateTimeConvertLocalToLocalNotification(date, "MMM dd, yyyy", "dd MMM yyyy");

        holder.customTextViewTime.setText(date);

        holder.linearLayoutContent.setOnClickListener(view -> {
            callBack.onClickRow(notifications.get(position));

        });

        holder.customTextChat.setOnClickListener(view -> callBack.onClickChat(notifications.get(position)));

        holder.customTextObjAccept.setOnClickListener(view -> callBack.onClickAcceptObjection(notifications.get(position)));

        holder.customTextRequestToAdmin.setOnClickListener(view -> callBack.onClickRequestAdmin(notifications.get(position)));

        holder.customTextAccept.setOnClickListener(view -> callBack.onClickAccept(notifications.get(position)));
        holder.customTextDecline.setOnClickListener(view -> callBack.onClickReject(notifications.get(position)));
        holder.customTextNotify.setOnClickListener(view -> callBack.onClickNotify(notifications.get(position)));


        holder.customTextRequestToAdmin.setText(notifications.get(position).getRequestToAdmin().equalsIgnoreCase("1") ? context.getResources().getString(R.string.send_to_support) : context.getResources().getString(R.string.notification_request_admin));

    }


    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customTextTitle)
        AppCompatTextView customTextTitle;
        @BindView(R.id.customTextViewTime)
        AppCompatTextView customTextViewTime;
        @BindView(R.id.customTextSubTitle)
        AppCompatTextView customTextSubTitle;
        @BindView(R.id.customTextChat)
        AppCompatTextView customTextChat;
        @BindView(R.id.customTextNotify)
        AppCompatTextView customTextNotify;
        @BindView(R.id.customTextAccept)
        AppCompatTextView customTextAccept;
        @BindView(R.id.customTextDecline)
        AppCompatTextView customTextDecline;
        @BindView(R.id.linearLayoutFullContent)
        LinearLayout linearLayoutFullContent;
        @BindView(R.id.customTextObjAccept)
        AppCompatTextView customTextObjAccept;
        @BindView(R.id.customTextRequestToAdmin)
        AppCompatTextView customTextRequestToAdmin;
        @BindView(R.id.linearLayoutObjection)
        LinearLayout linearLayoutObjection;
        @BindView(R.id.linearLayoutContent)
        LinearLayout linearLayoutContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickRow(Notification notification);

        void onClickChat(Notification notification);

        void onClickAcceptObjection(Notification notification);

        void onClickRequestAdmin(Notification notification);

        void onClickNotify(Notification notification);

        void onClickAccept(Notification notification);

        void onClickReject(Notification notification);

    }

}
