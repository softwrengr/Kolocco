package com.kooloco.ui.profile.view;
/**
 * Created by hlink44 on 25/9/17.
 */

import com.kooloco.model.Review;
import com.kooloco.model.User;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface ViewProfileView extends RootView {
    void setData(User data);

    void setDataRating(List<Review> data, int pg, int tootalCount);
}
