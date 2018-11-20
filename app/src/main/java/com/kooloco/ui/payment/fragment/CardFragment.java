package com.kooloco.ui.payment.fragment;
/**
 * Created by hlink on 14/6/18.
 */

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.exception.ApplicationException;
import com.kooloco.model.Card;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.payment.adapter.CreditCardAdapter;
import com.kooloco.ui.payment.presenter.CardPresenter;
import com.kooloco.ui.payment.view.CardView;
import com.kooloco.util.Validator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class CardFragment extends BaseFragment<CardPresenter, CardView> implements CardView {

    @BindView(R.id.recyclerCard)
    RecyclerView recyclerCard;
    @BindView(R.id.customTextViewAddCard)
    AppCompatTextView customTextViewAddCard;
    @BindView(R.id.editTextCardHolderName)
    AppCompatEditText editTextCardHolderName;
    @BindView(R.id.editTextCardNumber)
    AppCompatEditText editTextCardNumber;
    @BindView(R.id.editTextExpiryDate)
    AppCompatEditText editTextExpiryDate;
    @BindView(R.id.editTextCardCvv)
    AppCompatEditText editTextCardCvv;
    CreditCardAdapter creditCardAdapter;
    List<Card> cards;

    int month = 0;
    int year = 0;
    Dialog expiryDateDialog;
    String selectDate = "";
    String selectDateExp = "";


    @Inject
    Validator validator;
    @BindView(R.id.linearLayoutCard)
    LinearLayout linearLayoutCard;

    private boolean isBooking;

    @Override
    protected int createLayout() {
        return R.layout.card_visitor_fragment;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected CardView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        onClickExpiryDate();

        if (cards == null) {
            cards = new ArrayList<>();
            presenter.getData();
        }
        recyclerCard.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        creditCardAdapter = new CreditCardAdapter(getActivity(), cards, (card, pos) -> {

            showDialogDeleteWithAnimation("", isSuccess -> {
                if (isSuccess) {
                    presenter.callDelete(card, pos);
                }
            });
        });

        recyclerCard.setAdapter(creditCardAdapter);

    }

    @OnClick({R.id.imageViewBack, R.id.editTextExpiryDate, R.id.buttonAdd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.editTextExpiryDate:
                if (expiryDateDialog != null) {
                    if (!expiryDateDialog.isShowing()) {
                        expiryDateDialog.show();
                    }
                } else {
                    onClickExpiryDate();
                }
                break;
            case R.id.buttonAdd:

                try {
                    validator.submit(editTextCardHolderName).checkEmpty().errorMessage(R.string.valid_card_holder_name).check();
                    validator.submit(editTextCardNumber).checkEmpty().errorMessage(R.string.valid_empty_car_number).checkMinDigits(12).errorMessage(R.string.valid_card_number).check();
                    if (selectDate.isEmpty()) {
                        throw new ApplicationException(getString(R.string.valid_select_expiry_date));
                    }
                    validator.submit(editTextCardCvv).checkEmpty().errorMessage(R.string.valid_empty_cvv).checkMinDigits(3).errorMessage(R.string.valid_cvv).check();

                    presenter.saveCardData(editTextCardHolderName.getText().toString().trim(), editTextCardNumber.getText().toString().trim(), selectDate, editTextCardCvv.getText().toString().trim(), isBooking);

                } catch (ApplicationException e) {
                    showMessage(e.getMessage());
                }

                break;

        }
    }

    @Override
    public void setCardData(List<Card> data) {
        cards.clear();
        cards.addAll(data);

        if (creditCardAdapter != null) {
            creditCardAdapter.notifyDataSetChanged();
        }

        checkEmpty();
    }

    @Override
    public void deleteCard(int pos) {
        cards.remove(pos);

        if (creditCardAdapter != null) {
            creditCardAdapter.notifyItemRemoved(pos);
        }

        checkEmpty();

    }


    public void onClickExpiryDate() {

        expiryDateDialog = new BottomSheetDialog(getActivity());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_expiry_date_dialog, null, false);

        final NumberPicker numberPickerMonth = (NumberPicker) view.findViewById(R.id.numberPickerMonth);
        NumberPicker numberPickerYear = (NumberPicker) view.findViewById(R.id.numberPickerYear);

        Button buttonDone = (Button) view.findViewById(R.id.buttonDone);

        numberPickerMonth.setMaxValue(12);
        Calendar calendar = Calendar.getInstance();
        final int currentYear = calendar.get(Calendar.YEAR);
        numberPickerYear.setMinValue(currentYear);
        numberPickerYear.setMaxValue(currentYear + 20);

        expiryDateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        expiryDateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));


        expiryDateDialog.setContentView(view);


        final int currentMonth = calendar.get(Calendar.MONTH);


        month = currentMonth + 1;
        year = currentYear;

        numberPickerMonth.setValue(month);
        numberPickerYear.setValue(year);

        if (year == currentYear) {
            numberPickerMonth.setMinValue(currentMonth + 1);
        } else {
            numberPickerMonth.setMinValue(1);
        }

        numberPickerMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                month = newVal;
            }
        });

        numberPickerYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                year = newVal;
                if (currentYear == newVal) {
                    numberPickerMonth.setMinValue(currentMonth + 1);
                } else {
                    numberPickerMonth.setMinValue(1);
                }
            }
        });

        expiryDateDialog.setTitle("");
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month = numberPickerMonth.getValue();
                year = numberPickerYear.getValue();

                if (String.valueOf(month).length() == 1) {
                    selectDateExp = "0" + month + "/" + ("" + year).substring(2);

                    editTextExpiryDate.setText("0" + month + "/" + year);
                } else {
                    editTextExpiryDate.setText("" + month + "/" + year);
                    selectDateExp = month + "/" + ("" + year).substring(2);

                }
                selectDate = editTextExpiryDate.getText().toString();
                expiryDateDialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(expiryDateDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        expiryDateDialog.getWindow().setAttributes(lp);
        expiryDateDialog.getWindow().setGravity(Gravity.CENTER);
    }

    public void clearData() {
        editTextCardHolderName.setText("");
        editTextCardNumber.setText("");
        editTextExpiryDate.setText("");
        editTextCardCvv.setText("");
        selectDate = "";
        editTextCardHolderName.requestFocus();

    }

    @Override
    public void setIsBooking(boolean isBooking) {
        this.isBooking = isBooking;
    }

    private void checkEmpty() {
        if (linearLayoutCard != null) {
            linearLayoutCard.setVisibility((cards.size() == 0) ? View.GONE : View.VISIBLE);
        }
    }

}
