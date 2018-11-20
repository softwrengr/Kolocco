package com.kooloco.uilocal.setavability.view;
/**
 * Created by hlink44 on 11/10/17.
 */

import com.kooloco.model.SchduleDashboard;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface SetAvabilityView extends RootView {
    void setData(List<SchduleDashboard> data);

    void setIsEdit(boolean isEdit);
}
