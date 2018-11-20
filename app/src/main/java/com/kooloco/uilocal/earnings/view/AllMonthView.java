package com.kooloco.uilocal.earnings.view;
/**
 * Created by hlink44 on 9/10/17.
 */

import com.kooloco.model.MonthEarning;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface AllMonthView extends RootView {
    void setEarningData(List<MonthEarning> earningData);

    void setTotalAmount(String monthEarningTotal);

}
