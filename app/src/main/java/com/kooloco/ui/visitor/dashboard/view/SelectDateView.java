package com.kooloco.ui.visitor.dashboard.view;
/**
 * Created by hlink44 on 21/9/17.
 */

import com.kooloco.model.Time;
import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface SelectDateView extends RootView {
    void setData(List<Time> data);

    void setVisitorBooking(VisitorBooking visitorBooking);

    void setDataStartTime(List<Time> data);

    void setDataEndTime(List<Time> data);

}
