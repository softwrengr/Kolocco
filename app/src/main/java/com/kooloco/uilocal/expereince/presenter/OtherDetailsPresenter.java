package com.kooloco.uilocal.expereince.presenter;
/**
 * Created by hlink on 16/4/18.
 */

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.model.ExperienceResponse;
import com.kooloco.model.Language;
import com.kooloco.model.OtheDetailsResponse;
import com.kooloco.model.OtherDetailsFieldsSelect;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.uilocal.expereince.view.OtherDetailsView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class OtherDetailsPresenter extends BasePresenter<OtherDetailsView> {

    @Inject
    Session session;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public OtherDetailsPresenter() {
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

    public void callWs(String expId, String highLight, String include, String notInclude, List<String> hasTag, List<OtherDetailsFieldsSelect> otherDetailsFieldsSelectsRec, List<OtherDetailsFieldsSelect> otherDetailsFieldsSelectsPerfect, boolean isSelectAll, boolean isEdit) {
        //{ "experience_id": "1", "highlight": "This is a dummy test", "included": "This is a dummy test", "not_included": "This is a dummy test", "tags": "#test, #cool,#check", "recommended": "1,2", "prefered_for": "1,2" }

        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);
        map.put("highlight", highLight);
        map.put("included", include);
        map.put("not_included", notInclude);

        String hasTagData = "";

        for (String s : hasTag) {

            if (hasTagData.isEmpty()) {
                hasTagData = hasTagData + "#" + s;
            } else {
                hasTagData = hasTagData + ",#" + s;

            }
        }

        map.put("tags", hasTagData);

        if(isSelectAll){
            map.put("recommended", getKeyAll(otherDetailsFieldsSelectsRec));
        }else {
            map.put("recommended", getKey(otherDetailsFieldsSelectsRec));
        }

        map.put("prefered_for", getKey(otherDetailsFieldsSelectsPerfect));

        koolocoRepository.setExpOtherDetails(map).subscribe(new SubscribeWithView<Response<ExperienceResponse>>(view) {
            @Override
            public void onSuccess(Response<ExperienceResponse> experienceResponseResponse) {
                view.hideLoader();
                if (isEdit) {
                    navigator.goBack();
                } else {
                    navigator.openExperienceOtherDetailsAnotherFieldsView().hasData(otherDetailsAnotherFieldsView -> otherDetailsAnotherFieldsView.setExpId(expId)).replace(true);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }

    public void getData(String expId) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);

        koolocoRepository.getExpOtherDetails(map).subscribe(new SubscribeWithView<Response<OtheDetailsResponse>>(view) {
            @Override
            public void onSuccess(Response<OtheDetailsResponse> otheDetailsResponseResponse) {

                List<String> hasTag = new ArrayList<>();

                for (String tag : otheDetailsResponseResponse.getData().getTags()) {
                    hasTag.add(tag.substring(1));
                }

                OtheDetailsResponse data = otheDetailsResponseResponse.getData();
                data.setTags(hasTag);

                view.setData(data);
                view.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    private String getKey(List<OtherDetailsFieldsSelect> otherDetailsFieldsSelects) {
        String lngId = "";

        for (OtherDetailsFieldsSelect language : otherDetailsFieldsSelects) {
            if (language.isSelect()) {
                if (lngId.isEmpty()) {
                    lngId = lngId + language.getId();
                } else {
                    lngId = lngId + "," + language.getId();
                }
            }
        }

        return lngId;
    }

    private String getKeyAll(List<OtherDetailsFieldsSelect> otherDetailsFieldsSelects) {
        String lngId = "";

        for (OtherDetailsFieldsSelect language : otherDetailsFieldsSelects) {
                if (lngId.isEmpty()) {
                    lngId = lngId + language.getId();
                } else {
                    lngId = lngId + "," + language.getId();
                }
        }

        return lngId;
    }

}