package com.example.jhigu_fitness.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jhigu_fitness.model.ExerciseModel
import com.example.jhigu_fitness.repository.ExerciseRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ExerciseViewModel(val repo: ExerciseRepository) : ViewModel() {

    fun addExercise(exerciseModel: ExerciseModel, callback: (Boolean, String) -> Unit) {
        repo.addExercise(exerciseModel, callback)
    }

    fun updateExercise(exerciseId: String, data: MutableMap<String, Any>, callback: (Boolean, String) -> Unit) {
        repo.updateExercise(exerciseId, data, callback)
    }

    fun deleteExercise(exerciseId: String, callback: (Boolean, String) -> Unit) {
        repo.deleteExercise(exerciseId, callback)
    }

    private val _exercise = MutableLiveData<ExerciseModel?>()
    val exercise: LiveData<ExerciseModel?> get() = _exercise

    private var _allExercise = MutableLiveData<List<ExerciseModel>?>()
    var allExercise = MutableLiveData<List<ExerciseModel>?>()
        get() = _allExercise

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    fun getExeriseFromDatabase(productId: String) {
                Log.d("check","i am called outsise")
        _loadingState.value = true
        repo.getExeriseFromDatabase(productId) { exercises, success, message ->
            if (success) {
                Log.d("check","i am inside success")
                _allExercise.value = exercises
            } else {
                // Optionally handle the error (e.g., log or show a message)
                Log.d("check","i am inside failure")
                _allExercise.value = emptyList()
            }
            _loadingState.value = false
        }
    }

    // In your repository or ViewModel:
//    fun getExerciseById(productId: String, exerciseId: String, callback: (ExerciseModel?, Boolean, String) -> Unit) {
//        // Reference the 'productId' node and then the specific 'exerciseId' under it
//        repo.child("productid").child(productId).child(exerciseId).addListenerForSingleValueEvent(object :
//            ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()) {
//                    val exercise = snapshot.getValue(ExerciseModel::class.java)
//                    callback(exercise, true, "Exercise fetched successfully")
//                } else {
//                    callback(null, false, "Exercise not found")
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                callback(null, false, "Error: ${error.message}")
//            }
//        })
//    }






    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        repo.uploadImage(context, imageUri, callback)
    }
}
