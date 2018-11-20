package com.kooloco.ui.profile.view;
/**
 * Created by hlink44 on 25/9/17.
 */

import com.kooloco.model.Order;
import com.kooloco.model.OrderDetails;
import com.kooloco.ui.base.RootView;

public interface OrderDetailsView extends RootView {

    void setOrder(Order order);

    void setData(OrderDetails orderDetails);
}
