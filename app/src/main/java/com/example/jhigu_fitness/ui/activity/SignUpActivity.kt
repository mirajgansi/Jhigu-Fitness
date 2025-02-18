
package com.example.jhigu_fitness.ui.activity

import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.jhigu_fitness.databinding.ActivitySignUpBinding
import com.example.jhigu_fitness.ui.activity.ui.activity.LoginActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Handle back arrow click
        binding.backArrow.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Handle register button click
        binding.btnSignIn.setOnClickListener {
            if (validateFields()) {
                LoginActivity()
            }
        }
    }

    private fun validateFields(): Boolean {
        with(binding) {
            val username = userName.text.toString().trim()
            val email = email.text.toString().trim()
            val phone = phoneNumber.text.toString().trim()
            val password = Password.text.toString().trim()
            val confirmPassword = ConformPassword.text.toString().trim()

            return when {
                username.isEmpty() -> showError("Username")
                email.isEmpty() -> showError("Email")
                phone.isEmpty() -> showError("Phone Number")
                password.isEmpty() -> showError("Password")
                confirmPassword.isEmpty() -> showError("Confirm Password")
                password != confirmPassword -> {
                    Toast.makeText(
                        this@SignUpActivity, // Fixed class reference
                        "Passwords do not match",
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                }
                else -> true
            }
        }
    }

    private fun showError(fieldName: String): Boolean {
        Toast.makeText(this, "$fieldName cannot be empty", Toast.LENGTH_SHORT).show()
        return false
    }


}