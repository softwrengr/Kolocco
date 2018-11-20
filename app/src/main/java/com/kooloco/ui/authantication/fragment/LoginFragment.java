package com.kooloco.ui.authantication.fragment;
/**
 * Created by hlink44 on 23/8/17.
 */

import android.animation.Animator;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.exception.ApplicationException;
import com.kooloco.model.FbGoogleData;
import com.kooloco.ui.authantication.AuthActivity;
import com.kooloco.ui.authantication.presenter.LoginPresenter;
import com.kooloco.ui.authantication.view.LoginView;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.util.Validator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.support.animation.SpringForce.DAMPING_RATIO_HIGH_BOUNCY;
import static android.support.animation.SpringForce.STIFFNESS_LOW;


public class LoginFragment extends BaseFragment<LoginPresenter, LoginView> implements LoginView {

    @BindView(R.id.imageViewLogo)
    ImageView imageViewLogo;
    @BindView(R.id.linearLayoutLogo)
    LinearLayout linearLayoutLogo;
    @BindView(R.id.linearLayoutContent)
    LinearLayout linearLayoutContent;
    @BindView(R.id.loginEditTextEmail)
    AppCompatEditText loginEditTextEmail;
    @BindView(R.id.loginEditTextPassword)
    AppCompatEditText loginEditTextPassword;
    private VelocityTracker velocityTracker;
    private float velocity;

    @Inject
    Validator validator;

    @Override
    protected int createLayout() {
        velocityTracker = VelocityTracker.obtain();
        return R.layout.fragment_login;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linearLayoutLogo.animate().translationY(0).setDuration(1000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                if (getActivity() != null) {
                    if (imageViewLogo != null) {
                        // animLogo(imageViewLogo, DynamicAnimation.SCALE_X, 1);
                        //animLogo(imageViewLogo, DynamicAnimation.SCALE_Y, 1);
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();
        linearLayoutContent.animate().translationY(0).setDuration(1000).start();

    }

    @Override
    protected LoginView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        loginEditTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
//                loginEditTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

    }

    private void animLogo(View view, DynamicAnimation.ViewProperty viewProperty, float fromPosition) {
        SpringAnimation anim = new SpringAnimation(view, viewProperty, fromPosition);
        anim.getSpring().setStiffness(STIFFNESS_LOW);
        anim.getSpring().setDampingRatio(DAMPING_RATIO_HIGH_BOUNCY);
        velocityTracker.computeCurrentVelocity(2000);
        velocity = velocityTracker.getYVelocity();
        anim.setStartVelocity(velocity);
        anim.start();
    }

    @OnClick(R.id.loginEditTextPassword)
    public void onViewClicked() {
        //popUpInfo(loginEditTextPassword, Gravity.CENTER, 0, loginEditTextPassword.getBottom() + loginEditTextPassword.getHeight() + getActivity().getResources().getDimensionPixelOffset(R.dimen.dp_10));
    }

    private void popUpInfo(View parent, int grayity, int x, int y) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);

        View customView = inflater.inflate(R.layout.popup_custom_select, null);

        customView.findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "OnClick text", Toast.LENGTH_SHORT).show();
            }
        });
                /*
                    public PopupWindow (View contentView, int width, int height)
                        Create a new non focusable popup window which can display the contentView.
                        The dimension of the window must be passed to this constructor.

                        The popup does not provide any background. This should be handled by
                        the content view.

                    Parameters
                        contentView : the popup's content
                        width : the popup's width
                        height : the popup's height
                */
        // Initialize a new instance of popup window
        final PopupWindow mPopupWindow = new PopupWindow(
                customView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        // Set an elevation value for popup window
        // Call requires API level 21

        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mPopupWindow.dismiss();
                return true;
            }
        });
        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });

        mPopupWindow.showAtLocation(parent, grayity, x, y);
    }


    @OnClick({R.id.buttonLogin, R.id.buttonGooglePlus, R.id.buttonFacebook, R.id.customTextViewDont, R.id.customTextViewSignUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buttonLogin:
                try {
                    validator.submit(loginEditTextEmail).checkEmpty().errorMessage(R.string.val_enter_email).checkValidEmail().errorMessage(R.string.val_email_valid).check();
                    validator.submit(loginEditTextPassword).checkEmpty().errorMessage(R.string.val_enter_password).check();
                    //presenter.openHomeActivity();
                    presenter.login(loginEditTextEmail.getText().toString().trim(), loginEditTextPassword.getText().toString());
                } catch (ApplicationException e) {
                    e.printStackTrace();
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
            case R.id.customTextViewDont:
            case R.id.customTextViewSignUp:
                presenter.openSignUp();
                break;
        }
    }

    @OnClick(R.id.textViewLost)
    public void onClickForgot() {
        presenter.openForgotPassword();
    }
}
