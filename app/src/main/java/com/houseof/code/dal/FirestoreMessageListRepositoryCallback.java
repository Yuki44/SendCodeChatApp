package com.houseof.code.dal;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import com.houseof.code.view_model.MessageListViewModel;

import static com.houseof.code.interfaces.IConstants.LIMIT;
import static com.houseof.code.interfaces.IConstants.MESSAGES_COLLECTION;
import static com.houseof.code.interfaces.IConstants.MESSAGE_TIMESTAMP_PROPERTY;

public class FirestoreMessageListRepositoryCallback implements MessageListViewModel.MessageListRepository,
        MessageListLiveData.OnLastVisibleMessageCallback, MessageListLiveData.OnLastMessageReachedCallback {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String id = "qbj4FdbaBmKXaiHiPqOt";
    private DocumentSnapshot lastVisibleMessage;
    private CollectionReference messagesRef = db.collection("chatrooms").document(id).collection(MESSAGES_COLLECTION);
    private Query query = messagesRef.orderBy(MESSAGE_TIMESTAMP_PROPERTY, Query.Direction.ASCENDING).limit(LIMIT);
    private boolean isLastMessageReached;

    @Override
    public MessageListLiveData getMessageListLiveData() {

        if(isLastMessageReached){
        return null;
        }
        if(lastVisibleMessage != null){
            query = query.startAfter(lastVisibleMessage);
        }
        return new MessageListLiveData(query, this, this);
    }

    @Override
    public void setLastVisibleMessage(DocumentSnapshot lastVisibleMessage) {
        this.lastVisibleMessage = lastVisibleMessage;
    }

    @Override
    public void setLastMessageReached(boolean isLastMessageReached) {
this.isLastMessageReached = isLastMessageReached;
    }

}
