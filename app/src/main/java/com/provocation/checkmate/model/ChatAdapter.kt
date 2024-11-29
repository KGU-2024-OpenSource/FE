package com.provocation.checkmate.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.provocation.checkmate.R

class ChatAdapter(private val chatList: List<Chat>, private val myId: Long) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    // ViewHolder
    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMessage: TextView = itemView.findViewById(R.id.tvMessage)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
    }

    // viewType 반환: 0은 내 메시지, 1은 상대 메시지
    override fun getItemViewType(position: Int): Int {
        val chat = chatList[position]
        return if (chat.senderId == myId) {
            0 // 나의 메시지
        } else {
            1 // 상대방의 메시지
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view: View = if (viewType == 0) {
            LayoutInflater.from(parent.context).inflate(R.layout.my_chat_item, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.other_chat_item, parent, false)
        }
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chatList[position]
        holder.tvMessage.text = chat.text
        holder.tvDate.text = chat.date
    }

    override fun getItemCount(): Int = chatList.size
}

