package com.houseof.code.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.houseof.code.R;
import com.houseof.code.models.Chatroom;

import java.util.ArrayList;

public class ChatroomsRecyclerAdapter extends RecyclerView.Adapter<ChatroomsRecyclerAdapter.ViewHolder> {

    private ArrayList<Chatroom> mChatrooms = new ArrayList<>();

    public ChatroomsRecyclerAdapter(ArrayList<Chatroom> chatrooms) {
        this.mChatrooms = chatrooms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chatroom_list_item, parent, false);
        return new ViewHolder(view);
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

    public class ViewHolder extends RecyclerView.ViewHolder{
       TextView chatroomTitle, chatroomDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chatroomTitle = itemView.findViewById(R.id.chatroom_title);
            chatroomDescription = itemView.findViewById(R.id.chatroom_description);
        }
    }
}
