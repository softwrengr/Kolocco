package com.kooloco.ui.invite.presenter;/**
 * Created by hlink44 on 25/9/17.
 */

import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Invite;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.invite.view.InviteListView;
import com.kooloco.util.SubscribeWithView;

import java.util.List;

@PerActivity
public class InviteListPresenter extends BasePresenter<InviteListView> {
    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    public InviteListPresenter() {

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
        kooocoRepository.getInvites().subscribe(new SubscribeWithView<Response<List<Invite>>>(view) {
            @Override
            public void onSuccess(Response<List<Invite>> listResponse) {
                view.setData(listResponse.getData());
            }
        });
    }
}
