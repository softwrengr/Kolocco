package com.kooloco.uilocal.addservice.view;
/**
 * Created by hlink44 on 24/8/17.
 */

import com.kooloco.model.Currency;
import com.kooloco.model.LanguageResponse;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface BecomeALocalView extends RootView {
    void setLanguage(LanguageResponse data);

    void setCurrencyData(List<Currency> data);
}
