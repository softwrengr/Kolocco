package com.kooloco.uilocal.expereince.fragment;
/**
 * Created by hlink on 16/4/18.
 */

import android.app.Dialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.exception.ApplicationException;
import com.kooloco.model.AddImages;
import com.kooloco.model.Medium;
import com.kooloco.model.MultiFile;
import com.kooloco.model.OtherDetailsAnotherFields;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.expereince.adapter.AddDetailsImagesAdapter;
import com.kooloco.uilocal.expereince.adapter.OtherDetialsAnotherFieldsAdapter;
import com.kooloco.uilocal.expereince.presenter.OtherDetailsAnotherFieldsPresenter;
import com.kooloco.uilocal.expereince.view.OtherDetailsAnotherFieldsView;
import com.kooloco.util.FirstCapEditText;
import com.kooloco.util.ImagePicker;
import com.kooloco.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class OtherDetailsAnotherFieldsFragment extends BaseFragment<OtherDetailsAnotherFieldsPresenter, OtherDetailsAnotherFieldsView> implements OtherDetailsAnotherFieldsView {
    @BindView(R.id.buttonNext)
    AppCompatButton buttonNext;
    List<OtherDetailsAnotherFields> otherDetailsAnotherFields;
    @BindView(R.id.recyclerOtherFields)
    RecyclerView recyclerOtherFields;
    OtherDetialsAnotherFieldsAdapter otherDetialsAnotherFieldsAdapter;
    private boolean isEdit;
    Dialog dialogAddOther;
    List<AddImages> addImages;
    AddDetailsImagesAdapter addDetailsImagesAdapter;

    @Inject
    Validator validator;
    private String expId = "";

    String addFiledsId = "";

    @Override
    protected int createLayout() {
        return R.layout.exp_local_fragment_exp_other_details_another_fields;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected OtherDetailsAnotherFieldsView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        otherDetailsAnotherFields = new ArrayList<>();

        buttonNext.setText(isEdit ? getActivity().getResources().getString(R.string.button_done) : getActivity().getResources().getString(R.string.button_next));


        presenter.getData(expId);
        setNothanks();

        otherDetialsAnotherFieldsAdapter = new OtherDetialsAnotherFieldsAdapter(getActivity(), otherDetailsAnotherFields, new OtherDetialsAnotherFieldsAdapter.CallBack() {
            @Override
            public void onClickItem(OtherDetailsAnotherFields otherDetailsAnotherFields, int pos) {

            }

            @Override
            public void onClickItemDelete(OtherDetailsAnotherFields otherDetailsAnotherField, int pos) {
                presenter.callDeleteWs(otherDetailsAnotherField, pos);
            }

            @Override
            public void onClickItemEdit(OtherDetailsAnotherFields otherDetailsAnotherField, int pos) {
                showAddAnothrFields(otherDetailsAnotherField, pos);
            }
        });

        recyclerOtherFields.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerOtherFields.setAdapter(otherDetialsAnotherFieldsAdapter);
    }


    @OnClick({R.id.imageViewBack, R.id.linearLayoutAddPicture, R.id.buttonNext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.linearLayoutAddPicture:
                showAddAnothrFields(null, 0);
                break;
            case R.id.buttonNext:
                if (isEdit) {
                    goBack();
                } else {
                    presenter.openSetLocation(expId);
                }
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
    public void setData(List<OtherDetailsAnotherFields> data) {
        otherDetailsAnotherFields.clear();
        otherDetailsAnotherFields.addAll(data);

        if (otherDetialsAnotherFieldsAdapter != null) {
            otherDetialsAnotherFieldsAdapter.notifyDataSetChanged();
        }

        setNothanks();

    }

    @Override
    public void deleteExpOtherFiends(OtherDetailsAnotherFields otherDetailsAnotherField, int pos) {
        otherDetailsAnotherFields.remove(otherDetailsAnotherField);
        otherDetialsAnotherFieldsAdapter.notifyItemRemoved(pos);
        setNothanks();
    }

    public void showAddAnothrFields(OtherDetailsAnotherFields otherDetailsAnotherFieldsEdit, int pos) {


        if (dialogAddOther != null) {
            if (dialogAddOther.isShowing()) {
                dialogAddOther.dismiss();
            }
        }

        addImages = new ArrayList<>();


        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_other_details_add_another_fields, null, false);

        AppCompatButton buttonAdd = view.findViewById(R.id.buttonAdd);

        FirstCapEditText editTextExpNewFieldsTitle = view.findViewById(R.id.editTextExpNewFieldsTitle);

        LinearLayout linearLayoutAddPicture = view.findViewById(R.id.linearLayoutAddPicture);

        RecyclerView recyclerImageList = view.findViewById(R.id.recyclerImageList);

        AppCompatEditText editTextNameOfTheNewFields = view.findViewById(R.id.editTextNameOfTheNewFields);

        addDetailsImagesAdapter = new AddDetailsImagesAdapter(getActivity(), addImages, true, new AddDetailsImagesAdapter.CallBack() {
            @Override
            public void onClickSelect(AddImages addImages) {

            }

            @Override
            public void onClickDelete(AddImages addImage) {
                addImages.remove(addImage);
                addDetailsImagesAdapter.notifyDataSetChanged();

            }
        });


        recyclerImageList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerImageList.setAdapter(addDetailsImagesAdapter);

        linearLayoutAddPicture.setOnClickListener(view1 -> {
            presenter.imagePick(new ImagePicker.ImagePickerResult() {
                @Override
                public void onResult(String path) {
                    AddImages addImage = new AddImages();
                    addImage.setImageUrl(path);
                    addImages.add(addImage);
                    addDetailsImagesAdapter.notifyDataSetChanged();
                }
            });
        });


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validator.submit(editTextNameOfTheNewFields).checkEmpty().errorMessage(R.string.val_other_details_add_fields).check();

                    validator.submit(editTextExpNewFieldsTitle).checkEmpty().errorMessage(R.string.val_other_details_add_fields_desc).check();

                    if (addImages.size() == 0) {
                        throw new ApplicationException(getString(R.string.val_other_details_new_filds_picyure));
                    }

                    List<String> fileData = getFileData();

                    uploadImagesVideo(fileData, new ArrayList<>(), "experienceOtherImages", new CallBackUploadImagesMulti() {
                        @Override
                        public void onCallBack(List<MultiFile> imagePathList) {
                            imagePathList.addAll(0, getFileDataAllReadyUpload());

                            try {
                                presenter.callWs(addFiledsId, expId, editTextNameOfTheNewFields.getText().toString().trim(), editTextExpNewFieldsTitle.getText().toString().trim(), imagePathList, (isSuccess, message, otherDetailsAnotherFieldsData) -> {
                                    if (isSuccess) {
                                        dialogAddOther.dismiss();
                                        setData(otherDetailsAnotherFieldsData, !addFiledsId.isEmpty(), pos);

                                    } else {
                                        showSnackBar(editTextExpNewFieldsTitle, message);
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });

                } catch (ApplicationException e) {
                    e.printStackTrace();
                    showSnackBar(editTextExpNewFieldsTitle, e.getMessage());
                }

            }
        });

        dialogAddOther = new Dialog(getActivity());

        dialogAddOther.requestWindowFeature(Window.FEATURE_NO_TITLE);


        dialogAddOther.getWindow().setBackgroundDrawableResource(R.drawable.drwable_background_tra);

        dialogAddOther.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        dialogAddOther.setContentView(view);

        dialogAddOther.show();


        if (otherDetailsAnotherFieldsEdit == null) {
            addFiledsId = "";
            buttonAdd.setText(getActivity().getResources().getString(R.string.button_add));
        } else {
            addFiledsId = otherDetailsAnotherFieldsEdit.getId();
            editTextNameOfTheNewFields.setText(otherDetailsAnotherFieldsEdit.getTitle());
            editTextExpNewFieldsTitle.setText(otherDetailsAnotherFieldsEdit.getDesc());
            addImages.clear();
            addImages.addAll(otherDetailsAnotherFieldsEdit.getAddImages());
            buttonAdd.setText(getActivity().getResources().getString(R.string.button_done));
        }
    }

    private void setNothanks() {
        if (otherDetailsAnotherFields.size() == 0) {
            buttonNext.setText(R.string.title_no_thanks);
        } else {
            buttonNext.setText(isEdit ? getActivity().getResources().getString(R.string.button_done) : getActivity().getResources().getString(R.string.button_next));
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

    private void setData(OtherDetailsAnotherFields otherDetailsAnotherFieldsData, boolean isEdit, int pos) {

        List<AddImages> tempImages = new ArrayList<>();

        for (Medium medium : otherDetailsAnotherFieldsData.getMedia()) {
            AddImages addImage = new AddImages();

            addImage.setImageUrl(medium.getImage());
            addImage.setSetId(medium.getId());
            addImage.setFileName(medium.getFile());
            addImage.setVideo(false);
            tempImages.add(addImage);
        }

        otherDetailsAnotherFieldsData.setAddImages(tempImages);

        if (isEdit) {
            otherDetailsAnotherFields.set(pos, otherDetailsAnotherFieldsData);
            otherDetialsAnotherFieldsAdapter.notifyItemChanged(pos);
        } else {

            otherDetailsAnotherFields.add(otherDetailsAnotherFieldsData);
            otherDetialsAnotherFieldsAdapter.notifyItemInserted(otherDetailsAnotherFields.size() - 1);
        }

        setNothanks();
    }
}
