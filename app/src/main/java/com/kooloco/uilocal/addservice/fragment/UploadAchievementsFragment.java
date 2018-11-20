package com.kooloco.uilocal.addservice.fragment;
/**
 * Created by hlink44 on 3/10/17.
 */

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
import com.kooloco.model.Tag;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.addservice.adapter.AddImagesNewAdapter;
import com.kooloco.uilocal.addservice.adapter.UploadAchievementsAdapter;
import com.kooloco.uilocal.addservice.presenter.UploadAchievementsPresenter;
import com.kooloco.uilocal.addservice.view.UploadAchievementsView;
import com.kooloco.util.FlowLayout;
import com.kooloco.util.ImagePicker;
import com.kooloco.util.Validator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class UploadAchievementsFragment extends BaseFragment<UploadAchievementsPresenter, UploadAchievementsView> implements UploadAchievementsView {

    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.textViewSkip)
    AppCompatTextView textViewSkip;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerUploadAchievments)
    RecyclerView recyclerUploadAchievments;

    List<Certifications> certificationsList;
    UploadAchievementsAdapter uploadAchievementsAdapter;

    @Inject
    Validator validator;

    List<SportActivity> sportActivities;

    List<Tag> tags;

    @BindView(R.id.textViewLabel)
    AppCompatTextView textViewLabel;

    @BindView(R.id.imageViewCertificate)
    PorterShapeImageView imageViewCertificate;

    @BindView(R.id.imageViewFilter)
    ImageView imageViewFilter;

    @BindView(R.id.editTextCertificateTitle)
    AppCompatEditText editTextCertificateTitle;

    @BindView(R.id.editTextCertificateType)
    AppCompatEditText editTextCertificateType;

    @BindView(R.id.editTextCertificateSubType)
    AppCompatEditText editTextCertificateSubType;

    @BindView(R.id.viewBottomLine)
    View viewBottomLine;

    @BindView(R.id.editTextCertificateDesc)
    AppCompatEditText editTextCertificateDesc;

    @BindView(R.id.flowLayout)
    FlowLayout flowLayout;

    @BindView(R.id.buttonUpload)
    AppCompatButton buttonUpload;

    @BindView(R.id.imageViewAddSport)
    ImageView imageViewAddSport;

    @BindView(R.id.buttonNext)
    AppCompatButton buttonNext;


    private boolean isEdit = false;

    private String addImageId = "0";

    private String selectImage = "";

    private SportActivity selectSportActivity = null;

    private SportSubActivity selectSportSubActivity;

    AddImagesNewAdapter addImagesNewAdapter;

    @BindView(R.id.viewBottomSpace)
    View viewBottomSpace;

    @Override
    protected int createLayout() {
        return R.layout.fragment_upload_achievements;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected UploadAchievementsView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        if (tags == null) {
            tags = new ArrayList<>();
        }

        if (sportActivities == null) {
        }

        presenter.getSportType();

        if (certificationsList == null) {
            certificationsList = new ArrayList<>();
        }

        if (isEdit) {
            textViewSkip.setVisibility(View.INVISIBLE);
            buttonNext.setText(getActivity().getResources().getString(R.string.button_done));
            toolbarTitle.setText(getActivity().getResources().getString(R.string.toolbar_edit_achievements));
            textViewLabel.setVisibility(View.VISIBLE);
        } else {
            textViewSkip.setVisibility(View.VISIBLE);
            buttonNext.setText(getActivity().getResources().getString(R.string.button_next));
            toolbarTitle.setText(getActivity().getResources().getString(R.string.toolbar_upload_achivements));
            textViewLabel.setVisibility(View.VISIBLE);
        }

        if (!selectImage.isEmpty()) {
            setImage();
            Glide.with(getActivity()).load(selectImage).asBitmap().into(imageViewCertificate);
        }


        addImagesNewAdapter = new AddImagesNewAdapter(getActivity(), certificationsList, isEdit, new AddImagesNewAdapter.CallBack() {

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

                List<Tag> tagsTemp = mapTags(certifications.getTags());

                tags.clear();
                tags.addAll(tagsTemp);

                setTag();

                imageViewCertificate.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(certifications.getImageUrl()).asBitmap().placeholder(R.drawable.place).into(imageViewCertificate);
                addImageId = certifications.getId();
            }

            @Override
            public void onClickDelete(Certifications certifications) {
                clearValue();
                presenter.callDeleteWs(certifications);
            }
        });

        recyclerUploadAchievments.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerUploadAchievments.setAdapter(addImagesNewAdapter);
    }

    @OnClick({R.id.imageViewBack, R.id.textViewSkip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.textViewSkip:
                skipSignUpStep(Step.step4, new CallBackSignupStep() {
                    @Override
                    public void onSuccess() {
                        presenter.openChooseLanguage();
                    }
                });
                break;
        }
    }

    @Override
    public void setSportList(List<SportActivity> data, List<Tag> responseData) {
        sportActivities = new ArrayList<>();
        sportActivities.addAll(data);

        tags.addAll(responseData);

        presenter.getSelectAchivements(true);

        if (flowLayout != null) {
            setTag();
        }

    }

    @Override
    public void notifyItemChange(Certifications certificationsD, int position) {
        certificationsList.set(position, certificationsD);

        if (uploadAchievementsAdapter != null) {
            uploadAchievementsAdapter.notifyItemChanged(position);
        }


    }

    @Override
    public void setData(List<Certifications> data) {
        certificationsList.clear();
        certificationsList.addAll(data);

        if (addImagesNewAdapter != null) {
            addImagesNewAdapter.notifyDataSetChanged();
        }

        if (viewBottomSpace != null) {
            viewBottomSpace.setVisibility(certificationsList.size() == 0 ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void add() {
        if (presenter != null) {
            try {
                clearValue();
                presenter.getSelectAchivements(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    private List<Tag> getTags() {
        List<Tag> tags1 = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            Tag tag = new Tag();

            tag.setId(tags.get(i).getId());
            tag.setSelect(false);
            tag.setName(tags.get(i).getName());
            tag.setTag(tags.get(i).getTag());
            tag.setInsertdate(tags.get(i).getInsertdate());
            tag.setIsActive(tags.get(i).getIsActive());

            tags1.add(tag);
        }
        tags.clear();
        tags.addAll(tags1);
        return tags;
    }

    private List<Tag> mapTags(List<Tag> tagsSelect) {

        List<Tag> tags1 = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            Tag tag = new Tag();

            tag.setId(tags.get(i).getId());

            tag.setSelect(isCheck(tags.get(i).getId(), tagsSelect));

            tag.setName(tags.get(i).getName());
            tag.setTag(tags.get(i).getTag());
            tag.setInsertdate(tags.get(i).getInsertdate());
            tag.setIsActive(tags.get(i).getIsActive());

            tags1.add(tag);
        }
        return tags1;
    }

    private boolean isCheck(String id, List<Tag> tagsSelect) {
        boolean isSelect = false;

        for (Tag tag : tagsSelect) {
            if (id.equalsIgnoreCase(tag.getId())) {
                isSelect = true;
            }
        }
        return isSelect;
    }

    @OnClick({R.id.buttonUpload, R.id.imageViewAddSport, R.id.imageViewFilter, R.id.buttonNext, R.id.imageViewCertificate, R.id.editTextCertificateType, R.id.editTextCertificateSubType})
    public void onViewClickedUpload(View view) {
        switch (view.getId()) {
            case R.id.buttonUpload:
                try {
                    // validator.submit(editTextCertificateTitle).checkEmpty().errorMessage(R.string.val_achiv_title).check();

                    if (selectImage.isEmpty()) {
                        ApplicationException applicationException = new ApplicationException(getString(R.string.val_select_image_achiv));
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

/*
                    validator.submit(editTextCertificateDesc).checkEmpty().errorMessage(R.string.val_achiv_desc).check();
*/

                    if (isCheckSelect()) {
                        ApplicationException applicationException = new ApplicationException(getString(R.string.val_ach_select_tag));
                        throw applicationException;
                    }

                    Certifications certifications = new Certifications();

                    certifications.setSportTypeId(selectSportActivity.getId());
                    certifications.setType(selectSportActivity.getName());
                    certifications.setSportActivity(selectSportActivity);


                    if (selectSportSubActivity != null) {
                        certifications.setSportSubTypeId(selectSportSubActivity.getId());
                        certifications.setSubType(selectSportSubActivity.getName());
                    }
                    certifications.setSportSubActivity(selectSportSubActivity);

                    certifications.setTitles(editTextCertificateTitle.getText().toString().trim());

                    certifications.setDesc(editTextCertificateDesc.getText().toString().trim());
                    certifications.setAddImageId(addImageId);

                    certifications.setTags(tags);
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
                if (selectImage.isEmpty() && selectSportActivity == null && isCheckSelect()) {
                    hideLoader();
                    if (isEdit) {
                        goBack();
                    } else {
                        presenter.openChooseLanguage();
                    }
                    break;
                }

                try {
                    // validator.submit(editTextCertificateTitle).checkEmpty().errorMessage(R.string.val_achiv_title).check();

                    if (selectImage.isEmpty()) {
                        ApplicationException applicationException = new ApplicationException(getString(R.string.val_select_image_achiv));
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

/*
                    validator.submit(editTextCertificateDesc).checkEmpty().errorMessage(R.string.val_achiv_desc).check();
*/

                    if (isCheckSelect()) {
                        ApplicationException applicationException = new ApplicationException(getString(R.string.val_ach_select_tag));
                        throw applicationException;
                    }

                    Certifications certifications = new Certifications();

                    certifications.setSportTypeId(selectSportActivity.getId());
                    certifications.setType(selectSportActivity.getName());
                    certifications.setSportActivity(selectSportActivity);


                    if (selectSportSubActivity != null) {
                        certifications.setSportSubTypeId(selectSportSubActivity.getId());
                        certifications.setSubType(selectSportSubActivity.getName());
                    }
                    certifications.setSportSubActivity(selectSportSubActivity);

                    certifications.setTitles(editTextCertificateTitle.getText().toString().trim());

                    certifications.setDesc(editTextCertificateDesc.getText().toString().trim());
                    certifications.setAddImageId(addImageId);

                    certifications.setTags(tags);
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
            case R.id.imageViewCertificate:
            case R.id.imageViewFilter:
                presenter.imagePick(new ImagePicker.ImagePickerResult() {
                    @Override
                    public void onResult(String path) {
                        selectImage = path;
                        setImage();
                        Glide.with(getActivity()).load(selectImage).asBitmap().into(imageViewCertificate);
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
        imageViewCertificate.setVisibility(!selectImage.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        imageViewFilter.setVisibility(selectImage.isEmpty() ? View.VISIBLE : View.INVISIBLE);
    }

    private void clearValue() {
        addImageId = "0";
        selectImage = "";
        selectSportActivity = null;
        selectSportSubActivity = null;

        setImage();

        getTags();

        editTextCertificateTitle.setText("");
        editTextCertificateType.setText("");
        editTextCertificateSubType.setText("");
        editTextCertificateSubType.setVisibility(View.VISIBLE);
        viewBottomLine.setVisibility(View.VISIBLE);
        editTextCertificateDesc.setText("");

        setTag();

    }


    @Override
    public void clearValueNew() {
        addImageId = "0";
        selectImage = "";
        selectSportActivity = null;
        selectSportSubActivity = null;

        setImage();

        getTags();

        editTextCertificateTitle.setText("");
        editTextCertificateType.setText("");
        editTextCertificateSubType.setText("");
        editTextCertificateSubType.setVisibility(View.VISIBLE);
        viewBottomLine.setVisibility(View.VISIBLE);
        editTextCertificateDesc.setText("");

        setTag();

        if (isEdit) {
            goBack();
        } else {
            presenter.openChooseLanguage();
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

    private void setTag() {
        flowLayout.removeAllViews();

        for (int i = 0; i < tags.size(); i++) {

            final Tag tag = tags.get(i);

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_time, null);
            ((AppCompatTextView) view.findViewById(R.id.customTextViewTag)).setText(tag.getName());
            view.setSelected(tag.isSelect());

            final int finalI = i;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setSelected(!view.isSelected());
                    tag.setSelect(view.isSelected());
                    tags.set(finalI, tag);

                }
            });

            flowLayout.addView(view);
        }
    }

    private boolean isCheckSelect() {
        boolean isSelect = true;
        for (Tag tag : tags) {
            if (tag.isSelect()) {
                return false;
            }
        }
        return isSelect;
    }
}