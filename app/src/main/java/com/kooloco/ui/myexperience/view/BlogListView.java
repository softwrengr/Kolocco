package com.kooloco.ui.myexperience.view;
/**
 * Created by hlink44 on 31/10/17.
 */

import com.kooloco.model.BlogDetails;
import com.kooloco.model.ExperienceDetails;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface BlogListView extends RootView {
    void setData(List<BlogDetails> data, int page);

    void updateDataBlog(BlogDetails data, int position);
}
