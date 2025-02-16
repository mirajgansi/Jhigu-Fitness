package com.example.jhigu_fitness.model

import android.os.Parcel
import android.os.Parcelable

data class ExerciseModel(
    var exerciseId: String = "",
    var productId : String = "",
    var exerciseName: String = "",
    var description: String = "",
    var sets: Int =0,
    var imageUrl: String = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(exerciseId)
        parcel.writeString(exerciseName)
        parcel.writeString(description)
        parcel.writeInt(sets)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExerciseModel> {  // Changed to ExerciseModel
        override fun createFromParcel(parcel: Parcel): ExerciseModel {  // Changed return type
            return ExerciseModel(parcel)  // Changed constructor call
        }

        override fun newArray(size: Int): Array<ExerciseModel?> {  // Changed return type
            return arrayOfNulls(size)
        }
    }
}
