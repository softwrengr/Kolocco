package com.kooloco.ui.myexperience.view;
/**
 * Created by hlink44 on 28/9/17.
 */

import com.kooloco.model.BlogDetails;
import com.kooloco.model.ExperienceDetails;
import com.kooloco.model.OrderDetails;
import com.kooloco.ui.base.RootView;

public interface MyExperienceDetailsView extends RootView {

    void setExpDetails(ExperienceDetails experienceDetails);

    void setData(OrderDetails orderDetails);
}
