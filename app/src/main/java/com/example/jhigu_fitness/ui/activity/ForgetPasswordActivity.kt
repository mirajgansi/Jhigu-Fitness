package com.example.jhigu_fitness.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.databinding.ActivityForgotPasswordBinding
import com.example.jhigu_fitness.repository.UserRepositoryImp
import com.example.jhigu_fitness.utils.LoadingUtils
import com.example.jhigu_fitness.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordActivity : AppCompatActivity() {
    lateinit var forgetPasswordBinding: ActivityForgotPasswordBinding
    lateinit var userViewModel: UserViewModel
    lateinit var loadingUtils: LoadingUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        forgetPasswordBinding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(forgetPasswordBinding.root)

        //initializing auth viewmodel
        val mockAuth = FirebaseAuth.getInstance() // Example for Firebase Authentication

        var repo = UserRepositoryImp(mockAuth)
        userViewModel = UserViewModel(repo)

        //initializing loading
        loadingUtils = LoadingUtils(this)
        forgetPasswordBinding.btnForget.setOnClickListener {
            loadingUtils.show()
            var email: String = forgetPasswordBinding.editEmailForget.text.toString()

            userViewModel.forgetPassword(email) { success, message ->
                if (success) {
                    loadingUtils.dismiss()
                    Toast.makeText(this@ForgetPasswordActivity, message, Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    loadingUtils.dismiss()
                    Toast.makeText(this@ForgetPasswordActivity, message, Toast.LENGTH_LONG).show()

                }
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}