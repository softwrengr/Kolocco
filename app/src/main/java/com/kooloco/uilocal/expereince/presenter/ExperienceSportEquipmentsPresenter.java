package com.kooloco.uilocal.expereince.presenter;
/**
 * Created by hlink on 17/4/18.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Equipment;
import com.kooloco.model.EquipmentResponse;
import com.kooloco.model.Response;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.uilocal.addservice.fragment.ChooseSportEquipmentsFragment;
import com.kooloco.uilocal.expereince.fragment.ExperienceSportEquipmentsFragment;
import com.kooloco.uilocal.expereince.view.ExperienceSportEquipmentsView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class ExperienceSportEquipmentsPresenter extends BasePresenter<ExperienceSportEquipmentsView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public ExperienceSportEquipmentsPresenter() {
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

    public void addEquipments(String expId, String name, final ExperienceSportEquipmentsFragment.CallBackDialog callBackDialog) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);
        map.put("name", name);

        koolocoRepository.addEquipment(map).subscribe(new SubscribeWithView<Response<Equipment>>(view) {
            @Override
            public void onSuccess(Response<Equipment> listResponse) {
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

    public void callWs(List<EquipmentResponse> equipmentResponses, String expId) {
        view.showLoader();

        String equipmentId = "";


        for (EquipmentResponse equipmentResponse : equipmentResponses) {
            for (Equipment equipment : equipmentResponse.getEquipments()) {

                if (equipment.getIsSelected().equalsIgnoreCase("1")) {

                    if (equipmentId.isEmpty()) {
                        equipmentId = equipmentId + equipment.getId();
                    } else {
                        equipmentId = equipmentId + "," + equipment.getId();
                    }
                }

            }
        }

        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);
        map.put("equipment_csv", equipmentId);

        koolocoRepository.setExpEquipment(map).subscribe(new SubscribeWithView<Response>(view) {
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

    public void getEquipments(String expId) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);

        koolocoRepository.equipment(map).subscribe(new SubscribeWithView<Response<EquipmentResponse>>(view) {
            @Override
            public void onSuccess(Response<EquipmentResponse> listResponse) {
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

    public void deleteEquiqment(String id, String expId) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);
        map.put("equipment_id", id);

        koolocoRepository.deleteEquipment(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.hideLoader();
                view.showMessage(listResponse.getMessage());
                getEquipments(expId);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }
}