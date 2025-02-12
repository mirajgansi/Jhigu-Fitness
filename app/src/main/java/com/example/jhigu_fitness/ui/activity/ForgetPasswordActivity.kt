package com.example.jhigu_fitness.ui.activity

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jhigu_fitness.databinding.ActivityForgetPasswordBinding

class ForgetPasswordActivity : AppCompatActivity() {

    lateinit var binding : ActivityForgetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.codesend.setOnClickListener {

            val phoneNumber = binding.editTextNumberPassword.text.toString()
            if (phoneNumber.isEmpty()) {
                Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Toast.makeText(this, "Reset link sent to $phoneNumber", Toast.LENGTH_SHORT).show()
            }
        }
    }
