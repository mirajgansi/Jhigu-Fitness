package com.example.jhigu_fitness.model

import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.Telephony.Mms.Addr

data class UserModel(
    var userId:String="",
    var firstName:String="",
    var lastName:String="",
    var PhoneNumber:String="",
    var email: String=""
) {
}