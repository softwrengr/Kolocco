package com.kooloco.ui.visitor.home.view;
/**
 * Created by hlink44 on 14/9/17.
 */

import com.kooloco.model.DashboardDetails;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface HomeView extends RootView {

    void setData(List<DashboardDetails> data, int page, boolean isFromFilter);
}
