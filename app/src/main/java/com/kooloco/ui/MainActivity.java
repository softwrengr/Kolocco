package com.kooloco.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;
import com.kooloco.R;
import com.kooloco.constant.Common;
import com.kooloco.constant.EventBusAction;
import com.kooloco.core.Session;
import com.kooloco.data.URLFactory;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.component.ActivityComponent;
import com.kooloco.fcm.Notification;
import com.kooloco.model.Chat;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.NotificationReadCheck;
import com.kooloco.model.Response;
import com.kooloco.model.User;
import com.kooloco.ui.navigation.AppNavigationProvider;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.util.ProgressDialogCustom;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by hlink44 on 4/9/17.
 */

public class MainActivity extends AppNavigationProvider {
    @BindView(R.id.radioButtonSearch)
    AppCompatRadioButton radioButtonSearch;
    @BindView(R.id.radioButtonMap)
    AppCompatRadioButton radioButtonMap;
    @BindView(R.id.radioButtonKooloco)
    AppCompatRadioButton radioButtonKooloco;
    @BindView(R.id.radioButtonRecentChat)
    AppCompatRadioButton radioButtonRecentChat;
    @BindView(R.id.radioButtonProfile)
    AppCompatRadioButton radioButtonProfile;

    public KoolocoLocalHomeVisitorScreenManager koolocoLocalHomeVisitorScreenManager;

    @BindView(R.id.imageViewCount)
    ImageView imageViewCount;
    private String TAG = ",,,";

    @Inject
    Gson gson;

    @Inject
    KoolocoRepository koolocoRepository;

    ProgressDialogCustom progressDialog;
    Map<String, String> countMap = new LinkedHashMap<>();


    public boolean isDisplayNotificationIcon = false;

    @Override
    public int findFragmentPlaceHolder() {
        return R.id.placeHolder;
    }

    @Inject
    Session session;

    int count = 0;

    @Override
    public int findContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        koolocoLocalHomeVisitorScreenManager = new KoolocoLocalHomeVisitorScreenManager(navigationFactory.getFragmentHandler());
        koolocoLocalHomeVisitorScreenManager.showHomeScreen();
        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                pushNotificationAction(getIntent().getExtras());
            }
        }
        setChount();

        setFont();

     /*   Bundle bundle=new Bundle();
        bundle.putString("sports",new Gson().toJson(Temp.getOnBoardService()));
        openIsloatedFullActivity().setPage(Pages.ONBORDING).addBundle(bundle).byFinishingAll().start();*/

        //   openExperinceAddParticipantsView().hasData(experinceAddParticipantsView -> experinceAddParticipantsView.setVisitorBooking(Temp.getVisitorBookingNewFlow())).replace(true);

        //       openExperinceAddParticipantsView().hasData(experinceAddParticipantsView -> experinceAddParticipantsView.setVisitorBooking(Temp.getVisitorBookingNewFlow())).replace(true);

        setNotificationCountDisplay("V");

        getDeepLink();
    }

    @OnClick({R.id.radioButtonSearch, R.id.linearLayoutSearch, R.id.radioButtonMap, R.id.linearLayoutMap, R.id.radioButtonKooloco, R.id.linearLayoutKooloco, R.id.radioButtonRecentChat, R.id.linearLayoutRecentChat, R.id.radioButtonProfile, R.id.linearLayoutProfile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radioButtonSearch:
            case R.id.linearLayoutSearch:
                setSelection(0);
                koolocoLocalHomeVisitorScreenManager.showHomeScreen();
                break;

            case R.id.radioButtonMap:
            case R.id.linearLayoutMap:
                setSelection(1);
                koolocoLocalHomeVisitorScreenManager.showLocalAndExpScreen();
                break;

            case R.id.radioButtonKooloco:
            case R.id.linearLayoutKooloco:
                setSelection(2);
                koolocoLocalHomeVisitorScreenManager.showMyExperienceScreen();
                break;

            case R.id.radioButtonRecentChat:
            case R.id.linearLayoutRecentChat:
                setSelection(3);
                koolocoLocalHomeVisitorScreenManager.showRecentChatScreen();
                break;

            case R.id.radioButtonProfile:
            case R.id.linearLayoutProfile:
                setSelection(4);
                koolocoLocalHomeVisitorScreenManager.showProfileScreen();
                break;
        }
    }

    public void setSelectonTab(int pos) {
        if (pos == 0) {
            koolocoLocalHomeVisitorScreenManager.showHomeScreen();

        } else if (pos == 1) {
            koolocoLocalHomeVisitorScreenManager.showLocalAndExpScreen();

        } else if (pos == 2) {
            koolocoLocalHomeVisitorScreenManager.showMyExperienceScreen();

        } else if (pos == 3) {
            koolocoLocalHomeVisitorScreenManager.showRecentChatScreen();

        } else if (pos == 4) {
            koolocoLocalHomeVisitorScreenManager.showProfileScreen();
        }

        setSelection(pos);
    }


    public void openMapFragment() {
        koolocoLocalHomeVisitorScreenManager.showLocalAndExpScreen();
    }

/*    public void openHomeFragment() {
        koolocoLocalHomeVisitorScreenManager.showHomeScreen();
    }*/

    public void setSelection(int position) {
        radioButtonSearch.setChecked(position == 0);
        radioButtonMap.setChecked(position == 1);
        radioButtonKooloco.setChecked(position == 2);
        radioButtonRecentChat.setChecked(position == 3);
        radioButtonProfile.setChecked(position == 4);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            pushNotificationAction(bundle);
        }
    }


    private void setFont() {
        Typeface font = ResourcesCompat.getFont(this, R.font.opensans_regular);

        radioButtonSearch.setTypeface(font);
        radioButtonMap.setTypeface(font);
        radioButtonKooloco.setTypeface(font);
        radioButtonRecentChat.setTypeface(font);
        radioButtonProfile.setTypeface(font);
    }

    private void pushNotificationAction(Bundle bundle) {
        if (bundle.getString("data") != null) {
            String data = bundle.getString("data");

            if (!data.isEmpty()) {
                Notification notification = new Gson().fromJson(data, Notification.class);

                switch (notification.getNotificationTag()) {
                    case Common.Visitor.MODIFY_DURATION:
                    case Common.Visitor.MODIFY_LOCATION:
                    case Common.Visitor.MODIFY_OBJECTION:
                    case Common.Visitor.ACCEPT_OBJECTION:
                        openIsloatedFullActivity().setPage(Pages.Notification).start();
                    /*    setSelectonTab(1);
                        setSelection(1);*/
                        break;
                    case Common.Visitor.ACCEPT_ORDER:
                    case Common.Visitor.DECLINE_ORDER:
                    case Common.Visitor.COMPLETE_ORDER:
                        openIsloatedFullActivity().setPage(Pages.OrderHistory).start();
                        break;
                    case Common.Visitor.PAYMENT_REQUEST:
                        Bundle bundleData = new Bundle();
                        bundleData.putString("orderId", notification.getOrderId());
                        openIsloatedFullActivity().setPage(Pages.ReceipScreen).addBundle(bundleData).start();
                        break;
                    case Common.Visitor.NEW_CHAT_MESSAGE:
                        setSelectonTab(3);
                        setSelection(3);
                        break;
                    case Common.Visitor.RATE_TO_LOCAL:
                        Bundle bundleNew = new Bundle();
                        bundleNew.putString("orderId", notification.getOrderId());
                        openIsloatedFullActivity().setPage(AppNavigator.Pages.RATING).addBundle(bundleNew).start();
                        break;

                    case Common.Visitor.NEW_LOCAL_ADMIN_APPROVE:
                        showLoader();
                        Map<String, String> map = new HashMap<>();
                        map.put("user_id", session.getUser().getId());
                        koolocoRepository.setBecomeLocal(map).subscribe(new SingleObserver<Response<User>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(Response<User> userResponse) {
                                hideLoader();
                                openIsloatedFullActivity().setPage(AppNavigator.Pages.BecomeLocal).start();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
                        break;
                }
            }

        }
    }

    public void showLoader() {


        try {
            hideKeyboard();
            if (progressDialog == null && this != null) {
                try {
                    progressDialog = new ProgressDialogCustom(this);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
            if (progressDialog != null && this != null)
                progressDialog.show();

        } catch (Exception e) {

        }

    }

    public void hideLoader() {

        try {

            if (progressDialog != null && this != null) {
                progressDialog.dismiss();
            }

        } catch (Exception e) {

        }
    }

    private void setChat() {

        EventListener<QuerySnapshot> eventListener;


        eventListener = new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }


                for (DocumentChange document : documentSnapshots.getDocumentChanges()) {

                    //updateChatRead(document.getDocument().getId());


                    boolean isChatRead = false;

                    isChatRead = (boolean) document.getDocument().getData().get(Common.FireStore.FIELD_CHAT_READ);

                    switch (document.getType()) {
                        case ADDED:
                            Log.d(TAG, "Main Activity Count: Add " + document.getDocument().getData());
                            count = count + 1;
                            break;
                        case MODIFIED:
                            Log.d(TAG, "Main Activity Count Modify Data: " + document.getDocument().getData());
                            count = count + 1;
                            break;
                        case REMOVED:
                            Log.d(TAG, "Main Activity Count Removed Data: " + document.getDocument().getData());
                            count = count - 1;
                            break;
                    }

                }

                if (imageViewCount != null) {
                    imageViewCount.setVisibility((count <= 0) ? View.INVISIBLE : View.VISIBLE);
                }
            }
        };

        getDatabaseBaseActivity().collection(Common.FireStore.TAB_NAME_RECENT_CHAT).whereEqualTo(Common.FireStore.FIELD_CHAT_READ, false).whereEqualTo(Common.FireStore.FIELD_RECEIVER_ID, session.getUser().getId()).addSnapshotListener(eventListener);

    }


    private void setChount() {
        EventListener<QuerySnapshot> eventListener = (documentSnapshots, e) -> {

            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                return;
            }


            for (DocumentChange document : documentSnapshots.getDocumentChanges()) {

                switch (document.getType()) {
                    case ADDED:
                        Log.d(TAG, "New Data: " + document.getDocument().getData());

                        String data = gson.toJson(document.getDocument().getData());

                        Chat chat = gson.fromJson(data, Chat.class);

                        if (!chat.getOrderId().isEmpty()) {
                            countMap.put(document.getDocument().getId(), chat.getReceiverId().equalsIgnoreCase(session.getUser().getId()) ? chat.getChatCount() : "0");
                        }

                        break;
                    case MODIFIED:

                        Log.d(TAG, "Modified Data: " + document.getDocument().getData());

                        data = gson.toJson(document.getDocument().getData());

                        chat = gson.fromJson(data, Chat.class);

                        if (!chat.getOrderId().isEmpty()) {
                            countMap.put(document.getDocument().getId(), chat.getReceiverId().equalsIgnoreCase(session.getUser().getId()) ? chat.getChatCount() : "0");
                        }
                        break;
                    case REMOVED:
                        Log.d(TAG, "Removed Data: " + document.getDocument().getData());

                        data = gson.toJson(document.getDocument().getData());

                        chat = gson.fromJson(data, Chat.class);

                        if (!chat.getOrderId().isEmpty()) {
                            countMap.remove(document.getDocument().getId());
                        }

                        break;
                }

                showCountIndicator();
            }

        };

        CollectionReference collectionLive = getDatabaseBaseActivity().collection(Common.FireStore.TAB_NAME_RECENT_CHAT);

        collectionLive.whereEqualTo(Common.FireStore.FIELD_SENDER_ID, session.getUser().getId()).addSnapshotListener(eventListener);

        collectionLive.whereEqualTo(Common.FireStore.FIELD_RECEIVER_ID, session.getUser().getId()).addSnapshotListener(eventListener);

    }


    private void showCountIndicator() {

        boolean isShow = false;

        for (Map.Entry<String, String> stringEntry : countMap.entrySet()) {
            if (!stringEntry.getValue().isEmpty()) {
                if (!stringEntry.getValue().equalsIgnoreCase("0")) {
                    isShow = true;
                }
            }
        }
        if (imageViewCount != null) {
            imageViewCount.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
        }
    }


    private void updateChatRead(String documentId) {
        Map<String, Object> chatUpdate = new HashMap<>();

        chatUpdate.put(Common.FireStore.FIELD_CHAT_READ, true);

        getDatabaseBaseActivity()
                .collection(Common.FireStore.TAB_NAME_CHAT)
                .document(documentId)
                .set(chatUpdate, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "Success Update chat read");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Failure Update chat read");
                    }
                });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void onEvent(EventBusAction action) {
        if (action == EventBusAction.NOTIFICATIONCOUNTDISPLAYVISITOR) {
            setNotificationCountDisplay("V");
        }
    }

    private void setNotificationCountDisplay(String role) {
        if (session.getUser() != null) {

            Map<String, String> map = new HashMap<>();
            map.put("user_id", session.getUser().getId());
            map.put("role", role);

            koolocoRepository.getNotificationReadCheck(map).subscribe(new SubscribeWithView<Response<NotificationReadCheck>>(getCurrentFragment()) {
                @Override
                public void onSuccess(Response<NotificationReadCheck> notificationReadCheckResponse) {
                    isDisplayNotificationIcon = notificationReadCheckResponse.getData().getIsReadAll().equalsIgnoreCase("1");

                    EventBus.getDefault().post(EventBusAction.NOTIFICATIONCOUNTUIVISITOR);
                }
            });
        }
    }

    private void getDeepLink() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            if (deepLink.toString().contains(URLFactory.EXP_SHARE)) {
                                String expId = deepLink.getLastPathSegment();

                                Bundle bundle = new Bundle();
                                bundle.putString("expId", expId);
                                openIsloatedFullActivity().setPage(AppNavigator.Pages.EXPDETAILSLOCAL).addBundle(bundle).start();
                            } else if (deepLink.toString().contains(URLFactory.LOCAL_SHARE)) {

                                String localId = deepLink.getLastPathSegment();

                                Bundle bundle = new Bundle();
                                DashboardDetails dashboardDetails = new DashboardDetails();
                                dashboardDetails.setId(localId);
                                bundle.putString("localDetails", new Gson().toJson(dashboardDetails));

                                openIsloatedFullActivity().addBundle(bundle).setPage(AppNavigator.Pages.Dashboard).start();

                            }
                        }


                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "getDynamicLink:onFailure", e);
                    }
                });

    }
}
