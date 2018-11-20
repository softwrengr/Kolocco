/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kooloco.fcm;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.core.Common;
import com.kooloco.model.User;
import com.kooloco.ui.MainActivity;
import com.kooloco.uilocal.MainLocalActivity;

import java.util.Date;

import de.greenrobot.event.EventBus;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";


    Gson gson = new Gson();

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            String notificationJSON = remoteMessage.getData().get("message");

            Notification notification = gson.fromJson(notificationJSON, Notification.class);

            handelNotification(notification);

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param
     */

    private void handelNotification(Notification notification) {

        SharedPreferences appPreferences = getSharedPreferences(Common.SHARED_PREF_NAME, MODE_PRIVATE);

        if (appPreferences.getBoolean("isLogin", false)) {

            String userJSON = appPreferences.getString(Common.USER_JSON, "");
            User user = gson.fromJson(userJSON, User.class);

            if (user.getRole().equalsIgnoreCase("L")) {
                sendNotification(notification, false);
            } else {
                sendNotification(notification, true);
            }
            EventBus.getDefault().post(EventBusAction.NOTIFICATIONCOUNTDISPLAYVISITOR);
            EventBus.getDefault().post(EventBusAction.NOTIFICATIONCOUNTDISPLAYLOCAL);


        }
    }

    private void sendNotification(Notification messageBody, boolean isvisiotr) {

        //It is used to set chat push notification
        if (messageBody.getNotificationTag().equalsIgnoreCase(com.kooloco.constant.Common.Local.NEW_CHAT_MESSAGE)) {

            SharedPreferences sharedPreferences = getSharedPreferences(Common.SHARED_PREF_NAME, MODE_PRIVATE);

            boolean isChatScreen = sharedPreferences.getBoolean("isChatScreenD", false);
            String senderid = sharedPreferences.getString("chatReceiverId", "");

            if (isChatScreen) {
                if (senderid.equalsIgnoreCase(messageBody.getSenderId())) {
                    return;
                }
            }

        }

        if (messageBody.getNotificationTag().equalsIgnoreCase(com.kooloco.constant.Common.Visitor.NEW_LOCAL_ADMIN_APPROVE)) {
            isvisiotr = false;
        }

        Intent intent;

        intent = new Intent(this, isvisiotr ? MainActivity.class : MainLocalActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("data", gson.toJson(messageBody));


        int id = 0;

        if (messageBody.getNotificationTag().equalsIgnoreCase(com.kooloco.constant.Common.Local.NEW_CHAT_MESSAGE)) {
            id = Integer.parseInt(messageBody.getSenderId());
        } else if (messageBody.getNotificationTag().equalsIgnoreCase(com.kooloco.constant.Common.Visitor.NEW_LOCAL_ADMIN_APPROVE) || messageBody.getNotificationTag().equalsIgnoreCase(com.kooloco.constant.Common.Local.ORG_DELETE) || messageBody.getNotificationTag().equalsIgnoreCase(com.kooloco.constant.Common.Local.ORG_DELETE_LOCAL) || messageBody.getNotificationTag().equalsIgnoreCase(com.kooloco.constant.Common.Local.ORG_REQUEST)) {
            EventBus.getDefault().post(EventBusAction.UPDATELISTLOCAL);
            id = com.kooloco.constant.Common.Visitor.NEW_LOCAL_ADMIN_APPROVE_ID;
        } else {
            id = getUniqId();
        }


        //It is used to create to Chanale

        String channelId = "10001";
        CharSequence channelName = "All Notification";

        if (messageBody.getNotificationTag().equalsIgnoreCase(com.kooloco.constant.Common.Local.NEW_CHAT_MESSAGE)) {
            channelId = "10002";
            channelName = "Chat Message";
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, id, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(getString(R.string.app_name))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody.getMessage()))
                .setContentText(messageBody.getMessage())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setColor(Color.parseColor("#3fc1c9"))
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManager.notify(id, notificationBuilder.build());

        //This code for set oreo notification

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            notificationManager.createNotificationChannel(notificationChannel);
        }

        EventBus.getDefault().post(EventBusAction.ACCEPTREFRESE);
        EventBus.getDefault().post(EventBusAction.PENDINGREFRESE);

        EventBus.getDefault().post(EventBusAction.NOTIFICATIONLOCAL);
        EventBus.getDefault().post(EventBusAction.NOTIFICATIONVISITOR);


        if (messageBody.getNotificationTag().equalsIgnoreCase(com.kooloco.constant.Common.Local.ACCEPT_MODIFICATION)) {
            EventBus.getDefault().post(EventBusAction.ORDERDETAILSLOCAL);
        }

        if (messageBody.getNotificationTag().equalsIgnoreCase(com.kooloco.constant.Common.Local.EXP_ADMIN_ACCEPTED)) {
            EventBus.getDefault().post(EventBusAction.NOTIFICATIONEXPAPPROVE);
            EventBus.getDefault().post(EventBusAction.LOCALEXPADD);
        }
        if (messageBody.getNotificationTag().equalsIgnoreCase(com.kooloco.constant.Common.Local.EXP_ADMIN_REJECT)) {
            EventBus.getDefault().post(EventBusAction.NOTIFICATIONEXPREJEXT);
            EventBus.getDefault().post(EventBusAction.LOCALEXPADD);
        }

        if (messageBody.getNotificationTag().equalsIgnoreCase(com.kooloco.constant.Common.Local.ORG_ADMIN_ACCEPTED)) {
            EventBus.getDefault().post(EventBusAction.UPDATEORG);
        }

        if (messageBody.getNotificationTag().equalsIgnoreCase(com.kooloco.constant.Common.Visitor.MODIFY_DURATION) || messageBody.getNotificationTag().equalsIgnoreCase(com.kooloco.constant.Common.Visitor.MODIFY_LOCATION)) {
            EventBus.getDefault().post(EventBusAction.ORDERDETAILSVISITOR);
        }

    }

    private int getUniqId() {
        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int notificationId = Integer.valueOf(last4Str);
        return notificationId;
    }

}