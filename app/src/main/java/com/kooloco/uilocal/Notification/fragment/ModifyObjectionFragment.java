package com.kooloco.uilocal.Notification.fragment;
/**
 * Created by hlink44 on 9/10/17.
 */


import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputFilter;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.exception.ApplicationException;
import com.kooloco.model.ObjectionDetails;
import com.kooloco.model.OrderDetails;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.Notification.presenter.ModifyObjectionPresenter;
import com.kooloco.uilocal.Notification.view.ModifyObjectionView;
import com.kooloco.util.FirstCapEditText;
import com.kooloco.util.InputFilterMinMaxDouble;
import com.kooloco.util.Validator;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.logging.Handler;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class ModifyObjectionFragment extends BaseFragment<ModifyObjectionPresenter, ModifyObjectionView> implements ModifyObjectionView {


    @BindView(R.id.checkboxFlatAmount)
    CheckBox checkboxFlatAmount;
    @BindView(R.id.editTextDiscount)
    AppCompatEditText editTextDiscount;
    @BindView(R.id.checkboxDiscount)
    CheckBox checkboxDiscount;
    @Inject
    Validator validator;
    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewLocalName)
    AppCompatTextView customTextViewLocalName;
    @BindView(R.id.customTextViewPriceTotal)
    AppCompatTextView customTextViewPriceTotal;
    @BindView(R.id.customTexteditTextWriteNow)
    FirstCapEditText customTexteditTextWriteNow;
    @BindView(R.id.textViewFlatRate)
    AppCompatTextView textViewFlatRate;
    @BindView(R.id.editTextFlat)
    AppCompatEditText editTextFlat;

    private String notificationId = "";

    OrderDetails orderDetails;
    boolean isNotification = false;

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_modify_objection;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ModifyObjectionView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        presenter.getData(notificationId);

        new android.os.Handler().postDelayed(() -> setData(0), 100);

        textViewFlatRate.setText(getString(R.string.do_you_want_to_give_flat_rate_new).replace("$", BaseActivity.currency));

        editTextFlat.setHint(BaseActivity.currency + " " + "00");
    }

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        goBack();
    }

    @OnClick({R.id.checkboxFlatAmount, R.id.checkboxDiscount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.checkboxFlatAmount:
                setData(1);
                editTextDiscount.setText("");
                break;
            case R.id.checkboxDiscount:
                setData(0);
                editTextFlat.setText("");
                break;
        }
    }

    private void setData(int position) {
        checkboxDiscount.setChecked(position == 0);
        checkboxFlatAmount.setChecked(position == 1);
        editTextDiscount.setEnabled(position == 0);
        editTextFlat.setEnabled(position == 1);


        if (position == 0) {
            hideKeyBoard();
            editTextDiscount.requestFocus();
            focusKeyBoard(editTextDiscount);
        } else {
            hideKeyBoard();
            editTextFlat.requestFocus();
            focusKeyBoard(editTextFlat);
        }

    }


    @OnClick(R.id.buttonSubmit)
    public void onViewClickedSubmit() {
        try {
            validator.submit(checkboxDiscount.isChecked() ? editTextDiscount : editTextFlat).checkEmpty().errorMessage(checkboxDiscount.isChecked() ? getActivity().getResources().getString(R.string.val_enter_discount_amount) : getActivity().getResources().getString(R.string.val_enter_flat_rate)).check();

            String type = "TR";
            String objectionValue = "";

            if (checkboxDiscount.isChecked()) {
                type = "PR";
                objectionValue = editTextDiscount.getText().toString().trim();
            } else if (checkboxFlatAmount.isChecked()) {
                type = "FA";
                objectionValue = editTextFlat.getText().toString().trim();
            }

            validator.submit(customTexteditTextWriteNow).checkEmpty().errorMessage(R.string.val_w_reason).check();

            presenter.callSendObjection(orderDetails.getId(), type, objectionValue, customTexteditTextWriteNow.getText().toString().trim(), notificationId);

        } catch (ApplicationException e) {
            e.printStackTrace();
            showMessage(e.getMessage());
        }
    }

    @Override
    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    @Override
    public void setData(ObjectionDetails data) {
        Double totalPrice = Double.parseDouble(data.getOrderDetails().getActivityTootal());

        editTextDiscount.setFilters(new InputFilter[]{new InputFilterMinMaxDouble(0.00, 100.00)});
        editTextFlat.setFilters(new InputFilter[]{new InputFilterMinMaxDouble(0.00, totalPrice)});

        orderDetails = data.getOrderDetails();

        customTextViewLocalName.setText(data.getOrderDetails().getFirstname() + " " + data.getOrderDetails().getLastname());

        Picasso.with(getActivity()).load(data.getOrderDetails().getProfileImage()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);

        customTextViewPriceTotal.setText(BaseActivity.currency + " " + data.getOrderDetails().getActivityTootal());

    }

    @Override
    public void finishActivity() {
        if (isNotification) {
            goBack();
        } else {
            getActivity().finish();
            EventBus.getDefault().post(EventBusAction.NOTIFICATIONLOCAL);
            EventBus.getDefault().post(EventBusAction.ACCEPTREFRESE);
        }
    }

    @Override
    public void setIsOpenNotification(boolean isNotification) {
        this.isNotification = isNotification;

    }
}
