package com.kooloco.uilocal.profile.presenter;/**
 * Created by hlink44 on 16/10/17.
 */

import com.kooloco.di.PerActivity;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.uilocal.profile.view.EditCertificationsView;
import com.kooloco.util.ImagePicker;

@PerActivity
public class EditCertificationsPresenter extends BasePresenter<EditCertificationsView> {
    @Inject
    public EditCertificationsPresenter() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public void imagePick(ImagePicker.ImagePickerResult imagePickerResult) {
        navigator.pickImage(imagePickerResult);
    }
}
