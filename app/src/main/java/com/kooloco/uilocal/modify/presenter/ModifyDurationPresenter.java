package com.kooloco.uilocal.modify.presenter;
/**
 * Created by hlink on 20/1/18.
 */


import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Order;
import com.kooloco.model.ReceiverData;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.modify.view.ModifyAddressView;
import com.kooloco.uilocal.modify.view.ModifyDurationView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@PerActivity
public class ModifyDurationPresenter extends BasePresenter<ModifyDurationView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public ModifyDurationPresenter() {
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

    public void openMettingLocation(boolean isLocation, Order order) {
        navigator.openModifyLocation().hasData(new Passable<ModifyAddressView>() {
            @Override
            public void passData(ModifyAddressView modifyAddressView) {
                modifyAddressView.setIsLocation(isLocation);
                modifyAddressView.setOrder(order);

            }
        }).replace(true);
    }

    public void getData(String id) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();

        map.put("order_id", id);
        map.put("user_id", session.getUser().getId());

        koolocoRepository.getDurationLocal(map).subscribe(new SubscribeWithView<Response<List<String>>>(view) {
            @Override
            public void onSuccess(Response<List<String>> listResponse) {
                view.hideLoader();
                view.setData(listResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
                view.setData(new ArrayList<>());
            }
        });
    }

    public void callWs(boolean isLocation, Order order, String duration) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();

        map.put("order_id", order.getId());
        map.put("user_id", session.getUser().getId());
        map.put("is_modify_duration", "1");
        map.put("meeting_address", "");
        map.put("meeting_latitude", "");
        map.put("meeting_longitude", "");
        map.put("duration", duration);

        koolocoRepository.setModifyLocationDuration(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.hideLoader();
                if (isLocation) {
                    openMettingLocation(isLocation, order);
                } else {
                    navigator.goBack();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
                view.setData(new ArrayList<>());
            }
        });
    }

    public void openChat(ReceiverData receiverData) {

        Bundle bundle = new Bundle();
        bundle.putString("receiverData", new Gson().toJson(receiverData));
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Chat).addBundle(bundle).start();

    }
}