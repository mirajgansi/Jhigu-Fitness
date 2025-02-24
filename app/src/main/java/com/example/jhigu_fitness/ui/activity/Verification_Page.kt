package com.example.jhigu_fitness.ui.activity

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.databinding.ActivityVerificationPage2Binding

class Verification_Page : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationPage2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize ViewBinding
        binding = ActivityVerificationPage2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        binding.button2.setOnClickListener {
            val code = getVerificationCode()
            if (code.isNotEmpty()) {
                Toast.makeText(this, "Verification code entered: $code", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter the verification code.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView.setOnClickListener {
            Toast.makeText(this, "Back arrow clicked.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getVerificationCode(): String {
        val code1 = binding.editTextNumberDecimal.text.toString().trim()
        val code2 = binding.editTextNumberDecimal2.text.toString().trim()
        val code3 = binding.editTextNumberDecimal3.text.toString().trim()
        val code4 = binding.editTextNumberDecimal4.text.toString().trim()

        return if (code1.isNotEmpty() && code2.isNotEmpty() && code3.isNotEmpty() && code4.isNotEmpty()) {
            "$code1$code2$code3$code4"
        } else {
            ""
        }
    }
}
