package com.kooloco.uilocal.expereince.view;

import com.kooloco.model.CancellationPolicy;
import com.kooloco.ui.base.RootView;

import java.util.List;

/**
 * Created by hlink on 17/4/18.
 */

public interface ExperienceCancellationPoliciesView extends RootView {
    void setIsEdit(boolean isEdit);

    void setData(List<CancellationPolicy> data);

    void setExpId(String expId);
}