package com.example.jhigufitness.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jhigufitness.R
import com.example.jhigufitness.databinding.ActivityRegisterBinding
import com.example.jhigufitness.model.UserModel
import com.example.jhigufitness.repository.userRepositoryImpl
import com.example.jhigufitness.utils.LoadingUtils
import com.example.jhigufitness.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    lateinit var userViewModel: UserViewModel

    lateinit var loadingUtils: LoadingUtils





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)

        val repo = userRepositoryImpl()
        userViewModel = UserViewModel(repo)

//        auth = FirebaseAuth.getInstance()
        binding.signUp.setOnClickListener {
            loadingUtils.show()
            var email = binding.registerEmail.text.toString()
            var password = binding.registerPassword.text.toString()
            var fName = binding.registerFname.text.toString()
            var lName = binding.registerLName.text.toString()
            var address = binding.registerPassword.text.toString()
            var contact = binding.registerAddress.text.toString()


            userViewModel.register(email,password){
                success,message,userId ->
                if (success){
                    var userModel = UserModel(
                        userId.toString(),
                        fName, lName, address, email, contact
                    )
                    addUser(userModel)

                }else{
                    loadingUtils.dismiss()
                    Toast.makeText(
                        this@RegisterActivity,
                        message, Toast.LENGTH_LONG
                    ).show()
                }

            }
//            auth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener{
//                    if (it.isSuccessful){
//
//                        var userId = auth.currentUser?.uid
//
//                        var userModel = UserModel(
//                            userId.toString(),
//                            fName, lName, address, contact, email
//
//                        )
//                        reference.child(userId.toString()).setValue(userModel).addOnCompleteListener {
//                            if (it.isSuccessful) {
//                                Toast.makeText(
//                                    this@RegisterActivity,
//                                    "Registration sucess",
//                                    Toast.LENGTH_LONG
//                                ).show()
//                            } else {
//                                Toast.makeText(this@RegisterActivity,
//                                    it.exception?.message.toString(),
//                                    Toast.LENGTH_LONG).show()
//                            }
//                        }
//                    }else{
//                        Toast.makeText(this@RegisterActivity,
//                            it.exception?.message.toString(),
//                            Toast.LENGTH_LONG).show()
//
//
//
//                }
//        }
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}

    private fun addUser(userModel: UserModel) {

        userViewModel.addUserToDatabase(userModel.userId, userModel){
                success, message ->
            if (success) {
                Toast.makeText(this@RegisterActivity,
                    message, Toast.LENGTH_LONG  ).show()

            }else {
                Toast.makeText(this@RegisterActivity,
                    message, Toast.LENGTH_LONG  ).show()
            }
            loadingUtils.dismiss()
        }
    }
}

