package com.example.jhigu_fitness.repository

interface UserRepository {
    fun login(email:String,password:String,callBack:(Boolean,String,Int)->Unit)
    fun regsister(email:String,password:String,callBack:(Boolean,String,String)->Unit)
    fun forgetpassword(email:String,callBack:(Boolean,String,Int)->Unit)
    fun addUserTodatabase(UserId:String,UserModel:String,callBack:(Boolean,String,Int)->Unit)

}