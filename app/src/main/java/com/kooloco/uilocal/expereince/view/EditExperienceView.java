package com.kooloco.uilocal.expereince.view;

import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ProfileStatus;
import com.kooloco.ui.base.RootView;

import java.util.List;

/**
 * Created by hlink on 13/4/18.
 */

public interface EditExperienceView extends RootView {

    void setExpId(String expId);

    void setExpNew(ExpereinceNew expereinceNew);

    void setData(ExpereinceNew data);

    void setStepCountData(int stepPendingCount, List<ProfileStatus> progress);
}