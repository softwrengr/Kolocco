package com.kooloco.uilocal.expereince.fragment;
/**
 * Created by hlink on 17/4/18.
 */

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

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
import com.kooloco.R;
import com.kooloco.core.Session;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ExperienceResponse;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.PlaceAutocompleteAdapter;
import com.kooloco.uilocal.expereince.presenter.ExperienceMeetingSpotPresenter;
import com.kooloco.uilocal.expereince.view.ExperienceMeetingSpotView;
import com.kooloco.util.LocationManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * Created by hlink on 8/1/18.
 */

public class ExperienceMeetingSpotFragment extends BaseFragment<ExperienceMeetingSpotPresenter, ExperienceMeetingSpotView> implements ExperienceMeetingSpotView, OnMapReadyCallback {
    GoogleMap gMap;
    SupportMapFragment mapFragment;
    @BindView(R.id.map)
    FrameLayout map;

    @BindView(R.id.customEditTextAddress)
    AppCompatAutoCompleteTextView customEditTextAddress;
    Unbinder unbinder;

    private PlaceAutocompleteAdapter mAdapter;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    LatLng latLng1;

    double latitudeNew = 0.00;
    double longitudeNew = 0.00;
    String mettingCity = "";
    String mettingState = "";
    String mettingCountry = "";

    float ZOOM_LEVEL = 14.0f;

    @BindView(R.id.buttonSetLocation)
    AppCompatButton buttonSetLocation;

    private boolean isEdit = false;

    @Inject
    Session session;
    private String expId = "";
    private ExpereinceNew experienceResponse;

    @Override
    protected int createLayout() {
        return R.layout.exp_local_fragment_meeting_spot;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ExperienceMeetingSpotView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        openMap();
        if (isEdit) {
            buttonSetLocation.setText(getActivity().getResources().getString(R.string.button_done));
        } else {
            buttonSetLocation.setText(getActivity().getResources().getString(R.string.button_set_location));
        }

        customEditTextAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
    }

    private void openMap() {
        mapFragment = new SupportMapFragment();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.map, mapFragment);
        fragmentTransaction.addToBackStack(SupportMapFragment.class.getName());
        fragmentTransaction.commit();
        mapFragment.getMapAsync(ExperienceMeetingSpotFragment.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;


        gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.map));

        setUpMap(gMap);

        //  drawCircle(gMap, latLng1);

        ((BaseActivity) getActivity()).getLocationManager().triggerLocation(true, new LocationManager.LocationListener() {
            @Override
            public void onLocationAvailable(LatLng latLng) {
                BaseFragment.latitude = latLng.latitude;
                BaseFragment.longitude = latLng.longitude;
            }

            @Override
            public void onFail(Status status) {

            }
        });

        if (experienceResponse == null) {

            latitudeNew = BaseFragment.latitude;
            longitudeNew = BaseFragment.longitude;

            mettingState = "";
            mettingCity = "";
            mettingCountry = "";

        } else {

            try {
                latitudeNew = Double.parseDouble(experienceResponse.getActivityLatitude());
                longitudeNew = Double.parseDouble(experienceResponse.getActivityLongitude());

                mettingState = experienceResponse.getState();
                mettingCity = experienceResponse.getCity();
                mettingCountry = experienceResponse.getCountry();

                customEditTextAddress.setText(experienceResponse.getActivityAddress());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                latitudeNew = BaseFragment.latitude;
                longitudeNew = BaseFragment.longitude;

            }
        }


        addPin();

        gMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                LatLng target = gMap.getCameraPosition().target;
                latitudeNew = target.latitude;
                longitudeNew = target.longitude;

                ZOOM_LEVEL = gMap.getCameraPosition().zoom;

                //addPin();
                getAddressLetLong(latitudeNew, longitudeNew, "");

            }
        });


    }

    public void setUpMap(final GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
    }

    private void addPin() {
        latLng1 = new LatLng(latitudeNew, longitudeNew);
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

                    mettingCity = "" + addresses.get(0).getLocality();
                    mettingState = "" + addresses.get(0).getAdminArea();
                    mettingCountry = "" + addresses.get(0).getCountryName();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return;
            }

            if (address.size() != 0) {
                Address location = address.get(0);
                location.getLatitude();
                location.getLongitude();

                BaseFragment.latitude = location.getLatitude();
                BaseFragment.longitude = location.getLongitude();
                latitudeNew = BaseFragment.latitude;
                longitudeNew = BaseFragment.longitude;

                mettingCity = "" + location.getLocality();
                mettingState = "" + location.getAdminArea();
                mettingCountry = "" + location.getCountryName();

                customEditTextAddress.setText(strAddress);
                addPin();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    @Override
    public void setExpId(String expId) {
        this.expId = expId;
    }

    @Override
    public void setExpResponse(ExpereinceNew expResponse) {
        this.experienceResponse = expResponse;
    }

    @OnClick({R.id.imageViewBack, R.id.buttonSetLocation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.buttonSetLocation:
                if (customEditTextAddress.getText().toString().isEmpty()) {
                    showMessage(getString(R.string.val_set_meeting_spot_location));
                    return;
                }
                presenter.callWs(expId, customEditTextAddress.getText().toString().trim(), "" + latitudeNew, "" + longitudeNew, "", mettingCity, mettingState, mettingCountry, isEdit);
                break;
        }
    }

    @Override
    public void onStart() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onStart();
    }

    @Override
    public void onStop() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onStop();
    }
}
