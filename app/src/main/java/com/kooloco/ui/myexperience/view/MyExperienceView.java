package com.kooloco.ui.myexperience.view;
/**
 * Created by hlink44 on 28/9/17.
 */

import com.kooloco.model.ExperienceDetails;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface MyExperienceView extends RootView {
    void setData(List<ExperienceDetails> data, int page);

}
