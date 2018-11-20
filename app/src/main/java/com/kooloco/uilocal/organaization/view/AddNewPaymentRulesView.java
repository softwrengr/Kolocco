package com.kooloco.uilocal.organaization.view;

import com.kooloco.model.OrgLocal;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.PaymentRuleList;
import com.kooloco.model.PaymentRulesOption;
import com.kooloco.ui.base.RootView;

import java.util.List;

/**
 * Created by hlink on 1/2/18.
 */

public interface AddNewPaymentRulesView extends RootView {
    void setDataPaymentOption(List<PaymentRulesOption> dataPaymentOptions);

    void setDataUnAssignLocal(List<OrgLocal> orgLocals);

    void setDataOrganization(OrganizationDetails organizationDetails);

    void setPaymentRules(PaymentRuleList paymentRuleList);

    void setIsEdit(boolean isEdit);

    void setIsSetBank(boolean isBank);
}