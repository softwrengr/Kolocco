package com.kooloco.uilocal.expereince.view;

import com.kooloco.model.EquipmentResponse;
import com.kooloco.ui.base.RootView;

/**
 * Created by hlink on 17/4/18.
 */

public interface ExperienceSportEquipmentsView extends RootView {

    void setExpId(String expId);

    void setData(EquipmentResponse data);
}