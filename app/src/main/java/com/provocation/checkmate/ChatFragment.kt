package com.provocation.checkmate

import ChatWebSocket
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.provocation.checkmate.config.Conf
import com.provocation.checkmate.model.Chat
import com.provocation.checkmate.model.ChatAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Callback
import org.json.JSONObject
import kotlin.math.max

class ChatFragment : Fragment() {

    private var myId: Long = -1
    private var mateId: Long = -1
    private var roomId: Long = -1

    private lateinit var btnBack: Button
    private lateinit var tvUserName: TextView

    private lateinit var chatView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var btnSend: ImageView

    private lateinit var chatList: MutableList<Chat>
    private lateinit var chatAdapter: ChatAdapter

    private lateinit var chatWebSocket: ChatWebSocket
    private val SOCKET_URL = "ws://${Conf.BASE_IP}:8080/chat/"

    private var scrollType: Int = 0
    private var isLast = true

    companion object {
        fun newInstance(mateId: Long): ChatFragment {
            val fragment = ChatFragment()
            val args = Bundle()
            args.putLong("mateId", mateId)

            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            mateId = it.getLong("mateId")
        }

        btnBack = view.findViewById(R.id.btnChatBack)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        tvUserName = view.findViewById(R.id.tvUserName)

        chatView = view.findViewById(R.id.chat_view)
        chatView.layoutManager = LinearLayoutManager(requireContext())
        chatList = mutableListOf()

        chatView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                scrollType = newState
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        // 스크롤이 멈춘 상태
                    }
                    RecyclerView.SCROLL_STATE_DRAGGING -> {
                        // 사용자가 스크롤을 시작한 상태
                    }
                    RecyclerView.SCROLL_STATE_SETTLING -> {
                        // 스크롤이 끝나고 리사이클러 뷰가 정지하는 상태
                    }
                }
            }
        })

        chatView.viewTreeObserver.addOnGlobalLayoutListener {
            if (isLast && scrollType != RecyclerView.SCROLL_STATE_DRAGGING)
                chatView.scrollToPosition(chatList.size - 1)
        }

        messageEditText = view.findViewById(R.id.message_input)
        messageEditText.setOnTouchListener {
            _, event ->
            isLast = ((chatView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() == chatView.layoutManager?.itemCount?.minus(1))
            false
        }

        btnSend = view.findViewById(R.id.btnSend)
        setChatRoom()

        chatWebSocket = ChatWebSocket("${SOCKET_URL}$roomId",
            requireActivity().applicationContext) { receivedMessage ->
            requireActivity().runOnUiThread {
                // 메시지 수신 시 RecyclerView 업데이트
                val message = receivedMessage.getString("message")
                val senderId = receivedMessage.getLong("senderId")
                val date = receivedMessage.getString("createdAt")
                chatList.add(Chat(message, senderId, date))
                chatView.scrollToPosition(chatList.size - 1)
            }
        }
        chatWebSocket.connect()
    }

    fun setChatRoom() {
        IAmYouAreService.getUserNickname(
            mateId,
            context = requireActivity().applicationContext,
            onSuccess = {
                nickname -> requireActivity().runOnUiThread {
                    tvUserName.text = nickname
                }
            },
            onFailure = { errorMessage -> }
        )

        ChatService.getChatRoomId(
            mateId,
            context = requireActivity().applicationContext,
            onSuccess = { id -> requireActivity().runOnUiThread{
                    roomId = id
                    loadAllChat()
                }
            },
            onFailure = { errorMessage -> }
        )
    }
    fun loadAllChat() {
        ChatService.getAllChat(
            roomId,
            context = requireActivity().applicationContext,
            onSuccess = { jsonArray ->
                if (isAdded) {
                    requireActivity().runOnUiThread {

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)

                            val text: String = jsonObject.getString("message")
                            val senderId: Long = jsonObject.getLong("senderId")
                            // 현재 시그마 기준으론 채팅방에 상대 프로필 이미지를 띄우지 않지만, response에 함께 담겨옴으로 일단 받음.
                            val senderProfileImageUrl: String =
                                jsonObject.getString("senderProfileImageUrl")
                                    .replace("localhost", Conf.BASE_IP)
                            val date: String =  jsonObject.getString("createdAt")

                            chatList.add(Chat(text, senderId, date))
                        }
                        setChatForm()
                    }
                }
            },
            onFailure = { errorMessage -> }
        )
    }
    fun setChatForm() {
        IAmYouAreService.getMyInfo(
            context = requireActivity().applicationContext,
            onSuccess = { myInfoJson ->
                if (isAdded) {
                    requireActivity().runOnUiThread {
                        myId = myInfoJson.getLong("memberId")

                        chatAdapter = ChatAdapter(chatList, myId)
                        chatView.adapter = chatAdapter
                        chatView.scrollToPosition(chatList.size - 1)

                        btnSend.setOnClickListener {
                            sendMessage()
                        }
                    }
                }
            },
            onFailure = { errorMessage ->
            }
        )
    }
    fun sendMessage() {
        val text: String = messageEditText.text.toString()
        if (text.isNotEmpty()) {
            val chat = Chat(text, myId)

            //chatList.add(chat)
            chatView.scrollToPosition(chatList.size - 1)
            messageEditText.text.clear()

            ChatService.sendMessage(
                text,
                roomId,
                context = requireActivity().applicationContext,
                onSuccess = {
                },
                onFailure = { errorMessage ->
                    if (isAdded) {
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireActivity(), errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
            val jsonObject = JSONObject()
            jsonObject.put("message", text)
            jsonObject.put("senderId", myId)
            jsonObject.put("roomId", roomId)
            jsonObject.put("senderProfileImageUrl", "null url")
            jsonObject.put("createdAt", chat.date)
            jsonObject.put("senderName", "null name")

            chatWebSocket.sendMessage(jsonObject)
        }
    }
}