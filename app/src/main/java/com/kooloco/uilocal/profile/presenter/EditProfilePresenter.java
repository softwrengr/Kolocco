package com.kooloco.uilocal.profile.presenter;/**
 * Created by hlink44 on 16/10/17.
 */

import com.kooloco.core.AppPreferences;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Response;
import com.kooloco.model.User;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.uilocal.profile.view.EditProfileView;
import com.kooloco.util.ImagePicker;
import com.kooloco.util.SubscribeWithView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@PerActivity
public class EditProfilePresenter extends BasePresenter<EditProfileView> {
    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    Session session;

    @Inject
    AppPreferences appPreferences;

    @Inject
    public EditProfilePresenter() {

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

    public void getUserData() {
        view.setData(session.getUser());
    }

    public void updateProfile(final String firstName, final String lastName, final String introSelf, String imageUrl,String countryCode,String phone) {
        view.showLoader();

        view.uploadImages(imageUrl, "update_profile", new BaseFragment.CallBackUploadImages() {
            @Override
            public void onCallBack(String imagePath) {

                // view.showLoader();

                HashMap<String, String> map = new HashMap<>();
                map.put("user_id", session.getUser().getId());

                map.put("firstname", firstName);
                map.put("lastname", lastName);
                map.put("lastname", lastName);
                map.put("intro_your_self", introSelf);

                if(!phone.isEmpty()){
                    map.put("country_code", "" + countryCode);

                    map.put("phone", "" + phone);
                }

                if (!imagePath.isEmpty()) {
                    map.put("profile_image", imagePath);
                }

                kooocoRepository.updateProfile(map).subscribe(new SubscribeWithView<Response<User>>(view) {
                    @Override
                    public void onSuccess(Response<User> userResponse) {
                        view.hideLoader();
                        // view.hideUploadImageDialog();
                        navigator.goBack();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        //view.hideUploadImageDialog();
                        view.hideLoader();
                    }
                });

            }
        });

    }


}
