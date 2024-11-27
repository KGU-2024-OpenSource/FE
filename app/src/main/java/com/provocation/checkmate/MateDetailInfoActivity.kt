package com.provocation.checkmate

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class MateDetailInfoActivity : AppCompatActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mate_detail_info)

        imageProfile = findViewById(R.id.mate_detail_info_profile_image)
        Glide.with(this)
            .load(R.drawable.flower_kgu) // load 안에 사용자에 맞게 바꿔주어야 함
            .transform(CircleCrop())
            .into(imageProfile)

        // setText 부분 모두 사용자에 맞게 변경해야함
        tvNickname = findViewById(R.id.mate_detail_info_name)
        tvNickname.setText("꽃기룡")

        tvYearOfBirth = findViewById(R.id.mate_birth_year)
        tvYearOfBirth.setText("2003")

        tvYearOfAdmission = findViewById(R.id.mate_admission_year)
        tvYearOfAdmission.setText("22")

        tvGender = findViewById(R.id.mate_gender)
        tvGender.setText("남성")

        tvSmoke = findViewById(R.id.mate_smoke)
        tvSmoke.setText("노담")

        tvSnoring = findViewById(R.id.mate_snoring)
        tvSnoring.setText("피곤하면\n가끔")

        tvSleepSensor = findViewById(R.id.mate_sleep_sensor)
        tvSleepSensor.setText("밝음")

        tvMbti = findViewById(R.id.mate_mbti)
        tvMbti.setText("ISTP")

        tvHopeIntimacy = findViewById(R.id.mate_hope_intimacy)
        tvHopeIntimacy.setText("친구")

        tvMajor = findViewById(R.id.mate_major)
        tvMajor.setText("컴퓨터공학전공")
    }
}