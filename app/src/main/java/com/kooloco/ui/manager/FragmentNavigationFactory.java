package com.kooloco.ui.manager;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.kooloco.di.PerActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.base.RootView;

import java.util.List;

import javax.inject.Inject;

@PerActivity
public class FragmentNavigationFactory {

    private BaseFragment fragment;
    private FragmentHandler fragmentHandler;
    private FragmentHandler childFragmentHandler;
    private String tag;


    @Inject
    public FragmentNavigationFactory(FragmentHandler fragmentHandler) {
        this.fragmentHandler = fragmentHandler;
    }

    public void attachChildFragmentHandler(FragmentHandler childFragmentHandler) {
        this.childFragmentHandler = childFragmentHandler;
    }

    public <V extends RootView> FragmentActionPerformer<V> make(Class<? extends BaseFragment> aClass) {
        return make(FragmentFactory.getFragment(aClass));
    }

    public FragmentHandler getFragmentHandler() {
        return fragmentHandler;
    }

    private FragmentHandler getChildFragmentHandler() {
        return childFragmentHandler;
    }

    public <T extends BaseFragment, V extends RootView> FragmentActionPerformer<V> make(T fragment) {
        this.fragment = fragment;
        this.tag = fragment.getClass().getSimpleName();
        return new Provider<>((V) fragment, this);
    }

    private class Provider<T extends RootView> implements FragmentActionPerformer<T> {

        private final FragmentNavigationFactory navigationFactory;
        List<Pair<View, String>> sharedElements;
        private T view;

        public Provider(T view, FragmentNavigationFactory navigationFactory) {
            this.view = view;
            this.navigationFactory = navigationFactory;
        }

        @Override
        public void add(boolean toBackStack) {
            navigationFactory.getFragmentHandler().openFragment(fragment,
                    FragmentHandler.Option.ADD, toBackStack, tag, sharedElements);
        }

        @Override
        public void addAsChild(boolean toBackStack) {
            navigationFactory.getChildFragmentHandler().openFragment(fragment,
                    FragmentHandler.Option.ADD, toBackStack, tag, sharedElements);
        }

        @Override
        public void add(boolean toBackStack, String tag) {
            navigationFactory.getFragmentHandler().openFragment(fragment,
                    FragmentHandler.Option.ADD, toBackStack, tag, sharedElements);
        }

        @Override
        public void addAsChild(boolean toBackStack, String tag) {
            navigationFactory.getChildFragmentHandler().openFragment(fragment,
                    FragmentHandler.Option.ADD, toBackStack, tag, sharedElements);
        }

        @Override
        public void replace(boolean toBackStack) {
            navigationFactory.getFragmentHandler().openFragment(fragment,
                    FragmentHandler.Option.REPLACE, toBackStack, tag, sharedElements);
        }

        @Override
        public void replaceAsChild(boolean toBackStack) {

            navigationFactory.getChildFragmentHandler().openFragment(fragment,
                    FragmentHandler.Option.REPLACE, toBackStack, tag, sharedElements);
        }

        @Override
        public void replace(boolean toBackStack, String tag) {
            navigationFactory.getFragmentHandler().openFragment(fragment,
                    FragmentHandler.Option.REPLACE, toBackStack, tag, sharedElements);
        }

        @Override
        public void replaceAsChild(boolean toBackStack, String tag) {
            navigationFactory.getChildFragmentHandler().openFragment(fragment,
                    FragmentHandler.Option.REPLACE, toBackStack, tag, sharedElements);
        }

        @Override
        public FragmentActionPerformer setBundle(Bundle bundle) {
            fragment.setArguments(bundle);
            return this;
        }

        @Override
        public FragmentActionPerformer addSharedElements(List<Pair<View, String>> sharedElements) {
            this.sharedElements = sharedElements;
            return this;
        }

        @Override
        public FragmentActionPerformer clearHistory(String tag) {
            navigationFactory.getFragmentHandler().clearFragmentHistory(tag);
            return this;
        }

        @Override
        public FragmentActionPerformer clearChildHistory(String tag) {
            navigationFactory.getChildFragmentHandler().clearFragmentHistory(tag);
            return this;
        }

        @Override
        public FragmentActionPerformer hasData(Passable<T> passable) {
            passable.passData(view);
            return this;
        }


    }
}