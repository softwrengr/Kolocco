package com.kooloco.uilocal.addservice.presenter;/**
 * Created by hlink44 on 3/10/17.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Certifications;
import com.kooloco.model.Response;
import com.kooloco.model.SportActivity;
import com.kooloco.model.Tag;
import com.kooloco.model.User;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.uilocal.addservice.view.UploadAchievementsView;
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
public class UploadAchievementsPresenter extends BasePresenter<UploadAchievementsView> {
    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    Session session;

    @Inject
    public UploadAchievementsPresenter() {

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

    public void openChooseLanguage() {
  //      navigator.openChooseLangaugeView().replace(true);

        //As per client comment set choose language navigation
        navigator.openSetLocationView().replace(true);
    }

    public void getSportType() {
        view.showLoader();

        HashMap<String, String> map = new HashMap<>();

        kooocoRepository.sportType(map).subscribe(new SubscribeWithView<Response<List<SportActivity>>>(view) {
            @Override
            public void onSuccess(Response<List<SportActivity>> response) {
                view.hideLoader();
                //  view.showMessage(response.getMessage());
                getTag(response.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }

    public void getTag(final List<SportActivity> data) {
        view.showLoader();

        HashMap<String, String> map = new HashMap<>();

        kooocoRepository.tagList(map).subscribe(new SubscribeWithView<Response<List<Tag>>>(view) {
            @Override
            public void onSuccess(Response<List<Tag>> response) {
                view.hideLoader();
                view.setSportList(data, response.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void getSelectAchivements(boolean isShowing) {
        if (isShowing) {
            view.showLoader();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());

        kooocoRepository.getAchivements(map).subscribe(new SubscribeWithView<Response<List<Certifications>>>(view) {
            @Override
            public void onSuccess(Response<List<Certifications>> response) {
                if (isShowing) {
                    view.hideLoader();
                }
                //  view.showMessage(response.getMessage());
                view.setData(response.getData());
            }

            @Override
            public void onError(Throwable e) {
                // super.onError(e);
                if (isShowing) {
                    view.hideLoader();
                }
                view.setData(new ArrayList<Certifications>());
            }
        });
    }


    public void callWs(final Certifications certificationsD, boolean isDone, boolean isEdit) {

        view.uploadImages(certificationsD.getImageUrl(), "uploadAchievement", new BaseFragment.CallBackUploadImages() {
            @Override
            public void onCallBack(String imagePath) {
                //view.showLoader();

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


                String tagId = "";

                for (Tag tag : certificationsD.getTags()) {
                    if (tag.isSelect()) {
                        if (tagId.isEmpty()) {
                            tagId = tagId + tag.getId();
                        } else {
                            tagId = tagId + "," + tag.getId();
                        }
                    }
                }
                map.put("tags", tagId);

                if (!imagePath.isEmpty()) {
                    map.put("achievement_doc", imagePath);
                }

                kooocoRepository.uploadAchivementsStepFour(map).subscribe(new SubscribeWithView<Response<User>>(view) {
                    @Override
                    public void onSuccess(Response<User> userResponse) {
                        view.hideUploadImageDialog();
                        view.showMessage(userResponse.getMessage());
                        // view.showMessage(userResponse.getMessage());

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
        map.put("achievement_id", certifications.getId());

        kooocoRepository.deleteAchivement(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response response) {
                view.hideLoader();
                view.showMessage(response.getMessage());
                getSelectAchivements(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }
}
