package com.kooloco.uilocal.addservice.view;
/**
 * Created by hlink44 on 3/10/17.
 */

import com.kooloco.model.LanguageResponse;
import com.kooloco.ui.base.RootView;

public interface ChooseLanguagesView extends RootView {
    void setIsEdit(boolean isEdit);

    void setLanguage(LanguageResponse data);

}
