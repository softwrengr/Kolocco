package com.kooloco.uilocal;

import android.support.annotation.UiThread;

import com.kooloco.ui.alllocal.fragment.AllLocalMapFragment;
import com.kooloco.ui.chat.fragment.RecentChatFragment;
import com.kooloco.ui.manager.FragmentHandler;
import com.kooloco.ui.myexperience.fragment.MyExperienceFragment;
import com.kooloco.ui.profile.fragment.ProfileFragment;
import com.kooloco.uilocal.complateorder.fragment.CompleteOrderFragment;
import com.kooloco.uilocal.earnings.fragment.EarningsFragment;
import com.kooloco.uilocal.home.fragment.HomeFragment;
import com.kooloco.uilocal.profile.fragment.ProfileLocalFragment;

/**
 * Created by hlink21 on 28/4/16.
 */
@UiThread
public class KoolocoLocalHomeLocalScreenManager {

    private final HomeFragment homeFragment;
    private final EarningsFragment earningsFragment;
    private final CompleteOrderFragment completeOrderFragment;
    private final RecentChatFragment recentChatFragment;
    private final ProfileLocalFragment profileFragment;

    private final FragmentHandler fragmentHandler;

    public KoolocoLocalHomeLocalScreenManager(FragmentHandler fragmentHandler) {
        this.fragmentHandler = fragmentHandler;

        homeFragment = new HomeFragment();
        earningsFragment = new EarningsFragment();
        recentChatFragment = new RecentChatFragment();
        recentChatFragment.setIsLocal(true);
        completeOrderFragment = new CompleteOrderFragment();
        profileFragment = new ProfileLocalFragment();
    }

    public void showHomeScreen() {
        fragmentHandler.showFragment(homeFragment,
                earningsFragment, recentChatFragment, completeOrderFragment, profileFragment);
    }

    public void showEarningsScreen() {

        fragmentHandler.showFragment(earningsFragment,
                homeFragment, recentChatFragment, completeOrderFragment, profileFragment);
    }


    public void showRecentChatScreen() {
        fragmentHandler.showFragment(recentChatFragment,
                homeFragment, earningsFragment, completeOrderFragment, profileFragment);
    }

    public void showComplateOrderScreen() {
        fragmentHandler.showFragment(completeOrderFragment,
                homeFragment, earningsFragment, recentChatFragment, profileFragment);
    }

    public void showProfileScreen() {
        fragmentHandler.showFragment(profileFragment,
                homeFragment, earningsFragment, completeOrderFragment, recentChatFragment);
    }
}
