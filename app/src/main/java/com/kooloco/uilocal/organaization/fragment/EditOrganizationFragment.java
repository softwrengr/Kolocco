package com.kooloco.uilocal.organaization.fragment;
/**
 * Created by hlink44 on 16/10/17.
 */

import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.exception.ApplicationException;
import com.kooloco.model.Activities;
import com.kooloco.model.AddImages;
import com.kooloco.model.FilterGetData;
import com.kooloco.model.MultiFile;
import com.kooloco.model.OrgImage;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.Response;
import com.kooloco.model.Service;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.addservice.adapter.AddImagesAdapter;
import com.kooloco.uilocal.organaization.adapter.OrgActivityAdapter;
import com.kooloco.uilocal.organaization.adapter.OrgServiceAdapter;
import com.kooloco.uilocal.organaization.presenter.EditOrganizationPresenter;
import com.kooloco.uilocal.organaization.view.EditOrganizationView;
import com.kooloco.util.FirstCapEditText;
import com.kooloco.util.ImagePicker;
import com.kooloco.util.Validator;
import com.kooloco.util.picaso.Rounded;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class EditOrganizationFragment extends BaseFragment<EditOrganizationPresenter, EditOrganizationView> implements EditOrganizationView {
    List<AddImages> addImages;
    AddImagesAdapter addImagesAdapter;
    String selectImage = "";
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    List<Activities> activities;
    List<Service> sports;

    @Inject
    Validator validator;

    OrganizationDetails organizationDetails;

    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imageViewBusiness)
    ImageView imageViewBusiness;
    @BindView(R.id.customTextOrgName)
    AppCompatEditText customTextOrgName;
    @BindView(R.id.customTextViewActivitiesType)
    AppCompatEditText customTextViewActivitiesType;
    @BindView(R.id.recyclerActivitiesType)
    RecyclerView recyclerActivitiesType;
    @BindView(R.id.linearLayoutActivitiesType)
    LinearLayout linearLayoutActivitiesType;
    @BindView(R.id.customTextViewSport)
    AppCompatEditText customTextViewSport;
    @BindView(R.id.recyclerSportType)
    RecyclerView recyclerSportType;
    @BindView(R.id.linearLayoutSport)
    LinearLayout linearLayoutSport;
    @BindView(R.id.customTextLocation)
    AppCompatEditText customTextLocation;
    @BindView(R.id.customTextWebSiteUrl)
    AppCompatEditText customTextWebSiteUrl;
    @BindView(R.id.imageViewSlide)
    ImageView imageViewSlide;
    @BindView(R.id.linearLayoutImageSlider)
    LinearLayout linearLayoutImageSlider;
    @BindView(R.id.recyclerImageList)
    RecyclerView recyclerImageList;
    @BindView(R.id.imageViewAdd)
    ImageView imageViewAdd;
    @BindView(R.id.buttonUpdate)
    AppCompatButton buttonUpdate;
    @BindView(R.id.editTextDescripation)
    FirstCapEditText editTextDescripation;

    String logoPath = "";
    private double latitudeOrg = 0.0;
    private double longitudeOrg = 0.0;


    @Override
    protected int createLayout() {
        return R.layout.fragment_edit_organization;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected EditOrganizationView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        if (activities == null && sports == null) {
            activities = new ArrayList<>();
            sports = new ArrayList<>();

            presenter.getFilterData();
        }

        customTextViewActivitiesType.setSelected(true);

        customTextViewActivitiesType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard();
                customTextViewActivitiesType.setSelected(!customTextViewActivitiesType.isSelected());
                linearLayoutActivitiesType.setVisibility(!customTextViewActivitiesType.isSelected() ? View.VISIBLE : View.GONE);
            }
        });


        customTextViewSport.setSelected(true);

        customTextViewSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard();
                customTextViewSport.setSelected(!customTextViewSport.isSelected());
                linearLayoutSport.setVisibility(!customTextViewSport.isSelected() ? View.VISIBLE : View.GONE);
            }
        });


        customTextLocation.setOnClickListener(view1 -> {
            ///zxc asd
            try {
                Intent intent =
                        new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                .build(getActivity());
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            } catch (GooglePlayServicesRepairableException e) {
                // TODO: Handle the error.
            } catch (GooglePlayServicesNotAvailableException e) {
                // TODO: Handle the error.
            }

        });


        if (addImages == null) {
            addImages = new ArrayList<>();
        }

        check();

        addImagesAdapter = new AddImagesAdapter(getActivity(), addImages, new AddImagesAdapter.CallBack() {
            @Override
            public void onClickSelect(AddImages addImages) {
                selectImage = addImages.getImageUrl();
                check();
            }

            @Override
            public void onClickDelete(AddImages addImage) {
/*
                if (addImages.size() == 1) {
                    showMessage(getString(R.string.val_delete_org_image));
                    return;
                }
*/
                if (addImage.getSetId().isEmpty()) {
                    addImages.remove(addImage);
                    check();
                    addImagesAdapter.notifyDataSetChanged();
                } else {
                    presenter.callDeleteMediea(addImage);
                }
            }
        });

        recyclerImageList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerImageList.setAdapter(addImagesAdapter);

    }

    @OnClick({R.id.imageViewBack, R.id.imageViewAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.imageViewAdd:
                presenter.imagePick(new ImagePicker.ImagePickerResult() {
                    @Override
                    public void onResult(String path) {
                        AddImages addImage = new AddImages();
                        addImage.setImageUrl(path);
                        addImages.add(addImage);
                        addImagesAdapter.notifyDataSetChanged();
                    }
                });
                break;
        }
    }

    private void check() {
        if (addImages.size() == 0) {
            linearLayoutImageSlider.setVisibility(View.GONE);
        } else {
            if (linearLayoutImageSlider.getVisibility() == View.GONE) {
                linearLayoutImageSlider.setVisibility(View.VISIBLE);
            }
            if (selectImage.isEmpty()) {
                linearLayoutImageSlider.setVisibility(View.GONE);
            } else {
                if (selectImage.contains("android.resource:")) {
                    Picasso.with(getActivity()).load(selectImage).resize(getActivity().getResources().getDimensionPixelOffset(R.dimen.dp_570), getActivity().getResources().getDimensionPixelOffset(R.dimen.dp_310)).centerCrop().transform(new Rounded(20, Rounded.Corners.ALL)).placeholder(R.drawable.place).into(imageViewSlide);
                } else {
                    File file=new File(selectImage);
                    if(file.exists()){
                        Picasso.with(getActivity()).load(file).resize(getActivity().getResources().getDimensionPixelOffset(R.dimen.dp_570), getActivity().getResources().getDimensionPixelOffset(R.dimen.dp_310)).centerCrop().transform(new Rounded(20, Rounded.Corners.ALL)).placeholder(R.drawable.place).into(imageViewSlide);
                    }else {
                        Picasso.with(getActivity()).load(selectImage).resize(getActivity().getResources().getDimensionPixelOffset(R.dimen.dp_570), getActivity().getResources().getDimensionPixelOffset(R.dimen.dp_310)).centerCrop().transform(new Rounded(20, Rounded.Corners.ALL)).placeholder(R.drawable.place).into(imageViewSlide);
                    }
                }
            }
        }
    }

    @OnClick({R.id.imageViewBusiness})
    public void onViewClickedImage() {
        presenter.imagePickLogo(path -> {
            Glide.with(getActivity()).load(path).asBitmap().transform(new CropCircleTransformation(getActivity())).into(imageViewBusiness);
            logoPath = path;

        });
/*
        presenter.imagePick(new ImagePicker.ImagePickerResult() {
            @Override
            public void onResult(String path) {
                Glide.with(getActivity()).load(path).asBitmap().transform(new CropCircleTransformation(getActivity())).into(imageViewBusiness);
                logoPath = path;
            }
        });
*/
    }

    @OnClick(R.id.buttonUpdate)
    public void onViewClicked() {

        try {

            if (logoPath.isEmpty()) {
                ApplicationException applicationException = new ApplicationException(getString(R.string.please_select_org_logo));
                throw applicationException;
            }

            validator.submit(customTextOrgName).checkEmpty().errorMessage(R.string.val_org_name).check();
            validator.submit(editTextDescripation).checkEmpty().errorMessage(R.string.val_org_desc).check();

            if (isCheckActivity()) {
                ApplicationException applicationException = new ApplicationException(getString(R.string.cal_selec_activities));
                throw applicationException;
            }

            if (isCheckSport()) {
                ApplicationException applicationException = new ApplicationException(getString(R.string.val_select_sport));
                throw applicationException;
            }


            validator.submit(customTextLocation).checkEmpty().errorMessage(R.string.val_enter_location).check();
           // validator.submit(customTextWebSiteUrl).checkEmpty().errorMessage(R.string.val_enter_web_url).check();

            if (addImages.isEmpty()) {
                ApplicationException applicationException = new ApplicationException(getString(R.string.val_please_val_image));
                throw applicationException;
            }


            File file = new File(logoPath);

            if (file.isFile()) {

                List<String> fileData = getFileData();
                fileData.add(0, logoPath);

                uploadImagesVideo(fileData, new ArrayList<>(), "editOrganisation", new CallBackUploadImagesMulti() {
                    @Override
                    public void onCallBack(List<MultiFile> imagePathList) {

                        // showLoader();

                        String imagePath = imagePathList.get(0).getFile();
                        if (!imagePath.isEmpty()) {
                            imagePathList.remove(0);
                        }

                        presenter.callWsCrateOrg(organizationDetails, customTextOrgName.getText().toString().trim(),
                                imagePath,
                                editTextDescripation.getText().toString().trim(),
                                customTextLocation.getText().toString().trim(),
                                "" + latitudeOrg,
                                "" + longitudeOrg,
                                customTextWebSiteUrl.getText().toString().trim(),
                                sports,
                                activities,
                                imagePathList);
                    }
                });

/*
                uploadImages(logoPath, "editOrganisation", new CallBackUploadImages() {
                    @Override
                    public void onCallBack(String imagePath) {
                        if (!imagePath.isEmpty()) {


                        }
                    }
                });
*/

            } else {

                uploadImagesVideo(getFileData(), new ArrayList<>(), "editOrganisation", new CallBackUploadImagesMulti() {
                    @Override
                    public void onCallBack(List<MultiFile> imagePathList) {

                        // showLoader();

                        presenter.callWsCrateOrg(organizationDetails, customTextOrgName.getText().toString().trim(),
                                "",
                                editTextDescripation.getText().toString().trim(),
                                customTextLocation.getText().toString().trim(),
                                "" + latitudeOrg,
                                "" + longitudeOrg,
                                customTextWebSiteUrl.getText().toString().trim(),
                                sports,
                                activities,
                                imagePathList);
                    }
                });
            }

        } catch (ApplicationException e) {
            e.printStackTrace();
            showMessage(e.getMessage());
        }

    }

    private boolean isCheckSport() {
        for (Service service : sports) {
            if (service.isSelect()) {
                return false;
            }
        }
        return true;
    }

    private boolean isCheckActivity() {
        for (Activities activitie : activities) {
            if (activitie.isSelect()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setData(Response<FilterGetData> filterGetDataResponse) {


        activities.clear();
        activities.addAll(filterGetDataResponse.getData().getExperience());

        sports.clear();
        sports.addAll(filterGetDataResponse.getData().getSports());
        //It is used to set activity data

        presenter.getOrgData();

    }

    @Override
    public void setDataOrg(OrganizationDetails data) {
        organizationDetails = data;
        activities = presenter.mapActivity(activities, data.getActivities());
        sports = presenter.mapSport(sports, data.getServices());

        recyclerActivitiesType.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerActivitiesType.setAdapter(new OrgActivityAdapter(getActivity(), activities, new OrgActivityAdapter.CallBack() {
            @Override
            public void onClick(int position) {
                customTextViewActivitiesType.setText(presenter.getActivity(activities));

            }
        }));

        //It is used to set sport data

        recyclerSportType.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerSportType.setAdapter(new OrgServiceAdapter(getActivity(), sports, new OrgServiceAdapter.CallBack() {
            @Override
            public void onClickItem(int position) {
                customTextViewSport.setText(presenter.getSport(sports));
            }
        }));

        customTextViewActivitiesType.setText(presenter.getActivity(activities));
        customTextViewSport.setText(presenter.getSport(sports));

        logoPath = data.getImageUrl();
        Glide.with(getActivity()).load(data.getImageUrl()).asBitmap().transform(new CropCircleTransformation(getActivity())).into(imageViewBusiness);

        customTextOrgName.setText(data.getOrgName());
        editTextDescripation.setText(data.getOrgDescripation());

        customTextLocation.setText(data.getOrgLocation());

        latitudeOrg = Double.parseDouble(data.getOrgLatitude());
        longitudeOrg = Double.parseDouble(data.getOrgLongitude());

        customTextWebSiteUrl.setText(data.getSiteUrl());

        for (OrgImage blogMedia : data.getOrgImage()) {
            AddImages addImage = new AddImages();
            addImage.setSetId(blogMedia.getId());
            addImage.setVideo(false);
            addImage.setImageUrl(blogMedia.getImageUrl());
            addImages.add(addImage);
        }

        if (addImagesAdapter != null) {
            addImagesAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void deleteMedia(AddImages addImagesD) {
        addImages.remove(addImagesD);
        check();
        addImagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);

                latitudeOrg = place.getLatLng().latitude;
                longitudeOrg = place.getLatLng().longitude;

                setLocation(place.getAddress().toString());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    /**
     * It is used to set location
     *
     * @param tetx
     */
    private void setLocation(String tetx) {

        if (customTextLocation != null) {
            customTextLocation.setText(tetx);
        }
    }


    private List<String> getFileData() {
        List<String> strings = new ArrayList<>();

        for (AddImages addImage : addImages) {
            if (addImage.getSetId().isEmpty()) {
                if (addImage.getVideoPath().isEmpty()) {
                    strings.add(addImage.getImageUrl());
                } else {
                    strings.add(addImage.getVideoPath());
                }
            }
        }

        return strings;
    }

    @OnClick({R.id.textViewSportPriceRules, R.id.textViewAddLocal})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewSportPriceRules:
                presenter.openSetPriceRules(organizationDetails);
                break;
            case R.id.textViewAddLocal:
                presenter.openAddLocal(organizationDetails);
                break;
        }
    }
}
