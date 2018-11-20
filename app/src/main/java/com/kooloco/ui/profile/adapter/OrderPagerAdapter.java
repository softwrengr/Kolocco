package com.kooloco.ui.profile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kooloco.ui.profile.fragment.OrderHistoryComplateFragment;
import com.kooloco.ui.profile.fragment.OrderHistoryPendingFragment;

/**
 * Created by hlink44 on 25/9/17.
 */

public class OrderPagerAdapter extends FragmentStatePagerAdapter {

    public OrderPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new OrderHistoryPendingFragment();
        } else if (position == 1) {
            return new OrderHistoryComplateFragment();
        } else {
            return new OrderHistoryComplateFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
