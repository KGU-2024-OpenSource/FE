package com.provocation.checkmate.chatlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.provocation.checkmate.ChatFragment
import com.provocation.checkmate.MateDetailInfoFragment
import com.provocation.checkmate.R

class ChatListAdapter(
    val fragment: Fragment,
    private val chatList: List<ChatItemList>
) :
    RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>() {

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receiverName: TextView = itemView.findViewById(R.id.mate_name)
        val receiverProfileImageUrl: ImageView = itemView.findViewById(R.id.userChatImageView)
        val lastMessage: TextView = itemView.findViewById(R.id.last_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatRoom = chatList[position]
        holder.receiverName.text = chatRoom.receiverName
        Glide.with(holder.receiverProfileImageUrl.context)
            .load(chatRoom.receiverProfileImageUrl)
            .into(holder.receiverProfileImageUrl)
        holder.lastMessage.text = chatRoom.lastMessage


        holder.itemView.setOnClickListener {
            fragment.requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ChatFragment.newInstance(chatRoom.senderId))
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int = chatList.size

}