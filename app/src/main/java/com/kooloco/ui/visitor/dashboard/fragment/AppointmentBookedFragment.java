package com.kooloco.ui.visitor.dashboard.fragment;
/**
 * Created by hlink44 on 22/9/17.
 */


import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.dashboard.presenter.AppointmentBookedPresenter;
import com.kooloco.ui.visitor.dashboard.view.AppointmentBookedView;

import butterknife.BindView;
import butterknife.OnClick;


public class AppointmentBookedFragment extends BaseFragment<AppointmentBookedPresenter, AppointmentBookedView> implements AppointmentBookedView {


    @BindView(R.id.textViewNoData)
    AppCompatTextView textViewNoData;

    @Override
    protected int createLayout() {
        return R.layout.fragment_appointment_booked;
    }

    String expId = "";

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected AppointmentBookedView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        String s = getString(R.string.your_made_it) + " " + getString(R.string.enjoy_your_experience);

        Spannable wordtoSpan = new SpannableString(s);

        int startIndex=s.indexOf(getString(R.string.enjoy_your_experience));

        wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.greyText)), startIndex, startIndex+getString(R.string.enjoy_your_experience).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textViewNoData.setText(wordtoSpan);
    }

    @OnClick({R.id.buttonOkGreat, R.id.textViewBookAnotherDay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buttonOkGreat:
                getActivity().finish();
                break;
            case R.id.textViewBookAnotherDay:
                if (!expId.isEmpty()) {
                    presenter.openExpBookedScreen(expId);
                }
                break;
        }
    }

    @Override
    protected boolean onBackActionPerform() {
        return false;
    }

    @Override
    public void setExpId(String expId) {
        this.expId = expId;
    }
}
