package com.kooloco.ui.base;



import com.kooloco.ui.navigation.AppNavigator;

import javax.inject.Inject;


/**
 * Created by hlink21 on 25/4/16.
 */
public abstract class BasePresenter<ViewT extends RootView> {

    protected ViewT view;

    @Inject
    protected AppNavigator navigator;

    public void setNavigator(AppNavigator navigator) {
        this.navigator = navigator;
    }

    public void setView(ViewT view) {
        this.view = view;
    }

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onResume() method.
     */
    public abstract void resume();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onPause() method.
     */
    public abstract void pause();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    public abstract void destroy();


}
