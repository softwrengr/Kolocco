package com.kooloco.ui.chat.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kooloco.R;
import com.kooloco.constant.Common;
import com.kooloco.model.Chat;
import com.kooloco.util.TimeConvertUtils;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class RecentChatAdapter extends RecyclerView.Adapter<RecentChatAdapter.ViewHolder> implements Filterable {
    Context context;
    List<Chat> recentChats;

    List<Chat> recentChatsTemp;
    String userId = "";

    String TAG = ";;;";

    private FirebaseFirestore database;
    private CallBack callBack;

    Map<String, EventListener<QuerySnapshot>> listener = new LinkedHashMap<>();


    public RecentChatAdapter(Context context, List<Chat> recentChats, String userId, FirebaseFirestore database, CallBack callBack) {
        this.context = context;
        this.recentChats = recentChats;
        this.recentChatsTemp = recentChats;
        this.userId = userId;
        this.database = database;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_recent_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        String url = recentChats.get(position).getSenderId().equalsIgnoreCase(userId) ? recentChats.get(position).getReceiverImage() : recentChats.get(position).getSenderImageUrl();


        if (!url.isEmpty()) {
            Picasso.with(context).load(url).transform(new CircleTransform()).placeholder(R.drawable.user_round).resizeDimen(R.dimen.dp_60,R.dimen.dp_60).centerCrop().error(R.drawable.user_round).into(holder.imageViewProfile);

        } else {
            Picasso.with(context).load(R.drawable.user_round).transform(new CircleTransform()).placeholder(R.drawable.user_round).error(R.drawable.user_round).into(holder.imageViewProfile);
        }

        holder.customTextViewName.setText(recentChats.get(position).getSenderId().equalsIgnoreCase(userId) ? recentChats.get(position).getReceiverName() : recentChats.get(position).getSenderName());

        holder.customTextMessage.setText(recentChats.get(position).getMessage());

        holder.customTextOrderId.setText(recentChats.get(position).getOrderId().isEmpty() ? " " : context.getString(R.string.order_id) + " " + recentChats.get(position).getOrderId());

        String date = TimeConvertUtils.dateAndTimeGet(TimeConvertUtils.dateTimeConvertLocalToLocal(recentChats.get(position).getTime(), "MMM d, yyyy hh:mm:ss a", "dd MMM, yyyy HH:mm:ss"), "dd MMM, yyyy HH:mm:ss");

        date = TimeConvertUtils.dateTimeConvertLocalToLocalNotification(date, "MMM dd, yyyy", "dd MMM yyyy");

        holder.customTextTime.setText(date);

        holder.imageViewLive.setImageDrawable(context.getResources().getDrawable(R.drawable.drawable_recent_chat_gray));

        int count = 0;

        if (recentChats.get(position).getReceiverId().equalsIgnoreCase(userId)) {
            if (recentChats.get(position).getChatCount() != null) {
                if (!recentChats.get(position).getChatCount().isEmpty()) {

                    try {
                        count = Integer.parseInt(recentChats.get(position).getChatCount());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        count = 0;
                    }

                }
            }
        }

        if (holder.textViewChatCount != null) {
            holder.textViewChatCount.setText("" + count);
            holder.textViewChatCount.setVisibility((count == 0) ? View.INVISIBLE : View.VISIBLE);
        }


        if (database != null) {
            EventListener<QuerySnapshot> eventListener;

         /*   if (listener.containsKey(recentChats.get(position).getUniqId())) {
                eventListener = listener.get(recentChats.get(position).getUniqId());
            } else {


                listener.put(recentChats.get(position).getChatId(), eventListener);
            }*/
/*

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

                                if (!isChatRead) {
                                    count = count + 1;
                                }

                                break;
                            case MODIFIED:
                                Log.d(TAG, "Recent chat Count Modify Data: " + document.getDocument().getData());
                                if (isChatRead) {
                                    count = count - 1;
                                } else {
                                }
                                break;
                            case REMOVED:
                                Log.d(TAG, "Recent chat Count Removed Data: " + document.getDocument().getData());
                                if (isChatRead) {
                                    //   count = count - 1;
                                }

                                break;
                        }

                    }

                    Chat chat = recentChats.get(position);
                    chat.setChatCount(count);
                    recentChats.set(position, chat);

                    if (holder.textViewChatCount != null) {
                        holder.textViewChatCount.setText("" + count);
                        holder.textViewChatCount.setVisibility((count == 0) ? View.INVISIBLE : View.VISIBLE);
                    }
                }
            };
            ListenerRegistration listenerRegistration = database.collection(Common.FireStore.TAB_NAME_CHAT).whereEqualTo(Common.FireStore.FIELD_CHAT_READ, false).whereEqualTo(Common.FireStore.FIELD_RECEIVER_ID, userId).whereEqualTo(Common.FireStore.FIELD_UNIQ_ID, recentChats.get(position).getChatId()).addSnapshotListener(eventListener);
*/

      /*      database.collection(Common.FireStore.TAB_NAME_CHAT).whereEqualTo(Common.FireStore.FIELD_CHAT_READ, false).whereEqualTo(Common.FireStore.FIELD_RECEIVER_ID, userId).whereEqualTo(Common.FireStore.FIELD_UNIQ_ID, recentChats.get(position).getChatId()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot documentSnapshots) {
                    Log.e("::::", "Changes " + documentSnapshots.getDocuments().size());

                    int count = documentSnapshots.getDocuments().size();

                    if (holder.textViewChatCount != null) {
                        holder.textViewChatCount.setText("" + count);
                        holder.textViewChatCount.setVisibility((count == 0) ? View.INVISIBLE : View.VISIBLE);
                    }
                }
            });*/

            database.collection(Common.FireStore.TAB_NAME_USER).document(recentChats.get(position).getSenderId().equalsIgnoreCase(userId) ? recentChats.get(position).getReceiverId() : recentChats.get(position).getSenderId()).addSnapshotListener((documentSnapshot, e) -> {
                if (e != null) {
                    return;
                }


                if (documentSnapshot != null && documentSnapshot.exists()) {
                    // Log.d(TAG, source + " data: " + snapshot.getData());

                    Boolean aBoolean = documentSnapshot.getBoolean(Common.FireStore.FIELD_IS_ONLINE);

                    holder.imageViewLive.setImageDrawable(aBoolean ? context.getResources().getDrawable(R.drawable.drawable_recent_chat_green) : context.getResources().getDrawable(R.drawable.drawable_recent_chat_gray));

/*
                    String imageUrl = "";

                    imageUrl = documentSnapshot.getString(Common.FireStore.FIELD_SENDER_IMAGE_URL);

                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        Picasso.with(context).load(imageUrl).transform(new CircleTransform()).placeholder(R.drawable.user_round).error(R.drawable.user_round).into(holder.imageViewProfile);
                    }
*/

                } else {
                    // Log.d(TAG, source + " data: null");
                }


            });


        }

    }

    @Override
    public int getItemCount() {
        return recentChats.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    recentChats = recentChatsTemp;
                } else {

                    ArrayList<Chat> filteredList = new ArrayList<>();

                    for (Chat chat : recentChatsTemp) {


                        if ((chat.getSenderId().equalsIgnoreCase(userId) ? chat.getReceiverName() : chat.getSenderName()).toLowerCase().contains(charString.toLowerCase()) || chat.getOrderId().toLowerCase().contains(charString.toLowerCase())) {

                            filteredList.add(chat);
                        }
                    }

                    recentChats = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = recentChats;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                recentChats = (ArrayList<Chat>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewProfile)
        ImageView imageViewProfile;
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.customTextMessage)
        AppCompatTextView customTextMessage;
        @BindView(R.id.customTextTime)
        AppCompatTextView customTextTime;
        @BindView(R.id.imageViewLive)
        ImageView imageViewLive;
        @BindView(R.id.textViewChatCount)
        AppCompatTextView textViewChatCount;
        @BindView(R.id.customTextOrderId)
        AppCompatTextView customTextOrderId;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.onClickSelect(recentChats.get(getAdapterPosition()));
                }
            });
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickSelect(Chat chat);
    }

    public int countGetItem() {
        return recentChats.size();
    }

}
