package com.kooloco.uilocal.setavability.fragment;
/**
 * Created by hlink44 on 11/10/17.
 */

import android.app.DatePickerDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.DatePicker;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.DisableSport;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.setavability.adapter.DisableSportAdapter;
import com.kooloco.uilocal.setavability.presenter.DisableSportPresenter;
import com.kooloco.uilocal.setavability.view.DisableSportView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DisableSportFragment extends BaseFragment<DisableSportPresenter, DisableSportView> implements DisableSportView {

    @BindView(R.id.recyclerDisableSport)
    RecyclerView recyclerDisableSport;

    List<DisableSport> disableSports = new ArrayList<>();
    DisableSportAdapter disableSportAdapter;


    @Override
    protected int createLayout() {
        return R.layout.fragment_local_diable_sport;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected DisableSportView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        presenter.getData();
    }

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        goBack();
    }

    @Override
    public void setData(final List<DisableSport> disableSportD) {

        disableSports.clear();
        disableSports.addAll(disableSportD);

        disableSportAdapter = new DisableSportAdapter(getActivity(), disableSports, new DisableSportAdapter.CallBack() {
            @Override
            public void onClickStartDate(final int position) {
                hideKeyBoard();
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String date = new SimpleDateFormat("dd MMM, yyyy").format(calendar.getTime());

                        DisableSport disableSport = disableSports.get(position);
                        disableSport.setStartDate(date);
                        disableSport.setSelectStartDate(calendar.getTime());

                        disableSport.setEndDate(date);
                        disableSport.setSelectEndDate(calendar.getTime());

                        disableSports.set(position, disableSport);

                        disableSportAdapter.notifyItemChanged(position);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTime().getTime());
                datePickerDialog.show();

            }

            @Override
            public void onClickEndDate(final int position) {
                hideKeyBoard();
                if (disableSports.get(position).getStartDate().isEmpty()) {
                    showMessage(getString(R.string.val_select_strat_date));
                    return;
                }
                final Calendar calendar = Calendar.getInstance();
                calendar.setTime(disableSportD.get(position).getSelectStartDate());
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String date = new SimpleDateFormat("dd MMM, yyyy").format(calendar.getTime());

                        DisableSport disableSport = disableSports.get(position);
                        disableSport.setEndDate(date);
                        disableSport.setSelectEndDate(calendar.getTime());
                        disableSports.set(position, disableSport);

                        disableSportAdapter.notifyItemChanged(position);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(calendar.getTime().getTime());
                datePickerDialog.show();

            }
        });

        recyclerDisableSport.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerDisableSport.setAdapter(disableSportAdapter);
    }



    @OnClick(R.id.buttonSet)
    public void onViewClickedSet() {
        goBack();
    }


}
