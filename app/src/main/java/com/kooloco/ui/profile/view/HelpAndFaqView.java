package com.kooloco.ui.profile.view;
/**
 * Created by hlink44 on 27/9/17.
 */

import com.kooloco.model.HelpAndFaq;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface HelpAndFaqView extends RootView {
    void setData(List<HelpAndFaq> data);
}
