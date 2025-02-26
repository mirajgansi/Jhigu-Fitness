package com.example.jhigu_fitness.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a35b_crud.model.UserModel
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.databinding.ActivitySignUpBinding
import com.example.jhigu_fitness.repository.UserRepositoryImp
import com.example.jhigu_fitness.ui.activity.ui.activity.LoginActivity
import com.example.jhigu_fitness.utils.LoadingUtils
import com.example.jhigu_fitness.viewmodel.UserViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userRepository = UserRepositoryImp()
        userViewModel = UserViewModel(userRepository)
        loadingUtils = LoadingUtils(this)

        binding.btnSignIn.setOnClickListener {
            loadingUtils.show()
            val fName: String = binding.userName.text.toString()
            val email: String = binding.email.text.toString() // Fixed: Use .text.toString()
            val password: String = binding.Password.text.toString()
            val confirmPassword: String = binding.ConformPassword.text.toString()
            val contact: String = binding.phoneNumber.text.toString()

            // Optional: Add password confirmation check
            if (password != confirmPassword) {
                loadingUtils.dismiss()
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            userViewModel.signup(email, password) { success, message, userId ->
                if (success) {
                    val userModel = UserModel(
                        userId,
                        email, fName, contact
                    )
                    addUser(userModel)
                } else {
                    loadingUtils.dismiss()
                    Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Use binding.root instead of findViewById
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun addUser(userModel: UserModel) {
        userViewModel.addUserToDatabase(userModel.userId, userModel) { success, message ->
            loadingUtils.dismiss() // Dismiss loading in all cases
            if (success) {
                Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
                // Optional: Navigate to LoginActivity on success
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}