package com.kooloco.uilocal.addservice.fragment;
/**
 * Created by hlink44 on 27/11/17.
 */

import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Sport;
import com.kooloco.model.SportPriceRules;
import com.kooloco.model.SportPriceRulesSport;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.addservice.adapter.SportPriceRulesPagerAdapter;
import com.kooloco.uilocal.addservice.presenter.SportPriceRulesPresenter;
import com.kooloco.uilocal.addservice.view.SportPriceRulesView;
import com.kooloco.util.InputFilterMinMax;
import com.kooloco.util.InputFilterMinMaxDouble;
import com.nex3z.flowlayout.FlowLayout;

import butterknife.BindView;

public class SportPriceRulesFragment extends BaseFragment<SportPriceRulesPresenter, SportPriceRulesView> implements SportPriceRulesView {

    @BindView(R.id.editTextValuePrice)
    AppCompatEditText editTextValuePrice;
    @BindView(R.id.editTextValueDuration)
    AppCompatEditText editTextValueDuration;
    @BindView(R.id.checkBoxSelectRules)
    CheckBox checkBoxSelectRules;
    @BindView(R.id.editTextValuePer)
    AppCompatEditText editTextValuePer;
    @BindView(R.id.editTextValuePart)
    AppCompatEditText editTextValuePart;
    @BindView(R.id.flowLayoutSport)
    FlowLayout flowLayoutSport;
    @BindView(R.id.textViewExpLab)
    AppCompatTextView textViewExpLab;

    private SportPriceRules data;
    private int position = 0;
    private SportPriceRulesPagerAdapter.CallBack callBack;

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_price_rules;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected SportPriceRulesView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        editTextValuePrice.setFilters(new InputFilter[]{new InputFilterMinMaxDouble(0.00, 10000.00)});
        editTextValueDuration.setFilters(new InputFilter[]{new InputFilterMinMax("1", "23")});
        editTextValuePer.setFilters(new InputFilter[]{new InputFilterMinMaxDouble(0.00, 10000.00)});

        editTextValuePrice.setText(data.getPricePerHour());
        editTextValueDuration.setText(data.getMinDuration());

        if (data.getAddPersonPer().isEmpty() || data.getAddPersonPer().equalsIgnoreCase("0.00")) {
            editTextValuePer.setText("");
        } else {
            editTextValuePer.setText(data.getAddPersonPer());
        }

        editTextValuePart.setText(data.getAddPerson());


        checkBoxSelectRules.setChecked(data.getIsGroupBooking().equalsIgnoreCase("1"));

        setCheckboxAction(data.getIsGroupBooking().equalsIgnoreCase("1"));

        setHasTag();

        textViewExpLab.setText(textViewExpLab.getText().toString().replace("###", data.getName().toLowerCase()));

        checkBoxSelectRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.setIsGroupBooking((checkBoxSelectRules.isChecked()) ? "1" : "0");

                setCheckboxAction(checkBoxSelectRules.isChecked());

                if (!checkBoxSelectRules.isChecked()) {
                    data.setAddPersonPer("");
                    data.setAddPerson("");
                    editTextValuePer.setText(data.getAddPersonPer());
                    editTextValuePart.setText(data.getAddPerson());
                } else {
                    if (data.getAddPersonPer().isEmpty() || data.getAddPersonPer().equalsIgnoreCase("0.00")) {
                        editTextValuePer.setText("");
                    } else {
                        editTextValuePer.setText(data.getAddPersonPer());
                    }
                }

                sendCallback();
            }
        });

        editTextValuePrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                data.setPricePerHour(editTextValuePrice.getText().toString());
                sendCallback();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        editTextValueDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                data.setMinDuration(editTextValueDuration.getText().toString());
                sendCallback();

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        editTextValuePer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                data.setAddPersonPer(editTextValuePer.getText().toString());
                sendCallback();

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        editTextValuePart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                data.setAddPerson(editTextValuePart.getText().toString());
                sendCallback();

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


    }

    private void setHasTag() {
        flowLayoutSport.removeAllViews();

        for (int i = 0; i < data.getSports().size(); i++) {
            SportPriceRulesSport sport = data.getSports().get(i);

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.row_local_select_sport, null);

            final AppCompatTextView appCompatTextViewName = view.findViewById(R.id.customTextViewName);
            appCompatTextViewName.setText(sport.getName());
            appCompatTextViewName.setTag(i);
            appCompatTextViewName.setSelected(sport.getIsPrice().equalsIgnoreCase("1"));

            appCompatTextViewName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //hasTag.remove(((AppCompatTextView) view.findViewById(R.id.customTextViewName)).getText().toString());
                    //          setHasTag();
                    appCompatTextViewName.setSelected(!appCompatTextViewName.isSelected());
                    int position = (int) appCompatTextViewName.getTag();

                    SportPriceRulesSport sportPriceRulesSportS = data.getSports().get(position);
                    sportPriceRulesSportS.setIsPrice(appCompatTextViewName.isSelected() ? "1" : "0");
                    data.getSports().set(position, sportPriceRulesSportS);
                }
            });

            flowLayoutSport.addView(view);
        }

    }


    public void setData(SportPriceRules data) {
        this.data = data;
    }

    private void setCheckboxAction(boolean isSelect) {

        editTextValuePer.setSelected(isSelect);
        editTextValuePart.setSelected(isSelect);

        editTextValuePer.setEnabled(isSelect);
        editTextValuePart.setEnabled(isSelect);

    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setCallBack(SportPriceRulesPagerAdapter.CallBack callBack) {
        this.callBack = callBack;
    }

    private void sendCallback() {
        if (data != null) {
            callBack.setData(position, data);
        }
    }
}
