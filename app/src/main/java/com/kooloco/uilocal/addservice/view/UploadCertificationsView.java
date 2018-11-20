package com.kooloco.uilocal.addservice.view;
/**
 * Created by hlink44 on 3/10/17.
 */

import com.kooloco.model.Certifications;
import com.kooloco.model.SportActivity;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface UploadCertificationsView extends RootView {
    void setSportList(List<SportActivity> data);

    void notifyItemChange(Certifications certificationsD, int position);

    void setData(List<Certifications> data);

    void add();

    void setIsEdit(boolean isEdit);

    void clearValueNew();
}
