package com.kooloco.ui.profile.view;
/**
 * Created by hlink44 on 25/9/17.
 */

import com.kooloco.model.Order;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface OrderHistoryPendingView extends RootView {
    void setData(List<Order> data, int page);
}
