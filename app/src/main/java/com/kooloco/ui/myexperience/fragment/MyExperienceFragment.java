package com.kooloco.ui.myexperience.fragment;
/**
 * Created by hlink44 on 28/9/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.ExperienceDetails;
import com.kooloco.ui.MainActivity;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.myexperience.adapter.MyExperienceAdapter;
import com.kooloco.ui.myexperience.presenter.MyExperiencePresenter;
import com.kooloco.ui.myexperience.view.MyExperienceView;
import com.kooloco.util.TimeConvertUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class MyExperienceFragment extends BaseFragment<MyExperiencePresenter, MyExperienceView> implements MyExperienceView {

    @BindView(R.id.textViewNoData)
    AppCompatTextView textViewNoData;
    @BindView(R.id.recyclerMyExp)
    RecyclerView recyclerMyExp;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.linearLayoutNoData)
    LinearLayout linearLayoutNoData;
    @BindView(R.id.imageViewCount)
    ImageView imageViewCount;

    private List<ExperienceDetails> data;

    MyExperienceAdapter myExperienceAdapter;


    //Pagination

    LinearLayoutManager mLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    boolean isLoading = false;

    int page = 1;

    @Override
    protected int createLayout() {
        return R.layout.fragment_my_experience;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected MyExperienceView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        setDot(imageViewCount);

        data = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerMyExp.setLayoutManager(mLayoutManager);
        myExperienceAdapter = new MyExperienceAdapter(getActivity(), data, new MyExperienceAdapter.CallBack() {
            @Override
            public void onClickItem(int position, ExperienceDetails experienceDetails) {
                presenter.openMyExperinceDetails(experienceDetails);
            }

            @Override
            public void onClickImage(int position, ExperienceDetails experienceDetails) {
                imageOpenZoom(experienceDetails.getProfileImage());
            }
        });
        recyclerMyExp.setAdapter(myExperienceAdapter);


        presenter.getDataLocal();

        recyclerMyExp.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + 3 + pastVisiblesItems) >= totalItemCount) {
                    if (isLoading) {
                        page = page + 1;
                        isLoading = false;
                        presenter.getData(page, false);
                    }
                }

            }
        });

        page = 1;
        presenter.getData(page, false);


        swipeRefresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary), getActivity().getResources().getColor(R.color.yellow), getActivity().getResources().getColor(R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(() -> {
            if (presenter != null) {
                page = 1;
                presenter.getData(page, false);
            }
        });


    }

    @OnClick({R.id.imageViewNotification, R.id.imageViewFilter, R.id.imageViewBlog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewNotification:
                presenter.openNotification();
                break;
            case R.id.imageViewFilter:
                filterData((isApply, filterRequest) -> {

                }, true, false);
                break;
            case R.id.imageViewBlog:
                presenter.openBlogList();
                break;
        }
    }

    @Override
    public void setData(List<ExperienceDetails> dashData, int page) {
        if (page == 1) {
            data.clear();
        }

        data.addAll(dashData);

        if (swipeRefresh != null) {
            if (swipeRefresh.isRefreshing()) {
                swipeRefresh.setRefreshing(false);
            }
        }

        if (getActivity() != null) {
            if (myExperienceAdapter != null) {
                myExperienceAdapter.notifyDataSetChanged();
            }
        }

        if (linearLayoutNoData != null) {
            linearLayoutNoData.setVisibility((data.size() == 0) ? View.VISIBLE : View.INVISIBLE);
        }

        isLoading = !dashData.isEmpty();
    }

    @OnClick(R.id.buttonOkDone)
    public void onClickDone() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setSelectonTab(0);
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

        if (action == EventBusAction.NOTIFICATIONCOUNTUIVISITOR) {
            setDot(imageViewCount);
        }

    }

}
