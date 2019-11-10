package com.houseof.code;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.houseof.code.adapters.MessagesRecyclerAdapter;
import com.houseof.code.dal.DatabaseHelper;
import com.houseof.code.dal.MessageListLiveData;
import com.houseof.code.models.Chatroom;
import com.houseof.code.models.Message;
import com.houseof.code.view_model.MessageListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatroomActivity extends AppCompatActivity {

    private static final String TAG = "ChatroomActivity";

    /* UI Components */
    private ImageView mImageView;
    private TextView mTextView;
    private EditText mEditMessageText;
    private ImageButton mSendMessage, mAddImage;
    private RecyclerView mRecyclerView;

    //Variables
    private List<Message> mMessageList = new ArrayList<>();
    private MessagesRecyclerAdapter mMessagesRecyclerAdapter;
    private MessageListViewModel mMessageListViewModel;
    private boolean mIsScrolling;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private DocumentSnapshot mLastQueriedDocument;
    private String mCurrentUserId;
    private String mCurrentUserAvatar;
    private String mCurrentUsername;
    private DatabaseHelper mDatabaseHelper;
    private static final int REQUEST_CODE = 1;
    public static Bitmap mSelectedImage = null;
    public Chatroom mChatroom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getParcelable();
        findUiComponents();
        initUiComponents();
        getCurrentUser();
        sendButtonListener();
        imageButtonListener();
        initMessagesRecyclerAdapter();
        initMessageListViewModel();
        getMessages();
        initRecyclerViewOnScrollListener();
        mSelectedImage = null;

    }


    
    /* INIT */

    private void getParcelable() {
        if (getIntent().getBundleExtra("bundle") != null) {
            Bundle b = getIntent().getBundleExtra("bundle");
            mChatroom = b.getParcelable("selected_chat");
        }
    }

    private void initMessageListViewModel() {
        mMessageListViewModel = new ViewModelProvider(this).get(MessageListViewModel.class);
    }

    private void initMessagesRecyclerAdapter() {
        mRecyclerView = findViewById(R.id.messages_list);
        mMessagesRecyclerAdapter = new MessagesRecyclerAdapter(mMessageList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.hasFixedSize();
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mMessagesRecyclerAdapter);
    }

    private void findUiComponents() {
        mSendMessage = findViewById(R.id.chat_send_bttn);
        mAddImage = findViewById(R.id.chat_add_bttn);
        mEditMessageText = findViewById(R.id.edit_message_text);
        mTextView = findViewById(R.id.textChatToolbar);
        mImageView = findViewById(R.id.ivBackArrow);
        Toolbar mToolbar = findViewById(R.id.chat_app_bar);
        setSupportActionBar(mToolbar);
    }

    private void initUiComponents() {
        mTextView.setText(mChatroom.getChatroomTitle());
        mImageView.setOnClickListener(v -> onBackPressed());
    }



    /* CRUD METHODS */

    private void getCurrentUser() {
        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mCurrentUserAvatar = Objects.requireNonNull(mAuth.getCurrentUser().getPhotoUrl()).toString();
        mCurrentUsername = Objects.requireNonNull(mAuth.getCurrentUser().getDisplayName());
    }

    private void getMessages() {
        MessageListLiveData messageListLiveData = mMessageListViewModel.getMessageListLiveData();
        if (messageListLiveData != null) {
            messageListLiveData.observe(this, operation -> {
                switch (operation.type) {
                    case R.string.added:
                        Message addedMessage = operation.message;
                        addMessage(addedMessage);
                        break;

                    case R.string.modified:
                        Message modifiedMessage = operation.message;
                        modifyMessage(modifiedMessage);
                        break;

                    case R.string.removed:
                        Message removedMessage = operation.message;
                        removeMessage(removedMessage);
                }
                mMessagesRecyclerAdapter.notifyDataSetChanged();
            });
        }
    }

    private void addMessage(Message addedMessage) {
        mMessageList.add(addedMessage);
    }

    private void modifyMessage(Message modifiedMessage) {
        for (int i = 0; i < mMessageList.size(); i++) {
            Message currentMessage = mMessageList.get(i);
            if (currentMessage.messageId.equals(modifiedMessage.messageId)) {
                mMessageList.remove(currentMessage);
                mMessageList.add(i, modifiedMessage);
            }
        }
    }

    private void removeMessage(Message removedMessage) {
        for (int i = 0; i < mMessageList.size(); i++) {
            Message currentMessage = mMessageList.get(i);
            if (currentMessage.messageId.equals(removedMessage.messageId)) {
                mMessageList.remove(currentMessage);
            }
        }
    }



   /* PHOTO AND IMAGES */

    private void openPhotoDialog() {
        PhotoDialog dialog = new PhotoDialog();
        dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.PhotoDialog);
        dialog.show(getSupportFragmentManager(), getString(R.string.photo_dialog));
    }



    /* LISTENERS */

    private void sendButtonListener() {
        mSendMessage.setOnClickListener(v -> {
            String writeMessage = mEditMessageText.getText().toString();
            if (!TextUtils.isEmpty(writeMessage)) {
                String id = mChatroom.getChatroomId().trim();
                Message message = new Message();
                message.setMessageUsername(mCurrentUsername);
                message.setSenderAvatar(mCurrentUserAvatar);
                message.setMessageSender(mCurrentUserId);
                message.setMessageText(writeMessage);
                mDatabaseHelper = new DatabaseHelper();
                mDatabaseHelper.createNewMessage(message, id);
            }
            mEditMessageText.setText("");
            mMessagesRecyclerAdapter.notifyDataSetChanged();
        });
    }

    private void imageButtonListener() {
        mAddImage.setOnClickListener(v -> {
            openPhotoDialog();
        });
    }

    private void initRecyclerViewOnScrollListener() {
        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    mIsScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mMessagesRecyclerAdapter.notifyDataSetChanged();
                LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
                if (layoutManager != null) {
                    int firstVisibleMessagePosition = layoutManager.findFirstVisibleItemPosition();
                    int visibleMessageCount = layoutManager.getChildCount();
                    int totalMessageCount = layoutManager.getItemCount();

                    if (mIsScrolling && (firstVisibleMessagePosition + visibleMessageCount == totalMessageCount)) {
                        mIsScrolling = false;
                        getMessages();
                    }
                }
            }
        };
        mRecyclerView.addOnScrollListener(onScrollListener);
    }



    /* OVERRIDE METHODS */


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }



    /* MIGHT USE LATER */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verifyPermissions();
    }

    private void verifyPermissions() {
        Log.d(TAG, "verifyPermissions: asking user for permissions");
        String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[1]) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[2]) == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}