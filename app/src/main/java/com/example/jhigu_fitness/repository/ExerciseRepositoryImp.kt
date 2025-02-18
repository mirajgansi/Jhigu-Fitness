package com.example.jhigu_fitness.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.OpenableColumns
import android.util.Log
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.jhigu_fitness.model.ExerciseModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.InputStream
import java.util.concurrent.Executors

class ExerciseRepositoryImp : ExerciseRepository {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    val ref: DatabaseReference = database.reference
        .child("exercises")

    override fun addExercise(ExerciseModel: ExerciseModel, callback: (Boolean, String) -> Unit) {
        val id = ref.push().key
        if (id == null) {
            Log.e("Firebase", "Failed to generate ID")
            callback(false, "Error generating ID")
            return
        }

        ExerciseModel.exerciseId = id
        Log.d("Firebase", "Adding exercise: $ExerciseModel")

        ref.child(id).setValue(ExerciseModel).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase", "Exercise added successfully")
                callback(true, "Exercise added successfully")
            } else {
                Log.e("Firebase", "Failed to add exercise", task.exception)
                callback(false, task.exception?.message ?: "Unknown error")
            }
        }
    }


    override fun updateExercise(
        ExerciseId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(ExerciseId).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Product updated successfully")
            } else {
                callback(false, "${it.exception?.message}")

            }
        }
    }

    override fun deleteExercise(ExerciseId: String, callback: (Boolean, String) -> Unit) {
        ref.child(ExerciseId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Product deleted successfully")
            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun getExeriseFromDatabase(
        productId: String,
        callback: (List<ExerciseModel>?, Boolean, String) -> Unit
    ) {
        // Query: get exercises where the "productId" field equals the provided productId
        ref.orderByChild("productName").equalTo(productId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val exercises = mutableListOf<ExerciseModel>()
                    for (data in snapshot.children) {
                        val exercise = data.getValue(ExerciseModel::class.java)
                        if (exercise != null) {
                            Log.d("checkpoint","i am here")
                            Log.d("checkpoint",exercise.exerciseName)
                            exercises.add(exercise)
                        }
                    }
                    if (exercises.isNotEmpty()) {
                        callback(exercises, true, "Exercises fetched successfully")
                    } else {
                        callback(emptyList(), true, "No exercises found for the given productId")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null, false, error.message)
                }
            })
    }


    override fun getAllExercise(callback: (List<ExerciseModel>?, Boolean, String) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var Exercise = mutableListOf<ExerciseModel>()
                    for (eachData in snapshot.children) {
                        var model = eachData.getValue(ExerciseModel::class.java)
                        if (model != null) {
                            Exercise.add(model)
                        }
                    }

                    callback(Exercise, true, "Product fetched")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }

    private val cloudinary = Cloudinary(
        mapOf(
            "cloud_name" to "dkscpr3wa",
            "api_key" to "776537619471962",
            "api_secret" to "S_YN8k3Ne5Vlnc96hsQ5bAnOPik"
        )
    )

    override fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
                var fileName = getFileNameFromUri(context, imageUri)

                fileName = fileName?.substringBeforeLast(".") ?: "uploaded_image"

                val response = cloudinary.uploader().upload(
                    inputStream, ObjectUtils.asMap(
                        "public_id", fileName,
                        "resource_type", "image"
                    )
                )

                var imageUrl = response["url"] as String?

                imageUrl = imageUrl?.replace("http://", "https://")

                Handler(Looper.getMainLooper()).post {
                    callback(imageUrl)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Handler(Looper.getMainLooper()).post {
                    callback(null)
                }
            }
        }
    }

    override fun getFileNameFromUri(context: Context, uri: Uri): String? {
        var fileName: String? = null
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
        return fileName
    }
}