package com.kooloco.uilocal.organaization.view;

import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.OrganizationStep;
import com.kooloco.ui.base.RootView;

import java.util.List;

/**
 * Created by hlink on 20/3/18.
 */

public interface OrganizationIncompleteStepView extends RootView {
     void setData(List<OrganizationStep> data);

     void setOrganizationDetails(OrganizationDetails data);
}