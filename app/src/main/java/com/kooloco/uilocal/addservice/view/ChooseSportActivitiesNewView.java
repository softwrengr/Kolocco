package com.kooloco.uilocal.addservice.view;
/**
 * Created by hlink44 on 4/10/17.
 */

import com.kooloco.model.SportActivity;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface ChooseSportActivitiesNewView extends RootView {
    void setData(List<SportActivity> sportActivities);

    void setIsEdit(boolean isEdit);

    void setIsInComplete(boolean isInComplete);

}
