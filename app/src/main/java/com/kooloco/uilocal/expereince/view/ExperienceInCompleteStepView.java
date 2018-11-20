package com.kooloco.uilocal.expereince.view;

import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ProfileStatus;
import com.kooloco.ui.base.RootView;

import java.util.List;

/**
 * Created by hlink on 17/4/18.
 */

public interface ExperienceInCompleteStepView extends RootView {

    void setExpData(ExpereinceNew dataExp);

    void setData(List<ProfileStatus> data);
}