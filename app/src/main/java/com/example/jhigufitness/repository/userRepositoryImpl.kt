package com.example.jhigufitness.repository

import android.widget.Toast
import com.example.jhigufitness.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class userRepositoryImpl: UserRepository {

    var database : FirebaseDatabase = FirebaseDatabase.getInstance()

    var reference = database.reference.child("users")

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(
                    true, "Login  success",
                )
            } else {
                callback(false, it.exception?.message.toString())
            }

        }
    }

    override fun register(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(
                    true, "Registration success",
                    auth.currentUser?.uid.toString()
                )
            } else {
                callback(false, it.exception?.message.toString(), "")
            }

        }


    }

    override fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(
                    true, " Password reset link sent to $email")
            } else {
                callback(false, it.exception?.message.toString())
            }

        }
    }

    override fun addUserToDatabase(
        userId: String,
        userModel: UserModel,
        callback: (Boolean, String) -> Unit
    ) {
        reference.child(userId.toString()).setValue(userModel).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(
                    true, "  Registration Successful")
            } else {
                callback(false, it.exception?.message.toString())
            }

          }
    }


}