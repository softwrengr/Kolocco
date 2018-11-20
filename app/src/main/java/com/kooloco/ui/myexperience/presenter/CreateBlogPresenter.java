package com.kooloco.ui.myexperience.presenter;/**
 * Created by hlink44 on 6/11/17.
 */

import com.google.gson.Gson;
import com.kooloco.R;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.imagevideo.ImageAndVideoPicker;
import com.kooloco.model.AddImages;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.ExperienceDetails;
import com.kooloco.model.MultiFile;
import com.kooloco.model.OrderDetails;
import com.kooloco.model.Quation;
import com.kooloco.model.QuationRequest;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.myexperience.fragment.ImageFilterFragment;
import com.kooloco.ui.myexperience.view.CreateBlogView;
import com.kooloco.ui.myexperience.view.ImageFilterView;
import com.kooloco.ui.visitor.organizationDetails.view.LocalVisitorMapView;
import com.kooloco.util.ImagePicker;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;

@PerActivity
public class CreateBlogPresenter extends BasePresenter<CreateBlogView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    public void imagePick(ImagePicker.ImagePickerResult imagePickerResult) {
        navigator.pickImage(imagePickerResult);
    }


    @Inject
    public CreateBlogPresenter() {

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

    public void pickVideoOrImage(ImageAndVideoPicker.ImageCallBack imageCallBack) {
        navigator.openImageOrVideoPickDefault(imageCallBack, true);
    }

    public void openImageFilter(String imagePath, ImageFilterFragment.imageFilterCallback imageFilterCallback) {
        navigator.openImageFilterView().hasData(new Passable<ImageFilterView>() {
            @Override
            public void passData(ImageFilterView imageFilterView) {
                imageFilterView.setImageUrl(imagePath);
                imageFilterView.setCallBack(imageFilterCallback);
            }
        }).replace(true);
    }

    public void getQuation() {
//        {"type":"experience"}
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("type", "experience");

        koolocoRepository.getQuation(map).subscribe(new SubscribeWithView<Response<List<Quation>>>(view) {
            @Override
            public void onSuccess(Response<List<Quation>> listResponse) {
                view.hideLoader();
                view.setQuation(listResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });


    }


    public void callWs(ExperienceDetails experienceDetails, float rating, String writeNow, List<String> hasTag, List<MultiFile> imagePath, String question, List<Quation> data, int selectAns) {
/*
        {
            "order_id": "101",
                "local_id": "20",
                "description": "Djdjdkd",
                "user_id": "17",
                "questions": [{
            "answer_id": "2",
                    "question_id": "1"
        },
            {
                "answer_id": "2",
                    "question_id": "1"
            }
        ],
            "tags": "#Bali,#abad",
                "rate": "5.0",
                "media": [{}]
        }
*/

        String hasTagData = "";

        for (String s : hasTag) {

            if (hasTagData.isEmpty()) {
                hasTagData = hasTagData + "#" + s;
            } else {
                hasTagData = hasTagData + ", #" + s;

            }
        }

        List<QuationRequest> quationRequests = new ArrayList<>();

        QuationRequest quationRequest = new QuationRequest();

        quationRequest.setQuestionId(data.get(0).getId());

        quationRequest.setAnswerId(data.get(0).getAnswer().get(selectAns).getId());

        quationRequests.add(quationRequest);

        Map<String, String> map = new HashMap<>();
        map.put("order_id", experienceDetails.getId());
        map.put("local_id", experienceDetails.getLocalId());
        map.put("description", writeNow);
        map.put("user_id", session.getUser().getId());

        map.put("questions", new Gson().toJson(quationRequests));

        map.put("tags", hasTagData);

        map.put("rate", "" + rating);
        map.put("media", new Gson().toJson(imagePath));

        if (experienceDetails.getIsPublished().equalsIgnoreCase("1")) {

            map.put("blog_id", experienceDetails.getBlogId());

            koolocoRepository.editExperience(map).subscribe(new SubscribeWithView<Response>(view) {
                @Override
                public void onSuccess(Response response) {
                    view.hideLoader();
                    view.showMessage(response.getMessage());
                    navigator.goBack();
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    view.hideLoader();
                }
            });

        } else {
            koolocoRepository.publishExperience(map).subscribe(new SubscribeWithView<Response>(view) {
                @Override
                public void onSuccess(Response response) {
                    view.hideLoader();
                    view.showMessage(response.getMessage());
                    navigator.goBack();
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    view.hideLoader();
                }
            });
        }
    }

    public void callDeleteMediea(AddImages addImagesD) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("media_id", addImagesD.getSetId());

        koolocoRepository.deleteBlogMediea(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.hideLoader();
                view.deleteMedia(addImagesD);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }

    public void openMapScreen(ExperienceDetails experienceDetails) {
        DashboardDetails dashboardDetails = new DashboardDetails();


        dashboardDetails.setFirstname(experienceDetails.getFirstname());
        dashboardDetails.setLastname(experienceDetails.getLastname());

        dashboardDetails.setImageUrl(experienceDetails.getProfileImage());

        dashboardDetails.setLocalLocation(experienceDetails.getMeetingAddress());

        dashboardDetails.setLatitude(experienceDetails.getMeetingLatitude());
        dashboardDetails.setLongitude(experienceDetails.getMeetingLongitude());

        navigator.openLocalAndVistorMap().hasData(new Passable<LocalVisitorMapView>() {
            @Override
            public void passData(LocalVisitorMapView localVisitorMapView) {
                localVisitorMapView.setData(dashboardDetails);
            }
        }).replace(true);
    }

}
