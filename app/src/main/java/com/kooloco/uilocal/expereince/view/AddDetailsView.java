package com.kooloco.uilocal.expereince.view;

import com.kooloco.model.ExperienceResponse;
import com.kooloco.ui.base.RootView;

/**
 * Created by hlink on 16/4/18.
 */

public interface AddDetailsView extends RootView {
    void setIsEdit(boolean isEdit);

    void setExpId(String expId);

    void setData(ExperienceResponse experienceResponse);
}