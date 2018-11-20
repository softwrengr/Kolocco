package com.kooloco.uilocal.home.view;
/**
 * Created by hlink44 on 5/10/17.
 */

import com.kooloco.model.Order;
import com.kooloco.model.OrderDetails;
import com.kooloco.ui.base.RootView;

public interface AcceptOrderDetailsView extends RootView {
    void setOrder(Order order);

    void setData(OrderDetails orderDetails);

}
