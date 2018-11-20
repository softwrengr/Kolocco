package com.kooloco.uilocal.organaization.fragment;
/**
 * Created by hlink44 on 11/10/17.
 */

import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.exception.ApplicationException;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.organaization.presenter.PerAddBankPresenter;
import com.kooloco.uilocal.organaization.view.PerAddBankView;
import com.kooloco.util.Validator;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;

public class PerAddBankFragment extends BaseFragment<PerAddBankPresenter, PerAddBankView> implements PerAddBankView {

    @BindView(R.id.editTextDefPer)
    AppCompatEditText editTextDefPer;
    @BindView(R.id.editTextBankName)
    AppCompatEditText editTextBankName;
    @BindView(R.id.editTextAccountHolderName)
    AppCompatEditText editTextAccountHolderName;
    @BindView(R.id.editTextAccountNumber)
    AppCompatEditText editTextAccountNumber;
    @BindView(R.id.editTextRoutingNumber)
    AppCompatEditText editTextRoutingNumber;
    @Inject
    Validator validator;

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_add_bank_per;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected PerAddBankView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        InputFilter[] inputFilters = {
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if (src.toString().matches("[a-zA-Z]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        };

        editTextAccountHolderName.setFilters(inputFilters);

    }

    @OnClick({R.id.imageViewBack, R.id.buttonSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.buttonSubmit:

                try {
                    validator.submit(editTextDefPer).checkEmpty().errorMessage(R.string.val_define_per).check();
                    validator.submit(editTextBankName).checkEmpty().errorMessage(R.string.val_enter_bank_name).check();
                    validator.submit(editTextAccountHolderName).checkEmpty().errorMessage(R.string.val_account_holder_name).check();
                    validator.submit(editTextAccountNumber).checkEmpty().errorMessage(R.string.val_account_number).check();
                    validator.submit(editTextRoutingNumber).checkEmpty().errorMessage(R.string.val_routing_number).checkMinDigits(9).errorMessage(R.string.val_digit_routing_number).check();

                    presenter.getOrganizationDashboard();

                } catch (ApplicationException e) {
                    e.printStackTrace();
                    showMessage(e.getMessage());
                }
                break;
        }
    }

}
