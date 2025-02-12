package com.example.jhigu_fitness.repository

import android.content.Context
import android.net.Uri
import com.example.jhigu_fitness.model.ExerciseModel


interface ExerciseRepository {

    fun addExercise(
        ExerciseModel: ExerciseModel,
        callback: (Boolean, String) -> Unit
    )

    fun updateExercise(
        ExerciseId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    )

    fun deleteExercise(
        ExerciseId: String,
        callback: (Boolean, String) -> Unit
    )

    fun getExerciseById(
        productId: String,
        callback: (ExerciseModel?, Boolean, String)
        -> Unit
    )

    fun getAllExercise(
        callback:
            (List<ExerciseModel>?, Boolean, String) -> Unit
    )
    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit)

    fun getFileNameFromUri(context: Context, uri: Uri): String?
}
