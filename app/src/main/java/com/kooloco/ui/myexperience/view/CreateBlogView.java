package com.kooloco.ui.myexperience.view;
/**
 * Created by hlink44 on 6/11/17.
 */

import com.kooloco.model.AddImages;
import com.kooloco.model.BlogDetails;
import com.kooloco.model.ExperienceDetails;
import com.kooloco.model.Quation;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface CreateBlogView extends RootView {
    void setExperienceDetails(ExperienceDetails experienceDetails);

    void setQuation(List<Quation> data);

    void setBlogDetails(BlogDetails data);

    void deleteMedia(AddImages addImages);

}
