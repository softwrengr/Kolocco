package com.kooloco.uilocal.home.view;
/**
 * Created by hlink44 on 16/10/17.
 */

import com.kooloco.model.Order;
import com.kooloco.model.RatingOption;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface RateAndReviewView extends RootView {
    void setData(Order data);

    void setDataValue(List<RatingOption> data);

    void goBackScreen();

}
