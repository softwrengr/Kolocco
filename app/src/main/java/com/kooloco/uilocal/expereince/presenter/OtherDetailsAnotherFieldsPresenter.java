package com.kooloco.uilocal.expereince.presenter;
/**
 * Created by hlink on 16/4/18.
 */

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.AddImages;
import com.kooloco.model.ExperienceResponse;
import com.kooloco.model.Medium;
import com.kooloco.model.MultiFile;
import com.kooloco.model.OtherDetailsAnotherFields;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.uilocal.expereince.view.OtherDetailsAnotherFieldsView;
import com.kooloco.util.ImagePicker;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


@PerActivity
public class OtherDetailsAnotherFieldsPresenter extends BasePresenter<OtherDetailsAnotherFieldsView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public OtherDetailsAnotherFieldsPresenter() {
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

    public void openSetLocation(String expId) {
        navigator.openExperienceMeetingSpotView().hasData(experienceMeetingSpotView -> experienceMeetingSpotView.setExpId(expId)).replace(true);
    }

    public void getData(String expId) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);

        koolocoRepository.getExpOtherDetailsFields(map).subscribe(new SubscribeWithView<Response<List<OtherDetailsAnotherFields>>>(view) {
            @Override
            public void onSuccess(Response<List<OtherDetailsAnotherFields>> listResponse) {
                mapImages(listResponse.getData());
                view.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ServerException) {
                    ServerException exception = (ServerException) e;
                    if (exception.getServerCode() != 0) {
                        super.onError(e);
                    }
                } else {
                    super.onError(e);
                }
                view.setData(new ArrayList<>());
                view.hideLoader();
            }
        });
    }

    private void mapImages(List<OtherDetailsAnotherFields> otherDetailsAnotherFields) {


        Observable.fromIterable(otherDetailsAnotherFields).map(otherDetailsAnotherFields1 -> {
            List<AddImages> tempImages = new ArrayList<>();

            for (Medium medium : otherDetailsAnotherFields1.getMedia()) {
                AddImages addImage = new AddImages();

                addImage.setImageUrl(medium.getImage());
                addImage.setSetId(medium.getId());
                addImage.setFileName(medium.getFile());
                addImage.setVideo(false);
                tempImages.add(addImage);
            }

            otherDetailsAnotherFields1.setAddImages(tempImages);
            return otherDetailsAnotherFields;

        }).subscribe(lists -> {
            view.setData(lists);
        });

    }


    public void callWs(String addFiledsId, String expId, String title, String desc, List<MultiFile> imagePathList, CallBack callBack) {

        //{"field_id":"2","experience_id":"1","title":"","description":"This is a dummy text","images": [ { "file": "123456789.jpg", "media_type": "" } ]}

        new android.os.Handler().postDelayed(() -> {
            Map<String, String> map = new HashMap<>();

            if (!expId.isEmpty()) {
                map.put("field_id", addFiledsId);
            }

            map.put("experience_id", expId);
            map.put("title", title);
            map.put("description", desc);
            map.put("images", new Gson().toJson(imagePathList));

            koolocoRepository.setExpOtherDetailsFields(map).subscribe(new SubscribeWithView<Response<OtherDetailsAnotherFields>>(view) {
                @Override
                public void onSuccess(Response<OtherDetailsAnotherFields> otherDetailsAnotherFieldsResponse) {
                    view.hideLoader();
                    callBack.onSuccess(true, otherDetailsAnotherFieldsResponse.getMessage(), otherDetailsAnotherFieldsResponse.getData());

                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    view.hideLoader();
                    callBack.onSuccess(false, e.getMessage(), null);
                }
            });
        }, 3000);


    }

    public void callDeleteWs(OtherDetailsAnotherFields otherDetailsAnotherField, int pos) {

        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("field_id", otherDetailsAnotherField.getId());

        koolocoRepository.deleteExpOtherDetailsFields(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response response) {
                view.deleteExpOtherFiends(otherDetailsAnotherField, pos);
                view.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public interface CallBack {
        void onSuccess(boolean isSuccess, String message, OtherDetailsAnotherFields otherDetailsAnotherFields);
    }
}