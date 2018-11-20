package com.kooloco.uilocal.Notification.adapter;

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
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_notification, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.linearLayoutObjection.setVisibility((notifications.get(position).getStatus() == 1) ? View.VISIBLE : View.GONE);
        holder.linearLayoutOrganization.setVisibility((notifications.get(position).getStatus() == 4) ? View.VISIBLE : View.GONE);

        holder.customTextTitle.setText(notifications.get(position).getTitle());
        holder.customTextSubTitle.setText(notifications.get(position).getSubTitle());

        if (notifications.get(position).getSubTitle() == null) {
            notifications.get(position).setSubTitle("");
        }

        holder.customTextSubTitle.setVisibility(notifications.get(position).getSubTitle().isEmpty() ? View.GONE : View.VISIBLE);

        String date = TimeConvertUtils.dateAndTimeGet(TimeConvertUtils.dateTimeConvertLocalToLocal(notifications.get(position).getTime(), "yyyy-MM-dd HH:mm:ss", "dd MMM, yyyy HH:mm:ss"), "dd MMM, yyyy HH:mm:ss");

        date = TimeConvertUtils.dateTimeConvertLocalToLocalNotification(date, "MMM dd, yyyy", "dd MMM yyyy");

        holder.customTextViewTime.setText(date);

        holder.customTextAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickAccept(notifications.get(position));
            }
        });
        holder.linearLayoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickRow(notifications.get(position));
            }
        });

        holder.customTextModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickModify(notifications.get(position));

            }
        });

        holder.customTextOrgAccept.setOnClickListener(view -> callBack.onClickOrgAccept(notifications.get(position)));
        holder.customTextOrgDecline.setOnClickListener(view -> callBack.onClickOrgDecline(notifications.get(position)));

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
        @BindView(R.id.customTextAccept)
        AppCompatTextView customTextAccept;
        @BindView(R.id.customTextModify)
        AppCompatTextView customTextModify;
        @BindView(R.id.linearLayoutObjection)
        LinearLayout linearLayoutObjection;
        @BindView(R.id.linearLayoutContent)
        LinearLayout linearLayoutContent;

        @BindView(R.id.customTextOrgAccept)
        AppCompatTextView customTextOrgAccept;

        @BindView(R.id.customTextOrgDecline)
        AppCompatTextView customTextOrgDecline;

        @BindView(R.id.linearLayoutOrganization)
        LinearLayout linearLayoutOrganization;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickRow(Notification notification);

        void onClickModify(Notification notification);

        void onClickAccept(Notification notification);

        void onClickOrgAccept(Notification notification);

        void onClickOrgDecline(Notification notification);

    }

}
