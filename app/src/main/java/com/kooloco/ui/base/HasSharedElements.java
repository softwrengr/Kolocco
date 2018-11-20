package com.kooloco.ui.base;


import android.support.v4.util.Pair;
import android.view.View;

import java.util.List;

/**
 * Created by hlink21 on 11/5/16.
 */
public interface HasSharedElements {

    List<Pair<View, String>> getSharedElements();
}
