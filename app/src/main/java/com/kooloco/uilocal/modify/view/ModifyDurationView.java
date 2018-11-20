package com.kooloco.uilocal.modify.view;


import com.kooloco.model.Order;
import com.kooloco.ui.base.RootView;

import java.util.List;

/**
 * Created by hlink on 20/1/18.
 */

public interface ModifyDurationView extends RootView {

    void setIsLocation(boolean isLocation);

    void setOrder(Order order);

    void setData(List<String> data);
}