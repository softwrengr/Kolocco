package com.kooloco.uilocal.home.view;
/**
 * Created by hlink44 on 5/10/17.
 */

import com.kooloco.model.Order;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface HomeAcceptView extends RootView {
    void setData(List<Order> data, int page);
}
