package com.kooloco.ui.myexperience.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kooloco.model.BlogDetails;
import com.kooloco.model.BlogMedia;
import com.kooloco.model.LocalImage;

import java.util.List;

/**
 * Created by hlink44 on 2/10/17.
 */

public class BlogPagerSlider extends FragmentStatePagerAdapter {
    List<BlogMedia> localImages;

    public BlogPagerSlider(FragmentManager fm, List<BlogMedia> localImages) {
        super(fm);
        this.localImages = localImages;
    }

    @Override
    public Fragment getItem(int position) {
        com.kooloco.ui.visitor.dashboard.fragment.ViewPagerSlider viewPagerSlider = new com.kooloco.ui.visitor.dashboard.fragment.ViewPagerSlider();
        return viewPagerSlider;
    }

    @Override
    public int getCount() {
        return localImages.size();
    }
}
