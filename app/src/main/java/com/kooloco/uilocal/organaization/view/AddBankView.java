package com.kooloco.uilocal.organaization.view;
/**
 * Created by hlink44 on 11/10/17.
 */

import com.kooloco.model.Bank;
import com.kooloco.model.CountryList;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface AddBankView extends RootView {
    void setIsEdit(boolean isEdit);

    void setDataCountry(List<CountryList> data);

    void setDataBank(List<Bank> banks);

    void deleteData(int position);

    void clearData();

}
