package com.example.jhigu_fitness.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.jhigu_fitness.model.ProductModel
import com.example.jhigu_fitness.repository.ProductRepository

class ProductViewModel (val repo : ProductRepository) {
    fun addWorkout(
        productModel: ProductModel,
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

    var _products = MutableLiveData<ProductModel?>()
    var products = MutableLiveData<ProductModel?>()
        get() = _products

    var _allWorkout = MutableLiveData<List<ProductModel>?>()
    var allWorkout = MutableLiveData<List<ProductModel>?>()
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
                _allWorkout.value = products
            } else {
                Log.e("ProductViewModel", "Failed to get all products: $message")
            }
            _loading.value = false
        }
    }

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
