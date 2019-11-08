package com.houseof.code;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.houseof.code.adapters.ChatroomsRecyclerAdapter;
import com.houseof.code.models.Chatroom;
import com.houseof.code.util.VerticalSpacingItemDecorator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatroomsListActivity extends AppCompatActivity implements ChatroomsRecyclerAdapter.OnChatroomListener {

    private static final String TAG = "ChatroomsListActivity";

    //UI Components
    private RecyclerView mRecyclerViewWidget;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CircleImageView mImageView;

    //Variables
    private ArrayList<Chatroom> mChatrooms = new ArrayList<>();
    private ChatroomsRecyclerAdapter mChatroomsRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatrooms_list);
        mRecyclerViewWidget = findViewById(R.id.recyclerViewChatrooms);
        findUiComponents();
        initRecyclerView();
        insertFakeChatrooms();
        loadProfileImage();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mChatroomsRecyclerAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void loadProfileImage() {
        String imageUrl = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhotoUrl().toString();
        Picasso.get().load(imageUrl)
                .error(R.drawable.ic_user_no_photo)
                .into(mImageView);
    }

    private void findUiComponents() {
        mRecyclerViewWidget = findViewById(R.id.recyclerViewChatrooms);
        mImageView = findViewById(R.id.profileImage);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
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
