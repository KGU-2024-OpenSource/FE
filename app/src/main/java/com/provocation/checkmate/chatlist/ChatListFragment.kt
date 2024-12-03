package com.provocation.checkmate.chatlist

import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.provocation.checkmate.R
import com.provocation.checkmate.chatlist.service.ChatListService
import com.provocation.checkmate.home.CustomItemDecoration
import com.provocation.checkmate.home.UserItemAdapter
import org.w3c.dom.Text

class ChatListFragment : Fragment() {
    private lateinit var mateName: TextView
    private lateinit var lastMessage: TextView

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatListAdapter
    private val chatList = mutableListOf<ChatItemList>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat_list, container, false)

        initView(view)
        setupRecyclerView()
        loadData()

        return view
    }

    private fun initView(view: View) {
        try {
           recyclerView = view.findViewById(R.id.chat_list) ?: throw NullPointerException("RecyclerView not found")
        } catch (e: Exception) {
            Log.e("ChatListFragment", "initView error: ${e.message}")
        }
    }



    private fun setupRecyclerView() {
        recyclerView.addItemDecoration(CustomItemDecoration(1))
        adapter = ChatListAdapter(this, chatList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun loadData() {
        ChatListService.fetchChatList(
            context = requireContext(),
            onSuccess = { list ->
                requireActivity().runOnUiThread {
                    chatList.clear()
                    chatList.addAll(list)
                    adapter.notifyDataSetChanged()
                }
            },
            onFailure = { errorMessage ->
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "데이터 로드 실패: $errorMessage", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        )
    }
}

