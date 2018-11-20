package com.kooloco.ui.profile.view;

import com.kooloco.model.Currency;
import com.kooloco.model.Language;
import com.kooloco.model.LanguageResponse;
import com.kooloco.ui.base.RootView;

import java.util.List;

/**
 * Created by hlink on 29/6/18.
 */

public interface CurrencyVisitorView extends RootView {

    void setIsVisitor(boolean isVisitor);

    void setData(List<Currency> data);
}