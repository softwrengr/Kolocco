package com.kooloco.uilocal.organaization.view;
/**
 * Created by hlink44 on 16/10/17.
 */

import com.kooloco.model.AddImages;
import com.kooloco.model.FilterGetData;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.Response;
import com.kooloco.ui.base.RootView;

public interface EditOrganizationView extends RootView {
    void setData(Response<FilterGetData> filterGetDataResponse);

    void setDataOrg(OrganizationDetails data);

    void deleteMedia(AddImages addImagesD);

}
