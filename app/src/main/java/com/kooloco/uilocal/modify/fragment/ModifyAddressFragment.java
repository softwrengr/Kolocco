package com.kooloco.uilocal.modify.fragment;
/**
 * Created by hlink on 20/1/18.
 */

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Order;
import com.kooloco.model.ReceiverData;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.PlaceAutocompleteAdapter;
import com.kooloco.uilocal.modify.presenter.ModifyAddressPresenter;
import com.kooloco.uilocal.modify.view.ModifyAddressView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * Created by hlink on 8/1/18.
 */

public class ModifyAddressFragment extends BaseFragment<ModifyAddressPresenter, ModifyAddressView> implements ModifyAddressView, OnMapReadyCallback {

    @BindView(R.id.customEditTextAddress)
    AppCompatEditText customEditTextAddress;
    private boolean isLocation;
    private Order order;
    GoogleMap gMap;
    SupportMapFragment mapFragment;
    @BindView(R.id.map)
    FrameLayout map;


    protected GoogleApiClient mGoogleApiClient;


    private PlaceAutocompleteAdapter mAdapter;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    LatLng latLng1;

    double latitudeNew = 0.00;
    double longitudeNew = 0.00;
    Marker marker;

    float ZOOM_LEVEL = 14.0f;

    @Override
    protected int createLayout() {
        return R.layout.fragment_modify_location;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ModifyAddressView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        customEditTextAddress.setText(order.getMeetingAddress());

        if (!order.getMeetingLatitude().isEmpty()) {
            latitudeNew = Double.parseDouble(order.getMeetingLatitude());
        }

        if (!order.getMeetingLongitude().isEmpty()) {
            longitudeNew = Double.parseDouble(order.getMeetingLongitude());
        }


        openMap();

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
    public void setIsLocation(boolean isLocation) {
        this.isLocation = isLocation;
    }

    @Override
    public void setOrder(Order order) {

        this.order = order;

    }


    @OnClick({R.id.imageViewBack, R.id.buttonSetLocation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                getActivity().finish();
                break;
            case R.id.buttonSetLocation:
                presenter.callWs(isLocation, order, customEditTextAddress.getText().toString(), "" + latitudeNew, "" + longitudeNew, new ModifyAddressPresenter.CallBack() {
                    @Override
                    public void onSucess() {
                        if (isLocation) {
                            getActivity().finish();
                        } else {
                            goBack();
                        }
                    }
                });
                break;
        }
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
        mapFragment.getMapAsync(ModifyAddressFragment.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;


        gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.map));

        addPin();


        gMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng latLng = gMap.getCameraPosition().target;

                latitudeNew = latLng.latitude;
                longitudeNew = latLng.longitude;
                ZOOM_LEVEL = gMap.getCameraPosition().zoom;

                //  addPin();
                getAddressLetLong(latitudeNew, longitudeNew, "");

            }
        });


        setUpMap(gMap);

        //  drawCircle(gMap, latLng1);





  /*      gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
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
*/

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, ZOOM_LEVEL));

    }

    @OnClick(R.id.imageChat)
    public void onClickChat() {
        ReceiverData receiverData = new ReceiverData();
        receiverData.setUser_id(order.getUserId());
        receiverData.setName(order.getFirstname() + " " + order.getLastname());
        receiverData.setImageUrl(order.getProfileImage());
        receiverData.setDeviceType("A");
        receiverData.setDeviceToken("123");
        receiverData.setOrderId(order.getId());

        presenter.openChat(receiverData);
    }
}
