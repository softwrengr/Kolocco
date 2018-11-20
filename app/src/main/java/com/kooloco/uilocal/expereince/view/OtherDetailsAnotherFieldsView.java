package com.kooloco.uilocal.expereince.view;

import com.kooloco.model.OtherDetailsAnotherFields;
import com.kooloco.ui.base.RootView;

import java.util.List;

/**
 * Created by hlink on 16/4/18.
 */

public interface OtherDetailsAnotherFieldsView extends RootView {
    void setIsEdit(boolean isEdit);

    void setExpId(String expId);

    void setData(List<OtherDetailsAnotherFields> data);

    void deleteExpOtherFiends(OtherDetailsAnotherFields otherDetailsAnotherField,int pos);

}