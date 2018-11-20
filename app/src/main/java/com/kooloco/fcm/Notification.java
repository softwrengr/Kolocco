package com.kooloco.fcm;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink21 on 14/10/16.
 */

public class Notification {
    @SerializedName("msg")
    private String message;
    @SerializedName("notification_tag")
    private String notificationTag;

    @SerializedName("order_id")
    private String orderId;

    @SerializedName("sender_id")
    private String senderId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotificationTag() {
        return notificationTag;
    }

    public void setNotificationTag(String notificationTag) {
        this.notificationTag = notificationTag;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
