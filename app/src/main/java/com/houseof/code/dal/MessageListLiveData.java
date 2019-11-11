package com.houseof.code.dal;


import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.houseof.code.R;
import com.houseof.code.models.Message;
import com.houseof.code.models.Operation;

import static com.houseof.code.interfaces.IConstants.LIMIT;


public class MessageListLiveData extends LiveData<Operation> implements EventListener<QuerySnapshot> {

    private static final String TAG = "MessageListLiveData";
    private Query mQuery;
    private ListenerRegistration mListenerRegistration;
    private OnLastVisibleMessageCallback mOnLastVisibleMessageCallback;
    private OnLastMessageReachedCallback mOnLastMessageReachedCallback;


    MessageListLiveData(Query query, OnLastVisibleMessageCallback onLastVisibleMessageCallback, OnLastMessageReachedCallback onLastMessageReachedCallback) {
        this.mQuery = query;
        this.mOnLastVisibleMessageCallback = onLastVisibleMessageCallback;
        this.mOnLastMessageReachedCallback = onLastMessageReachedCallback;

    }

    @Override
    protected void onActive() {
        mListenerRegistration = mQuery.addSnapshotListener(this);
    }

    @Override
    protected void onInactive() {
        mListenerRegistration.remove();
    }


    @Override
    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
        if (e != null) return;

        for (DocumentChange documentChange : querySnapshot.getDocumentChanges()) {
            switch (documentChange.getType()) {
                case ADDED:
                    Message addedMessage = documentChange.getDocument().toObject(Message.class);
                    Operation addOperation = new Operation(addedMessage, R.string.added);
                    setValue(addOperation);
                    break;

                case MODIFIED:
                    Message modifiedMessage = documentChange.getDocument().toObject(Message.class);
                    Operation modifyOperation = new Operation(modifiedMessage, R.string.modified);
                    setValue(modifyOperation);
                    break;

                case REMOVED:
                    Message removedMessage = documentChange.getDocument().toObject(Message.class);
                    Operation removeOperation = new Operation(removedMessage, R.string.removed);
                    setValue(removeOperation);
            }
        }

        int querySnapshotSize = querySnapshot.size();
        if (querySnapshotSize < LIMIT) {
            mOnLastMessageReachedCallback.setLastMessageReached(true);
        } else {
            DocumentSnapshot lastVisibleMessage = querySnapshot.getDocuments().get(querySnapshotSize - 1);
            mOnLastVisibleMessageCallback.setLastVisibleMessage(lastVisibleMessage);
        }
    }


    interface OnLastVisibleMessageCallback {
        void setLastVisibleMessage(DocumentSnapshot lastVisibleMessage);
    }

    interface OnLastMessageReachedCallback {
        void setLastMessageReached(boolean isLastMessageReached);
    }

}
