package com.houseof.code.adapters;

import android.content.Context;
import android.util.Log;
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

import java.util.List;

public class MessagesRecyclerAdapter extends RecyclerView.Adapter<MessagesRecyclerAdapter.ViewHolder> {

    private static final String TAG = "MessagesRecyclerAdapter";
    private static  final int MSG_TYPE_LEFT = 0;
    private static  final int MSG_TYPE_RIGHT = 1;
    private List<Message> mMessages;

    public MessagesRecyclerAdapter(List<Message> messages) {
        this.mMessages = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chat_item_right, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chat_item_left, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = mMessages.get(position);
        holder.show_message.setText(message.getMessageText());
        holder.show_username.setText(message.getMessageUsername());
        Picasso.get().load(message.getSenderAvatar())
                .error(R.drawable.ic_user_no_photo).into(holder.user_avatar);
        if(message.getMessageTimestamp() != null){
        String timestamp = GetTimeAgo.getTimeAgo(message.getMessageTimestamp().getTime());
        holder.message_timestamp.setText(timestamp);
        }
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView show_message;
        TextView show_username;
        ImageView user_avatar;
        TextView message_timestamp;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            show_username = itemView.findViewById(R.id.show_username);
            user_avatar = itemView.findViewById(R.id.user_avatar);
            message_timestamp = itemView.findViewById(R.id.message_timestamp);
        }

    }

        @Override
        public int getItemViewType(int position) {
            FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (mMessages.get(position).getMessageSender().equals(mCurrentUser.getUid())) {
                    return MSG_TYPE_RIGHT;
                } else {
                    return MSG_TYPE_LEFT;
                }
        }


}