package com.example.jhigu_fitness.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkoutModel(
    var workoutId: String = "",
    var name: String = "",
    var description: String = "",
    var reps: Int = 0,
    var imageUrl: String = ""
) : Parcelable
