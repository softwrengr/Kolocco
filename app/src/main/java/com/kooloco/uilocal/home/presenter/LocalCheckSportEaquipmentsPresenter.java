package com.kooloco.uilocal.home.presenter;/**
 * Created by hlink44 on 26/9/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.CheckEquipment;
import com.kooloco.model.CheckEquipmentResponse;
import com.kooloco.model.Equipment;
import com.kooloco.model.ReceiverData;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.expereince.fragment.ExperienceSportEquipmentsFragment;
import com.kooloco.uilocal.home.fragment.LocalCheckSportEaquipmentsFragment;
import com.kooloco.uilocal.home.view.LocalCheckSportEaquipmentsView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@PerActivity
public class LocalCheckSportEaquipmentsPresenter extends BasePresenter<LocalCheckSportEaquipmentsView> {
    @Inject
    KoolocoRepository koolocoRepostry;

    @Inject
    public LocalCheckSportEaquipmentsPresenter() {

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

    public void getData(String orderId) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();

        map.put("booking_id", orderId);

        koolocoRepostry.getEquipmentOrder(map).subscribe(new SubscribeWithView<Response<CheckEquipmentResponse>>(view) {
            @Override
            public void onSuccess(Response<CheckEquipmentResponse> listResponse) {
                view.hideLoader();
                view.setData(listResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void opnChat(ReceiverData receiverData) {
        Bundle bundle = new Bundle();
        bundle.putString("receiverData", new Gson().toJson(receiverData));

        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Chat).addBundle(bundle).start();
    }

    public void callWsSetEquipment(List<CheckEquipment> checkEquipments, String orderId) {
        view.showLoader();

        String equipmentId = "";


        for (CheckEquipment equipment : checkEquipments) {

            if (equipment.isSelect()) {

                if (equipmentId.isEmpty()) {
                    equipmentId = equipmentId + equipment.getId();
                } else {
                    equipmentId = equipmentId + "," + equipment.getId();
                }
            }

        }

        Map<String, String> map = new HashMap<>();
        map.put("equipment_id", equipmentId);
        map.put("booking_id", orderId);

        koolocoRepostry.setEquipmentOrder(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.hideLoader();
                navigator.goBack();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void addEquipments(String bookingId, String name, final LocalCheckSportEaquipmentsFragment.CallBackDialog callBackDialog) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("booking_id", bookingId);
        map.put("name", name);

        koolocoRepostry.addEquipmentOrder(map).subscribe(new SubscribeWithView<Response<CheckEquipment>>(view) {
            @Override
            public void onSuccess(Response<CheckEquipment> listResponse) {
                view.hideLoader();
                view.showMessage(listResponse.getMessage());
                callBackDialog.onSuccess(listResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
                callBackDialog.onFail(e.getMessage());
            }
        });
    }

    public void deleteEquipment(CheckEquipment sport, int pos) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("equipment_id", sport.getId());
        map.put("order_id", sport.getBookingId());

        koolocoRepostry.localOrderEquipmentDelete(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.hideLoader();
                view.deleteEquipmentData(pos);
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }
}
