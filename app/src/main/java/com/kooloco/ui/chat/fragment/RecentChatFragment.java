package com.kooloco.ui.chat.fragment;
/**
 * Created by hlink44 on 26/9/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.kooloco.R;
import com.kooloco.constant.Common;
import com.kooloco.constant.EventBusAction;
import com.kooloco.core.Session;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Chat;
import com.kooloco.ui.MainActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.chat.adapter.RecentChatAdapter;
import com.kooloco.ui.chat.presenter.RecentChatPresenter;
import com.kooloco.ui.chat.view.RecentChatView;
import com.kooloco.uilocal.MainLocalActivity;
import com.kooloco.util.StringDateComparator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecentChatFragment extends BaseFragment<RecentChatPresenter, RecentChatView> implements RecentChatView {

    @BindView(R.id.recyclerRecentChat)
    RecyclerView recyclerRecentChat;
    final String TAG = "::: Recent Chat";

    @Inject
    Gson gson;

    @Inject
    Session session;

    List<Chat> chats = new ArrayList<>();
    @BindView(R.id.linearLayoutNoData)
    LinearLayout linearLayoutNoData;
    @BindView(R.id.linearLayoutNoDataSecond)
    LinearLayout linearLayoutNoDataSecond;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    List<Chat> chatsData;
    RecentChatAdapter recentChatAdapter;
    ListenerRegistration listenerRegistration1, listenerRegistration;
    @BindView(R.id.imageViewArchive)
    ImageView imageViewArchive;
    @BindView(R.id.imageViewCountNotification)
    ImageView imageViewCountNotification;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private boolean isLocal = false;

    @Override
    protected int createLayout() {
        return R.layout.fragment_recent_chat;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected RecentChatView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        setDot(imageViewCountNotification);

        setViewNodataFound(true);
        if (chatsData == null) {
            chatsData = new ArrayList<>();

        }


        recentChatAdapter = new RecentChatAdapter(getActivity(), chatsData, session.getUserId(), getDatabase(), new RecentChatAdapter.CallBack() {
            @Override
            public void onClickSelect(Chat chat) {
                presenter.openChat(chat);
            }
        });

        recyclerRecentChat.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        recyclerRecentChat.setAdapter(recentChatAdapter);


        addLiveData();

        swipeRefresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary), getActivity().getResources().getColor(R.color.yellow), getActivity().getResources().getColor(R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(() -> {
            if (listenerRegistration != null)
                listenerRegistration.remove();

            if (listenerRegistration1 != null)
                listenerRegistration1.remove();

            addLiveData();
        });


    }

    @Override
    public void setData(List<Chat> data) {
        chatsData.clear();
        chatsData.addAll(data);

        if (swipeRefresh != null) {
            if (swipeRefresh.isRefreshing()) {
                swipeRefresh.setRefreshing(false);
            }
        }

        if (data.size() == 0) {
            setViewNodataFound(true);
        } else {
            setViewNodataFound(false);
        }

        if (getActivity() != null) {
            recentChatAdapter.notifyDataSetChanged();
        }

    }


    private void addLiveData() {

        chats.clear();

        EventListener<QuerySnapshot> eventListener = (documentSnapshots, e) -> {

            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                return;
            }

            if (documentSnapshots.getDocumentChanges().size() == 0) {
                if (swipeRefresh != null) {
                    if (swipeRefresh.isRefreshing()) {
                        swipeRefresh.setRefreshing(false);
                    }
                }

            }

            for (DocumentChange document : documentSnapshots.getDocumentChanges()) {

                switch (document.getType()) {
                    case ADDED:
                        Log.d(TAG, "New Data: " + document.getDocument().getData());

                        String data = gson.toJson(document.getDocument().getData());

                        Chat chat = gson.fromJson(data, Chat.class);

                        chat.setUserType(session.getUser().getId().equalsIgnoreCase(chat.getSenderId()));
                        chat.setChatId(document.getDocument().getId());

                        if (getActivity() != null)
                            addChatList(chat);

                        break;
                    case MODIFIED:

                        Log.d(TAG, "Modified Data: " + document.getDocument().getData());

                        data = gson.toJson(document.getDocument().getData());

                        chat = gson.fromJson(data, Chat.class);

                        chat.setUserType(session.getUser().getId().equalsIgnoreCase(chat.getSenderId()));
                        chat.setChatId(document.getDocument().getId());

                        if (getActivity() != null)
                            modifyChatList(chat);

                        break;
                    case REMOVED:
                        Log.d(TAG, "Removed Data: " + document.getDocument().getData());

                        data = gson.toJson(document.getDocument().getData());

                        chat = gson.fromJson(data, Chat.class);

                        chat.setUserType(session.getUser().getId().equalsIgnoreCase(chat.getSenderId()));
                        chat.setChatId(document.getDocument().getId());

                        if (getActivity() != null)
                            removeChatList(chat);

                        break;
                }

            }

        };

        CollectionReference collectionLive = getDatabase().collection(Common.FireStore.TAB_NAME_RECENT_CHAT);

        listenerRegistration = collectionLive.whereEqualTo(Common.FireStore.FIELD_SENDER_ID, session.getUser().getId()).addSnapshotListener(eventListener);

        listenerRegistration1 = collectionLive.whereEqualTo(Common.FireStore.FIELD_RECEIVER_ID, session.getUser().getId()).addSnapshotListener(eventListener);

    }


    /**
     * It is used to add chat list
     *
     * @param chat
     */
    private void addChatList(Chat chat) {
        chats.add(chat);
        setChat();
    }


    private boolean checkUser(Chat chat) {
        boolean isExsit = false;

        for (Chat chatTemp : chats) {
            if (chatTemp.getChatId().equalsIgnoreCase(chat.getChatId())) {
                isExsit = true;
                break;
            }
        }
        return isExsit;
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

        setChat();


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
                chatModify = chat;
                break;
            }
            i = i + 1;
        }

        if (chatModify != null) {
            chats.set(i, chatModify);
        }

        setChat();

    }

    private void setChat() {
        Observable.just(chats).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).flatMapIterable(chats1 -> chats1).toSortedList((chat, t1) -> {
            StringDateComparator stringDateComparator = new StringDateComparator();
            return stringDateComparator.compare(chat.getTime(), t1.getTime());
        }).subscribe(chats1 ->
                setData(chats1)
        );

    }

    @OnClick({R.id.buttonOkDone, R.id.buttonOkDoneS})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonOkDone:
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).setSelectonTab(0);
                }
                break;
            case R.id.buttonOkDoneS:
                break;
        }
    }

    private void setViewNodataFound(boolean isShow) {
        if (isShow) {
            if (getActivity() instanceof MainActivity) {
                setVisibility(0);
            } else if (getActivity() instanceof MainLocalActivity) {
                setVisibility(1);
            }
        } else {
            if (linearLayoutNoDataSecond != null) {
                linearLayoutNoDataSecond.setVisibility(View.GONE);
            }
            if (linearLayoutNoData != null) {
                linearLayoutNoData.setVisibility(View.GONE);
            }

        }
    }

    private void setVisibility(int pos) {

        if (linearLayoutNoDataSecond != null) {
            linearLayoutNoDataSecond.setVisibility((pos == 1) ? View.VISIBLE : View.GONE);
        }
        if (linearLayoutNoData != null) {
            linearLayoutNoData.setVisibility((pos == 0) ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onShow() {
        super.onShow();
      /*  if (listenerRegistration != null)
            listenerRegistration.remove();

        if (listenerRegistration1 != null)
            listenerRegistration1.remove();

        addLiveData();*/
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(EventBusAction action) {
       /* if (action == EventBusAction.RECENTCHATUPDATE) {
            if (listenerRegistration != null)
                listenerRegistration.remove();

            if (listenerRegistration1 != null)
                listenerRegistration1.remove();

            addLiveData();

        }*/
        if (action == EventBusAction.NOTIFICATIONCOUNTUIVISITOR || action == EventBusAction.NOTIFICATIONCOUNTUILOCAL) {
            setDot(imageViewCountNotification);
        }

    }

    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    @OnClick(R.id.imageViewArchive)
    public void onClick() {
        presenter.openNotification(isLocal);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.search, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);

        search(searchView);


        super.onCreateOptionsMenu(menu, inflater);
    }


    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                recentChatAdapter.getFilter().filter(newText, new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int count) {
                        if (recentChatAdapter.countGetItem() == 0) {
                            if (!newText.isEmpty()) {
                                setViewNodataFound(true);
                            }
                        } else {

                            setViewNodataFound(false);
                        }
                    }
                });
                return true;
            }
        });
    }
}
