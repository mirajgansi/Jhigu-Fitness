package com.example.jhigu_fitness.repository



import android.content.Context
import android.net.Uri
import com.example.jhigu_fitness.model.ProductModel


interface ProductRepository {
//    {
//     "success":true
//     "message":"Product fetched successfully"
//    }

    fun addWorkout(
        productModel: ProductModel,
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
        callback: (ProductModel?, Boolean, String)
        -> Unit
    )

    fun getAllWorkout(
        callback:
            (List<ProductModel>?, Boolean, String) -> Unit
    )

    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit)

    fun getFileNameFromUri(context: Context, uri: Uri): String?
}
