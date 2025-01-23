package com.example.jhigufitness.repository

import com.example.jhigufitness.model.UserModel

interface UserRepository {

    fun login(email:String, password:String,callback:(Boolean,String) ->Unit)

    fun register(email:String, password:String, callback: (Boolean, String, String) -> Unit)

    fun forgetPassword(email:String,callback:(Boolean,String) ->Unit)

    fun addUserToDatabase(userId : String, userModel: UserModel,
                          callback:(Boolean,String) ->Unit)
}