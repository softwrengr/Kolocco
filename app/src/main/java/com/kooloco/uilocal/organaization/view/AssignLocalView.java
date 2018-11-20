package com.kooloco.uilocal.organaization.view;

import com.kooloco.model.OrgLocal;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.ui.base.RootView;
import com.kooloco.uilocal.organaization.fragment.AddNewPaymentRulesFragment;

import java.util.List;

/**
 * Created by hlink on 1/2/18.
 */

public interface AssignLocalView extends RootView {

    void setData(List<OrgLocal> data);

    void setOrderDetails(OrganizationDetails organizationDetails);

    void setOrgLocal(List<OrgLocal> orgLocals);

    void setCallBack(AddNewPaymentRulesFragment.CallBack callBack);

}