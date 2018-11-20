package com.kooloco.ui.rating.view;
/**
 * Created by hlink44 on 22/9/17.
 */

import com.kooloco.model.OrderDetails;
import com.kooloco.model.RatingOption;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface RatingnView extends RootView {
    void setData(List<RatingOption> data);

    void setOrderId(String orderId);


    void backToScreen();

    void setNotification(boolean isNotification);

    void setOrderData(OrderDetails data);

    void setIsObjection(boolean isObjection);

}
