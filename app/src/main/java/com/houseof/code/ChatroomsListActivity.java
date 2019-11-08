package com.houseof.code;

import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.houseof.code.adapters.ChatroomsRecyclerAdapter;
import com.houseof.code.models.Chatroom;
import com.houseof.code.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;

public class ChatroomsListActivity extends AppCompatActivity implements ChatroomsRecyclerAdapter.OnChatroomListener {

    private static final String TAG = "ChatroomsListActivity";

    //UI Components
    private RecyclerView mRecyclerViewWidget;

    //Variables
    private ArrayList<Chatroom> mChatrooms = new ArrayList<>();
    private ChatroomsRecyclerAdapter mChatroomsRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatrooms_list);
        mRecyclerViewWidget = findViewById(R.id.recyclerViewChatrooms);

        initRecyclerView();
        insertFakeChatrooms();
    }
    
    private void insertFakeChatrooms(){
        for (int i = 0; i < 1000; i++) {
            Chatroom chatroom = new Chatroom();
            chatroom.setChatroomTitle("Chatroom title # " + i);
            chatroom.setChatroomDescription("Example description # " + i);
            chatroom.setChatroomPhoto("");
            mChatrooms.add(chatroom);
        }
        mChatroomsRecyclerAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewWidget.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(5);
        mRecyclerViewWidget.addItemDecoration(itemDecorator);
        mChatroomsRecyclerAdapter = new ChatroomsRecyclerAdapter(mChatrooms, this);
        mRecyclerViewWidget.setAdapter(mChatroomsRecyclerAdapter);
    }

    @Override
    public void onChatroomClick(int position) {
        //Intent intent = new Intent(this, NewActivity.class);
        //startActivity(intent);
        Log.d(TAG, "onChatroomClick: clicked");
    }
}
