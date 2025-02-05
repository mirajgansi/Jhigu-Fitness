package com.example.jhigu_fitness.ui.activity.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jhigu_fitness.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        // Handle login button click
        binding.btnSignIn.setOnClickListener {
            performLogin()
        }

        // Handle sign up text click
        binding.tvSignUpLink.setOnClickListener {
            navigateToSignUp()
        }
    }

    private fun performLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (validateInput(email, password)) {
            // Proceed with login logic
            attemptLogin(email, password)
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        var isValid = true

        if (email.isEmpty()) {
            binding.etEmail.error = "Email cannot be empty"
            isValid = false
        }

        if (password.isEmpty()) {
            binding.etPassword.error = "Password cannot be empty"
            isValid = false
        }

        return isValid
    }

    private fun attemptLogin(email: String, password: String) {
        // Add your actual login logic here
        if (isCredentialsValid(email, password)) {
            navigateToMainActivity()
        } else {
            binding.etPassword.error = "Invalid credentials"
        }
    }

    private fun isCredentialsValid(email: String, password: String): Boolean {
        // Replace with real authentication logic
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}