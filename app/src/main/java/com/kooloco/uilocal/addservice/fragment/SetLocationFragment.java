package com.kooloco.uilocal.addservice.fragment;
/**
 * Created by hlink44 on 5/10/17.
 */

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kooloco.R;
import com.kooloco.core.Session;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.DashboardDetails;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.PlaceAutocompleteAdapter;
import com.kooloco.uilocal.addservice.presenter.SetLocationPresenter;
import com.kooloco.uilocal.addservice.view.SetLocationView;
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

public class SetLocationFragment extends BaseFragment<SetLocationPresenter, SetLocationView> implements SetLocationView, OnMapReadyCallback {
    GoogleMap gMap;
    SupportMapFragment mapFragment;
    @BindView(R.id.map)
    FrameLayout map;
    Unbinder unbinder;

    protected GoogleApiClient mGoogleApiClient;
    @BindView(R.id.customEditTextAddress)
    AppCompatAutoCompleteTextView customEditTextAddress;
    @BindView(R.id.editTextValueNoExpand)
    AppCompatEditText editTextValueNoExpand;

    private PlaceAutocompleteAdapter mAdapter;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    LatLng latLng1;

    double latitudeNew = 0.00;
    double longitudeNew = 0.00;
    String mettingCity = "";
    String mettingState = "";
    String mettingCountry = "";

    Marker marker;
    float ZOOM_LEVEL = 14.0f;

    @BindView(R.id.buttonSetLocation)
    AppCompatButton buttonSetLocation;

    private Circle lastUserCircle;
    private long pulseDuration = 3000;
    private ValueAnimator lastPulseAnimator;
    private boolean isEdit = false;
    private DashboardDetails dashboardDetails;

    @Inject
    Session session;

    @Override
    protected int createLayout() {
/*        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), 2, this)
                .addApi(Places.GEO_DATA_API)
                .build();*/
        return R.layout.fragment_local_set_location;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected SetLocationView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        openMap();

        if (isEdit) {
            buttonSetLocation.setText(getActivity().getResources().getString(R.string.button_done));
            customEditTextAddress.setText(dashboardDetails.getLocalLocation());
            editTextValueNoExpand.setText(dashboardDetails.getActivityArea());
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
    /*    mAdapter = new PlaceAutocompleteAdapter(getActivity(), mGoogleApiClient, null, null);


        customEditTextAddress.setAdapter(mAdapter);

        customEditTextAddress.setOnItemClickListener(mAutocompleteHomeLocation);
    */
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private AdapterView.OnItemClickListener mAutocompleteHomeLocation
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);
            Log.i("", "Autocomplete item selected: " + primaryText);

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceHomeDetailsCallback);

            Log.i("", "Called getPlaceById to get Place details for " + placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceHomeDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
// Request did not complete successfully
                Log.e("", "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
// Get the Place object from the buffer.
            final Place place = places.get(0);

            getAddressLetLong(place.getLatLng().latitude, place.getLatLng().longitude, place.getAddress().toString());

  /*          mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                    place.getId(), place.getAddress(), place.getPhoneNumber(), place.getLatLng().toString()));
  */
            places.release();
        }
    };


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

    private void openMap() {
        mapFragment = new SupportMapFragment();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.map, mapFragment);
        fragmentTransaction.addToBackStack(SupportMapFragment.class.getName());
        fragmentTransaction.commit();
        mapFragment.getMapAsync(SetLocationFragment.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;


        gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.map));

        setUpMap(gMap);

        //  drawCircle(gMap, latLng1);

        if (isEdit) {

            if (!dashboardDetails.getLatitude().isEmpty()) {
                BaseFragment.latitude = Double.parseDouble(dashboardDetails.getLatitude());
            }
            if (!dashboardDetails.getLongitude().isEmpty()) {
                BaseFragment.longitude = Double.parseDouble(dashboardDetails.getLongitude());
            }

            latitudeNew = BaseFragment.latitude;
            longitudeNew = BaseFragment.longitude;

            mettingState = dashboardDetails.getLocalState();
            mettingCity = dashboardDetails.getLocalCity();
            mettingCountry = dashboardDetails.getLocalCountry();

            addPin();

        } else {

            latitudeNew = BaseFragment.latitude;
            longitudeNew = BaseFragment.longitude;

            mettingState = "";
            mettingCity = "";
            mettingCountry = "";
            addPin();

            //getLocationFromAddress(session.getUser().getAddress());
        }


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


      /*  gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                latitudeNew = latLng.latitude;
                longitudeNew = latLng.longitude;
                addPin();

                getAddressLetLong(latitudeNew, longitudeNew, "");

                //  getAddressLetLong(latLng.latitude, latLng.latitude);
            }
        });*/
    }

    public void setUpMap(final GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
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

    @OnClick({R.id.imageViewBack, R.id.buttonSetLocation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.buttonSetLocation:
                if (customEditTextAddress.getText().toString().isEmpty()) {
                    showMessage(getString(R.string.val_set_location));
                    return;
                }
                presenter.callWs(customEditTextAddress.getText().toString().trim(), "" + latitudeNew, "" + longitudeNew, editTextValueNoExpand.getText().toString(), mettingCity, mettingState, mettingCountry, isEdit);
                break;
        }
    }

 /*   @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("", "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
// TODO(Developer): Check error code and notify the user of error state and resolution.
        Log.d(":::", "Could not connect to Google API Client: Error " + connectionResult.getErrorCode());

    }*/

    @Override
    public void onStop() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onStop();
/*
        mGoogleApiClient.stopAutoManage(getActivity());
*/
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
        marker = gMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.map_local_location_icon)).title("Set location").draggable(true).position(latLng1));

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 14));

        addPulsatingEffectNew(latLng1);*/

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, ZOOM_LEVEL));
    }

    private void addPulsatingEffectNew(final LatLng userLatlng) {
        if (lastPulseAnimator != null) {
            lastPulseAnimator.cancel();
            Log.d("onLocationUpdated: ", "cancelled");
        }
        if (lastUserCircle != null)
            lastUserCircle.setCenter(userLatlng);
        lastPulseAnimator = valueAnimateNew(700, pulseDuration, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (lastUserCircle != null)
                    lastUserCircle.setRadius((Float) animation.getAnimatedValue());
                else {
                    lastUserCircle = gMap.addCircle(new CircleOptions()
                            .center(userLatlng)
                            .radius((Float) animation.getAnimatedValue())
                            .strokeColor(Color.TRANSPARENT)
                            .strokeWidth(2)
                            .fillColor(getActivity().getResources().getColor(R.color.buttonColorTra)));
                }
            }
        });

    }


    protected ValueAnimator valueAnimateNew(float accuracy, long duration, ValueAnimator.AnimatorUpdateListener updateListener) {
        Log.d("valueAnimate: ", "called");
        ValueAnimator va = ValueAnimator.ofFloat(0, accuracy);
        va.setDuration(duration);
        va.addUpdateListener(updateListener);
        va.setRepeatCount(ValueAnimator.INFINITE);
        va.setRepeatMode(ValueAnimator.RESTART);

        va.start();
        return va;
    }

    @Override
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    @Override
    public void setDashboardDetails(DashboardDetails dashboardDetails) {
        this.dashboardDetails = dashboardDetails;
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
    public void onStart() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onStart();
    }

}
