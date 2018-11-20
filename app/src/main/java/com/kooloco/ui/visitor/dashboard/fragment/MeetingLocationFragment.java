package com.kooloco.ui.visitor.dashboard.fragment;
/**
 * Created by hlink44 on 21/9/17.
 */

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.ReceiverData;
import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.dashboard.presenter.MeetingLocationPresenter;
import com.kooloco.ui.visitor.dashboard.view.MeetingLocationView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class MeetingLocationFragment extends BaseFragment<MeetingLocationPresenter, MeetingLocationView> implements MeetingLocationView, OnMapReadyCallback {
    GoogleMap gMap;
    SupportMapFragment mapFragment;
    @BindView(R.id.map)
    FrameLayout map;
    @BindView(R.id.customEditTextSetName)
    AppCompatTextView customEditTextSetName;
    @BindView(R.id.checkboxChoose)
    AppCompatCheckBox checkboxChoose;

    @BindView(R.id.customEditTextAddress)
    AppCompatEditText customEditTextAddress;

    @BindView(R.id.buttonSetLocation)
    AppCompatButton buttonSetLocation;
    @BindView(R.id.imageViewPin)
    ImageView imageViewPin;
    private VisitorBooking visitorBooking;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    LatLng latLng1;
    double latitudeNew = 0.00;
    double longitudeNew = 0.00;
    Marker marker;

    boolean isSetData = false;

    float ZOOM_LEVEL = 14.0f;

    @Override
    protected int createLayout() {
        return R.layout.fragment_meeting_location;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected MeetingLocationView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        isSetData = true;

        visitorBooking.setLocalAddress(visitorBooking.getLocalAddressOld());
        visitorBooking.setLocalLati(visitorBooking.getLocalLatiOld());
        visitorBooking.setLocalLang(visitorBooking.getLocalLangOld());

        String text = "" + getActivity().getResources().getString(R.string.meeting_location_let).replace("***", "<br>");
        text = text.replace("###", "<font color='" + getActivity().getResources().getColor(R.color.buttonColor) + "'> " + visitorBooking.getLocalName() + " </font>");
        customEditTextSetName.setText(Html.fromHtml(text));
        customEditTextAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSetData = false;
                if (!checkboxChoose.isChecked()) {
                    try {
                        Intent intent =
                                new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                        .build(getActivity());
                        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                    } catch (GooglePlayServicesRepairableException e) {
                        // TODO: Handle the error.
                    } catch (GooglePlayServicesNotAvailableException e) {
                        // TODO: Handle the error.
                    }

                }

            }
        });

        latitudeNew = Double.parseDouble(visitorBooking.getLocalLati());
        longitudeNew = Double.parseDouble(visitorBooking.getLocalLang());

        openMap();

        customEditTextAddress.setEnabled(!checkboxChoose.isChecked());

        customEditTextAddress.setVisibility(checkboxChoose.isChecked() ? View.GONE : View.VISIBLE);

        imageViewPin.setVisibility(!checkboxChoose.isChecked() ? View.VISIBLE : View.GONE);

        customEditTextAddress.setText(visitorBooking.getLocalAddress());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isSetData) {
            customEditTextAddress.setText(visitorBooking.getLocalAddress());
        }
    }

    private void openMap() {
        mapFragment = new SupportMapFragment();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.map, mapFragment);
        fragmentTransaction.addToBackStack(SupportMapFragment.class.getName());
        fragmentTransaction.commit();
        mapFragment.getMapAsync(MeetingLocationFragment.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;


        //gMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.map_local_location_icon)).title("Meeting location").draggable(true).position(latLng1));

        //gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 17));
        gMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                if (checkboxChoose != null) {
                    if (!checkboxChoose.isChecked()) {

                        LatLng target = gMap.getCameraPosition().target;
                        latitudeNew = target.latitude;
                        longitudeNew = target.longitude;

                        ZOOM_LEVEL = gMap.getCameraPosition().zoom;

                        //addPin();
                        getAddressLetLong(latitudeNew, longitudeNew, "");
                    }
                }
            }
        });

        addPin();

        gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.map));

        setUpMap(gMap);


     /*   gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (!checkboxChoose.isChecked()) {
                    latitudeNew = latLng.latitude;
                    longitudeNew = latLng.longitude;

                    addPin();
                    getAddressLetLong(latitudeNew, longitudeNew, "");
                }
                //  getAddressLetLong(latLng.latitude, latLng.latitude);
            }
        });*/

    }

    public void setUpMap(final GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(false);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
           /*     googleMap.clear();

                MarkerOptions marker = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.map_local_location_icon)).title("Location").draggable(true).position(
                        new LatLng(point.latitude, point.longitude)).title("New Marker");

                googleMap.addMarker(marker);*/

            }
        });
    }


    @OnClick(R.id.buttonSetLocation)
    public void onViewClickedNext() {

        if (!checkboxChoose.isChecked()) {
            visitorBooking.setLocalLati("" + latitudeNew);
            visitorBooking.setLocalLang("" + longitudeNew);
            visitorBooking.setLocalAddress("" + customEditTextAddress.getText().toString().trim());
        }

        visitorBooking.setLocalSelectAddress(checkboxChoose.isChecked());

        presenter.openAddParticipants(visitorBooking);
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
                ReceiverData receiverData = new ReceiverData();
                receiverData.setUser_id(visitorBooking.getLocalId());
                receiverData.setName(visitorBooking.getLocalName());
                receiverData.setImageUrl(visitorBooking.getLocalImage());
                receiverData.setDeviceType("A");
                receiverData.setDeviceToken("123");

                presenter.opnChat(receiverData);
                break;
        }
    }

    @OnClick(R.id.linearLayoutCheckBox)
    public void onViewClicked() {
        checkboxChoose.setChecked(!checkboxChoose.isChecked());
        customEditTextAddress.setEnabled(!checkboxChoose.isChecked());

        customEditTextAddress.setVisibility(checkboxChoose.isChecked() ? View.GONE : View.VISIBLE);

        imageViewPin.setVisibility(!checkboxChoose.isChecked() ? View.VISIBLE : View.GONE);
        buttonSetLocation.setText(!checkboxChoose.isChecked() ? "" + getActivity().getResources().getString(R.string.button_set_location) : "" + getActivity().getResources().getString(R.string.button_next));

    }

    @Override
    public void setBookingData(VisitorBooking visitorBooking) {
        this.visitorBooking = visitorBooking;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                latitudeNew = place.getLatLng().latitude;
                longitudeNew = place.getLatLng().longitude;
                ZOOM_LEVEL = gMap.getCameraPosition().zoom;
                addPin();

                getAddressLetLong(place.getLatLng().latitude, place.getLatLng().longitude, place.getAddress().toString());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private void addPin() {

        latLng1 = new LatLng(latitudeNew, longitudeNew);

/*
        if (marker != null) {
            marker.remove();
        }
        marker = gMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.map_current_location_icon)).title("Meeting location").draggable(true).position(latLng1));
*/

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, ZOOM_LEVEL));

    }

    private void getAddressLetLong(double lat, double longitude, String name) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(lat, longitude, 1);
            if (addresses != null) {
                if (addresses.size() > 0) {

                    String address = "" + addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String address2 = "" + addresses.get(0).getAddressLine(1);
                    String city = "" + addresses.get(0).getLocality();
                    String state = "" + addresses.get(0).getAdminArea();
                    String country = "" + addresses.get(0).getCountryName();
                    String postalCode = "" + addresses.get(0).getPostalCode();
                    String knownName = "" + addresses.get(0); //
                    String setAddressAutoCompletTextview = "";

                    if (!address.trim().isEmpty() && !address.equals("null")) {
                        setAddressAutoCompletTextview = setAddressAutoCompletTextview + address;
                    }

                    if (name.isEmpty()) {
                        customEditTextAddress.setText(setAddressAutoCompletTextview);
                    } else {
                        customEditTextAddress.setText(name);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
