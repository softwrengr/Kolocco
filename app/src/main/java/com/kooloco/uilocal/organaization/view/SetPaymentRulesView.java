package com.kooloco.uilocal.organaization.view;

import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.PaymentRuleList;
import com.kooloco.ui.base.RootView;

import java.util.List;

/**
 * Created by hlink on 1/2/18.
 */

public interface SetPaymentRulesView extends RootView {


    void setOrderDetails(OrganizationDetails organizationDetails);

    void setData(List<PaymentRuleList> data);

    void setIsCreatOrganization(boolean isOrganization);

    void setIsBank(boolean bank);
}