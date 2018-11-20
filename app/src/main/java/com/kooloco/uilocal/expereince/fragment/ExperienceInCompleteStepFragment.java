package com.kooloco.uilocal.expereince.fragment;
/**
 * Created by hlink on 17/4/18.
 */

import android.app.Dialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kooloco.R;
import com.kooloco.core.Session;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ProfileStatus;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.expereince.presenter.ExperienceInCompleteStepPresenter;
import com.kooloco.uilocal.expereince.view.ExperienceInCompleteStepView;
import com.kooloco.uilocal.profile.adapter.LocalInCompleteStepAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by hlink on 8/1/18.
 */

public class ExperienceInCompleteStepFragment extends BaseFragment<ExperienceInCompleteStepPresenter, ExperienceInCompleteStepView> implements ExperienceInCompleteStepView {
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
    private ExpereinceNew expNew;

    @Override
    protected int createLayout() {
        return R.layout.exp_local_fragment_in_complete_step;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ExperienceInCompleteStepView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        if (expNew != null) {
            setData(expNew.getProgress());
            presenter.getDataExp(expNew, false);
        }
    }

    @Override
    public void setData(List<ProfileStatus> data) {

        Observable.fromIterable(data).filter(profileStatus -> profileStatus.getIsComplete().equalsIgnoreCase("0")).toList().subscribe(profileStatuses -> {

            if (profileStatuses.size() == 0) {
                goBack();
                if (appCompatTextView != null) {
                    appCompatTextView.setVisibility(View.GONE);
                }
            }

            if (recyclerStepInComplete != null) {
                recyclerStepInComplete.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                recyclerStepInComplete.setAdapter(new LocalInCompleteStepAdapter(getActivity(), profileStatuses, v1 -> {
                    if (expNew == null) {
                        return;
                    }
                    presenter.setExpData(expNew);
                    switch (v1.getStep()) {
                        case "1":
                            presenter.openAddDetails();
                            break;
                        case "2":
                            presenter.openSport();
                            break;
                        case "3":
                            presenter.openSchedulePrice();
                            break;
                        case "4":
                            presenter.openOtherDetails();
                            break;
                        case "5":
                            presenter.openMeetingSpot();
                            break;
                        case "6":
                            presenter.openCancellationPolice();
                            break;
                    }
                }));

            }
        });

        Observable.fromIterable(data).filter(profileStatus -> profileStatus.getIsComplete().equalsIgnoreCase("1")).toList().subscribe(profileStatuses -> {
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
    public void setExpData(ExpereinceNew dataExp) {
        this.expNew = dataExp;
    }
}
