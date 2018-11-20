package com.kooloco.ui.onboarding.fragment;
/**
 * Created by hlink on 18/5/18.
 */

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Service;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.onboarding.adapter.OnBoardingServiceAdapter;
import com.kooloco.ui.onboarding.presenter.OnBoardingPresenter;
import com.kooloco.ui.onboarding.view.OnBoardingView;
import com.kooloco.util.StartSnapHelper;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by hlink on 8/1/18.
 */

public class OnBoardingFragment extends BaseFragment<OnBoardingPresenter, OnBoardingView> implements OnBoardingView {

    @BindView(R.id.recyclerViewService)
    RecyclerView recyclerViewService;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;
    @BindView(R.id.linearLayoutButton)
    LinearLayout linearLayoutButton;
    OnBoardingServiceAdapter onBoardingServiceAdapter;
    LinearLayoutManager linearLayoutManager;

    List<Service> services = new ArrayList<>();

    Map<String, String> selectAnswer;
    int selectPosition = 0;

    @Override
    protected int createLayout() {
        return R.layout.onboarding_fragment;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected OnBoardingView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        selectAnswer = new HashMap<>();

        if (services == null) {
            services = new ArrayList<>();
        }

        if (services.isEmpty()) {
            presenter.getData();
        } else {

            services = presenter.mapStaticData(services);

            setData(services);
        }
    }

    @OnClick({R.id.buttonNo, R.id.buttonYes,R.id.textViewSkipIntro})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonNo:
                putAnswer("0");
                break;
            case R.id.buttonYes:
                putAnswer("1");
                break;
            case R.id.textViewSkipIntro:
                presenter.introSkip(selectAnswer);
                break;
        }
    }

    @Override
    public void setData(List<Service> localServices) {
        pageIndicatorView.setCount(localServices.size());
        pageIndicatorView.setAnimationType(AnimationType.WORM);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerViewService.setLayoutManager(linearLayoutManager);

        onBoardingServiceAdapter = new OnBoardingServiceAdapter(getActivity(), localServices);

        recyclerViewService.setAdapter(onBoardingServiceAdapter);

        recyclerViewService.setOnFlingListener(null);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewService);


        recyclerViewService.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                selectPosition = firstVisibleItemPosition;
                pageIndicatorView.setSelection(firstVisibleItemPosition);
            }
        });
    }

    @Override
    public void data(List<Service> localServices) {
        services.clear();
        services.addAll(localServices);
    }

    private void putAnswer(String answer) {
        boolean isRedirect = false;
        if (selectPosition == (services.size() - 1)) {
            isRedirect = true;
        }
        selectAnswer.put(services.get(selectPosition).getId(), answer);
        if (isRedirect) {
            presenter.callWs(selectAnswer);
        } else {
            recyclerViewService.scrollToPosition(selectPosition + 1);
        }
    }
}
