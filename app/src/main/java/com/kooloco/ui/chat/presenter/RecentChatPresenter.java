package com.kooloco.ui.chat.presenter;/**
 * Created by hlink44 on 26/9/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Chat;
import com.kooloco.model.ReceiverData;
import com.kooloco.model.RecentChat;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.chat.view.RecentChatView;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.util.SubscribeWithView;

import java.util.List;

@PerActivity
public class RecentChatPresenter extends BasePresenter<RecentChatView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public RecentChatPresenter() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }


    public void openChat(Chat chat) {


        boolean isSender = chat.getSenderId().equalsIgnoreCase(session.getUser().getId());


        ReceiverData receiverData = new ReceiverData();

        receiverData.setUser_id(isSender ? chat.getReceiverId() : chat.getSenderId());
        receiverData.setName(isSender ? chat.getReceiverName() : chat.getSenderName());
        receiverData.setImageUrl(isSender ? chat.getReceiverImage() : chat.getSenderImageUrl());
        receiverData.setDeviceType(isSender ? chat.getReceiverDeviceType() : chat.getSenderDeviceType());
        receiverData.setDeviceToken(isSender ? chat.getReceiverDeviceToken() : chat.getSenderDeviceToken());

        receiverData.setOrderId(chat.getOrderId());

        Bundle bundle = new Bundle();
        bundle.putString("receiverData", new Gson().toJson(receiverData));
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Chat).addBundle(bundle).start();
    }

    public void openNotification(boolean isLocal) {

        navigator.openIsloatedFullActivity().setPage(isLocal ? AppNavigator.Pages.NotificationLocal : AppNavigator.Pages.Notification).start();
    }
}
