package com.kooloco.ui.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.kooloco.core.Common;
import com.kooloco.di.PerActivity;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by hlink21 on 11/5/16.
 */
@PerActivity
public class ActivityStarter {

    private BaseActivity context;
    private Intent intent;
    private Class<? extends Activity> activity;
    private boolean shouldAnimate = true;

    @Inject
    ActivityStarter(BaseActivity context) {
        this.context = context;
    }

    public ActivityBuilder make(Class<? extends BaseActivity> activityClass) {
        activity = activityClass;
        intent = new Intent(context, activityClass);
        return new Builder();
    }

    private class Builder implements ActivityBuilder {
        private Bundle bundle;
        private Bundle activityOptionsBundle;
        private boolean isToFinishCurrent;
        private int requestCode;

        @Override
        public void start() {
            if (bundle != null)
                intent.putExtras(bundle);

            if (!shouldAnimate)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            if (requestCode == 0) {

                if (activityOptionsBundle == null)
                    context.startActivity(intent);
                else context.startActivity(intent, activityOptionsBundle);

            } else {
                BaseFragment currentFragment = context.getCurrentFragment();
                if (currentFragment != null)
                    currentFragment.getActivity().startActivityForResult(intent, requestCode);
                else context.startActivityForResult(intent, requestCode);
            }

            /*if (shouldAnimate)
                context.overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);*/


            if (isToFinishCurrent)
                context.finish();
        }

        @Override
        public ActivityBuilder addBundle(Bundle bundle) {
            if (this.bundle != null)
                this.bundle.putAll(bundle);
            else
                this.bundle = bundle;
            return this;
        }

        @Override
        public ActivityBuilder addSharedElements(List<Pair<View, String>> pairs) {

            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(context, pairs.toArray(new Pair[pairs.size()]));
            activityOptionsBundle = optionsCompat.toBundle();
            return this;
        }

        @Override
        public ActivityBuilder byFinishingCurrent() {
            isToFinishCurrent = true;
            return this;
        }

        @Override
        public ActivityBuilder byFinishingAll() {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            return this;
        }


        @Override
        public <T extends AppNavigationProvider.HasPage> ActivityBuilder setPage(T page) {
            intent.putExtra(Common.ACTIVITY_FIRST_PAGE, (Serializable) page);
            return this;
        }

        @Override
        public ActivityBuilder forResult(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        @Override
        public ActivityBuilder shouldAnimate(boolean isAnimate) {
            shouldAnimate = isAnimate;
            return this;
        }

        @Override
        public <T extends AppNavigationProvider.HasThemeColor> ActivityBuilder setTheme(T color) {
            intent.putExtra(AppNavigationProvider.HasThemeColor.THEME_COLOR, (Serializable) color);
            return this;
        }
    }


}
