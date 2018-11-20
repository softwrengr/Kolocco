package com.kooloco.ui.myexperience.view;


import com.kooloco.ui.base.RootView;
import com.kooloco.ui.myexperience.fragment.ImageFilterFragment;

/**
 * Created by hlink on 22/1/18.
 */

public interface ImageFilterView extends RootView {

    void setImageUrl(String imageUrl);

    void setCallBack(ImageFilterFragment.imageFilterCallback callBack);
}