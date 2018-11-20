package com.kooloco.ui.visitor.dashboard.view;
/**
 * Created by hlink44 on 16/9/17.
 */

import com.kooloco.model.CheckPaymentRules;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.Response;
import com.kooloco.model.Review;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface DashboardView extends RootView {
    void setData(DashboardDetails data);

    void setDataNew(DashboardDetails dashboardDetails);

    void setIsPreview(boolean isPreview);

    void setDataRating(List<Review> list, int pageRate, int count);

    void setIsOrg(boolean isOrgScreen);

    void setOrgData(Response<CheckPaymentRules> checkPaymentRulesResponse);
}
