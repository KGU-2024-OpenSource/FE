package com.provocation.checkmate.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.provocation.checkmate.IAmYouAreActivity
import com.provocation.checkmate.MateDetailInfoFragment
import com.provocation.checkmate.R
class HomeFragment : Fragment() {

    private lateinit var btnUpdate: MaterialButton
    private lateinit var adapter: UserItemAdapter
    private lateinit var recyclerView: RecyclerView
    private val itemList = arrayListOf<UserItemList>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        initView(view)
        setupListeners()
        setupRecyclerView()
        loadData()
        return view


    }

    private fun setupRecyclerView() {
        adapter = UserItemAdapter(itemList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        adapter.notifyDataSetChanged()
    }

    private fun loadData() {
        itemList.add(UserItemList("민빛별",R.drawable.computer_kgu, 2023, 1999, "#ESFJ", "비즈니스"))
        itemList.add(UserItemList("김은별",R.drawable.computer_kgu, 2024, 1998, "#ISFJ", "비즈니스"))
        itemList.add(UserItemList("박빛상",R.drawable.computer_kgu, 2025, 1997, "#ENFP", "비즈니스"))
        itemList.add(UserItemList("민빛별",R.drawable.computer_kgu, 2023, 1999, "#ESFJ", "비즈니스"))
        itemList.add(UserItemList("김은별",R.drawable.computer_kgu, 2024, 1998, "#ISFJ", "비즈니스"))
        itemList.add(UserItemList("박빛상",R.drawable.computer_kgu, 2025, 1997, "#ENFP", "비즈니스"))
        itemList.add(UserItemList("민빛별",R.drawable.computer_kgu, 2023, 1999, "#ESFJ", "비즈니스"))
        itemList.add(UserItemList("김은별",R.drawable.computer_kgu, 2024, 1998, "#ISFJ", "비즈니스"))
        itemList.add(UserItemList("박빛상",R.drawable.computer_kgu, 2025, 1997, "#ENFP", "비즈니스"))

    }

    private fun initView(view: View) {
        btnUpdate = view.findViewById(R.id.btn_update)
        recyclerView = view.findViewById(R.id.user_list)
    }

    private fun setupListeners() {
        btnUpdate.setOnClickListener {
            val intent = Intent(requireContext(), IAmYouAreActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


}