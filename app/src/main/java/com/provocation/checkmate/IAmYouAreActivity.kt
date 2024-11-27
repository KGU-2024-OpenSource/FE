package com.provocation.checkmate

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import android.widget.ToggleButton
import com.provocation.checkmate.presentation.home.HomeActivity

class IAmYouAreActivity : AppCompatActivity() {

    private lateinit var spinnerIAmMbti: Spinner
    private lateinit var spinnerIAmYearOfAdmission: Spinner
    private lateinit var spinnerIAmYearOfBirth: Spinner

    private lateinit var toggleIAmSmokeNo: ToggleButton
    private lateinit var toggleIAmSmokeTobacco: ToggleButton
    private lateinit var toggleIAmSmokeElectronic: ToggleButton

    private lateinit var toggleIAmSnoringYes: ToggleButton
    private lateinit var toggleIAmSnoringNo: ToggleButton
    private lateinit var toggleIAmSnoringLittle: ToggleButton

    private lateinit var toggleIAmSleepSenseLight: ToggleButton
    private lateinit var toggleIAmSleepSenseDark: ToggleButton
    private lateinit var toggleIAmSleepSenseDontKnow: ToggleButton

    private lateinit var editTextIAmMajor: EditText

    private lateinit var toggleIAmHopeIntimacyBusiness: ToggleButton
    private lateinit var toggleIAmHopeIntimacyFriend: ToggleButton
    private lateinit var toggleIAmHopeIntimacyBestFriend: ToggleButton

    private lateinit var toggleYouAreSmokeNo: ToggleButton
    private lateinit var toggleYouAreSmokeTobacco: ToggleButton
    private lateinit var toggleYouAreSmokeElectronic: ToggleButton

    private lateinit var toggleYouAreSnoringYes: ToggleButton
    private lateinit var toggleYouAreSnoringNo: ToggleButton
    private lateinit var toggleYouAreSnoringLittle: ToggleButton

    private lateinit var toggleYouAreSleepSensorLight: ToggleButton
    private lateinit var toggleYouAreSleepSensorDark: ToggleButton
    private lateinit var toggleYouAreSleepSensorDontKnow: ToggleButton

    private lateinit var toggleYouAreMajorSame: ToggleButton
    private lateinit var toggleYouAreMajorDiffer: ToggleButton
    private lateinit var toggleYouAreMajorNoMatter: ToggleButton

    private lateinit var buttonUpdate: Button
    private lateinit var buttonBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_i_am_you_are)

        // 나의 MBTI, 학번, 출생년도
        spinnerIAmMbti = findViewById(R.id.iam_spinnerMbti)
        spinnerIAmYearOfAdmission = findViewById(R.id.iam_spinnerYearOfAdmission)
        spinnerIAmYearOfBirth = findViewById(R.id.iam_spinnerYearOfBirth)
        setupSpinners()

        // 나의 흡연
        toggleIAmSmokeNo = findViewById(R.id.iam_smoke_toggle_no)
        toggleIAmSmokeTobacco = findViewById(R.id.iam_smoke_toggle_tobacco)
        toggleIAmSmokeElectronic = findViewById(R.id.iam_smoke_toggle_electronic)

        // 나의 코골이
        toggleIAmSnoringYes = findViewById(R.id.iam_snoring_toggle_yes)
        toggleIAmSnoringNo = findViewById(R.id.iam_snoring_toggle_no)
        toggleIAmSnoringLittle = findViewById(R.id.iam_snoring_toggle_little)

        // 나의 잠귀
        toggleIAmSleepSenseLight = findViewById(R.id.iam_sleep_sensor_toggle_light)
        toggleIAmSleepSenseDark = findViewById(R.id.iam_sleep_sensor_toggle_dark)
        toggleIAmSleepSenseDontKnow = findViewById(R.id.iam_sleep_sensor_toggle_dont_know)

        // 나의 학과
        editTextIAmMajor = findViewById(R.id.iam_et_major)

        // 희망 친밀도
        toggleIAmHopeIntimacyBusiness = findViewById(R.id.iam_hope_intimacy_toggle_business)
        toggleIAmHopeIntimacyFriend = findViewById(R.id.iam_hope_intimacy_toggle_friend)
        toggleIAmHopeIntimacyBestFriend = findViewById(R.id.iam_hope_intimacy_toggle_best_friend)

        // 너의 흡연
        toggleYouAreSmokeNo = findViewById(R.id.youare_smoke_toggle_no)
        toggleYouAreSmokeTobacco = findViewById(R.id.youare_smoke_toggle_tobacco)
        toggleYouAreSmokeElectronic = findViewById(R.id.youare_smoke_toggle_electronic)

        // 너의 코골이
        toggleYouAreSnoringYes = findViewById(R.id.youare_snoring_toggle_yes)
        toggleYouAreSnoringNo = findViewById(R.id.youare_snoring_toggle_no)
        toggleYouAreSnoringLittle = findViewById(R.id.youare_snoring_toggle_little)

        // 너의 잠귀
        toggleYouAreSleepSensorLight = findViewById(R.id.youare_sleep_sensor_toggle_light)
        toggleYouAreSleepSensorDark = findViewById(R.id.youare_sleep_sensor_toggle_dark)
        toggleYouAreSleepSensorDontKnow = findViewById(R.id.youare_sleep_sensor_toggle_dont_know)

        // 너의 학과
        toggleYouAreMajorSame = findViewById(R.id.youare_major_same)
        toggleYouAreMajorDiffer = findViewById(R.id.youare_major_differ)
        toggleYouAreMajorNoMatter = findViewById(R.id.youare_major_no_matter)

        // 업데이트 버튼 정의
        buttonUpdate = findViewById(R.id.btnUpdate)
        buttonUpdate.setOnClickListener{
            saveUserData()
        }

        // 뒤로가기 버튼 정의
        buttonBack = findViewById(R.id.btnBack)
        buttonBack.setOnClickListener{
            goBack()
        }
    }

    private fun setupSpinners() {
        // MBTI 데이터
        val mbtiList = listOf("ENFJ", "ENTJ", "INFJ", "INTJ", "ENFP", "ENTP", "INFP", "INTP",
            "ESFJ", "ESTJ", "ISFJ", "ISTJ", "ESFP", "ESTP", "ISFP", "ISTP")
        val mbtiAdapter = ArrayAdapter(this, R.layout.spinner_item, mbtiList)
        mbtiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerIAmMbti.adapter = mbtiAdapter

        // 학번 데이터
        val studentIdList = (2016 ..2024).map { it.toString() }
        val studentIdAdapter = ArrayAdapter(this, R.layout.spinner_item, studentIdList)
        studentIdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerIAmYearOfAdmission.adapter = studentIdAdapter

        // 출생년도 데이터
        val birthYearList = (1997..2006).map { it.toString() }
        val birthYearAdapter = ArrayAdapter(this, R.layout.spinner_item, birthYearList)
        birthYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerIAmYearOfBirth.adapter = birthYearAdapter
    }
    private fun saveUserData() {
        val selectedMbti: String = spinnerIAmMbti.selectedItem.toString() // Spinner 선택 값
        val isSmoker: Boolean = toggleIAmSmokeTobacco.isChecked or toggleIAmSmokeElectronic.isChecked
        val major: String = editTextIAmMajor.text.toString()

        // 확인 메시지
        Toast.makeText(
            this,
            "MBTI: $selectedMbti" + "흡연여부: $isSmoker" + "학과: $major",
            Toast.LENGTH_SHORT
        ).show()
    }
    private fun goBack() {
        val intent = Intent(this, HomeActivity::class.java)

        // 새로운 액티비티를 스택에 쌓지 않고 이전 화면으로 돌아가게 하려면, FLAG_ACTIVITY_CLEAR_TOP을 설정
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        // 액티비티 전환
        startActivity(intent)

        // 현재 액티비티 종료 (선택적)
        finish()
    }

}
