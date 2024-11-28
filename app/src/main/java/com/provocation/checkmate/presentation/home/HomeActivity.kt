package com.provocation.checkmate.presentation.home

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.provocation.checkmate.IAmYouAreActivity
import com.provocation.checkmate.R
class HomeActivity : AppCompatActivity() {

    private lateinit var btn_Chatbar : View
    private lateinit var btn_IUbar : View
    private lateinit var btn_Homebar : View
    private lateinit var btn_update : MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
        setupListener()

    }

    private fun initViews() {
        btn_Chatbar = findViewById(R.id.btn_section_chat)
        btn_Homebar = findViewById(R.id.btn_section_home)
        btn_IUbar = findViewById(R.id.btn_section_I_U)
        btn_update = findViewById(R.id.btn_update)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupListener() {
        btn_IUbar.setOnClickListener {
            val intent = Intent(this, IAmYouAreActivity::class.java)
            startActivity(intent)
        }
        btn_Homebar.setOnClickListener {
            showToast("홈 전환")
        }
        btn_Chatbar.setOnClickListener {
            showToast("Chat 전환")
        }

        btn_update.setOnClickListener {
            val intent = Intent(this, IAmYouAreActivity::class.java)
            startActivity(intent)
        }

    }

}