package com.kooloco.ui.profile.fragment;
/**
 * Created by hlink44 on 25/9/17.
 */

import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.profile.adapter.OrderPagerAdapter;
import com.kooloco.ui.profile.presenter.OrderHIstoryPresenter;
import com.kooloco.ui.profile.view.OrderHIstoryView;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderHIstoryFragment extends BaseFragment<OrderHIstoryPresenter, OrderHIstoryView> implements OrderHIstoryView {

    @BindView(R.id.customTextViewPendingOrder)
    AppCompatTextView customTextViewPendingOrder;
    @BindView(R.id.imageViewSelectionPending)
    ImageView imageViewSelectionPending;
    @BindView(R.id.customTextViewComplateOrder)
    AppCompatTextView customTextViewComplateOrder;
    @BindView(R.id.imageViewSelectionComplate)
    ImageView imageViewSelectionComplate;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int createLayout() {
        return R.layout.fragment_order_history;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected OrderHIstoryView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        setSelection(0);
        viewPager.setAdapter(new OrderPagerAdapter(getChildFragmentManager()));
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

    @OnClick({R.id.customTextViewPendingOrder, R.id.customTextViewComplateOrder, R.id.linearLayoutComplate, R.id.linearLayoutPending})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.customTextViewPendingOrder:
            case R.id.linearLayoutPending:
                setSelection(0);
                viewPager.setCurrentItem(0);
                break;
            case R.id.customTextViewComplateOrder:
            case R.id.linearLayoutComplate:
                setSelection(1);
                viewPager.setCurrentItem(1);
                break;
        }
    }

    private void setSelection(int position) {
        customTextViewPendingOrder.setSelected(position == 0);
        customTextViewComplateOrder.setSelected(position == 1);

        imageViewSelectionPending.setVisibility((position == 0) ? View.VISIBLE : View.INVISIBLE);
        imageViewSelectionComplate.setVisibility((position == 1) ? View.VISIBLE : View.INVISIBLE);
    }

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        goBack();
    }
}
