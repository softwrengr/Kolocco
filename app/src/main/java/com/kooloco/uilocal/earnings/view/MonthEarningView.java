package com.kooloco.uilocal.earnings.view;
/**
 * Created by hlink44 on 7/10/17.
 */

import com.kooloco.model.AllMonth;
import com.kooloco.model.Order;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface MonthEarningView extends RootView {

    void setAllMonth(AllMonth allMonth);

    void setData(List<Order> objects, int page);
}
