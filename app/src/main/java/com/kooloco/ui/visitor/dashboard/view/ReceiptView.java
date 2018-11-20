package com.kooloco.ui.visitor.dashboard.view;
/**
 * Created by hlink44 on 22/9/17.
 */

import com.kooloco.model.OrderDetails;
import com.kooloco.ui.base.RootView;

public interface ReceiptView extends RootView {

    void setOrderId(String orderId);

    void setData(OrderDetails data);
}
