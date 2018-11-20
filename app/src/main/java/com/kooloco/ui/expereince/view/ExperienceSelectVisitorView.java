package com.kooloco.ui.expereince.view;

import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ExperienceBookingFlow;
import com.kooloco.ui.base.RootView;

import java.util.List;

/**
 * Created by hlink on 21/4/18.
 */

public interface ExperienceSelectVisitorView extends RootView {

    void setData(List<ExpereinceNew> listExpNew);

    void setExpBookingData(ExperienceBookingFlow experienceBookingFlow);
}