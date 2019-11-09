package com.houseof.code.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.houseof.code.R;
import com.houseof.code.models.Message;
import com.houseof.code.util.GetTimeAgo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessagesRecyclerAdapter extends RecyclerView.Adapter<MessagesRecyclerAdapter.ViewHolder> {

    public static  final int MSG_TYPE_LEFT = 0;
    public static  final int MSG_TYPE_RIGHT = 1;
    private ArrayList<Message> mMessages = new ArrayList<>();
    private Context mContext;
    private String mUserAvatar;
    private OnMessageListener mOnMessageListener;
    FirebaseUser fuser;

    public MessagesRecyclerAdapter(ArrayList<Message> messages, Context context, String userAvatar, OnMessageListener onMessageListener) {
        this.mMessages = messages;
        this.mContext = context;
        this.mUserAvatar = userAvatar;
        this.mOnMessageListener = onMessageListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_chat_item_right, parent, false);
            return new ViewHolder(view, mOnMessageListener);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_chat_item_left, parent, false);
            return new ViewHolder(view, mOnMessageListener);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.show_message.setText(mMessages.get(position).getMessageText());
        Picasso.get().load(mUserAvatar).error(R.drawable.ic_user_no_photo).into(holder.user_avatar);
        String timestamp = GetTimeAgo.getTimeAgo(mMessages.get(position).getMessageTimestamp(), mContext);
        holder.message_timestamp.setText(timestamp);
    }


    @Override
    public int getItemCount() {
        return mMessages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView show_message;
        ImageView user_avatar;
        TextView message_timestamp;
        OnMessageListener onMessageListener;

        public ViewHolder(@NonNull View itemView, OnMessageListener onMessageListener) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            user_avatar = itemView.findViewById(R.id.user_avatar);
            message_timestamp = itemView.findViewById(R.id.message_timestamp);
            this.onMessageListener = onMessageListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onMessageListener.onMessageClick(getAdapterPosition());
        }
    }

    public interface OnMessageListener{
        void onMessageClick(int position);
    }
        @Override
        public int getItemViewType(int position) {
            fuser = FirebaseAuth.getInstance().getCurrentUser();
            if (mMessages.get(position).getMessageSender().equals(fuser.getUid())){
                return MSG_TYPE_RIGHT;
            } else {
                return MSG_TYPE_LEFT;
            }
        }


}









