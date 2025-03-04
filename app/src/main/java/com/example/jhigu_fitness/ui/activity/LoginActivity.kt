package com.example.jhigu_fitness.ui.activity.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.databinding.ActivityLoginBinding
import com.example.jhigu_fitness.repository.UserRepositoryImp
import com.example.jhigu_fitness.ui.activity.ForgetPasswordActivity
import com.example.jhigu_fitness.ui.activity.RegisterActivity
import com.example.jhigu_fitness.utils.LoadingUtils
import com.example.jhigu_fitness.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = UserRepositoryImp()
        userViewModel = UserViewModel(repo)

        // Initializing loading
        loadingUtils = LoadingUtils(this)

        binding.btnLogin.setOnClickListener {
            loadingUtils.show()
            val email: String = binding.etEmail.text.toString()
            val password: String = binding.etPassword.text.toString()

            userViewModel.login(email, password) { success, message ->
                if (success) {
                    Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
                    loadingUtils.dismiss()
                    val intent = Intent(this@LoginActivity, NavigationActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                    loadingUtils.dismiss()
                }
            }
        }

        binding.tvSignUpLink.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }


        // Use binding.root instead of findViewById
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}