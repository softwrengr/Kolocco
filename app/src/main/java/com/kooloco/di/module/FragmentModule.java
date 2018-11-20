package com.kooloco.di.module;

import android.support.v4.app.FragmentManager;

import com.kooloco.di.PerFragment;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.manager.FragmentHandler;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hlink21 on 31/5/16.
 */
@Module
public class FragmentModule {

    private final BaseFragment baseFragment;

    public FragmentModule(BaseFragment baseFragment) {
        this.baseFragment = baseFragment;
    }

    @Provides
    @PerFragment
    BaseFragment provideBaseFragment() {
        return baseFragment;
    }

    @Provides
    @Named("child_placeholder")
    int placeHolder(BaseFragment baseActivity) {
        return baseActivity.getChildPlaceHolder();
    }

    @Provides
    @Named("child_manager")
    FragmentManager fragmentManager(BaseFragment baseFragment) {
        return baseFragment.getChildFragmentManager();
    }

    @Provides
    @Named("child_fragment_handler")
    FragmentHandler fragmentHandler(@Named("child_manager") FragmentManager fragmentManager
            , @Named("child_placeholder") int placeHolder) {
        return new com.kooloco.ui.manager.FragmentManager(fragmentManager, placeHolder);
    }

}
