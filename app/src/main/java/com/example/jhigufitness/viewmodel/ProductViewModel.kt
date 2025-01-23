package com.example.jhigufitness.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.jhigufitness.model.ProductModel
import com.example.jhigufitness.repository.ProductRepository

class ProductViewModel (val repo:ProductRepository) {

    fun addProduct(productModel: ProductModel,
                   callback:(Boolean,String)->Unit){
        repo.addProduct(productModel,callback)

    }

    fun updateProduct(productId:String,
                      data: MutableMap<String,Any>,callback: (Boolean, String) -> Unit){
        repo.updateProduct(productId, data, callback)

    }

    fun deleteProduct(productId: String
                      ,callback: (Boolean, String) -> Unit){
        repo.deleteProduct(productId, callback)

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


    fun getProductById(productId: String)
                       {
        repo.getProductById(productId){
            products,success,message->
            if (success) {
                _products.value = products
            }
        }

    }


    fun getAllProduct(){
        _loading.value = true
        repo.getAllProduct{
            products,success,message->
            if (success){
                _getAllProducts.value = products
                _loading.value = false
            }
        }

    }

}