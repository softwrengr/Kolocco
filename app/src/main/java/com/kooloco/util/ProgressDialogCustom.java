package com.kooloco.util;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.kooloco.R;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink on 26/2/18.
 */

public class ProgressDialogCustom extends Dialog implements Animation.AnimationListener {

    @BindView(R.id.imageViewLoader)
    AppCompatImageView imageViewLoader;
    @BindView(R.id.textViewMessage)
    AppCompatTextView textViewMessage;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    private Animation fadeIn;
    private Animation fadeOut;


    public ProgressDialogCustom(@NonNull Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null, false);
        ButterKnife.bind(this, view);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = context.getResources().getDimensionPixelOffset(R.dimen.dp_190);
        lp.height = context.getResources().getDimensionPixelOffset(R.dimen.dp_130);
        window.setAttributes(lp);
        setCancelable(false);

        fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        fadeIn.setAnimationListener(this);
        fadeOut.setAnimationListener(this);
        imageViewLoader.startAnimation(fadeIn);
        setContentView(view);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation.equals(fadeIn))
            imageViewLoader.startAnimation(fadeOut);
        else
            imageViewLoader.startAnimation(fadeIn);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void startAniationAgain() {
        fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        fadeIn.setAnimationListener(this);
        fadeOut.setAnimationListener(this);
        imageViewLoader.startAnimation(fadeIn);
        avi.smoothToShow();

    }

    public void hideAniationAgain() {
/*
        fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        fadeIn.setAnimationListener(this);
        fadeOut.setAnimationListener(this);
        imageViewLoader.startAnimation(fadeIn);
*/

        avi.smoothToHide();


    }

}
