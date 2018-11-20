package com.kooloco.ui.onboarding.view;

import com.kooloco.model.Service;
import com.kooloco.ui.base.RootView;

import java.util.List;

/**
 * Created by hlink on 18/5/18.
 */

public interface OnBoardingView extends RootView {

    void setData(List<Service> localServices);

    void data(List<Service> localServices);
}