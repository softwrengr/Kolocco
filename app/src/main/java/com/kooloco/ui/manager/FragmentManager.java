package com.kooloco.ui.manager;

import android.support.v4.app.FragmentTransaction;
import android.util.Pair;
import android.view.View;

import com.kooloco.di.PerActivity;
import com.kooloco.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by hlink21 on 25/4/16.
 */
@PerActivity
public class FragmentManager implements FragmentHandler {

    public static boolean sDisableFragmentAnimations = false;
    private final android.support.v4.app.FragmentManager fragmentManager;
    private final int placeHolder;

    @Inject
    public FragmentManager(android.support.v4.app.FragmentManager fragmentManager, @Named("placeholder") int placeHolder) {
        this.fragmentManager = fragmentManager;
        this.placeHolder = placeHolder;
    }

    @Override
    public final void openFragment(BaseFragment baseFragment, Option option, boolean isToBackStack, String tag, List<Pair<View, String>> sharedElements) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // animation
        /*if (option != Option.ADD)
            fragmentTransaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit, R.anim.pop_enter, R.anim.pop_exit);*/

        switch (option) {

            case ADD:
                fragmentTransaction.add(placeHolder, baseFragment, tag);
                break;
            case REPLACE:
                fragmentTransaction.replace(placeHolder, baseFragment, tag);
                break;
            case SHOW:
                if (baseFragment.isAdded())
                    fragmentTransaction.show(baseFragment);
                break;
            case HIDE:
                if (baseFragment.isAdded())
                    fragmentTransaction.hide(baseFragment);
                break;
        }

        if (isToBackStack)
            fragmentTransaction.addToBackStack(tag);

        // shared element Transition
        /*if (sharedElements != null
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                && sharedElements.size() > 0) {

            RootFragment currentFragment = (RootFragment) fragmentManager.findFragmentById(placeHolder);

            Transition changeTransform = TransitionInflater.from(currentFragment.getContext()).
                    inflateTransition(R.transition.change_image_transform);

            currentFragment.setSharedElementReturnTransition(changeTransform);
            // currentFragment.setExitTransition(new Fade());

            baseFragment.setSharedElementEnterTransition(changeTransform);
            //baseFragment.setEnterTransition(new Fade());


            for (Pair<View, String> se : sharedElements) {
                fragmentTransaction.addSharedElement(se.first, se.second);
            }
        }*/


        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void showFragment(BaseFragment fragmentToShow, BaseFragment... fragmentToHide) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragmentToShow.isAdded()) {
            fragmentTransaction.show(fragmentToShow);
            fragmentToShow.onShow();
        } else openFragment(fragmentToShow, Option.ADD, false, "home", null);

        for (BaseFragment fragment : fragmentToHide) {
            if (fragment.isAdded())
                fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void clearFragmentHistory(String tag) {
        sDisableFragmentAnimations = true;
        fragmentManager.popBackStackImmediate(tag, android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
        sDisableFragmentAnimations = false;
    }


}
