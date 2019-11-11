package com.houseof.code.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.houseof.code.R;
import com.houseof.code.models.Chatroom;

import java.util.List;

public class ChatroomsRecyclerAdapter extends RecyclerView.Adapter<ChatroomsRecyclerAdapter.ViewHolder> {

    private static final String TAG = "ChatroomsRecyclerAdapte";
    private List<Chatroom> mChatrooms;
    private OnChatroomListener mOnChatroomListener;


    public ChatroomsRecyclerAdapter(List<Chatroom> chatrooms, OnChatroomListener onChatroomListener) {
        this.mChatrooms = chatrooms;
        this.mOnChatroomListener = onChatroomListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chatroom_list_item, parent, false);
        return new ViewHolder(view, mOnChatroomListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.chatroomDescription.setText(mChatrooms.get(position).getChatroomDescription());
    holder.chatroomTitle.setText(mChatrooms.get(position).getChatroomTitle());
    }

    @Override
    public int getItemCount() {
        return mChatrooms.size();
    }

    public void setData(List<Chatroom> chatrooms) {
        mChatrooms = chatrooms;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
       TextView chatroomTitle, chatroomDescription;
       OnChatroomListener onChatroomListener;

        ViewHolder(@NonNull View itemView, OnChatroomListener onChatroomListener) {
            super(itemView);
            chatroomTitle = itemView.findViewById(R.id.chatroom_title);
            chatroomDescription = itemView.findViewById(R.id.chatroom_description);
            this.onChatroomListener = onChatroomListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onChatroomListener.onChatroomClick(getAdapterPosition());
        }
    }

    public interface OnChatroomListener{
        void onChatroomClick(int position);
    }
}
