package com.kooloco.util;


import com.kooloco.ui.base.RootView;

import java.lang.ref.WeakReference;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by hlink21 on 21/7/16.
 */
public abstract class SubscribeWithView<T> implements SingleObserver<T> {

    private WeakReference<RootView> rootView;

    public SubscribeWithView(RootView rootView) {
        this.rootView = new WeakReference<>(rootView);
    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (rootView.get() != null)
            rootView.get().onError(e);
    }


}
