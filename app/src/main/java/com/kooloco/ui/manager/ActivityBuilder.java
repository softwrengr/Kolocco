package com.kooloco.ui.manager;

import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v4.util.Pair;
import android.view.View;

import java.util.List;

/**
 * Created by hlink21 on 11/5/16.
 */
@UiThread
public interface ActivityBuilder {

    void start();

    ActivityBuilder addBundle(Bundle bundle);

    ActivityBuilder addSharedElements(List<Pair<View, String>> pairs);

    ActivityBuilder byFinishingCurrent();

    ActivityBuilder byFinishingAll();

    <T extends AppNavigationProvider.HasPage> ActivityBuilder setPage(T page);

    ActivityBuilder forResult(int requestCode);

    ActivityBuilder shouldAnimate(boolean isAnimate);

    <T extends AppNavigationProvider.HasThemeColor> ActivityBuilder setTheme(T color);

}
