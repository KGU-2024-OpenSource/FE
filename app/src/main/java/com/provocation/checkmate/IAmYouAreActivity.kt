package com.provocation.checkmate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import android.widget.ToggleButton
import com.provocation.checkmate.model.IAmYouAreInfo
import com.provocation.checkmate.home.HomeFragment

class IAmYouAreActivity : AppCompatActivity() {

    private val mbtiList = listOf(
        "INFP", "INTP", "INFJ", "INTJ",
        "ISFP", "ISTP", "ISFJ", "ISTJ",
        "ENFP", "ENTP", "ENFJ", "ENTJ",
        "ESFP", "ESTP", "ESFJ", "ESTJ"
    )
    val studentIdList = (2016 ..2024).map { it.toString() }
    val birthYearList = (1997..2006).map { it.toString() }

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
            onBackPressedDispatcher.onBackPressed();
        }

        loadUserDate()
    }

    private fun setupSpinners() {
        // MBTI 데이터
        val mbtiAdapter = ArrayAdapter(this, R.layout.spinner_item, mbtiList)
        mbtiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerIAmMbti.adapter = mbtiAdapter

        // 학번 데이터
        val studentIdAdapter = ArrayAdapter(this, R.layout.spinner_item, studentIdList)
        studentIdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerIAmYearOfAdmission.adapter = studentIdAdapter

        // 출생년도 데이터
        val birthYearAdapter = ArrayAdapter(this, R.layout.spinner_item, birthYearList)
        birthYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerIAmYearOfBirth.adapter = birthYearAdapter
    }
    private fun saveUserData() {
        var myMBTI: String = spinnerIAmMbti.selectedItem.toString()
        var myStudentId: Long = spinnerIAmYearOfAdmission.selectedItem.toString().toLong()
        var myBirthYear: Int = spinnerIAmYearOfBirth.selectedItem.toString().toInt()

        var mySmokingStatus: String = ""
        if (toggleIAmSmokeNo.isChecked) {
            mySmokingStatus = toggleIAmSmokeNo.textOn.toString()
        } else if (toggleIAmSmokeTobacco.isChecked) {
            mySmokingStatus = toggleIAmSmokeTobacco.textOn.toString()
        } else if (toggleIAmSmokeElectronic.isChecked) {
            mySmokingStatus = toggleIAmSmokeElectronic.textOn.toString()
        }

        var mySnoringStatus: String = ""
        if (toggleIAmSnoringNo.isChecked) {
            mySnoringStatus = toggleIAmSnoringNo.textOn.toString()
        } else if (toggleIAmSnoringYes.isChecked) {
            mySnoringStatus = toggleIAmSnoringYes.textOn.toString()
        } else if (toggleIAmSnoringLittle.isChecked) {
            mySnoringStatus = toggleIAmSnoringLittle.textOn.toString()
        }

        var mySleepSensitivity: String = ""
        if (toggleIAmSleepSenseLight.isChecked) {
            mySleepSensitivity = toggleIAmSleepSenseLight.textOn.toString()
        } else if (toggleIAmSleepSenseDark.isChecked) {
            mySleepSensitivity = toggleIAmSleepSenseDark.textOn.toString()
        } else if (toggleIAmSleepSenseDontKnow.isChecked) {
            mySleepSensitivity = toggleIAmSleepSenseDontKnow.textOn.toString()
        }

        var myDepartment: String = editTextIAmMajor.text.toString()

        var myDesiredCloseness: String = ""
        if (toggleIAmHopeIntimacyBusiness.isChecked) {
            myDesiredCloseness = toggleIAmHopeIntimacyBusiness.textOn.toString()
        } else if (toggleIAmHopeIntimacyFriend.isChecked) {
            myDesiredCloseness = toggleIAmHopeIntimacyFriend.textOn.toString()
        } else if (toggleIAmHopeIntimacyBestFriend.isChecked) {
            myDesiredCloseness = toggleIAmHopeIntimacyBestFriend.textOn.toString()
        }

        var yourSmokingStatus: String = ""
        if (toggleYouAreSmokeNo.isChecked) {
            yourSmokingStatus = toggleYouAreSmokeNo.textOn.toString()
        } else if (toggleYouAreSmokeTobacco.isChecked) {
            yourSmokingStatus = toggleYouAreSmokeTobacco.textOn.toString()
        } else if (toggleYouAreSmokeElectronic.isChecked) {
            yourSmokingStatus = toggleYouAreSmokeElectronic.textOn.toString()
        }

        var yourSnoringStatus: String = ""
        if (toggleYouAreSnoringNo.isChecked) {
            yourSnoringStatus = toggleYouAreSnoringNo.textOn.toString()
        } else if (toggleYouAreSnoringYes.isChecked) {
            yourSnoringStatus = toggleYouAreSnoringYes.textOn.toString()
        } else if (toggleYouAreSnoringLittle.isChecked) {
            yourSnoringStatus = toggleYouAreSnoringLittle.textOn.toString()
        }

        var yourSleepSensitivity: String = ""
        if (toggleYouAreSleepSensorLight.isChecked) {
            yourSleepSensitivity = toggleYouAreSleepSensorLight.textOn.toString()
        } else if (toggleYouAreSleepSensorDark.isChecked) {
            yourSleepSensitivity = toggleYouAreSleepSensorDark.textOn.toString()
        } else if (toggleYouAreSleepSensorDontKnow.isChecked) {
            yourSleepSensitivity = toggleYouAreSleepSensorDontKnow.textOn.toString()
        }

        var yourDepartment: String = ""
        if (toggleYouAreMajorSame.isChecked) {
            yourDepartment = toggleYouAreMajorSame.textOn.toString()
        } else if (toggleYouAreMajorDiffer.isChecked) {
            yourDepartment = toggleYouAreMajorDiffer.textOn.toString()
        } else if (toggleYouAreMajorNoMatter.isChecked) {
            yourDepartment = toggleYouAreMajorNoMatter.textOn.toString()
        }

        IAmYouAreService.sendIamYouAreInfo(
            myMBTI, myStudentId, myBirthYear,
            mySmokingStatus, mySnoringStatus, mySleepSensitivity,
            myDepartment, myDesiredCloseness,
            yourSmokingStatus, yourSnoringStatus, yourSleepSensitivity,
            yourDepartment,
            context = applicationContext,
            onSuccess = { runOnUiThread {
                    Toast.makeText(this, "업데이트 되었습니다.", Toast.LENGTH_SHORT).show()
                }
            },
            onFailure = { errorMessage -> runOnUiThread{ Toast.makeText(this, "정보를 모두 기입해주세요.", Toast.LENGTH_SHORT).show()} }
        )
    }
    private fun loadUserDate() {
        IAmYouAreService.getIamYouAreInfo(applicationContext,
            onSuccess = {
                info -> runOnUiThread{
                    changeUserData(info)
                }
            }, onFailure = {
                runOnUiThread {
                    //Toast.makeText(this, "나는 너는 정보가 아직 없습니다.\n등록해주세요.", Toast.LENGTH_SHORT).show()
                }
            })
    }
    private fun changeUserData(info: IAmYouAreInfo) {
        spinnerIAmMbti.post {
            spinnerIAmMbti.setSelection(mbtiList.indexOf(info.myMBTI))
        }
        spinnerIAmYearOfAdmission.post {
            spinnerIAmYearOfAdmission.setSelection(studentIdList.indexOf(info.myStudentId.toString()))
        }
        spinnerIAmYearOfBirth.post {
            spinnerIAmYearOfBirth.setSelection(birthYearList.indexOf(info.myBirthYear.toString()))
        }

        toggleIAmSmokeNo.isChecked = (info.mySmokingStatus.equals(toggleIAmSmokeNo.textOn))
        toggleIAmSmokeTobacco.isChecked = (info.mySmokingStatus.equals(toggleIAmSmokeTobacco.textOn))
        toggleIAmSmokeElectronic.isChecked = (info.mySmokingStatus.equals(toggleIAmSmokeElectronic.textOn))

        toggleIAmSnoringNo.isChecked = (info.mySnoringStatus.equals(toggleIAmSnoringNo.textOn))
        toggleIAmSnoringYes.isChecked = (info.mySnoringStatus.equals(toggleIAmSnoringYes.textOn))
        toggleIAmSnoringLittle.isChecked = (info.mySnoringStatus.equals(toggleIAmSnoringLittle.textOn))

        toggleIAmSleepSenseLight.isChecked = (info.mySleepSensitivity.equals(toggleIAmSleepSenseLight.textOn))
        toggleIAmSleepSenseDark.isChecked = (info.mySleepSensitivity.equals(toggleIAmSleepSenseDark.textOn))
        toggleIAmSleepSenseDontKnow.isChecked = (info.mySleepSensitivity.equals(toggleIAmSleepSenseDontKnow.textOn))

        editTextIAmMajor.setText(info.myDepartment)

        toggleIAmHopeIntimacyBusiness.isChecked = (info.myDesiredCloseness.equals(toggleIAmHopeIntimacyBusiness.textOn))
        toggleIAmHopeIntimacyFriend.isChecked = (info.myDesiredCloseness.equals(toggleIAmHopeIntimacyFriend.textOn))
        toggleIAmHopeIntimacyBestFriend.isChecked = (info.myDesiredCloseness.equals(toggleIAmHopeIntimacyBestFriend.textOn))

        toggleYouAreSmokeNo.isChecked = (info.yourSmokingStatus.equals(toggleYouAreSmokeNo.textOn))
        toggleYouAreSmokeTobacco.isChecked = (info.yourSmokingStatus.equals(toggleYouAreSmokeTobacco.textOn))
        toggleYouAreSmokeElectronic.isChecked = (info.yourSmokingStatus.equals(toggleYouAreSmokeElectronic.textOn))

        toggleYouAreSnoringNo.isChecked = (info.yourSnoringStatus.equals(toggleYouAreSnoringNo.textOn))
        toggleYouAreSnoringYes.isChecked = (info.yourSnoringStatus.equals(toggleYouAreSnoringYes.textOn))
        toggleYouAreSnoringLittle.isChecked = (info.yourSnoringStatus.equals(toggleYouAreSnoringLittle.textOn))

        toggleYouAreSleepSensorLight.isChecked = (info.yourSleepSensitivity.equals(toggleYouAreSleepSensorLight.textOn))
        toggleYouAreSleepSensorDark.isChecked = (info.yourSleepSensitivity.equals(toggleYouAreSleepSensorDark.textOn))
        toggleYouAreSleepSensorDontKnow.isChecked = (info.yourSleepSensitivity.equals(toggleYouAreSleepSensorDontKnow.textOn))

        toggleYouAreMajorSame.isChecked = (info.yourDepartment.equals(toggleYouAreMajorSame.textOn))
        toggleYouAreMajorDiffer.isChecked = (info.yourDepartment.equals(toggleYouAreMajorDiffer.textOn))
        toggleYouAreMajorNoMatter.isChecked = (info.yourDepartment.equals(toggleYouAreMajorNoMatter.textOn))
    }
}
