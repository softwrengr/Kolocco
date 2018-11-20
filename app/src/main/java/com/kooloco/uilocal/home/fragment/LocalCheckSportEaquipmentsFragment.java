package com.kooloco.uilocal.home.fragment;
/**
 * Created by hlink44 on 26/9/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.CheckEquipment;
import com.kooloco.model.CheckEquipmentResponse;
import com.kooloco.model.Equipment;
import com.kooloco.model.ReceiverData;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.addservice.adapter.CheckSportEquipmentsSubAdapter;
import com.kooloco.uilocal.home.adapter.LocalCheckSportEquipmentsAdapter;
import com.kooloco.uilocal.home.presenter.LocalCheckSportEaquipmentsPresenter;
import com.kooloco.uilocal.home.view.LocalCheckSportEaquipmentsView;
import com.kooloco.util.FirstCapEditText;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

public class LocalCheckSportEaquipmentsFragment extends BaseFragment<LocalCheckSportEaquipmentsPresenter, LocalCheckSportEaquipmentsView> implements LocalCheckSportEaquipmentsView {

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
    LocalCheckSportEquipmentsAdapter checkSportEquipmentsAdapter;
    @BindView(R.id.editTextEquipmentName)
    FirstCapEditText editTextEquipmentName;
    @BindView(R.id.buttonAdd)
    AppCompatButton buttonAdd;
    private String orderId = "";
    private ReceiverData receiverData;
    private String orderStatus = "1";
    private String chatStatus = "1";

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_check_sport_eaquipments;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected LocalCheckSportEaquipmentsView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        recyclerSportType.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        checkEquipment.clear();
        checkSportEquipmentsAdapter = new LocalCheckSportEquipmentsAdapter(getActivity(), checkEquipment, orderStatus, (sport, pos) -> {
            presenter.deleteEquipment(sport, pos);
        });
        recyclerSportType.setAdapter(checkSportEquipmentsAdapter);
        // setSelectioValue(false);
        presenter.getData(orderId);

        imageViewChat.setClickable(chatStatus.equalsIgnoreCase("1"));
        imageViewChat.setAlpha(chatStatus.equalsIgnoreCase("1") ? 1f : 0.75f);

        editTextEquipmentName.setClickable(orderStatus.equalsIgnoreCase("1"));
        editTextEquipmentName.setEnabled(orderStatus.equalsIgnoreCase("1"));

        buttonAdd.setClickable(orderStatus.equalsIgnoreCase("1"));
        buttonAdd.setAlpha(orderStatus.equalsIgnoreCase("1") ? 1f : 0.75f);

    }

    @OnClick(R.id.linearLayoutSelectAll)
    public void onViewClicked() {
        checkBoxSelectAll.setChecked(!checkBoxSelectAll.isChecked());
        if (!checkBoxSelectAll.isChecked()) {
            //  unSelectAll();
            checkSportEquipmentsAdapter.notifyDataSetChanged();
            // setSelectioValue(false);
        } else {
            //  selectAll();
            checkSportEquipmentsAdapter.notifyDataSetChanged();
            // setSelectioValue(false);
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
        //setSelectioValue(true);
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
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public void setChatStatus(String chatStatus) {
        this.chatStatus = chatStatus;

    }

    @Override
    public void deleteEquipmentData(int pos) {
        if (checkEquipment != null) {
            checkEquipment.remove(pos);
        }

        if (checkSportEquipmentsAdapter != null) {
            checkSportEquipmentsAdapter.notifyDataSetChanged();
        }
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

    @OnClick({R.id.imageViewBack, R.id.textViewSkip, R.id.buttonAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.textViewSkip:
                goBack();
                break;
            case R.id.buttonAdd:
                if (editTextEquipmentName.getText().toString().isEmpty()) {
                    showMessage(getString(R.string.val_add_other_equipment));
                    break;
                }
                presenter.addEquipments(orderId, editTextEquipmentName.getText().toString().trim(), new CallBackDialog() {
                    @Override
                    public void onSuccess(CheckEquipment data) {
                        editTextEquipmentName.setText("");

                        checkEquipment.add(data);


                        Observable.fromIterable(checkEquipment).sorted((checkEquipment1, t1) -> checkEquipment1.getName().compareToIgnoreCase(t1.getName())).toList().subscribe(equipment -> {
                            checkEquipment.clear();
                            checkEquipment.addAll(equipment);
                        });

                        if (checkSportEquipmentsAdapter != null) {
                            checkSportEquipmentsAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFail(String message) {
                        showMessage(message);
                    }
                });
                break;
        }
    }


    @OnClick(R.id.imageViewChat)
    public void onViewClickedChat() {
        presenter.opnChat(receiverData);
    }


    @OnClick(R.id.buttonSubmit)
    public void onViewClickedSubmit() {
       /* if (checkSelect()) {
            showMessage(getString(R.string.val_select_error_message));
        }
        presenter.callWsSetEquipment(checkEquipment, orderId);*/
        goBack();
    }

    public interface CallBackDialog {
        void onSuccess(CheckEquipment data);

        void onFail(String message);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onDestroyView();

    }

}
