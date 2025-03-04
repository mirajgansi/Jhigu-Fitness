package com.example.jhigu_fitness.model

import android.os.Parcel
import android.os.Parcelable

data class WorkoutModel(
    var productId : String = "",
    var productName : String = "",
    var productDesc : String = "",
    var price : Int = 0,
    var imageUrl: String="",
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productId)
        parcel.writeString(productName)
        parcel.writeString(productDesc)
        parcel.writeInt(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WorkoutModel> {
        override fun createFromParcel(parcel: Parcel): WorkoutModel {
            return WorkoutModel(parcel)
        }

        override fun newArray(size: Int): Array<WorkoutModel?> {
            return arrayOfNulls(size)
        }
    }
}