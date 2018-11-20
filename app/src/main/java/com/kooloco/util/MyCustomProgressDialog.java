package com.kooloco.util;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


import com.kooloco.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MyCustomProgressDialog extends android.support.v4.app.DialogFragment implements Animation.AnimationListener {
    @BindView(R.id.imageViewLoader)
    AppCompatImageView imageViewLoader;
    @BindView(R.id.textViewMessage)
    AppCompatTextView textViewMessage;

    private Animation fadeIn;
    private Animation fadeOut;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_progress, container, false);
        ButterKnife.bind(this, view);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getDialog().getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = getResources().getDimensionPixelOffset(R.dimen.dp_190);
        lp.height = getResources().getDimensionPixelOffset(R.dimen.dp_130);
        window.setAttributes(lp);
        setCancelable(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        fadeIn.setAnimationListener(this);
        fadeOut.setAnimationListener(this);
        imageViewLoader.startAnimation(fadeIn);
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

}
