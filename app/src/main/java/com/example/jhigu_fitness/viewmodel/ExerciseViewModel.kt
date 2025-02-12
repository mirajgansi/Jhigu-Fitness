package com.example.jhigu_fitness.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jhigu_fitness.model.ExerciseModel
import com.example.jhigu_fitness.repository.ExerciseRepository

class ExerciseViewModel(private val repo: ExerciseRepository): ViewModel() {

    private val _exercise = MutableLiveData<ExerciseModel?>()
    val exercise: LiveData<ExerciseModel?> get() = _exercise

    private val _allExercise = MutableLiveData<List<ExerciseModel>?>()
    val allExercise: LiveData<List<ExerciseModel>?> get() = _allExercise

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun addExercise(exerciseModel: ExerciseModel, callback: (Boolean, String) -> Unit) {
        repo.addExercise(exerciseModel, callback)
    }

    fun updateExercise(
        productId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        repo.updateExercise(productId, data, callback)
    }

    fun deleteExercise(productId: String, callback: (Boolean, String) -> Unit) {
        repo.deleteExercise(productId, callback)
    }

    fun getExerciseById(productId: String) {
        repo.getExerciseById(productId) { exercise, success, _ ->
            if (success) {
                _exercise.value = exercise
            }
        }
    }

    fun getAllExercise() {
        _loading.value = true
        repo.getAllExercise { exercise, success, _ ->
            if (success) {
                _allExercise.postValue(exercise ?: emptyList())
            }
            _loading.value = false
        }
    }

    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        repo.uploadImage(context, imageUri) { url ->
            callback(url ?: "Failed to upload image")
        }
    }
}
