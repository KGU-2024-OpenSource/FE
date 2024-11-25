package com.provocation.checkmate.presentation.signup

import android.app.PendingIntent.OnFinished
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.provocation.checkmate.R
import com.provocation.checkmate.presentation.signup.service.AuthEmailService
import com.provocation.checkmate.presentation.signup.service.EmailService
import com.provocation.checkmate.presentation.signup.service.checkNickname

class SignupActivity : AppCompatActivity() {

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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initViews()
        setupGenderSelection()
        setupCharacterSelection()
        setupEmailVerification()
        setupVerifyCode()
        setupPassword()
        setupNickname()
    }

    private fun initViews() {
        btnMale = findViewById(R.id.btn_male)
        btnFemale = findViewById(R.id.btn_female)

        btnSendVerifyCode = findViewById(R.id.btn_verify_code_send)
        emailInput = findViewById(R.id.et_email)

        btnVerifyCode = findViewById(R.id.btn_verify_code_check)
        verifyInput = findViewById(R.id.et_email_verify)

        passwordInput = findViewById(R.id.et_pw)

        nicknameInput = findViewById(R.id.et_nickname)
        btnDuplicateCheck = findViewById(R.id.btn_dup_check)

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

    private fun setupGenderSelection() {
        btnMale.setOnClickListener {
            updateGenderSelection(btnMale, btnFemale)
        }

        btnFemale.setOnClickListener {
            updateGenderSelection(btnFemale, btnMale)
        }
    }

    private fun updateGenderSelection(selectionButton: MaterialButton,
                                      unselectedButton: MaterialButton) {
        selectionButton.isSelected = true
        unselectedButton.isSelected = false
    }

    private fun setupCharacterSelection() {
        characters.forEachIndexed { index, character ->
            character.setOnClickListener { updateCharterSelection(index) }
        }
    }

    private fun updateCharterSelection(selectedIndex: Int) {
        dots.forEachIndexed { index, dot ->
            dot.isSelected = index == selectedIndex
        }
    }

    private fun setupEmailVerification() {
        btnSendVerifyCode.setOnClickListener {
            val email = emailInput.text.toString().trim()
            if (email.isNotEmpty() && email.contains("@kyonggi.ac.kr")) {
                sendEmailVerification(email)
            } else {
                Toast.makeText(this, "경기대학교 이메일만을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupVerifyCode() {
        btnVerifyCode.setOnClickListener {
            val code = verifyInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            if(code.isNotEmpty())
                authenticationVerifyCode(email, code)
            else {
                Toast.makeText(this, "이메일로 받은 인증번호를 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupPassword() {
        val password = passwordInput.text.toString().trim()
        if (!isPasswordValid(password)) {
            Toast.makeText(this, "비밀번호는 영어와 숫자를 포함하며, 최소 10글자 이상이어야 합니다", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupNickname() {
        btnDuplicateCheck.setOnClickListener {
            val nickname = nicknameInput.text.toString().trim()
            if (nickname.isNotEmpty()) {
                duplicateCheckNickname(nickname)
            } else {
                Toast.makeText(this, "닉네임을 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun authenticationVerifyCode(email: String, code: String) {
        AuthEmailService.sendVerificationCode(
            email,
            code,
            onSuccess = {
                runOnUiThread{
                    btnVerifyCode.text = "인증 완료"
                    btnVerifyCode.setTextColor(Color.WHITE)
                    btnSendVerifyCode.isEnabled = false
                }
            },
            onFailure = { errorMessage ->
                runOnUiThread {
                    Toast.makeText(this, "인증 코드가 일치하지 않습니다: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
    private fun sendEmailVerification(email: String) {
        EmailService.sendVerificationEmail(
            email,
            onSuccess = {
                runOnUiThread{
                    Toast.makeText(this, "인증코드 전송 완료", Toast.LENGTH_SHORT).show()
                    btnSendVerifyCode.text = "전송완료"
                    btnSendVerifyCode.setTextColor(Color.WHITE)
                    btnSendVerifyCode.isEnabled = false
                }
            },
            onFailure = { errorMessage ->
                runOnUiThread {
                    Toast.makeText(this, "인증코드 전송 실패: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun duplicateCheckNickname(nickname: String) {
        checkNickname(
            nickname,
            onSuccess = {
                runOnUiThread{
                    Toast.makeText(this, "닉네임 확인 완료", Toast.LENGTH_SHORT).show()
                    btnDuplicateCheck.text = "사용가능"
                    btnDuplicateCheck.setTextColor(Color.WHITE)
                    btnDuplicateCheck.isEnabled = false
                }
            },
            onFailure = { errorMessage ->
                runOnUiThread {
                    Toast.makeText(this, "이미 사용 중인 닉네임입니다: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun isPasswordValid(password: String): Boolean {
        val hasLetter = password.any { it.isLetter() }
        val hasDigit = password.any { it.isDigit() }
        val isLongEnough = password.length >= 10

        return hasLetter && hasDigit && isLongEnough
    }
}