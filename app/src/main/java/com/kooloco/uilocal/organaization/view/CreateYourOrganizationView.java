package com.kooloco.uilocal.organaization.view;
/**
 * Created by hlink44 on 10/10/17.
 */

import com.kooloco.model.FilterGetData;
import com.kooloco.model.Response;
import com.kooloco.ui.base.RootView;

public interface CreateYourOrganizationView extends RootView {

    void setData(Response<FilterGetData> filterGetDataResponse);
}
