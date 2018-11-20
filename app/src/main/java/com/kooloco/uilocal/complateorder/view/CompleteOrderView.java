package com.kooloco.uilocal.complateorder.view;
/**
 * Created by hlink44 on 9/10/17.
 */

import com.kooloco.model.Order;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface CompleteOrderView extends RootView {
    void setData(List<Order> ordersComplete, int page, String orderCount);
}
