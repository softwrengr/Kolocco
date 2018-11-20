package com.kooloco.ui.visitor.home.view;

import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.LocalNew;
import com.kooloco.ui.base.RootView;

import java.util.List;

/**
 * Created by hlink on 21/4/18.
 */

public interface HomeLocalAndExpView extends RootView {

    void setExpData(List<ExpereinceNew> listExpNew, int page);

    void setLocalData(List<LocalNew> listLocalNew, int page);
}