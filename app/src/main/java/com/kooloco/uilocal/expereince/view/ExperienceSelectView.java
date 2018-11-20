package com.kooloco.uilocal.expereince.view;

import com.kooloco.model.Activities;
import com.kooloco.ui.base.RootView;

import java.util.List;

/**
 * Created by hlink on 13/4/18.
 */

public interface ExperienceSelectView extends RootView {
    void setData(List<Activities> data, int selectIndex);

    void setIsEdit(boolean isEdit);

    void setExpId(String expId);

    void setSportId(String sportId);
}