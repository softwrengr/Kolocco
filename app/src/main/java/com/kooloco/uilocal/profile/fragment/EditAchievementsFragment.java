package com.kooloco.uilocal.profile.fragment;
/**
 * Created by hlink44 on 16/10/17.
 */

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.kooloco.R;


import com.kooloco.data.temp.Temp;
import com.kooloco.exception.ApplicationException;
import com.kooloco.model.Certifications;
import com.kooloco.uilocal.addservice.adapter.UploadAchievementsAdapter;
import com.kooloco.uilocal.profile.adapter.EditAchievementsAdapter;
import com.kooloco.uilocal.profile.view.EditAchievementsView;
import com.kooloco.uilocal.profile.presenter.EditAchievementsPresenter;
import com.kooloco.di.component.FragmentComponent;

import com.kooloco.ui.base.BaseFragment;
import com.kooloco.util.ImagePicker;
import com.kooloco.util.Validator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class EditAchievementsFragment extends BaseFragment<EditAchievementsPresenter, EditAchievementsView> implements EditAchievementsView {

    @BindView(R.id.recyclerUploadAchievments)
    RecyclerView recyclerUploadAchievments;
    @BindView(R.id.imageViewAddCertificate)
    ImageView imageViewAddCertificate;

    List<Certifications> certificationsList;

    EditAchievementsAdapter uploadAchievementsAdapter;

    @Inject
    Validator validator;

    @Override
    protected int createLayout() {
        return R.layout.fragment_edit_achievements;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected EditAchievementsView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        certificationsList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Certifications certifications = new Certifications();
            certifications.setTitles("Skiing Achievements");
            certifications.setType("Skiing");
            certifications.setSubType("Slop");
            certifications.setImageUrl("android.resource://com.kooloco/drawable/temp_certificat");
            certifications.setDesc("Nunquam locus tumultumque. Speciess cantare, tanquam camerarius torus.");
            certificationsList.add(certifications);
        }

        uploadAchievementsAdapter = new EditAchievementsAdapter(getActivity(), certificationsList, new EditAchievementsAdapter.CallBack() {
            @Override
            public void onClickDelete(int position) {
                certificationsList.remove(position);
                uploadAchievementsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onClickSelectImage(final int position) {
                presenter.imagePick(new ImagePicker.ImagePickerResult() {
                    @Override
                    public void onResult(String path) {
                        Certifications certifications = certificationsList.get(position);
                        certifications.setImageUrl(path);
                        certificationsList.set(position, certifications);
                        uploadAchievementsAdapter.notifyItemChanged(position);
                    }
                });
            }

            @Override
            public void onClickSelectSportType(final int position) {
                openBottomSheet(Temp.getFilSports(), new CallBackSelectBottomSheet() {
                    @Override
                    public void onClick(String value) {
                        Certifications certifications = certificationsList.get(position);
                        certifications.setType(value);
                        certificationsList.set(position, certifications);
                        uploadAchievementsAdapter.notifyItemChanged(position);
                    }
                });
            }

            @Override
            public void onClickSelectSubSportType(final int position, Certifications certifications) {
                if (certificationsList.get(position).getType().isEmpty()) {
                    showMessage(getActivity().getResources().getString(R.string.val_select_images));
                    return;
                }
                openBottomSheet(Temp.getSubServiceN(), new CallBackSelectBottomSheet() {
                    @Override
                    public void onClick(String value) {
                        Certifications certifications = certificationsList.get(position);
                        certifications.setSubType(value);
                        certificationsList.set(position, certifications);
                        uploadAchievementsAdapter.notifyItemChanged(position);
                    }
                });
            }

            @Override
            public void onCheckValidation(AppCompatEditText appCompatEditTextTitle, AppCompatEditText appCompatEditTextDesc, Certifications certificationsD) {
                try {
                    if (certificationsD.getImageUrl().isEmpty()) {
                        ApplicationException applicationException = new ApplicationException(getString(R.string.val_select_image_achiv));
                        throw applicationException;
                    }
                    validator.submit(appCompatEditTextTitle).checkEmpty().errorMessage(R.string.val_achiv_title).check();
                    if (certificationsD.getType().isEmpty()) {
                        ApplicationException applicationException = new ApplicationException(getString(R.string.val_select_sport_type));
                        throw applicationException;
                    }
                    if (certificationsD.getSubType().isEmpty()) {
                        ApplicationException applicationException = new ApplicationException(getString(R.string.val_select_sub_sport_type));
                        throw applicationException;
                    }
                    validator.submit(appCompatEditTextDesc).checkEmpty().errorMessage(R.string.val_achiv_desc).check();
                } catch (ApplicationException e) {
                    e.printStackTrace();
                    showMessage(e.getMessage());
                }
            }
        });

        recyclerUploadAchievments.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerUploadAchievments.setAdapter(uploadAchievementsAdapter);
    }

    @OnClick({R.id.imageViewBack, R.id.imageViewAddCertificate, R.id.buttonUpdate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.imageViewAddCertificate:
                Certifications certifications = new Certifications();
                certificationsList.add(certifications);
                uploadAchievementsAdapter.notifyItemInserted(certificationsList.size() - 1);
                // recyclerUploadCertificates.scrollToPosition(certificationsList.size() - 1);
                break;
            case R.id.buttonUpdate:
                goBack();
                break;
        }
    }
}
