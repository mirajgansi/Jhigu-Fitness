package com.example.jhigu_fitness.repository



import android.content.Context
import android.net.Uri
import com.example.jhigu_fitness.model.WorkoutModel
import com.google.firebase.auth.FirebaseUser


interface WorkoutRepository {
//    {
//     "success":true
//     "message":"Product fetched successfully"
//    }

    fun addWorkout(
        productModel: WorkoutModel,
        callback: (Boolean, String) -> Unit
    )

    fun updateWork(
        productId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    )

    fun deleteWorkout(
        productId: String,
        callback: (Boolean, String) -> Unit
    )

    fun getWorkoutById(
        productId: String,
        callback: (WorkoutModel?, Boolean, String)
        -> Unit
    )

    fun getAllWorkout(
        callback:
            (List<WorkoutModel>?, Boolean, String) -> Unit
    )
    fun getCurrentWorkout(

    ) : FirebaseUser?

    fun getWorkoutByCategory(
        userID: String,
        callback: (WorkoutModel?, Boolean, String) -> Unit
    )



    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit)

    fun getFileNameFromUri(context: Context, uri: Uri): String?
}
