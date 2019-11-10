package com.houseof.code.dal;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.houseof.code.models.Message;
import com.houseof.code.models.User;

public class DatabaseHelper  {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DatabaseHelper";
    private Boolean mUserIdExists;

    public void createNewUser(String username, String userAvatar, String userId) {

        CollectionReference userCollectionRef = db.collection("users");

        User user = new User();
        user.setUsername(username);
        user.setUserAvatar(userAvatar);
        user.setUserId(userId);

        userCollectionRef.document(userId).set(user).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Log.d(TAG, "onComplete: Success");
            }else {
                Log.d(TAG, "onComplete: Failed");
            }
        });
    }


    public void createNewMessage(Message message, String documentId) {
        DocumentReference messageColRef =  db.collection("chatrooms").document(documentId).collection("messages").document();
        message.setMessageId(messageColRef.getId());
        messageColRef.set(message).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: MESSAGE SENT");
                } else {
                    Log.d(TAG, "onComplete: MESSAGE FAILED");
                }
            }
        });
    }
}
