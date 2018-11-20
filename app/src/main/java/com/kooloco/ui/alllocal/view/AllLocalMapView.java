package com.kooloco.ui.alllocal.view;
/**
 * Created by hlink44 on 28/9/17.
 */

import com.kooloco.model.DashboardDetails;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface AllLocalMapView extends RootView {
    void setData(List<DashboardDetails> data, int page);

    void setDataDashboard(List<DashboardDetails> data);

}
