package com.kooloco.uilocal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.kooloco.R;
import com.kooloco.constant.Common;
import com.kooloco.constant.EventBusAction;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.component.ActivityComponent;
import com.kooloco.fcm.Notification;
import com.kooloco.model.Chat;
import com.kooloco.model.NotificationReadCheck;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.navigation.AppNavigationProvider;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.splash.SplashActivity;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by hlink44 on 4/9/17.
 */

public class MainLocalActivity extends AppNavigationProvider {
    @BindView(R.id.radioButtonHome)
    AppCompatRadioButton radioButtonHome;
    @BindView(R.id.radioButtonEarning)
    AppCompatRadioButton radioButtonEarning;
    @BindView(R.id.radioButtonRecentChat)
    AppCompatRadioButton radioButtonRecentChat;
    @BindView(R.id.radioButtonCompleteOrder)
    AppCompatRadioButton radioButtonCompleteOrder;
    @BindView(R.id.radioButtonProfile)
    AppCompatRadioButton radioButtonProfile;
    KoolocoLocalHomeLocalScreenManager koolocoLocalHomeLocalScreenManager;

    @BindView(R.id.imageViewCount)
    ImageView imageViewCount;
    private String TAG = ",,,";

    @Inject
    Session session;

    @Inject
    Gson gson;

    public boolean isDisplayNotificationIcon = false;

    @Inject
    KoolocoRepository koolocoRepository;

    Map<String, String> countMap = new LinkedHashMap<>();

    @Override
    public int findFragmentPlaceHolder() {
        return R.id.placeHolder;
    }

    @Override
    public int findContentView() {
        return R.layout.activity_main_local;
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

        koolocoLocalHomeLocalScreenManager = new KoolocoLocalHomeLocalScreenManager(navigationFactory.getFragmentHandler());
        koolocoLocalHomeLocalScreenManager.showHomeScreen();
        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                pushNotificationAction(getIntent().getExtras(), false);
            }
        }

        setChount();

        BaseFragment.convertFormat(252.50);

        setNotificationCountDisplay("L");
    }

    @OnClick({R.id.radioButtonHome, R.id.linearLayoutHome, R.id.radioButtonEarning, R.id.linearLayoutEarning, R.id.radioButtonCompleteOrder, R.id.linearLayoutCompleteOrder, R.id.radioButtonRecentChat, R.id.linearLayoutRecentChat, R.id.radioButtonProfile, R.id.linearLayoutProfile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radioButtonHome:
            case R.id.linearLayoutHome:
                setSelection(0);
                koolocoLocalHomeLocalScreenManager.showHomeScreen();
                break;

            case R.id.radioButtonEarning:
            case R.id.linearLayoutEarning:
                setSelection(1);
                koolocoLocalHomeLocalScreenManager.showEarningsScreen();
                break;

            case R.id.radioButtonRecentChat:
            case R.id.linearLayoutRecentChat:
                setSelection(2);
                koolocoLocalHomeLocalScreenManager.showRecentChatScreen();
                break;

            case R.id.radioButtonCompleteOrder:
            case R.id.linearLayoutCompleteOrder:
                setSelection(3);
                koolocoLocalHomeLocalScreenManager.showComplateOrderScreen();
                break;

            case R.id.radioButtonProfile:
            case R.id.linearLayoutProfile:
                setSelection(4);
                koolocoLocalHomeLocalScreenManager.showProfileScreen();
                break;
        }
    }

    private void setSelection(int position) {
        radioButtonHome.setChecked(position == 0);
        radioButtonEarning.setChecked(position == 1);
        radioButtonRecentChat.setChecked(position == 2);
        radioButtonCompleteOrder.setChecked(position == 3);
        radioButtonProfile.setChecked(position == 4);
    }

    public void selectHomeTab()
    {
        koolocoLocalHomeLocalScreenManager.showHomeScreen();
        setSelection(0);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            pushNotificationAction(bundle, true);
        }
    }


    private void pushNotificationAction(Bundle bundle, boolean isOpen) {

        if (bundle.getString("data") != null) {
            String data = bundle.getString("data");

            if (!data.isEmpty()) {
                Notification notification = new Gson().fromJson(data, Notification.class);

                switch (notification.getNotificationTag()) {
                    case Common.Local.ACCEPT_MODIFICATION:
                    case Common.Local.DECLINE_MODIFCATION:
                    case Common.Local.ACCEPT_OBJECTION:
                    case Common.Local.RECEIPT_OBJECTION:
                    case Common.Local.PAYMENT_MADE:
                    case Common.Local.ACCEPT_ORG_REQUEST:
                    case Common.Local.REJECT_ORG_REQUEST:
                    case Common.Local.ORG_REQUEST:
                    case Common.Local.ORG_SET_PAYMENT_RULES:
                    case Common.Local.ORG_DELETE_LOCAL:
                    case Common.Local.ORG_EXIT_LOCAL:
                    case Common.Local.ORG_DELETE:
                    case Common.Local.ORG_ADMIN_ACCEPTED:
                    case Common.Local.EXP_ADMIN_ACCEPTED:
                    case Common.Local.EXP_ADMIN_REJECT:
                        openIsloatedFullActivity().setPage(Pages.NotificationLocal).start();
                        break;
                    case Common.Local.ORDER_REQUEST:
                        if (isOpen) {
                            Intent authanticationIntent;
                            authanticationIntent = new Intent(MainLocalActivity.this, MainLocalActivity.class);
                            finishAffinity();
                            startActivity(authanticationIntent);
                        }
                        break;
                    case Common.Visitor.PAYMENT_REQUEST:
                        Bundle bundleData = new Bundle();
                        bundleData.putString("orderId", notification.getOrderId());
                        openIsloatedFullActivity().setPage(AppNavigator.Pages.ReceipScreen).addBundle(bundleData).start();
                        break;
                    case Common.Local.NEW_CHAT_MESSAGE:
                        if (koolocoLocalHomeLocalScreenManager != null) {
                            koolocoLocalHomeLocalScreenManager.showRecentChatScreen();
                            setSelection(2);
                        }
                        break;
                    case Common.Visitor.RATE_TO_LOCAL:
                        Bundle bundleNew = new Bundle();
                        bundleNew.putString("orderId", notification.getOrderId());
                        openIsloatedFullActivity().setPage(AppNavigator.Pages.RATING).addBundle(bundleNew).start();
                        break;
                }
            }

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

                int count = 0;

                for (DocumentChange document : documentSnapshots.getDocumentChanges()) {

                    Log.d(TAG, "Recent chat Count: data " + count);

                    boolean isChatRead = false;

                    isChatRead = (boolean) document.getDocument().getData().get(Common.FireStore.FIELD_CHAT_READ);

                    switch (document.getType()) {
                        case ADDED:
                            Log.d(TAG, "Recent chat Count: " + document.getDocument().getData());
                            count = count + 1;
                            break;
                        case MODIFIED:
                            Log.d(TAG, "Recent chat Count Modify Data: " + document.getDocument().getData());
                            count = count + 1;
                            break;
                        case REMOVED:
                            Log.d(TAG, "Recent chat Count Removed Data: " + document.getDocument().getData());
                            count = count - 1;
                            break;
                    }

                }

                if (imageViewCount != null) {
                    imageViewCount.setVisibility((count <= 0) ? View.INVISIBLE : View.VISIBLE);
                }
            }
        };

        getDatabaseBaseActivity().collection(Common.FireStore.TAB_NAME_CHAT).whereEqualTo(Common.FireStore.FIELD_CHAT_READ, false).whereEqualTo(Common.FireStore.FIELD_RECEIVER_ID, session.getUser().getId()).addSnapshotListener(eventListener);

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void onEvent(EventBusAction action) {
        if (action == EventBusAction.NOTIFICATIONCOUNTDISPLAYLOCAL) {
            setNotificationCountDisplay("L");
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

                    EventBus.getDefault().post(EventBusAction.NOTIFICATIONCOUNTUILOCAL);
                }
            });
        }
    }


}
