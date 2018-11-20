package com.kooloco.uilocal.profile.fragment;
/**
 * Created by hlink on 20/3/18.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.core.Session;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.profile.adapter.LocalInCompleteStepAdapter;
import com.kooloco.uilocal.profile.presenter.LocalIncompleteStepPresenter;
import com.kooloco.uilocal.profile.view.LocalIncompleteStepView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;

/**
 * Created by hlink on 8/1/18.
 */

public class LocalIncompleteStepFragment extends BaseFragment<LocalIncompleteStepPresenter, LocalIncompleteStepView> implements LocalIncompleteStepView {

    @BindView(R.id.recyclerStepInComplete)
    RecyclerView recyclerStepInComplete;
    @BindView(R.id.recyclerStepComplete)
    RecyclerView recyclerStepComplete;

    @Inject
    Session session;
    @BindView(R.id.appCompatTextView)
    AppCompatTextView appCompatTextView;
    @BindView(R.id.appCompatTextView2)
    AppCompatTextView appCompatTextView2;

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_incomplete_step;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }


    @Override
    protected LocalIncompleteStepView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        setData();
    }

    private void setData() {

        Observable.fromIterable(session.getUser().getProfileStatuses()).filter(profileStatus -> profileStatus.getIsComplete().equalsIgnoreCase("0")).toList().subscribe(profileStatuses -> {

            if (profileStatuses.size() == 0) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            goBack();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                if (appCompatTextView != null) {
                    appCompatTextView.setVisibility(View.GONE);
                }
            }

            if (recyclerStepInComplete != null) {
                recyclerStepInComplete.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                recyclerStepInComplete.setAdapter(new LocalInCompleteStepAdapter(getActivity(), profileStatuses, v1 -> {
                    switch (v1.getStep()) {
                        case "1":
                            //presenter.openBecomeAlocal();
                            break;
                        case "2":
                            presenter.openAddImages();
                            break;
                        case "3":
                            presenter.openUploadCertificates();
                            break;
                        case "4":
                            presenter.openUploadAchivements();
                            break;
                        case "5":
                            presenter.openChooseLanguage();
                            break;
                        case "6":
                            presenter.openLocationView();
                            break;
                        case "7":
                            presenter.openExp();
                            break;
                        case "8":
                            presenter.openAddBank();
                            break;
                    }
                }));

            }
        });

        Observable.fromIterable(session.getUser().getProfileStatuses()).filter(profileStatus -> profileStatus.getIsComplete().equalsIgnoreCase("1")).toList().subscribe(profileStatuses -> {
            if (recyclerStepComplete != null) {
                recyclerStepComplete.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                recyclerStepComplete.setAdapter(new LocalInCompleteStepAdapter(getActivity(), profileStatuses, v1 -> {

                }));

            }
        });


    }

    @OnClick(R.id.imageViewBack)
    public void onClick() {
        goBack();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(EventBusAction action) {
        if (action == EventBusAction.LOCALEXPSTEP) {
            presenter.getDataUser();
        }
    }

    @Override
    public void updateData() {
        setData();
    }
}
