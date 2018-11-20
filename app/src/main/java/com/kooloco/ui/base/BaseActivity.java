package com.kooloco.ui.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.kooloco.R;
import com.kooloco.Startup;
import com.kooloco.constant.Common;
import com.kooloco.constant.EventBusAction;
import com.kooloco.core.AppPreferences;
import com.kooloco.core.Session;
import com.kooloco.di.HasComponent;
import com.kooloco.di.Injector;
import com.kooloco.di.component.ActivityComponent;
import com.kooloco.di.component.DaggerActivityComponent;
import com.kooloco.di.module.ActivityModule;
import com.kooloco.ui.chat.fragment.ChatFragment;
import com.kooloco.ui.isolated.IsolatedFullActivity;
import com.kooloco.ui.manager.ActivityStarter;
import com.kooloco.ui.manager.AppNavigationProvider;
import com.kooloco.ui.manager.FragmentNavigationFactory;
import com.kooloco.ui.webOpen.fragment.WebOpenFragment;
import com.kooloco.uilocal.expereince.fragment.ExperienceFragment;
import com.kooloco.uilocal.profile.fragment.LocalIncompleteStepFragment;
import com.kooloco.util.LocationManager;
import com.kooloco.util.PermissionUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by hlink21 on 25/4/16.
 */
public abstract class BaseActivity extends AppCompatActivity
        implements HasComponent<ActivityComponent>, HasToolbar {

    @Inject
    protected FragmentNavigationFactory navigationFactory;

    @Inject
    protected ActivityStarter activityStarter;


    private ActivityComponent activityComponent;

    LocationManager locationManager;

    public LocationManager getLocationManager() {
        return locationManager;
    }

    FirebaseFirestore dbBaseActivity;

    @Inject
    Session session;

    @Inject
    AppPreferences appPreferences;


    public static String currency = "USD";


    @Nullable
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @Nullable
    @BindView(R.id.toolbarTitle)
    protected AppCompatTextView toolbarTitle;

    AlertDialog alertDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(Injector.INSTANCE.getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();

        inject(activityComponent);

/*
        String language = appPreferences.getString(Common.APPLANG);
        if (!language.isEmpty()) {
            ((Startup) getApplication()).changeAppLanguage(language);
        }
*/

        super.onCreate(savedInstanceState);

        locationManager = LocationManager.newInstance(this);

        // set dynamic theme
        if (getIntent().hasExtra(AppNavigationProvider.HasThemeColor.THEME_COLOR)) {
            AppNavigationProvider.HasThemeColor themeColor = (AppNavigationProvider.HasThemeColor) getIntent().getSerializableExtra(AppNavigationProvider.HasThemeColor.THEME_COLOR);
            if (themeColor != null)
                setTheme(themeColor.getTheme());
        }

        setContentView(findContentView());

        ButterKnife.bind(this);


        if (toolbar != null)
            setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);


        setUpAlertDialog();

        /*if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, Common.PERMISSIONS_WRITE_SETTING, Common.REQUEST_WRITE_SETTING);
                return;
            }
        }*/
        //youDesirePermissionCode(this);

        currency = session.getCurrency();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // youDesirePermissionCode(this);
    }

    public static void youDesirePermissionCode(Activity context) {
        boolean permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = Settings.System.canWrite(context);
        } else {
            permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED;
        }
        if (permission) {
            Settings.System.putInt(context.getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED, 1); //To Enable
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivityForResult(intent, Common.REQUEST_WRITE_SETTING);
            } else {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_SETTINGS}, Common.REQUEST_WRITE_SETTING);
            }
        }
    }

    private void setUpAlertDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setPositiveButton("ok", null)
                .create();
        alertDialog.setTitle(R.string.app_name);

    }

    public <F extends BaseFragment> F getCurrentFragment() {
        return (F) getSupportFragmentManager().findFragmentById(findFragmentPlaceHolder());
    }

    public abstract int findFragmentPlaceHolder();

    @LayoutRes
    public abstract int findContentView();

    @Override
    public ActivityComponent getComponent() {

        return activityComponent;
    }

    public abstract void inject(ActivityComponent activityComponent);


    public void showErrorMessage(String message) {

        if (alertDialog.isShowing())
            alertDialog.dismiss();

        alertDialog.setMessage(message);
        alertDialog.show();

    }

    @Override
    public void onBackPressed() {
        hideKeyboard();

        BaseFragment currentFragment = getCurrentFragment();

        if (currentFragment != null) {
            if (currentFragment instanceof ChatFragment) {
                EventBus.getDefault().post(EventBusAction.RECENTCHATUPDATE);
            } else if (currentFragment instanceof LocalIncompleteStepFragment) {
                EventBus.getDefault().post(EventBusAction.UPDATELISTLOCAL);
            } else if (currentFragment instanceof ExperienceFragment) {
                EventBus.getDefault().post(EventBusAction.LOCALEXPSTEP);
            }

        }


        if (currentFragment == null)
            super.onBackPressed();
        else if (currentFragment.onBackActionPerform())
            super.onBackPressed();

        // pending animation
        // overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit);

    }

    public void hideKeyboard() {
        // Check if no view has focus:

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showToolbar(boolean b) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {

            if (b)
                supportActionBar.show();
            else supportActionBar.hide();
        }
    }

    @Override
    public void setToolbarTitle(@NonNull String title) {

        if (toolbarTitle != null) {
            toolbarTitle.setText(title);
        }
    }

    @Override
    public void setToolbarTitle(@StringRes int title) {

        if (toolbarTitle != null) {
            toolbarTitle.setText(title);
        }
    }

    @Override
    public void showBackButton(boolean b) {

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(b);
            //supportActionBar.setDisplayShowHomeEnabled(b);
        }
    }

    @Override
    public void setToolbarColor(@ColorRes int color) {

        if (toolbar != null) {
            toolbar.setBackgroundResource(color);

            /*if (color == R.color.colorWhite) {
                if (toolbarTitle != null)
                    toolbarTitle.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
                if (getSupportActionBar() != null)
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_black);
            } else {
                if (toolbarTitle != null)
                    toolbarTitle.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));

                if (getSupportActionBar() != null)
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_white);
            }*/
        }

    }

    @Override
    public void setDrawerToolbarColor(@ColorRes int color) {

        if (toolbar != null) {
            toolbar.setBackgroundResource(color);

           /* if (color == R.color.colorWhite) {
                if (toolbarTitle != null)
                    toolbarTitle.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
                if (getSupportActionBar() != null)
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
            } else {
                if (toolbarTitle != null)
                    toolbarTitle.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));

                if (getSupportActionBar() != null)
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_white);
            }*/
        }
    }


    @Override
    public void setToolbarElevation(boolean isVisible) {

        if (toolbar != null) {
            AppBarLayout appBarLayout = (AppBarLayout) toolbar.getParent();
            ViewCompat.setElevation(appBarLayout, isVisible ? 8 : 0);
        }
    }


    public String[] permissionToTake(String... s) {

        List<String> permissionList = new ArrayList<>();

        for (String permission : s) {

            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }

        }
        return permissionList.toArray(new String[permissionList.size()]);
    }

    public boolean requestAllPermission() {

        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissionToTake = permissionToTake(Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO);
            if (permissionToTake != null && permissionToTake.length > 0) {
                requestPermissions(permissionToTake, Common.REQUEST_PERMISSION);
                return false;
            }
            return true;
        } else return true;

    }

    public void showKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        locationManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requestCode == Common.REQUEST_WRITE_SETTING && Settings.System.canWrite(this)) {
                Log.d("TAG", "CODE_WRITE_SETTINGS_PERMISSION success");
                //do your code
                Settings.System.putInt(getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED, 1); //To Enable
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Common.REQUEST_WRITE_SETTING && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //do your code
            Settings.System.putInt(getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED, 1); //To Enable
        }
    }


    public FirebaseFirestore getDatabaseBaseActivity() {

        if (dbBaseActivity == null) {
            dbBaseActivity = FirebaseFirestore.getInstance();

            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build();
            dbBaseActivity.setFirestoreSettings(settings);
        }
        return dbBaseActivity;

    }

    @Override
    protected void onResume() {
        super.onResume();
        //   setIsOnLine(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //   setIsOnLine(false);
    }

    private void setIsOnLine(boolean isOnLine) {
        if (session.getUser() != null) {
            if (appPreferences.getBoolean("isLogin")) {

                Map<String, Object> chat = new HashMap<>();

                chat.put(Common.FireStore.FIELD_SENDER_DEVICE_TYPE, session.getUser().getDeviceType());
                chat.put(Common.FireStore.FIELD_SENDER_DEVICE_TOKEN, session.getUser().getDeviceId());
                chat.put(Common.FireStore.FIELD_IS_ONLINE, isOnLine);
                chat.put(Common.FireStore.FIELD_SENDER_IMAGE_URL, session.getUser().getProfileImage());

                getDatabaseBaseActivity().collection(Common.FireStore.TAB_NAME_USER).document(session.getUser().getId()).set(chat).addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        Log.e(":::::: Base Activity", "Data updated successfully");
                    }
                });

            }
        }
    }

    public void setCurrencyForce() {
        currency = session.getCurrency();
    }


    private void updateLanguage() {


    }

}
