package com.kooloco.uilocal.expereince.view;

import com.kooloco.model.Service;
import com.kooloco.ui.base.RootView;

import java.util.List;

/**
 * Created by hlink on 13/4/18.
 */

public interface ExperienceSportView extends RootView {
    void setIsEdit(boolean isEdit);

    void setData(List<Service> data, int selectMainService, int selectSubService);

    void setExpId(String expId);

}