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
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.modify.view.ModifyAddressView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

@PerActivity
public class ModifyAddressPresenter extends BasePresenter<ModifyAddressView> {
    @Inject
    Session session;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public ModifyAddressPresenter() {
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

    public void callWs(boolean isLocation, Order order, String address, String let, String lng, CallBack callBack) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();

        map.put("order_id", order.getId());
        map.put("user_id", session.getUser().getId());
        map.put("is_modify_duration", "0");
        map.put("meeting_address", address);
        map.put("meeting_latitude", let);
        map.put("meeting_longitude", lng);
        map.put("duration", "");

        koolocoRepository.setModifyLocationDuration(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.hideLoader();
                callBack.onSucess();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public interface CallBack {
        void onSucess();
    }

    public void openChat(ReceiverData receiverData) {

        Bundle bundle = new Bundle();
        bundle.putString("receiverData", new Gson().toJson(receiverData));
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Chat).addBundle(bundle).start();

    }
}