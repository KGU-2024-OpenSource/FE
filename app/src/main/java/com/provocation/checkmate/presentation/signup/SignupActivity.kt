package com.provocation.checkmate.presentation.signup

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.provocation.checkmate.R
import com.provocation.checkmate.presentation.signup.service.AuthEmailService
import com.provocation.checkmate.presentation.signup.service.EmailService
import com.provocation.checkmate.presentation.signup.service.SignupService
import com.provocation.checkmate.presentation.signup.service.checkNickname

class SignupActivity : AppCompatActivity() {

    private lateinit var btnBack: MaterialToolbar

    private lateinit var btnMale: MaterialButton
    private lateinit var btnFemale: MaterialButton

    private lateinit var btnSendVerifyCode: MaterialButton
    private lateinit var emailInput: EditText

    private lateinit var btnVerifyCode: MaterialButton
    private lateinit var verifyInput: EditText

    private lateinit var passwordInput: EditText

    private lateinit var nicknameInput: EditText
    private lateinit var btnDuplicateCheck: MaterialButton

    private lateinit var characters: List<LinearLayout>
    private lateinit var dots: List<View>
    private lateinit var btnSignup: MaterialButton

    private var selectedGender: String? = null
    private var selectedProfile: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.toolbar)
        btnMale = findViewById(R.id.btn_male)
        btnFemale = findViewById(R.id.btn_female)

        btnSendVerifyCode = findViewById(R.id.btn_verify_code_send)
        emailInput = findViewById(R.id.et_email)

        btnVerifyCode = findViewById(R.id.btn_verify_code_check)
        verifyInput = findViewById(R.id.et_email_verify)

        passwordInput = findViewById(R.id.et_pw)

        nicknameInput = findViewById(R.id.et_nickname)
        btnDuplicateCheck = findViewById(R.id.btn_dup_check)

        btnSignup = findViewById(R.id.btn_register)

        characters = listOf(
            findViewById(R.id.character_1),
            findViewById(R.id.character_2),
            findViewById(R.id.character_3)
        )
        dots = listOf(
            findViewById(R.id.dot_1),
            findViewById(R.id.dot_2),
            findViewById(R.id.dot_3)
        )
    }
    private fun setupListeners() {
        // EditText 유효성 검사
        val textWatcher = createTextWatcher()

        setupBtnBack()

        emailInput.addTextChangedListener(textWatcher)
        passwordInput.addTextChangedListener(textWatcher)
        nicknameInput.addTextChangedListener(textWatcher)

        // 성별 선택
        setupGenderSelection(btnMale, "MALE", btnFemale)
        setupGenderSelection(btnFemale, "FEMALE", btnMale)

        // 프로필 선택
        characters.forEachIndexed { index, character ->
            character.setOnClickListener {
                selectedProfile = index
                updateCharterSelection(index)
                updateSignupButtonState()
            }
        }

        // 이메일 인증
        btnSendVerifyCode.setOnClickListener {
            val email = emailInput.text.toString().trim()
            if (email.isNotEmpty() && email.contains("@kyonggi.ac.kr")) {
                sendEmailVerification(email)
            } else {
                showToast("경기대학교 이메일만을 입력해주세요")
            }
        }

        // 인증 코드 확인
        btnVerifyCode.setOnClickListener {
            val code = verifyInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            if (code.isNotEmpty()) authenticationVerifyCode(email, code)
            updateSignupButtonState()
        }

        // 닉네임 중복 확인
        btnDuplicateCheck.setOnClickListener {
            val nickname = nicknameInput.text.toString().trim()
            if (nickname.isNotEmpty()) duplicateCheckNickname(nickname)
            updateSignupButtonState()
        }

        // 회원가입
        btnSignup.setOnClickListener { performSignup() }
    }
    private fun setupBtnBack() {
        btnBack.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupGenderSelection(button: MaterialButton, gender: String, otherButton: MaterialButton) {
        button.setOnClickListener {
            selectedGender = gender
            updateGenderSelection(button, otherButton)
            updateSignupButtonState()
        }
    }

    private fun updateGenderSelection(selectedButton: MaterialButton, unselectedButton: MaterialButton) {
        selectedButton.isSelected = true
        unselectedButton.isSelected = false
    }

    private fun updateCharterSelection(selectedIndex: Int) {
        dots.forEachIndexed { index, dot -> dot.isSelected = index == selectedIndex }
    }

    private fun createTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            updateSignupButtonState()
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    private fun updateSignupButtonState() {
        val isEmailValid = emailInput.text.toString().isNotEmpty() && emailInput.text.toString().contains("@kyonggi.ac.kr")
        val isPasswordValid = isPasswordValid(passwordInput.text.toString())
        val isNicknameChecked = btnDuplicateCheck.text == "사용가능"
        val isVerifyCodeChecked = btnVerifyCode.text == "인증 완료"
        val isGenderSelected = selectedGender != null
        val isProfileSelected = selectedProfile != null

        val isAllValid = isEmailValid && isPasswordValid && isNicknameChecked && isVerifyCodeChecked && isGenderSelected && isProfileSelected

        btnSignup.isEnabled = isAllValid
        btnSignup.setBackgroundColor(if (isAllValid) Color.parseColor("#000000") else Color.parseColor("#D3D3D3"))
        btnSignup.setTextColor(if (isAllValid) Color.parseColor("#FFFFFF") else Color.parseColor("#000000"))
    }

    private fun sendEmailVerification(email: String) {
        EmailService.sendVerificationEmail(email,
            onSuccess = { runOnUiThread { showToast("인증코드 전송 완료") } },
            onFailure = { errorMessage -> runOnUiThread { showToast("인증코드 전송이 실패했습니다") } })
            //onFailure = { errorMessage -> runOnUiThread { showToast("인증코드 전송 실패: $errorMessage") } })
    }

    private fun authenticationVerifyCode(email: String, code: String) {
        AuthEmailService.sendVerificationCode(email, code,
            onSuccess = { runOnUiThread { btnVerifyCode.text = "인증 완료" } },
            onFailure = { errorMessage -> runOnUiThread { showToast("인증 코드가 일치하지 않습니다") } })
            //onFailure = { errorMessage -> runOnUiThread { showToast("인증 코드가 일치하지 않습니다: $errorMessage") } })
    }

    private fun duplicateCheckNickname(nickname: String) {
        checkNickname(nickname,
            onSuccess = { runOnUiThread { btnDuplicateCheck.text = "사용가능" } },
            onFailure = { errorMessage -> runOnUiThread { showToast("닉네임이 중복됩니다") } })
            //onFailure = { errorMessage -> runOnUiThread { showToast("닉네임 중복: $errorMessage") } })
    }

    private fun performSignup() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()
        val nickname = nicknameInput.text.toString().trim()

        if (!isPasswordValid(password)) {
            showToast("비밀번호는 영어와 숫자를 포함하며, 최소 10글자 이상이어야 합니다")
            return
        }

        SignupService.signup(email, password, nickname, selectedGender!!, getProfileNumber(),
            onSuccess = { runOnUiThread { showToast("회원가입 성공"); finish() } },
            onFailure = { errorMessage -> runOnUiThread { showToast("회원가입 실패: $errorMessage") } })
    }

    private fun getProfileNumber(): String = when (selectedProfile!!) {
        0 -> "ONE"
        1 -> "TWO"
        2 -> "THREE"
        else -> throw IllegalStateException("Invalid profile selection")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun isPasswordValid(password: String): Boolean {
        val hasLetter = password.any { it.isLetter() }
        val hasDigit = password.any { it.isDigit() }
        val isLongEnough = password.length >= 10
        return hasLetter && hasDigit && isLongEnough
    }
}
