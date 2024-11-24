package com.provocation.checkmate.presentation.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.provocation.checkmate.R

class LegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    val btnMale: MaterialButton = findViewById(R.id.btn_male)
    val btnFemale: MaterialButton = findViewById(R.id.btn_female)

    btnMale.isSelected = false

}