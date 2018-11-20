package com.kooloco.uilocal.expereince.view;

import com.kooloco.model.SchedulePrice;
import com.kooloco.model.SchedulePriceData;
import com.kooloco.ui.base.RootView;

import java.util.List;

/**
 * Created by hlink on 16/4/18.
 */

public interface ScheduleAndPriceView extends RootView {
    void setIsEdit(boolean isEdit);

    void setExpId(String expId);

    void setData(SchedulePriceData data);

    void setDataParticipants(String maximumParticipant);
}