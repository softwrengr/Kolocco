package com.kooloco.uilocal.organaization.fragment;
/**
 * Created by hlink on 1/2/18.
 */

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.OrgLocal;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.organaization.adapter.OrgAssignLocalListAdapter;
import com.kooloco.uilocal.organaization.presenter.AddLocalPresenter;
import com.kooloco.uilocal.organaization.view.AddLocalView;
import com.kooloco.util.LocationManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class AddLocalFragment extends BaseFragment<AddLocalPresenter, AddLocalView> implements AddLocalView {

    @BindView(R.id.customTextSearchLocation)
    AppCompatEditText customTextSearchLocation;
    @BindView(R.id.recyclerLocalList)
    RecyclerView recyclerLocalList;
    List<OrgLocal> orgLocals, orgLocalsTemp;
    OrgAssignLocalListAdapter orgAssignLocalListAdapter;
    @BindView(R.id.buttonContinue)
    AppCompatButton buttonContinue;
    @BindView(R.id.textViewNoLocalFound)
    AppCompatTextView textViewNoLocalFound;

    private OrganizationDetails organizationDetails;
    private boolean isCreateOrg = false;

    @Override
    protected int createLayout() {
        return R.layout.fragment_add_local;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected AddLocalView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        ((BaseActivity) getActivity()).getLocationManager().triggerLocation(true, new LocationManager.LocationListener() {
            @Override
            public void onLocationAvailable(LatLng latLng) {
                BaseFragment.latitude = latLng.latitude;
                BaseFragment.longitude = latLng.longitude;

            }

            @Override
            public void onFail(Status status) {
                Log.e("Err", status.toString());
            }
        });

        buttonContinue.setText(isCreateOrg ? getActivity().getString(R.string.button_next) : getActivity().getString(R.string.button_done));
        if (orgLocals == null) {
            orgLocals = new ArrayList<>();
            orgLocalsTemp = new ArrayList<>();


            if (presenter != null) {
                presenter.getlocal(organizationDetails.getId());
            }
        }

        orgAssignLocalListAdapter = new OrgAssignLocalListAdapter(getActivity(), orgLocals, new OrgAssignLocalListAdapter.CallBack() {
            @Override
            public void onDelete(int position, OrgLocal orgLocal) {
                showDialogDeleteWithAnimation(getString(R.string.affilated_local_delete),getActivity().getResources().getString(R.string.delete_local_message), isSuccess -> {
                    presenter.deleteLocalOrg(position, organizationDetails, orgLocal);
                });
            }

            @Override
            public void onClickItem(int position, OrgLocal orgLocal) {

                presenter.openDashboardDetails(orgLocal, true);

            }
        });

        recyclerLocalList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        recyclerLocalList.setAdapter(orgAssignLocalListAdapter);

        customTextSearchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                presenter.appplyFilter(orgLocalsTemp, charSequence.toString()).subscribe(orgLocals1 -> {
                    orgLocals.clear();
                    orgLocals.addAll(orgLocals1);
                    if (orgAssignLocalListAdapter != null) {
                        orgAssignLocalListAdapter.notifyDataSetChanged();
                    }
                    if (textViewNoLocalFound != null) {
                        textViewNoLocalFound.setVisibility((orgLocals.size() == 0) ? View.VISIBLE : View.GONE);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    @OnClick({R.id.imageViewBack, R.id.buttonContinue})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.buttonContinue:
                if (isSelectLocal()) {
                    showMessage(getString(R.string.val_select_local));
                    return;
                }
                presenter.openSetPriceRules(organizationDetails, orgLocals, isCreateOrg);
                break;
        }
    }

    @Override
    public void setData(List<OrgLocal> data) {
        orgLocals.clear();
        orgLocalsTemp.clear();

        orgLocals.addAll(data);
        orgLocalsTemp.addAll(data);

        if (orgAssignLocalListAdapter != null) {
            orgAssignLocalListAdapter.notifyDataSetChanged();
        }

        if (textViewNoLocalFound != null) {
            textViewNoLocalFound.setVisibility((orgLocals.size() == 0) ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void setOrderDetails(OrganizationDetails organizationDetails) {
        this.organizationDetails = organizationDetails;
    }

    @Override
    public void setOrgLocal(List<OrgLocal> orgLocals) {
        this.orgLocals = new ArrayList<>();
        this.orgLocalsTemp = new ArrayList<>();

        this.orgLocals.addAll(orgLocals);
        this.orgLocalsTemp.addAll(orgLocals);
    }

    @Override
    public void setIsCreateOrg(boolean isCreateOrg) {
        this.isCreateOrg = isCreateOrg;
    }

    @Override
    public void setModifyData(int position, OrgLocal orgLocal) {
        orgLocals.set(position, orgLocal);
        if (orgAssignLocalListAdapter != null) {
            orgAssignLocalListAdapter.notifyItemChanged(position);
        }
    }

    private boolean isSelectLocal() {
        boolean isSelect = true;

        for (OrgLocal orgLocal :
                orgLocalsTemp) {

            if (orgLocal.isSelect()) {
                return false;
            }
        }
        return isSelect;

    }

}
