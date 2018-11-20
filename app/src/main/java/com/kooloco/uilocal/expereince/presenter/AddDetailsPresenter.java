package com.kooloco.uilocal.expereince.presenter;
/**
 * Created by hlink on 16/4/18.
 */

import com.google.gson.Gson;
import com.kooloco.constant.EventBusAction;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.ExperienceResponse;
import com.kooloco.model.MultiFile;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.Response;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.uilocal.expereince.view.AddDetailsView;
import com.kooloco.util.ImagePicker;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;


@PerActivity
public class AddDetailsPresenter extends BasePresenter<AddDetailsView> {

    @Inject
    Session session;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public AddDetailsPresenter() {
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

    public void callWs(String expId, String title, String descripation, List<MultiFile> fileNames, boolean isEdit) {
        ///{"local_id": "1", "title": "This is my Experience Titile", "description": "Yellow", "images": [ { "file": "123456789.jpg", "file_type": "" } ] }

        Map<String, String> map = new HashMap<>();

        if (!expId.isEmpty()) {
            map.put("experience_id", expId);
        }

        map.put("local_id", session.getUser().getId());
        map.put("title", title);
        map.put("description", descripation);
        map.put("images", new Gson().toJson(fileNames));

        koolocoRepository.setExpAddDetails(map).subscribe(new SubscribeWithView<Response<ExperienceResponse>>(view) {
            @Override
            public void onSuccess(Response<ExperienceResponse> experienceResponseResponse) {
                view.hideLoader();

                if (isEdit) {
                    navigator.goBack();
                } else {
                    view.setExpId(experienceResponseResponse.getData().getId());

                    navigator.openExperienceSportView().hasData(experienceSelectView -> experienceSelectView.setExpId(experienceResponseResponse.getData().getId())).replace(true);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });


    }

    public void getExpData(String expId) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);

        koolocoRepository.getExpAddDetails(map).subscribe(new SubscribeWithView<Response<ExperienceResponse>>(view) {
            @Override
            public void onSuccess(Response<ExperienceResponse> experienceResponseResponse) {
                view.hideLoader();
                view.setData(experienceResponseResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }
}