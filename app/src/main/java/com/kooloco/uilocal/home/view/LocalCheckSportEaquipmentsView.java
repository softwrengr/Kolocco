package com.kooloco.uilocal.home.view;
/**
 * Created by hlink44 on 26/9/17.
 */

import com.kooloco.model.CheckEquipmentResponse;
import com.kooloco.model.ReceiverData;
import com.kooloco.ui.base.RootView;

public interface LocalCheckSportEaquipmentsView extends RootView {
    void setData(CheckEquipmentResponse data);

    void setOrderId(String orderId);

    void setRecevierData(ReceiverData receiverData);

    void setOrderStatus(String orderStatus);

    void setChatStatus(String chatStatus);

    void deleteEquipmentData(int pos);
}
