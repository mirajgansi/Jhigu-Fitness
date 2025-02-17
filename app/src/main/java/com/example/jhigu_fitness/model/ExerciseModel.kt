package com.example.jhigu_fitness.model

import android.os.Parcel
import android.os.Parcelable

data class ExerciseModel(
    var exerciseId: String = "",
    var exerciseName: String = "",
    var description: String = "",
    var sets: Int =0,
    var category: String = "",  // e.g., "Abs", "Chest", "Biceps"
    var imageUrl: String = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(exerciseId)
        parcel.writeString(exerciseName)
        parcel.writeString(description)
        parcel.writeInt(sets)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductModel> {
        override fun createFromParcel(parcel: Parcel): ProductModel {
            return ProductModel(parcel)
        }

        override fun newArray(size: Int): Array<ProductModel?> {
            return arrayOfNulls(size)
        }
    }
}
