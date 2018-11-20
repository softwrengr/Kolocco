package com.kooloco.uilocal.addservice.view;
/**
 * Created by hlink44 on 4/10/17.
 */

import com.kooloco.model.CancellationPolicy;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface CancellationPoliciesView extends RootView {
    void setIsEdit(boolean isEdit);

    void setData(List<CancellationPolicy> data);
}
