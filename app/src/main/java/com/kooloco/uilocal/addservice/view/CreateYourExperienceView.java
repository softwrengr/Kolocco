package com.kooloco.uilocal.addservice.view;
/**
 * Created by hlink44 on 3/10/17.
 */

import com.kooloco.model.Activities;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface CreateYourExperienceView extends RootView {
    void setData(List<Activities> activites);

    void setIsEdit(boolean isEdit);

    void setIsLocalInComplete(boolean isLocalIncomplete);
}
