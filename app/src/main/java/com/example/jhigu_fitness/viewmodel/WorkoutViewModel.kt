package com.example.jhigu_fitness.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jhigu_fitness.model.WorkoutModel
import com.example.jhigu_fitness.repository.WorkoutRepository

class WorkoutViewModel (val repo : WorkoutRepository): ViewModel() {
    fun addWorkout(
        productModel: WorkoutModel,
        callback: (Boolean, String) -> Unit
    ) {
        repo.addWorkout(productModel, callback)
    }

    fun updateWork(
        productId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit  // Change here to accept both Boolean and String
    ) {
        repo.updateWork(productId, data) { success, message ->
            callback(success, message)  // Pass both success and message to the callback
        }
    }

    fun deleteWorkout(
        productId: String,
        callback: (Boolean, String) -> Unit
    ) {
        repo.deleteWorkout(productId, callback)
    }

    var _products = MutableLiveData<WorkoutModel?>()
    var products = MutableLiveData<WorkoutModel?>()
        get() = _products

    var _allWorkout = MutableLiveData<List<WorkoutModel>?>()
    var allWorkout = MutableLiveData<List<WorkoutModel>?>()
        get() = _allWorkout

    var _loading = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
        get() = _loading



    fun getWorkoutById(
        productId: String
    ) {
        repo.getWorkoutById(productId) { products, success, message ->
            if (success) {
                _products.value = products
            }

        }
    }

    // Inside ProductViewModel
    fun getAllWorkout() {
        _loading.value = true
        repo.getAllWorkout { products, success, message ->
            if (success) {
                _allWorkout.postValue(products ?: emptyList())
            }
            _loading.value = false
        }
    }

    val filteredWorkout = MutableLiveData<List<WorkoutModel>>()

//    fun getWorkoutByCategory(category: String) {
//        repo.getWorkoutByCategory(category) { workouts, success, _ ->
//            if (success) {
//                filteredWorkout.postValue(workouts ?: emptyList())
//            } else {
//                filteredWorkout.postValue(emptyList()) // Ensures RecyclerView clears on failure
//            }
//        }
//    }


    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        if (imageUri == null) {
            callback("Image URI is null")
            return
        }

        // Handle the image upload logic here
        repo.uploadImage(context, imageUri) { url ->
            if (url != null) {
                callback(url)  // Successfully uploaded image
            } else {
                callback("Failed to upload image")
            }
        }
    }
}