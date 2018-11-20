package com.kooloco.uilocal.expereince.view;

import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.SchedulePrice;
import com.kooloco.ui.base.RootView;

import java.util.List;

/**
 * Created by hlink on 17/4/18.
 */

public interface ExperienceSetSpecialAvailabilitesView extends RootView {
    void setData(List<SchedulePrice> slot);

    void setExpNew(ExpereinceNew expNew);

    void setIsNotAvability(boolean isNotAvability);
}