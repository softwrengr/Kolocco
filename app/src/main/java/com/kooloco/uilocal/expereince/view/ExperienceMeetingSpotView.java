package com.kooloco.uilocal.expereince.view;

import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ExperienceDetails;
import com.kooloco.model.ExperienceResponse;
import com.kooloco.ui.base.RootView;

/**
 * Created by hlink on 17/4/18.
 */

public interface ExperienceMeetingSpotView extends RootView {

    void setIsEdit(boolean isEdit);

    void setExpId(String expId);

    void setExpResponse(ExpereinceNew expResponse);
}