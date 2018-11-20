package com.kooloco.uilocal.addservice.fragment;
/**
 * Created by hlink44 on 4/10/17.
 */

import android.app.Dialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kooloco.R;
import com.kooloco.constant.Step;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Equipment;
import com.kooloco.model.EquipmentResponse;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.addservice.adapter.CheckSportEquipmentsAdapter;
import com.kooloco.uilocal.addservice.presenter.ChooseSportEquipmentsPresenter;
import com.kooloco.uilocal.addservice.view.ChooseSportEquipmentsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseSportEquipmentsFragment extends BaseFragment<ChooseSportEquipmentsPresenter, ChooseSportEquipmentsView> implements ChooseSportEquipmentsView {

    @BindView(R.id.recyclerChooseEquipments)
    RecyclerView recyclerChooseEquipments;

    List<EquipmentResponse> equipmentResponses;

    CheckSportEquipmentsAdapter checkSportEquipmentsAdapter;
    Dialog dialogAddEquipmentsRequest;

    List<Equipment> equipmentSelect;
    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.textViewSkip)
    AppCompatTextView textViewSkip;
    @BindView(R.id.buttonSubmit)
    AppCompatButton buttonSubmit;

    private boolean isEdit = false;

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_choose_sport_equipments;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ChooseSportEquipmentsView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        if (equipmentResponses == null) {
            equipmentResponses = new ArrayList<>();
            equipmentSelect = new ArrayList<>();
            presenter.getEquipments();
        }

        if (isEdit) {
            textViewSkip.setVisibility(View.INVISIBLE);
            buttonSubmit.setText(getActivity().getResources().getString(R.string.button_update));
            toolbarTitle.setText(getActivity().getResources().getString(R.string.toolbar_check_sport_eaquipments));
        } else {
            textViewSkip.setVisibility(View.VISIBLE);
            buttonSubmit.setText(getActivity().getResources().getString(R.string.button_submit));
            toolbarTitle.setText(getActivity().getResources().getString(R.string.toolbar_check_sport_eaquipments));
        }


        checkSportEquipmentsAdapter = new CheckSportEquipmentsAdapter(getActivity(), equipmentResponses, new CheckSportEquipmentsAdapter.CallBack() {
            @Override
            public void onClickAdd(EquipmentResponse equipmentResponse, String name) {

                presenter.addEquipments(equipmentResponse.getId(), name, new CallBackDialog() {
                    @Override
                    public void onSuccess(Equipment data) {
                        presenter.getEquipments();
                    }

                    @Override
                    public void onFail(String message) {

                    }
                });

            }

            @Override
            public void onShowErrorMessage(String message) {
                showMessage(message);
            }

            @Override
            public void onSelect(Equipment equipment) {
                presenter.callWs(equipment.getId(), (equipment.getIsSelected().equalsIgnoreCase("1") ? "0" : "1"));
            }

            @Override
            public void onDelete(Equipment equipment) {
                Log.d("::::", equipment.getId());
                presenter.deleteEquiqment(equipment.getId());
            }
        });

        recyclerChooseEquipments.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerChooseEquipments.setAdapter(checkSportEquipmentsAdapter);

    }

    @OnClick({R.id.imageViewBack, R.id.textViewSkip, R.id.buttonSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.textViewSkip:
                skipSignUpStep(Step.step9, new CallBackSignupStep() {
                    @Override
                    public void onSuccess() {
                        presenter.openSetSchdule();
                    }
                });
                break;
            case R.id.buttonSubmit:
                if (isEdit) {
                    skipSignUpStep(Step.step9, new CallBackSignupStep() {
                        @Override
                        public void onSuccess() {
                            goBack();
                        }
                    });
                } else {
                    skipSignUpStep(Step.step9, new CallBackSignupStep() {
                        @Override
                        public void onSuccess() {
                            presenter.openSetSchdule();
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void setData(EquipmentResponse data) {
        equipmentResponses.clear();
        equipmentResponses.add(data);
        if (checkSportEquipmentsAdapter != null) {
            checkSportEquipmentsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public void showAddEquipments(final EquipmentResponse equipmentResponse) {

        if (dialogAddEquipmentsRequest != null) {
            if (dialogAddEquipmentsRequest.isShowing()) {
                dialogAddEquipmentsRequest.dismiss();
            }
        }

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_equipments, null, false);

        final AppCompatEditText editTextEquipment = view.findViewById(R.id.editTextEquipment);

        AppCompatButton buttonOk = view.findViewById(R.id.buttonOk);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextEquipment.getText().toString().isEmpty()) {
                    showSnackBar(editTextEquipment, getActivity().getResources().getString(R.string.val_add_other_equipment));
                } else {

                    presenter.addEquipments(equipmentResponse.getId(), editTextEquipment.getText().toString().trim(), new CallBackDialog() {
                        @Override
                        public void onSuccess(Equipment data) {
                            dialogAddEquipmentsRequest.dismiss();
                            presenter.getEquipments();
                        }

                        @Override
                        public void onFail(String message) {
                            showSnackBar(editTextEquipment, message);
                        }
                    });

                }
            }
        });

        dialogAddEquipmentsRequest = new Dialog(getActivity());

        dialogAddEquipmentsRequest.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogAddEquipmentsRequest.setCancelable(true);

        dialogAddEquipmentsRequest.getWindow().setBackgroundDrawableResource(R.drawable.drwable_background_tra);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        dialogAddEquipmentsRequest.setContentView(view);

        dialogAddEquipmentsRequest.show();
    }

    public interface CallBackDialog {
        void onSuccess(Equipment data);

        void onFail(String message);
    }
}
