package com.kooloco.ui.visitor.organizationDetails.view;
/**
 * Created by hlink44 on 19/9/17.
 */

import com.kooloco.model.OrgLocal;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.OrganizationVisitor;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface OrganizationDetailsView extends RootView {

    void setData(OrganizationDetails data);

    void setDataOrgVisitor(OrganizationVisitor organizationVisitor);

    void setIsLocal(boolean isTrue);

    void setNewSearch(List<OrgLocal> orgLocals);

    void setIsPreview(boolean isPreview);

    void setIsDeleteOrg(boolean isDeleteOrg);

    void goBackData();

    void setTextPaymentRules(String isPaymentText);
}
