package com.kooloco.uilocal.profile.view;
/**
 * Created by hlink44 on 9/10/17.
 */

import com.kooloco.model.DashboardDetails;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.ui.base.RootView;

public interface ViewProfileLocalView extends RootView {
    void setData(DashboardDetails data);

    void deleteExp(int pos, ExpereinceNew expereinceNew);
}
