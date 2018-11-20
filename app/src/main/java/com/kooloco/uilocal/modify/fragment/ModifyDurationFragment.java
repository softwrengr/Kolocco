package com.kooloco.uilocal.modify.fragment;
/**
 * Created by hlink on 20/1/18.
 */

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Order;
import com.kooloco.model.ReceiverData;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.home.adapter.DurationStringAdapter;
import com.kooloco.uilocal.modify.presenter.ModifyDurationPresenter;
import com.kooloco.uilocal.modify.view.ModifyDurationView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class ModifyDurationFragment extends BaseFragment<ModifyDurationPresenter, ModifyDurationView> implements ModifyDurationView {
    boolean isLocation;
    @BindView(R.id.recyclerDuration)
    RecyclerView recyclerDuration;
    private Order order;
    String duration = "";

    @Override
    protected int createLayout() {
        return R.layout.fragment_modify_duration;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ModifyDurationView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        presenter.getData(order.getId());
    }

    @Override
    public void setIsLocation(boolean isLocation) {
        this.isLocation = isLocation;
    }

    @Override
    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public void setData(List<String> data) {
        if (!data.isEmpty()) {
            duration = data.get(0);
        }
        recyclerDuration.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerDuration.setAdapter(new DurationStringAdapter(getActivity(), data, new DurationStringAdapter.CallBack() {
            @Override
            public void onClickItem(String time) {
                duration = time;
            }
        }));
    }

    @OnClick({R.id.imageViewBack, R.id.buttonSetDuration})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.buttonSetDuration:
                presenter.callWs(isLocation, order,duration);
                break;
        }
    }

    @OnClick(R.id.imageChat)
    public void onClickChat() {
        ReceiverData receiverData = new ReceiverData();
        receiverData.setUser_id(order.getUserId());
        receiverData.setName(order.getFirstname() + " " + order.getLastname());
        receiverData.setImageUrl(order.getProfileImage());
        receiverData.setDeviceType("A");
        receiverData.setDeviceToken("123");
        receiverData.setOrderId(order.getId());

        presenter.openChat(receiverData);
    }
}
