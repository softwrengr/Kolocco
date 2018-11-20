package com.kooloco.ui.profile.view;
/**
 * Created by hlink44 on 25/9/17.
 */

import com.kooloco.model.CancellationData;
import com.kooloco.ui.base.RootView;

public interface CanecellationView extends RootView {
    void setOrderId(String orderId);

    void setData(CancellationData data);
}
