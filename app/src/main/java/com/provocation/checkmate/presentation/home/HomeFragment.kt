package com.provocation.checkmate.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.provocation.checkmate.MateDetailInfoFragment
import com.provocation.checkmate.R
class HomeFragment : Fragment() {

    // Mate 상세 정보 보기 테스트용 버튼
    private lateinit var btnMDI : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        btnMDI = view.findViewById(R.id.btnHomeToMDI)
//        btnMDI.setOnClickListener {
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, MateDetailInfoFragment())
//                .addToBackStack(null)
//                .commit()
//        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


}