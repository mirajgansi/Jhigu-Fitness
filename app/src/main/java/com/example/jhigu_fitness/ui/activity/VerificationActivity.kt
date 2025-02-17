package com.example.jhigu_fitness.ui.activity.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.databinding.ActivityVerificationBinding

class VerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationBinding
    private val codeDigits = mutableListOf<EditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEditTexts()
        setupClickListeners()
    }

    private fun setupEditTexts() {
        codeDigits.apply {
            add(binding.etCode1)
            add(binding.etCode2)
            add(binding.etCode3)
            add(binding.etCode4)
        }

        codeDigits.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        if (index < codeDigits.lastIndex) {
                            codeDigits[index + 1].requestFocus()
                        }
                    }
                    // Clear field if more than 1 character is entered
                    if (s?.length ?: 0 > 1) {
                        editText.setText(s?.take(1))
                    }
                }
            })
        }
    }

    private fun setupClickListeners() {
        binding.backArrow.setOnClickListener {
            finish() // Go back to previous activity
        }

        binding.btnVerify.setOnClickListener {
            if (isCodeComplete()) {
                // Navigate to Gender Selection Activity
                startActivity(Intent(this, GenderActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Please enter complete verification code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isCodeComplete(): Boolean {
        return codeDigits.all { it.text?.length == 1 }
    }

    private fun getVerificationCode(): String {
        return codeDigits.joinToString("") { it.text.toString() }
    }
}