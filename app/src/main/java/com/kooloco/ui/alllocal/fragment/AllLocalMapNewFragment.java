package com.kooloco.ui.alllocal.fragment;
/**
 * Created by hlink44 on 28/9/17.
 */

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.DrawableRes;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.FilterRequest;
import com.kooloco.model.ServiceType;
import com.kooloco.ui.alllocal.adapter.MapLocalServiceAdapter;
import com.kooloco.ui.alllocal.cluster.MyItem;
import com.kooloco.ui.alllocal.cluster.OwnIconRender;
import com.kooloco.ui.alllocal.presenter.AllLocalMapNewPresenter;
import com.kooloco.ui.alllocal.view.AllLocalMapNewView;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.util.LocationManager;
import com.kooloco.util.picaso.CircleTransform;
import com.rd.PageIndicatorView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kooloco.util.BitmapUtil.drawableToBitmap;


public class AllLocalMapNewFragment extends BaseFragment<AllLocalMapNewPresenter, AllLocalMapNewView> implements AllLocalMapNewView, OnMapReadyCallback {
    GoogleMap gMap;
    SupportMapFragment mapFragment;
    @BindView(R.id.map)
    FrameLayout map;
    List<DashboardDetails> dashboardDetails;

    int page = 1;
    boolean isLoading = false;

    @Inject
    Gson gson;

    @BindView(R.id.textViewAddress)
    AppCompatTextView textViewAddress;

    boolean isApplyFilter = false;
    @BindView(R.id.bottomSheet)
    LinearLayout bottomSheet;
    BottomSheetBehavior sheetBehavior;

    long mLastClickTime = 0;

    @BindView(R.id.linearLayoutMain)
    LinearLayout linearLayoutMain;
    @BindView(R.id.recyclerViewImageSlide)
    RecyclerView recyclerViewImageSlide;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;
    @BindView(R.id.customTextViewName)
    AppCompatTextView customTextViewName;
    @BindView(R.id.recyclerServiceType)
    RecyclerView recyclerServiceType;
    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewRatingValue)
    AppCompatTextView customTextViewRatingValue;
    @BindView(R.id.ratingView)
    FrameLayout ratingView;

    @BindView(R.id.customTextViewDistance)
    AppCompatTextView customTextViewDistance;
    @BindView(R.id.recyclerViewService)
    RecyclerView recyclerViewService;
    @BindView(R.id.linearLayoutFilter)
    LinearLayout linearLayoutFilter;
    @BindView(R.id.imageViewRefrase)
    ImageView imageViewRefrase;

    private Circle lastUserCircle;
    private long pulseDuration = 3000;
    private ValueAnimator lastPulseAnimator;
    private boolean isEdit = false;

    FilterRequest filterRequest = new FilterRequest();
    MarkerOptions current;
    private AnimatorSet overlayAnimatorSet;
    GroundOverlay groundOverlay;

    ClusterManager<MyItem> clusterManager;
    public float ZOOM_LEVEL;

    @Override
    protected int createLayout() {
        return R.layout.fragment_all_location_map;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected AllLocalMapNewView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        sheetBehavior = BottomSheetBehavior.from(bottomSheet);

        if (dashboardDetails == null) {
            dashboardDetails = new ArrayList<>();
        }

        linearLayoutFilter.setVisibility(View.GONE);
        imageViewRefrase.setVisibility(View.GONE);

        openMap();
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
        mapFragment.getMapAsync(AllLocalMapNewFragment.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        lastUserCircle = null;

        lastPulseAnimator = null;
        pulseDuration = 3000;
        isEdit = false;

        clusterManager = new ClusterManager<MyItem>(getActivity(), gMap);

        gMap.setOnCameraIdleListener(clusterManager);
        gMap.setOnMarkerClickListener(clusterManager);

        clusterManager.setRenderer(new OwnIconRender(getActivity(), gMap, clusterManager));

        clusterManager.setAnimation(true);

        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {

            @Override
            public boolean onClusterClick(Cluster<MyItem> cluster) {
                Log.e("::::::", "Click Cluster ");

                return false;
            }
        });

        clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
            @Override
            public boolean onClusterItemClick(MyItem myItem) {
                Log.e("::::::", "Click Cluster item");
                return false;
            }
        });


        LatLng latLngCurrent = new LatLng(BaseFragment.latitude, BaseFragment.longitude);

    /*    List<Marker> markersAnimation = new ArrayList<>();

        for (DashboardDetails dashboardDetail : dashboardDetails) {

            LatLng latLngLocal = new LatLng(Double.parseDouble(dashboardDetail.getLatitude()), Double.parseDouble(dashboardDetail.getLongitude()));

            Marker location = gMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.map_local_location_icon)).title("Location").draggable(true).position(latLngLocal));

            location.setTag(dashboardDetail);
            markersAnimation.add(location);
        }*/

        current = new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.my_pin)).title("Current Location").draggable(true)
                .position(latLngCurrent).anchor(0.52f, 0.52f);

        gMap.addMarker(current);

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngCurrent, 13));

        gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.map));

        setUpMap(gMap);

/*        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if ("Current Location".equalsIgnoreCase(marker.getTitle())) {
                    return true;
                } else {
*//*
                    final DashboardDetails dashboardDetail = (DashboardDetails) marker.getTag();
                    setDataMap(dashboardDetail);
*//*
                    return false;
                }
            }
        });*/

        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        gMap.getUiSettings().setCompassEnabled(true);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        } else {
            // gMap.setMyLocationEnabled(true);
        }

        gMap.getUiSettings().setMyLocationButtonEnabled(true);

        //drawCircle(gMap, latLng1);


        /*gMap.addCircle(new CircleOptions()
                .center(latLngCurrent)
                .fillColor(getActivity().getResources().getColor(R.color.buttonColorTra))
                .radius(250));

        LatLng latLngCurrentNew = new LatLng(BaseFragment.latitude, BaseFragment.longitude);
*/
        addPulsatingEffectNew(latLngCurrent);

/*
        setAnimation(markersAnimation);
*/

        if (presenter != null) {
            page = 1;
            if (isApplyFilter) {
                //  presenter.getDataFilter(page, true, filterRequest);
            } else {
                //presenter.getData(page, true);
            }

            setData();
        }


    }

    private void setAnimation(List<MyItem> MyItems) {


        for (MyItem myItem : MyItems) {
            clusterManager.addItem(myItem);
        }
        clusterManager.cluster();

    }

    public void setUpMap(final GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(false);

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (marker.getTitle() == null) {

                    return;

                }
                if ("Current Location".equalsIgnoreCase(marker.getTitle())) {
                    return;
                }

                DashboardDetails dashboardDetail = new Gson().fromJson(marker.getSnippet(), DashboardDetails.class);

                presenter.openDashBoard(dashboardDetail);
            }
        });

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(final Marker marker) {

                if (marker.getTitle() == null) {


                    return null;

                }
                if ("Current Location".equalsIgnoreCase(marker.getTitle())) {
                    return null;
                }

                int zoom = (int) gMap.getCameraPosition().zoom;
                CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(marker.getPosition().latitude + (double) 100 / Math.pow(2, zoom), marker.getPosition().longitude), zoom);
                gMap.animateCamera(cu);


                final DashboardDetails dashboardDetail = new Gson().fromJson(marker.getSnippet(), DashboardDetails.class);

                View view = LayoutInflater.from(getContext()).inflate(R.layout.map_tip_popup, null);
                final ImageView imageViewProfile = view.findViewById(R.id.imageViewProfile);

                final ImageView imageViewSerivce = view.findViewById(R.id.imageViewSerivce);


                Picasso.with(getActivity()).load(dashboardDetail.getLocalImages().get(0).getLocalImage()).placeholder(R.drawable.place).into(imageViewSerivce, new MarkerCallback(marker));

                AppCompatTextView customTextViewRatingValue = view.findViewById(R.id.customTextViewRatingValue);

                AppCompatTextView customTextViewAddress = view.findViewById(R.id.customTextViewAddress);

                AppCompatTextView customTextViewName = view.findViewById(R.id.customTextViewName);

                RecyclerView recyclerViewService = view.findViewById(R.id.recyclerViewService);


                recyclerViewService.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

                recyclerViewService.setAdapter(new MapLocalServiceAdapter(getActivity(), dashboardDetail.getServices()));

                AppCompatTextView textViewSessionValue = view.findViewById(R.id.textViewSessionValue);
                AppCompatTextView textViewLessonValue = view.findViewById(R.id.textViewLessonValue);
                AppCompatTextView textViewDiscoveryValue = view.findViewById(R.id.textViewDiscoveryValue);

                AppCompatTextView textViewSession = view.findViewById(R.id.textViewSession);
                AppCompatTextView textViewLesson = view.findViewById(R.id.textViewLesson);
                AppCompatTextView textViewDiscovery = view.findViewById(R.id.textViewDiscovery);


                Picasso.with(getActivity()).load(dashboardDetail.getImageUrl()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile, new MarkerCallback(marker));


                customTextViewRatingValue.setText(dashboardDetail.getLocalRating());

                customTextViewRatingValue.setText(dashboardDetail.getLocalRating());
                customTextViewName.setText(dashboardDetail.getLocalName());
                // customTextViewKmValue.setText(dashboardDetail.getLocalDistance() + "");

                AppCompatTextView customTextViewLowestPrice = view.findViewById(R.id.textViewLowestValue);
                AppCompatRatingBar ratingBar = view.findViewById(R.id.ratingBar);


                ratingBar.setRating(Float.parseFloat(dashboardDetail.getLocalRating()));

                customTextViewLowestPrice.setText(getString(R.string.currency) + " " + dashboardDetail.getLowPrice() + getString(R.string.hr_new));

                customTextViewAddress.setText(dashboardDetail.getLocalCity() + ", " + dashboardDetail.getLocalCountry());

                textViewSession.setVisibility(View.GONE);
                textViewSessionValue.setVisibility(View.GONE);

                textViewLesson.setVisibility(View.GONE);
                textViewLessonValue.setVisibility(View.GONE);

                textViewDiscovery.setVisibility(View.GONE);
                textViewDiscoveryValue.setVisibility(View.GONE);

                for (ServiceType serviceType : dashboardDetail.getServicesTypes()) {
                    if (serviceType.getServiceName().equalsIgnoreCase("Session")) {
                        textViewSessionValue.setText(serviceType.getServicePrice() + serviceType.getServicePriceAs());
                        textViewSession.setVisibility(View.VISIBLE);
                        textViewSessionValue.setVisibility(View.VISIBLE);

                    }
                    if (serviceType.getServiceName().equalsIgnoreCase("Lesson")) {
                        textViewLessonValue.setText(serviceType.getServicePrice() + serviceType.getServicePriceAs());
                        textViewLesson.setVisibility(View.VISIBLE);
                        textViewLessonValue.setVisibility(View.VISIBLE);

                    }
                    if (serviceType.getServiceName().equalsIgnoreCase("Discoveries") || serviceType.getServiceName().equalsIgnoreCase("Discovery")) {
                        textViewDiscoveryValue.setText(serviceType.getServicePrice() + serviceType.getServicePriceAs());
                        textViewDiscovery.setVisibility(View.VISIBLE);
                        textViewDiscoveryValue.setVisibility(View.VISIBLE);
                    }
                }

                return view;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });
    }

    @OnClick(R.id.customTextViewFilter)
    public void onViewClicked() {

        filterData((isApply, filterRequest) -> {
            isApplyFilter = isApply;
            this.filterRequest = filterRequest;

            page = 1;
            if (isApplyFilter) {
                presenter.getDataFilter(page, true, filterRequest);

                String textGenrate = "";

                if (!filterRequest.getSportName().isEmpty()) {
                    textGenrate = textGenrate + filterRequest.getSportName();
                } else {
                    textGenrate = textGenrate + "Sport";
                }

                if (!filterRequest.getDate().isEmpty()) {
                    textGenrate = textGenrate + " - " + filterRequest.getDate();
                } else {
                    textGenrate = textGenrate + " - date";
                }

                if (!filterRequest.getStartTime().isEmpty()) {
                    textGenrate = textGenrate + " - " + filterRequest.getStartTime();
                } else {
                    textGenrate = textGenrate + " - time";
                }

                if (!filterRequest.getAddres().isEmpty()) {
                    textGenrate = textGenrate + " - " + filterRequest.getAddres();
                } else {
                    textGenrate = textGenrate + " - location";
                }

                if (!filterRequest.getActivityName().isEmpty()) {
                    textGenrate = textGenrate + " - " + filterRequest.getActivityName();
                } else {
                    textGenrate = textGenrate + " - activity";
                }

                if (!filterRequest.getLanguageName().isEmpty()) {
                    textGenrate = textGenrate + " - " + filterRequest.getLanguageName();
                } else {
                    textGenrate = textGenrate + " - language";
                }

                // customTextViewFilter.setText(textGenrate);

            } else {
                // customTextViewFilter.setText(getActivity().getResources().getString(R.string.sport_date_time_location_activity_language));
                presenter.getData(page, true);
            }

        }, !isApplyFilter,false);
    }

    @Override
    public void setData() {

        if (page == 1) {

            if (gMap != null) {
                gMap.clear();
                clusterManager.clearItems();

                lastUserCircle = null;

                lastPulseAnimator = null;

                pulseDuration = 3000;

                isEdit = false;

                LatLng latLngCurrent = new LatLng(BaseFragment.latitude, BaseFragment.longitude);

                current = new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.my_pin)).title("Current Location").draggable(true)
                        .position(latLngCurrent).anchor(0.52f, 0.52f);


                gMap.addMarker(current);

                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngCurrent, 13));


                addPulsatingEffectNew(latLngCurrent);

                List<Marker> markersAnimation = new ArrayList<>();

                List<MyItem> myItems = new ArrayList<>();

                for (DashboardDetails dashboardDetail : dashboardDetails) {

                    LatLng latLngLocal = new LatLng(Double.parseDouble(dashboardDetail.getLatitude()), Double.parseDouble(dashboardDetail.getLongitude()));

/*
                    Marker location = gMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.map_local_location_icon)).title("Location").draggable(true).position(latLngLocal));
                    markersAnimation.add(location);
                    location.setTag(dashboardDetail);
*/

                    MyItem myItem = new MyItem(latLngLocal.latitude, latLngLocal.longitude, "Location", new Gson().toJson(dashboardDetail));

                    myItems.add(myItem);

                }

                setAnimation(myItems);
            }

       /*     if (isLoading) {
                page = page + 1;
                isLoading = false;
                if (isApplyFilter) {
                    presenter.getDataFilter(page, false, filterRequest);
                } else {
                    presenter.getData(page, false);
                }
            }*/

        }
    }

    @Override
    public void setDataDashboard(List<DashboardDetails> data) {
        dashboardDetails = data;
        if (gMap != null) {
            setData();
        }
    }

    @Override
    public void onShow() {
        super.onShow();
    }

    @OnClick(R.id.imageViewCurrentLocation)
    public void onClick() {
        if (gMap != null) {
            ((BaseActivity) getActivity()).getLocationManager().triggerLocation(true, new LocationManager.LocationListener() {
                @Override
                public void onLocationAvailable(LatLng latLng) {
                    gMap.clear();

                    clusterManager.clearItems();

                    lastUserCircle = null;

                    lastPulseAnimator = null;

                    pulseDuration = 3000;

                    isEdit = false;

                    BaseFragment.latitude = latLng.latitude;
                    BaseFragment.longitude = latLng.longitude;
                    getAddressLetLong(BaseFragment.latitude, BaseFragment.longitude, "");

                    LatLng latLngCurrent = latLng;

                    current = new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.my_pin)).title("Current Location").draggable(true)
                            .position(latLngCurrent).anchor(0.52f, 0.52f);


                    gMap.addMarker(current);

                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngCurrent, 13));

                    addPulsatingEffectNew(latLngCurrent);


                    List<MyItem> myItems = new ArrayList<>();


                    for (DashboardDetails dashboardDetail : dashboardDetails) {

                        LatLng latLngLocal = new LatLng(Double.parseDouble(dashboardDetail.getLatitude()), Double.parseDouble(dashboardDetail.getLongitude()));

                    /*    Marker location = gMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.map_local_location_icon)).title("Location").draggable(true).position(latLngLocal));
                        location.setTag(dashboardDetail);
*/
                        MyItem myItem = new MyItem(latLngLocal.latitude, latLngLocal.longitude, "Location", new Gson().toJson(dashboardDetail));

                        myItems.add(myItem);

                    }


                    setAnimation(myItems);


                }

                @Override
                public void onFail(Status status) {

                }
            });
        }
    }

    @OnClick(R.id.imageViewRefrase)
    public void onClickRefrese() {
        if (presenter != null) {
            page = 1;
            if (isApplyFilter) {
                presenter.getDataFilter(page, true, filterRequest);
            } else {
                presenter.getData(page, true);
            }
        }
    }

    public void setIsCameraFocus(boolean isApplyFilter, double let, double lng) {
        if (gMap != null) {
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(let, lng), 13));
        }
    }

    public class ViewHolder {

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }


    private void getAddressLetLong(double lat, double longitude, String name) {

        if (getActivity() != null) {
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
                        if (!city.trim().isEmpty() && !city.equals("null")) {
                            setAddressAutoCompletTextview = setAddressAutoCompletTextview + ", " + city;
                        }
                        if (!state.trim().isEmpty() && !state.equals("null")) {
                            setAddressAutoCompletTextview = setAddressAutoCompletTextview + ", " + state;
                        }
                        if (!country.trim().isEmpty() && !country.equals("null")) {
                            setAddressAutoCompletTextview = setAddressAutoCompletTextview + ", " + country;
                        }

                        if (name.isEmpty()) {
                            textViewAddress.setText(setAddressAutoCompletTextview);
                        } else {
                            textViewAddress.setText(name + ", " + setAddressAutoCompletTextview);

                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class MarkerCallback implements Callback {
        Marker marker = null;

        MarkerCallback(Marker marker) {
            this.marker = marker;
        }

        @Override
        public void onError() {

        }

        @Override
        public void onSuccess() {
          /*  if (marker!=null && marker.isInfoWindowShown()){
                marker.showInfoWindow();
            }*/

            if (marker == null) {
                return;
            }

            if (!marker.isInfoWindowShown()) {
                return;
            }

            // If Info Window is showing, then refresh it's contents:
            marker.hideInfoWindow(); // Calling only showInfoWindow() throws an error
            marker.showInfoWindow();
        }
    }

    private void addPulsatingEffectNew(final LatLng userLatlng) {
        groundOverlay = null;
        overlayAnimatorSet = null;
        animateCurrentMarker(R.drawable.map_overly, userLatlng);
    }


    /**
     * To animate current marker by adding @{@link GroundOverlay} below marker
     *
     * @param drawable overlay to animate below current marker
     */
    public void animateCurrentMarker(@DrawableRes int drawable, LatLng currentLocation) {
        if (groundOverlay == null) {

            try {
                groundOverlay = gMap.addGroundOverlay(new GroundOverlayOptions()
                        .position(currentLocation, 2400)
                        .transparency(0.5f)
                        .zIndex(3)
                        .image(BitmapDescriptorFactory.fromBitmap(drawableToBitmap(ContextCompat.getDrawable(getActivity(), drawable)))));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        startOverlayAnimation(groundOverlay);
    }

    /**
     * To start animation on a @{@link GroundOverlay} below current marker
     *
     * @param groundOverlay Overlay to animate
     */
    public void startOverlayAnimation(final GroundOverlay groundOverlay) {

        if (overlayAnimatorSet == null) {
            overlayAnimatorSet = new AnimatorSet();
        }

        ValueAnimator vAnimator = ValueAnimator.ofInt(0, 2400);
        vAnimator.setRepeatCount(ValueAnimator.INFINITE);
        vAnimator.setRepeatMode(ValueAnimator.RESTART);
        vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                final Integer val = (Integer) valueAnimator.getAnimatedValue();
                groundOverlay.setDimensions(val);
            }
        });

        ValueAnimator tAnimator = ValueAnimator.ofFloat(0, 1);
        tAnimator.setRepeatCount(ValueAnimator.INFINITE);
        tAnimator.setRepeatMode(ValueAnimator.RESTART);
        tAnimator.setInterpolator(new LinearInterpolator());
        tAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float val = (Float) valueAnimator.getAnimatedValue();
                groundOverlay.setTransparency(val);
            }
        });

        overlayAnimatorSet.setDuration(3000);
        overlayAnimatorSet.playTogether(vAnimator, tAnimator);
        overlayAnimatorSet.start();

    }


}
