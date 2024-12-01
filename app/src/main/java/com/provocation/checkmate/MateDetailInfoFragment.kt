package com.provocation.checkmate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.provocation.checkmate.config.Conf
import java.time.LocalDate

class MateDetailInfoFragment : Fragment() {

    private var mateId: Long = -1

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

    companion object {
        fun newInstance(mateId: Long): MateDetailInfoFragment {
            val fragment = MateDetailInfoFragment()
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
        // Fragment의 레이아웃을 Inflate
        return inflater.inflate(R.layout.fragment_mate_detail_info, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            mateId = it.getLong("mateId")
        }


        MateDetailInfoService.getMateDetailInfo(mateId,
            requireContext().applicationContext,
            onSuccess = { json -> requireActivity().runOnUiThread{
                // 이미지 로딩 (Glide 사용)
                imageProfile = view.findViewById(R.id.mate_detail_info_profile_image)
                Glide.with(requireContext())
                    .load(json.getString("profileUrl").replace("localhost", Conf.BASE_IP)) // 사용자 이미지 URL을 넣어야 함
                    .transform(CircleCrop())
                    .into(imageProfile)

                // 텍스트 설정
                tvNickname = view.findViewById(R.id.mate_detail_info_name)
                tvNickname.text = json.getString("nickname")

                tvYearOfBirth = view.findViewById(R.id.mate_birth_year)
                tvYearOfBirth.text = json.getInt("birthYear").toString()

                tvYearOfAdmission = view.findViewById(R.id.mate_admission_year)
                tvYearOfAdmission.text = json.getInt("studentId").toString()

                tvGender = view.findViewById(R.id.mate_gender)
                tvGender.text = if (json.getString("gender") == "MALE") "남성" else "여성"

                tvSmoke = view.findViewById(R.id.mate_smoke)
                tvSmoke.text = json.getString("smokingStatus")

                tvSnoring = view.findViewById(R.id.mate_snoring)
                tvSnoring.text = json.getString("snoringStatus")

                tvSleepSensor = view.findViewById(R.id.mate_sleep_sensor)
                tvSleepSensor.text = json.getString("sleepSensitivity")

                tvMbti = view.findViewById(R.id.mate_mbti)
                tvMbti.text = json.getString("mbti")

                tvHopeIntimacy = view.findViewById(R.id.mate_hope_intimacy)
                tvHopeIntimacy.text = json.getString("desiredCloseness")

                tvMajor = view.findViewById(R.id.mate_major)
                tvMajor.text = json.getString("department")

                btnStartChat = view.findViewById(R.id.start_chatting)
                btnStartChat.setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ChatFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }},
            onFailure = { errorMessage -> }
        )


    }
}
