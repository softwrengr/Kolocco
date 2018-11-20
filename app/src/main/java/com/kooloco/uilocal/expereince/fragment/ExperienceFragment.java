package com.kooloco.uilocal.expereince.fragment;
/**
 * Created by hlink on 13/4/18.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ProfileStatus;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.expereince.adapter.ExperiencesAdapter;
import com.kooloco.uilocal.expereince.presenter.ExperiencePresenter;
import com.kooloco.uilocal.expereince.view.ExperienceView;
import com.kooloco.util.LocationManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

/**
 * Created by hlink on 8/1/18.
 */

public class ExperienceFragment extends BaseFragment<ExperiencePresenter, ExperienceView> implements ExperienceView {

    @BindView(R.id.recyclerExperiences)
    RecyclerView recyclerExperiences;
    ExperiencesAdapter experiencesAdapter;

    List<ExpereinceNew> expereinceNews;
    @BindView(R.id.textViewSkip)
    AppCompatTextView textViewSkip;
    Unbinder unbinder;
    @BindView(R.id.buttonUpdate)
    AppCompatButton buttonUpdate;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private boolean isLocalFlow;

    @Override
    protected int createLayout() {
        return R.layout.exp_local_fragment_create;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ExperienceView createView() {
        return this;
    }

    @Override
    protected void bindData() {


        ((BaseActivity) getActivity()).getLocationManager().triggerLocation(true, new LocationManager.LocationListener() {
            @Override
            public void onLocationAvailable(LatLng latLng) {
                BaseFragment.latitude = latLng.latitude;
                BaseFragment.longitude = latLng.longitude;
            }

            @Override
            public void onFail(Status status) {

            }
        });

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        textViewSkip.setVisibility(isLocalFlow ? View.VISIBLE : View.GONE);

        buttonUpdate.setText(isLocalFlow ? getString(R.string.button_next) : getString(R.string.button_done));


        expereinceNews = new ArrayList<>();
        experiencesAdapter = new ExperiencesAdapter(getActivity(), expereinceNews, new ExperiencesAdapter.CallBack() {
            @Override
            public void onClickItem(ExpereinceNew expereinceNew, int pos) {

                int stepPendingCount = 0;
                for (ProfileStatus profileStatus : expereinceNew.getProgress()) {
                    if (profileStatus.getIsComplete().equalsIgnoreCase("0")) {
                        stepPendingCount = stepPendingCount + 1;
                    }
                }

                if (stepPendingCount == 0) {
                    presenter.openExpDetails(expereinceNew);
                } else {
                    showDialogWithOkButton(getString(R.string.error_message_exp_first), getString(R.string.var_ok), new CallBackDeleteAlert() {
                        @Override
                        public void onSuccess(boolean isSuccess) {
                            presenter.openEditExperience(expereinceNew);
                        }
                    });
                }

            }

            @Override
            public void onClickDelete(ExpereinceNew expereinceNew, int pos) {
                showDialogDeleteWithAnimation(getString(R.string.delete_exp), isSuccess -> {
                    if (isSuccess) {
                        presenter.callWsDeleteExp(pos, expereinceNew);
                    }
                });
            }

            @Override
            public void onClickEdit(ExpereinceNew expereinceNew, int pos) {
                presenter.openEditExperience(expereinceNew);
            }
        });

        presenter.getData(true);

        recyclerExperiences.setNestedScrollingEnabled(false);
        recyclerExperiences.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerExperiences.setAdapter(experiencesAdapter);
    }

    @OnClick({R.id.imageViewBack, R.id.textViewSkip, R.id.linearLayoutAddExp})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.textViewSkip:
                presenter.openAddBank();
                break;
            case R.id.linearLayoutAddExp:
                presenter.openExpAddDetails();
                break;
        }
    }

    @Override
    public void setData(List<ExpereinceNew> expereinceNew) {
        expereinceNews.clear();
        expereinceNews.addAll(expereinceNew);
        if (experiencesAdapter != null) {
            experiencesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setIsLocalFlow(boolean isLocalFlow) {
        this.isLocalFlow = isLocalFlow;
    }

    @Override
    public void deleteExp(int pos, ExpereinceNew expereinceNew) {

        expereinceNews.remove(expereinceNew);
        if (experiencesAdapter != null)
            experiencesAdapter.notifyDataSetChanged();
    }


    @OnClick(R.id.buttonUpdate)
    public void onClick() {
        if (isLocalFlow) {
            presenter.openAddBank();
        } else {
            goBack();
        }
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
        if (action == EventBusAction.LOCALEXPADD) {
            presenter.getData(false);
        }
    }

}
