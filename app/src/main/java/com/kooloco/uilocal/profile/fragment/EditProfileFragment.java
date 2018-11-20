package com.kooloco.uilocal.profile.fragment;
/**
 * Created by hlink44 on 16/10/17.
 */

import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.exception.ApplicationException;
import com.kooloco.model.User;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.profile.presenter.EditProfilePresenter;
import com.kooloco.uilocal.profile.view.EditProfileView;
import com.kooloco.util.ImagePicker;
import com.kooloco.util.Validator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class EditProfileFragment extends BaseFragment<EditProfilePresenter, EditProfileView> implements EditProfileView {

    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.editTextFirstName)
    AppCompatEditText editTextFirstName;
    @BindView(R.id.editTextLastName)
    AppCompatEditText editTextLastName;
    @BindView(R.id.editTextEmail)
    AppCompatEditText editTextEmail;
    @BindView(R.id.editTextPassword)
    AppCompatEditText editTextPassword;
    @BindView(R.id.editTextIntroducation)
    AppCompatEditText editTextIntroducation;

    @Inject
    Validator validator;

    String imageUrl = "";
    @BindView(R.id.becomALocalEditTextTelphone)
    AppCompatEditText becomALocalEditTextTelphone;


    boolean isLocal;

    @BindView(R.id.textViewTelephoneNumber)
    AppCompatTextView textViewTelephoneNumber;
    @BindView(R.id.editTextCountryCode)
    AppCompatEditText editTextCountryCode;
    @BindView(R.id.frameLayoutTelephoneNumber)
    FrameLayout frameLayoutTelephoneNumber;

    @Override
    protected int createLayout() {
        return R.layout.fragment_edit_profile;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected EditProfileView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        InputFilter[] inputFilters = {
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if (src.toString().matches("[a-zA-Z ]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        };
/*        editTextFirstName.setFilters(inputFilters);
        editTextLastName.setFilters(inputFilters);*/

        presenter.getUserData();


        textViewTelephoneNumber.setVisibility(isLocal ? View.VISIBLE : View.GONE);
        becomALocalEditTextTelphone.setVisibility(isLocal ? View.VISIBLE : View.GONE);
        frameLayoutTelephoneNumber.setVisibility(isLocal ? View.VISIBLE : View.GONE);


    }


    @OnClick({R.id.imageViewBack, R.id.imageViewProfile, R.id.buttonUpdate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.imageViewProfile:
                presenter.imagePick(new ImagePicker.ImagePickerResult() {
                    @Override
                    public void onResult(String path) {
                        imageUrl = path;
                        Glide.with(getActivity()).load(path).asBitmap().transform(new CropCircleTransformation(getActivity())).into(imageViewProfile);

                        //Picasso.with(getActivity()).load(path).transform(new CircleTransform()).into(imageViewProfile);
                    }
                });
                break;
            case R.id.buttonUpdate:
                try {
                    validator.submit(editTextFirstName).checkEmpty().errorMessage(R.string.val_enter_first_name).check();
                    validator.submit(editTextLastName).checkEmpty().errorMessage(R.string.val_enter_last_name).check();
//                    validator.submit(editTextEmail).checkEmpty().errorMessage(R.string.val_enter_email).checkValidEmail().errorMessage(R.string.val_email_valid).check();
                    if (isLocal) {
                        validator.submit(becomALocalEditTextTelphone).checkEmpty().errorMessage(R.string.cal_enter_tel_mob).check();
                    }

                    presenter.updateProfile(editTextFirstName.getText().toString().trim(), editTextLastName.getText().toString().trim(), editTextIntroducation.getText().toString().trim(), imageUrl, editTextCountryCode.getText().toString(), becomALocalEditTextTelphone.getText().toString().trim());
                } catch (ApplicationException e) {
                    showMessage(e.getMessage());
                }
                break;
        }
    }

    @Override
    public void setData(User user) {

        if (!user.getProfileImage().isEmpty()) {
            Glide.with(getActivity()).load(user.getProfileImage()).asBitmap().transform(new CropCircleTransformation(getActivity())).placeholder(R.drawable.user_round).into(imageViewProfile);
        }

        editTextFirstName.setText(user.getFirstname());
        editTextLastName.setText(user.getLastname());
        editTextEmail.setText(user.getEmail());

        becomALocalEditTextTelphone.setText(user.getPhone());

        if (user.getCountryCode() != null) {
            if (user.getCountryCode().isEmpty()) {
                editTextCountryCode.setText("+41");
            } else {
                editTextCountryCode.setText(user.getCountryCode());
            }
        }

        editTextIntroducation.setText(user.getIntroYourSelf());

    }

    @Override
    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    @OnClick(R.id.editTextCountryCode)
    public void onClick() {
        showDialogCountryCode(text -> {
            if (editTextCountryCode != null) {
                editTextCountryCode.setText(text);
            }
        });


    }
}
