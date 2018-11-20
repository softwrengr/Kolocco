package com.kooloco.uilocal.addservice.view;
/**
 * Created by hlink44 on 4/10/17.
 */

import com.kooloco.model.EquipmentResponse;
import com.kooloco.ui.base.RootView;

public interface ChooseSportEquipmentsView extends RootView {
    void setData(EquipmentResponse data);

    void setIsEdit(boolean isEdit);
}
