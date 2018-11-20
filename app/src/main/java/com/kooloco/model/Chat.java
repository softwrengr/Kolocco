package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kooloco.gsonadapter.DateConvert;

import java.io.File;

/**
 * Created by hlink44 on 17/3/16.
 */
public class Chat {
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("sender_name")
    @Expose
    private String senderName;
    @SerializedName("sender_device_type")
    @Expose
    private String senderDeviceType;
    @SerializedName("sender_device_token")
    @Expose
    private String senderDeviceToken;
    @SerializedName("sender_image_url")
    @Expose
    private String senderImageUrl;
    @SerializedName("receiver_id")
    @Expose
    private String receiverId;
    @SerializedName("receiver_name")
    @Expose
    private String receiverName;
    @SerializedName("receiver_image_url")
    @Expose
    private String receiverImage;
    @SerializedName("receiver_device_type")
    @Expose
    private String receiverDeviceType;
    @SerializedName("receiver_device_token")
    @Expose
    private String receiverDeviceToken;

    //Set Document id
    private String chatId;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("message_type")
    @Expose
    private String messageType;

    //SetUserType
    private boolean userType;

    @JsonAdapter(DateConvert.class)
    @SerializedName("chat_time_utc")
    @Expose
    private String time = "";

    @SerializedName("chat_read")
    @Expose
    private Boolean chatRead;

    @SerializedName("uniq_id")
    @Expose
    private String uniqId = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isUserType() {
        return userType;
    }

    public void setUserType(boolean userType) {
        this.userType = userType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderDeviceType() {
        return senderDeviceType;
    }

    public void setSenderDeviceType(String senderDeviceType) {
        this.senderDeviceType = senderDeviceType;
    }

    public String getSenderDeviceToken() {
        return senderDeviceToken;
    }

    public void setSenderDeviceToken(String senderDeviceToken) {
        this.senderDeviceToken = senderDeviceToken;
    }

    public String getSenderImageUrl() {
        return senderImageUrl;
    }

    public void setSenderImageUrl(String senderImageUrl) {
        this.senderImageUrl = senderImageUrl;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverImage() {
        return receiverImage;
    }

    public void setReceiverImage(String receiverImage) {
        this.receiverImage = receiverImage;
    }

    public String getReceiverDeviceType() {
        return receiverDeviceType;
    }

    public void setReceiverDeviceType(String receiverDeviceType) {
        this.receiverDeviceType = receiverDeviceType;
    }

    public String getReceiverDeviceToken() {
        return receiverDeviceToken;
    }

    public void setReceiverDeviceToken(String receiverDeviceToken) {
        this.receiverDeviceToken = receiverDeviceToken;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Boolean getChatRead() {
        return chatRead;
    }

    public void setChatRead(Boolean chatRead) {
        this.chatRead = chatRead;
    }

    public String getUniqId() {
        return uniqId;
    }

    public void setUniqId(String uniqId) {
        this.uniqId = uniqId;
    }

    @SerializedName("chat_count")
    @Expose
    private String chatCount="0";

    public String getChatCount() {
        return chatCount;
    }

    public void setChatCount(String chatCount) {
        this.chatCount = chatCount;
    }



    @SerializedName("local_id")
    @Expose
    private String localId="";

    @SerializedName("order_id")
    @Expose
    private String orderId="";

    @SerializedName("visitor_id")
    @Expose
    private String visitorId="";

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }
}
