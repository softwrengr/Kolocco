package com.kooloco.ui.splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.annotation.NonNull;
import android.support.transition.Slide;
import android.support.transition.TransitionManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kooloco.R;
import com.kooloco.constant.Common;
import com.kooloco.core.AppPreferences;
import com.kooloco.core.Session;
import com.kooloco.di.component.ActivityComponent;
import com.kooloco.ui.MainActivity;
import com.kooloco.ui.authantication.AuthActivity;
import com.kooloco.ui.navigation.AppNavigationProvider;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.MainLocalActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.animation.SpringForce.DAMPING_RATIO_HIGH_BOUNCY;
import static android.support.animation.SpringForce.STIFFNESS_LOW;

public class SplashActivity extends AppNavigationProvider {
    AppPreferences appPreferences;
    @BindView(R.id.imageViewLogo)
    ImageView imageViewLogo;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.buttonStart)
    AppCompatButton buttonStart;
    private VelocityTracker velocityTracker;
    private float velocity;

    @Inject
    Session session;

    android.os.Handler handler;
    Runnable runnable;

    @Override
    public int findContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        velocityTracker = VelocityTracker.obtain();
        appPreferences = new AppPreferences(this);
/*
        appPreferences.putBoolean("isLogin", false);
*/
        //int i = 10 / 0;

        facebookHaskey();

        imageViewLogo.setVisibility(appPreferences.getBoolean("isLogin") ? View.INVISIBLE : View.VISIBLE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        //As per client requirement remove animation
        /*Change xml set scale x and y 0 , imageViewLogo animLogo open comment*/


        //  imageViewLogo.setScaleX(0.0f);
        //    imageViewLogo.setScaleY(0.0f);
        runnable = new Runnable() {
            @Override
            public void run() {
                /* animLogo(imageViewLogo, DynamicAnimation.SCALE_X, 1);
                animLogo(imageViewLogo, DynamicAnimation.SCALE_Y, 1);
                */
                TransitionManager.beginDelayedTransition(root, new Slide(Gravity.BOTTOM));
                //               buttonStart.setVisibility(View.VISIBLE);
                buttonStart.setVisibility(appPreferences.getBoolean("isLogin") ? View.INVISIBLE : View.VISIBLE);


                if (appPreferences.getBoolean("isLogin")) {

                    if (!appPreferences.getBoolean("isOnBoard")) {

                        Bundle bundle = new Bundle();
                        bundle.putString("sports", "[]");

                        openIsloatedFullActivity().setPage(AppNavigator.Pages.ONBORDING).addBundle(bundle).byFinishingAll().start();

                    } else {
                        Intent authanticationIntent;
                        if (session.getUser().getRole().equalsIgnoreCase("L") /*&& session.getUser().getIsAdminApprove().equalsIgnoreCase("1")*/) {
                            authanticationIntent = new Intent(SplashActivity.this, MainLocalActivity.class);
                        } else {
                            authanticationIntent = new Intent(SplashActivity.this, MainActivity.class);
                        }
                        startActivity(authanticationIntent);
                        finish();
                    }

                }
                else {
                    Toast.makeText(SplashActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

        };
        handler = new Handler();
        handler.postDelayed(runnable, 700);

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    public void facebookHaskey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.kooloco", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash Customer :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @OnClick(R.id.buttonStart)
    public void onViewClicked() {
        if (appPreferences.getBoolean("isLogin")) {

            if (!appPreferences.getBoolean("isOnBoard")) {

                Bundle bundle = new Bundle();
                bundle.putString("sports", "[]");

                openIsloatedFullActivity().setPage(AppNavigator.Pages.ONBORDING).addBundle(bundle).byFinishingAll().start();

            } else {
                Intent authanticationIntent;
                if (session.getUser().getRole().equalsIgnoreCase("L") /*&& session.getUser().getIsAdminApprove().equalsIgnoreCase("1")*/) {
                    authanticationIntent = new Intent(SplashActivity.this, MainLocalActivity.class);
                } else {
                    authanticationIntent = new Intent(SplashActivity.this, MainActivity.class);
                }

                startActivity(authanticationIntent);
                finish();
            }

        } else {
            Intent authanticationIntent = new Intent(SplashActivity.this, AuthActivity.class);
            startActivity(authanticationIntent);
            finish();
        }
    }

    private void animLogo(View view, DynamicAnimation.ViewProperty viewProperty, float fromPosition) {
        SpringAnimation anim = new SpringAnimation(view, viewProperty, fromPosition);
        anim.getSpring().setStiffness(STIFFNESS_LOW);
        anim.getSpring().setDampingRatio(DAMPING_RATIO_HIGH_BOUNCY);
        velocityTracker.computeCurrentVelocity(2000);
        velocity = velocityTracker.getYVelocity();
        anim.setStartVelocity(velocity);
        anim.start();
    }

}
