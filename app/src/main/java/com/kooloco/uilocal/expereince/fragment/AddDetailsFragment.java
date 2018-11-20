package com.kooloco.uilocal.expereince.fragment;
/**
 * Created by hlink on 16/4/18.
 */

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.exception.ApplicationException;
import com.kooloco.model.Activities;
import com.kooloco.model.AddImages;
import com.kooloco.model.ExperienceResponse;
import com.kooloco.model.Medium;
import com.kooloco.model.MultiFile;
import com.kooloco.model.Service;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.expereince.adapter.AddDetailsImagesAdapter;
import com.kooloco.uilocal.expereince.adapter.AddDetailsImagesNewAdapter;
import com.kooloco.uilocal.expereince.presenter.AddDetailsPresenter;
import com.kooloco.uilocal.expereince.view.AddDetailsView;
import com.kooloco.util.FirstCapEditText;
import com.kooloco.util.ImagePicker;
import com.kooloco.util.Validator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class AddDetailsFragment extends BaseFragment<AddDetailsPresenter, AddDetailsView> implements AddDetailsView {

    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.editTextExpTitle)
    FirstCapEditText editTextExpTitle;
    @BindView(R.id.editTextExpDesc)
    FirstCapEditText editTextExpDesc;
    @BindView(R.id.linearLayoutAddPicture)
    LinearLayout linearLayoutAddPicture;
    @BindView(R.id.buttonNext)
    AppCompatButton buttonNext;
    @BindView(R.id.recyclerImageList)
    RecyclerView recyclerImageList;
    private boolean isEdit;


    List<AddImages> addImages;
    AddDetailsImagesNewAdapter addImagesAdapter;

    @Inject
    Validator validator;
    public String selectImage = "";
    private String expId = "";

    @Override
    protected int createLayout() {
        return R.layout.exp_local_fragment_exp_add_details;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected AddDetailsView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        buttonNext.setText(isEdit ? getActivity().getResources().getString(R.string.button_done) : getActivity().getResources().getString(R.string.button_next));

        if (addImages == null) {
            addImages = new ArrayList<>();
        }


        addImagesAdapter = new AddDetailsImagesNewAdapter(getActivity(), addImages, true, new AddDetailsImagesNewAdapter.CallBack() {
            @Override
            public void onClickSelect(AddImages addImages) {
                selectImage = addImages.getImageUrl();
            }

            @Override
            public void onClickDelete(AddImages addImage) {
                addImages.remove(addImage);
                addImagesAdapter.notifyDataSetChanged();
            }
        });

        recyclerImageList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerImageList.setAdapter(addImagesAdapter);

        if (!expId.isEmpty()) {
            presenter.getExpData(expId);
        }
    }

    @OnClick({R.id.imageViewBack, R.id.buttonNext, R.id.linearLayoutAddPicture})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.buttonNext:
                try {
                    validator.submit(editTextExpTitle).checkEmpty().errorMessage(R.string.vla_exp_title).check();
                    validator.submit(editTextExpDesc).checkEmpty().errorMessage(R.string.exp_val_desc).check();
                    if (addImages.size() == 0) {
                        throw new ApplicationException(getString(R.string.val_exp_picture));
                    }


                    if (addImages.isEmpty()) {
                        ApplicationException applicationException = new ApplicationException(getString(R.string.val_please_val_image));
                        throw applicationException;
                    }

                    List<String> fileData = getFileData();

                    uploadImagesVideo(fileData, new ArrayList<>(), "experienceImages", new CallBackUploadImagesMulti() {
                        @Override
                        public void onCallBack(List<MultiFile> imagePathList) {
                            imagePathList.addAll(0, getFileDataAllReadyUpload());
                            presenter.callWs(expId, editTextExpTitle.getText().toString().trim(), editTextExpDesc.getText().toString().trim(), imagePathList, isEdit);
                        }
                    });

                } catch (ApplicationException e) {
                    e.printStackTrace();
                    showMessage(e.getMessage());
                }
                break;
            case R.id.linearLayoutAddPicture:
                presenter.imagePick(new ImagePicker.ImagePickerResult() {
                    @Override
                    public void onResult(String path) {
                        AddImages addImage = new AddImages();
                        addImage.setImageUrl(path);
                        addImages.add(addImage);
                        addImage.setFileName(new File(path).getName());

                        addImagesAdapter.notifyDataSetChanged();
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
    public void setExpId(String expId) {
        this.expId = expId;
    }

    @Override
    public void setData(ExperienceResponse experienceResponse) {
        expId = experienceResponse.getId();
        editTextExpTitle.setText(experienceResponse.getTitle());
        editTextExpDesc.setText(experienceResponse.getDescription());

        if (addImages == null) {
            addImages = new ArrayList<>();
        }

        addImages.clear();

        for (Medium medium : experienceResponse.getMedia()) {
            AddImages addImage = new AddImages();

            addImage.setImageUrl(medium.getImage());
            addImage.setSetId(medium.getId());
            addImage.setFileName(medium.getFile());
            addImage.setVideo(false);
            addImages.add(addImage);
        }


        if (addImagesAdapter != null) {
            addImagesAdapter.notifyDataSetChanged();
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

    private List<MultiFile> getFileDataAllReadyUpload() {
        List<MultiFile> strings = new ArrayList<>();

        for (AddImages addImage : addImages) {
            if (!addImage.getSetId().isEmpty()) {
                MultiFile multiFile = new MultiFile();
                if (addImage.getVideoPath().isEmpty()) {
                    multiFile.setFile(addImage.getFileName());
                    multiFile.setMediaType("I");
                } else {
                    multiFile.setFile(addImage.getVideoPath());
                    multiFile.setMediaType("I");
                }

                strings.add(multiFile);
            }
        }
        return strings;
    }

}
