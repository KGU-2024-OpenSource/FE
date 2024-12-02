package com.provocation.checkmate.chatlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.provocation.checkmate.R

class ChatListAdapter(private val chatList: List<ChatItemList>) :
    RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>() {

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receiverName: TextView = itemView.findViewById(R.id.mate_name)
        val lastMessage: TextView = itemView.findViewById(R.id.last_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatRoom = chatList[position]
        holder.receiverName.text = chatRoom.receiverName
        holder.lastMessage.text = chatRoom.lastMessage
    }

    override fun getItemCount(): Int = chatList.size

}