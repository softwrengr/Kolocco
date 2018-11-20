package com.kooloco.uilocal.expereince.view;

import com.kooloco.model.OtheDetailsResponse;
import com.kooloco.ui.base.RootView;

/**
 * Created by hlink on 16/4/18.
 */

public interface OtherDetailsView extends RootView {

    void setIsEdit(boolean isEdit);

    void setData(OtheDetailsResponse data);

    void setExpId(String expId);

}