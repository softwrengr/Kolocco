package com.kooloco.uilocal.Notification.presenter;/**
 * Created by hlink44 on 9/10/17.
 */

import android.os.Bundle;

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ObjectionDetails;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.Notification.view.ModifyObjectionView;
import com.kooloco.uilocal.Notification.view.ReceivedObjectionLocalView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

@PerActivity
public class ReceivedObjectionLocalPresenter extends BasePresenter<ReceivedObjectionLocalView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public ReceivedObjectionLocalPresenter() {

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

    public void openModifyObjection(String id) {
        navigator.openModifyObjectionLocalView().hasData(new Passable<ModifyObjectionView>() {
            @Override
            public void passData(ModifyObjectionView modifyObjectionView) {
                modifyObjectionView.setNotificationId(id);
            }
        }).replace(true);
    }

    public void getData(String notificationId) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("notification_id", notificationId);

        koolocoRepository.objectionDetails(map).subscribe(new SubscribeWithView<Response<ObjectionDetails>>(view) {
            @Override
            public void onSuccess(Response<ObjectionDetails> objectionDetailsResponse) {
                view.hideLoader();
                view.setData(objectionDetailsResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void openExpDetails(ExpereinceNew expereinceNew) {

        Bundle bundle = new Bundle();
        bundle.putString("expId", expereinceNew.getId());

        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.EXPDETAILSLOCALSIDE).addBundle(bundle).start();
    }
}
