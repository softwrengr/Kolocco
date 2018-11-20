package com.kooloco.uilocal.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.SearchView;

import com.kooloco.ui.profile.fragment.OrderHistoryComplateFragment;
import com.kooloco.ui.profile.fragment.OrderHistoryPendingFragment;
import com.kooloco.uilocal.home.fragment.HomeAcceptFragment;
import com.kooloco.uilocal.home.fragment.HomePendingFragment;

/**
 * Created by hlink44 on 25/9/17.
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    HomePendingFragment homePendingFragment;
    HomeAcceptFragment homeAcceptFragment;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
        homePendingFragment = new HomePendingFragment();
        homeAcceptFragment = new HomeAcceptFragment();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return homePendingFragment;
        } else if (position == 1) {
            return homeAcceptFragment;
        } else {
            return homeAcceptFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public void setSerachData(String search){
        if(homeAcceptFragment!=null){
            homeAcceptFragment.setSearchText(search);

        }
        if(homePendingFragment!=null){
            homePendingFragment.setSearchText(search);
        }
    }

    public void setSearchView(SearchView searchView) {
        if(homeAcceptFragment!=null){
            homeAcceptFragment.setSearchView(searchView);

        }
        if(homePendingFragment!=null){
            homePendingFragment.setSearchView(searchView);
        }

    }
}
