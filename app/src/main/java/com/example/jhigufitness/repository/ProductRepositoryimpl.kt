package com.example.jhigufitness.repository


import com.example.jhigufitness.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.net.IDN

class ProductRepositoryimpl: ProductRepository{

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    val ref: DatabaseReference = database.reference.child("products")

    override fun addProduct(productModel: ProductModel, callback: (Boolean, String) -> Unit) {

        var id = ref.push().key.toString()
        productModel.productId = id

        ref.child(id).setValue(productModel).addOnCompleteListener {
            if (it.isSuccessful){
                callback(true, "Product added successfully")
            }else {
                callback(false, "${it.exception?.message}")
            }
        }

    }

    override fun updateProduct(
        productId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(productId).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful){
                callback(true, "Product added successfully")
            }else {
                callback(false, "${it.exception?.message}")
            }
        }

    }

    override fun deleteProduct(productId: String, callback: (Boolean, String) -> Unit) {
        ref.child(productId).removeValue().addOnCompleteListener {
            if (it.isSuccessful){
                callback(true, "Product deleted successfully")
            }else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun getProductById(
        productId: String,
        callback: (ProductModel?, Boolean, String) -> Unit
    ) {
        ref.child(productId).addValueEventListener(
            object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        var model = snapshot.getValue(ProductModel::class.java)
                        callback(model,true,"Product fetched")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null,false,error.message)
                }

            }
        )

    }

    override fun getAllProducts(callback: (List<ProductModel>?, Boolean, String) -> Unit) {
        ref.addValueEventListener(
            object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        var products = mutableListOf<ProductModel>()
                        for(eachData in snapshot.children){
                            var model = eachData.getValue(ProductModel::class.java)
                            if (model != null){
                                products.add(model)
                            }
                        }

                        callback(products,true,"Product fetched")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null,false,error.message)
                }

            }
        )
    }
}