package com.provocation.checkmate.presentation.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.provocation.checkmate.FragmentManageActivity
import com.provocation.checkmate.R
import com.provocation.checkmate.presentation.login.service.AuthLoginService
import com.provocation.checkmate.presentation.signup.SignupActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText

    private lateinit var btnLogin: MaterialButton
    private lateinit var btnSignup: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()
        setupListeners()

    }
    private fun initViews() {

        emailInput = findViewById(R.id.et_id)
        passwordInput = findViewById(R.id.et_pw)

        btnLogin = findViewById(R.id.btn_login)
        btnSignup = findViewById(R.id.btn_register)

        btnLogin.isEnabled = false
    }
    private fun setupListeners() {

        val textWatcher = createTextWatcher()

        emailInput.addTextChangedListener(textWatcher)
        passwordInput.addTextChangedListener(textWatcher)

        btnLogin.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            if(email.isNotEmpty() && password.isNotEmpty()) {
                sendLoginInformation(email, password)
            }
        }

        btnSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun sendLoginInformation(email: String, password: String) {
        AuthLoginService.sendLoginInformation(
            applicationContext,
            email,
            password,
            onSuccess = {
                runOnUiThread {
                    showToast("로그인 성공")
                    startActivity(Intent(this, FragmentManageActivity::class.java))
                    finish()
                }
            },
            onFailure = { errorMessage -> runOnUiThread {
                showToast("로그인 실패 : $errorMessage")
                }
            })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun updateLoginButtonState() {

        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()
        val isAllValid = email.isNotEmpty() && password.isNotEmpty()

        btnLogin.isEnabled = isAllValid
        btnLogin.setBackgroundColor(if (isAllValid) Color.parseColor("#000000") else Color.parseColor("#D3D3D3"))
        btnLogin.setTextColor(if(isAllValid) Color.parseColor("#FFFFFF") else Color.parseColor("#000000"))

    }

    private fun createTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            updateLoginButtonState()
        }
        override fun afterTextChanged(s: Editable?) {}
    }
}