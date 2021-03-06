package com.kooloco.uilocal.addservice.presenter;/**
 * Created by hlink44 on 2/10/17.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Certifications;
import com.kooloco.model.Response;
import com.kooloco.model.Sport;
import com.kooloco.model.SportActivity;
import com.kooloco.model.User;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.uilocal.addservice.view.AddImageView;
import com.kooloco.util.ImagePicker;
import com.kooloco.util.SubscribeWithView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@PerActivity
public class AddImagePresenter extends BasePresenter<AddImageView> {
    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    Session session;

    @Inject
    public AddImagePresenter() {

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

    public void getData() {

    }

    public void imagePick(ImagePicker.ImagePickerResult imagePickerResult) {
        navigator.pickImage(imagePickerResult);
    }

    public void openUploadCertificates() {
        navigator.openUploadCertificateView().replace(true);
    }

    public void getSportType() {
        view.showLoader();

        HashMap<String, String> map = new HashMap<>();

        kooocoRepository.sportType(map).subscribe(new SubscribeWithView<Response<List<SportActivity>>>(view) {
            @Override
            public void onSuccess(Response<List<SportActivity>> response) {
                view.hideLoader();
                //  view.showMessage(response.getMessage());
                view.setSportList(response.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void getSelectSportList(boolean isShowLoder) {
        if (isShowLoder) {
            view.showLoader();

        }

        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());

        kooocoRepository.getSport(map).subscribe(new SubscribeWithView<Response<List<Certifications>>>(view) {
            @Override
            public void onSuccess(Response<List<Certifications>> response) {
                if (isShowLoder) {
                    view.hideLoader();
                }
                //  view.showMessage(response.getMessage());
                view.setData(response.getData());
            }

            @Override
            public void onError(Throwable e) {
                //super.onError(e);
                view.setData(new ArrayList<Certifications>());
                if (isShowLoder) {
                    view.hideLoader();
                }
            }
        });
    }

    public void callWs(final Certifications certificationsD, boolean isDone, boolean isEdit) {
        //   view.showLoader();

        view.uploadImages(certificationsD.getImageUrl(), "addImages", new BaseFragment.CallBackUploadImages() {
            @Override
            public void onCallBack(String imagePath) {

                // view.showLoader();

                HashMap<String, String> map = new HashMap<>();
                map.put("user_id", session.getUser().getId());
                map.put("sport_type_id", certificationsD.getSportActivity().getId());
                map.put("description", certificationsD.getDesc());
                map.put("add_image_id", certificationsD.getAddImageId());

                if (certificationsD.getSportSubActivity() != null) {
                    map.put("sport_sub_type_id", certificationsD.getSportSubActivity().getId());
                    map.put("title", certificationsD.getSportSubActivity().getName() + " " + certificationsD.getSportActivity().getName());
                } else {
                    map.put("title", certificationsD.getSportActivity().getName());
                }

                if (!imagePath.isEmpty()) {
                    map.put("image", imagePath);
                }

                kooocoRepository.addImageStepTwo(map).subscribe(new SubscribeWithView<Response<User>>(view) {
                    @Override
                    public void onSuccess(Response<User> userResponse) {
                        view.hideUploadImageDialog();
                        view.showMessage(userResponse.getMessage());
                        if (isDone) {
                            view.clearValueNew();
                        } else {
                            view.add();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.hideUploadImageDialog();
                    }
                });
            }
        });

    }

    public void callDeleteWs(Certifications certifications) {
        view.showLoader();

        HashMap<String, String> map = new HashMap<>();
        map.put("image_id", certifications.getId());

        kooocoRepository.deleteSportImage(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response response) {
                view.hideLoader();
                view.showMessage(response.getMessage());
                getSelectSportList(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }
}
