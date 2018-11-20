package com.kooloco.ui.manager;

import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.util.Pair;
import android.view.View;

import com.kooloco.ui.base.BaseFragment;

import java.util.List;

@UiThread
public interface FragmentHandler {

    /**
     * @param baseFragment   Fragment to open
     * @param option
     * @param isToBackStack
     * @param tag
     * @param sharedElements
     */
    void openFragment(BaseFragment baseFragment, Option option, boolean isToBackStack, String tag, @Nullable List<Pair<View, String>> sharedElements);

    /**
     * @param fragmentToShow Fragment to show
     * @param fragmentToHide array of fragments to hide
     */
    void showFragment(BaseFragment fragmentToShow, BaseFragment... fragmentToHide);

    void clearFragmentHistory(String tag);


    enum Option {
        ADD, REPLACE, SHOW, HIDE
    }
}