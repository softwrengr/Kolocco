package com.kooloco.ui.myexperience.view;
/**
 * Created by hlink44 on 1/11/17.
 */

import com.kooloco.model.BlogDetails;
import com.kooloco.model.Review;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface BlogDetailsView extends RootView {
    void setData(BlogDetails blogDetails);

    void setDataReview(List<Review> data);

    void setDataComments(Review data);
}
