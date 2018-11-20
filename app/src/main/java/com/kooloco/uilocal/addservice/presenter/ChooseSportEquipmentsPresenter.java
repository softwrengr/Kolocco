package com.kooloco.uilocal.addservice.presenter;/**
 * Created by hlink44 on 4/10/17.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Equipment;
import com.kooloco.model.EquipmentResponse;
import com.kooloco.model.Response;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.uilocal.addservice.fragment.ChooseSportEquipmentsFragment;
import com.kooloco.uilocal.addservice.view.ChooseSportEquipmentsView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

@PerActivity
public class ChooseSportEquipmentsPresenter extends BasePresenter<ChooseSportEquipmentsView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public ChooseSportEquipmentsPresenter() {

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

    public void openSetSchdule() {
        navigator.openSetAvilabilitesView().replace(true);
    }

    public void getEquipments() {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());

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

    public void callWs(String equipmentId, String isEdit) {
        view.showLoader();

    /*    String equipmentId = "";

        for (EquipmentResponse equipmentResponse1 : equipmentResponse) {

            for (Equipment equipment : equipmentResponse1.getEquipments()) {

                if (equipment.getIsSelected().equalsIgnoreCase("1")) {

                    if (equipmentId.isEmpty()) {
                        equipmentId = equipmentId + equipment.getId();
                    } else {
                        equipmentId = equipmentId + "," + equipment.getId();
                    }
                }
            }
        }*/

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("equipment_id", equipmentId);
        map.put("is_edit", isEdit);


        koolocoRepository.setSportEquipments(map).subscribe(new SubscribeWithView<Response<User>>(view) {
            @Override
            public void onSuccess(Response<User> listResponse) {
                view.hideLoader();
         /*       openSetSchdule();*/
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }

    public void addEquipments(String id, String name, final ChooseSportEquipmentsFragment.CallBackDialog callBackDialog) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("sport_id", id);
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

    public void deleteEquiqment(String id) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("equipment_id", id);

        koolocoRepository.deleteEquipment(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.hideLoader();
                view.showMessage(listResponse.getMessage());
                getEquipments();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }
}
