package com.houseof.code;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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
    private Toolbar mToolbar;

    //Variables
    private ArrayList<Chatroom> mChatrooms = new ArrayList<>();
    private ChatroomsRecyclerAdapter mChatroomsRecyclerAdapter;
    private DocumentSnapshot mLastQueriedDocument;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatrooms_list);
        findUiComponents();
        checkIfAuthenticated();
        initRecyclerView();
        loadProfileImage();
        loadChatrooms();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadChatrooms();
                mChatroomsRecyclerAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /* INIT */

    private void findUiComponents() {
        mRecyclerViewWidget = findViewById(R.id.recyclerViewChatrooms);
        mImageView = findViewById(R.id.profileImage);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mToolbar = (Toolbar) findViewById(R.id.snippet_chatroomlist_toolbar);
        setSupportActionBar(mToolbar);
    }


    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewWidget.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(5);
        mRecyclerViewWidget.addItemDecoration(itemDecorator);
        mChatroomsRecyclerAdapter = new ChatroomsRecyclerAdapter(mChatrooms, this);
        mRecyclerViewWidget.setAdapter(mChatroomsRecyclerAdapter);
    }


    /* GET METHODS */

    private void loadProfileImage() {
        String imageUrl = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhotoUrl().toString();
        Picasso.get().load(imageUrl)
                .error(R.drawable.ic_user_no_photo)
                .into(mImageView);
    }

    private void checkIfAuthenticated() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent startIntent = new Intent(this, SplashScreenActivity.class);
            startActivity(startIntent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    private void loadChatrooms() {
        CollectionReference chatroomsCollectionRef = db
                .collection("chatrooms");

        Query chatroomsQuery = null;
        if(mLastQueriedDocument != null){
            chatroomsQuery = chatroomsCollectionRef.startAfter(mLastQueriedDocument);
        }
        else{
            chatroomsQuery = chatroomsCollectionRef;
        }

        chatroomsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    for(QueryDocumentSnapshot document: task.getResult()){
                        Chatroom chatroom = document.toObject(Chatroom.class);
                        mChatrooms.add(chatroom);
                    }

                    if(task.getResult().size() != 0){
                        mLastQueriedDocument = task.getResult().getDocuments()
                                .get(task.getResult().size() -1);
                    }

                    mChatroomsRecyclerAdapter.notifyDataSetChanged();
                }
                else{
                    Log.e(TAG, "onComplete: Failed", task.getException());
                }
            }
        });
    }

    /* OVERRIDE */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       super.onCreateOptionsMenu(menu);
getMenuInflater().inflate(R.menu.menu_logout, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
if(item.getItemId() == R.id.menuitem_logout){
    FirebaseAuth.getInstance().signOut();
    checkIfAuthenticated();
} return true;
    }

    @Override
    public void onChatroomClick(int position) {
        Intent intent = new Intent(this, ChatroomActivity.class);
        intent.putExtra("selected_chat", mChatrooms.get(position));
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        Log.d(TAG, "onChatroomClick: clicked");
    }

/* TESTING METHODS */

    /*  private void insertFakeChatrooms(){
        for (int i = 0; i < 1000; i++) {
            Chatroom chatroom = new Chatroom();
            chatroom.setChatroomTitle("Chatroom title # " + i);
            chatroom.setChatroomDescription("Example description # " + i);
            chatroom.setChatroomPhoto("");
            mChatrooms.add(chatroom);
        }
        mChatroomsRecyclerAdapter.notifyDataSetChanged();
    }*/

}
