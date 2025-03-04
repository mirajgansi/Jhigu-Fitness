package com.example.jhigu_fitness

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.jhigu_fitness.databinding.ActivityLoginBinding
import com.example.jhigu_fitness.util.UiUtil
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check if user is already logged in
        auth.currentUser?.let {
            navigateToMain()
        }

        binding.submitBtn.setOnClickListener { login() }

        binding.goToSignupBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }

    private fun setInProgress(inProgress: Boolean) {
        binding.progressBar.visibility = if (inProgress) View.VISIBLE else View.GONE
        binding.submitBtn.visibility = if (inProgress) View.GONE else View.VISIBLE
    }

    private fun login() {
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInput.error = "Invalid Email"
            return
        }
        if (password.length < 6) {
            binding.passwordInput.error = "Minimum 6 characters required"
            return
        }

        loginWithFirebase(email, password)
    }

    private fun loginWithFirebase(email: String, password: String) {
        setInProgress(true)
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                UiUtil.showToast(this, "Login successful")
                navigateToMain()
            }
            .addOnFailureListener {
                UiUtil.showToast(applicationContext, it.localizedMessage ?: "Login failed")
                setInProgress(false)
            }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
