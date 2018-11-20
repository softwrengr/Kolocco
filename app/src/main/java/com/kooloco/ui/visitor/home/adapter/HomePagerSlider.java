package com.kooloco.ui.visitor.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kooloco.model.BlogMedia;
import com.kooloco.model.LocalImage;

import java.util.List;

/**
 * Created by hlink44 on 2/10/17.
 */

public class HomePagerSlider extends FragmentStatePagerAdapter {
    List<LocalImage> localImages;

    public HomePagerSlider(FragmentManager fm, List<LocalImage> localImages) {
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
