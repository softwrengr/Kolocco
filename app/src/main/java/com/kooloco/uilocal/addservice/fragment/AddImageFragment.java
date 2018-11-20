package com.kooloco.uilocal.addservice.fragment;
/**
 * Created by hlink44 on 2/10/17.
 */

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.constant.Step;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.exception.ApplicationException;
import com.kooloco.model.Certifications;
import com.kooloco.model.SportActivity;
import com.kooloco.model.SportSubActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.addservice.adapter.AddImagesNewAdapter;
import com.kooloco.uilocal.addservice.presenter.AddImagePresenter;
import com.kooloco.uilocal.addservice.view.AddImageView;
import com.kooloco.util.ImagePicker;
import com.kooloco.util.Validator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class AddImageFragment extends BaseFragment<AddImagePresenter, AddImageView> implements AddImageView {

    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.textViewSkip)
    AppCompatTextView textViewSkip;

    @BindView(R.id.recyclerAddImages)
    RecyclerView recyclerAddImages;

    List<SportActivity> sportActivities;

    @BindView(R.id.imageViewAddImages)
    PorterShapeImageView imageViewAddImages;

    @BindView(R.id.imageViewFilter)
    ImageView imageViewFilter;

    @BindView(R.id.editTextCertificateType)
    AppCompatEditText editTextCertificateType;

    @BindView(R.id.editTextCertificateSubType)
    AppCompatEditText editTextCertificateSubType;

    @BindView(R.id.viewBottomSpace)
    View viewBottomSpace;

    @BindView(R.id.editTextCertificateDesc)
    AppCompatEditText editTextCertificateDesc;

    @BindView(R.id.buttonUpload)
    AppCompatButton buttonUpload;

    @BindView(R.id.buttonNext)
    AppCompatButton buttonNext;
    @BindView(R.id.viewBottomLine)
    View viewBottomLine;

    private boolean isEdit = false;

    List<Certifications> sportList;

    AddImagesNewAdapter addImagesNewAdapter;

    @BindView(R.id.editTextCertificateTitle)
    AppCompatEditText editTextCertificateTitle;

    @Inject
    Validator validator;

    private String addImageId = "0";

    private String selectImage = "";

    private SportActivity selectSportActivity = null;

    private SportSubActivity selectSportSubActivity;

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_add_image;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected AddImageView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        if (sportActivities == null) {

        }

        presenter.getSportType();

        if (isEdit) {
            textViewSkip.setVisibility(View.INVISIBLE);
            buttonNext.setText(getActivity().getResources().getString(R.string.button_done));
            toolbarTitle.setText(getActivity().getResources().getString(R.string.toolbar_edit_images));
        } else {
            textViewSkip.setVisibility(View.VISIBLE);
            buttonNext.setText(getActivity().getResources().getString(R.string.button_next));
            toolbarTitle.setText(getActivity().getResources().getString(R.string.toolbar_add_images));
        }

        if (sportList == null) {
            sportList = new ArrayList<>();
        }

        if (!selectImage.isEmpty()) {
            setImage();
            Glide.with(getActivity()).load(selectImage).asBitmap().into(imageViewAddImages);
        }

        addImagesNewAdapter = new AddImagesNewAdapter(getActivity(), sportList, isEdit, new AddImagesNewAdapter.CallBack() {

            @Override
            public void onClickEdit(Certifications certifications) {

                editTextCertificateTitle.setText(certifications.getTitles());
                editTextCertificateDesc.setText(certifications.getDesc());
                editTextCertificateType.setText(certifications.getType());
                editTextCertificateSubType.setText(certifications.getSubType());
                selectImage = certifications.getImageUrl();

                selectSportActivity = getSelectSportActivity(certifications.getSportTypeId());

                if (selectSportActivity != null) {
                    selectSportSubActivity = getSelectSportSubActivity(certifications.getSportSubTypeId(), selectSportActivity.getSportSubActivities());
                }

                editTextCertificateSubType.setVisibility((selectSportSubActivity == null) ? View.INVISIBLE : View.VISIBLE);
                viewBottomLine.setVisibility((selectSportSubActivity == null) ? View.INVISIBLE : View.VISIBLE);


                imageViewAddImages.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(certifications.getImageUrl()).asBitmap().placeholder(R.drawable.place).into(imageViewAddImages);
                addImageId = certifications.getId();
            }

            @Override
            public void onClickDelete(Certifications certifications) {
                clearValue();
                presenter.callDeleteWs(certifications);
            }
        });

        recyclerAddImages.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerAddImages.setAdapter(addImagesNewAdapter);

    }

    @OnClick({R.id.imageViewBack, R.id.textViewSkip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.textViewSkip:
                skipSignUpStep(Step.step2, new CallBackSignupStep() {
                    @Override
                    public void onSuccess() {
                        presenter.openUploadCertificates();
                    }
                });
                break;

        }
    }

    @Override
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    @Override
    public void setSportList(List<SportActivity> data) {
        sportActivities = new ArrayList<>();
        sportActivities.addAll(data);
        presenter.getSelectSportList(true);
    }

    @Override
    public void notifyItemChange(Certifications certificationsD, int position) {
        sportList.set(position, certificationsD);

        if (addImagesNewAdapter != null) {
            addImagesNewAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void add() {

        if (presenter != null) {
            try {
                clearValue();
                presenter.getSelectSportList(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void setData(List<Certifications> data) {
        sportList.clear();
        sportList.addAll(data);

        if (addImagesNewAdapter != null) {
            addImagesNewAdapter.notifyDataSetChanged();
        }

        if (viewBottomSpace != null) {
            viewBottomSpace.setVisibility(sportList.size() == 0 ? View.GONE : View.VISIBLE);
        }
    }

    @OnClick({R.id.buttonUpload, R.id.imageViewAddSport, R.id.imageViewFilter, R.id.buttonNext, R.id.imageViewAddImages, R.id.editTextCertificateType, R.id.editTextCertificateSubType})
    public void onViewClickedUpload(View view) {
        switch (view.getId()) {
            case R.id.buttonUpload:
                try {

                    // validator.submit(editTextCertificateTitle).checkEmpty().errorMessage(getString(R.string.val_add_images_title)).check();

                    if (selectImage.isEmpty()) {
                        ApplicationException applicationException = new ApplicationException(getString(R.string.val_select_image_add));
                        throw applicationException;
                    }
                    if (selectSportActivity == null) {
                        ApplicationException applicationException = new ApplicationException(getString(R.string.val_select_sport_type));
                        throw applicationException;
                    }

                    if (selectSportActivity.isExpand()) {
                        if (selectSportSubActivity == null) {
                            ApplicationException applicationException = new ApplicationException(getString(R.string.val_select_sub_sport_type));
                            throw applicationException;
                        }
                    }

                    //  validator.submit(editTextCertificateDesc).checkEmpty().errorMessage(R.string.val_add_image_desc).check();

                    Certifications certifications = new Certifications();

                    certifications.setSportTypeId(selectSportActivity.getId());
                    certifications.setType(selectSportActivity.getName());
                    certifications.setSportActivity(selectSportActivity);
                    certifications.setTitles(editTextCertificateTitle.getText().toString().trim());


                    if (selectSportSubActivity != null) {
                        certifications.setSportSubTypeId(selectSportSubActivity.getId());
                        certifications.setSubType(selectSportSubActivity.getName());
                    }

                    certifications.setSportSubActivity(selectSportSubActivity);


                    certifications.setDesc(editTextCertificateDesc.getText().toString().trim());
                    certifications.setAddImageId(addImageId);

                    certifications.setImageUrl(selectImage);


                    File file = new File(selectImage);

                    if (!file.isFile()) {
                        certifications.setImageUrl("");
                    }

                    presenter.callWs(certifications, false, false);

                } catch (ApplicationException e) {
                    e.printStackTrace();
                    showMessage(e.getMessage());
                }
                break;
            case R.id.imageViewAddSport:
                clearValue();
                break;
            case R.id.buttonNext:

                if (selectImage.isEmpty() && selectSportActivity == null) {
                    hideLoader();

                    if (isEdit) {
                        goBack();
                    } else {
                        presenter.openUploadCertificates();
                    }

                    break;
                }

                try {

                    // validator.submit(editTextCertificateTitle).checkEmpty().errorMessage(getString(R.string.val_add_images_title)).check();

                    if (selectImage.isEmpty()) {
                        ApplicationException applicationException = new ApplicationException(getString(R.string.val_select_image_add));
                        throw applicationException;
                    }
                    if (selectSportActivity == null) {
                        ApplicationException applicationException = new ApplicationException(getString(R.string.val_select_sport_type));
                        throw applicationException;
                    }

                    if (selectSportActivity.isExpand()) {
                        if (selectSportSubActivity == null) {
                            ApplicationException applicationException = new ApplicationException(getString(R.string.val_select_sub_sport_type));
                            throw applicationException;
                        }
                    }

                    //  validator.submit(editTextCertificateDesc).checkEmpty().errorMessage(R.string.val_add_image_desc).check();

                    Certifications certifications = new Certifications();

                    certifications.setSportTypeId(selectSportActivity.getId());
                    certifications.setType(selectSportActivity.getName());
                    certifications.setSportActivity(selectSportActivity);
                    certifications.setTitles(editTextCertificateTitle.getText().toString().trim());


                    if (selectSportSubActivity != null) {
                        certifications.setSportSubTypeId(selectSportSubActivity.getId());
                        certifications.setSubType(selectSportSubActivity.getName());
                    }

                    certifications.setSportSubActivity(selectSportSubActivity);


                    certifications.setDesc(editTextCertificateDesc.getText().toString().trim());
                    certifications.setAddImageId(addImageId);

                    certifications.setImageUrl(selectImage);


                    File file = new File(selectImage);

                    if (!file.isFile()) {
                        certifications.setImageUrl("");
                    }

                    presenter.callWs(certifications, true, isEdit);

                } catch (ApplicationException e) {
                    e.printStackTrace();
                    showMessage(e.getMessage());
                }

                break;
            case R.id.imageViewAddImages:
            case R.id.imageViewFilter:
                presenter.imagePick(new ImagePicker.ImagePickerResult() {
                    @Override
                    public void onResult(String path) {
                        selectImage = path;
                        setImage();
                        Glide.with(getActivity()).load(selectImage).asBitmap().into(imageViewAddImages);
                    }
                });
                break;
            case R.id.editTextCertificateType:
                openBottomSheetSportType(sportActivities, new CallBackSelectBottomSheetSportType() {
                    @Override
                    public void onClick(SportActivity value) {
                        selectSportActivity = value;
                        editTextCertificateType.setText(value.getName());
                        editTextCertificateSubType.setVisibility(value.isExpand() ? View.VISIBLE : View.INVISIBLE);
                        viewBottomLine.setVisibility(value.isExpand() ? View.VISIBLE : View.INVISIBLE);
                        editTextCertificateSubType.setText("");
                        selectSportSubActivity = null;
                    }
                });
                break;
            case R.id.editTextCertificateSubType:
                if (editTextCertificateType.getText().toString().isEmpty() || selectSportActivity == null) {
                    showMessage(getActivity().getResources().getString(R.string.val_select_images));
                    return;
                }
                openBottomSheetSportSubType(selectSportActivity.getSportSubActivities(), new CallBackSelectBottomSheetSportSubType() {
                    @Override
                    public void onClick(SportSubActivity value) {
                        selectSportSubActivity = value;
                        editTextCertificateSubType.setText(value.getName());
                    }
                });
                break;
        }
    }

    private void setImage() {
        imageViewAddImages.setVisibility(!selectImage.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        imageViewFilter.setVisibility(selectImage.isEmpty() ? View.VISIBLE : View.INVISIBLE);
    }

    private void clearValue() {
        addImageId = "0";
        selectImage = "";
        selectSportActivity = null;
        selectSportSubActivity = null;

        setImage();
        editTextCertificateTitle.setText("");
        editTextCertificateType.setText("");
        editTextCertificateSubType.setText("");
        editTextCertificateSubType.setVisibility(View.VISIBLE);
        viewBottomLine.setVisibility(View.VISIBLE);
        editTextCertificateDesc.setText("");
    }

    @Override
    public void clearValueNew() {
        addImageId = "0";
        selectImage = "";
        selectSportActivity = null;
        selectSportSubActivity = null;

        setImage();
        editTextCertificateTitle.setText("");
        editTextCertificateType.setText("");
        editTextCertificateSubType.setText("");
        editTextCertificateSubType.setVisibility(View.VISIBLE);
        viewBottomLine.setVisibility(View.VISIBLE);
        editTextCertificateDesc.setText("");

        if (isEdit) {
            goBack();
        } else {
            presenter.openUploadCertificates();
        }

    }

    private SportActivity getSelectSportActivity(String id) {
        SportActivity sportActivityNew = null;
        for (SportActivity sportActivity : sportActivities) {
            if (sportActivity.getId().equalsIgnoreCase(id)) {
                sportActivityNew = sportActivity;
                break;
            }
        }
        return sportActivityNew;
    }

    private SportSubActivity getSelectSportSubActivity(String id, List<SportSubActivity> sportSubActivitiesNew) {
        SportSubActivity sportActivityNew = null;
        for (SportSubActivity sportSubActivity : sportSubActivitiesNew) {
            if (sportSubActivity.getId().equalsIgnoreCase(id)) {
                sportActivityNew = sportSubActivity;
                break;
            }
        }
        return sportActivityNew;
    }
}
