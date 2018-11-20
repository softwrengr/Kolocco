package com.kooloco.ui.visitor.dashboard.view;
/**
 * Created by hlink44 on 21/9/17.
 */

import com.kooloco.model.Duration;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface DurationView extends RootView {
    void setData(List<Duration> data);
}
