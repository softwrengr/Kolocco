package com.kooloco.uilocal.addservice.view;
/**
 * Created by hlink44 on 2/10/17.
 */

import com.kooloco.model.AddImages;
import com.kooloco.model.Certifications;
import com.kooloco.model.SportActivity;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface AddImageView extends RootView {

    void setIsEdit(boolean isEdit);

    void setSportList(List<SportActivity> data);

    void notifyItemChange(Certifications certificationsD, int position);

    void add();

    void setData(List<Certifications> data);

    void clearValueNew();
}
