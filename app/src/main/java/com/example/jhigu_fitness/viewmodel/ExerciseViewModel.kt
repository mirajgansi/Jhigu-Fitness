package com.example.jhigu_fitness.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jhigu_fitness.model.ExerciseModel
import com.example.jhigu_fitness.repository.ExerciseRepository

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

    private val _allExercise = MutableLiveData<List<ExerciseModel>?>()
    val allExercise: LiveData<List<ExerciseModel>?> = _allExercise

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    fun getExerciseById(exerciseId: String) {
        repo.getExerciseById(exerciseId) { exercise, success, _ ->
            if (success) {
                _exercise.value = exercise
            }
        }
    }

    fun fetchAllExercises() {  // ✅ Renamed function
        _loadingState.value = true
        repo.getAllExercise { exercises, success, _ ->
            if (success) {
                _allExercise.value = exercises
            }
            _loadingState.value = false
        }
    }


    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        repo.uploadImage(context, imageUri, callback)
    }
}
