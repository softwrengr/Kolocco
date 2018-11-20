package com.kooloco.uilocal.profile.fragment;
/**
 * Created by hlink44 on 16/10/17.
 */

import android.net.Uri;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.kooloco.R;


import com.kooloco.data.temp.Temp;
import com.kooloco.exception.ApplicationException;
import com.kooloco.model.Certifications;
import com.kooloco.uilocal.profile.adapter.EditCertificationsAdapter;
import com.kooloco.uilocal.profile.view.EditCertificationsView;
import com.kooloco.uilocal.profile.presenter.EditCertificationsPresenter;
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

public class EditCertificationsFragment extends BaseFragment<EditCertificationsPresenter, EditCertificationsView> implements EditCertificationsView {

    @BindView(R.id.recyclerUploadCertificates)
    RecyclerView recyclerUploadCertificates;
    @BindView(R.id.imageViewAddCertificate)
    ImageView imageViewAddCertificate;

    List<Certifications> certificationsList;

    EditCertificationsAdapter uploadCertificationsAdapter;

    @Inject
    Validator validator;

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_edit_certifications;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected EditCertificationsView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        certificationsList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Certifications certifications = new Certifications();
            certifications.setTitles("Official swiss bike instructor teacher");
            certifications.setType("Biking");
            certifications.setSubType("Slop");
            certifications.setImageUrl("android.resource://com.kooloco/drawable/temp_certificat");
            certifications.setDesc("Nunquam locus tumultumque. Speciess cantare, tanquam camerarius torus.");
            certificationsList.add(certifications);
        }


        uploadCertificationsAdapter = new EditCertificationsAdapter(getActivity(), certificationsList, new EditCertificationsAdapter.CallBack() {
            @Override
            public void onClickDelete(int position) {
                certificationsList.remove(position);
                uploadCertificationsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onClickSelectImage(final int position) {
                presenter.imagePick(new ImagePicker.ImagePickerResult() {
                    @Override
                    public void onResult(String path) {
                        Certifications certifications = certificationsList.get(position);
                        certifications.setImageUrl(path);
                        certificationsList.set(position, certifications);
                        uploadCertificationsAdapter.notifyItemChanged(position);
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
                        uploadCertificationsAdapter.notifyItemChanged(position);
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
                        uploadCertificationsAdapter.notifyItemChanged(position);
                    }
                });
            }

            @Override
            public void onCheckValidation(AppCompatEditText appCompatEditTextTitle, AppCompatEditText appCompatEditTextDesc, Certifications certificationsD) {
                try {
                    if (certificationsD.getImageUrl().isEmpty()) {
                        ApplicationException applicationException = new ApplicationException(getString(R.string.val_select_image_cert));
                        throw applicationException;
                    }
                    validator.submit(appCompatEditTextTitle).checkEmpty().errorMessage(R.string.val_certificate_title).check();
                    if (certificationsD.getType().isEmpty()) {
                        ApplicationException applicationException = new ApplicationException(getString(R.string.val_select_sport_type));
                        throw applicationException;
                    }
                    if (certificationsD.getSubType().isEmpty()) {
                        ApplicationException applicationException = new ApplicationException(getString(R.string.val_select_sub_sport_type));
                        throw applicationException;
                    }
                    validator.submit(appCompatEditTextDesc).checkEmpty().errorMessage(R.string.val_cert_desc).check();
                } catch (ApplicationException e) {
                    e.printStackTrace();
                    showMessage(e.getMessage());
                }
            }
        });

        recyclerUploadCertificates.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerUploadCertificates.setAdapter(uploadCertificationsAdapter);
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
                uploadCertificationsAdapter.notifyItemInserted(certificationsList.size() - 1);
                recyclerUploadCertificates.scrollToPosition(certificationsList.size() - 1);
                break;
            case R.id.buttonUpdate:
                goBack();
                break;
        }
    }
}
