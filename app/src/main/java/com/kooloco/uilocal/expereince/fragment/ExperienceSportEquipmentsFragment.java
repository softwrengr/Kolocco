package com.kooloco.uilocal.expereince.fragment;
/**
 * Created by hlink on 17/4/18.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Equipment;
import com.kooloco.model.EquipmentResponse;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.addservice.adapter.CheckSportEquipmentsAdapter;
import com.kooloco.uilocal.addservice.fragment.ChooseSportEquipmentsFragment;
import com.kooloco.uilocal.expereince.presenter.ExperienceSportEquipmentsPresenter;
import com.kooloco.uilocal.expereince.view.ExperienceSportEquipmentsView;
import com.kooloco.util.CustomLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class ExperienceSportEquipmentsFragment extends BaseFragment<ExperienceSportEquipmentsPresenter, ExperienceSportEquipmentsView> implements ExperienceSportEquipmentsView {


    @BindView(R.id.recyclerChooseEquipments)
    RecyclerView recyclerChooseEquipments;

    List<EquipmentResponse> equipmentResponses;

    CheckSportEquipmentsAdapter checkSportEquipmentsAdapter;

    List<Equipment> equipmentSelect;
    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.buttonSubmit)
    AppCompatButton buttonSubmit;
    private String expId = "";


    @Override
    protected int createLayout() {
        return R.layout.exp_local_fragment_sport_equipments;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ExperienceSportEquipmentsView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        if (equipmentResponses == null) {
            equipmentResponses = new ArrayList<>();
            equipmentSelect = new ArrayList<>();
        }

        presenter.getEquipments(expId);

        checkSportEquipmentsAdapter = new CheckSportEquipmentsAdapter(getActivity(), equipmentResponses, new CheckSportEquipmentsAdapter.CallBack() {
            @Override
            public void onClickAdd(EquipmentResponse equipmentResponse, String name) {

                presenter.addEquipments(expId, name, new CallBackDialog() {
                    @Override
                    public void onSuccess(Equipment data) {
                        equipmentResponse.getEquipments().add(data);

                        if (checkSportEquipmentsAdapter != null) {
                            checkSportEquipmentsAdapter.notifyDataSetChanged();
                        }

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

            }

            @Override
            public void onDelete(Equipment equipment) {
                Log.d("::::", equipment.getId());
                presenter.deleteEquiqment(equipment.getId(), expId);
            }
        });

        recyclerChooseEquipments.setLayoutManager(new CustomLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerChooseEquipments.setAdapter(checkSportEquipmentsAdapter);

    }


    @OnClick({R.id.imageViewBack, R.id.buttonSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.buttonSubmit:
                presenter.callWs(equipmentResponses, expId);
                break;
        }
    }

    @Override
    public void setExpId(String expId) {
        this.expId = expId;
    }

    @Override
    public void setData(EquipmentResponse data) {
        equipmentResponses.clear();
        equipmentResponses.add(data);
        if (checkSportEquipmentsAdapter != null) {
            checkSportEquipmentsAdapter.notifyDataSetChanged();
        }
    }

    public interface CallBackDialog {
        void onSuccess(Equipment data);

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
