package com.kooloco.ui.notification.fragment;
/**
 * Created by hlink44 on 26/9/17.
 */

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.CheckEquipment;
import com.kooloco.model.CheckEquipmentResponse;
import com.kooloco.model.ReceiverData;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.notification.adapter.CheckSportEquipmentsAdapter;
import com.kooloco.ui.notification.presenter.CheckSportEaquipmentsPresenter;
import com.kooloco.ui.notification.view.CheckSportEaquipmentsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

public class CheckSportEaquipmentsFragment extends BaseFragment<CheckSportEaquipmentsPresenter, CheckSportEaquipmentsView> implements CheckSportEaquipmentsView {

    @BindView(R.id.imageViewChat)
    ImageView imageViewChat;
    @BindView(R.id.customTextServiceName)
    AppCompatTextView customTextServiceName;
    @BindView(R.id.customTextCount)
    AppCompatTextView customTextCount;
    @BindView(R.id.checkBoxSelectAll)
    CheckBox checkBoxSelectAll;
    @BindView(R.id.linearLayoutSelectAll)
    LinearLayout linearLayoutSelectAll;
    @BindView(R.id.recyclerSportType)
    RecyclerView recyclerSportType;
    List<CheckEquipment> checkEquipment = new ArrayList<>();
    CheckSportEquipmentsAdapter checkSportEquipmentsAdapter;
    private String orderId = "";
    private ReceiverData receiverData;
    private String orderStatus = "1";
    private String chatStatus = "1";

    @Override
    protected int createLayout() {
        return R.layout.fragment_check_sport_eaquipments;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected CheckSportEaquipmentsView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        recyclerSportType.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        checkEquipment.clear();
        checkSportEquipmentsAdapter = new CheckSportEquipmentsAdapter(getActivity(), checkEquipment, orderStatus, new CheckSportEquipmentsAdapter.CallBack() {
            @Override
            public void onClickSelect(CheckEquipment sport) {
                setSelectioValue(true);
            }
        });
        recyclerSportType.setAdapter(checkSportEquipmentsAdapter);
        setSelectioValue(false);
        presenter.getData(orderId);

        imageViewChat.setClickable(chatStatus.equalsIgnoreCase("1"));
        imageViewChat.setAlpha(chatStatus.equalsIgnoreCase("1") ? 1f : 0.75f);

/*
        checkBoxSelectAll.setClickable(orderStatus.equalsIgnoreCase("1"));
        checkBoxSelectAll.setAlpha(orderStatus.equalsIgnoreCase("1") ? 1f : 0.75f);
*/

        checkBoxSelectAll.setAlpha(orderStatus.equalsIgnoreCase("1") ? 1f : 0.75f);

        linearLayoutSelectAll.setClickable(orderStatus.equalsIgnoreCase("1"));
        linearLayoutSelectAll.setAlpha(orderStatus.equalsIgnoreCase("1") ? 1f : 0.75f);


    }

    @OnClick(R.id.linearLayoutSelectAll)
    public void onViewClicked() {
        checkBoxSelectAll.setChecked(!checkBoxSelectAll.isChecked());
        if (!checkBoxSelectAll.isChecked()) {
            unSelectAll();
            checkSportEquipmentsAdapter.notifyDataSetChanged();
            setSelectioValue(false);
        } else {
            selectAll();
            checkSportEquipmentsAdapter.notifyDataSetChanged();
            setSelectioValue(false);
        }
    }

    @Override
    public void setData(CheckEquipmentResponse data) {
        customTextServiceName.setText(data.getName());
        checkEquipment.clear();


        Observable.fromIterable(data.getEquipments()).sorted((checkEquipment1, t1) -> checkEquipment1.getName().compareToIgnoreCase(t1.getName())).toList().subscribe(equipment -> {
            checkEquipment.addAll(equipment);
        });

        checkSportEquipmentsAdapter.notifyDataSetChanged();
        setSelectioValue(true);
    }

    @Override
    public void setOrderId(String orderId) {
        this.orderId = orderId;

    }

    @Override
    public void setRecevierData(ReceiverData receiverData) {
        this.receiverData = receiverData;
    }

    @Override
    public void setChatStatus(String chatStatus) {
        this.chatStatus = chatStatus;
    }

    @Override
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    private void setSelectioValue(boolean isClick) {
        int i = 0;

        for (CheckEquipment sport : checkSportEquipmentsAdapter.getData()) {
            if (sport.isSelect()) {
                i = i + 1;
            }
        }
        customTextCount.setText("(" + i + " " + getActivity().getResources().getString(R.string.check_sport_equipments_selected) + ")");

        if (isClick) {
            if (i == checkSportEquipmentsAdapter.getData().size()) {
                checkBoxSelectAll.setChecked(true);
            } else {
                checkBoxSelectAll.setChecked(false);
            }
        }

    }

    private List<CheckEquipment> unSelectAll() {
        List<CheckEquipment> sports1 = new ArrayList<>();
        for (CheckEquipment sport : checkEquipment) {
            sport.setSelect(false);
            sports1.add(sport);
        }
        return sports1;
    }

    private List<CheckEquipment> selectAll() {
        List<CheckEquipment> sports1 = new ArrayList<>();
        for (CheckEquipment sport : checkEquipment) {
            sport.setSelect(true);
            sports1.add(sport);
        }
        return sports1;
    }


    private boolean checkSelect() {
        boolean isError = true;
        for (CheckEquipment sport : checkEquipment) {
            if (sport.isSelect()) {
                isError = false;
                break;
            }
        }
        return isError;
    }

    @OnClick({R.id.imageViewBack, R.id.textViewSkip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.textViewSkip:
                goBack();
                break;
        }
    }


    @OnClick(R.id.imageViewChat)
    public void onViewClickedChat() {
        presenter.opnChat(receiverData);
    }


    @OnClick(R.id.buttonSubmit)
    public void onViewClickedSubmit() {
        if (orderStatus.equalsIgnoreCase("1")) {
            presenter.callWsSetEquipment(checkEquipment, orderId);
        } else {
            goBack();
        }
    }
}
