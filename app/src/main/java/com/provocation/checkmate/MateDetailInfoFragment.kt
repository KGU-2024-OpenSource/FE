package com.provocation.checkmate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class MateDetailInfoFragment : Fragment() {

    private lateinit var imageProfile: ImageView
    private lateinit var tvNickname: TextView
    private lateinit var tvYearOfBirth: TextView
    private lateinit var tvYearOfAdmission: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvSmoke: TextView
    private lateinit var tvSnoring: TextView
    private lateinit var tvSleepSensor: TextView
    private lateinit var tvMbti: TextView
    private lateinit var tvHopeIntimacy: TextView
    private lateinit var tvMajor: TextView

    private lateinit var btnStartChat: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment의 레이아웃을 Inflate
        return inflater.inflate(R.layout.fragment_mate_detail_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이미지 로딩 (Glide 사용)
        imageProfile = view.findViewById(R.id.mate_detail_info_profile_image)
        Glide.with(requireContext())
            .load(R.drawable.flower_kgu) // 사용자 이미지 URL을 넣어야 함
            .transform(CircleCrop())
            .into(imageProfile)

        // 텍스트 설정
        tvNickname = view.findViewById(R.id.mate_detail_info_name)
        tvNickname.text = "꽃기룡"

        tvYearOfBirth = view.findViewById(R.id.mate_birth_year)
        tvYearOfBirth.text = "2003"

        tvYearOfAdmission = view.findViewById(R.id.mate_admission_year)
        tvYearOfAdmission.text = "22"

        tvGender = view.findViewById(R.id.mate_gender)
        tvGender.text = "남성"

        tvSmoke = view.findViewById(R.id.mate_smoke)
        tvSmoke.text = "노담"

        tvSnoring = view.findViewById(R.id.mate_snoring)
        tvSnoring.text = "피곤하면\n가끔"

        tvSleepSensor = view.findViewById(R.id.mate_sleep_sensor)
        tvSleepSensor.text = "밝음"

        tvMbti = view.findViewById(R.id.mate_mbti)
        tvMbti.text = "ISTP"

        tvHopeIntimacy = view.findViewById(R.id.mate_hope_intimacy)
        tvHopeIntimacy.text = "친구"

        tvMajor = view.findViewById(R.id.mate_major)
        tvMajor.text = "컴퓨터공학전공"

        btnStartChat = view.findViewById(R.id.start_chatting)
        btnStartChat.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ChatFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
