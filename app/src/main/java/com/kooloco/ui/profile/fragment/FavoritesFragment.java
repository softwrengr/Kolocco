package com.kooloco.ui.profile.fragment;
/**
 * Created by hlink44 on 26/9/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.profile.adapter.FavoritesAdapter;
import com.kooloco.ui.profile.presenter.FavoritesPresenter;
import com.kooloco.ui.profile.view.FavoritesView;
import com.kooloco.ui.visitor.home.adapter.HomeLocalAndExpExperienceAdapter;
import com.kooloco.ui.visitor.home.adapter.HomeLocalAndExpExperienceWithScrollAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class FavoritesFragment extends BaseFragment<FavoritesPresenter, FavoritesView> implements FavoritesView {

    @BindView(R.id.recyclerFav)
    RecyclerView recyclerFav;

    List<ExpereinceNew> favorites;
    FavoritesAdapter favoritesAdapter;
    HomeLocalAndExpExperienceWithScrollAdapter homeExperienceAdapter;


    int page = 1;

    LinearLayoutManager linearLayoutManager;

    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean isLoading = false;
    @BindView(R.id.textViewNoData)
    AppCompatTextView textViewNoData;

    @Override
    protected int createLayout() {
        return R.layout.fragment_fav;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected FavoritesView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        favorites = new ArrayList<>();

        homeExperienceAdapter = new HomeLocalAndExpExperienceWithScrollAdapter(getActivity(), favorites, new HomeLocalAndExpExperienceWithScrollAdapter.CallBack() {
            @Override
            public void onClickItem(ExpereinceNew expereinceNew, int pos) {
                presenter.openExpDetails(expereinceNew);

            }

            @Override
            public void onClickFav(ExpereinceNew expereinceNew, int pos) {

                setFavExp(expereinceNew.getId(), status -> {
                    favorites.remove(pos);
                    homeExperienceAdapter.notifyItemRemoved(pos);
                });
            }
        });

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerFav.setLayoutManager(linearLayoutManager);
        recyclerFav.setAdapter(homeExperienceAdapter);

        presenter.getData(page);


        recyclerFav.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                    if (isLoading) {
                        page = page + 1;
                        isLoading = false;
                        //  presenter.getData(page);
                    }
                }

            }
        });


    }


    @OnClick({R.id.imageViewBack, R.id.imageSearch, R.id.imageViewFilter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.imageSearch:
                break;
            case R.id.imageViewFilter:
                filterData((isApply, filterRequest) -> {

                }, true, false);
                break;
        }
    }

    @Override
    public void setData(List<ExpereinceNew> data, int page) {
        if (page == 1) {
            favorites.clear();
        }
        favorites.addAll(data);

        isLoading = !data.isEmpty();

        if (textViewNoData != null) {
            textViewNoData.setVisibility((favorites.size() == 0) ? View.VISIBLE : View.GONE);
        }

        if (homeExperienceAdapter != null) {
            homeExperienceAdapter.notifyDataSetChanged();
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
        if (action == EventBusAction.FAVLISTUPDATE) {
            page = 1;
            presenter.getDataHide(page);
        }
    }
}
