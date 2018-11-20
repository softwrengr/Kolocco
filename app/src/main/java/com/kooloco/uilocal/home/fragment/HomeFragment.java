package com.kooloco.uilocal.home.fragment;
/**
 * Created by hlink44 on 5/10/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Filter;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.home.adapter.HomePagerAdapter;
import com.kooloco.uilocal.home.presenter.HomePresenter;
import com.kooloco.uilocal.home.view.HomeView;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class HomeFragment extends BaseFragment<HomePresenter, HomeView> implements HomeView {
    @BindView(R.id.customTextViewPendingOrder)
    AppCompatTextView customTextViewPendingOrder;
    @BindView(R.id.imageViewSelectionPending)
    ImageView imageViewSelectionPending;
    @BindView(R.id.customTextViewAcceptOrder)
    AppCompatTextView customTextViewAcceptOrder;
    @BindView(R.id.imageViewSelectionAcceptOrder)
    ImageView imageViewSelectionAcceptOrder;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.imageViewCount)
    ImageView imageViewCount;
    @BindView(R.id.imageViewSearch)
    ImageView imageViewSearch;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    SearchView searchView;
    HomePagerAdapter homePagerAdapter;

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_home;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected HomeView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        imageViewSearch.setVisibility(View.INVISIBLE);

        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        setDot(imageViewCount);

        setSelection(0);
        homePagerAdapter = new HomePagerAdapter(getChildFragmentManager());

        viewPager.setAdapter(homePagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.customTextViewPendingOrder, R.id.customTextViewAcceptOrder, R.id.linearLayoutPending, R.id.linearLayoutAccept})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.customTextViewPendingOrder:
            case R.id.linearLayoutPending:
                setSelection(0);
                viewPager.setCurrentItem(0);
                break;
            case R.id.customTextViewAcceptOrder:
            case R.id.linearLayoutAccept:
                setSelection(1);
                viewPager.setCurrentItem(1);
                break;
        }
    }

    private void setSelection(int position) {
        customTextViewPendingOrder.setSelected(position == 0);
        customTextViewAcceptOrder.setSelected(position == 1);

        imageViewSelectionPending.setVisibility((position == 0) ? View.VISIBLE : View.INVISIBLE);
        imageViewSelectionAcceptOrder.setVisibility((position == 1) ? View.VISIBLE : View.INVISIBLE);
    }


    @OnClick(R.id.imageViewNotification)
    public void onViewClicked() {
        presenter.openNotification();
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

        if (action == EventBusAction.NOTIFICATIONCOUNTUILOCAL) {
            setDot(imageViewCount);
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.search, menu);

        MenuItem search = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(search);

        search(searchView);
        homePagerAdapter.setSearchView(searchView);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                if (homePagerAdapter != null) {
                    homePagerAdapter.setSerachData(newText);
                }

                return true;
            }
        });
    }

}
