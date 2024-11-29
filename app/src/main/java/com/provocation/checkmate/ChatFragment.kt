package com.provocation.checkmate

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.provocation.checkmate.model.Chat
import com.provocation.checkmate.model.ChatAdapter

class ChatFragment : Fragment() {

    private lateinit var chatView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var btnSend: Button

    private lateinit var chatList: MutableList<Chat>
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myId: Long = 0 // temp
        val mateId: Long = 1
        chatList = mutableListOf(
            Chat("Hello!", myId, "2024-11-28 10:00:00"),
            Chat("How are you?", myId, "2024-11-28 10:01:00"),
            Chat("I'm fine, thank you.", mateId),
            Chat("What about you?", mateId),
        ) // 해당 부분을 추후 연동하여 가져와야합니다.

        chatView = view.findViewById(R.id.chat_view)
        chatView.layoutManager = LinearLayoutManager(requireContext())

        chatAdapter = ChatAdapter(chatList, myId)
        chatView.adapter = chatAdapter
        chatView.scrollToPosition(chatList.size - 1)


        messageEditText = view.findViewById(R.id.message_input)
        btnSend = view.findViewById(R.id.btnSend)
        btnSend.setOnClickListener {
            val text: String = messageEditText.text.toString()
            if (text.isNotEmpty()) {
                chatList.add(Chat(text=text, senderId=myId))
                chatView.scrollToPosition(chatList.size - 1)
            }
            messageEditText.text.clear()
        }
    }

}