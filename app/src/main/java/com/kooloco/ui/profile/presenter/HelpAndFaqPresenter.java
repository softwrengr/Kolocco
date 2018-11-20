package com.kooloco.ui.profile.presenter;/**
 * Created by hlink44 on 27/9/17.
 */

import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.HelpAndFaq;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.profile.view.HelpAndFaqView;
import com.kooloco.util.SubscribeWithView;

import java.util.List;

@PerActivity
public class HelpAndFaqPresenter extends BasePresenter<HelpAndFaqView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public HelpAndFaqPresenter() {

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
        koolocoRepository.getHelpAndFaq().subscribe(new SubscribeWithView<Response<List<HelpAndFaq>>>(view) {
            @Override
            public void onSuccess(Response<List<HelpAndFaq>> listResponse) {
                view.setData(listResponse.getData());
            }
        });
    }
}
