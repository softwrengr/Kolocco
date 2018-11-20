package com.kooloco.ui.visitor.dashboard.fragment;
/**
 * Created by hlink44 on 21/9/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Duration;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.dashboard.adapter.DurationAdapter;
import com.kooloco.ui.visitor.dashboard.presenter.DurationPresenter;
import com.kooloco.ui.visitor.dashboard.view.DurationView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DurationFragment extends BaseFragment<DurationPresenter, DurationView> implements DurationView {

    @BindView(R.id.customTextViewSelectDuration)
    AppCompatEditText customTextViewSelectDuration;
    @BindView(R.id.recyclerDuration)
    RecyclerView recyclerDuration;
    @BindView(R.id.linearLayoutDuration)
    LinearLayout linearLayoutDuration;
    @BindView(R.id.buttonNext)
    AppCompatButton buttonNext;
    @BindView(R.id.checkboxChoose)
    AppCompatCheckBox checkboxChoose;

    @Override
    protected int createLayout() {
        return R.layout.fragment_duration;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected DurationView createView() {
        return this;
    }

    @Override
    public void onResume() {
        super.onResume();
        setSelactionDuration(checkboxChoose.isChecked() ? 1 : 0);
        customTextViewSelectDuration.setEnabled(!checkboxChoose.isChecked());
    }

    @Override
    protected void bindData() {
        //setSelactionDuration(0);


        presenter.getData();
        String text = "" + getActivity().getResources().getString(R.string.duration_let) + "<font color='" + getActivity().getResources().getColor(R.color.buttonColor) + "'> Doris Candiz </font>" + getActivity().getResources().getString(R.string.duration_let_choose);
        checkboxChoose.setText(Html.fromHtml(text));

        checkboxChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelactionDuration(checkboxChoose.isChecked() ? 1 : 0);

                customTextViewSelectDuration.setEnabled(!checkboxChoose.isChecked());
            }
        });
    }

    @OnClick({R.id.customTextViewSelectDuration, R.id.buttonNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.customTextViewSelectDuration:
                setSelactionDuration((linearLayoutDuration.getVisibility() == View.VISIBLE) ? 1 : 0);
                break;
            case R.id.buttonNext:
                presenter.openMettingLocation();
                break;
        }
    }

    @Override
    public void setData(List<Duration> data) {
        recyclerDuration.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerDuration.setAdapter(new DurationAdapter(getActivity(), data));
    }

    private void setSelactionDuration(int position) {
        customTextViewSelectDuration.setSelected(position == 0);
        linearLayoutDuration.setVisibility((position == 0) ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbarIsolatedAppointment(this.getClass().getSimpleName());
    }


    @OnClick({R.id.imageViewBack, R.id.imageChat})
    public void onViewClickedTool(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.imageChat:
                presenter.openChat();
                break;
        }
    }
}
