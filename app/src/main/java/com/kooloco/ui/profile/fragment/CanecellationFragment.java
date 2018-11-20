package com.kooloco.ui.profile.fragment;
/**
 * Created by hlink44 on 25/9/17.
 */

import android.app.Dialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.CancellationData;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.profile.presenter.CanecellationPresenter;
import com.kooloco.ui.profile.view.CanecellationView;
import com.kooloco.util.FirstCapEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class CanecellationFragment extends BaseFragment<CanecellationPresenter, CanecellationView> implements CanecellationView {
    Dialog dialog;
    @BindView(R.id.customTextViewTotalBookingAmount)
    AppCompatTextView customTextViewTotalBookingAmount;
    @BindView(R.id.customTextViewWillBeBy)
    AppCompatTextView customTextViewWillBeBy;
    @BindView(R.id.customTexteditTextWriteNow)
    FirstCapEditText customTexteditTextWriteNow;
    private String orderId;

    @Override
    protected int createLayout() {
        return R.layout.fragment_canecellation;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected CanecellationView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        presenter.getCancellationData(orderId);
    }

    @OnClick(R.id.buttonCancel)
    public void onViewClicked() {
        showDialog();
    }

    public void showDialog() {

        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_cancel, null, false);

        AppCompatButton appCompatButtonNo = view.findViewById(R.id.buttonNo);
        AppCompatButton appCompatButtonYes = view.findViewById(R.id.buttonYes);

        appCompatButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        appCompatButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.callWsCancel(orderId, customTexteditTextWriteNow.getText().toString().trim(), new CanecellationPresenter.CallBack() {
                    @Override
                    public void onSuccess(boolean isSucess,String message) {
                        if (isSucess) {
                            dialog.dismiss();
                            //  showMessage(getActivity().getResources().getString(R.string.cancel_fully));
                            presenter.goBack();
                        }
                        else {
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.drwable_background_tra);

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        dialog.setContentView(view);

        dialog.show();

    }


    @OnClick(R.id.imageViewBack)
    public void onViewClickedBack() {
        goBack();
    }


    @Override
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public void setData(CancellationData data) {
        if (customTextViewTotalBookingAmount != null) {
            customTextViewTotalBookingAmount.setText(BaseActivity.currency + " " + data.getTotal_booking());
        }
        if (customTextViewWillBeBy != null) {
            customTextViewWillBeBy.setText(BaseActivity.currency + " " + data.getRefaund());
        }

    }
}
