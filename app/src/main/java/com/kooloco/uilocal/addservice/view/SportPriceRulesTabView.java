package com.kooloco.uilocal.addservice.view;
/**
 * Created by hlink44 on 27/11/17.
 */

import com.kooloco.model.SportPriceRules;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface SportPriceRulesTabView extends RootView {
    void setData(List<SportPriceRules> data);

    void setIsEdit(boolean isEdit);
    void setIsLocalInComplete(boolean isIncompleteLocal);

}
