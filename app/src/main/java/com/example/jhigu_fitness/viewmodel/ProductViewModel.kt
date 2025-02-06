package com.example.jhigu_fitness.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.jhigu_fitness.model.ProductModel
import com.example.jhigu_fitness.repository.ProductRepository

class ProductViewModel (val repo : ProductRepository){
    fun addProduct(
        productModel: ProductModel,
        callback: (Boolean, String) -> Unit
    ){
        repo.addWorkout(productModel,callback)
    }

    fun updateProduct(
        productId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit  // Change here to accept both Boolean and String
    ) {
        repo.updateWork(productId, data) { success, message ->
            callback(success, message)  // Pass both success and message to the callback
        }
    }

    fun deleteProduct(
        productId: String,
        callback: (Boolean, String) -> Unit
    ){
        repo.deleteWorkout(productId, callback)
    }

    var _products = MutableLiveData<ProductModel>()
    var products = MutableLiveData<ProductModel>()
        get() = _products

    var _getAllProducts = MutableLiveData<List<ProductModel>>()
    var getAllProducts = MutableLiveData<List<ProductModel>>()
        get() = _getAllProducts

    var _loading = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
        get() = _loading

    fun getProductById(
        productId: String
    ){
        repo.getWorkoutById(productId){
                products,success,message->
            if(success){
                _products.value = products
            }

        }
    }

    fun getAllProductFunc(){
        _loading.value = true
        repo.getAllProducts { products, success, message ->
            if(success){
                _getAllProducts.value = products
                _loading.value = false
            } else {
                Log.e("ProductViewModel", "Failed to get all products: $message")
                _loading.value = false
            }
        }
    }

    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit){
        repo.uploadImage(context, imageUri, callback)
    }
}