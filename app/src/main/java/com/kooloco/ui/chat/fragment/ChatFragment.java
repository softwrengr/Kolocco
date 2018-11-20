package com.kooloco.ui.chat.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;
import com.kooloco.R;
import com.kooloco.constant.Common;
import com.kooloco.core.AppPreferences;
import com.kooloco.core.Session;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Chat;
import com.kooloco.model.ReceiverData;
import com.kooloco.model.Time;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.chat.adapter.ChatAdapter;
import com.kooloco.ui.chat.presenter.ChatPresenter;
import com.kooloco.ui.chat.view.ChatView;
import com.kooloco.util.TimeConvertUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by hlink44 on 14/3/17.
 */

public class ChatFragment extends BaseFragment<ChatPresenter, ChatView> implements ChatView {
    @BindView(R.id.recyclerViewChat)
    RecyclerView recyclerViewChat;
    @BindView(R.id.editTextMessage)
    AppCompatEditText editTextMessage;
    @BindView(R.id.textViewSend)
    FrameLayout textViewSend;
    List<Chat> chats;
    ChatAdapter chatAdapter;
    @Inject
    Gson gson;
    @Inject
    Session session;
    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.toolbarstaus)
    AppCompatTextView toolbarstaus;
    @BindView(R.id.linearLayoutViewLocal)
    LinearLayout linearLayoutViewLocal;
    private ReceiverData receiverData;
    String uniqId = "";

    final String TAG = ":::";

    @Inject
    AppPreferences appPreferences;

    String personalImageUrl = "";

    String receiverImageUrl = "";

    Chat recentChat;


    @Override
    protected int createLayout() {
        return R.layout.chat;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }


    @Override
    protected ChatView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        if (chats == null) {
            chats = new ArrayList<>();
        }

        //As per clint Comment code
/*
        if (session.getUser().getRole().equalsIgnoreCase("L") && session.getUser().getIsAdminApprove().equalsIgnoreCase("1")) {
            linearLayoutViewLocal.setVisibility(View.GONE);
        } else {
            linearLayoutViewLocal.setVisibility(View.VISIBLE);
        }
*/

        personalImageUrl = session.getUser().getProfileImage();

        if (receiverData != null) {

            uniqId = getDocumentIdForDatabse(session.getUser().getId(), receiverData.getUser_id()) + "#" + receiverData.getOrderId();
            toolbarTitle.setText(receiverData.getName());
            receiverImageUrl = receiverData.getImageUrl();

            getDatabase().collection(Common.FireStore.TAB_NAME_USER).document(receiverData.getUser_id()).addSnapshotListener((documentSnapshot, e) -> {
                if (e != null) {
                    return;
                }


                if (documentSnapshot != null && documentSnapshot.exists()) {
                    // Log.d(TAG, source + " data: " + snapshot.getData());

                    Boolean aBoolean = documentSnapshot.getBoolean(Common.FireStore.FIELD_IS_ONLINE);

                    if (toolbarstaus != null) {
                        toolbarstaus.setText(aBoolean ? getActivity().getString(R.string.online_chat) : getActivity().getString(R.string.offline_chat));
                    }

                } else {
                    // Log.d(TAG, source + " data: null");
                }


            });

            getDatabase().collection(Common.FireStore.TAB_NAME_RECENT_CHAT).document(uniqId).addSnapshotListener((documentSnapshot, e) -> {
                if (e != null) {
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {

                    String data = gson.toJson(documentSnapshot.getData());

                    recentChat = gson.fromJson(data, Chat.class);

                } else {
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                    // Log.d(TAG, source + " data: null");
                }

            });

        }


        addLiveData();

        chatAdapter = new ChatAdapter(getActivity(), chats, personalImageUrl, receiverImageUrl);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        //linearLayoutManager.setReverseLayout(true);
        recyclerViewChat.setLayoutManager(linearLayoutManager);
        recyclerViewChat.setAdapter(chatAdapter);

        if (chats.size() >= 3) {
            recyclerViewChat.scrollToPosition(chats.size() - 1);
        }

        editTextMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerViewChat.scrollToPosition(chats.size() - 1);
                    }
                }, 200);

                return false;
            }
        });
    }

    @OnClick(R.id.textViewSend)
    public void onClick() {
        String chat1 = editTextMessage.getText().toString().trim();
        if (!chat1.isEmpty()) {
            String message = editTextMessage.getText().toString();
            editTextMessage.setText("");
            sendChat(message);
        } else {
            editTextMessage.setText("");
        }
    }

    private void sendChat(String message) {

        if (receiverData == null) {
            return;
        }

        Map<String, Object> chat = new HashMap<>();

        chat.put(Common.FireStore.FIELD_SENDER_ID, session.getUser().getId());
        chat.put(Common.FireStore.FIELD_SENDER_NAME, session.getUser().getFirstname() + " " + session.getUser().getLastname());
        chat.put(Common.FireStore.FIELD_SENDER_IMAGE_URL, session.getUser().getProfileImage());
        chat.put(Common.FireStore.FIELD_SENDER_DEVICE_TYPE, session.getUser().getDeviceType());
        chat.put(Common.FireStore.FIELD_SENDER_DEVICE_TOKEN, session.getUser().getDeviceId());

        chat.put(Common.FireStore.FIELD_RECEIVER_ID, receiverData.getUser_id());
        chat.put(Common.FireStore.FIELD_RECEIVER_NAME, receiverData.getName());
        chat.put(Common.FireStore.FIELD_RECEIVER_IMAGE_URL, receiverData.getImageUrl());
        chat.put(Common.FireStore.FIELD_RECEIVER_DEVICE_TYPE, receiverData.getDeviceType());
        chat.put(Common.FireStore.FIELD_RECEIVER_DEVICE_TOKEN, receiverData.getDeviceToken());

        chat.put(Common.FireStore.FIELD_MESSAGE, message.trim());
        chat.put(Common.FireStore.FIELD_MESSAGE_TYPE, "M");

        Date time = Calendar.getInstance().getTime();

        String convertDate = new SimpleDateFormat("MMM d, yyyy hh:mm:ss a", Locale.US).format(time.getTime());

        chat.put(Common.FireStore.FIELD_CHAT_TIME, time);
        chat.put(Common.FireStore.FIELD_CHAT_TIME_UTC, TimeConvertUtils.datTimeConvertLocalToServerMain(convertDate, "MMM d, yyyy hh:mm:ss a", "MM d, yyyy hh:mm:ss a"));

        chat.put(Common.FireStore.FIELD_CHAT_READ, false);

        addChatDataBase(chat);

    }

    private void addChatDataBase(Map<String, Object> chat) {
        chat.put(Common.FireStore.FIELD_UNIQ_ID, uniqId);

        getDatabase().collection(Common.FireStore.TAB_NAME_CHAT).add(chat).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.e(":::::: Chat", "Added successfully");
                getRecentChatCount(chat);
            }
        });

    }

    private void getRecentChatCount(Map<String, Object> chat) {


        getDatabase().collection(Common.FireStore.TAB_NAME_CHAT).whereEqualTo(Common.FireStore.FIELD_CHAT_READ, false).whereEqualTo(Common.FireStore.FIELD_RECEIVER_ID, chat.get(Common.FireStore.FIELD_RECEIVER_ID)).whereEqualTo(Common.FireStore.FIELD_UNIQ_ID, uniqId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                Log.e("::::", "Changes " + documentSnapshots.getDocuments().size());

                int count = documentSnapshots.getDocuments().size();

                chat.put(Common.FireStore.FIELD_CHAT_COUNT, "" + count);

                addRecentChat(chat);
            }
        });

    }


    private void addRecentChat(Map<String, Object> chat) {

        chat.remove(Common.FireStore.FIELD_UNIQ_ID);

        getDatabase().collection(Common.FireStore.TAB_NAME_RECENT_CHAT).document(uniqId).set(chat, SetOptions.merge()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.e(":::::: RecentChat", "Added successfully");
            }
        });

    }

    /**
     * It is used to add chat list
     *
     * @param chat
     */
    private void addChatList(Chat chat) {
        chat.setTime(TimeConvertUtils.dateTimeConvertLocalToLocal(chat.getTime(), "MMM d, yyyy hh:mm:ss a", "MMM d, yyyy hh:mm a"));
        chats.add(chat);
        if (chatAdapter != null) {
            chatAdapter.notifyDataSetChanged();
            if (chats.size() == 1) {
                if (recyclerViewChat != null) {
                    recyclerViewChat.scrollToPosition(0);
                }
            } else {
                if (recyclerViewChat != null) {
                    recyclerViewChat.scrollToPosition(chats.size() - 1);
                }

            }

        }

    }

    /**
     * It is use to delete data
     *
     * @param chat
     */
    private void removeChatList(Chat chat) {

        Chat chatDelete = null;
        int i = 0;
        for (Chat chatTemp : chats) {
            if (chatTemp.getChatId().equalsIgnoreCase(chat.getChatId())) {
                chatDelete = chatTemp;
                break;
            }
            i = i + 1;
        }
        if (chatDelete != null) {
            chats.remove(chatDelete);
        }

        if (chatAdapter != null) {
            chatAdapter.notifyItemRemoved(i);
            if (chats.size() == 1) {
                if (recyclerViewChat != null)
                    recyclerViewChat.scrollToPosition(0);
            } else {
                if (recyclerViewChat != null)
                    recyclerViewChat.scrollToPosition(chats.size() - 1);

            }

        }

    }

    /**
     * It is used to modify data
     *
     * @param chat
     */
    private void modifyChatList(Chat chat) {

        Chat chatModify = null;
        int i = 0;
        for (Chat chatTemp : chats) {
            if (chatTemp.getChatId().equalsIgnoreCase(chat.getChatId())) {
                chat.setTime(TimeConvertUtils.dateTimeConvertLocalToLocal(chat.getTime(), "MMM d, yyyy hh:mm:ss a", "MMM d, yyyy hh:mm a"));
                chatModify = chat;
                break;
            }
            i = i + 1;
        }

        if (chatModify != null) {

            chats.set(i, chatModify);
        }

        if (chatAdapter != null) {
            chatAdapter.notifyItemChanged(i);
            if (chats.size() == 1) {
                if (recyclerViewChat != null)
                    recyclerViewChat.scrollToPosition(0);
            } else {
                if (recyclerViewChat != null)
                    recyclerViewChat.scrollToPosition(chats.size() - 1);
            }

        }

    }

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        goBack();
    }

    @Override
    public void setReceiverData(ReceiverData receiverData) {
        this.receiverData = receiverData;
    }

    private void addLiveData() {

        CollectionReference collectionLive = getDatabase().collection(Common.FireStore.TAB_NAME_CHAT);
        collectionLive.whereEqualTo("uniq_id", uniqId).orderBy(Common.FireStore.FIELD_CHAT_TIME, Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

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

                            chat.setUserType(session.getUser().getId().equalsIgnoreCase(chat.getSenderId()));
                            chat.setChatId(document.getDocument().getId());


                            if (!chat.getChatRead() && chat.getReceiverId().equalsIgnoreCase(session.getUser().getId())) {
                                updateChatRead(document.getDocument().getId());
                            }

                            if (getActivity() != null)
                                addChatList(chat);

                            break;
                        case MODIFIED:
                            //  Log.d(TAG, "Modified Data: " + document.getDocument().getData());

                            data = gson.toJson(document.getDocument().getData());

                            chat = gson.fromJson(data, Chat.class);

                            chat.setUserType(session.getUser().getId().equalsIgnoreCase(chat.getSenderId()));
                            chat.setChatId(document.getDocument().getId());

                            if (!chat.getChatRead() && chat.getReceiverId().equalsIgnoreCase(session.getUser().getId())) {
                                updateChatRead(document.getDocument().getId());
                            }

                            if (getActivity() != null)
                                modifyChatList(chat);

                            break;
                        case REMOVED:
                            // Log.d(TAG, "Removed Data: " + document.getDocument().getData());

                            data = gson.toJson(document.getDocument().getData());

                            chat = gson.fromJson(data, Chat.class);

                            chat.setUserType(session.getUser().getId().equalsIgnoreCase(chat.getSenderId()));
                            chat.setChatId(document.getDocument().getId());

                            if (getActivity() != null)
                                removeChatList(chat);

                            break;
                    }

                }

            }
        });
    }

    private void updateChatRead(String documentId) {
        if (getActivity() != null) {
            Map<String, Object> chatUpdate = new HashMap<>();

            chatUpdate.put(Common.FireStore.FIELD_CHAT_READ, true);

            getDatabase()
                    .collection(Common.FireStore.TAB_NAME_CHAT)
                    .document(documentId)
                    .set(chatUpdate, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.e(TAG, "Success Update chat read");
                            updateChatCount();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Failure Update chat read");
                        }
                    });


        }


    }

    private void updateChatCount() {
        if (getActivity() != null) {
            Map<String, Object> chatUpdate = new HashMap<>();

            chatUpdate.put(Common.FireStore.FIELD_CHAT_COUNT, "0");

            getDatabase()
                    .collection(Common.FireStore.TAB_NAME_RECENT_CHAT)
                    .document(uniqId)
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

    }

    @OnClick(R.id.textViewViewLocalDashboard)
    public void onClickDahsboard() {

        if (recentChat != null) {
            presenter.openOrderDetails(recentChat);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String receiverId = "";
        if (receiverData != null) {
            receiverId = receiverData.getUser_id();
        }
        setChatPeference(true, receiverId);
    }

    @Override
    public void onPause() {
        super.onPause();
        setChatPeference(false, "");
    }

    private void setChatPeference(boolean isChatStatus, String senderIdS) {
        appPreferences.putBoolean("isChatScreenD", isChatStatus);
        appPreferences.putString("chatReceiverId", senderIdS);
    }
}
