package com.kooloco.ui.visitor.home.view;

import com.kooloco.model.HomeNewResponse;
import com.kooloco.ui.base.RootView;

/**
 * Created by hlink on 19/4/18.
 */

public interface HomeNewView extends RootView {
    void setData(HomeNewResponse data);
}