package com.kooloco.uilocal.organaization.view;
/**
 * Created by hlink44 on 11/10/17.
 */

import com.kooloco.model.Order;
import com.kooloco.model.OrderOrg;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface OrganizationDashboardView extends RootView {
    void setData(List<OrderOrg> ordersComplate);

    void setOrgData(OrganizationDetails orgData);
}
