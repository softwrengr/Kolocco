package com.kooloco.ui.base;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.LocaleList;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.kooloco.BuildConfig;
import com.kooloco.R;
import com.kooloco.constant.Common;
import com.kooloco.constant.EventBusAction;
import com.kooloco.constant.LocalOrderAction;
import com.kooloco.core.AppPreferences;
import com.kooloco.core.Session;
import com.kooloco.data.URLFactory;
import com.kooloco.data.datasource.DatabaseCacheDataSource;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.HasComponent;
import com.kooloco.di.component.ActivityComponent;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.di.module.FragmentModule;
import com.kooloco.exception.ApplicationException;
import com.kooloco.exception.AuthenticationException;
import com.kooloco.exception.ServerException;
import com.kooloco.model.Activities;
import com.kooloco.model.CertificateInfo;
import com.kooloco.model.Country;
import com.kooloco.model.DurationFilter;
import com.kooloco.model.ExpFavData;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.FilterGetData;
import com.kooloco.model.FilterRequest;
import com.kooloco.model.Language;
import com.kooloco.model.MultiFile;
import com.kooloco.model.Order;
import com.kooloco.model.OtherDetailsFieldsSelect;
import com.kooloco.model.Response;
import com.kooloco.model.Service;
import com.kooloco.model.SportActivity;
import com.kooloco.model.SportSubActivity;
import com.kooloco.model.SubService;
import com.kooloco.model.Tag;
import com.kooloco.model.User;
import com.kooloco.ui.MainActivity;
import com.kooloco.ui.filter.adapter.FilterActivitys;
import com.kooloco.ui.filter.adapter.FilterAdapter;
import com.kooloco.ui.filter.adapter.FilterLabguage;
import com.kooloco.ui.filter.adapter.FilterServiceAdapter;
import com.kooloco.ui.filter.adapter.FilterTimeAdapter;
import com.kooloco.ui.manager.FragmentHandler;
import com.kooloco.ui.manager.FragmentManager;
import com.kooloco.ui.manager.FragmentNavigationFactory;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.visitor.home.adapter.MultipalImageUpload;
import com.kooloco.ui.visitor.home.adapter.MultipalImageUploadNew;
import com.kooloco.uilocal.MainLocalActivity;
import com.kooloco.uilocal.addservice.adapter.BottomSheetSportSubTypeAdapter;
import com.kooloco.uilocal.addservice.adapter.BottomSheetSportTypeAdapter;
import com.kooloco.uilocal.addservice.adapter.CountryAdapter;
import com.kooloco.util.DaysSeekBar;
import com.kooloco.util.FlowLayout;
import com.kooloco.util.ProgressDialogCustom;
import com.kooloco.util.SubscribeWithView;
import com.kooloco.util.TimeConvertUtils;
import com.kooloco.util.TouchImageView;
import com.kooloco.util.Util;
import com.kooloco.util.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * Created by hlink21 on 25/4/16.
 */
public abstract class BaseFragment<PresenterT extends BasePresenter<ViewT>, ViewT extends RootView> extends Fragment
        implements RootView, HasComponent<FragmentComponent> {

    Circle lastUserCircle;

    public static double latitude = 0.00;
    public static double longitude = 0.00;


    public double filterLatitude = 0.00;
    public double filterLongitude = 0.00;

    public String filterCity = "";
    public String filterCountry = "";

    @Inject
    protected PresenterT presenter;
    @Inject
    protected AppPreferences appPreferences;

    @Inject
    @Named("child_fragment_handler")
    FragmentHandler childFragmentHandler;

    @Inject
    FragmentNavigationFactory fragmentNavigationFactory;

    protected HasToolbar toolbar;
    protected Unbinder bind;

    private ProgressDialog loaderDialog;

    private CompositeDisposable compositeDisposable;

    private FragmentComponent fragmentComponent;

    String dateText = "";

    BottomSheetDialog dialog, dialogSportType;

    Dialog dialogModify, dialogSendRequest, dialogSingalAction;

    Dialog dialogSetSportActivityGroup, dialogAlertDelete;

    Dialog dialogSetSportSubActivityGroup;

    Dialog dialogProgressSingal;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    FilterRequest filterRequest;

    long mLastClickTime = 0;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE_NEW = 11;

    AppCompatEditText customTextViewLocation;
    private String setAddress = "";


    ProgressDialogCustom progressDialog;

    //It is used to hide progress dialog

    AppCompatTextView textViewCount;
    ProgressBar progressBar1;

    ImageView imageViewUpload;
    //

    FirebaseFirestore db;
    private TransferUtility transferUtility;

    public static FilterRequest filterRequestData;

    AppCompatEditText customTextViewSport;

    RecyclerView recyclerSportType;
    FilterServiceAdapter filterServiceAdapter;
    LinearLayout linearLayoutSport;

    @Inject
    DatabaseCacheDataSource databaseCacheDataSource;

    List<MultiFile> imagePath;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(createLayout(), container, false);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * It is use to get database instant fire stock
     *
     * @return
     */
    public FirebaseFirestore getDatabase() {

        if (db == null) {
            db = FirebaseFirestore.getInstance();

            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build();
            db.setFirestoreSettings(settings);
        }
        return db;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (presenter != null) {
            presenter.setView(createView());
        }
        bindData();
    }

    @Override
    public FragmentComponent getComponent() {
        if (fragmentComponent == null) {
            if (getActivity() instanceof HasComponent) {
                ActivityComponent component = getComponent(ActivityComponent.class);
                fragmentComponent = component.plus(new FragmentModule(this));
            }
        }
        return fragmentComponent;
    }

    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    public int getChildPlaceHolder() {
        return 0;
    }

    @Override
    public void onAttach(Context context) {
        inject(getComponent());

        // attach child fragment manager
        if (fragmentNavigationFactory == null)
            throw new RuntimeException("make sure you have inject the fragment in inject() method");
        else
            fragmentNavigationFactory.attachChildFragmentHandler(childFragmentHandler);

        super.onAttach(context);
        compositeDisposable = new CompositeDisposable();

        if (getActivity() instanceof HasToolbar)
            toolbar = (HasToolbar) getActivity();


    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (FragmentManager.sDisableFragmentAnimations) {
            Animation a = new Animation() {
            };
            a.setDuration(0);
            return a;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onDestroy() {

        if (presenter != null) {
            presenter.destroy();
            presenter = null;
            compositeDisposable.dispose();
        }
        if (bind != null)
            bind.unbind();

        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        hideKeyBoard();
        if (presenter != null)
            presenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        hideKeyBoard();
        if (presenter != null)
            presenter.pause();
    }

    @Override
    public void showMessage(String message) {
        if (presenter != null)
            showSnackBar(message);
        //presenter.navigator.showErrorMessage(message);
    }

    @Override
    public void showMessageFullTime(String message) {
        hideKeyBoard();
        if (getView() != null) {
            final Snackbar snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_INDEFINITE);
            snackbar.setActionTextColor(Color.WHITE);
            snackbar.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
            View snackView = snackbar.getView();
            TextView textView = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.opensans_regular));
            textView.setMaxLines(4);

            snackView.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
            snackbar.show();
        }
    }

    private void showSnackBar(String message) {
        hideKeyBoard();
        if (getView() != null) {
            final Snackbar snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_LONG);
            snackbar.setDuration(8000);
            snackbar.setActionTextColor(Color.WHITE);
            snackbar.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
            View snackView = snackbar.getView();
            TextView textView = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.opensans_regular));
            textView.setMaxLines(4);

            snackView.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
            snackbar.show();
        }
    }

    public void showSnackBar(View view, String message) {
        hideKeyBoard();
        if (getView() != null) {
            final Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);
            snackbar.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
            View snackView = snackbar.getView();

        /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                snackView.setElevation(3.0f);
            }
*/
            TextView textView = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.opensans_regular));
            textView.setMaxLines(4);

            snackView.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
            snackbar.show();
        }
    }

    @Override
    public void showLoader() {

/*
        if (loaderDialog == null) {
            loaderDialog = new ProgressDialog(getContext());
            loaderDialog.setMessage("Please wait");
            loaderDialog.setIndeterminate(true);
            loaderDialog.setCancelable(false);
            loaderDialog.setCanceledOnTouchOutside(false);
        }

        try {
            if (!loaderDialog.isShowing())
                loaderDialog.show();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

*/
        try {
            hideKeyBoard();
            if (progressDialog == null && getActivity() != null) {
                try {
                    progressDialog = new ProgressDialogCustom(getActivity());
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
            if (progressDialog != null && getActivity() != null) {
                progressDialog.show();
                progressDialog.startAniationAgain();
            }

        } catch (Exception e) {

        }

    }

    @Override
    public void hideLoader() {
        /*try {
            if (loaderDialog != null && loaderDialog.isShowing()) {
                loaderDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        try {

            if (progressDialog != null && getActivity() != null) {
                progressDialog.hideAniationAgain();
                progressDialog.dismiss();
            }

        } catch (Exception e) {

        }
    }


    public void hideLoderNew() {
        progressDialog = null;
    }


    @Override
    public void hideKeyBoard() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideKeyboard();
        }
    }

    @Override
    public void showKeyBoard() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showKeyboard();
        }
    }

    @Override
    public void onError(Throwable throwable) {

        try {
            if (throwable instanceof ServerException) {
                showMessage(throwable.getMessage());
            } else if (throwable instanceof ConnectException) {
                showMessage("connect to internet");
            } else if (throwable instanceof ApplicationException) {
                showMessage(throwable.getMessage());
            } else if (throwable instanceof AuthenticationException) {

                changeAppLanguageSetDefault();

                Realm realm = Realm.getDefaultInstance();

                realm.beginTransaction();

                realm.deleteAll();

                realm.commitTransaction();

                appPreferences.clearAll();

                NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancelAll();

                if (presenter != null)
                    presenter.navigator.startAuthenticationActivity()
                            .byFinishingAll()
                            .start();

            } else if (throwable instanceof SocketTimeoutException) {
                showMessage("Connection time out");
            } else showMessage("Something wrong here");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onShow() {

    }

    @Override
    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    protected boolean onBackActionPerform() {
        return true;
    }

    protected abstract int createLayout();

    protected abstract void inject(FragmentComponent fragmentComponent);

    protected abstract ViewT createView();

    protected abstract void bindData();

    /**
     * It is used to set toolbar
     *
     * @param name passing class name
     */
    public void setToolbarIsolatedAppointment(String name) {
        Log.d(":::", "" + name);
    }

    /**
     * It is used to open filter screen
     *
     * @param filterGetData  get filter data response
     * @param callBackFilter set call back to return
     * @param isClear
     */
    private void openFilter(FilterGetData filterGetData, CallBackFilter callBackFilter, boolean isClear, boolean isHome) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        if (isClear) {
            dialog = null;
            filterRequest = null;
            filterLatitude = latitude;
            filterLongitude = longitude;
        }

        if (isHome) {
            if (filterRequestData != null) {
                filterRequest = filterRequestData;
            }
        }

        if (dialog != null) {
            if (dialog.isShowing()) {
                return;
            } else {
                if (isHome) {
                    if (filterRequest != null) {
                        if (filterRequest.getAddres() != null) {
                            if (customTextViewLocation != null) {
                                customTextViewLocation.setText(filterRequest.getAddres());
                                setAddress = filterRequest.getAddres();
                            }

                            if (!filterRequest.getAddres().isEmpty()) {
                                filterLatitude = Double.parseDouble(filterRequest.getLatitude());
                                filterLongitude = Double.parseDouble(filterRequest.getLongitude());
                                filterCity = filterRequest.getCity();
                                filterCountry = filterRequest.getCountry();
                            }
                        }

                        if (customTextViewSport != null) {

                            customTextViewSport.setSelected(false);
                            if (linearLayoutSport != null) {
                                linearLayoutSport.setVisibility(customTextViewSport.isSelected() ? View.VISIBLE : View.GONE);
                            }

                            customTextViewSport.setText(filterRequest.getSportName());
                        }
                        if (filterServiceAdapter != null) {

                            if (!filterRequestData.getSportName().isEmpty()) {

                               /* int selectPosition = -1;

                                int j = 0;
                                String serviceSelectId = "," + filterRequest.getSportId() + ",";

                                List<Service> servicesTemp = new ArrayList<>();

                                for (Service service1 : filterGetData.getSports()) {

                                    if (serviceSelectId.contains("," + service1.getId() + ",")) {

                                        selectPosition = j;
                                        if (service1.getIsExpandable().equalsIgnoreCase("1")) {
                                            int i = 0;
                                            for (SubService subService : service1.getSubServices()) {
                                                subService.setSelect(serviceSelectId.contains("," + subService.getId() + ","));
                                                subService.setIsSelected(serviceSelectId.contains("," + subService.getId() + ",") ? "1" : "0");
                                                service1.getSubServices().set(i, subService);
                                                i = i + 1;
                                                if (serviceSelectId.contains("," + subService.getId() + ",")) {
                                                    selectPosition = j;
                                                }
                                            }
                                            servicesTemp.add(service1);
                                        } else {

                                            servicesTemp.add(service1);
                                        }

                                    } else {
                                        if (service1.getIsExpandable().equalsIgnoreCase("1")) {
                                            int i = 0;
                                            for (SubService subService : service1.getSubServices()) {
                                                subService.setSelect(serviceSelectId.contains("," + subService.getId() + ","));
                                                subService.setIsSelected(serviceSelectId.contains("," + subService.getId() + ",") ? "1" : "0");
                                                service1.getSubServices().set(i, subService);
                                                i = i + 1;
                                                if (serviceSelectId.contains("," + subService.getId() + ",")) {
                                                    selectPosition = j;
                                                }
                                            }
                                            servicesTemp.add(service1);
                                        } else {

                                            servicesTemp.add(service1);
                                        }
                                    }
                                    j = j + 1;

                                }

                                filterServiceAdapter.setNewFilterData(servicesTemp);
                                filterServiceAdapter.onSelect(-1);
                                filterServiceAdapter.notifyDataSetChanged();*/
                            }

                        }
                    }
                }
                dialog.show();
                return;
            }
        }

        if (dialog == null) {
            dialog = new BottomSheetDialog(getActivity());
            if (!isHome) {
                filterRequest = new FilterRequest();
            }
            filterLatitude = latitude;
            filterLongitude = longitude;
        }

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_filter, null, false);
        dialog.setContentView(view);

        final NestedScrollView nestedScrollView = view.findViewById(R.id.nestedScrollView);

        final ImageView imageViewClose = view.findViewById(R.id.imageViewClose);

        final ImageView imageViewRating1 = view.findViewById(R.id.imageViewRating1);
        final ImageView imageViewRating2 = view.findViewById(R.id.imageViewRating2);
        final ImageView imageViewRating3 = view.findViewById(R.id.imageViewRating3);
        final ImageView imageViewRating4 = view.findViewById(R.id.imageViewRating4);
        final ImageView imageViewRating5 = view.findViewById(R.id.imageViewRating5);

        final DaysSeekBar daysSeekBar = view.findViewById(R.id.daySeekBarRadus);
        final DaysSeekBar daySeekBarRating = view.findViewById(R.id.daySeekBarRating);
        final AppCompatTextView textViewRating = view.findViewById(R.id.textViewRating);

        final AppCompatTextView textViewReset = view.findViewById(R.id.textViewReset);

        daysSeekBar.setRangeValues(0, 5000);

        daySeekBarRating.setOnTouchListener((v, event) -> {

            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // Disallow ScrollView to intercept touch events.

                    try {
                        v.getParent().getParent().requestDisallowInterceptTouchEvent(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    // Allow ScrollView to intercept touch events.

                    try {
                        v.getParent().getParent().requestDisallowInterceptTouchEvent(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
            }
            // Handle ListView touch events.
            v.onTouchEvent(event);
            return true;
        });

        daysSeekBar.setOnTouchListener((v, event) -> {

            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // Disallow ScrollView to intercept touch events.

                    try {
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    // Allow ScrollView to intercept touch events.

                    try {
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
            }
            // Handle ListView touch events.
            v.onTouchEvent(event);
            return true;
        });

        daySeekBarRating.setOnRangeSeekBarChangeListener(new DaysSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(DaysSeekBar bar, Number minValue, Number maxValue) {
                Log.d("Seek", "" + minValue + " , " + maxValue);
                daySeekBarRating.setSelectedMinValue(minValue);
                daySeekBarRating.setSelectedMaxValue(maxValue);
                textViewRating.setText("" + minValue + " - " + "" + maxValue);

                int minv = (int) minValue;
                int maxv = (int) maxValue;

                imageViewRating1.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.rating_star));
                imageViewRating2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.rating_star));
                imageViewRating3.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.rating_star));
                imageViewRating4.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.rating_star));
                imageViewRating5.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.rating_star));

                for (int i = minv; i <= maxv; i++) {
                    if (i == 1) {
                        imageViewRating1.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.rating_sel_star));
                    }
                    if (i == 2) {
                        imageViewRating2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.rating_sel_star));
                    }
                    if (i == 3) {
                        imageViewRating3.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.rating_sel_star));
                    }
                    if (i == 4) {
                        imageViewRating4.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.rating_sel_star));
                    }
                    if (i == 5) {
                        imageViewRating5.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.rating_sel_star));
                    }

                }
            }
        });

        daySeekBarRating.setRangeValues(1, 5);


        customTextViewSport = view.findViewById(R.id.customTextViewSport);


        linearLayoutSport = view.findViewById(R.id.linearLayoutSport);

        recyclerSportType = view.findViewById(R.id.recyclerSportType);

        customTextViewSport.setSelected(true);

        customTextViewSport.setSelected(!customTextViewSport.isSelected());

        customTextViewSport.setText(filterRequest.getSportName());

        customTextViewSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!customTextViewSport.isSelected()) {
                    // customTextViewSport.setText("");
                }

                customTextViewSport.setSelected(!customTextViewSport.isSelected());

                linearLayoutSport.setVisibility(customTextViewSport.isSelected() ? View.VISIBLE : View.GONE);

                recyclerSportType.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


                if (customTextViewSport.isSelected()) {

                    nestedScrollView.post(() -> {
                        nestedScrollView.smoothScrollTo(0, 0);
                        nestedScrollView.smoothScrollTo(0, customTextViewSport.getTop() - getActivity().getResources().getDimensionPixelOffset(R.dimen.dp_16));
                    });
                }

                int j = 0;
                String serviceSelectId = "," + filterRequest.getSportId() + ",";

                List<Service> servicesTemp = new ArrayList<>();

                for (Service service1 : filterGetData.getSports()) {

                    if (serviceSelectId.contains("," + service1.getId() + ",")) {


                        if (service1.getIsExpandable().equalsIgnoreCase("1")) {
                            int i = 0;
                            for (SubService subService : service1.getSubServices()) {
                                subService.setSelect(serviceSelectId.contains("," + subService.getId() + ","));
                                subService.setIsSelected(serviceSelectId.contains("," + subService.getId() + ",") ? "1" : "0");
                                service1.getSubServices().set(i, subService);
                                i = i + 1;
                                if (serviceSelectId.contains("," + subService.getId() + ",")) {

                                }
                            }
                            servicesTemp.add(service1);
                        } else {

                            servicesTemp.add(service1);
                        }

                    } else {
                        if (service1.getIsExpandable().equalsIgnoreCase("1")) {
                            int i = 0;
                            for (SubService subService : service1.getSubServices()) {
                                subService.setSelect(serviceSelectId.contains("," + subService.getId() + ","));
                                subService.setIsSelected(serviceSelectId.contains("," + subService.getId() + ",") ? "1" : "0");
                                service1.getSubServices().set(i, subService);
                                i = i + 1;
                                if (serviceSelectId.contains("," + subService.getId() + ",")) {

                                }
                            }
                            servicesTemp.add(service1);
                        } else {

                            servicesTemp.add(service1);
                        }
                    }
                    j = j + 1;

                }

                filterServiceAdapter = new FilterServiceAdapter(getActivity(), servicesTemp, new FilterServiceAdapter.CallBack() {
                    @Override
                    public void onClickSelect(int position, String name, String id) {

                        if (position == -1) {
                            customTextViewSport.setText(name);
                        } else {
                            linearLayoutSport.setVisibility(View.GONE);
                            customTextViewSport.setSelected(false);
                            customTextViewSport.setText(name);
                        }

                        filterRequest.setSportName(name);
                        filterRequest.setSportId(id);
                    }
                });

                recyclerSportType.setAdapter(filterServiceAdapter);

            }
        });


        //Set sub service

        //End sub service

        final AppCompatEditText customTextViewDate = view.findViewById(R.id.customTextViewDate);

        customTextViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard();
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        dateText = new SimpleDateFormat("dd MMMM, yyyy").format(calendar.getTime());
                        customTextViewDate.setText(new SimpleDateFormat("dd MMMM, yyyy").format(calendar.getTime()));
                        filterRequest.setDate(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTime().getTime());
                datePickerDialog.show();
            }
        });


        //Duration

        final AppCompatEditText customTextViewDuration = view.findViewById(R.id.customTextViewDuration);

        final LinearLayout linearLayoutDuration = view.findViewById(R.id.linearLayoutDuration);

        RecyclerView recyclerDuration = view.findViewById(R.id.recyclerDuration);

        customTextViewDuration.setSelected(false);


        List<String> durations = new ArrayList<>();

        for (DurationFilter otherDetailsFieldsSelect : filterGetData.getDurationFilters()) {
            durations.add(otherDetailsFieldsSelect.getTitle());
        }


        recyclerDuration.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerDuration.setAdapter(new FilterAdapter(getActivity(), durations, new FilterAdapter.CallBack() {
            @Override
            public void onClickSelect(int position) {

                filterRequest.setDurationId(filterGetData.getDurationFilters().get(position).getId());
                filterRequest.setDurationText(durations.get(position));
                linearLayoutDuration.setVisibility(View.GONE);
                customTextViewDuration.setSelected(false);
                customTextViewDuration.setText(Temp.getFilDuration().get(position));
            }
        }));

        customTextViewDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customTextViewDuration.setSelected(!customTextViewDuration.isSelected());

                linearLayoutDuration.setVisibility(customTextViewDuration.isSelected() ? View.VISIBLE : View.GONE);

                if (customTextViewDuration.isSelected()) {

                    nestedScrollView.post(() -> {
                        nestedScrollView.smoothScrollTo(0, 0);
                        nestedScrollView.smoothScrollTo(0, customTextViewDuration.getTop() - getActivity().getResources().getDimensionPixelOffset(R.dimen.dp_16));
                    });
                }

            }
        });


        final AppCompatEditText customTextViewStartTime = view.findViewById(R.id.customTextViewStartTime);

        final LinearLayout linearLayoutTime = view.findViewById(R.id.linearLayoutTime);

        RecyclerView recyclerTime = view.findViewById(R.id.recyclerTime);

        customTextViewStartTime.setSelected(false);

        recyclerTime.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerTime.setAdapter(new FilterTimeAdapter(getActivity(), Temp.getFilTimeNew(), new FilterTimeAdapter.CallBack() {
            @Override
            public void onClickSelect(int position, String time) {
                filterRequest.setStartTime(time);
                linearLayoutTime.setVisibility(View.GONE);
                customTextViewStartTime.setSelected(false);
                customTextViewStartTime.setText(TimeConvertUtils.dateTimeConvertLocalToLocal(time, "HH:mm:ss", "hh:mm a"));
            }
        }));

        customTextViewStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePickerFilter(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {


                        String formatedDate = "";
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        SimpleDateFormat sdfs = new SimpleDateFormat("HH:mm:ss");
                        Date dt;
                        String time = "";

                        try {
                            time = "" + hourOfDay + ":" + minute + ":" + "00";

                            dt = sdf.parse(time);
                            formatedDate = sdfs.format(dt);
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        filterRequest.setStartTime(formatedDate);
                        linearLayoutTime.setVisibility(View.GONE);
                        customTextViewStartTime.setSelected(false);
                        customTextViewStartTime.setText(TimeConvertUtils.dateTimeConvertLocalToLocal(formatedDate, "HH:mm:ss", "hh:mm a"));

                    }
                });
                /*customTextViewStartTime.setSelected(!customTextViewStartTime.isSelected());

                linearLayoutTime.setVisibility(customTextViewStartTime.isSelected() ? View.VISIBLE : View.GONE);

                if (customTextViewStartTime.isSelected()) {

                    nestedScrollView.post(() -> {
                        nestedScrollView.smoothScrollTo(0, 0);
                        nestedScrollView.smoothScrollTo(0, customTextViewStartTime.getTop() - getActivity().getResources().getDimensionPixelOffset(R.dimen.dp_16));
                    });
                }*/
            }
        });

        customTextViewLocation = view.findViewById(R.id.customTextViewLocation);

        if (filterRequest.getAddres() != null) {
            customTextViewLocation.setText(filterRequest.getAddres());
            setAddress = filterRequest.getAddres();

            if (!filterRequest.getAddres().isEmpty()) {
                filterLatitude = Double.parseDouble(filterRequest.getLatitude());
                filterLongitude = Double.parseDouble(filterRequest.getLongitude());

                filterCity = filterRequest.getCity();
                filterCountry = filterRequest.getCountry();
            }
        }


        customTextViewLocation.setOnClickListener(view1 -> {
            ///zxc asd
            try {

                Intent intent =
                        new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(getActivity());
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE_NEW);
            } catch (GooglePlayServicesRepairableException e) {
                // TODO: Handle the error.
            } catch (GooglePlayServicesNotAvailableException e) {
                // TODO: Handle the error.
            }

        });

        final AppCompatEditText customTextViewActivites = view.findViewById(R.id.customTextViewActivites);

        final LinearLayout linearLayoutActivityType = view.findViewById(R.id.linearLayoutActivityType);

        RecyclerView recyclerActivitytype = view.findViewById(R.id.recyclerActivitytype);


        customTextViewActivites.setSelected(false);

        recyclerActivitytype.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerActivitytype.setAdapter(new FilterActivitys(getActivity(), filterGetData.getExperience(), new FilterActivitys.CallBack() {
            @Override
            public void onClickSelect(int position, Activities activities) {
                linearLayoutActivityType.setVisibility(View.GONE);
                customTextViewActivites.setSelected(false);
                customTextViewActivites.setText(activities.getName());
                filterRequest.setExperienceId(activities.getId());
                filterRequest.setActivityName(activities.getName());
            }
        }));

        customTextViewActivites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customTextViewActivites.setSelected(!customTextViewActivites.isSelected());

                linearLayoutActivityType.setVisibility(customTextViewActivites.isSelected() ? View.VISIBLE : View.GONE);


                if (customTextViewActivites.isSelected()) {

                    nestedScrollView.post(() -> {
                        nestedScrollView.smoothScrollTo(0, 0);
                        nestedScrollView.smoothScrollTo(0, customTextViewActivites.getTop() - getActivity().getResources().getDimensionPixelOffset(R.dimen.dp_16));
                    });
                }
            }
        });


        final AppCompatEditText customTextViewLanguage = view.findViewById(R.id.customTextViewLanguage);

        final LinearLayout linearLayoutLanguage = view.findViewById(R.id.linearLayoutLanguage);

        RecyclerView recyclerLanguage = view.findViewById(R.id.recyclerLanguage);


        customTextViewLanguage.setSelected(false);

        recyclerLanguage.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerLanguage.setAdapter(new FilterLabguage(getActivity(), filterGetData.getLanguages(), new FilterLabguage.CallBack() {
            @Override
            public void onClickSelect(int position, Language language) {
                linearLayoutLanguage.setVisibility(View.GONE);
                customTextViewLanguage.setSelected(false);
                customTextViewLanguage.setText(language.getName());
                filterRequest.setLanguageId(language.getId());
                filterRequest.setLanguageName(language.getName());
            }
        }));

        customTextViewLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customTextViewLanguage.setSelected(!customTextViewLanguage.isSelected());

                linearLayoutLanguage.setVisibility(customTextViewLanguage.isSelected() ? View.VISIBLE : View.GONE);

                if (customTextViewLanguage.isSelected()) {

                    nestedScrollView.post(() -> {
                        nestedScrollView.smoothScrollTo(0, 0);
                        nestedScrollView.smoothScrollTo(0, customTextViewLanguage.getTop() - getActivity().getResources().getDimensionPixelOffset(R.dimen.dp_16));
                    });
                }

            }
        });


        final AppCompatTextView textViewPrice = view.findViewById(R.id.textViewPrice);


        daysSeekBar.setOnRangeSeekBarChangeListener(new DaysSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(DaysSeekBar bar, Number minValue, Number maxValue) {
                Log.d("Seek", "" + minValue + " , " + maxValue);
                textViewPrice.setText(getString(R.string.usd) + " " + minValue + " - " + getString(R.string.usd) + " " + maxValue);
            }
        });


        final AppCompatEditText customTextViewRecomand = view.findViewById(R.id.textViewRecommendedLevel);

        final LinearLayout linearLayoutRecomand = view.findViewById(R.id.linearLayoutRecommendedLevel);

        RecyclerView recyclerRecommendedLevel = view.findViewById(R.id.recyclerRecommendedLevel);

        customTextViewRecomand.setSelected(false);


        List<String> recommand = new ArrayList<>();

        for (OtherDetailsFieldsSelect otherDetailsFieldsSelect : filterGetData.getRecommendedLevel()) {
            recommand.add(otherDetailsFieldsSelect.getName());
        }


        recyclerRecommendedLevel.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerRecommendedLevel.setAdapter(new FilterAdapter(getActivity(), recommand, new FilterAdapter.CallBack() {
            @Override
            public void onClickSelect(int position) {
                filterRequest.setRecomanId(filterGetData.getRecommendedLevel().get(position).getId());
                filterRequest.setRecomanText(filterGetData.getRecommendedLevel().get(position).getName());
                linearLayoutRecomand.setVisibility(View.GONE);
                customTextViewRecomand.setSelected(false);
                customTextViewRecomand.setText(filterGetData.getRecommendedLevel().get(position).getName());
            }
        }));

        customTextViewRecomand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customTextViewRecomand.setSelected(!customTextViewRecomand.isSelected());

                linearLayoutRecomand.setVisibility(customTextViewRecomand.isSelected() ? View.VISIBLE : View.GONE);


                if (customTextViewRecomand.isSelected()) {

                    nestedScrollView.post(() -> {
                        nestedScrollView.smoothScrollTo(0, 0);
                        nestedScrollView.smoothScrollTo(0, customTextViewRecomand.getTop() - getActivity().getResources().getDimensionPixelOffset(R.dimen.dp_16));
                    });
                }
            }
        });


        final AppCompatEditText textViewPerfectFor = view.findViewById(R.id.textViewPerfectFor);

        final LinearLayout linearLayoutPerfectFor = view.findViewById(R.id.linearLayoutPerfectFor);

        RecyclerView recyclerPerfectFor = view.findViewById(R.id.recyclerPerfectFor);

        textViewPerfectFor.setSelected(false);


        List<String> perefect = new ArrayList<>();

        for (OtherDetailsFieldsSelect otherDetailsFieldsSelect : filterGetData.getPerfectFor()) {
            perefect.add(otherDetailsFieldsSelect.getName());
        }


        recyclerPerfectFor.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerPerfectFor.setAdapter(new FilterAdapter(getActivity(), perefect, new FilterAdapter.CallBack() {
            @Override
            public void onClickSelect(int position) {
                filterRequest.setPerfectId(filterGetData.getPerfectFor().get(position).getId());
                filterRequest.setPerfectForText(filterGetData.getPerfectFor().get(position).getName());

                linearLayoutPerfectFor.setVisibility(View.GONE);
                textViewPerfectFor.setSelected(false);
                textViewPerfectFor.setText(filterGetData.getPerfectFor().get(position).getName());


            }
        }));

        textViewPerfectFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewPerfectFor.setSelected(!textViewPerfectFor.isSelected());

                linearLayoutPerfectFor.setVisibility(textViewPerfectFor.isSelected() ? View.VISIBLE : View.GONE);

                if (textViewPerfectFor.isSelected()) {

                    nestedScrollView.post(() -> {
                        nestedScrollView.smoothScrollTo(0, 0);
                        nestedScrollView.smoothScrollTo(0, textViewPerfectFor.getTop() - getActivity().getResources().getDimensionPixelOffset(R.dimen.dp_16));
                    });
                }
            }
        });

        AppCompatButton buttonApply = view.findViewById(R.id.buttonApply);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (filterRequest != null) {
                    if (filterRequest.getSportId().isEmpty()
                            && filterRequest.getDate().isEmpty()
                            && filterRequest.getStartTime().isEmpty()
                            && filterRequest.getExperienceId().isEmpty()
                            && filterRequest.getLanguageId().isEmpty()

                      /*      && filterRequest.getLatitude().isEmpty()
                            && filterRequest.getLongitude().isEmpty()*/

                            && filterRequest.getPriceMin().isEmpty()
                            && filterRequest.getPriceMax().isEmpty()

                            && filterRequest.getRateMin().isEmpty()
                            && filterRequest.getRateMax().isEmpty()

                            && filterRequest.getDurationId().isEmpty()
                            && filterRequest.getRecomanId().isEmpty()
                            && filterRequest.getPerfectId().isEmpty()

                            ) {
                        callBackFilter.onCallBack(false, new FilterRequest());
                        filterRequestData = new FilterRequest();
                    }
                } else {
                    filterRequestData = new FilterRequest();
                    callBackFilter.onCallBack(false, new FilterRequest());
                }
                dialog.dismiss();
            }
        });

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterRequest.setPriceMin("" + daysSeekBar.getSelectedMinValue());
                filterRequest.setPriceMax("" + daysSeekBar.getSelectedMaxValue());

                if (daySeekBarRating.getSelectedMinValue().toString().equalsIgnoreCase("1")) {
                    filterRequest.setRateMin("0");
                } else {
                    filterRequest.setRateMin("" + daySeekBarRating.getSelectedMinValue());
                }


                filterRequest.setRateMax("" + daySeekBarRating.getSelectedMaxValue());

                filterRequest.setLatitude("" + filterLatitude);
                filterRequest.setLongitude("" + filterLongitude);

                filterRequest.setCity("" + filterCity);
                filterRequest.setCountry("" + filterCountry);

                filterRequest.setAddres(setAddress);

                if (filterRequest.getSportId().isEmpty()
                        && filterRequest.getDate().isEmpty()
                        && filterRequest.getStartTime().isEmpty()
                        && filterRequest.getExperienceId().isEmpty()
                        && filterRequest.getLanguageId().isEmpty()

                /*        && filterRequest.getLatitude().isEmpty()
                        && filterRequest.getLongitude().isEmpty()*/
                        && filterRequest.getAddres().isEmpty()

                        && filterRequest.getDurationId().isEmpty()
                        && filterRequest.getRecomanId().isEmpty()
                        && filterRequest.getPerfectId().isEmpty()

                        && filterRequest.getPriceMin().equalsIgnoreCase("0")
                        && filterRequest.getPriceMax().equalsIgnoreCase("5000")

                        && filterRequest.getRateMin().equalsIgnoreCase("0")
                        && filterRequest.getRateMax().equalsIgnoreCase("5")

                        ) {
                    filterRequestData = new FilterRequest();
                    callBackFilter.onCallBack(false, new FilterRequest());
                    dialog.dismiss();
                } else {
                    filterRequestData = filterRequest;
                    callBackFilter.onCallBack(true, filterRequest);
                    dialog.dismiss();
                }
            }
        });


        AppCompatTextView buttonResetAll = view.findViewById(R.id.buttonResetAll);

        textViewReset.setOnClickListener(view1 -> buttonResetAll.performClick());

        buttonResetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                filterRequest = null;
                filterRequest = new FilterRequest();
                filterRequestData = new FilterRequest();

                customTextViewSport.setText("");
                customTextViewDate.setText("");
                customTextViewStartTime.setText("");
                customTextViewLocation.setText("");
                customTextViewActivites.setText("");
                customTextViewLanguage.setText("");
                setAddress = "";

                customTextViewDuration.setText("");
                customTextViewRecomand.setText("");
                textViewPerfectFor.setText("");

                daysSeekBar.setSelectedMinValue(0);
                daysSeekBar.setSelectedMaxValue(5000);

                daySeekBarRating.setSelectedMinValue(1);
                daySeekBarRating.setSelectedMaxValue(5);

                for (int i = 1; i <= 5; i++) {
                    if (i == 1) {
                        imageViewRating1.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.rating_sel_star));
                    }
                    if (i == 2) {
                        imageViewRating2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.rating_sel_star));
                    }
                    if (i == 3) {
                        imageViewRating3.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.rating_sel_star));
                    }
                    if (i == 4) {
                        imageViewRating4.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.rating_sel_star));
                    }
                    if (i == 5) {
                        imageViewRating5.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.rating_sel_star));
                    }
                }

                textViewRating.setText("" + 1 + " - " + "" + 5);

                textViewPrice.setText(BaseActivity.currency + "" + 0 + " - " + BaseActivity.currency + "" + 5000);

                filterRequest.setPriceMin("0");
                filterRequest.setPriceMax("5000");

                filterRequest.setRateMin("0");
                filterRequest.setRateMax("5");

                filterRequestData = new FilterRequest();

                callBackFilter.onCallBack(false, filterRequest);

                dialog.dismiss();
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                BottomSheetDialog d = (BottomSheetDialog) dialog;

                FrameLayout bottomSheet = (FrameLayout) d.findViewById(android.support.design.R.id.design_bottom_sheet);

                BottomSheetBehavior.from(bottomSheet)
                        .setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });


        dialog.show();
    }


    public void goBack() {
        getActivity().onBackPressed();
    }

    /**
     * It is used to set call back filter
     */
    public interface CallBackFilter {
        /**
         * @param isApply true means apply filter button and false means apply clear button
         * @param
         */
        void onCallBack(boolean isApply, FilterRequest filterRequest);

    }

    /**
     * It is used to open call intent
     * @param countryCode
     * @param number
     */
    public void openCallIntent(String countryCode, String number) {
        if (countryCode == null) {
            countryCode = "";
        }
        if (countryCode.isEmpty()) {
            countryCode = "+41";
        }

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + countryCode + number));
        startActivity(intent);
    }

    /**
     * It ised to show modify dialog
     * Currently not used any location
     * @param order
     */
    public void showModifyDialog(Order order) {

        if (dialogModify != null) {
            if (dialogModify.isShowing()) {
                dialogModify.dismiss();
            }
        }

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_modify, null, false);

        AppCompatButton buttonSubmit = view.findViewById(R.id.buttonSubmit);


        AppCompatCheckBox checkboxChooseDuration = view.findViewById(R.id.checkboxChooseDuration);
        AppCompatCheckBox checkboxChooseLocation = view.findViewById(R.id.checkboxChooseLocation);


        checkboxChooseDuration.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.opensans_regular));

        checkboxChooseLocation.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.opensans_regular));

        AppCompatTextView buttonCancel = view.findViewById(R.id.buttonCancel);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkboxChooseDuration.isChecked() && checkboxChooseLocation.isChecked()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("orderStatus", new Gson().toJson(order));
                    presenter.navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.MODIFYDURATIONLOCATION).addBundle(bundle).start();
                } else if (checkboxChooseDuration.isChecked()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("orderStatus", new Gson().toJson(order));
                    presenter.navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.MODIFYDURATION).addBundle(bundle).start();
                } else if (checkboxChooseLocation.isChecked()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("orderStatus", new Gson().toJson(order));
                    presenter.navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.MODIFYLOCATION).addBundle(bundle).start();
                }
                dialogModify.dismiss();
            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogModify.dismiss();
            }
        });

        dialogModify = new Dialog(getActivity());

        dialogModify.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogModify.setCancelable(true);

        dialogModify.getWindow().setBackgroundDrawableResource(R.drawable.drwable_background_tra);

        dialogModify.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        dialogModify.setContentView(view);

        dialogModify.show();


    }

    /**
     * It is used to show payment request dialog
     */
    public void showSendPaymentRequestDialog() {

        if (dialogSendRequest != null) {
            if (dialogSendRequest.isShowing()) {
                dialogSendRequest.dismiss();
            }
        }

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_send_payment_request, null, false);

        AppCompatButton buttonThanks = view.findViewById(R.id.buttonThanks);

        buttonThanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSendRequest.dismiss();
            }
        });

        dialogSendRequest = new Dialog(getActivity());

        dialogSendRequest.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogSendRequest.setCancelable(true);

        dialogSendRequest.getWindow().setBackgroundDrawableResource(R.drawable.drwable_background_tra);

        dialogSendRequest.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        dialogSendRequest.setContentView(view);

        dialogSendRequest.show();


    }


    //Set map animation

    /**
     * It is used to set animation map
     * @param userLatlng
     * @param googleMap
     * @param isNew
     */
    public void addPulsatingEffect(final LatLng userLatlng, final GoogleMap googleMap, boolean isNew) {
        if (lastUserCircle != null) {
            lastUserCircle.remove();
            lastUserCircle = null;
        }

        if (isNew) {
            lastUserCircle = null;
        }

        long pulseDuration = 3000;
        ValueAnimator lastPulseAnimator = valueAnimate(700, pulseDuration, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (lastUserCircle != null) {
                    lastUserCircle.setRadius((Float) animation.getAnimatedValue());
                } else {
                    lastUserCircle = googleMap.addCircle(new CircleOptions()
                            .center(userLatlng)
                            .radius((Float) animation.getAnimatedValue())
                            .strokeWidth(2)
                            .strokeColor(Color.TRANSPARENT)
                            .fillColor(getResources().getColor(R.color.buttonColorTra)));
                }
            }
        });

        animation(lastUserCircle);

    }

    /**
     * It is used to show animation map
     * @param circle
     */
    public void animation(final Circle circle) {
        int colorFrom = getResources().getColor(R.color.buttonColorTra);
        int colorTo = getResources().getColor(R.color.buttonColorTra);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(3000);
        // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                circle.setStrokeColor((int) animator.getAnimatedValue());
            }
        });
        colorAnimation.setRepeatCount(ValueAnimator.INFINITE);
        colorAnimation.start();

    }

    /**
     * It is used to show animation map
     * @param accuracy
     * @param duration
     * @param updateListener
     * @return
     */
    protected ValueAnimator valueAnimate(float accuracy, long duration, ValueAnimator.AnimatorUpdateListener updateListener) {
        Log.d("valueAnimate: ", "called");
        ValueAnimator va = ValueAnimator.ofFloat(0, accuracy);
        va.setDuration(duration);
        va.addUpdateListener(updateListener);
        va.setRepeatCount(ValueAnimator.INFINITE);
        va.setRepeatMode(ValueAnimator.RESTART);
        va.start();
        return va;

    }

    /**
     * It is used to show Bottom sheet sport selection
     * @param strings
     * @param callBackSelectBottomSheet
     */
    public void openBottomSheet(final List<String> strings, final CallBackSelectBottomSheet callBackSelectBottomSheet) {
        if (dialogSportType != null) {
            if (dialogSportType.isShowing()) {
                return;
            }
        }

        if (dialogSportType == null) {
            dialogSportType = new BottomSheetDialog(getActivity());
        }

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_select_sport_types, null, false);
        dialogSportType.setContentView(view);

        RecyclerView recyclerTime = view.findViewById(R.id.recyclerTime);

        recyclerTime.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerTime.setAdapter(new FilterAdapter(getActivity(), strings, new FilterAdapter.CallBack() {
            @Override
            public void onClickSelect(int position) {
                callBackSelectBottomSheet.onClick(strings.get(position));
                dialogSportType.dismiss();
            }
        }));

        dialogSportType.show();
    }

    /**
     * It is used to show Bottom sheet sport sub selection
     * @param sportActivities
     * @param callBackSelectBottomSheet
     */
    public void openBottomSheetSportType(final List<SportActivity> sportActivities, final CallBackSelectBottomSheetSportType callBackSelectBottomSheet) {
        if (dialogSportType != null) {
            if (dialogSportType.isShowing()) {
                return;
            }
        }

        if (dialogSportType == null) {
            dialogSportType = new BottomSheetDialog(getActivity());
        }

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_select_sport_types, null, false);
        dialogSportType.setContentView(view);

        RecyclerView recyclerTime = view.findViewById(R.id.recyclerTime);

        recyclerTime.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerTime.setAdapter(new BottomSheetSportTypeAdapter(getActivity(), sportActivities, new BottomSheetSportTypeAdapter.CallBack() {
            @Override
            public void onClickSelect(SportActivity sportActivity) {
                callBackSelectBottomSheet.onClick(sportActivity);
                dialogSportType.dismiss();
            }
        }));

        dialogSportType.show();
    }

    /**
     * It is used to select sub sport type
     * @param sportSubActivities
     * @param callBackSelectBottomSheet
     */
    public void openBottomSheetSportSubType(final List<SportSubActivity> sportSubActivities, final CallBackSelectBottomSheetSportSubType callBackSelectBottomSheet) {
        if (dialogSportType != null) {
            if (dialogSportType.isShowing()) {
                return;
            }
        }

        if (dialogSportType == null) {
            dialogSportType = new BottomSheetDialog(getActivity());
        }

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_select_sport_types, null, false);
        dialogSportType.setContentView(view);

        RecyclerView recyclerTime = view.findViewById(R.id.recyclerTime);

        recyclerTime.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerTime.setAdapter(new BottomSheetSportSubTypeAdapter(getActivity(), sportSubActivities, new BottomSheetSportSubTypeAdapter.CallBack() {
            @Override
            public void onClickSelect(SportSubActivity sportActivity) {
                callBackSelectBottomSheet.onClick(sportActivity);
                dialogSportType.dismiss();
            }
        }));

        dialogSportType.show();
    }


    public interface CallBackSelectBottomSheetSportType {
        void onClick(SportActivity value);
    }

    public interface CallBackSelectBottomSheetSportSubType {
        void onClick(SportSubActivity value);
    }

    public interface CallBackSelectBottomSheet {
        void onClick(String value);
    }


    //Set sport Activity section start

    /**
     * It is used to open price rules setup
     * Not used app any location
     * @param sportActivity
     * @param position
     * @param callBackPriceSportActivity
     */
    public void showSetSportPriceRulesDialog(final SportActivity sportActivity, final int position, final CallBackPriceSportActivity callBackPriceSportActivity) {
        if (dialogSetSportActivityGroup != null) {
            if (dialogSetSportActivityGroup.isShowing()) {
                dialogSetSportActivityGroup.dismiss();
            }
        }

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_price_rules, null, false);

        final CheckBox checkBoxSelectRules;
        final AppCompatEditText editTextValuePer;
        final AppCompatEditText editTextValuePart;
        final AppCompatEditText editTextValuePrice;
        final AppCompatEditText editTextValueDuration;

        checkBoxSelectRules = view.findViewById(R.id.checkBoxSelectRules);
        editTextValuePer = view.findViewById(R.id.editTextValuePer);
        editTextValuePart = view.findViewById(R.id.editTextValuePart);
        editTextValuePrice = view.findViewById(R.id.editTextValuePrice);
        editTextValueDuration = view.findViewById(R.id.editTextValueDuration);

        AppCompatButton buttonOk = view.findViewById(R.id.buttonOk);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSetSportActivityGroup.dismiss();

                sportActivity.setGroup(checkBoxSelectRules.isChecked());
                sportActivity.setPrice(editTextValuePrice.getText().toString());
                sportActivity.setPricePerPerPerson(editTextValuePer.getText().toString());
                sportActivity.setAddParticipants(editTextValuePart.getText().toString());
                sportActivity.setMinimumDuration(editTextValueDuration.getText().toString());

                callBackPriceSportActivity.onCall(sportActivity, position);


            }
        });


        editTextValuePer.setEnabled(checkBoxSelectRules.isChecked());
        editTextValuePart.setEnabled(checkBoxSelectRules.isChecked());

        editTextValuePart.setSelected(checkBoxSelectRules.isChecked());
        editTextValuePer.setSelected(checkBoxSelectRules.isChecked());


        checkBoxSelectRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextValuePer.setEnabled(checkBoxSelectRules.isChecked());
                editTextValuePart.setEnabled(checkBoxSelectRules.isChecked());

                editTextValuePart.setSelected(checkBoxSelectRules.isChecked());
                editTextValuePer.setSelected(checkBoxSelectRules.isChecked());

                editTextValuePart.setText(!checkBoxSelectRules.isChecked() ? "" : "");
                editTextValuePer.setText(!checkBoxSelectRules.isChecked() ? "" : "");

            }
        });

        dialogSetSportActivityGroup = new Dialog(getActivity());

        dialogSetSportActivityGroup.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogSetSportActivityGroup.setCancelable(true);

        dialogSetSportActivityGroup.getWindow().setBackgroundDrawableResource(R.drawable.drwable_background_tra);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        dialogSetSportActivityGroup.setContentView(view);

        dialogSetSportActivityGroup.show();
    }


    /**
     * It is used to open price rules setup
     * Not used app any location
     */
    public void showSetSportSubPriceRulesDialog(final SportSubActivity sportActivity, final int position, final CallBackPriceSportSubActivity callBackPriceSportSubActivity) {


        if (dialogSetSportSubActivityGroup != null) {
            if (dialogSetSportSubActivityGroup.isShowing()) {
                dialogSetSportSubActivityGroup.dismiss();
            }
        }

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_price_rules, null, false);

        final CheckBox checkBoxSelectRules;
        final AppCompatEditText editTextValuePer;
        final AppCompatEditText editTextValuePart;
        final AppCompatEditText editTextValuePrice;
        final AppCompatEditText editTextValueDuration;

        checkBoxSelectRules = view.findViewById(R.id.checkBoxSelectRules);
        editTextValuePer = view.findViewById(R.id.editTextValuePer);
        editTextValuePart = view.findViewById(R.id.editTextValuePart);
        editTextValuePrice = view.findViewById(R.id.editTextValuePrice);
        editTextValueDuration = view.findViewById(R.id.editTextValueDuration);

        AppCompatButton buttonOk = view.findViewById(R.id.buttonOk);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSetSportSubActivityGroup.dismiss();

                sportActivity.setGroup(checkBoxSelectRules.isChecked());
                sportActivity.setPrice(editTextValuePrice.getText().toString());
                sportActivity.setPricePerPerPerson(editTextValuePer.getText().toString());
                sportActivity.setAddParticipants(editTextValuePart.getText().toString());
                sportActivity.setMinimumDuration(editTextValueDuration.getText().toString());

                callBackPriceSportSubActivity.onCall(sportActivity, position);


            }
        });


        editTextValuePer.setEnabled(checkBoxSelectRules.isChecked());
        editTextValuePart.setEnabled(checkBoxSelectRules.isChecked());

        editTextValuePart.setSelected(checkBoxSelectRules.isChecked());
        editTextValuePer.setSelected(checkBoxSelectRules.isChecked());


        checkBoxSelectRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextValuePer.setEnabled(checkBoxSelectRules.isChecked());
                editTextValuePart.setEnabled(checkBoxSelectRules.isChecked());

                editTextValuePart.setSelected(checkBoxSelectRules.isChecked());
                editTextValuePer.setSelected(checkBoxSelectRules.isChecked());

                editTextValuePart.setText(!checkBoxSelectRules.isChecked() ? "" : "");
                editTextValuePer.setText(!checkBoxSelectRules.isChecked() ? "" : "");

            }
        });

        dialogSetSportSubActivityGroup = new Dialog(getActivity());

        dialogSetSportSubActivityGroup.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogSetSportSubActivityGroup.setCancelable(true);

        dialogSetSportSubActivityGroup.getWindow().setBackgroundDrawableResource(R.drawable.drwable_background_tra);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        dialogSetSportSubActivityGroup.setContentView(view);

        dialogSetSportSubActivityGroup.show();
    }

    public interface CallBackPriceSportActivity {
        void onCall(SportActivity sportActivity, int position);
    }

    public interface CallBackPriceSportSubActivity {
        void onCall(SportSubActivity sportActivity, int position);
    }


    //Signup step skip

    /**
     * It is used to skip singup step
     * @param signupStep
     * @param callBackSignupStep
     */
    public void skipSignUpStep(String signupStep, final CallBackSignupStep callBackSignupStep) {
        showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("signup_step", signupStep);

        koolocoRepository.skipSignupStep(map).subscribe(new SubscribeWithView<Response<User>>(this) {
            @Override
            public void onSuccess(Response<User> userResponse) {
                hideLoader();
                callBackSignupStep.onSuccess();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                hideLoader();
            }
        });
    }

    public interface CallBackSignupStep {
        void onSuccess();
    }

    /**
     * It is used to open certification and achievement details dialog
     * @param certificateInfo
     */
    public void openInfoCertficates(CertificateInfo certificateInfo) {

        AppCompatTextView toolbarTitle;

        ImageView imageViewCertificate;

        AppCompatTextView customTextViewCertificateName;

        AppCompatTextView customTextViewCertificateDesc, customTextViewSport, customTextViewTag;

        LinearLayout linearLayoutTag, linearLayoutSport;

        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_visitor_certificates, null, false);
        dialog.setContentView(view);

        ImageView imageViewClose = view.findViewById(R.id.imageViewClose);

        toolbarTitle = view.findViewById(R.id.toolbarTitle);

        customTextViewCertificateName = view.findViewById(R.id.customTextViewCertificateName);

        customTextViewCertificateDesc = view.findViewById(R.id.customTextViewCertificateDesc);

        customTextViewSport = view.findViewById(R.id.customTextViewSport);

        customTextViewTag = view.findViewById(R.id.customTextViewTag);

        linearLayoutSport = view.findViewById(R.id.linearLayoutSport);

        linearLayoutTag = view.findViewById(R.id.linearLayoutTag);

        FlowLayout flowLayout = view.findViewById(R.id.flowLayout);

        linearLayoutSport.setVisibility(certificateInfo.getType().isEmpty() ? View.GONE : View.VISIBLE);

        linearLayoutTag.setVisibility((certificateInfo.getTags() == null || certificateInfo.getTags().size() == 0) ? View.GONE : View.VISIBLE);

        if (certificateInfo.getSubType().isEmpty()) {
            customTextViewSport.setText(certificateInfo.getType());
        } else {
            customTextViewSport.setText(certificateInfo.getSubType() + " " + certificateInfo.getType());
        }

        String tag = "";

        if (certificateInfo.getTags() != null) {
            for (Tag tagTemp : certificateInfo.getTags()) {

                if (tag.isEmpty()) {

                    tag = tag + tagTemp.getName();

                } else {
                    tag = tag + ", " + tagTemp.getName();

                }
            }

        }


        customTextViewTag.setText(tag);

        flowLayout.removeAllViews();


        for (Tag tagDe : certificateInfo.getTags()) {

            View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.item_time, null);
            ((AppCompatTextView) view1.findViewById(R.id.customTextViewTag)).setText(tagDe.getName());


            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View viewD) {

                }
            });

            flowLayout.addView(view1);
        }

        imageViewCertificate = view.findViewById(R.id.imageViewCertificate);
        toolbarTitle.setText(certificateInfo.getName());
        customTextViewCertificateName.setText(certificateInfo.getName());
        customTextViewCertificateDesc.setText(certificateInfo.getDesc());


        Picasso.with(getActivity()).load(certificateInfo.getImageUrl()).into(imageViewCertificate);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        customTextViewCertificateDesc.setVisibility(certificateInfo.getDesc().isEmpty() ? View.GONE : View.VISIBLE);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                BottomSheetDialog d = (BottomSheetDialog) dialog;

                FrameLayout bottomSheet = (FrameLayout) d.findViewById(android.support.design.R.id.design_bottom_sheet);

                BottomSheetBehavior.from(bottomSheet)
                        .setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        dialog.show();
    }

    /**
     * It is used to get dif. start time and end time
     * @param stratTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public String getTimeDiffrent(String stratTime, String endTime) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        Date dt1 = format.parse(stratTime);
        Date dt2 = format.parse(endTime);

        long diff = dt2.getTime() - dt1.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);


        String min = ("" + diffMinutes).replace("-", "");
        String sec = ("" + diffSeconds).replace("-", "");
        String hou = "" + diffHours;

        if (hou.length() == 1) {
            hou = "0" + hou;
        }

        if (min.length() == 1) {
            min = "0" + min;
        }
        if (sec.length() == 1) {
            sec = "0" + sec;
        }

/*
        if (diffHours < 0) {
            return min + "minutes";
        } else {
            if (diffMinutes < 0) {
                return hou + "hours";
            } else {
                return hou + "hours " + min + " ";
            }
        }
*/

        if (diffHours < 0 || diffSeconds < 0 || diffMinutes < 0) {
            return "--:--";
        } else {
            return hou + ":" + min;
        }

    }

    /**
     * It is used to upload image whole app
     *
     * @param fileName             Set file name
     * @param uploadType           set upload type images (e.g:- signup,certificates etc)
     * @param callBackUploadImages return call back
     */

    @Override
    public void uploadImages(String fileName, String uploadType, final CallBackUploadImages callBackUploadImages) {

        if (fileName.isEmpty()) {
            callBackUploadImages.onCallBack("");
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("type", uploadType);

        Map<String, RequestBody> requestBodyMap = new HashMap<>();

        requestBodyMap.clear();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            requestBodyMap.put(entry.getKey(), createStringPart(entry.getValue()));
        }

        MultipartBody.Part body = null;

        if (!fileName.isEmpty()) {
            File file = new File(fileName);
       /*     RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

            body = MultipartBody.Part.createFormData("image", file.getName(), new ProgressRequestBody(requestFile, showDialogProgress()));
*/

            String folderPath = "";


            switch (uploadType) {
                case "uploadAchievement":
                    folderPath = Util.BUCKET_LOCAL_ACHIVEMENT;
                    break;
                case "addCertificate":
                    folderPath = Util.BUCKET_LOCAL_CERTIFICATES;
                    break;
                case "addImages":
                    folderPath = Util.BUCKET_LOCAL_SPORT_IMAGES;
                    break;
                case "signup":
                case "update_profile":
                    folderPath = Util.BUCKET_PROFILE_IMAGE;
                    break;
                case "createOrganisation":
                case "editOrganisation":
                    folderPath = Util.BUCKET_ORGANIZATION;
                    break;
            }

            String userId = "";

            if (session.getUser() != null) {
                if (session.getUser().getId() != null) {
                    userId = session.getUser().getId();
                }
            }

            uploads3(folderPath, userId + "_" + file.getName(), file, callBackUploadImages);

        }


       /* koolocoRepository.uploadImage(requestBodyMap, body).subscribe(new SubscribeWithView<Response<UploadImage>>(this) {
            @Override
            public void onSuccess(Response<UploadImage> uploadImageResponse) {
                Log.e(":::Per", "Upload images");

                if (dialogProgress != null) {
                    if (dialogProgress.isShowing()) {
                        dialogProgress.dismiss();
                    }
                }

                callBackUploadImages.onCallBack(uploadImageResponse.getData().getImage());

            }

            @Override
            public void onError(Throwable e) {
                Log.e(":::Per", "Upload images error");

                super.onError(e);
                callBackUploadImages.onCallBack("");
            }
        });
*/
    }

    /**
     * It is used to get upload image call back
     */
    public interface CallBackUploadImages {
        /**
         * It is used to get callback upload images
         *
         * @param imagePath return image path
         */
        void onCallBack(String imagePath);

    }

    private RequestBody createStringPart(String data) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, data);
    }


    public static Double roundDouble(Double value) {
        double v = value;
        double y = Math.round(100 * v) / 100d;
        return y;
    }


    public static String convertFormat(Double value) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("0.00");
        String output = df.format(value);
        return output;
    }


    public static String convertFormatNew(Double value) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat df = (DecimalFormat) nf;
        df.setRoundingMode(RoundingMode.DOWN);
        df.applyPattern("0.00");
        String output = df.format(value);
        return output;
    }

    /*
       Local side Accept, Reject, Complete,cancel order api calling section Start
    */

    /**
     * It is used to local side all notification action
     * @param order
     * @param localOrderAction
     * @param callBackOrderAction
     */
    @Override
    public void localOrderAction(Order order, LocalOrderAction localOrderAction, CallBackOrderAction callBackOrderAction) {
        showLoader();
        SubscribeWithView<Response> responseSubscribeWithView = new SubscribeWithView<Response>(this) {
            @Override
            public void onSuccess(Response response) {
                hideLoader();
                callBackOrderAction.onSuccess(response);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                hideLoader();
                callBackOrderAction.onError(e.getMessage());
            }
        };
        Map<String, String> map = new HashMap<>();

        map.put("user_id", session.getUser().getId());
        map.put("order_id", order.getId());

        switch (localOrderAction) {
            case ACCEPT:
                koolocoRepository.acceptOrder(map).subscribe(responseSubscribeWithView);
                break;
            case REJECT:
                koolocoRepository.rejectOrder(map).subscribe(responseSubscribeWithView);
                break;
            case COMPLETE:
                koolocoRepository.complateOrder(map).subscribe(responseSubscribeWithView);
                break;
            case CANCEL:
                koolocoRepository.cancelOrder(map).subscribe(responseSubscribeWithView);
                break;
            case SHOWPAYMENTREQUEST:
                koolocoRepository.sendPaymentRequest(map).subscribe(responseSubscribeWithView);
                break;
        }
    }

    @Override
    public void filterData(CallBackFilter callBackFilter, boolean isClear, boolean isHome) {
        // openFilter(Temp.getStaticFilter(), callBackFilter, isClear);

        showLoader();
        Map<String, String> map = new HashMap<>();

        koolocoRepository.filterData(map).subscribe(new SubscribeWithView<Response<FilterGetData>>(this) {
            @Override
            public void onSuccess(Response<FilterGetData> filterGetDataResponse) {
                openFilter(filterGetDataResponse.getData(), callBackFilter, isClear, isHome);
                hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                hideLoader();
            }
        });
    }

    /*
    Local side Accept, Reject, Complete,cancel order api calling section End
    */

    /**
     * It is used to get call back order action
     */
    public interface CallBackOrderAction {

        void onSuccess(Response response);

        void onError(String message);

    }

    /**
     * It is used to accept objection local
     * @param notificationId
     * @param callBackOrderAction
     */
    public void localSendObjectionAction(String notificationId, CallBackOrderAction callBackOrderAction) {
        showLoader();
        SubscribeWithView<Response> responseSubscribeWithView = new SubscribeWithView<Response>(this) {
            @Override
            public void onSuccess(Response response) {
                hideLoader();
                callBackOrderAction.onSuccess(response);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                hideLoader();
                callBackOrderAction.onError(e.getMessage());
            }
        };
        Map<String, String> map = new HashMap<>();

        map.put("user_id", session.getUser().getId());
        map.put("notification_id", notificationId);

        koolocoRepository.acceptObjection(map).subscribe(responseSubscribeWithView);
    }

    /**
     * It is used to send objection
     * @param notificationId
     * @param callBackOrderAction
     */
    public void visitorSendObjectionAction(String notificationId, CallBackOrderAction callBackOrderAction) {
        showLoader();
        SubscribeWithView<Response> responseSubscribeWithView = new SubscribeWithView<Response>(this) {
            @Override
            public void onSuccess(Response response) {
                showMessage(response.getMessage());
                hideLoader();
                callBackOrderAction.onSuccess(response);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                hideLoader();
                callBackOrderAction.onError(e.getMessage());
            }
        };
        Map<String, String> map = new HashMap<>();

        map.put("user_id", session.getUser().getId());
        map.put("notification_id", notificationId);

        koolocoRepository.acceptObjectionVisitor(map).subscribe(responseSubscribeWithView);
    }


    /**
     * It is used to send objection admin
     * @param notificationId
     * @param callBackOrderAction
     */
    public void visitorSendObjectionActionAdmin(String notificationId, CallBackOrderAction callBackOrderAction) {
        showLoader();
        SubscribeWithView<Response> responseSubscribeWithView = new SubscribeWithView<Response>(this) {
            @Override
            public void onSuccess(Response response) {
                showMessage(response.getMessage());
                hideLoader();
                callBackOrderAction.onSuccess(response);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                hideLoader();
                callBackOrderAction.onError(e.getMessage());
            }
        };
        Map<String, String> map = new HashMap<>();

        map.put("notification_id", notificationId);

        koolocoRepository.adminObjectionRequest(map).subscribe(responseSubscribeWithView);

    }

    /**
     * It is used to accept action
     * @param notificationId
     * @param localOrderAction
     * @param callBackOrderAction
     */
    public void visitorModifyAction(String notificationId, LocalOrderAction localOrderAction, CallBackOrderAction callBackOrderAction) {
        showLoader();
        SubscribeWithView<Response> responseSubscribeWithView = new SubscribeWithView<Response>(this) {
            @Override
            public void onSuccess(Response response) {
                showMessage(response.getMessage());
                hideLoader();
                callBackOrderAction.onSuccess(response);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                hideLoader();
                callBackOrderAction.onError(e.getMessage());
            }
        };
        Map<String, String> map = new HashMap<>();

        map.put("user_id", session.getUser().getId());
        map.put("notification_id", notificationId);

        switch (localOrderAction) {
            case ACCEPT:
                koolocoRepository.acceptLocationDuration(map).subscribe(responseSubscribeWithView);
                break;
            case REJECT:
                koolocoRepository.declineLocationDuration(map).subscribe(responseSubscribeWithView);
                break;
            case NOTIFY:
                koolocoRepository.notifyAction(map).subscribe(responseSubscribeWithView);
                break;
        }
    }

    /**
     * It is used to upload single video and image
     * @param fileNames
     * @param videoThum
     * @param type
     * @param callBackUploadImages
     */

    public void uploadImagesVideo(List<String> fileNames, List<String> videoThum, String type, final CallBackUploadImagesMulti callBackUploadImages) {

        if (fileNames.isEmpty() && videoThum.isEmpty()) {
            showLoader();
            callBackUploadImages.onCallBack(new ArrayList<>());
            return;
        }

        //showLoader();
     /*   HashMap<String, String> map = new HashMap<>();
        map.put("type", type);

        Map<String, RequestBody> requestBodyMap = new HashMap<>();

        requestBodyMap.clear();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            requestBodyMap.put(entry.getKey(), createStringPart(entry.getValue()));
        }


        List<MultipartBody.Part> parts = new ArrayList<>();


        for (String fileName : fileNames) {
            if (!fileName.isEmpty()) {
                File file = new File(fileName);
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("image/jpeg"), file);

                parts.add(MultipartBody.Part.createFormData("file[]", file.getName(), requestFile));
            }

        }

        for (String fileName : videoThum) {
            if (!fileName.isEmpty()) {
                File file = new File(fileName);
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("image/jpeg"), file);
                parts.add(MultipartBody.Part.createFormData("video_thumb[]", file.getName(), requestFile));
            }

        }
*/

        String directory = "";
        switch (type) {
            case "createOrganisation":
            case "editOrganisation":
                directory = Util.BUCKET_ORGANIZATION;
                break;
            case "publishExperience":
                directory = Util.BUCKET_BLOG;
                break;
            case "experienceImages":
                directory = Util.BUCKET_LOCAL_EXP_IMAGES;
                break;
            case "experienceOtherImages":
                directory = Util.BUCKET_LOCAL_EXP_OTHER_FIELDS_IMAGES;
                break;
        }

        openMultiOpenDialog(directory, fileNames, callBackUploadImages);

/*
        koolocoRepository.uploadFile(requestBodyMap, parts).subscribe(new SubscribeWithView<Response<List<MultiFile>>>(this) {
            @Override
            public void onSuccess(Response<List<MultiFile>> uploadImageResponse) {
                hideLoader();
                callBackUploadImages.onCallBack(uploadImageResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                // super.onError(e);
                //   callBackUploadImages.onCallBack("");
                hideLoader();
                showMessage(e.getMessage());
            }
        });
*/

    }

    /**
     * It is used to get upload image call back
     */
    public interface CallBackUploadImagesMulti {
        /**
         * It is used to get callback upload images
         *
         * @param imagePath return image path
         */
        void onCallBack(List<MultiFile> imagePath);

    }

    public void playVideo(String path) {
        try {
            String newVideoPath = path;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newVideoPath));
            intent.setDataAndType(Uri.parse(newVideoPath), "video/mp4");
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_NEW) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                filterLatitude = place.getLatLng().latitude;
                filterLongitude = place.getLatLng().longitude;
                setAddress = "";
                setLocation(place.getAddress().toString());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());
                filterLatitude = latitude;
                filterLongitude = longitude;
                filterCity = "";
                filterCountry = "";
                setLocation("");

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                filterLatitude = latitude;
                filterLongitude = longitude;
                filterCity = "";
                filterCountry = "";
                setLocation("");
            }
        }
    }

    /**
     * It is used to set location
     *
     * @param tetx
     */
    private void setLocation(String tetx) {
        setAddress = tetx;
        if (customTextViewLocation != null) {
            customTextViewLocation.setText(tetx);
        }

        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;

        try {
            address = coder.getFromLocationName(tetx, 5);
            if (address == null) {
                return;
            }

            if (address.size() != 0) {
                Address location = address.get(0);

                filterCity = "" + location.getLocality();
                if (filterCity.contains("null")) {
                    filterCity = "";
                }
                filterCountry = "" + location.getCountryName();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * It is used to open zoom image
     * @param imageUrl
     */
    public void imageOpenZoom(String imageUrl) {
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_image_view, null, false);

        final TouchImageView imageViewPic = (TouchImageView) view.findViewById(R.id.imageViewPic);

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setGravity(Gravity.CENTER);

        imageViewPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        if (!imageUrl.isEmpty()) {
            Picasso.with(getActivity()).load(imageUrl).error(R.drawable.place).placeholder(R.drawable.place).into(imageViewPic, new Callback() {
                @Override
                public void onSuccess() {
                    dialog.show();
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                }

                @Override
                public void onError() {
                    dialog.show();
                    dialog.dismiss();
                }
            });
        } else {
            Picasso.with(getActivity()).load(R.drawable.place).error(R.drawable.place).placeholder(R.drawable.place).into(imageViewPic);
        }
    }


    /**
     * It is use to get uniq_id
     *
     * @param id1 Sender id
     * @param id2 Receiver id
     * @return
     */
    public String getDocumentIdForDatabse(String id1, String id2) {
        String ids;

        ids = "@" + Math.min(Integer.parseInt(id1), Integer.parseInt(id2)) + "@" + Math.max(Integer.parseInt(id1), Integer.parseInt(id2));

        return ids;
    }


    /**
     * It is used to delete recent chat
     * @param orderId
     */
    @Override
    public void deleteRecentChat(String orderId) {

        getDatabase().collection(Common.FireStore.TAB_NAME_RECENT_CHAT).whereEqualTo(Common.FireStore.FIELD_CHAT_ORDER_ID, orderId)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                for (DocumentSnapshot documentSnapshot : documentSnapshots.getDocuments()) {

                    getDatabase().collection(Common.FireStore.TAB_NAME_RECENT_CHAT).document(documentSnapshot.getId())
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error deleting document", e);
                                }
                            });

                }
            }
        });
    }


    @Override
    public void clearAllNotification(String name) {

        try {
            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * It is used to push to call become local api
     */

    public void callWsBecomeLocal(CallBackMainActivity callBackMainActivity) {
        showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        koolocoRepository.setBecomeLocal(map).subscribe(new SubscribeWithView<Response<User>>(this) {
            @Override
            public void onSuccess(Response<User> userResponse) {
                hideLoader();
                if (callBackMainActivity != null) {
                    callBackMainActivity.onSuccess(true);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                hideLoader();
                if (callBackMainActivity != null) {
                    callBackMainActivity.onSuccess(false);
                }
            }
        });

    }

    public interface CallBackMainActivity {
        void onSuccess(boolean status);
    }

    /**
     * It is used to display delete dialog
     * @param message
     * @param callBackDeleteAlert
     */
    public void showDialogDeleteWithAnimation(String message, CallBackDeleteAlert callBackDeleteAlert) {

        if (dialogAlertDelete != null) {
            if (dialogAlertDelete.isShowing()) {
                dialogAlertDelete.dismiss();
            }
        }

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_delete_popup, null, false);

        AppCompatButton appCompatButtonNo = view.findViewById(R.id.buttonNo);
        AppCompatButton appCompatButtonYes = view.findViewById(R.id.buttonYes);

        AppCompatTextView textViewDeleteMessage = view.findViewById(R.id.textViewDeleteMessage);

        if (message.isEmpty()) {
            textViewDeleteMessage.setVisibility(View.GONE);
        } else {
            textViewDeleteMessage.setVisibility(View.VISIBLE);
            textViewDeleteMessage.setText(message);
        }

        appCompatButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAlertDelete.dismiss();
                callBackDeleteAlert.onSuccess(false);
            }
        });

        appCompatButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAlertDelete.dismiss();
                callBackDeleteAlert.onSuccess(true);
            }
        });

        dialogAlertDelete = new Dialog(getActivity());

        dialogAlertDelete.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogAlertDelete.setCancelable(true);

        dialogAlertDelete.getWindow().setBackgroundDrawableResource(R.drawable.drwable_background_tra);

        dialogAlertDelete.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        dialogAlertDelete.setContentView(view);

        dialogAlertDelete.show();


    }


    //It is used to show upload data

    public interface CallBackDeleteAlert {
        void onSuccess(boolean isSuccess);
    }

    /**
     * It is used to display delete dialog
     * @param title
     * @param message
     * @param callBackDeleteAlert
     */
    public void showDialogDeleteWithAnimation(String title, String message, CallBackDeleteAlert callBackDeleteAlert) {

        if (dialogAlertDelete != null) {
            if (dialogAlertDelete.isShowing()) {
                dialogAlertDelete.dismiss();
            }
        }

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_delete_popup, null, false);

        AppCompatButton appCompatButtonNo = view.findViewById(R.id.buttonNo);
        AppCompatButton appCompatButtonYes = view.findViewById(R.id.buttonYes);

        AppCompatTextView textViewDeleteMessage = view.findViewById(R.id.textViewDeleteMessage);
        AppCompatTextView textViewTitle = view.findViewById(R.id.textViewTitle);

        if (message.isEmpty()) {
            textViewDeleteMessage.setVisibility(View.GONE);
        } else {
            textViewDeleteMessage.setVisibility(View.VISIBLE);
            textViewDeleteMessage.setText(message);
        }

        if (!title.isEmpty()) {
            textViewTitle.setVisibility(View.VISIBLE);
            textViewTitle.setText(title);
        } else {
            textViewTitle.setVisibility(View.GONE);
        }

        appCompatButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAlertDelete.dismiss();
                callBackDeleteAlert.onSuccess(false);
            }
        });

        appCompatButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAlertDelete.dismiss();
                callBackDeleteAlert.onSuccess(true);
            }
        });

        dialogAlertDelete = new Dialog(getActivity());

        dialogAlertDelete.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogAlertDelete.setCancelable(true);

        dialogAlertDelete.getWindow().setBackgroundDrawableResource(R.drawable.drwable_background_tra);

        dialogAlertDelete.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        dialogAlertDelete.setContentView(view);

        dialogAlertDelete.show();


    }

    /**
     * It is used to display with ok button
     * @param message
     * @param buttonName
     * @param callBackDeleteAlert
     */
    public void showDialogWithOkButton(String message, String buttonName, CallBackDeleteAlert callBackDeleteAlert) {
        if (dialogSingalAction != null) {
            if (dialogSingalAction.isShowing()) {
                dialogSingalAction.dismiss();
            }
        }

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_un_complete_exp, null, false);

        AppCompatButton appCompatButtonNo = view.findViewById(R.id.buttonNo);
        AppCompatButton appCompatButtonYes = view.findViewById(R.id.buttonYes);
        appCompatButtonYes.setText(buttonName);

        AppCompatTextView textViewTitle = view.findViewById(R.id.textViewTitle);

        textViewTitle.setText(message);

        appCompatButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSingalAction.dismiss();
                callBackDeleteAlert.onSuccess(false);

            }
        });

        appCompatButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSingalAction.dismiss();
                callBackDeleteAlert.onSuccess(true);

            }
        });

        dialogSingalAction = new Dialog(getActivity());

        dialogSingalAction.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogSingalAction.setCancelable(true);

        dialogSingalAction.getWindow().setBackgroundDrawableResource(R.drawable.drwable_background_tra);

        dialogSingalAction.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        dialogSingalAction.setContentView(view);

        dialogSingalAction.show();


    }

    /**
     * It is used to hide uploading image dialog
     */
    @Override
    public void hideUploadImageDialog() {
        if (dialogProgressSingal != null) {
            if (dialogProgressSingal.isShowing()) {
                if (textViewCount != null) {
                    textViewCount.setText("" + 100);
                }
                if (progressBar1 != null) {
                    progressBar1.setProgress(100);
                }
                dialogProgressSingal.dismiss();
            }
        }
    }

    /**
     * It is used to upload image s3 bucket
     *
     * @param objectKey
     * @param fileName
     * @param originalFile
     * @param callBackUploadImages
     */
    public void uploads3(final String objectKey,
                         final String fileName, final File originalFile, CallBackUploadImages callBackUploadImages) {

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.progress_upload_dialog, null, false);


        textViewCount = view.findViewById(R.id.textViewCount);
        progressBar1 = view.findViewById(R.id.progressBar1);
        imageViewUpload = view.findViewById(R.id.imageViewUpload);

        dialogProgressSingal = new Dialog(getActivity());

        dialogProgressSingal.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogProgressSingal.setCancelable(false);

        dialogProgressSingal.getWindow().setBackgroundDrawableResource(R.drawable.drwable_background_tra);

        dialogProgressSingal.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        dialogProgressSingal.setContentView(view);

        if (Utils.isVideoFile(originalFile.getAbsolutePath())) {
            Bitmap compressedThumbFile = ThumbnailUtils.createVideoThumbnail(originalFile.getAbsolutePath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
            imageViewUpload.setImageBitmap(compressedThumbFile);
        } else {
            Bitmap compressedThumbFile = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(originalFile.getAbsolutePath()), 200, 200);
            imageViewUpload.setImageBitmap(compressedThumbFile);
        }

        transferUtility = Util.getTransferUtility(getActivity());
        TransferObserver observer = transferUtility.upload(objectKey,
                fileName, originalFile, CannedAccessControlList.PublicRead);

        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                Log.e("AMZON", "onStateChanged: " + id + ", " + state);
                if (state == TransferState.IN_PROGRESS) {
                    if (!Util.BUCKET_PROFILE_IMAGE.equalsIgnoreCase(objectKey)) {
                        dialogProgressSingal.show();
                    }
                }
                if (state == TransferState.COMPLETED) {
                    //showLoader();
                    Bitmap compressedThumbFile = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(originalFile.getAbsolutePath()), 200, 200);
                    uploads3Thum(objectKey + "/thum", fileName, Utils.saveImage(compressedThumbFile));
                    callBackUploadImages.onCallBack(fileName);

                    //   dialogProgressSingal.dismiss();

                    Log.e("AMZON", "onComplete: " + fileName);


                } else if (state == TransferState.FAILED) {
                    // callback.onFailed();
                    dialogProgressSingal.dismiss();
                    // callBackUploadImages.onCallBack("");
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

                int percentage = (int) (100F * bytesCurrent / bytesTotal);
                Log.e("AMZON_PROGRESS_CHANGE", String.format("onProgressChanged: %d, total: %d, current: %d per: %d",
                        id, bytesTotal, bytesCurrent, percentage));

                if (dialogProgressSingal.isShowing()) {
                    int perTemp = 0;

                    if (percentage == 100) {
                        perTemp = percentage - 1;
                    } else {
                        perTemp = percentage;
                    }

                    if (textViewCount != null) {
                        textViewCount.setText("" + perTemp + "%");
                    }
                    if (progressBar1 != null) {
                        // progressBar1.setProgress(perTemp);

                        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar1, "progress", perTemp);
                        animation.setDuration(500); // 0.5 second
                        animation.setInterpolator(new DecelerateInterpolator());
                        animation.start();
                    }
                }

/*
                if (percentage == 100) {

                }
*/

            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e("AMZONERROR", "Error during upload: " + id + ex.toString());
                dialogProgressSingal.dismiss();
                showMessage(ex.getMessage().toString());
            }
        });
    }

    /**
     * It is used to upload thum image
     *
     * @param objectKey
     * @param fileName
     * @param originalFile
     */
    public void uploads3Thum(final String objectKey,
                             final String fileName, final File originalFile) {

        try {
            transferUtility = Util.getTransferUtility(getActivity());
            TransferObserver observer = transferUtility.upload(objectKey,
                    fileName, originalFile, CannedAccessControlList.PublicRead);

            observer.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    Log.e("AMZON THUM", "onStateChanged: " + id + ", " + state);

                    if (state == TransferState.IN_PROGRESS) {

                    }

                    if (state == TransferState.COMPLETED) {


                    } else if (state == TransferState.FAILED) {

                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

                    int percentage = (int) (100F * bytesCurrent / bytesTotal);
                    Log.e("AMZON_PROGRESS THUM", String.format("onProgressChanged: %d, total: %d, current: %d per: %d",
                            id, bytesTotal, bytesCurrent, percentage));
                }

                @Override
                public void onError(int id, Exception ex) {
                    Log.e("AMZONERROR THUM", "Error during upload: " + id + ex.toString());

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * It is used to upload multipaleimage
     * @param folderLocation
     * @param fileList
     * @param callBackUploadImages
     */
    private void openMultiOpenDialog(String folderLocation, List<String> fileList, CallBackUploadImagesMulti callBackUploadImages) {

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.progress_upload_dialog_multipale, null, false);

        RecyclerView recyclerProgress = view.findViewById(R.id.recyclerProgress);

        Dialog dialogProgress = new Dialog(getActivity());

        dialogProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogProgress.setCancelable(false);

        dialogProgress.getWindow().setBackgroundDrawableResource(R.drawable.drwable_background_tra);

        dialogProgress.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        dialogProgress.setContentView(view);

        String userId = "";
        if (session.getUser() != null) {
            if (session.getUser().getId() != null) {
                userId = session.getUser().getId();
            }
        }

   /*     MultipalImageUpload multipalImageUpload = new MultipalImageUpload(getActivity(), fileList, folderLocation, userId, new MultipalImageUpload.CallBack() {
            @Override
            public void onSuccess(List<MultiFile> multiFiles) {
                dialogProgress.dismiss();
                showLoader();
                callBackUploadImages.onCallBack(multiFiles);

            }

            @Override
            public void onError(String message) {
                dialogProgress.dismiss();
                showMessage(message);
            }
        });
        recyclerProgress.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerProgress.setAdapter(multipalImageUpload);*/

        //This code for new implement
        List<Integer> per = new ArrayList<>();

        for (int i = 0; i < fileList.size(); i++) {
            per.add(0);
        }

        MultipalImageUploadNew multipalImageUpload = new MultipalImageUploadNew(getActivity(), fileList, per);
        recyclerProgress.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerProgress.setAdapter(multipalImageUpload);

        uploadFile(folderLocation, userId, fileList, 0, multipalImageUpload, new MultipalImageUpload.CallBack() {
            @Override
            public void onSuccess(List<MultiFile> multiFiles) {
                dialogProgress.dismiss();
                showLoader();
                callBackUploadImages.onCallBack(multiFiles);
            }

            @Override
            public void onError(String message) {
                dialogProgress.dismiss();
                showMessage(message);
            }
        });
        dialogProgress.show();
    }

    /* It is used to upload file*
     */

    /**
     * It is used to upload one by one file
     * @param folderLocation
     * @param userId
     * @param files
     * @param pos
     * @param multipalImageUploadNew
     * @param callBack
     */
    private void uploadFile(String folderLocation, String userId, List<String> files, int pos, MultipalImageUploadNew multipalImageUploadNew, MultipalImageUpload.CallBack callBack) {

        if (pos == 0) {
            if (imagePath == null) {
                imagePath = new ArrayList<>();
            } else {
                imagePath.clear();
            }
        }


        File file = new File(files.get(pos));

        TransferUtility transferUtility = Util.getTransferUtility(getActivity());

        TransferObserver observer = transferUtility.upload(folderLocation, userId + "_" + file.getName(), file, CannedAccessControlList.PublicRead);

        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                Log.e("AMZON", "onStateChanged: " + id + ", " + state);
                if (state == TransferState.IN_PROGRESS) {

                }
                if (state == TransferState.COMPLETED) {
                    Log.e("AMZON", "Upload Complete-->" + pos);

                    if (imagePath.size() == files.size()) {
                        sendCallBack(files, userId, callBack);
                    }

                } else if (state == TransferState.FAILED) {
                    Log.e("AMZON", "Upload FAIL-->" + pos);
                    //sendCallBack();

                    uploadFile(folderLocation, userId, files, pos, multipalImageUploadNew, callBack);

                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

                int percentage = (int) (100F * bytesCurrent / bytesTotal);

                multipalImageUploadNew.setProgres(pos, percentage);

                if (percentage == 100) {

                    if (Utils.isVideoFile(file.getAbsolutePath())) {
                        Bitmap compressedThumbFile = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);

                        String filename = userId + "_" + file.getName();
                        filename = filename.replace(".mp4", ".jpg");

                        uploads3Thum(folderLocation + "/thum", filename, Utils.saveImage(compressedThumbFile));
                    } else {
                        Bitmap compressedThumbFile = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(file.getAbsolutePath()), 200, 200);
                        uploads3Thum(folderLocation + "/thum", userId + "_" + file.getName(), Utils.saveImage(compressedThumbFile));
                    }

                    MultiFile multiFile = new MultiFile();
                    multiFile.setFile(userId + "_" + file.getName());

                    String mediaType = "I";
                    if (Utils.isVideoFile(file.getAbsolutePath())) {
                        mediaType = "V";
                    }

                    multiFile.setMediaType(mediaType);

                    if (Utils.isVideoFile(file.getAbsolutePath())) {

                        String filename = userId + "_" + file.getName();
                        filename = filename.replace(".mp4", ".jpg");

                        multiFile.setVidepThumb(filename);
                    }

                    if (!checkIsAllReadyFile(multiFile.getFile())) {
                        imagePath.add(multiFile);
                        if (!(pos == files.size() - 1)) {
                            uploadFile(folderLocation, userId, files, pos + 1, multipalImageUploadNew, callBack);
                        }
                    }


                }
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e("AMZONERROR", "Error during upload: " + id + ex.toString());
            }
        });

    }

    /**
     * It is used to check file is upload or not
     * @param nameFile
     * @return
     */
    private boolean checkIsAllReadyFile(String nameFile) {
        boolean isExists = false;
        for (MultiFile MultiFile : imagePath) {
            if (MultiFile.getFile().equalsIgnoreCase(nameFile)) {
                isExists = true;
                break;
            }
        }

        return isExists;
    }

    /**
     * It is used to send upload image call back
     * @param files
     * @param userId
     * @param callBack
     */
    private void sendCallBack(List<String> files, String userId, MultipalImageUpload.CallBack callBack) {
        List<MultiFile> multiFiles = new ArrayList<>();

        for (String s : files) {
            File file = new File(s);

            MultiFile multiFile = new MultiFile();
            multiFile.setFile(userId + "_" + file.getName());

            String mediaType = "I";
            if (Utils.isVideoFile(file.getAbsolutePath())) {
                mediaType = "V";
            }
            multiFile.setMediaType(mediaType);


            if (Utils.isVideoFile(file.getAbsolutePath())) {

                String filename = userId + "_" + file.getName();
                filename = filename.replace(".mp4", ".jpg");

                multiFile.setVidepThumb(filename);
            }

            multiFiles.add(multiFile);
        }

        if (callBack != null) {
            callBack.onSuccess(multiFiles);
        }
    }


    /**
     * It is used to check expeirnce is delete or disable admin
     * @param expereinceNew
     * @return
     */
    public boolean checkIsDeleteOrActive(ExpereinceNew expereinceNew) {
        boolean isComplete = true;

        if (expereinceNew.getIsDeleted().equalsIgnoreCase("1")) {
            isComplete = false;
            showMessage(getString(R.string.experience_delete_local));
        } else if (expereinceNew.getIsActive().equalsIgnoreCase("0")) {
            isComplete = false;
            showMessage(getString(R.string.experience_disable_val));
        }

        return isComplete;
    }


    /**
     *It is used to open time picker
     * @param onTimeSetListenerNew
     */
    private void openTimePickerFilter(TimePickerDialog.OnTimeSetListener onTimeSetListenerNew) {

        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd;
        if (!DateFormat.is24HourFormat(getActivity())) {
            //12 hrs format
            tpd = TimePickerDialog.newInstance(
                    onTimeSetListenerNew,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE), false);
            tpd.setMaxTime(23, 29, 00);
        } else {
            //24 hrs format
            tpd = TimePickerDialog.newInstance(onTimeSetListenerNew,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE), true);
            tpd.setMaxTime(23, 29, 00);
        }

        tpd.setMaxTime(23, 59, 00);

        tpd.setSelectableTimes(generateTimepoints(24, 15));

        tpd.setAccentColor(R.color.colorAccent);
        tpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
        tpd.setAccentColor(getActivity().getResources().getColor(R.color.colorAccent));
    }

    public void setDot(ImageView imageViewCount) {
        boolean isDisplay = false;
        if (imageViewCount != null) {
            if (getActivity() instanceof MainActivity) {
                isDisplay = ((MainActivity) getActivity()).isDisplayNotificationIcon;
            } else if (getActivity() instanceof MainLocalActivity) {
                isDisplay = ((MainLocalActivity) getActivity()).isDisplayNotificationIcon;
            }

            imageViewCount.setVisibility(isDisplay ? View.VISIBLE : View.INVISIBLE);
        }
    }

    /**
     * It is used to fav experience
     * @param expId
     * @param callBackMainActivity
     */
    public void setFavExp(String expId, CallBackMainActivity callBackMainActivity) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("experience_id", expId);

        koolocoRepository.setFavExp(map).subscribe(new SubscribeWithView<Response<ExpFavData>>(this) {
            @Override
            public void onSuccess(Response<ExpFavData> userResponse) {

                databaseCacheDataSource.setDataFavExp(expId, userResponse.getData().getIsFav());

                new Handler().postDelayed(() -> {
                    EventBus.getDefault().post(EventBusAction.FAVREFRESE);
                    EventBus.getDefault().post(EventBusAction.FAVLISTUPDATE);
                    if (callBackMainActivity != null) {
                        callBackMainActivity.onSuccess(true);
                    }
                }, 200);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (callBackMainActivity != null) {
                    callBackMainActivity.onSuccess(false);
                }
            }
        });
    }


    /**
     * It is used to set time point time picker set difreent 15 minues
     * @param maxHour
     * @param minutesInterval
     * @return
     */
    public static Timepoint[] generateTimepoints(double maxHour, int minutesInterval) {

        int lastValue = (int) (maxHour * 60);

        List<Timepoint> timepoints = new ArrayList<>();

        for (int minute = 0; minute <= lastValue; minute += minutesInterval) {
            int currentHour = minute / 60;
            int currentMinute = minute - (currentHour > 0 ? (currentHour * 60) : 0);
            if (currentHour == 24)
                continue;
            timepoints.add(new Timepoint(currentHour, currentMinute));
        }
        return timepoints.toArray(new Timepoint[timepoints.size()]);
    }


    /**
     * It is used to get end time point
     * @param startHours
     * @param maxHour
     * @param minutesInterval
     * @return
     */
    public static Timepoint[] generateTimepointsEnds(double startHours, double maxHour, int minutesInterval) {

        int startValue = (int) (startHours * 60);

        int lastValue = (int) (maxHour * 60);

        List<Timepoint> timepoints = new ArrayList<>();

        for (int minute = startValue; minute <= lastValue; minute += minutesInterval) {
            int currentHour = minute / 60;
            int currentMinute = minute - (currentHour > 0 ? (currentHour * 60) : 0);
            if (currentHour == 24)
                continue;
            timepoints.add(new Timepoint(currentHour, currentMinute));
        }
        return timepoints.toArray(new Timepoint[timepoints.size()]);
    }

    /**
     * It is sued to show country select dialog
     * @param callBackCountry
     */
    public void showDialogCountryCode(CallBackCountry callBackCountry) {


        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_country_selection, null, false);

        RecyclerView recyclerViewMyActivitySession = view.findViewById(R.id.recyclerViewMyActivitySession);

        ImageView imageViewClose = view.findViewById(R.id.imageViewClose);


        Dialog dialog = new BottomSheetDialog(getActivity());

        CountryAdapter countryAdapter;
        List<Country> countries = new ArrayList<>();
        List<Country> tempCountries = new ArrayList<>();

        countries.addAll(Temp.getCountryList());

        tempCountries.addAll(Temp.getCountryList());

        countryAdapter = new CountryAdapter(getActivity(), countries, position -> {

            callBackCountry.onCountry(countries.get(position).getDialCode());
            dialog.hide();
//            selectedCode = Temp.getCountryList().get(position).getCode();

         /*   new android.os.Handler().postDelayed(() -> {
                onCountrySelectListener.onCountrySelect(Temp.getCountryList().get(position).getDialCode());
            }, 300);

            presenter.goBack();*/

        });

        AppCompatEditText editTextSearch = view.findViewById(R.id.editTextSearch);

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Observable.fromIterable(tempCountries).subscribeOn(Schedulers.newThread()).filter(orgLocal -> orgLocal.getName().toUpperCase().contains(charSequence.toString().toUpperCase())).observeOn(AndroidSchedulers.mainThread()).toList().subscribe(countries1 -> {

                    countries.clear();
                    countries.addAll(countries1);
                    if (countryAdapter != null) {
                        countryAdapter.notifyDataSetChanged();
                    }

                });

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        recyclerViewMyActivitySession.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewMyActivitySession.setAdapter(countryAdapter);


        dialog.setContentView(view);

        imageViewClose.setOnClickListener(v -> {
            dialog.hide();
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                BottomSheetDialog d = (BottomSheetDialog) dialog;

                FrameLayout bottomSheet = (FrameLayout) d.findViewById(android.support.design.R.id.design_bottom_sheet);

                BottomSheetBehavior.from(bottomSheet)
                        .setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        dialog.show();


    }

    public void focusKeyBoard(View yourView) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(yourView, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * It used to call back country code
     */
    public interface CallBackCountry {
        void onCountry(String text);
    }


    /**
     * It is used to share experience
     * @param expId
     * @param title
     * @param desc
     * @param imageUrl
     * @param isExp
     */
    public void shareExperienceData(String expId, String title, String desc, String imageUrl, boolean isExp) {
        showLoader();
        FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(((isExp ? URLFactory.EXP_SHARE : URLFactory.LOCAL_SHARE) + expId)))
                .setDynamicLinkDomain(URLFactory.FIREBASE_ID_DYNAMICLINK)
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder(BuildConfig.APPLICATION_ID)
                        .build())
                .setIosParameters(new DynamicLink.IosParameters.Builder(BuildConfig.APPLICATION_ID)
                        .setAppStoreId(URLFactory.IOS_APPSTORE_ID)
                        .build())
                .setGoogleAnalyticsParameters(
                        new DynamicLink.GoogleAnalyticsParameters.Builder()
                                .setSource(getString(R.string.firebase_source))
                                .setMedium(getString(R.string.firebase_social))
                                .setCampaign(getString(R.string.firebase_share_exp))
                                .build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle(title)
                                .setDescription(desc)
                                .setImageUrl(Uri.parse(imageUrl))
                                .build())
                .setNavigationInfoParameters(new DynamicLink.NavigationInfoParameters.Builder().setForcedRedirectEnabled(true).build())
                .buildShortDynamicLink()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            shareExpIntent(title, desc, shortLink);
                            hideLoader();
                        } else {
                        }
                    }
                });
    }


    /**
     * It is used to open share  intent
     * @param title
     * @param desc
     * @param shortLink
     */
    private void shareExpIntent(String title, String desc, Uri shortLink) {

        String shareBody = title + "\n\n\n" + desc + "\n\n" + shortLink.toString();

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.app_name)));
    }

    /**
     * It is used to change app language
     * @param language
     */
    @Override
    public void changeAppLanguage(String language) {


        Locale defaultLocale = Locale.getDefault();

        String languageCode;

        language = language.toLowerCase();

        switch (language) {
            case "german":
                languageCode = "de";
                break;
            case "spanish":
                languageCode = "es";
                break;
            case "french":
                languageCode = "fr";
                break;
            default:
                languageCode = "en";
                break;
        }

        appPreferences.putString(Common.APPLANG, language);

        if (!languageCode.equalsIgnoreCase(defaultLocale.getLanguage())) {
            Locale newLocal = new Locale(languageCode, defaultLocale.getCountry());

/*
            if (languageCode.equalsIgnoreCase("eg")) {
                newLocal = Resources.getSystem().getConfiguration().locale;
            }
*/

            Locale.setDefault(newLocal);

            Configuration configuration = getResources().getConfiguration();
            configuration.setLocale(newLocal);

            getActivity().getBaseContext().getResources().updateConfiguration(configuration,
                    getActivity().getBaseContext().getResources().getDisplayMetrics());


            if (session.getUser().getRole().equalsIgnoreCase("L")) {
                presenter.navigator.startHomeLocalActivity().byFinishingAll().start();
            } else {
                presenter.navigator.startHomeActivity().byFinishingAll().start();
            }
        }
    }

    /**
     * It is used to set force to local
     */

    public void forceToSetLocal() {

        String language = appPreferences.getString(Common.APPLANG);


        if (!language.isEmpty()) {

            Locale defaultLocale = Locale.getDefault();

            String languageCode;

            language = language.toLowerCase();

            switch (language) {
                case "german":
                    languageCode = "de";
                    break;
                case "spanish":
                    languageCode = "es";
                    break;
                case "french":
                    languageCode = "fr";
                    break;
                default:
                    languageCode = "en";
                    break;
            }

            appPreferences.putString(Common.APPLANG, language);

            Locale newLocal = new Locale(languageCode, defaultLocale.getCountry());


            Locale.setDefault(newLocal);

            Configuration configuration = getResources().getConfiguration();
            configuration.setLocale(newLocal);

            getActivity().getBaseContext().getResources().updateConfiguration(configuration,
                    getActivity().getBaseContext().getResources().getDisplayMetrics());


        } else {
            changeAppLanguageSetDefault();
        }

    }

    /**
     * It is used to change default app language
     */

    @Override
    public void changeAppLanguageSetDefault() {


        Locale locale = Resources.getSystem().getConfiguration().locale;

        Locale.setDefault(locale);
        Configuration configuration = getResources().getConfiguration();
        configuration.setLocale(locale);
        getActivity().getBaseContext().getResources().updateConfiguration(configuration,
                getActivity().getBaseContext().getResources().getDisplayMetrics());

    }
}
