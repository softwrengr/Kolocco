package com.kooloco.ui.visitor.dashboard.view;
/**
 * Created by hlink44 on 25/8/17.
 */

import com.kooloco.model.SelectActivites;
import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.RootView;

public interface SelectActivitiesView extends RootView {
    void setData(SelectActivites data);

    void setVisitorBooking(VisitorBooking visitorBooking);
}
