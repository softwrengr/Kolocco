package com.kooloco.uilocal.expereince.view;


import com.kooloco.model.ExpereinceNew;
import com.kooloco.ui.base.RootView;

import java.util.List;

/**
 * Created by hlink on 13/4/18.
 */

public interface ExperienceView extends RootView {

    void setData(List<ExpereinceNew> expereinceNew);

    void setIsLocalFlow(boolean isLocalFlow);

    void deleteExp(int pos, ExpereinceNew expereinceNew);
}