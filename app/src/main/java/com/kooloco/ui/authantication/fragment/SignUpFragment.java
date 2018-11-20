package com.kooloco.ui.authantication.fragment;
/**
 * Created by hlink44 on 24/8/17.
 */

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.exception.ApplicationException;
import com.kooloco.model.FbGoogleData;
import com.kooloco.ui.authantication.AuthActivity;
import com.kooloco.ui.authantication.presenter.SignUpPresenter;
import com.kooloco.ui.authantication.view.SignUpView;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.util.ImagePicker;
import com.kooloco.util.Validator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class SignUpFragment extends BaseFragment<SignUpPresenter, SignUpView> implements SignUpView {

    @BindView(R.id.signUpEditTextFirstName)
    AppCompatEditText signUpEditTextFirstName;
    @BindView(R.id.signUpEditTextLastName)
    AppCompatEditText signUpEditTextLastName;
    @BindView(R.id.signUpEditTextEmail)
    AppCompatEditText signUpEditTextEmail;
    @BindView(R.id.signUpEditTextCreatePassword)
    AppCompatEditText signUpEditTextCreatePassword;
    @BindView(R.id.signUpEditTextConfirmPassword)
    AppCompatEditText signUpEditTextConfirmPassword;
    @BindView(R.id.signUpEditTextReferralCode)
    AppCompatEditText signUpEditTextReferralCode;
    @BindView(R.id.signupTermsAndConditions)
    AppCompatCheckBox signupTermsAndConditions;

    String imageUrl = "";
    @BindView(R.id.buttonGooglePlus)
    ImageView buttonGooglePlus;
    @BindView(R.id.buttonFacebook)
    ImageView buttonFacebook;

    private boolean isSocial;

    private String signType = "S";

    private String socialId = "";

    private FbGoogleData fbGoogleData;

    @Inject
    Validator validator;
    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;

    @Override
    protected int createLayout() {
        return R.layout.fragment_sign_up;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }


    @Override
    protected SignUpView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        String text = "" + getActivity().getResources().getString(R.string.sign_up_referral_code) + " " + "<font color='" + getActivity().getResources().getColor(R.color.greyText) + "'>" + getActivity().getResources().getString(R.string.sign_up_referral_code_optional) + "</font>";
        signUpEditTextReferralCode.setHint(Html.fromHtml(text));
        signupTermsAndConditions.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.opensans_regular));
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

/*        signUpEditTextFirstName.setFilters(inputFilters);
        signUpEditTextLastName.setFilters(inputFilters);*/

        signUpEditTextEmail.setEnabled(!isSocial);

        signUpEditTextCreatePassword.setVisibility(isSocial ? View.GONE : View.VISIBLE);
        signUpEditTextConfirmPassword.setVisibility(isSocial ? View.GONE : View.VISIBLE);

        if (isSocial) {

            buttonFacebook.setVisibility(isSocial ? View.GONE : View.VISIBLE);
            buttonGooglePlus.setVisibility(isSocial ? View.GONE : View.VISIBLE);

            if (!fbGoogleData.getImageUrl().isEmpty()) {
                Glide.with(getActivity()).load(fbGoogleData.getImageUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        imageUrl = ((AuthActivity) getActivity()).storeImageToCache(resource);
                        Glide.with(getActivity()).load(imageUrl).asBitmap().transform(new CropCircleTransformation(getActivity())).into(imageViewProfile);
                    }
                });
            }

            String fName = "";
            String lName = "";

            String[] split = fbGoogleData.getFirstName().split(" ");

            for (int i = 0; i < split.length; i++) {
                if (i == 0) {
                    fName = split[i];
                } else if (i == 1) {
                    lName = split[i];
                }
            }

            signUpEditTextFirstName.setText(fName);
            signUpEditTextLastName.setText(lName);
            signUpEditTextEmail.setText(fbGoogleData.getEmail());
        }
    }

    @OnClick({R.id.buttonSignuUp, R.id.buttonGooglePlus, R.id.buttonFacebook})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buttonSignuUp:
                try {
                    validator.submit(signUpEditTextFirstName).checkEmpty().errorMessage(R.string.val_enter_first_name).check();
                    validator.submit(signUpEditTextLastName).checkEmpty().errorMessage(R.string.val_enter_last_name).check();
                    validator.submit(signUpEditTextEmail).checkEmpty().errorMessage(R.string.val_enter_email).checkValidEmail().errorMessage(R.string.val_email_valid).check();

                    if (!isSocial) {
                        validator.submit(signUpEditTextCreatePassword).checkEmpty().errorMessage(R.string.val_enter_create_password).checkMinDigits(4).errorMessage(R.string.val_enter_password_minimum_4).check();
                        validator.submit(signUpEditTextConfirmPassword).checkEmpty().errorMessage(R.string.val_enter_conformpassword).matchString("" + signUpEditTextCreatePassword.getText().toString()).errorMessage(R.string.val_password_mismatch).check();
                    }

                    if (!signupTermsAndConditions.isChecked()) {
                        ApplicationException application = new ApplicationException(getString(R.string.val_agress_terms_conditions));
                        throw application;
                    }

                    presenter.signUp(signUpEditTextFirstName.getText().toString().trim(), signUpEditTextLastName.getText().toString().trim(), signUpEditTextEmail.getText().toString().trim(), signUpEditTextCreatePassword.getText().toString(), signUpEditTextReferralCode.getText().toString(), imageUrl, isSocial, signType, socialId);

                } catch (ApplicationException e) {
                    showMessage(e.getMessage());
                }
                break;
            case R.id.buttonGooglePlus:
                if (getActivity() instanceof AuthActivity) {
                    AuthActivity authActivity = (AuthActivity) getActivity();
                    authActivity.loginGoogle(new AuthActivity.CallBack() {
                        @Override
                        public void onSuccess(FbGoogleData data) {
                            presenter.checkSocialId(data);
                        }
                    });
                }

                break;
            case R.id.buttonFacebook:
                if (getActivity() instanceof AuthActivity) {
                    AuthActivity authActivity = (AuthActivity) getActivity();
                    authActivity.loginFacebook(new AuthActivity.CallBack() {
                        @Override
                        public void onSuccess(FbGoogleData data) {
                            presenter.checkSocialId(data);
                        }
                    });
                }

                break;
        }
    }


    @OnClick(R.id.imageViewProfile)
    public void onViewClicked() {
        presenter.imagePick(new ImagePicker.ImagePickerResult() {
            @Override
            public void onResult(String path) {
                imageUrl = path;
                Glide.with(getActivity()).load(path).asBitmap().transform(new CropCircleTransformation(getActivity())).into(imageViewProfile);
                //     Picasso.with(getActivity()).load(path).transform(new CircleTransform()).into(imageViewProfile);
            }
        });
    }


    @OnClick(R.id.imageViewBack)
    public void onViewClickedBack() {
        goBack();
    }

    @Override
    public void setDataSignup(FbGoogleData data) {
        setSocial(true);
        setSocialId(data.getIsSocialId());
        setSocialType(data.getSignType());
        setFbGoogleData(data);
        bindData();
    }

    @Override
    public void setDataSignupNew(FbGoogleData data) {
        setSocial(true);
        setSocialId(data.getIsSocialId());
        setSocialType(data.getSignType());
        setFbGoogleData(data);
    }

    public void setSocial(boolean isSocial) {
        this.isSocial = isSocial;
    }

    public void setSocialType(String signType) {
        this.signType = signType;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public void setFbGoogleData(FbGoogleData fbGoogleData) {
        this.fbGoogleData = fbGoogleData;
    }

}
