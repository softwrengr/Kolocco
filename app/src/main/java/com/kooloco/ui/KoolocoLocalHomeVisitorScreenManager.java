package com.kooloco.ui;

import android.support.annotation.UiThread;

import com.kooloco.ui.chat.fragment.RecentChatFragment;
import com.kooloco.ui.manager.FragmentHandler;
import com.kooloco.ui.myexperience.fragment.MyExperienceFragment;
import com.kooloco.ui.profile.fragment.ProfileFragment;
import com.kooloco.ui.visitor.home.fragment.HomeLocalAndExpFragment;
import com.kooloco.ui.visitor.home.fragment.HomeNewFragment;


/**
 * Created by hlink21 on 28/4/16.
 */
@UiThread
public class KoolocoLocalHomeVisitorScreenManager {

    private final HomeNewFragment homeFragment;
    private final HomeLocalAndExpFragment homeLocalAndExpFragment;
    private final MyExperienceFragment myExperienceFragment;
    private final RecentChatFragment recentChatFragment;
    private final ProfileFragment profileFragment;

    private final FragmentHandler fragmentHandler;

    public KoolocoLocalHomeVisitorScreenManager(FragmentHandler fragmentHandler) {
        this.fragmentHandler = fragmentHandler;

        homeFragment = new HomeNewFragment();
        homeLocalAndExpFragment = new HomeLocalAndExpFragment();
        myExperienceFragment = new MyExperienceFragment();
        recentChatFragment = new RecentChatFragment();
        profileFragment = new ProfileFragment();
    }

    public void showHomeScreen() {
        fragmentHandler.showFragment(homeFragment,
                homeLocalAndExpFragment, myExperienceFragment, recentChatFragment, profileFragment);
    }

    public void showLocalAndExpScreen() {
        fragmentHandler.showFragment(homeLocalAndExpFragment,
                homeFragment, myExperienceFragment, recentChatFragment, profileFragment);
    }

    public void showMyExperienceScreen() {
        fragmentHandler.showFragment(myExperienceFragment,
                homeFragment, homeLocalAndExpFragment, recentChatFragment, profileFragment);
    }

    public void showRecentChatScreen() {
        fragmentHandler.showFragment(recentChatFragment,
                homeFragment, homeLocalAndExpFragment, myExperienceFragment, profileFragment);
    }

    public void showProfileScreen() {
        fragmentHandler.showFragment(profileFragment,
                homeFragment, homeLocalAndExpFragment, myExperienceFragment, recentChatFragment);
    }
}
