package com.kooloco.ui.profile.view;
/**
 * Created by hlink44 on 26/9/17.
 */

import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.Favorites;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface FavoritesView extends RootView {


    void setData(List<ExpereinceNew> data, int page);
}
