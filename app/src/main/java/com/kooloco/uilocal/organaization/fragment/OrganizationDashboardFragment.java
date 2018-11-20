package com.kooloco.uilocal.organaization.fragment;
/**
 * Created by hlink44 on 11/10/17.
 */

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Order;
import com.kooloco.model.OrderOrg;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.organaization.adapter.OrganizationOrderAdapter;
import com.kooloco.uilocal.organaization.presenter.OrganizationDashboardPresenter;
import com.kooloco.uilocal.organaization.view.OrganizationDashboardView;
import com.kooloco.util.LocationManager;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class OrganizationDashboardFragment extends BaseFragment<OrganizationDashboardPresenter, OrganizationDashboardView> implements OrganizationDashboardView {


    @BindView(R.id.calendarViewAvailability)
    MaterialCalendarView calendarViewAvailability;
    @BindView(R.id.textViewDays)
    AppCompatTextView textViewDays;
    @BindView(R.id.textViewMonth)
    AppCompatTextView textViewMonth;
    @BindView(R.id.textViewLocalValue)
    AppCompatTextView textViewLocalValue;
    @BindView(R.id.recyclerOrganization)
    RecyclerView recyclerOrganization;
    @BindView(R.id.textViewNoData)
    AppCompatTextView textViewNoData;
    private OrganizationDetails orgData;

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_organization_dashboard;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected OrganizationDashboardView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        Calendar calendar = Calendar.getInstance();
        calendarViewAvailability.setCurrentDate(calendar);
        calendarViewAvailability.setSelectedDate(calendar);
        textViewDays.setText("" + calendar.get(Calendar.DATE));
        textViewMonth.setText(new SimpleDateFormat("MMM").format(calendar.getTime()));


        presenter.getData(orgData.getId(), new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));

        calendarViewAvailability.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                textViewDays.setText("" + date.getCalendar().get(Calendar.DATE));
                textViewMonth.setText(new SimpleDateFormat("MMM").format(date.getCalendar().getTime()));
                //              textViewMonth.setText(date.getMonth());
                presenter.getData(orgData.getId(), new SimpleDateFormat("yyyy-MM-dd").format(date.getCalendar().getTime()));

            }
        });
    }

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        goBack();
    }

    @Override
    public void setData(List<OrderOrg> ordersComplate) {

        textViewLocalValue.setText("(" + ordersComplate.size() + ")");
        recyclerOrganization.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerOrganization.setAdapter(new OrganizationOrderAdapter(getActivity(), ordersComplate, new OrganizationOrderAdapter.CallBack() {
            @Override
            public void onClickRow(Order order) {

            }

            @Override
            public void onClickImage(String profileImage) {
                imageOpenZoom(profileImage);
            }

            @Override
            public void onClickLocalDetails(String localId) {

                presenter.openDashboardDetails(localId, true);

            }
        }));

        if (textViewNoData != null) {
            textViewNoData.setVisibility((ordersComplate.size() == 0) ? View.VISIBLE : View.GONE);
        }

    }

    @Override
    public void setOrgData(OrganizationDetails orgData) {
        this.orgData = orgData;
    }
}
