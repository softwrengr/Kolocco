package com.kooloco.uilocal.profile.view;
/**
 * Created by hlink44 on 6/10/17.
 */

import com.kooloco.ui.base.RootView;

public interface ProfileLocalView extends RootView {

    void updateData();

    void setStepCountData(int pendingCount, int stepTootalCount);
}
