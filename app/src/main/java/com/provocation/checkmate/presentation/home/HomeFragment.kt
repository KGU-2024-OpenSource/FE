package com.provocation.checkmate.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.provocation.checkmate.IAmYouAreActivity
import com.provocation.checkmate.IAmYouAreService
import com.provocation.checkmate.MateDetailInfoFragment
import com.provocation.checkmate.R
import com.provocation.checkmate.presentation.home.service.UserListService
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.provocation.checkmate.config.Conf

class HomeFragment : Fragment() {

    private lateinit var profileImage: ImageView
    private lateinit var profileName: TextView

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
        recyclerView.addItemDecoration(CustomItemDecoration(1))
        adapter = UserItemAdapter(this, itemList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun loadData() {

        IAmYouAreService.getMyInfo(
            context = requireContext(),
            onSuccess = { myInfoJson ->
                if (isAdded && view != null) {
                    requireActivity().runOnUiThread {

                        Glide.with(requireContext())
                            .load(
                                myInfoJson.getString("profileImageUrl")
                                    .replace("localhost", Conf.BASE_IP)
                            )
                            .transform(CircleCrop())
                            .into(profileImage)

                        profileName.text = myInfoJson.getString("nickname")
                    }
                }},
            onFailure = { errorMessage -> }
        )

        UserListService.fetchUserList(
            context = requireContext(),
            onSuccess = { userList ->
                if (isAdded && view != null) {
                    requireActivity().runOnUiThread {
                        itemList.clear()
                        itemList.addAll(userList)
                        adapter.notifyDataSetChanged()
                    }
                }
            },
            onFailure = { errorMessage ->
                if (isAdded && view != null) {
                    requireActivity().runOnUiThread {
                        showToast("데이터를 불러오지 못했습니다: $errorMessage")
                    }
                }
            }
        )
    }

    private fun initView(view: View) {
        profileImage = view.findViewById(R.id.myImage)
        profileName = view.findViewById(R.id.myNickname)
        recyclerView = view.findViewById(R.id.user_list)
    }

    private fun setupListeners() {
        /*btnUpdate.setOnClickListener {
            val intent = Intent(requireContext(), IAmYouAreActivity::class.java)
            startActivity(intent)
        }

        btnMDI.setOnClickListener {
            // MateDetailInfoFragment로 이동
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MateDetailInfoFragment())
                .addToBackStack(null)
                .commit()
        }*/
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
