package com.kooloco.ui.visitor.organizationDetails.fragment;
/**
 * Created by hlink44 on 19/9/17.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.DashboardDetails;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.organizationDetails.presenter.LocalVisitorMapPresenter;
import com.kooloco.ui.visitor.organizationDetails.view.LocalVisitorMapView;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LocalVisitorMapFragment extends BaseFragment<LocalVisitorMapPresenter, LocalVisitorMapView> implements LocalVisitorMapView, OnMapReadyCallback {
    GoogleMap gMap;
    SupportMapFragment mapFragment;
    @BindView(R.id.map)
    FrameLayout map;
    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;

    @BindView(R.id.textViewAddress)
    AppCompatTextView textViewAddress;

    private DashboardDetails dashboardDetails;

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_visitor_location;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected LocalVisitorMapView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        openMap();
        if (dashboardDetails.getLocalName() != null) {
            toolbarTitle.setText(dashboardDetails.getLocalName());
        } else {
            toolbarTitle.setText("");
        }
        if (dashboardDetails.getImageUrl() != null) {
            Picasso.with(getActivity()).load(dashboardDetails.getImageUrl()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);
        } else {

            Picasso.with(getActivity()).load(R.drawable.user_round).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);
        }

        if (dashboardDetails.getLocalLocation() != null) {
            textViewAddress.setText(dashboardDetails.getLocalLocation());
        } else {
            textViewAddress.setText("");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void openMap() {
        mapFragment = new SupportMapFragment();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.map, mapFragment);
        fragmentTransaction.addToBackStack(SupportMapFragment.class.getName());
        fragmentTransaction.commit();
        mapFragment.getMapAsync(LocalVisitorMapFragment.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        LatLng latLngCurrent = new LatLng(BaseFragment.latitude, BaseFragment.longitude);


        double latitude1 = 0, longitude1 = 0;

        try {
            latitude1 = Double.parseDouble(dashboardDetails.getLatitude());
        } catch (Exception e) {
        }

        try {
            longitude1 = Double.parseDouble(dashboardDetails.getLongitude());
        } catch (Exception e) {
        }

        LatLng latLng1 = new LatLng(latitude1, longitude1);


        gMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.map_local_location_icon)).position(latLng1));


        gMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.my_pin)).title("My current location").draggable(true).position(latLngCurrent));

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 14));

        gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.map));

        setUpMap(gMap);
        //drawCircle(gMap, latLng1);
        addPulsatingEffect(latLng1, gMap, true);
    }


    public void setUpMap(final GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if (marker.getTitle() == null) {
                    openGmapIntent(marker.getPosition());
                } else {
                    if (!marker.getTitle().equalsIgnoreCase("My current location")) {
                        openGmapIntent(marker.getPosition());
                    }
                }
                return true;
            }
        });
    }

    private void openGmapIntent(LatLng position) {
        String uriString = "google.navigation:q=" + position.latitude + "," + position.longitude;
        Uri gmmIntentUri = Uri.parse(uriString);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    private void drawCircle(GoogleMap map, LatLng point) {

        // Instantiating CircleOptions to draw a circle around the marker
        CircleOptions circleOptions = new CircleOptions();

        // Specifying the center of the circle
        circleOptions.center(point);

        // Radius of the circle
        circleOptions.radius(50);

        // Border color of the circle
        circleOptions.strokeColor(getActivity().getResources().getColor(R.color.buttonColor));

        // Fill color of the circle
        circleOptions.fillColor(getActivity().getResources().getColor(R.color.buttonColorTra));

        // Border width of the circle
        circleOptions.strokeWidth(2);

        // Adding the circle to the GoogleMap
        map.addCircle(circleOptions);

    }

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        presenter.onBack();
    }

    @Override
    public void setData(DashboardDetails dashboardDetails) {
        this.dashboardDetails = dashboardDetails;
    }

}
