package com.kooloco.uilocal.modify.view;

import com.kooloco.model.Order;
import com.kooloco.ui.base.RootView;

/**
 * Created by hlink on 20/1/18.
 */

public interface ModifyAddressView extends RootView {

    void setIsLocation(boolean isLocation);

    void setOrder(Order order);
}