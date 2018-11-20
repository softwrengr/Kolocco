package com.kooloco.uilocal.addservice.view;
/**
 * Created by hlink44 on 5/10/17.
 */

import com.kooloco.model.DashboardDetails;
import com.kooloco.ui.base.RootView;

public interface SetLocationView extends RootView {
    void setIsEdit(boolean isEdit);
    void setDashboardDetails(DashboardDetails dashboardDetails);

}
