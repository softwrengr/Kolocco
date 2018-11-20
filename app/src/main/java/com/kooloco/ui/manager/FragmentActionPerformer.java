package com.kooloco.ui.manager;

import android.os.Bundle;
import android.support.annotation.UiThread;
import android.util.Pair;
import android.view.View;

import java.util.List;

@UiThread
public interface FragmentActionPerformer<T> {

    void add(boolean toBackStack);

    void addAsChild(boolean toBackStack);

    void add(boolean toBackStack, String tag);

    void addAsChild(boolean toBackStack, String tag);

    void replace(boolean toBackStack);

    void replaceAsChild(boolean toBackStack);

    void replace(boolean toBackStack, String tag);

    void replaceAsChild(boolean toBackStack, String tag);

    FragmentActionPerformer setBundle(Bundle bundle);

    FragmentActionPerformer addSharedElements(List<Pair<View, String>> sharedElements);

    FragmentActionPerformer clearHistory(String tag);

    FragmentActionPerformer clearChildHistory(String tag);

    FragmentActionPerformer hasData(Passable<T> passable);


}