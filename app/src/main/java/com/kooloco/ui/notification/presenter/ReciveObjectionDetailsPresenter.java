package com.kooloco.ui.notification.presenter;/**
 * Created by hlink44 on 26/9/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ObjectionDetails;
import com.kooloco.model.ReceiverData;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.notification.view.ReciveObjectionDetailsView;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

@PerActivity
public class ReciveObjectionDetailsPresenter extends BasePresenter<ReciveObjectionDetailsView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public ReciveObjectionDetailsPresenter() {

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

    public void openChatFragment(ReceiverData receiverData) {


        Bundle bundle = new Bundle();
        bundle.putString("receiverData", new Gson().toJson(receiverData));

        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Chat).addBundle(bundle).start();
    }

    public void getData(String notificationId) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("notification_id", notificationId);

        koolocoRepository.objectionDetailsVisitor(map).subscribe(new SubscribeWithView<Response<ObjectionDetails>>(view) {
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

    public void openRatingScreen(String orderId) {
       /* Bundle bundle = new Bundle();
        bundle.putString("orderId", orderId);
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.RATING).addBundle(bundle).start();*/

        navigator.openRatingView().hasData(ratingnView -> {
            ratingnView.setOrderId(orderId);
            ratingnView.setIsObjection(true);
        }).replace(true);

    }

    public void openExpDetails(ExpereinceNew expereinceNew) {

        Bundle bundle = new Bundle();
        bundle.putString("expId", expereinceNew.getId());

        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.EXPDETAILSLOCAL).addBundle(bundle).start();
    }
}
