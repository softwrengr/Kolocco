package com.kooloco.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


/**
 * Created by Android Dev team  on 27/9/16.
 */


public class LocationManager implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<LocationSettingsResult> {


    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private static final int REQUEST_CHECK_SETTINGS = 111;
    private static final int REQUEST_CHECK_PERMISSION = 222;
    // Location updates intervals in sec
    private static final int UPDATE_INTERVAL = 3000; // 3 sec
    private static final int FATEST_INTERVAL = 1000; // 1 sec
    private static final int DISPLACEMENT = 10; // 10 meters


    private GoogleApiClient mGoogleApiClient;
    private AppCompatActivity activity;
    private LocationRequest mLocationRequest;
    private boolean isFreshLocation = false;

    private LocationListener locationListener;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private boolean isOneTime = true;


    private LocationManager() {

    }

    public static LocationManager newInstance(AppCompatActivity activity) {

        LocationManager locationManager = new LocationManager();
        locationManager.setActivity(activity);
        return locationManager;
    }


    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void triggerLocation(LocationListener locationListener) {
        isOneTime = true;
        this.locationListener = locationListener;
        init();
    }

    public void triggerLocation(boolean isFreshLocation,LocationListener locationListener) {
        isOneTime = true;
        this.isFreshLocation = isFreshLocation;
        this.locationListener = locationListener;
        init();
    }


    public void getLocationUpdate(LocationListener locationListener) {
        isOneTime = false;
        isFreshLocation = true;
        this.locationListener = locationListener;
        init();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean requestPermission() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CHECK_PERMISSION);
        } else if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CHECK_PERMISSION);
        } else if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CHECK_PERMISSION);
        } else {
            return true;
        }
        return false;
    }

    private void init() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                    onLocationChanged(location);
                }
            }
        };
        if (checkPlayServices()) {

            if (Build.VERSION.SDK_INT >= 23 && requestPermission()) {
                connectToClient();
            } else {
                connectToClient();
            }

        } else {

            if (locationListener != null)
                locationListener.onFail(LocationListener.Status.NO_PLAY_SERVICE);
        }
    }

    private void connectToClient() {
        buildGoogleApiClient();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }


    /**
     * Creating google api client object
     */
    public synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApiIfAvailable(LocationServices.API).build();
    }

    /**
     * Creating location request object
     */
    protected void createLocationRequest() {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(isFreshLocation ? LocationRequest.PRIORITY_HIGH_ACCURACY : LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);

    }


    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        if (mFusedLocationClient != null)
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }


    /**
     * Method to display the location on UI
     */

    public void getLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        if (isFreshLocation) {
            checkLocationEnableAndStartUpdate(true);
        } else {
            if (!checkPermission()) {
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object
                                    double latitude = location.getLatitude();
                                    double longitude = location.getLongitude();
                                    Log.e("LAST Location ", +latitude + " : " + longitude);
                                    if (locationListener != null)
                                        locationListener.onLocationAvailable(new LatLng(latitude, longitude));
                                    if (isOneTime)
                                        stop();
                                } else {
                                    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                    checkLocationEnableAndStartUpdate(true);
                                }
                            }
                        });

            }


        }
    }

    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return true;
        }
        return false;
    }


    /**
     * Method to verify google play services on the device
     */
    private boolean checkPlayServices() {

        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(activity);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(activity, result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }

            return false;
        }
        return true;
    }

    public void checkLocationEnableAndStartUpdate(final boolean startUpdate) {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(activity);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(activity, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                if (startUpdate) {
                    if (!checkPermission() && mFusedLocationClient != null)
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                } else
                    getLocation();
            }
        });

        task.addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case CommonStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(activity,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }
        });


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e("Location Manager", "onConnected");
        createLocationRequest();
        checkLocationEnableAndStartUpdate(false);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("Location Manager", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.e("Location Manager", "onConnectionFailed ");
    }


    public void onLocationChanged(Location location) {
        if (isOneTime)
            stop();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        Log.e("Location Manager", "onLocationChanged " + location.getLatitude() + " : " + location.getLongitude());
        if (locationListener != null)
            locationListener.onLocationAvailable(new LatLng(location.getLatitude(), location.getLongitude()));

    }

    public void stop() {
        stopLocationUpdates();
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
    }


    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {

        Status status = locationSettingsResult.getStatus();
        Log.e("Location Manager", "Location Setting " + status.hasResolution());
        if (status.hasResolution()) {
            Toast.makeText(activity, "Please Enable the Location service ", Toast.LENGTH_SHORT).show();
            try {
                status.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }

        } else {
            getLocation();
        }


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHECK_SETTINGS) {

            if (resultCode == Activity.RESULT_OK) {
                getLocation();
            } else {

                if (locationListener != null)
                    locationListener.onFail(LocationListener.Status.DENIED_LOCATION_SETTING);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CHECK_PERMISSION) {

            // We have requested multiple permissions for contacts, so all of them need to be
            // checked.
            if (PermissionUtil.verifyPermissions(grantResults)) {
                // All required permissions have been granted, display contacts fragment.
                connectToClient();
            } else {

                if (locationListener != null)
                    locationListener.onFail(LocationListener.Status.PERMISSION_DENIED);

            }

        }
    }

    public interface LocationListener {

        void onLocationAvailable(LatLng latLng);

        void onFail(Status status);

        enum Status {
            PERMISSION_DENIED, NO_PLAY_SERVICE, DENIED_LOCATION_SETTING
        }
    }
}