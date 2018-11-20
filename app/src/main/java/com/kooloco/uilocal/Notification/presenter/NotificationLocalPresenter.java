package com.kooloco.uilocal.Notification.presenter;/**
 * Created by hlink44 on 9/10/17.
 */

import android.os.Bundle;

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.Notification;
import com.kooloco.model.Response;
import com.kooloco.model.User;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.Notification.view.ModifyObjectionView;
import com.kooloco.uilocal.Notification.view.NotificationLocalView;
import com.kooloco.uilocal.Notification.view.ReceivedObjectionLocalView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class NotificationLocalPresenter extends BasePresenter<NotificationLocalView> {
    @Inject
    KoolocoRepository koolocoRepostry;

    @Inject
    Session session;

    @Inject
    public NotificationLocalPresenter() {

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

    public void getData(int page, boolean isLoading) {

        if (isLoading) {
            view.showLoader();
        }
        Map<String, String> map = new HashMap<>();

        map.put("user_id", session.getUser().getId());
        map.put("page", "" + page);
        map.put("role", "L");

        koolocoRepostry.getNotifications(map).subscribe(new SubscribeWithView<Response<List<Notification>>>(view) {
            @Override
            public void onSuccess(Response<List<Notification>> listResponse) {
                if (isLoading) {
                    view.hideLoader();
                }
                view.setData(listResponse.getData(), page);
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ServerException) {
                    ServerException exception = (ServerException) e;
                    if (page == 1) {
                        //super.onError(e);
                    }
                } else {
                    super.onError(e);
                }

                if (isLoading) {
                    view.hideLoader();
                }
                view.setData(new ArrayList<>(), page);
            }
        });
    }

    public void openReceivedObjection(Notification notification) {
        Bundle bundleData = new Bundle();
        bundleData.putString("notificationId", notification.getId());
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.LOCALRECIVEOBJECTION).addBundle(bundleData).start();

    }

    public void openModifyObjection(Notification notification) {
        navigator.openModifyObjectionLocalView().hasData(new Passable<ModifyObjectionView>() {
            @Override
            public void passData(ModifyObjectionView modifyObjectionView) {
                modifyObjectionView.setNotificationId(notification.getId());
                modifyObjectionView.setIsOpenNotification(true);
            }
        }).replace(true);
    }

    public void callOrgAccept(String id, BaseFragment.CallBackOrderAction callBackOrderAction) {

        view.showLoader();
        Map<String, String> map = new HashMap<>();

        map.put("user_id", session.getUser().getId());
        map.put("notification_id", id);

        koolocoRepostry.acceptOrg(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.hideLoader();

                User user = session.getUser();
                user.setIsAffilated("1");
                session.setUser(user);

                view.showMessage(listResponse.getMessage());
                callBackOrderAction.onSuccess(listResponse);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
                callBackOrderAction.onError(e.getMessage());
            }
        });
    }

    public void callOrgDecline(String id, BaseFragment.CallBackOrderAction callBackOrderAction) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();

        map.put("user_id", session.getUser().getId());
        map.put("notification_id", id);


        koolocoRepostry.declineOrg(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.hideLoader();
                view.showMessage(listResponse.getMessage());
                callBackOrderAction.onSuccess(listResponse);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
                callBackOrderAction.onError(e.getMessage());
            }
        });
    }
}
