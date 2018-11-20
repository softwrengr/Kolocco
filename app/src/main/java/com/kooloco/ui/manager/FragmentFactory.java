package com.kooloco.ui.manager;


import com.kooloco.ui.base.BaseFragment;

/**
 * Created by hlink21 on 28/4/16.
 */
public class FragmentFactory {

    public static <T extends BaseFragment> T getFragment(Class<T> aClass) {

        try {
            T t = aClass.newInstance();
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
