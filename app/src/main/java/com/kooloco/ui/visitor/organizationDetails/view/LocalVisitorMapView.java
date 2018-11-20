package com.kooloco.ui.visitor.organizationDetails.view;
/**
 * Created by hlink44 on 19/9/17.
 */

import com.kooloco.model.DashboardDetails;
import com.kooloco.ui.base.RootView;

public interface LocalVisitorMapView extends RootView {
    void setData(DashboardDetails dashboardDetails);
}
