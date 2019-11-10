package com.houseof.code.view_model;

import androidx.lifecycle.ViewModel;

import com.houseof.code.dal.FirestoreMessageListRepositoryCallback;
import com.houseof.code.dal.MessageListLiveData;

public class MessageListViewModel extends ViewModel {
    private MessageListRepository mMessageListRepository = new FirestoreMessageListRepositoryCallback();

    public MessageListLiveData getMessageListLiveData() {
        return mMessageListRepository.getMessageListLiveData();
    }

    public interface MessageListRepository {
        MessageListLiveData getMessageListLiveData();
    }

}
