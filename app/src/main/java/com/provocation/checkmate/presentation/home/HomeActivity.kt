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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
        setupListener()

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

    }

    private fun initViews() {
        btn_Chatbar = findViewById(R.id.btn_section_chat)
        btn_Homebar = findViewById(R.id.btn_section_home)
        btn_IUbar = findViewById(R.id.btn_section_I_U)

        Log.d("HomeActivity", "btn_Chatbar: $btn_Chatbar")
        Log.d("HomeActivity", "btn_Homebar: $btn_Homebar")
        Log.d("HomeActivity", "btn_IUbar: $btn_IUbar")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}