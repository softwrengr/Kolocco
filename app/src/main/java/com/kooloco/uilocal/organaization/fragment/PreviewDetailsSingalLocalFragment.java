package com.kooloco.uilocal.organaization.fragment;
/**
 * Created by hlink44 on 16/10/17.
 */

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.OrgLocal;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.organizationDetails.adapter.LocalAdapter;
import com.kooloco.ui.visitor.organizationDetails.adapter.OrgImageAdapter;
import com.kooloco.uilocal.organaization.presenter.PreviewDetailsSingalLocalPresenter;
import com.kooloco.uilocal.organaization.view.PreviewDetailsSingalLocalView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PreviewDetailsSingalLocalFragment extends BaseFragment<PreviewDetailsSingalLocalPresenter, PreviewDetailsSingalLocalView> implements PreviewDetailsSingalLocalView {

    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewName)
    AppCompatTextView customTextViewName;
    @BindView(R.id.customTextViewActivityTypes)
    AppCompatTextView customTextViewActivityTypes;
    @BindView(R.id.customTextViewSportTypes)
    AppCompatTextView customTextViewSportTypes;
    @BindView(R.id.customTextViewWebSite)
    AppCompatTextView customTextViewWebSite;
    @BindView(R.id.customTextViewLocation)
    AppCompatTextView customTextViewLocation;
    @BindView(R.id.textViewLocalValue)
    AppCompatTextView textViewLocalValue;
    @BindView(R.id.recyclerViewLocal)
    RecyclerView recyclerViewLocal;
    @BindView(R.id.recyclerViewImage)
    RecyclerView recyclerViewImage;
    List<OrgLocal> orgLocals = new ArrayList<>();
    LocalAdapter localDeleteAdapter;

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_prview_details_local;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected PreviewDetailsSingalLocalView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        presenter.getData();
    }

    @Override
    public void setData(OrganizationDetails data) {
        //Set profile data
        Picasso.with(getActivity()).load(data.getImageUrl()).into(imageViewProfile);
        customTextViewName.setText(data.getOrgName());
        customTextViewActivityTypes.setText(data.getOrgActivityTypes());
        customTextViewSportTypes.setText(data.getOrgSportTypes());
        customTextViewLocation.setText(data.getOrgLocation());


        orgLocals.clear();
        orgLocals.addAll(data.getOrgLocals());
        //Set local data
        textViewLocalValue.setText("(" + orgLocals.size() + ")");
        recyclerViewLocal.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        localDeleteAdapter = new LocalAdapter(getActivity(), orgLocals);
        recyclerViewLocal.setAdapter(localDeleteAdapter);

        //Set organization image data
        recyclerViewImage.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewImage.setAdapter(new OrgImageAdapter(getActivity(), data.getOrgImage()));
    }


    @OnClick({R.id.imageViewBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
        }
    }
}
