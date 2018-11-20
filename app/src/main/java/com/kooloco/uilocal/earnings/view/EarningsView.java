package com.kooloco.uilocal.earnings.view;
/**
 * Created by hlink44 on 7/10/17.
 */

import com.kooloco.model.EarningData;
import com.kooloco.model.Review;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface EarningsView extends RootView {

    void setDataRating(List<Review> list, int pageRate, int count);

    void setData(EarningData earningRates);

}
